@echo off
set devdir=C:\dev_mcp\mcdev\forge\mcp
set topleveldomain=com
set prjdir=C:\dev_mcp\workspace\ChatLoggerPlus
set prjsrcdir=%prjdir%\src
set sevenzipexe=C:\dev_mcp\7zip\7za.exe

:step1
echo "****************************************"
echo "documentのクリーンアップ"
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
echo "sphinxのクリーンアップ"
echo "****************************************"
cd %prjdir%
rmdir /S /Q %prjdir%\sphinx\build
rmdir /S /Q %prjdir%\sphinx\source\java

echo "****************************************"
echo "javadocの生成"
echo "****************************************"
cd %prjdir%
call ant javadoc -f javadoc.xml

echo "****************************************"
echo "javasphinxの生成"
echo "****************************************"
cd %prjdir%
call javasphinx-apidoc -f -o sphinx/source/java src/com/tojc/minecraft/mod/ChatLoggerPlusPlugin/v1

echo "****************************************"
echo "バージョン情報書き換え"
echo "****************************************"
cd %prjdir%
call python update_version.py

echo "****************************************"
echo "sphinxのHTML生成"
echo "****************************************"
cd %prjdir%\sphinx
call make html

echo "****************************************"
echo "documentにコピー"
echo "****************************************"
cd %prjdir%
xcopy %prjdir%\sphinx\build\html %prjdir%\document /e /c /h /r /y

echo "****************************************"
echo "documentのファイルをgit add -all"
echo "****************************************"
cd %prjdir%\document
git add --all

pause
