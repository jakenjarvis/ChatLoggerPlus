@echo off
set devdir=C:\dev_mcp\mcdev\forge\mcp
set topleveldomain=com
set prjdir=C:\dev_mcp\workspace\ChatLoggerPlus
set prjsrcdir=%prjdir%\src
set sevenzipexe=C:\dev_mcp\7zip\7za.exe

echo "%devdir%\src\minecraft\%topleveldomain%"
echo "%prjsrcdir%\%topleveldomain%"

echo "****************************************"
echo "reobf�t�H���_�̃N���[���A�b�v"
echo "****************************************"
rmdir /S /Q "%devdir%\reobf"

echo "****************************************"
echo "src�ւ̃V���{���b�N�����N�쐬"
echo "****************************************"
cd "%devdir%"
mklink /j "%devdir%\src\minecraft\%topleveldomain%" "%prjsrcdir%\%topleveldomain%"

echo "****************************************"
echo "recompile�J�n"
echo "****************************************"
runtime\bin\python\python_mcp runtime\recompile.py %*

echo "****************************************"
echo "reobfuscate�J�n"
echo "****************************************"
runtime\bin\python\python_mcp runtime\reobfuscate.py --srgnames %*

echo "****************************************"
echo "src�ւ̃V���{���b�N�����N�폜"
echo "****************************************"
rmdir "%devdir%\src\minecraft\%topleveldomain%"

echo "****************************************"
echo "release�t�H���_�̃N���[���A�b�v"
echo "****************************************"
rmdir /S /Q "%prjdir%\release"

echo "****************************************"
echo "release�t�H���_�֊֘A�t�@�C���̃R�s�["
echo "****************************************"
xcopy "%devdir%\reobf\minecraft\com" "%prjdir%\release\com" /e /c /h /r /y /i
copy /b /y "%prjdir%\mcmod.info" "%prjdir%\release\mcmod.info"

echo "****************************************"
echo "�����[�X�pzip�t�@�C���쐬"
echo "****************************************"
cd "%prjdir%\release"
%sevenzipexe% a -tzip ChatLoggerPlusRelease.zip *.* -r -y

pause
