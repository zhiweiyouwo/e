package com.loy.app.common.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.loy.app.common.domain.entity.AttachmentEntity;
import com.loy.app.common.repository.AttachmentRepository;
import com.loy.e.common.util.Assert;
import com.loy.e.common.vo.SuccessResponse;
import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.conf.Settings;
import com.loy.e.core.util.FileNameUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

@RestController(value="attachmentService")
@RequestMapping(value = "attachment",method={RequestMethod.POST,RequestMethod.GET})
@Transactional

@Api(value="附件相关操作",description="附件相关操作")

public class AttachmentServiceImpl {
	protected final Log logger = LogFactory.getLog(AttachmentServiceImpl.class);
	@Autowired
	AttachmentRepository attachmentRepository;
	@Autowired
	Settings settings;
	
	public void save(MultipartFile multipartFile,String targetId){
		String fileName = multipartFile.getOriginalFilename();
		if(StringUtils.isEmpty(fileName)){
			return;
		}
		String fileNameSuffix = FileNameUtil.getFileSuffix(fileName);
		AttachmentEntity attachmentEntity = new AttachmentEntity();
		attachmentEntity.setTargetId(targetId);
		attachmentEntity.setFileShowName(fileName);
		attachmentRepository.save(attachmentEntity);
		String attachmentId = attachmentEntity.getId();
		fileName = attachmentId+fileNameSuffix;
		attachmentEntity.setFileName(fileName);
		try {
			File dir = new File(settings.getAttachmentBaseDirectory());
			if(!dir.exists()){
				dir.mkdirs();
			}
		    File file = new File(settings.getAttachmentBaseDirectory(),fileName);
			FileCopyUtils.copy(multipartFile.getBytes(), file);
		} catch (IOException e) {
			logger.error(e);
			Assert.throwException();
		}
	}
	
	@ControllerLogExeTime(description="删除附件")
	@RequestMapping(value="/del",method={RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST})
	
	@ApiOperation(value="删除附件",httpMethod="DELETE")
	@ApiImplicitParam(name="id",value="附件ID",paramType="query" ,required=true,dataType="string")
	
	public SuccessResponse  del(String id){
		attachmentRepository.delete(id);
		return SuccessResponse.newInstance();
	}
	
	@ControllerLogExeTime(description="下载附件",log=false)
	@RequestMapping(value="/download",method={RequestMethod.GET})
	
	@ApiOperation(value="下载附件",httpMethod="GET")
	@ApiImplicitParam(name="id",value="附件ID",paramType="query",dataType="string")
	
    public void  download(String id,HttpServletResponse response) throws IOException{
		AttachmentEntity attachmentEntity = attachmentRepository.get(id);
		String fileName = attachmentEntity.getFileName();
		response.setContentType("application/x-download");
		String fileDisplay = fileName;//下载文件时显示的文件保存名称  
		fileDisplay = URLEncoder.encode(fileDisplay,"UTF-8");  
		response.addHeader("Content-Disposition","attachment;filename=" + fileDisplay);  
		OutputStream out = response.getOutputStream();
		File file = new File(settings.getAttachmentBaseDirectory(),fileName);
		FileCopyUtils.copy(new FileInputStream(file), out);
		out.flush();
		out.close();
	}
}