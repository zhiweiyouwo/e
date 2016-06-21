
begin;
 INSERT INTO loy_resource 
(id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code) 
 VALUES ('01',NULL,NULL,NULL,NULL,1,'系统管理',NULL,NULL,'MENU','','menu-icon fa fa-desktop','menu.sys_manage',10,NULL);
 
 INSERT INTO loy_resource 
(id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code) 
 VALUES ('0101',NULL,NULL,NULL,NULL,1,'用户管理','01',NULL,'MENU','static/sys/user_index.html','menu-icon fa fa-caret-right','menu.user_manage',10,NULL);
 
 INSERT INTO loy_resource 
(id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code) 
 VALUES ('0102',NULL,NULL,NULL,NULL,1,'角色管理','01',NULL,'MENU','static/sys/role_index.html','menu-icon fa fa-caret-right','menu.role_manage',20,NULL);
 
 INSERT INTO loy_resource 
(id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code) 
 VALUES ('010201',NULL,NULL,NULL,NULL,1,'新增','0102',NULL,'BUTTON','role/save',NULL,'menu.add',NULL,'role:save');
 
 INSERT INTO loy_resource 
(id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code) 
 VALUES ('010202',NULL,NULL,NULL,NULL,1,'编辑','0102',NULL,'BUTTON','role/update',NULL,'menu.edit',NULL,'role:update');
 
 
 INSERT INTO loy_resource 
(id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code) 
 VALUES ('010203',NULL,NULL,NULL,NULL,1,'授权','0102',NULL,'BUTTON','role/authority',NULL,'menu.authority',NULL,'role:authority');
 
 
 
 INSERT INTO loy_resource 
(id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code) 
 VALUES ('0103',NULL,NULL,NULL,NULL,1,'监控管理','01',NULL,'MENU','static/sys/monitor_index.html','menu-icon fa fa-caret-right','menu.monitor_manage',30,NULL);
 
 INSERT INTO loy_resource 
(id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code) 
 VALUES ('0104',NULL,NULL,NULL,NULL,1,'操作日志','01',NULL,'MENU','static/sys/log_index.html','menu-icon fa fa-caret-right','menu.log_manage',30,NULL);
 
 INSERT INTO loy_resource 
(id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code) 
 VALUES ('test',NULL,NULL,NULL,NULL,1,'自动代码页','01',NULL,'MENU','static/sys/test_index.html','menu-icon fa fa-caret-right',null,30,NULL);
 
 
 INSERT INTO loy_resource 
(id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code) 
 VALUES ('02',NULL,NULL,NULL,NULL,1,'行政管理',NULL,NULL,'MENU','','menu-icon fa fa-list','menu.personnel_manage',20,NULL);
 
 INSERT INTO loy_resource 
(id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code) 
 VALUES ('0201',NULL,NULL,NULL,NULL,1,'部门管理','02',NULL,'MENU','static/personnel/org_index.html','menu-icon fa fa-caret-right','menu.org_manage',10,NULL);
 
 INSERT INTO loy_resource 
(id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code) 
 VALUES ('0202',NULL,NULL,NULL,NULL,1,'职务管理','02',NULL,'MENU','static/personnel/position_index.html','menu-icon fa fa-caret-right','menu.position_manage',20,NULL);
 
INSERT INTO loy_resource 
(id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code) 
 VALUES ('0203',NULL,NULL,NULL,NULL,1,'人员管理','02',NULL,'MENU','static/personnel/employee_index.html','menu-icon fa fa-caret-right','menu.employee_manage',30,NULL);
 
 INSERT INTO loy_resource 
(id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code) 
 VALUES ('0204',NULL,NULL,NULL,NULL,1,'通知管理','02',NULL,'MENU','static/personnel/notice_index.html','menu-icon fa fa-caret-right','menu.notice_manage',40,NULL);
 
 INSERT INTO loy_role (id,created_time,creator_id,modified_time,modifier_id,description,name) 
 VALUES ('ADMIN',NULL,NULL,NULL,NULL,'ADMIN','ADMIN');
 
 
 INSERT INTO loy_user (id,created_time,creator_id,modified_time,modifier_id,deleted,name,password,user_status,salt,username,email,phone) 
 VALUES ('ADMIN','2015-12-15 12:59:33',NULL,'2015-12-15 12:59:33',NULL,0,'Loy','ujJTh2rta8ItSm/1PYQGxq2GQZXtFEq1yHYhtsIztUi66uaVbfNG7IwX9eoQ817jy8UUeX7X3dMUVGTioLq0Ew==','NORMAL',NULL,'admin',NULL,NULL);
 
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

INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','test');

INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','02');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','0201');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','0202');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','0203');
INSERT INTO loy_role_resource (role_id,resource_id) VALUES ('ADMIN','0204');

INSERT INTO loy_org (id,name,code,sort_num) VALUES ('root','XXXXXXXX','000',10);
INSERT INTO loy_position (id,name,code,sort_num) VALUES ('root','XXXXXXXX','000',10);

INSERT INTO loy_sequence(id,v) VALUES('EMPLOYEE_SEQ',1);
commit;