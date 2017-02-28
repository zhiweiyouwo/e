@echo off
rem if "%1" == "yes" goto begin
rem mshta vbscript:createobject("wscript.shell").run("%~nx0 yes",0)(window.close)&&exit
rem :begin
cd e-example/e-micro-service/e-example-ms-start/build/libs
java -jar e-example-ms-start-3.0.1.3.jar