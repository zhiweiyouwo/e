package com.loy.upm.personnel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.loy.e.core.repository.GenericRepository;
import com.loy.upm.personnel.domain.entity.NoticeEntity;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public interface NoticeRepository extends GenericRepository<NoticeEntity,String>{

	@Query("select count(x.notice) from  NoticeReaderEntity x where x.readerId = ?1  and x.seen = 0")
	public Long findNotReadNoticeCount(String readerId);
	
	@Query("select x.notice from  NoticeReaderEntity x where x.readerId = ?1  and x.seen = 0")
	public List<NoticeEntity> findNotReadNotice(String readerId);
}