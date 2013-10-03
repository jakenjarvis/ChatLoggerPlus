#!/usr/bin/python
# -*- coding: utf-8 -*-
# This file encoding UTF-8 no BOM. このファイルの文字コードはUTF-8 BOM無しです。
################################################################################
# Import
import sys
reload(sys)
sys.setdefaultencoding('utf-8')
import os
import codecs
import re
import shutil

def file_replace(path, enc, pattern, repl):
    tempname = path + "_temp"
    with codecs.open(path, "r", enc) as infile:
        with codecs.open(tempname, "w", enc) as outfile:
            for line in infile:
                line = re.sub(pattern, repl, line)
                outfile.write(line)

    infile = None
    outfile = None

    shutil.copyfile(tempname, path)
    os.remove(tempname)


def file_getValue(path, pattern, groupid):
    result = None
    r = re.compile(pattern)
    with codecs.open(path, "r", "utf-8") as infile:
        for line in infile:
            m = r.search(line)
            if m:
                result = m.group(groupid)
                break
    return result

if __name__  == "__main__":
    versionMinecraft = ur"" + file_getValue(ur"mcmod.info", ur"(?<=([\"]mcversion[\"]))(\s*[:]\s*)([\"])(\d{1,2}[.]\d{1,2}[.]\d{1,2})([\"])", 4)
    versionChatLoggerPlus = ur"" + file_getValue(ur"mcmod.info", ur"(?<=([\"]version[\"]))(\s*[:]\s*)([\"])(\d{1,2}[.]\d{1,2}[.]\d{1,2})([\"])", 4)

    print ur"versionMinecraft:" + versionMinecraft
    print ur"versionChatLoggerPlus:" + versionChatLoggerPlus

    #version = 'x.x.x'
    file_replace(ur"sphinx\source\conf.py", ur"sjis", ur"(?<=(version))(\s*[=]\s*)([\'])(\d{1,2}[.]\d{1,2}[.]\d{1,2})([\'])", ur"\g<2>\g<3>" + versionChatLoggerPlus + "\g<5>")

    #"version": "x.x.x",
    #file_replace(ur"mcmod.info", ur"utf-8", ur"(?<=([\"]version[\"]))(\s*[:]\s*)([\"])(\d{1,2}[.]\d{1,2}[.]\d{1,2})([\"])", ur"\g<2>\g<3>" + versionChatLoggerPlus + "\g<5>")
    #"mcversion": "x.x.x",
    #file_replace(ur"mcmod.info", ur"utf-8", ur"(?<=([\"]mcversion[\"]))(\s*[:]\s*)([\"])(\d{1,2}[.]\d{1,2}[.]\d{1,2})([\"])", ur"\g<2>\g<3>" + versionMinecraft + "\g<5>")

    #print file_getValue(ur"mcmod.info", ur"(?<=([\"]version[\"]))(\s*[:]\s*)([\"])(\d{1,2}[.]\d{1,2}[.]\d{1,2})([\"])", 4)


