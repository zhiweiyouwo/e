package com.loy.app.common.repository;

import java.util.List;

import com.loy.app.common.domain.entity.AttachmentEntity;
import com.loy.e.core.repository.GenericRepository;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public interface AttachmentRepository extends GenericRepository<AttachmentEntity, String> {

    public List<AttachmentEntity> findByTargetId(String targetId);

}