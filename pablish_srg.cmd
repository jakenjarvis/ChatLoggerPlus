@echo off
set devdir=C:\dev_mcp\mcdev\forge\mcp
set topleveldomain=com
set prjdir=C:\dev_mcp\workspace\ChatLoggerPlus
set prjsrcdir=%prjdir%\src
set sevenzipexe=C:\dev_mcp\7zip\7za.exe

echo "%devdir%\src\minecraft\%topleveldomain%"
echo "%prjsrcdir%\%topleveldomain%"

echo "****************************************"
echo "reobfフォルダのクリーンアップ"
echo "****************************************"
rmdir /S /Q "%devdir%\reobf"

echo "****************************************"
echo "srcへのシンボリックリンク作成"
echo "****************************************"
cd "%devdir%"
mklink /j "%devdir%\src\minecraft\%topleveldomain%" "%prjsrcdir%\%topleveldomain%"

echo "****************************************"
echo "recompile開始"
echo "****************************************"
runtime\bin\python\python_mcp runtime\recompile.py %*

echo "****************************************"
echo "reobfuscate開始"
echo "****************************************"
runtime\bin\python\python_mcp runtime\reobfuscate.py --srgnames %*

echo "****************************************"
echo "srcへのシンボリックリンク削除"
echo "****************************************"
rmdir "%devdir%\src\minecraft\%topleveldomain%"

echo "****************************************"
echo "releaseフォルダのクリーンアップ"
echo "****************************************"
rmdir /S /Q "%prjdir%\release"

echo "****************************************"
echo "releaseフォルダへ関連ファイルのコピー"
echo "****************************************"
xcopy "%devdir%\reobf\minecraft\com" "%prjdir%\release\com" /e /c /h /r /y /i
copy /b /y "%prjdir%\mcmod.info" "%prjdir%\release\mcmod.info"

echo "****************************************"
echo "リリース用zipファイル作成"
echo "****************************************"
cd "%prjdir%\release"
%sevenzipexe% a -tzip ChatLoggerPlusRelease.zip *.* -r -y

pause
