package com.loy.upm.sys.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;

import com.loy.app.common.domain.entity.SequenceEntity;
import com.loy.e.core.repository.GenericRepository;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public interface SequenceRepository extends GenericRepository<SequenceEntity,String>{
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	public SequenceEntity getById(String id);
	
}