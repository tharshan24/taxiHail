package com.taxi.taxihailcore.controller;

import com.taxi.taxihailcore.service.PaymentModeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment_modes")
public class PaymentModeController {

    private final PaymentModeService paymentModeService;

    public PaymentModeController(PaymentModeService paymentModeService){
        this.paymentModeService = paymentModeService;
    }
}
