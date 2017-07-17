
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('com_xx_demo_test_company',NULL,NULL,NULL,NULL,1,'公司','demo',NULL,'MENU','demo-ui/demo/test/company_index.html','menu-icon fa fa-caret-right','menu.demo.test.company_manage',20,NULL,'demo');

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.demo.test.company_manage','menu.demo.test.company_manage','','公司管理','demo');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.demo.test.company_manage_zh_CN','menu.demo.test.company_manage','zh_CN','公司管理','demo');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.demo.test.company_manage_en_US','menu.demo.test.company_manage','en_US','Company MGT','demo');


INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('com_xx_demo_test_company_add',NULL,NULL,NULL,NULL,1,'新增','com_xx_demo_test_company',NULL,'BUTTON','company/save',NULL,'menu.add',NULL,'company:save','demo');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('com_xx_demo_test_company_edit',NULL,NULL,NULL,NULL,1,'编辑','com_xx_demo_test_company',NULL,'BUTTON','company/update',NULL,'menu.edit',NULL,'company:update','demo');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('com_xx_demo_test_company_delete',NULL,NULL,NULL,NULL,1,'删除','com_xx_demo_test_company',NULL,'BUTTON','company/delete',NULL,'menu.delete',NULL,'company:delete','demo');