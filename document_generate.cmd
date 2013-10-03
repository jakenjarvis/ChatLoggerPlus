@echo off
set devdir=C:\dev_mcp\mcdev\forge\mcp
set topleveldomain=com
set prjdir=C:\dev_mcp\workspace\ChatLoggerPlus
set prjsrcdir=%prjdir%\src

:step1
echo "****************************************"
echo "document�̃N���[���A�b�v"
echo "****************************************"
cd %prjdir%\document
for %%f in ( * ) do call :document_cleanup "%%f"
for /D %%f in ( * ) do call :document_cleanup "%%f" d
goto :step2

:document_cleanup
set flag=OFF
for %%e in ( .git .nojekyll ) do if %1=="%%e" set flag=ON
if "%flag%"=="ON" goto :EOF
if "%2"=="" del /F /S /Q %1
if "%2"=="d" rmdir /S /Q %1
goto :EOF

:step2
echo "****************************************"
echo "sphinx�̃N���[���A�b�v"
echo "****************************************"
cd %prjdir%
rmdir /S /Q %prjdir%\sphinx\build
rmdir /S /Q %prjdir%\sphinx\source\java

echo "****************************************"
echo "javadoc�̐���"
echo "****************************************"
call ant javadoc -f javadoc.xml

echo "****************************************"
echo "javasphinx�̐���"
echo "****************************************"
call javasphinx-apidoc -f -o sphinx/source/java src/com/tojc/minecraft/mod/ChatLoggerPlusPlugin/v1
cd %prjdir%\sphinx

echo "****************************************"
echo "sphinx��HTML����"
echo "****************************************"
call make html

echo "****************************************"
echo "document�ɃR�s�["
echo "****************************************"
xcopy %prjdir%\sphinx\build\html %prjdir%\document /e /c /h /r /y

pause
