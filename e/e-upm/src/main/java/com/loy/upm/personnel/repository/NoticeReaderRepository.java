package com.loy.upm.personnel.repository;

import java.util.List;

import com.loy.e.core.repository.GenericRepository;
import com.loy.upm.personnel.domain.entity.NoticeReaderEntity;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public interface NoticeReaderRepository extends GenericRepository<NoticeReaderEntity, String> {

    List<NoticeReaderEntity> findByNoticeId(String noticeId);

    NoticeReaderEntity findByNoticeIdAndReaderId(String noticeId, String readerId);
}