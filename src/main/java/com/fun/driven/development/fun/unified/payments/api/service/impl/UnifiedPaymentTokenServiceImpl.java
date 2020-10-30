package com.fun.driven.development.fun.unified.payments.api.service.impl;

import com.fun.driven.development.fun.unified.payments.api.domain.UnifiedPaymentToken;
import com.fun.driven.development.fun.unified.payments.api.repository.UnifiedPaymentTokenRepository;
import com.fun.driven.development.fun.unified.payments.api.service.UnifiedPaymentTokenService;
import com.fun.driven.development.fun.unified.payments.api.service.dto.UnifiedPaymentTokenDTO;
import com.fun.driven.development.fun.unified.payments.api.service.mapper.UnifiedPaymentTokenMapper;
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
public class UnifiedPaymentTokenServiceImpl implements UnifiedPaymentTokenService {

    private final Logger log = LoggerFactory.getLogger(UnifiedPaymentTokenServiceImpl.class);

    private final UnifiedPaymentTokenRepository unifiedPaymentTokenRepository;

    private final UnifiedPaymentTokenMapper unifiedPaymentTokenMapper;

    public UnifiedPaymentTokenServiceImpl(UnifiedPaymentTokenRepository unifiedPaymentTokenRepository,
                                          UnifiedPaymentTokenMapper unifiedPaymentTokenMapper) {
        this.unifiedPaymentTokenRepository = unifiedPaymentTokenRepository;
        this.unifiedPaymentTokenMapper = unifiedPaymentTokenMapper;
    }

    @Override
    public UnifiedPaymentTokenDTO save(UnifiedPaymentTokenDTO unifiedPaymentTokenDTO) {
        log.debug("Request to save UnifiedPaymentToken : {}", unifiedPaymentTokenDTO);
        UnifiedPaymentToken unifiedPaymentToken = unifiedPaymentTokenMapper.toEntity(unifiedPaymentTokenDTO);
        unifiedPaymentToken = unifiedPaymentTokenRepository.save(unifiedPaymentToken);
        return unifiedPaymentTokenMapper.toDto(unifiedPaymentToken);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UnifiedPaymentTokenDTO> findAll() {
        log.debug("Request to get all UnifiedPaymentTokens");
        return unifiedPaymentTokenRepository.findAll().stream()
            .map(unifiedPaymentTokenMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UnifiedPaymentTokenDTO> findOne(Long id) {
        log.debug("Request to get UnifiedPaymentToken : {}", id);
        return unifiedPaymentTokenRepository.findById(id)
                                            .map(unifiedPaymentTokenMapper::toDto);
    }

    @Override
    public Optional<UnifiedPaymentTokenDTO> findOneByTokenAndMerchantId(String token, Long merchantId) {
        log.debug("Request to get UnifiedPaymentToken : {} {}", token, merchantId);
        return unifiedPaymentTokenRepository.findByTokenAndMerchantId(token, merchantId)
                                            .map(unifiedPaymentTokenMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UnifiedPaymentToken : {}", id);
        unifiedPaymentTokenRepository.deleteById(id);
    }
}
