package com.example.backend.service;

import com.example.backend.dto.ConfirmTransfer;
import com.example.backend.dto.ResponseFailed;
import com.example.backend.dto.ResponseSuccess;
import com.example.backend.dto.Transfer;
import com.example.backend.exception.InternalServerErrorExceptionId1;
import com.example.backend.exception.InternalServerErrorExceptionId2;
import com.example.backend.exception.RejectedOperationException;
import com.example.backend.repo.CardRepository;
import com.example.backend.util.LoggerMessages;
import com.example.backend.util.ServiceMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import static com.example.backend.BackendApplication.LOGGER;

@Service
public class TransferServiceImpl implements TransferService {

    private final CardRepository repository;

    @Autowired
    public TransferServiceImpl(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Object transfer(Transfer transfer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ServiceMessages.valueOf(bindingResult.getAllErrors().get(0).getDefaultMessage()).getMessage();
            ResponseFailed responseFailed = new ResponseFailed(message, 1);
            LOGGER.info(LoggerMessages.FAILED_TRANSFER.getMessage(responseFailed));
            throw new RejectedOperationException(responseFailed.toString());
        }
        String operationId;
        try {
            operationId = repository.addOperation(transfer.getCardFromNumber(), transfer.getCardToNumber(), transfer.getAmount());
        } catch (RuntimeException e) {
            LOGGER.info(LoggerMessages.EXCEPTION.getMessage(e.getMessage()));
            throw new InternalServerErrorExceptionId1(e.getMessage());
        }
        ResponseSuccess responseSuccess = new ResponseSuccess(operationId);
        LOGGER.info(LoggerMessages.SUCCESS_TRANSFER.getMessage(responseSuccess));
        return responseSuccess;
    }

    @Override
    public Object confirmTransfer(ConfirmTransfer confirmTransfer) {
        String operationId = confirmTransfer.getOperationId();
        try {
            if (repository.getOperation(operationId)
                    .confirm(confirmTransfer.getVerificationCode())) {
                ResponseSuccess responseSuccess = new ResponseSuccess(operationId);
                LOGGER.info(LoggerMessages.SUCCESS_CONFIRM_TRANSFER.getMessage(responseSuccess));
                return responseSuccess;
            }
        } catch (RuntimeException e) {
            LOGGER.info(LoggerMessages.EXCEPTION.getMessage(e.getMessage()));
            throw new InternalServerErrorExceptionId2(e.getMessage());
        }
        ResponseFailed responseFailed = new ResponseFailed(ServiceMessages.INVALID_CONFIRMATION_CODE.getMessage(), 2);
        LOGGER.info(LoggerMessages.FAILED_CONFIRM_TRANSFER.getMessage(responseFailed));
        throw new RejectedOperationException(responseFailed.toString());
    }
}
