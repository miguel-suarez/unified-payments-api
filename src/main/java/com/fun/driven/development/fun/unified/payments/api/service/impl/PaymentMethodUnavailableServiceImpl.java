package com.fun.driven.development.fun.unified.payments.api.service.impl;

import com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodUnavailable;
import com.fun.driven.development.fun.unified.payments.api.repository.PaymentMethodUnavailableRepository;
import com.fun.driven.development.fun.unified.payments.api.service.PaymentMethodUnavailableService;
import com.fun.driven.development.fun.unified.payments.api.service.dto.PaymentMethodUnavailableDTO;
import com.fun.driven.development.fun.unified.payments.api.service.mapper.PaymentMethodUnavailableMapper;
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
public class PaymentMethodUnavailableServiceImpl implements PaymentMethodUnavailableService {

    private final Logger log = LoggerFactory.getLogger(PaymentMethodUnavailableServiceImpl.class);

    private final PaymentMethodUnavailableRepository paymentMethodUnavailableRepository;

    private final PaymentMethodUnavailableMapper paymentMethodUnavailableMapper;

    public PaymentMethodUnavailableServiceImpl(PaymentMethodUnavailableRepository paymentMethodUnavailableRepository, PaymentMethodUnavailableMapper paymentMethodUnavailableMapper) {
        this.paymentMethodUnavailableRepository = paymentMethodUnavailableRepository;
        this.paymentMethodUnavailableMapper = paymentMethodUnavailableMapper;
    }

    @Override
    public PaymentMethodUnavailableDTO save(PaymentMethodUnavailableDTO paymentMethodUnavailableDTO) {
        log.debug("Request to save PaymentMethodUnavailable : {}", paymentMethodUnavailableDTO);
        PaymentMethodUnavailable paymentMethodUnavailable = paymentMethodUnavailableMapper.toEntity(paymentMethodUnavailableDTO);
        paymentMethodUnavailable = paymentMethodUnavailableRepository.save(paymentMethodUnavailable);
        return paymentMethodUnavailableMapper.toDto(paymentMethodUnavailable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodUnavailableDTO> findAll() {
        log.debug("Request to get all PaymentMethodUnavailables");
        return paymentMethodUnavailableRepository.findAll().stream()
            .map(paymentMethodUnavailableMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentMethodUnavailableDTO> findOne(Long id) {
        log.debug("Request to get PaymentMethodUnavailable : {}", id);
        return paymentMethodUnavailableRepository.findById(id)
            .map(paymentMethodUnavailableMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentMethodUnavailable : {}", id);
        paymentMethodUnavailableRepository.deleteById(id);
    }
}
