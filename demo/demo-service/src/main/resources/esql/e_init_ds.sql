--demo

INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id) VALUES ('demo',NULL,NULL,NULL,NULL,1,'例子',NULL,NULL,'MENU','','menu-icon fa fa-book','menu.demo.example_manage',30,NULL,'demo');
INSERT INTO e_role_resource (role_id,resource_id) VALUES ('ADMIN','demo');

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.demo.example_manage','menu.demo.example_manage','','例子','demo');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.demo.example_manage_zh_CN','menu.demo.example_manage','zh_CN','例子','demo');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.demo.example_manage_en_US','menu.demo.example_manage','en_US','EXAMPLE','demo');


INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('com_xx_demo_test_company',NULL,NULL,NULL,NULL,1,'公司','demo',NULL,'MENU','demo-ui/demo/test/company_index.html','menu-icon fa fa-caret-right','menu.demo.test.company_manage',20,NULL,'demo');
INSERT INTO e_role_resource (role_id,resource_id) VALUES ('ADMIN','com_xx_demo_test_company');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.demo.test.company_manage','menu.demo.test.company_manage','','公司管理','demo');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.demo.test.company_manage_zh_CN','menu.demo.test.company_manage','zh_CN','公司管理','demo');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.demo.test.company_manage_en_US','menu.demo.test.company_manage','en_US','Company MGT','demo');


INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('com_xx_demo_test_company_add',NULL,NULL,NULL,NULL,1,'新增','com_xx_demo_test_company',NULL,'BUTTON','company/save',NULL,'menu.add',NULL,'company:save','demo');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('com_xx_demo_test_company_edit',NULL,NULL,NULL,NULL,1,'编辑','com_xx_demo_test_company',NULL,'BUTTON','company/update',NULL,'menu.edit',NULL,'company:update','demo');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('com_xx_demo_test_company_delete',NULL,NULL,NULL,NULL,1,'删除','com_xx_demo_test_company',NULL,'BUTTON','company/del',NULL,'menu.delete',NULL,'company:del','demo');

INSERT INTO e_role_resource (role_id,resource_id) VALUES ('ADMIN','com_xx_demo_test_company_add');
INSERT INTO e_role_resource (role_id,resource_id) VALUES ('ADMIN','com_xx_demo_test_company_edit');
INSERT INTO e_role_resource (role_id,resource_id) VALUES ('ADMIN','com_xx_demo_test_company_delete');

--demo customer

INSERT INTO e_dictionary (id,i18n_key,code,name,parent_id) VALUES ('demo.sex','demo.sex','SEX','性别',NULL);
INSERT INTO e_dictionary (id,i18n_key,code,name,sort_num,parent_id) VALUES ('demo.sex.male','demo.sex.male','MALE','男',1,'demo.sex');
INSERT INTO e_dictionary (id,i18n_key,code,name,sort_num,parent_id) VALUES ('demo.sex.female','demo.sex.female','FEMALE','女',2,'demo.sex');

INSERT INTO e_i18n (id,key_,lang_,value_,system_code) VALUES ('demo.sex.male','demo.sex.male','','男','demo');
INSERT INTO e_i18n (id,key_,lang_,value_,system_code) VALUES ('demo.sex.male_zh_CN','demo.sex.male','zh_CN','男','demo');
INSERT INTO e_i18n (id,key_,lang_,value_,system_code) VALUES ('demo.sex.male_en_US','demo.sex.male','en_US','MALE','demo');

INSERT INTO e_i18n (id,key_,lang_,value_,system_code) VALUES ('demo.sex.female','demo.sex.female','','女','demo');
INSERT INTO e_i18n (id,key_,lang_,value_,system_code) VALUES ('demo.sex.female_zh_CN','demo.sex.female','zh_CN','女','demo');
INSERT INTO e_i18n (id,key_,lang_,value_,system_code) VALUES ('demo.sex.female_en_US','demo.sex.female','en_US','FEMALE','demo');


INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('com_xx_demo_test_customer',NULL,NULL,NULL,NULL,1,'客户','demo',NULL,'MENU','demo-ui/demo/test/customer_index.html','menu-icon fa fa-caret-right','menu.demo.test.customer_manage',20,NULL,'demo');

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.demo.test.customer_manage','menu.demo.test.customer_manage','','客户管理','demo');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.demo.test.customer_manage_zh_CN','menu.demo.test.customer_manage','zh_CN','客户管理','demo');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.demo.test.customer_manage_en_US','menu.demo.test.customer_manage','en_US','CUSTOMER MGT','demo');


INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('com_xx_demo_test_customer_add',NULL,NULL,NULL,NULL,1,'新增','com_xx_demo_test_customer',NULL,'BUTTON','customer/save',NULL,'menu.add',NULL,'customer:save','demo');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('com_xx_demo_test_customer_edit',NULL,NULL,NULL,NULL,1,'编辑','com_xx_demo_test_customer',NULL,'BUTTON','customer/update',NULL,'menu.edit',NULL,'customer:update','demo');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('com_xx_demo_test_customer_delete',NULL,NULL,NULL,NULL,1,'删除','com_xx_demo_test_customer',NULL,'BUTTON','customer/del',NULL,'menu.del',NULL,'customer:del','demo');



INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('demo_sys',NULL,NULL,NULL,NULL,1,'DEMO 系统','basic',NULL,'MENU',NULL,'menu-icon fa fa-caret-right','menu.basic.demo_sys',20,NULL,'basic');

INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('com_loy_e_basic_data_timedTask',NULL,NULL,NULL,NULL,1,'定时任务','demo_sys',NULL,'MENU','demo/basic/data/timedTask_index.html','menu-icon fa fa-caret-right','menu.basic.data.timedTask_manage',10,NULL,'basic');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('com_loy_e_basic_data_param',NULL,NULL,NULL,NULL,1,'参数管理','demo_sys',NULL,'MENU','demo/basic/data/configParam_index.html','menu-icon fa fa-caret-right','menu.basic.data.param_manage',20,NULL,'basic');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('com_loy_e_basic_data_tailLog',NULL,NULL,NULL,NULL,1,'日志控制台','demo_sys',NULL,'MENU','demo/basic/data/tailLog_index.html','menu-icon fa fa-caret-right','menu.basic.data.tailLog',30,NULL,'basic');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('com_loy_e_basic_data_api',NULL,NULL,NULL,NULL,1,'API','demo_sys',NULL,'MENU','demo/basic/data/api_index.html','menu-icon fa fa-caret-right','menu.basic.data.api',40,NULL,'basic');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('com_loy_e_basic_data_cache',NULL,NULL,NULL,NULL,1,'缓存管理','demo_sys',NULL,'MENU','demo/basic/data/cache_index.html','menu-icon fa fa-caret-right','menu.basic.data.cache',50,NULL,'basic');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('com_loy_e_basic_data_dictionary',NULL,NULL,NULL,NULL,1,'数据字典','demo_sys',NULL,'MENU','demo/basic/data/dictionary_index.html','menu-icon fa fa-caret-right','menu.basic.data.dictionary',60,NULL,'basic');

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.basic.data.task_manage','menu.basic.data.timedTask_manage','','定时任务管理','basic');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.basic.data.task_manage_zh_CN','menu.basic.data.timedTask_manage','zh_CN','定时任务管理','basic');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.basic.data.task_manage_en_US','menu.basic.data.timedTask_manage','en_US','TIMED TASKMGT','basic');

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.basic.data.param_manage','menu.basic.data.param_manage','','参数管理','basic');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.basic.data.param_manage_zh_CN','menu.basic.data.param_manage','zh_CN','参数管理','basic');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.basic.data.param_manage_en_US','menu.basic.data.param_manage','en_US','PARAM MGT','basic');

----例子参数
INSERT INTO e_config_param (id,param_key,description,error_msg,name,regular_expression,val,order_num) VALUES ('OPEN_LOG','OPEN_LOG','例子,没实际用到','格式为true或false','是否打开日志','^(true)|(false)$','false',10);
INSERT INTO e_config_param (id,param_key,description,error_msg,name,regular_expression,val,order_num) VALUES ('MEMBER_REGISTRATION_FEE','MEMBER_REGISTRATION_FEE','例子,没实际用到','格式为数值型如100.00','金额','^(([1-9][0-9]*)|(([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2})))$','100.00',20);

	
INSERT INTO e_timed_task (id,task_name,status,init_status,cron,service_name,exe_method,description) VALUES ('TEST_HELLO','测试定时任务','STOP','STOP','0/5 * * * * ?','timeTaskTestService','hello','定时任务例子')

--INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('com_loy_e_basic_data_timedTask_add',NULL,NULL,NULL,NULL,1,'新增','com_loy_e_basic_data_timedTask',NULL,'BUTTON','timedTask/save',NULL,'menu.add',NULL,'timedTask:save','basic');
--INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('com_loy_e_basic_data_timedTask_edit',NULL,NULL,NULL,NULL,1,'编辑','com_loy_e_basic_data_timedTask',NULL,'BUTTON','timedTask/update',NULL,'menu.edit',NULL,'timedTask:update','basic');
--INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('com_loy_e_basic_data_timedTask_delete',NULL,NULL,NULL,NULL,1,'删除','com_loy_e_basic_data_timedTask',NULL,'BUTTON','timedTask/delete',NULL,'menu.delete',NULL,'timedTask:delete','basic');