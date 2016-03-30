package com.loy.e.sys.repository;

import java.util.List;

import com.loy.e.core.repository.GenericRepository;
import com.loy.e.sys.domain.entity.DictionaryEntity;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public interface DictionaryRepository extends GenericRepository<DictionaryEntity,String>{

	List<DictionaryEntity> findByGroup(String group);

}
