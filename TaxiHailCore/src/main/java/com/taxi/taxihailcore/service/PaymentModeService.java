package com.taxi.taxihailcore.service;

import com.taxi.taxihailcore.repository.PaymentModeRepository;
import com.taxi.taxihailcore.repository.PaymentModeRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentModeService {
    private final PaymentModeRepository paymentModeRepository;

    public PaymentModeService(PaymentModeRepository paymentModeRepository){
        this.paymentModeRepository = paymentModeRepository;
    }
}
