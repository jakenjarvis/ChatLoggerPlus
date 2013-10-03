@echo off
set devdir=C:\dev_mcp\mcdev\forge\mcp
set topleveldomain=com
set prjdir=C:\dev_mcp\workspace\ChatLoggerPlus
set prjsrcdir=%prjdir%\src

echo "%devdir%\src\minecraft\%topleveldomain%"
echo "%prjsrcdir%\%topleveldomain%"

cd "%devdir%"

mklink /j "%devdir%\src\minecraft\%topleveldomain%" "%prjsrcdir%\%topleveldomain%"

runtime\bin\python\python_mcp runtime\recompile.py %*
runtime\bin\python\python_mcp runtime\reobfuscate.py --srgnames %*

rmdir "%devdir%\src\minecraft\%topleveldomain%"

pause
