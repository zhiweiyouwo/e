@echo off
if "%1" == "yes" goto begin
mshta vbscript:createobject("wscript.shell").run("%~nx0 yes",0)(window.close)&&exit
:begin
cd e-example/e-micro-service/e-example-ms-start-w/build/libs
java -jar e-example-ms-start-w-3.0.1.3.jar