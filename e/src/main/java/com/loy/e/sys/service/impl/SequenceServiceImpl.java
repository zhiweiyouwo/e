package com.loy.e.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loy.e.sys.domain.entity.SequenceEntity;
import com.loy.e.sys.repository.SequenceRepository;
import com.loy.e.sys.service.SequenceService;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@Service(value="sequenceService")
@Transactional
public class SequenceServiceImpl implements SequenceService{

	@Autowired
	SequenceRepository sequenceRepository;
	@Override
	public String getEmployeeNo() {
		SequenceEntity sequenceEntity = sequenceRepository.get(SequenceEntity.EMPLOYEE_KEY);
		Integer v = sequenceEntity.getV()+1;
		sequenceEntity.setV(v);
		sequenceRepository.save(sequenceEntity);
		String employeeNo = String.format("%06d", v);
		return employeeNo;
	}
}