--demo  sex  dictionary
INSERT INTO e_dictionary (id,i18n_key,code,name,parent_id) VALUES ('demo.sex','demo.sex','SEX','性别',NULL);
INSERT INTO e_dictionary (id,i18n_key,code,name,sort_num,parent_id) VALUES ('demo.sex.male','demo.sex.male','MALE','男',1,'demo.sex');
INSERT INTO e_dictionary (id,i18n_key,code,name,sort_num,parent_id) VALUES ('demo.sex.female','demo.sex.female','FEMALE','女',2,'demo.sex');

INSERT INTO e_i18n (id,key_,lang_,value_,system_code) VALUES ('demo.sex.male','demo.sex.male','','男','demo');
INSERT INTO e_i18n (id,key_,lang_,value_,system_code) VALUES ('demo.sex.male_zh_CN','demo.sex.male','zh_CN','男','demo');
INSERT INTO e_i18n (id,key_,lang_,value_,system_code) VALUES ('demo.sex.male_en_US','demo.sex.male','en_US','MALE','demo');

INSERT INTO e_i18n (id,key_,lang_,value_,system_code) VALUES ('demo.sex.female','demo.sex.female','','女','demo');
INSERT INTO e_i18n (id,key_,lang_,value_,system_code) VALUES ('demo.sex.female_zh_CN','demo.sex.female','zh_CN','女','demo');
INSERT INTO e_i18n (id,key_,lang_,value_,system_code) VALUES ('demo.sex.female_en_US','demo.sex.female','en_US','FEMALE','demo');


