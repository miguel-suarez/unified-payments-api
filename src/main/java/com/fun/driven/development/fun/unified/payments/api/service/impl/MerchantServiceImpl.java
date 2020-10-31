package com.fun.driven.development.fun.unified.payments.api.service.impl;

import com.fun.driven.development.fun.unified.payments.api.domain.Merchant;
import com.fun.driven.development.fun.unified.payments.api.domain.User;
import com.fun.driven.development.fun.unified.payments.api.repository.MerchantRepository;
import com.fun.driven.development.fun.unified.payments.api.service.MerchantService;
import com.fun.driven.development.fun.unified.payments.api.service.UserService;
import com.fun.driven.development.fun.unified.payments.api.service.dto.MerchantDTO;
import com.fun.driven.development.fun.unified.payments.api.service.dto.UserDTO;
import com.fun.driven.development.fun.unified.payments.api.service.mapper.MerchantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MerchantServiceImpl implements MerchantService {

    private final Logger log = LoggerFactory.getLogger(MerchantServiceImpl.class);

    private final MerchantRepository merchantRepository;

    private final MerchantMapper merchantMapper;

    private final UserService userService;

    public MerchantServiceImpl(MerchantRepository merchantRepository, MerchantMapper merchantMapper, UserService userService) {
        this.merchantRepository = merchantRepository;
        this.merchantMapper = merchantMapper;
        this.userService = userService;
    }

    @Override
    public MerchantDTO save(MerchantDTO merchantDTO) {
        log.debug("Request to save Merchant : {}", merchantDTO);
        Merchant merchant = merchantMapper.toEntity(merchantDTO);
        merchant = merchantRepository.save(merchant);
        return merchantMapper.toDto(merchant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerchantDTO> findAll() {
        log.debug("Request to get all Merchants");
        return merchantRepository.findAllWithEagerRelationships().stream()
            .map(merchantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<MerchantDTO> findAllWithEagerRelationships(Pageable pageable) {
        return merchantRepository.findAllWithEagerRelationships(pageable).map(merchantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MerchantDTO> findOne(Long id) {
        log.debug("Request to get Merchant : {}", id);
        return merchantRepository.findOneWithEagerRelationships(id)
                                 .map(merchantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MerchantDTO> findOneByReference(String reference) {
        log.debug("Request to get Merchant : {}", reference);
        return merchantRepository.findOneWithEagerRelationshipsByReference(reference)
                                 .map(merchantMapper::toDto);
    }

    @Override
    public boolean isNotAuthorizedUserOwnedByMerchant(String reference) {
        Optional<User> authorizedUser = userService.getUserWithAuthorities();
        Long authorizedUserId = authorizedUser.isPresent() ? authorizedUser.get().getId() : -1;
        Optional<MerchantDTO> merchantOptional = findOneByReference(reference);
        MerchantDTO merchantDTO = merchantOptional.orElseGet(MerchantDTO::new);
        Optional<UserDTO> userDto = merchantDTO.getUsers()
                                               .stream()
                                               .filter(u -> u.getId().equals(authorizedUserId))
                                               .findFirst();
        return userDto.isEmpty();
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Merchant : {}", id);
        merchantRepository.deleteById(id);
    }
}
