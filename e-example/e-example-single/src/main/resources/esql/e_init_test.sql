--- 数据权限例子-------------------------------------------------------------

INSERT INTO e_user (id,created_time,creator_id,modified_time,modifier_id,deleted,name,password,salt,username,email,phone,enabled) VALUES ('test','2015-12-15 12:59:33',NULL,'2015-12-15 12:59:33',NULL,0,'Test','662a764fe348cb9f2153eef98b1732f5d9ac3233c78409a76ce1f39fbc42ad660ab10322aea38351',NULL,'test',NULL,NULL,1);
INSERT INTO e_role (id,created_time,creator_id,modified_time,modifier_id,description,name)  VALUES ('test_test',NULL,NULL,NULL,NULL,'例子，一个只可以查自己创建的公司的角色','可查自己创建的公司');
INSERT INTO e_role (id,created_time,creator_id,modified_time,modifier_id,description,name)  VALUES ('test_all',NULL,NULL,NULL,NULL,'例子，一个只可以查所有公司的角色','可查所有公司');

INSERT INTO e_user_role (user_id,role_id) VALUES ('test','test_test');
INSERT INTO e_user_role (user_id,role_id) VALUES ('test','ADMIN');

INSERT INTO e_data_strategy (id,name,ql,unique_key,effective,description) VALUES ('1','公司分页查询-只能查自己创建的数据','creatorId = :userId','CompanyServiceImpl.queryPage',1,'例子');
INSERT INTO e_data_strategy (id,name,ql,unique_key,effective,description) VALUES ('2','公司分页查询-能查所有数据',' 1=1 ','CompanyServiceImpl.queryPage',1,'例子');

INSERT INTO e_strategy_role(strategy_id,role_id) VALUES('1','test_test');
-----------------------------------------------------------------------------
