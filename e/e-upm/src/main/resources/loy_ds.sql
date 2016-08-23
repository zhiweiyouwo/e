INSERT INTO loy_system(id,name,code,url,lable_key)  VALUES('upm','用户权限管理','upm','http://localhost:19090/upm','system.upm');

INSERT INTO loy_system(id,name,code,url,lable_key)  VALUES('crm','客户关系管理','crm','http://localhost:29090/crm','system.crm');
 
INSERT INTO loy_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id) VALUES ('01',NULL,NULL,NULL,NULL,1,'系统管理',NULL,NULL,'MENU','','menu-icon fa fa-desktop','menu.upm.sys_manage',10,NULL,'upm');
 
INSERT INTO loy_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('0101',NULL,NULL,NULL,NULL,1,'用户管理','01',NULL,'MENU','upm/sys/user_index.html','menu-icon fa fa-caret-right','menu.upm.user_manage',10,NULL,'upm');
 
INSERT INTO loy_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('0102',NULL,NULL,NULL,NULL,1,'角色管理','01',NULL,'MENU','upm/sys/role_index.html','menu-icon fa fa-caret-right','menu.upm.role_manage',20,NULL,'upm');
 
INSERT INTO loy_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('010201',NULL,NULL,NULL,NULL,1,'新增','0102',NULL,'BUTTON','role/save',NULL,'menu.add',NULL,'role:save','upm');
 
INSERT INTO loy_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('010202',NULL,NULL,NULL,NULL,1,'编辑','0102',NULL,'BUTTON','role/update',NULL,'menu.edit',NULL,'role:update','upm');
 
INSERT INTO loy_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('010203',NULL,NULL,NULL,NULL,1,'授权','0102',NULL,'BUTTON','role/authority',NULL,'menu.upm.role_authority',NULL,'role:authority','upm');
 
INSERT INTO loy_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('0103',NULL,NULL,NULL,NULL,1,'监控管理','01',NULL,'MENU','upm/sys/monitor_index.html','menu-icon fa fa-caret-right','menu.upm.monitor_manage',30,NULL,'upm');
 
INSERT INTO loy_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('0104',NULL,NULL,NULL,NULL,1,'操作日志','01',NULL,'MENU','upm/sys/log_index.html','menu-icon fa fa-caret-right','menu.upm.log_manage',30,NULL,'upm');
 
INSERT INTO loy_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('02',NULL,NULL,NULL,NULL,1,'行政管理',NULL,NULL,'MENU','','menu-icon fa fa-list','menu.upm.personnel_manage',20,NULL,'upm');
 
INSERT INTO loy_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('0201',NULL,NULL,NULL,NULL,1,'部门管理','02',NULL,'MENU','upm/personnel/org_index.html','menu-icon fa fa-caret-right','menu.upm.org_manage',10,NULL,'upm');
 
INSERT INTO loy_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('0202',NULL,NULL,NULL,NULL,1,'职务管理','02',NULL,'MENU','upm/personnel/position_index.html','menu-icon fa fa-caret-right','menu.upm.position_manage',20,NULL,'upm');
 
INSERT INTO loy_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('0203',NULL,NULL,NULL,NULL,1,'人员管理','02',NULL,'MENU','upm/personnel/employee_index.html','menu-icon fa fa-caret-right','menu.upm.employee_manage',30,NULL,'upm');

INSERT INTO loy_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id) VALUES ('0204',NULL,NULL,NULL,NULL,1,'通知管理','02',NULL,'MENU','upm/personnel/notice_index.html','menu-icon fa fa-caret-right','menu.upm.notice_manage',40,NULL,'upm');
 

INSERT INTO loy_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id) VALUES ('crm_01',NULL,NULL,NULL,NULL,1,'CRM管理',NULL,NULL,'MENU','','menu-icon fa fa-desktop','menu.crm.crm_manage',10,NULL,'crm');
INSERT INTO loy_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('crm_0101',NULL,NULL,NULL,NULL,1,'客户管理','crm_01',NULL,'MENU','crm/customer/customer_index.html','menu-icon fa fa-caret-right','menu.crm.customer_manage',30,NULL,'crm');




INSERT INTO loy_role (id,created_time,creator_id,modified_time,modifier_id,description,name)  VALUES ('ADMIN',NULL,NULL,NULL,NULL,'ADMIN','ADMIN');
 
INSERT INTO loy_user (id,created_time,creator_id,modified_time,modifier_id,deleted,name,password,user_status,salt,username,email,phone) VALUES ('ADMIN','2015-12-15 12:59:33',NULL,'2015-12-15 12:59:33',NULL,0,'Loy','$loy$SHA-512$1$$ujJTh2rta8ItSm/1PYQGxq2GQZXtFEq1yHYhtsIztUi66uaVbfNG7IwX9eoQ817jy8UUeX7X3dMUVGTioLq0Ew==','NORMAL',NULL,'admin',NULL,NULL);
 


INSERT INTO loy_employee (id) VALUES ('ADMIN');
 
INSERT INTO loy_user_role (user_id,role_id) VALUES ('ADMIN','ADMIN');
 
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','01');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','0101');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','0102');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','0103');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','010201');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','010202');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','010203');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','0104');



INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','crm_01');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','crm_0101');


INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','02');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','0201');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','0202');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','0203');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','0204');

INSERT INTO loy_org (id,name,code,sort_num) VALUES ('root','XXXXXXXX','000',10);
INSERT INTO loy_position (id,name,code,sort_num) VALUES ('root','XXXXXXXX','000',10);

INSERT INTO loy_sequence(id,v) VALUES('EMPLOYEE_SEQ',1);




INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('system.crm','system.crm','','客户关系管理',null);
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('system.crm_zh_CN','system.crm','zh_CN','客户关系管理',null);
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('system.crm_en_US','system.crm','en_US','CRM',null);


INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('system.upm','system.upm','','用户权限管理',null);
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('system.upm_zh_CN','system.upm','zh_CN','用户权限管理',null);
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('system.upm_en_US','system.upm','en_US','UPM',null);

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.add','menu.add','','新增',null);
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.add_zh_CN','menu.add','zh_CN','新增',null);
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.add_en_US','menu.add','en_US','ADD',null);

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.edit','menu.edit','','编辑',null);
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.edit_zh_CN','menu.edit','zh_CN','编辑',null);
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.edit_en_US','menu.edit','en_US','EDIT',null);

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.save','menu.save','','保存',null);
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.save_zh_CN','menu.save','zh_CN','保存',null);
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.save_en_US','menu.save','en_US','SAVE',null);

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.update','menu.update','','修改',null);
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.update_zh_CN','menu.update','zh_CN','修改',null);
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.update_en_US','menu.update','en_US','UPDATE',null);

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.delete','menu.delete','','删除',null);
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.delete_zh_CN','menu.delete','zh_CN','删除',null);
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.delete_en_US','menu.delete','en_US','DELETE',null);

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.sys_manage','menu.upm.sys_manage','','系统管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.sys_manage_zh_CN','menu.upm.sys_manage','zh_CN','系统管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.sys_manage_en_US','menu.upm.sys_manage','en_US','SYSTEM MGT','upm');

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.user_manage','menu.upm.user_manage','','用户管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.user_manage_zh_CN','menu.upm.user_manage','zh_CN','用户管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.user_manage_en_US','menu.upm.user_manage','en_US','USER MGT','upm');

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.role_manage','menu.upm.role_manage','','角色管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.role_manage_zh_CN','menu.upm.role_manage','zh_CN','角色管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.role_manage_en_US','menu.upm.role_manage','en_US','ROLE MGT','upm');

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.role_authority','menu.upm.role_authority','','授权','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.role_authority_zh_CN','menu.upm.role_authority','zh_CN','授权','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.role_authority_en_US','menu.upm.role_authority','en_US','AUTHORITY','upm');

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.monitor_manage','menu.upm.monitor_manage','','监控管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.monitor_manage_zh_CN','menu.upm.monitor_manage','zh_CN','监控管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.monitor_manage_en_US','menu.upm.monitor_manage','en_US','MONITOR MGT','upm');

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.log_manage','menu.upm.log_manage','','操作日志','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.log_manage_zh_CN','menu.upm.log_manage','zh_CN','操作日志','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.log_manage_en_US','menu.upm.log_manage','en_US','OPERATE LOG','upm');

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.personnel_manage','menu.upm.personnel_manage','','行政管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.personnel_manage_zh_CN','menu.upm.personnel_manage','zh_CN','行政管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.personnel_manage_en_US','menu.upm.personnel_manage','en_US','PERSONNEL MGT','upm');

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.org_manage','menu.upm.org_manage','','部门管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.org_manage_zh_CN','menu.upm.org_manage','zh_CN','部门管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.org_manage_en_US','menu.upm.org_manage','en_US','ORG MGT','upm');

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.position_manage','menu.upm.position_manage','','职务管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.position_manage_zh_CN','menu.upm.position_manage','zh_CN','职务管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.position_manage_en_US','menu.upm.position_manage','en_US','POSITION MGT','upm');

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.employee_manage','menu.upm.employee_manage','','员工管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.employee_manage_zh_CN','menu.upm.employee_manage','zh_CN','员工管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.employee_manage_en_US','menu.upm.employee_manage','en_US','EMPLOYEE MGT','upm');

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.notice_manage','menu.upm.notice_manage','','通知管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.notice_manage_zh_CN','menu.upm.notice_manage','zh_CN','通知管理','upm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.upm.notice_manage_en_US','menu.upm.notice_manage','en_US','NOTICE MGT','upm');

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.crm.crm_manage','menu.crm.crm_manage','','客户关系','crm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.crm.crm_manage_zh_CN','menu.crm.crm_manage','zh_CN','客户关系','crm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.crm.crm_manage_en_US','menu.crm.crm_manage','en_US','CRM','crm');

INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.crm.customer_manage','menu.crm.customer_manage','','客户管理','crm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.crm.customer_manage_zh_CN','menu.crm.customer_manage','zh_CN','客户管理','crm');
INSERT INTO loy_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.crm.customer_manage_en_US','menu.crm.customer_manage','en_US','CUSTOMER MGT','crm');