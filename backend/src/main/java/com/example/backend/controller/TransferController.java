package com.example.backend.controller;

import com.example.backend.dto.ConfirmTransfer;
import com.example.backend.dto.Transfer;
import com.example.backend.service.TransferService;
import com.example.backend.util.LoggerMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.backend.BackendApplication.LOGGER;

@RestController
public class TransferController {

    private final TransferService service;

    @Autowired
    public TransferController(TransferService service) {
        this.service = service;
        LOGGER.info(LoggerMessages.START.getMessage());
    }

    @PostMapping("/transfer")
    public Object transfer(@RequestBody @Valid Transfer transfer, BindingResult bindingResult) {
        return service.transfer(transfer, bindingResult);
    }

    @PostMapping("/confirmOperation")
    public Object confirmTransfer(@RequestBody ConfirmTransfer confirmTransfer) {
        LOGGER.info(LoggerMessages.CONFIRM_TRANSFER.getMessage(confirmTransfer));
        return service.confirmTransfer(confirmTransfer);
    }
}
