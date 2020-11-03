package com.fun.driven.development.fun.unified.payments.vault;

import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.PaymentMethodType;
import com.fun.driven.development.fun.unified.payments.api.service.UnifiedPaymentTokenService;
import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsVaultMock implements PaymentDetailsVault {

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private UnifiedPaymentTokenService tokenService;

    @Override
    public UnifiedPaymentTokenDTO tokenize(Long merchantId, Card card) {
        String token = "abcdef123456";
        String payload = "";
        UnifiedPaymentTokenDTO tokenDTO = new UnifiedPaymentTokenDTO(token).merchantId(merchantId)
                                                                           .payload(payload)
                                                                           .type(PaymentMethodType.CARD);
        tokenService.save(tokenDTO);
        return tokenDTO;
    }

    public Card retrieveCard(String token) {
        return new Card().setNumber("4111111111111111")
                         .setExpirationMonth(5)
                         .setExpirationYear(2030);
    }

}
