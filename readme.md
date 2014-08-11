## xowa: the xowa offline wiki application

### Summary
XOWA is an application for reading Wikipedia and other wikis offline.

### Environment
The xowa_source was built with Eclipse Indigo on Windows. For simplicity's sake, the remainder of these instructions will target Windows machines. There are no OS dependencies, so the same instructions apply to other OS's, except the paths will need to be updated.

There are no explicit dependencies on Eclipse, so the source code should be usable in other Java IDEs.

## Instructions
* Download '''xowa_app_windows_*''' from https://sourceforge.net/projects/xowa/files/ and unzip to the proper directory. The following will be required:
  * All files under C:\xowa\user\anonymous\
  * The main cfg file: C:\xowa\xowa.gfs
  * The swt.jar at C:\xowa\bin\wnt\swt\swt.jar
* Download the xowa repo
* Unzip it to C:\xowa\dev\. You will have a folder called C:\xowa\dev\400_xowa\ as well as many others.
* Move the swt.jar from C:\xowa\bin\wnt\swt\swt.jar to C:\xowa\dev\150_gfui\lib\swt\swt.jar
* Launch Eclipse. Choose a workbench folder of C:\xowa\dev
* Select all four projects. Do File -> Refresh.
* Right-click on 400_xowa in the Package Explorer. Select Debug As -> Java Application. Select Xowa_main. XOWA should launch.
* Right-click on 400_xowa in the Package Explorer. Select Debug As -> JUnit Test. All tests should pass.
 
