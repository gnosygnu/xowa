== Overview ==
XOWA is an offline Wikipedia application. It is meant to be used with the dumps at https://dumps.wikimedia.org.

This is the initial draft of a README. Most of the instructions below are Windows-centric, and will be updated later to a more machine-agnostic form.

In addition, the enclosed build.xml assumes a Windows environment with an XOWA installation at C:\xowa. This too will be updated at a later date to be more portable.

== Requirements ==
XOWA is written in Java and requires 1.6 or above. It has seven dependencies:

# JUnit 4.8.2 (default version with Eclipse)
# [https://download.eclipse.org/eclipse/downloads/drops4/R-4.2.1-201209141800/#SWT SWT 4.2.1]: GUI library
# [https://sourceforge.net/projects/xowa/files/support/luaj/ luaj_xowa.jar]: Lua library
# [https://sourceforge.net/projects/jtidy/files/JTidy/r938/jtidy-r938.jar/download jtidy-r938.jar]: HTML tidy library
# [https://bitbucket.org/xerial/sqlite-jdbc/downloads sqlite-jdbc-3.7.15-SNAPSHOT-2.jar]: Database library
# [https://dev.mysql.com/downloads/connector/j/ mysql-connector-java-5.1.12-bin.jar]: Database library
# [https://jdbc.postgresql.org/download.html postgresql-8.4-701.jdbc4.jar]: Database library

Note that the last two libraries are not currently used in XOWA.

== Environment ==
The '''xowa_source.7z''' was built with Eclipse Indigo on Windows. For simplicity's sake, the remainder of these instructions will target Windows machines. There are no OS dependencies, so the same instructions apply to other OS's, except the paths will need to be updated.

There are no explicit dependencies on Eclipse, so the source code should be usable in other Java IDEs.

== Instructions ==
* Download '''xowa_app_windows_*''' and unzip to the proper directory. The following will be required:
** All files under {{fsysname|C:\xowa\user\anonymous\}}
** The main cfg file: {{fsysname|C:\xowa\xowa.gfs}}
** The swt.jar at {{fsysname|C:\xowa\bin\your_platform_name\swt\swt.jar}}
* Download '''xowa_source.7z'''
* Unzip it to {{fsysname|C:\xowa\dev\}}. You will have a folder called {{fsysname|C:\xowa\dev\400_xowa}} as well as many others.
* Move the swt.jar from {{fsysname|C:\xowa\bin\your_platform_name\swt\swt.jar}} to {{fsysname|C:\xowa\dev\150_gfui\lib\swt\swt.jar}}
* Launch Eclipse. Choose a workbench folder of {{fsysname|C:\xowa\dev}}
* Select all four projects. Do File -> Refresh.
* Right-click on 400_xowa in the Package Explorer. Select Debug As -> Java Application. Select Xowa_main. XOWA should launch.
* Right-click on 400_xowa in the Package Explorer. Select Debug As -> JUnit Test. All tests should pass.

== Eclipse-specific settings ==
This section documents specific project customizations that differ from the standard Eclipse defaults.

=== Project properties ===
Resource -> Text file encoding -> Other -> UTF-8

=== Preferences ===
These settings are available under Window -> Preferences

*Disable Spelling
:General -> Editors -> Text Editors -> Spelling
*Ignore Warnings
:Java -> Compiler -> Errors/Warnings
:: Annotations -> Unhandled token in '@SuppressWarnings'
:: Potential programming problems -> Serializable class without serialVersionUID
:: Generic Types -> Unnecessary generic type operation
:: Generic Types -> Usage of a raw type
:: Unnecessary Code -> Unused import

=== Configuration arguments ===
*Configuration arguments
:Run -> Debug Configurations -> Arguments
::<code>--root_dir C:\xowa\ --show_license n --show_args n</code>
