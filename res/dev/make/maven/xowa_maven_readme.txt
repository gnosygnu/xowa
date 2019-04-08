== Reading this file ==
To view this file with formatting, do the following:
* Go to https://en.wikipedia.org/w/index.php?title=Wikipedia:Sandbox&action=edit
* Paste this entire file into the box
* Press "Show Preview"

== Purpose ==
The xowa_maven.sh script will do the following:
* Download the latest source code of XOWA 
* Compile the code
* Run the unit tests
* Build a runnable jar

== Requirements ==
The shell script requires 4 applications:
* Git 2.21+
* Java 1.7+ JDK
* Maven 3.6+
* Apache Ant 1.9+

The shell script also requires an internet connection for the following:
* Download necessary binaries
* Download XOWA git repository
* Download Maven repositories

== Setup ==
Windows instructions are listed below. 
* Java will come installed on most Linux / Mac OS X system
* Git / Maven / Ant can downloaded / set-up

In addition, file paths in this README follow the Windows convention (C:\xowa\ instead of C:/xowa/)

However, the shell script uses the Unix convention (C:/xowa/)

Finally, note that directories are given for example purposes only. Feel free to change C:\xowa_dev to D:\whatever_you_want

=== Binaries ===
==== Git ====
===== Windows =====
* Download "Git for Windows Portable" from https://git-scm.com/download/win
* Unzip it to C:\xowa_dev\bin\git

==== Java 1.8 JDK ====
* Download "Java SE Development Kit 8u###" from https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
* Install it
* Confirm the JAVA_HOME variable exists
  * echo %JAVA_HOME%
  * EX: C:\xowa_dev\bin\java_jdk_1_8

==== Maven ====
* Download "apache-maven-#.#.#-bin.zip" https://maven.apache.org/download.cgi
* Unzip it to C:\xowa_dev\bin\maven

==== Apache Ant ====
* Download "#.#.##.zip" https://ant.apache.org/bindownload.cgi
* Unzip it to C:\xowa_dev\bin\ant

==== Minttyrc (Optional: for Ctrl+V) ====
* Open $HOME/.minttyrc
* Add $CtrlExchangeShift=yes
https://github.com/mintty/mintty/issues/602

=== Boot ===
* Copy xowa_maven_boot.txt to C:\xowa_dev\xowa_maven_boot.txt
* Rename it to xowa_maven_boot.sh
* Review all the exports and make sure they match
** The platform type (windows vs linux vs macosx)
** The directories on your machine

== Launch Git ==
* Run C:\xowa_dev\bin\git\git-bash.exe
* Run git config --global core.autocrlf false
  * autocrlf needs to be disabled on Windows boxes, or else, Git will change all .txt to \r\n . This will break xowa.gfs
* Run xowa_maven_boot.sh
