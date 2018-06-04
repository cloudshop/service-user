package com.eyun.user.service.impl;

import com.eyun.user.service.AuthenticationService;
import com.eyun.user.domain.Authentication;
import com.eyun.user.repository.AuthenticationRepository;
import com.eyun.user.service.dto.AuthenticationDTO;
import com.eyun.user.service.dto.SubTimeAuth;
import com.eyun.user.service.mapper.AuthenticationMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Authentication.
 */
@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final AuthenticationRepository authenticationRepository;

    private final AuthenticationMapper authenticationMapper;

    public AuthenticationServiceImpl(AuthenticationRepository authenticationRepository, AuthenticationMapper authenticationMapper) {
        this.authenticationRepository = authenticationRepository;
        this.authenticationMapper = authenticationMapper;
    }

    /**
     * Save a authentication.
     *
     * @param authenticationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AuthenticationDTO save(AuthenticationDTO authenticationDTO) {
        log.debug("Request to save Authentication : {}", authenticationDTO);
        Authentication authentication = authenticationMapper.toEntity(authenticationDTO);
        authentication = authenticationRepository.save(authentication);
        return authenticationMapper.toDto(authentication);
    }

    /**
     * Get all the authentications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AuthenticationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Authentications");
        return authenticationRepository.findAll(pageable)
            .map(authenticationMapper::toDto);
    }

    /**
     * Get one authentication by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AuthenticationDTO findOne(Long id) {
        log.debug("Request to get Authentication : {}", id);
        Authentication authentication = authenticationRepository.findOne(id);
        return authenticationMapper.toDto(authentication);
    }

    /**
     * Delete the authentication by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Authentication : {}", id);
        authenticationRepository.delete(id);
    }

	@Override
	public List<Authentication> findSubAutherntication(SubTimeAuth subTimeAuth) {
		List<Authentication> findSubAutherntication = authenticationRepository.findSubAutherntication(subTimeAuth.getFrist(),subTimeAuth.getLast(),subTimeAuth.getPage(),subTimeAuth.getSize());
		return findSubAutherntication;
	}
}
