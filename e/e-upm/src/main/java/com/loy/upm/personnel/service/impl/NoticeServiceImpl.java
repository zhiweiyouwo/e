package com.loy.upm.personnel.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.loy.app.common.domain.entity.AttachmentEntity;
import com.loy.app.common.repository.AttachmentRepository;
import com.loy.app.common.service.impl.AttachmentServiceImpl;
import com.loy.e.common.vo.Response;
import com.loy.e.common.vo.SuccessResponse;
import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.annotation.Converter;
import com.loy.e.core.converter.DefaultPageConverter;
import com.loy.e.core.query.MapQueryParam;
import com.loy.e.core.util.TableToExcelUtil;
import com.loy.e.security.service.UserSessionService;
import com.loy.upm.personnel.domain.NoticeQueryParam;
import com.loy.upm.personnel.domain.entity.NoticeEntity;
import com.loy.upm.personnel.domain.entity.NoticeReaderEntity;
import com.loy.upm.personnel.domain.entity.NoticeStatus;
import com.loy.upm.personnel.repository.NoticeReaderRepository;
import com.loy.upm.personnel.repository.NoticeRepository;
import com.loy.upm.sys.domain.entity.UserEntity;
import com.loy.upm.sys.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping(value = "**/notice",method={RequestMethod.POST,RequestMethod.GET})
@Transactional
@Api(value="通知管理",description="通知管理")
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
	@Autowired
	UserSessionService userSessionService;
	@RequestMapping(value="/page")
	@ControllerLogExeTime(description="查询通知",log = false)
	
	@ApiOperation(value="查询通知",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="subject" ,value="标题" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="noticeStatus" ,value="状态" ,paramType="form",dataType="string"),

		@ApiImplicitParam(name="pageNumber" ,value="页号" ,paramType="form",dataType="int"),
		@ApiImplicitParam(name="pageSize" ,value="页的大小" ,paramType="form",dataType="int")
	})
	public Page<NoticeEntity>  queryPage(
			@ApiIgnore NoticeQueryParam noticeQueryParam,
			@ApiIgnore Pageable pageable){
		String userId = userSessionService.getSessionUser().getId();
		noticeQueryParam.setCreatorId(userId);
		Page<NoticeEntity> page = noticeRepository.findPage("personnel.notice.findPageNotice",
				new MapQueryParam(noticeQueryParam), pageable);
		return page;
	}
	
	
	@RequestMapping(value="/receive")
	@ControllerLogExeTime(description="我的通知",log = false)
	@Converter(value=DefaultPageConverter.class)
	
	@ApiOperation(value="查询我通知",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="subject" ,value="标题" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="noticeStatus" ,value="状态" ,paramType="form",dataType="string"),

		@ApiImplicitParam(name="pageNumber" ,value="页号" ,paramType="form",dataType="int"),
		@ApiImplicitParam(name="pageSize" ,value="页的大小" ,paramType="form",dataType="int")
	})
	
	public Page<NoticeReaderEntity>  queryReceivePage(
			@ApiIgnore NoticeQueryParam noticeQueryParam,
			@ApiIgnore Pageable pageable){
		String userId = userSessionService.getSessionUser().getId();
		if(noticeQueryParam == null){
			noticeQueryParam = new NoticeQueryParam();
		}
		noticeQueryParam.setReaderId(userId);
		Page<NoticeReaderEntity> page = noticeReaderRepository.findPage("personnel.notice.queryReceivePage",
				new MapQueryParam(noticeQueryParam), pageable);
		return page;
	}
	
	@RequestMapping(value="/noread")
	@ControllerLogExeTime(description="我的没有读的通知",log = false)
	
	@ApiOperation(value="我的没有读的通知",httpMethod="GET")
	
	public Page<NoticeEntity>  queryNotReadPage(){
		String userId = userSessionService.getSessionUser().getId();
		List<NoticeEntity> list = noticeRepository.findNotReadNotice(userId);
		long total = list == null?0:list.size();
		Page<NoticeEntity> page = new PageImpl<NoticeEntity>(list,null,total);
		return page;
	}
	
	@RequestMapping(value="/onlyRead")
	@ControllerLogExeTime(description="读通知",log = false)
	
	@ApiOperation(value="阅读通知",notes = "阅读通知 改状态", httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id" ,value="ID" ,paramType="form",required = true, dataType="string"),
	})
	
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
	
	@ApiOperation(value="阅读通知",notes = "阅读通知并返回通知信息",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id" ,value="ID" ,paramType="form",required = true, dataType="string"),
	})
	public NoticeEntity  read(String id){
		NoticeEntity notice = this.get(id);
		NoticeReaderEntity noticeReader = noticeReaderRepository.findByNoticeIdAndReaderId(id, userSessionService.getSessionUser().getId());
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
	
	@ApiOperation(value="获取通知信息",notes = "获取通知信息",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id" ,value="ID" ,paramType="form",required = true, dataType="string"),
	})
	
	public NoticeEntity  get(String id){
		NoticeEntity noticeEntity = noticeRepository.get(id);
		List<AttachmentEntity> attachments = attachmentRepository.findByTargetId(id);
		noticeEntity.setAttachments(attachments);
		return noticeEntity;
	}
	
	@ControllerLogExeTime(description="删除通知",log = false)
	@RequestMapping(value="/del",method={RequestMethod.POST,RequestMethod.DELETE})
	
	@ApiOperation(value="删除通知",notes="删除通知  多个通知ID用,号分隔",httpMethod="DELETE")
	@ApiImplicitParam(name="id" ,value="通知IDS" ,paramType="form",required=true,dataType="string")
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
	
	@ApiOperation(value="保存通知",notes="保存通知",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="subject" ,value="名称" ,paramType="form", required = true,dataType="string"),
		@ApiImplicitParam(name="content" ,value="描述" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="uploadAttachments" ,value="附件" ,paramType="form", dataType="file")
	})
	public NoticeEntity  save(@ApiIgnore NoticeEntity noticeEntity){
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
	
	@RequestMapping(value="/update",method={RequestMethod.POST,RequestMethod.PUT})
	@ControllerLogExeTime(description="修改通知",log = false)
	
	@ApiOperation(value="修改通知",notes="修改通知",httpMethod="PUT")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id" ,value="ID" ,paramType="form", required = true,dataType="string"),
		@ApiImplicitParam(name="subject" ,value="名称" ,paramType="form", required = true,dataType="string"),
		@ApiImplicitParam(name="content" ,value="描述" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="uploadAttachments" ,value="附件" ,paramType="form", dataType="file")
	})
	
	public SuccessResponse  update(@ApiIgnore NoticeEntity noticeEntity){
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
	
	@ApiOperation(value="发送通知",notes="发送通知",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="subject" ,value="名称" ,paramType="form", required = true,dataType="string"),
		@ApiImplicitParam(name="content" ,value="描述" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="uploadAttachments" ,value="附件" ,paramType="form", dataType="file")
	})
	
	public SuccessResponse  send(@ApiIgnore NoticeEntity noticeEntity){
		NoticeEntity notice = null;
		String id = noticeEntity.getId();
		boolean needSend = false;
		if(StringUtils.isEmpty(id)){
			this.save(noticeEntity);
			noticeEntity.setNoticeStatus(NoticeStatus.SENT);
			noticeEntity.setSendTime(new Date());
			noticeRepository.save(noticeEntity);
			noticeEntity.setSender(userSessionService.getSessionUser().getName());
			notice = noticeEntity;
			needSend = true;
		}else{
			notice = noticeRepository.get(id);
			if(notice != null){
				if(notice.getNoticeStatus() == NoticeStatus.DRAFT){
					this.update(noticeEntity);
					notice.setNoticeStatus(NoticeStatus.SENT);
					notice.setSendTime(new Date());
					notice.setSender(userSessionService.getSessionUser().getId());
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
	
	@RequestMapping(value="/excel",method={RequestMethod.POST})
	@ControllerLogExeTime(description="导出通知",log=false)
	@ApiIgnore
    public void  excel(String html ,HttpServletResponse response) throws IOException{
		response.setContentType("application/msexcel;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=notices.xls");
		OutputStream out = response.getOutputStream();
		TableToExcelUtil.createExcelFormTable("notice", html, 1, out);
		out.flush();
		out.close();
		
	}
}