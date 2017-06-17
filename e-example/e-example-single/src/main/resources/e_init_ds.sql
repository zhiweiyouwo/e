
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id) VALUES ('01',NULL,NULL,NULL,NULL,1,'系统管理',NULL,NULL,'MENU','','menu-icon fa fa-desktop','menu.upm.sys_manage',10,NULL,'upm');
 
---INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('0101',NULL,NULL,NULL,NULL,1,'用户管理','01',NULL,'MENU','upm-ui/sys/user_index.html','menu-icon fa fa-user-circle-o','menu.upm.user_manage',10,NULL,'upm');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('0101',NULL,NULL,NULL,NULL,1,'用户管理','01',NULL,'MENU','upm-ui/sys/user_data_permission_index.html','menu-icon fa fa fa-user-circle-o','menu.upm.user_manage',10,NULL,'upm');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('010101',NULL,NULL,NULL,NULL,1,'新增','0101',NULL,'BUTTON','user/save',NULL,'menu.add',NULL,'user:save','upm');
 
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('010102',NULL,NULL,NULL,NULL,1,'编辑','0101',NULL,'BUTTON','user/update',NULL,'menu.edit',NULL,'user:update','upm');
 
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('010103',NULL,NULL,NULL,NULL,1,'删除','0101',NULL,'BUTTON','user/del',NULL,'menu.delete',NULL,'user:del','upm');


 
--INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('0102',NULL,NULL,NULL,NULL,1,'角色管理','01',NULL,'MENU','upm-ui/sys/role_index.html','menu-icon fa fa-caret-right','menu.upm.role_manage',20,NULL,'upm');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('0102',NULL,NULL,NULL,NULL,1,'角色管理','01',NULL,'MENU','upm-ui/sys/role_data_permission_index.html','menu-icon fa fa-caret-right','menu.upm.role_manage',20,NULL,'upm'); 
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('010201',NULL,NULL,NULL,NULL,1,'新增','0102',NULL,'BUTTON','role/save',NULL,'menu.add',NULL,'role:save','upm');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('010202',NULL,NULL,NULL,NULL,1,'编辑','0102',NULL,'BUTTON','role/update',NULL,'menu.edit',NULL,'role:update','upm');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('010203',NULL,NULL,NULL,NULL,1,'授权','0102',NULL,'BUTTON','role/authority',NULL,'menu.upm.role_authority',NULL,'role:authority','upm');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('010204',NULL,NULL,NULL,NULL,1,'删除','0102',NULL,'BUTTON','role/del',NULL,'menu.delete',NULL,'role:del','upm');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('010205',NULL,NULL,NULL,NULL,1,'数据授权','0102',NULL,'BUTTON','strategy/role/data/authority',NULL,'menu.upm.strategy_manage',NULL,'role:data:authority','upm');

INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('0103',NULL,NULL,NULL,NULL,1,'菜单资源','01',NULL,'MENU','upm-ui/sys/resource_index.html','menu-icon fa fa-caret-right','menu.upm.resource_manage',30,NULL,'upm');

INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('010301',NULL,NULL,NULL,NULL,1,'新增','0103',NULL,'BUTTON','resource/save',NULL,'menu.add',NULL,'resource:save','upm');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('010302',NULL,NULL,NULL,NULL,1,'编辑','0103',NULL,'BUTTON','resource/update',NULL,'menu.edit',NULL,'resource:update','upm'); 
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('010303',NULL,NULL,NULL,NULL,1,'删除','0103',NULL,'BUTTON','resource/del',NULL,'menu.delete',NULL,'resource:del','upm');


INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id) VALUES ('02',NULL,NULL,NULL,NULL,1,'跟踪管理',NULL,NULL,'MENU','','menu-icon fa fa-list','menu.log.trace_manage',20,NULL,'log');

INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('0201',NULL,NULL,NULL,NULL,1,'监控管理','02',NULL,'MENU','log-ui/log/monitor_index.html','menu-icon fa fa-caret-right','menu.log.monitor_manage',20,NULL,'log');
 
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('0202',NULL,NULL,NULL,NULL,1,'操作日志','02',NULL,'MENU','log-ui/log/log_index.html','menu-icon fa fa-caret-right','menu.log.log_manage',30,NULL,'log');

INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('020201',NULL,NULL,NULL,NULL,1,'分页查询','0202',NULL,'BUTTON','operation/page',NULL,'menu.page_search',NULL,'operation:page','log');


INSERT INTO e_role (id,created_time,creator_id,modified_time,modifier_id,description,name)  VALUES ('ADMIN',NULL,NULL,NULL,NULL,'ADMIN','ADMIN');
 
INSERT INTO e_user (id,created_time,creator_id,modified_time,modifier_id,deleted,name,password,salt,username,email,phone,enabled) VALUES ('ADMIN','2015-12-15 12:59:33',NULL,'2015-12-15 12:59:33',NULL,0,'Loy','662a764fe348cb9f2153eef98b1732f5d9ac3233c78409a76ce1f39fbc42ad660ab10322aea38351',NULL,'admin',NULL,NULL,1);
 

 
INSERT INTO e_user_role (user_id,role_id) VALUES ('ADMIN','ADMIN');
 
INSERT INTO e_role_resource (role_id,resource_id) VALUES ('ADMIN','01');

INSERT INTO e_role_resource (role_id,resource_id) VALUES ('ADMIN','0101');

INSERT INTO e_role_resource (role_id,resource_id) VALUES ('ADMIN','0102');

INSERT INTO e_role_resource (role_id,resource_id) VALUES ('ADMIN','010201');
INSERT INTO e_role_resource (role_id,resource_id) VALUES ('ADMIN','010202');
INSERT INTO e_role_resource (role_id,resource_id) VALUES ('ADMIN','010203');

INSERT INTO e_role_resource (role_id,resource_id) VALUES ('ADMIN','02');

INSERT INTO e_role_resource (role_id,resource_id) VALUES ('ADMIN','0201');
INSERT INTO e_role_resource (role_id,resource_id) VALUES ('ADMIN','0202');



INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('system.upm','system.upm','','用户权限管理',null);
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('system.upm_zh_CN','system.upm','zh_CN','用户权限管理',null);
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('system.upm_en_US','system.upm','en_US','UPM',null);

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.add','menu.add','','新增',null);
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.add_zh_CN','menu.add','zh_CN','新增',null);
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.add_en_US','menu.add','en_US','ADD',null);

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.edit','menu.edit','','编辑',null);
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.edit_zh_CN','menu.edit','zh_CN','编辑',null);
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.edit_en_US','menu.edit','en_US','EDIT',null);

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.save','menu.save','','保存',null);
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.save_zh_CN','menu.save','zh_CN','保存',null);
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.save_en_US','menu.save','en_US','SAVE',null);

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.update','menu.update','','修改',null);
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.update_zh_CN','menu.update','zh_CN','修改',null);
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.update_en_US','menu.update','en_US','UPDATE',null);

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.delete','menu.delete','','删除',null);
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.delete_zh_CN','menu.delete','zh_CN','删除',null);
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.delete_en_US','menu.delete','en_US','DELETE',null);

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.page_search','menu.page_search','','分页查询',null);
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.page_search_zh_CN','menu.page_search','zh_CN','分页查询',null);
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.page_search_en_US','menu.page_search','en_US','PAGE SEARCH',null);

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.sys_manage','menu.upm.sys_manage','','系统管理','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.sys_manage_zh_CN','menu.upm.sys_manage','zh_CN','系统管理','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.sys_manage_en_US','menu.upm.sys_manage','en_US','SYSTEM MGT','upm');

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.user_manage','menu.upm.user_manage','','用户管理','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.user_manage_zh_CN','menu.upm.user_manage','zh_CN','用户管理','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.user_manage_en_US','menu.upm.user_manage','en_US','USER MGT','upm');

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.role_manage','menu.upm.role_manage','','角色管理','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.role_manage_zh_CN','menu.upm.role_manage','zh_CN','角色管理','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.role_manage_en_US','menu.upm.role_manage','en_US','ROLE MGT','upm');

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.role_authority','menu.upm.role_authority','','授权','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.role_authority_zh_CN','menu.upm.role_authority','zh_CN','授权','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.role_authority_en_US','menu.upm.role_authority','en_US','AUTHORITY','upm');


INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.resource_manage','menu.upm.resource_manage','','菜单资源','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.resource_manage_zh_CN','menu.upm.resource_manage','zh_CN','菜单资源','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.resource_manage_en_US','menu.upm.resource_manage','en_US','MENU RESOURCE','upm');

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.log.trace_manage','menu.log.trace_manage','','跟踪管理','log');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.log.trace_manage_zh_CN','menu.log.trace_manage','zh_CN','跟踪管理','log');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.log.trace_manage_en_US','menu.log.trace_manage','en_US','TRACKING MGT','log');

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.log.monitor_manage','menu.log.monitor_manage','','监控管理','log');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.log.monitor_manage_zh_CN','menu.log.monitor_manage','zh_CN','监控管理','log');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.log.monitor_manage_en_US','menu.log.monitor_manage','en_US','MONITOR MGT','log');

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.log.log_manage','menu.log.log_manage','','操作日志','log');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.log.log_manage_zh_CN','menu.log.log_manage','zh_CN','操作日志','log');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.log.log_manage_en_US','menu.log.log_manage','en_US','OPERATE LOG','log');


INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.resource_button','menu.upm.resource_button','','按钮','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.resource_button_zh_CN','menu.upm.resource_button','zh_CN','按钮','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.resource_button_en_US','menu.upm.resource_button','en_US','BUTTON','upm');

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.resource_client','menu.upm.resource_client','','客户端','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.resource_client_zh_CN','menu.upm.resource_client','zh_CN','客户端','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.resource_client_en_US','menu.upm.resource_client','en_US','CLIENT','upm');
--客户端授权

INSERT INTO oauth_client_details (client_id,resource_ids,client_secret,scope,authorized_grant_types,web_server_redirect_uri,authorities,access_token_validity,refresh_token_validity,additional_information,autoapprove) VALUES  ('acme',NULL,'acmesecret','openid','authorization_code,client_credentials,refresh_token',NULL,NULL,NULL,NULL,NULL,'true');
INSERT INTO e_role (id,created_time,creator_id,modified_time,modifier_id,description,name)  VALUES ('SERVICE_CLIENT',NULL,NULL,NULL,NULL,'SERVICE CLIENT','SERVICE CLIENT');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('authority_resource',NULL,NULL,NULL,NULL,1,'授权资源','0102',NULL,'CLIENT','authority/resource',NULL,'menu.upm.authority_resource',NULL,'authority:resource','upm');
INSERT INTO e_client_role (client_id,role_id) VALUES ('acme','SERVICE_CLIENT');
INSERT INTO e_role_resource (role_id,resource_id) VALUES ('SERVICE_CLIENT','authority_resource');

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.authority_resource','menu.upm.authority_resource','','查授权资源','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.authority_resource_zh_CN','menu.upm.authority_resource','zh_CN','查授权资源','upm');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.authority_resource_en_US','menu.upm.authority_resource','en_US','SEARCH AUTHORITY RESOURCE','upm');

