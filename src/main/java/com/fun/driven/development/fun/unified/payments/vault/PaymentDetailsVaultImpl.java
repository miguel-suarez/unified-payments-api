package com.fun.driven.development.fun.unified.payments.vault;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fun.driven.development.fun.unified.payments.api.domain.enumeration.PaymentMethodType;
import com.fun.driven.development.fun.unified.payments.api.service.UnifiedPaymentTokenService;
import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import com.fun.driven.development.fun.unified.payments.vault.service.StrongCryptography;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

/**
 * Follows PCI DSS TokenizationGuidelines v2.0, August 2011
 */
@Service
public class PaymentDetailsVaultImpl implements PaymentDetailsVault {

    private static final Logger log = LoggerFactory.getLogger(PaymentDetailsVaultImpl.class);

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private UnifiedPaymentTokenService tokenService;

    @Autowired
    private StrongCryptography cryptography;

    @Override
    public UnifiedPaymentTokenDTO tokenize(long merchantId, Card card) {
        String payload = cryptography.encrypt(card.toJson());
        Optional<UnifiedPaymentTokenDTO> tokenOptional = tokenService.findByPayloadAndMerchantId(payload, merchantId);
        if (tokenOptional.isPresent()) {
            log.info("Request tokenization of an already existing payload. Token: {}", tokenOptional.get().getToken());
            return tokenOptional.get();
        } else {
            Instant lastUsableUTCInstant = card.lastUsableUTCInstant();
            UnifiedPaymentTokenDTO tokenDTO = tokenGenerator.newToken()
                                                            .merchantId(merchantId)
                                                            .payload(payload)
                                                            .type(PaymentMethodType.CARD)
                                                            .validUntil(lastUsableUTCInstant);
            log.info("Request tokenization of a new Card. Token: {}, valid until: {}",
                    tokenDTO.getToken(),
                    lastUsableUTCInstant);
            tokenService.save(tokenDTO);
            return tokenDTO;
        }
    }

    public Optional<Card> retrieveCard(long merchantId, String token) {
        Optional<UnifiedPaymentTokenDTO> tokenOptional = tokenService.findOneByTokenAndMerchantId(
                                                                                        token,
                                                                                        merchantId);
        if (tokenOptional.isEmpty()) {
            log.info("Token not found {} for merchant {}", token, merchantId);
            return Optional.empty();
        }
        UnifiedPaymentTokenDTO tokenDTO = tokenOptional.get();

        if (PaymentMethodType.CARD.equals(tokenDTO.getType())) {
            String payloadJson = cryptography.decrypt(tokenDTO.getPayload());
            try {
                Card card = Card.fromJson(payloadJson);
                return Optional.of(card);
            } catch (JsonProcessingException e) {
                log.error("Can't parse json to object", e);
                return Optional.empty();
            }
        } else {
            log.error("Can't decrypt payment details of type: {}",
                      tokenDTO.getType().name());
            return Optional.empty();
        }
    }

}
