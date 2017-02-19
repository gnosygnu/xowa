## Overview
XOWA is an offline Wikipedia application that lets you run Wikipedia on your computer.

![XOWA showing Wikipedia's article on Wikipedia](http://xowa.sourceforge.net/wiki/file_screenshot_wikipedia_xowa.png)

## Features
* Run a complete copy of English Wikipedia from your computer.
* Display 5.0+ million articles in full HTML formatting.
* Show images within an article. Access 4.0+ million images using the offline image databases.
* Set up over 800+ other wikis including: English Wiktionary, English Wikisource, English Wikiquote, English Wikivoyage, Non-English wikis (French Wiktionary, German Wikisource, Dutch Wikivoyage), Wikidata, Wikimedia Commons and many more.
* Update your wiki whenever you want, using Wikimedia's database backups.
* Navigate between offline wikis. Click on "Look up this word in Wiktionary" and instantly view the page in Wiktionary.
* Edit articles to remove vandalism or errors.    
* Install to a flash memory card for portability to other machines.
* Run on Windows, Linux and Mac OS X. (Android Alpha available)
* Run as an HTTP server to browse from Firefox, Chrome or Safari
* View the HTML source for any wiki page.
* Search for any page by title using a Wikipedia-like Search box. Search across wikis as well.
* Browse pages by alphabetical order using Special:AllPages.
* Find a word on a page.
* Access a history of viewed pages.
* Bookmark your favorite pages.
* Preview articles by hovering over links
* Customize any one of over 50 options

## Requirements
XOWA is written in Java and requires 1.7 or above.

## Installation
* Download xowa_app_your_operating_system_name.zip from https://github.com/gnosygnu/xowa/releases/
* Unzip to any folder on your hard drive (or flash memory card)
* Run XOWA
   * On '''Windows''', double-click '''C:\xowa\xowa_64.exe'''
   * On '''Linux''', open a terminal and run <code>sh /home/your_user_name/xowa/xowa_linux_64.sh</code>
   * On '''OS X''', open a terminal and run <code>sh /Users/your_user_name/xowa/xowa_macosx_64.sh</code>

## Development Info
### Dependencies
XOWA has seven dependencies:

* JUnit 4.8.2 (default version with Eclipse)
* [SWT 4.5.1](http://download.eclipse.org/eclipse/downloads/drops4/R-4.5-201506032000/)
* [LuaJ](https://github.com/gnosygnu/luaj_xowa)
* [JTidy](https://github.com/gnosygnu/jtidy_xowa jtidy_xowa.jar)
* [SQLite JDBC](https://bitbucket.org/xerial/sqlite-jdbc/downloads)
* [MySQL JDBC](https://dev.mysql.com/downloads/connector/j/)
* [Postgres JDBC](https://jdbc.postgresql.org/download.html)

### Compilation instructions (ANT command-line)
#### Setup the XOWA app
* Download the latest XOWA app package for your operating system. For example, if you're on a 64-bit Linux system, "xowa_app_linux_64_v1.9.5.1.zip".
* Unzip the XOWA app package to a directory. For the sake of simplicity, these instructions assume this directory is "/xowa/"
* Review your directories. You should have the following:
    * An XOWA jar: "/xowa/xowa_linux_64.jar"
    * An XOWA "/bin/any/" directory with several jar files. For example, "/xowa/bin/any/java/apache/commons-compress-1.5.jar"
    * An XOWA "/bin/linux_64/" directory with an SWT jar: "/xowa/bin/linux_64/swt/swt.jar"

#### Setup the XOWA source
* Download the latest XOWA source archive. For example: "xowa_source_v1.9.5.1.7z"
* Unzip the source to "/xowa/dev". When you're done, you'll have a file called "/xowa/dev/build.xml" as well as others
    * NOTE: if you're not on a Linux 64-bit system, overwrite the swt jar at "/xowa/dev/150_gfui/lib/swt.jar" with the copy from your "/bin/OS" directory. For example, if you're on a 64 bit Windows system, replace "/xowa/dev/150_gfui/lib/swt.jar" with "/bin/windows_64/swt/swt.jar"

#### Run the ant file
* Open up a console, and run "ant -buildfile build.xml -Dplat_name=linux_64"

### IDE instructions (Eclipse)
#### Environment
The '''xowa_source.7z''' was built with Eclipse Indigo. There are no OS dependencies, nor are there dependencies on Eclipse.

#### Setup
* Follow the steps in these two sections from above:
    * Setup the XOWA app
    * Setup the XOWA source
* Launch Eclipse. Choose a workbench folder of "/xowa/dev"
* If the projects don't load, do File -> Import -> Existing Projects Into Workspace
* Select all projects. Do File -> Refresh.
* Right-click on 400_xowa in the Package Explorer. Select Debug As -> Java Application. Select Xowa_main. XOWA should launch.
* Right-click on 400_xowa in the Package Explorer. Select Debug As -> JUnit Test. All tests should pass.

#### Eclipse-specific settings
This section documents specific project customizations that differ from the standard Eclipse defaults.

##### Project properties
Resource -> Text file encoding -> Other -> UTF-8

##### Preferences
These settings are available under Window -> Preferences

* Disable Spelling
    * General -> Editors -> Text Editors -> Spelling
*Ignore Warnings
    * Java -> Compiler -> Errors/Warnings
        * Annotations -> Unhandled token in '@SuppressWarnings'
        * Potential programming problems -> Serializable class without serialVersionUID
        * Generic Types -> Unnecessary generic type operation (In Eclipse Luna: "Unchecked generic type operation")
        * Generic Types -> Usage of a raw type
        * Unnecessary Code -> Unused import

##### Configuration arguments
* Configuration arguments
    * Run -> Debug Configurations -> Arguments
        * <code>--root_dir /xowa/ --show_license n --show_args n</code>

## License
XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

See LICENSE.txt for more information.
