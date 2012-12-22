@echo off
set devdir=C:\dev_mcp\mcdev\forge\mcp
set topleveldomain=com
set prjsrcdir=C:\dev_mcp\workspace\ChatLoggerPlus\src

echo "%devdir%\src\minecraft\%topleveldomain%"
echo "%prjsrcdir%\%topleveldomain%"

cd "%devdir%"

mklink /j "%devdir%\src\minecraft\%topleveldomain%" "%prjsrcdir%\%topleveldomain%"

runtime\bin\python\python_mcp runtime\recompile.py %*
runtime\bin\python\python_mcp runtime\reobfuscate.py %*

rmdir "%devdir%\src\minecraft\%topleveldomain%"

pause
