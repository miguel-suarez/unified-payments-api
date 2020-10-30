package com.fun.driven.development.fun.unified.payments.api.service.impl;

import com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodCredential;
import com.fun.driven.development.fun.unified.payments.api.repository.PaymentMethodCredentialRepository;
import com.fun.driven.development.fun.unified.payments.api.service.PaymentMethodCredentialService;
import com.fun.driven.development.fun.unified.payments.api.service.dto.PaymentMethodCredentialDTO;
import com.fun.driven.development.fun.unified.payments.api.service.mapper.PaymentMethodCredentialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentMethodCredentialServiceImpl implements PaymentMethodCredentialService {

    private final Logger log = LoggerFactory.getLogger(PaymentMethodCredentialServiceImpl.class);

    private final PaymentMethodCredentialRepository paymentMethodCredentialRepository;

    private final PaymentMethodCredentialMapper paymentMethodCredentialMapper;

    public PaymentMethodCredentialServiceImpl(PaymentMethodCredentialRepository paymentMethodCredentialRepository, PaymentMethodCredentialMapper paymentMethodCredentialMapper) {
        this.paymentMethodCredentialRepository = paymentMethodCredentialRepository;
        this.paymentMethodCredentialMapper = paymentMethodCredentialMapper;
    }

    @Override
    public PaymentMethodCredentialDTO save(PaymentMethodCredentialDTO paymentMethodCredentialDTO) {
        log.debug("Request to save PaymentMethodCredential : {}", paymentMethodCredentialDTO);
        PaymentMethodCredential paymentMethodCredential = paymentMethodCredentialMapper.toEntity(paymentMethodCredentialDTO);
        paymentMethodCredential = paymentMethodCredentialRepository.save(paymentMethodCredential);
        return paymentMethodCredentialMapper.toDto(paymentMethodCredential);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodCredentialDTO> findAll() {
        log.debug("Request to get all PaymentMethodCredentials");
        return paymentMethodCredentialRepository.findAll().stream()
            .map(paymentMethodCredentialMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentMethodCredentialDTO> findOne(Long id) {
        log.debug("Request to get PaymentMethodCredential : {}", id);
        return paymentMethodCredentialRepository.findById(id)
                                                .map(paymentMethodCredentialMapper::toDto);
    }

    @Override
    public Optional<PaymentMethodCredentialDTO> findOneByPaymentMethodAndMerchant(Long paymentMethodId, Long merchantId) {
        log.debug("Request to get PaymentMethodCredential : {} {}", paymentMethodId, merchantId);
        return paymentMethodCredentialRepository.findOneByPaymentMethodIdAndMerchantId(paymentMethodId, merchantId)
                                                .map(paymentMethodCredentialMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentMethodCredential : {}", id);
        paymentMethodCredentialRepository.deleteById(id);
    }
}
