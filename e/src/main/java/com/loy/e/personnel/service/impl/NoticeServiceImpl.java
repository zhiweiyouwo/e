package com.loy.e.personnel.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.annotation.Converter;
import com.loy.e.core.converter.DefaultPageConverter;
import com.loy.e.core.data.Response;
import com.loy.e.core.data.SuccessResponse;
import com.loy.e.core.query.MapQueryParam;
import com.loy.e.core.util.UserUtils;
import com.loy.e.core.web.SimpleUser;
import com.loy.e.personnel.domain.NoticeQueryParam;
import com.loy.e.personnel.domain.entity.NoticeEntity;
import com.loy.e.personnel.domain.entity.NoticeReaderEntity;
import com.loy.e.personnel.domain.entity.NoticeStatus;
import com.loy.e.personnel.repository.NoticeReaderRepository;
import com.loy.e.personnel.repository.NoticeRepository;
import com.loy.e.sys.domain.entity.AttachmentEntity;
import com.loy.e.sys.domain.entity.UserEntity;
import com.loy.e.sys.repository.AttachmentRepository;
import com.loy.e.sys.repository.UserRepository;
import com.loy.e.sys.service.impl.AttachmentServiceImpl;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping(value = "notice",method={RequestMethod.POST,RequestMethod.GET})
@Transactional
public class NoticeServiceImpl {

	@Autowired
	NoticeRepository noticeRepository;
	@Autowired
	AttachmentRepository attachmentRepository;
	@Autowired
	NoticeReaderRepository noticeReaderRepository;
	@Autowired
	AttachmentServiceImpl attachmentService;
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="/page")
	@ControllerLogExeTime(description="查询通知",log = false)
	public Page<NoticeEntity>  queryPage(NoticeQueryParam noticeQueryParam,Pageable pageable){
		String userId = UserUtils.getUserId();
		noticeQueryParam.setCreatorId(userId);
		Page<NoticeEntity> page = noticeRepository.findPage("personnel.notice.findPageNotice", new MapQueryParam(noticeQueryParam), pageable);
		return page;
	}
	
	
	@RequestMapping(value="/receive")
	@ControllerLogExeTime(description="我的通知",log = false)
	@Converter(value=DefaultPageConverter.class)
	public Page<NoticeReaderEntity>  queryReceivePage(NoticeQueryParam noticeQueryParam,Pageable pageable){
		String userId = UserUtils.getUserId();
		if(noticeQueryParam == null){
			noticeQueryParam = new NoticeQueryParam();
		}
		noticeQueryParam.setReaderId(userId);
		Page<NoticeReaderEntity> page = noticeReaderRepository.findPage("personnel.notice.queryReceivePage", new MapQueryParam(noticeQueryParam), pageable);
		return page;
	}
	
	@RequestMapping(value="/noread")
	@ControllerLogExeTime(description="我的没有读的通知",log = false)
	public Page<NoticeEntity>  queryNotReadPage(){
		String userId = UserUtils.getUserId();
		List<NoticeEntity> list = noticeRepository.findNotReadNotice(userId);
		long total = list == null?0:list.size();
		Page<NoticeEntity> page = new PageImpl<NoticeEntity>(list,null,total);
		return page;
	}
	
	@RequestMapping(value="/onlyRead")
	@ControllerLogExeTime(description="读通知",log = false)
	public Response  onlyRead(String id){
		NoticeReaderEntity noticeReader = noticeReaderRepository.get(id);
		if(noticeReader != null){
			if(!noticeReader.getSeen()){
				noticeReader.setReaderTime(new Date());
				noticeReader.setSeen(Boolean.TRUE);
				noticeReaderRepository.save(noticeReader);
			}
		}
		return SuccessResponse.newInstance();
	}
	
	@RequestMapping(value="/read")
	@ControllerLogExeTime(description="读取通知",log = false)
	public NoticeEntity  read(String id){
		NoticeEntity notice = this.get(id);
		NoticeReaderEntity noticeReader = noticeReaderRepository.findByNoticeIdAndReaderId(id, UserUtils.getUserId());
		if(noticeReader != null){
			if(!noticeReader.getSeen()){
				noticeReader.setReaderTime(new Date());
				noticeReader.setSeen(Boolean.TRUE);
				noticeReaderRepository.save(noticeReader);
			}
		}
		return notice;
	}
	@ControllerLogExeTime(description="获取通知")
	@RequestMapping(value="/get")
	public NoticeEntity  get(String id){
		NoticeEntity noticeEntity = noticeRepository.get(id);
		List<AttachmentEntity> attachments = attachmentRepository.findByTargetId(id);
		noticeEntity.setAttachments(attachments);
		return noticeEntity;
	}
	@ControllerLogExeTime(description="删除通知",log = false)
	@RequestMapping(value="/del")
	public SuccessResponse  del(String id){
		if(StringUtils.isNotEmpty(id)){
			String[] idsArr = id.split(",");
			if(idsArr != null){
				for(String idd : idsArr){
					NoticeEntity noticeEntity = noticeRepository.get(idd);
					if(noticeEntity != null){
						if(noticeEntity.getNoticeStatus()==NoticeStatus.SENT){
							continue;
						}
						List<AttachmentEntity> attachments = attachmentRepository.findByTargetId(id);
						if(attachments != null){
							for(AttachmentEntity attachment : attachments){
								attachmentRepository.delete(attachment);
							}
						}
						List<NoticeReaderEntity> noticeReaders = noticeReaderRepository.findByNoticeId(idd);
						if(noticeReaders != null){
							for(NoticeReaderEntity noticeReader : noticeReaders){
								noticeReaderRepository.delete(noticeReader);
							}
						}
						noticeRepository.delete(noticeEntity);
					}
				}
			}
		}
		return SuccessResponse.newInstance();
	}
	
	@RequestMapping(value="/save")
	@ControllerLogExeTime(description="保存通知",log = false)
	public NoticeEntity  save(NoticeEntity noticeEntity){
		noticeEntity.setNoticeStatus(NoticeStatus.DRAFT);
		List<MultipartFile> uploadAttachments = noticeEntity.getUploadAttachments();
		noticeEntity.setUploadAttachments(null);
		noticeRepository.save(noticeEntity);
		if(uploadAttachments != null){
			for(MultipartFile f : uploadAttachments){
				attachmentService.save(f, noticeEntity.getId());
			}
		}
		return noticeEntity;
	}
	
	@RequestMapping(value="/update")
	@ControllerLogExeTime(description="修改通知",log = false)
	public SuccessResponse  update(NoticeEntity noticeEntity){
		String id = noticeEntity.getId();
		NoticeEntity notice = noticeRepository.get(id);
		notice.setSubject(noticeEntity.getSubject());
		notice.setContent(noticeEntity.getContent());
		notice.setModifierId(noticeEntity.getModifierId());
		List<MultipartFile> uploadAttachments = noticeEntity.getUploadAttachments();
		noticeRepository.save(notice);
		if(uploadAttachments != null){
			for(MultipartFile f : uploadAttachments){
				attachmentService.save(f, noticeEntity.getId());
			}
		}
		return SuccessResponse.newInstance();
	}
	
	@RequestMapping(value="/send")
	@ControllerLogExeTime(description="发送通知",log = false)
	public SuccessResponse  send(NoticeEntity noticeEntity){
		NoticeEntity notice = null;
		String id = noticeEntity.getId();
		boolean needSend = false;
		if(StringUtils.isEmpty(id)){
			this.save(noticeEntity);
			noticeEntity.setNoticeStatus(NoticeStatus.SENT);
			noticeEntity.setSendTime(new Date());
			noticeRepository.save(noticeEntity);
			SimpleUser simpleUser = UserUtils.getSimipleUser();
			noticeEntity.setSender(simpleUser.getName());
			notice = noticeEntity;
			needSend = true;
		}else{
			notice = noticeRepository.get(id);
			if(notice != null){
				if(notice.getNoticeStatus() == NoticeStatus.DRAFT){
					this.update(noticeEntity);
					notice.setNoticeStatus(NoticeStatus.SENT);
					notice.setSendTime(new Date());
					SimpleUser simpleUser = UserUtils.getSimipleUser();
					notice.setSender(simpleUser.getName());
					noticeRepository.save(notice);
					needSend = true;
				}
			}
		}
		if(needSend){
			List<UserEntity> users = userRepository.findAll();
			if(users != null){
				for(UserEntity u : users){
					NoticeReaderEntity noticeReaderEntity = new NoticeReaderEntity();
					noticeReaderEntity.setNotice(notice);
					noticeReaderEntity.setReaderId(u.getId());
					noticeReaderEntity.setReaderName(u.getName());
					noticeReaderRepository.save(noticeReaderEntity);
				}
			}
		}
		return SuccessResponse.newInstance();
	}
}