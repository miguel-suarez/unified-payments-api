package com.fun.driven.development.fun.unified.payments.api.service.impl;

import com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodToken;
import com.fun.driven.development.fun.unified.payments.api.repository.PaymentMethodTokenRepository;
import com.fun.driven.development.fun.unified.payments.api.service.PaymentMethodTokenService;
import com.fun.driven.development.fun.unified.payments.api.service.dto.PaymentMethodTokenDTO;
import com.fun.driven.development.fun.unified.payments.api.service.mapper.PaymentMethodTokenMapper;
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
public class PaymentMethodTokenServiceImpl implements PaymentMethodTokenService {

    private final Logger log = LoggerFactory.getLogger(PaymentMethodTokenServiceImpl.class);

    private final PaymentMethodTokenRepository paymentMethodTokenRepository;

    private final PaymentMethodTokenMapper paymentMethodTokenMapper;

    public PaymentMethodTokenServiceImpl(PaymentMethodTokenRepository paymentMethodTokenRepository, PaymentMethodTokenMapper paymentMethodTokenMapper) {
        this.paymentMethodTokenRepository = paymentMethodTokenRepository;
        this.paymentMethodTokenMapper = paymentMethodTokenMapper;
    }

    @Override
    public PaymentMethodTokenDTO save(PaymentMethodTokenDTO paymentMethodTokenDTO) {
        log.debug("Request to save PaymentMethodToken : {}", paymentMethodTokenDTO);
        PaymentMethodToken paymentMethodToken = paymentMethodTokenMapper.toEntity(paymentMethodTokenDTO);
        paymentMethodToken = paymentMethodTokenRepository.save(paymentMethodToken);
        return paymentMethodTokenMapper.toDto(paymentMethodToken);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodTokenDTO> findAll() {
        log.debug("Request to get all PaymentMethodTokens");
        return paymentMethodTokenRepository.findAll().stream()
            .map(paymentMethodTokenMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentMethodTokenDTO> findOne(Long id) {
        log.debug("Request to get PaymentMethodToken : {}", id);
        return paymentMethodTokenRepository.findById(id)
            .map(paymentMethodTokenMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentMethodToken : {}", id);
        paymentMethodTokenRepository.deleteById(id);
    }
}
