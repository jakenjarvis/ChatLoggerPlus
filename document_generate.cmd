@echo off
set devdir=C:\dev_mcp\mcdev\forge\mcp
set topleveldomain=com
set prjdir=C:\dev_mcp\workspace\ChatLoggerPlus
set prjsrcdir=%prjdir%\src
set sevenzipexe=C:\dev_mcp\7zip\7za.exe

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
for %%e in ( .git .nojekyll release ) do if %1=="%%e" set flag=ON
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
cd %prjdir%
call ant javadoc -f javadoc.xml

echo "****************************************"
echo "javasphinx�̐���"
echo "****************************************"
cd %prjdir%
call javasphinx-apidoc -f -o sphinx/source/java src/com/tojc/minecraft/mod/ChatLoggerPlusPlugin/v1

echo "****************************************"
echo "�o�[�W������񏑂�����"
echo "****************************************"
cd %prjdir%
call python update_version.py

echo "****************************************"
echo "sphinx��HTML����"
echo "****************************************"
cd %prjdir%\sphinx
call make html

echo "****************************************"
echo "document�ɃR�s�["
echo "****************************************"
cd %prjdir%
xcopy %prjdir%\sphinx\build\html %prjdir%\document /e /c /h /r /y

echo "****************************************"
echo "document�̃t�@�C����git add -all"
echo "****************************************"
cd %prjdir%\document
git add --all

pause
