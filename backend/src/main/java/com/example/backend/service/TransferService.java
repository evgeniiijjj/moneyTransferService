package com.example.backend.service;

import com.example.backend.dto.ConfirmTransfer;
import com.example.backend.dto.Transfer;
import org.springframework.validation.BindingResult;

public interface TransferService {
    Object transfer(Transfer transfer, BindingResult bindingResult);
    Object confirmTransfer(ConfirmTransfer confirmTransfer);
}
