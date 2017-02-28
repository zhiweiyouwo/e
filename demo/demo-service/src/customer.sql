
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('com_xx_demo_test_customer',NULL,NULL,NULL,NULL,1,'客户','demo',NULL,'MENU','demo-ui/demo/test/customer_index.html','menu-icon fa fa-caret-right','menu.demo.test.customer_manage',20,NULL,'demo');

INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.demo.test.customer_manage','menu.demo.test.customer_manage','','客户管理','demo');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.demo.test.customer_manage_zh_CN','menu.demo.test.customer_manage','zh_CN','客户管理','demo');
INSERT INTO e_menu_i18n (id,key_,lang_,value_,system_code) VALUES ('menu.demo.test.customer_manage_en_US','menu.demo.test.customer_manage','en_US','Customer MGT','demo');


INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)  VALUES ('com_xx_demo_test_customer_add',NULL,NULL,NULL,NULL,1,'新增','com_xx_demo_test_customer',NULL,'BUTTON','customer/save',NULL,'menu.add',NULL,'customer:save','demo');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('com_xx_demo_test_customer_edit',NULL,NULL,NULL,NULL,1,'编辑','com_xx_demo_test_customer',NULL,'BUTTON','customer/update',NULL,'menu.edit',NULL,'customer:update','demo');
INSERT INTO e_resource (id,created_time,creator_id,modified_time,modifier_id,available,name,parent_id,permission,resource_type,url,cls,lable_key,sort_num,access_code,system_id)   VALUES ('com_xx_demo_test_customer_delete',NULL,NULL,NULL,NULL,1,'删除','com_xx_demo_test_customer',NULL,'BUTTON','customer/delete',NULL,'menu.delete',NULL,'customer:delete','demo');