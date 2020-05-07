# == OVERVIEW ==
# * REQUIREMENTS
# ** Java JDK 1.7 (or higher): https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
# ** Apache Ant 1.9.13 (or higher): https://ant.apache.org/bindownload.cgi
# ** A ROOT_DIR directory on your file-system
# ** (Windows) cygwin: https://www.cygwin.com/
#
# * PROCESS
# ** Copy-paste this file to a plain-text file; EX: /cygdrive/c/xowa_dev/xowa_get_and_make.sh
# ** Adjust these environment variables to your system: PLAT_NAME, ROOT_DIR, ANT_BINARY, JAVA_HOME
# ** cd to your ROOT_DIR
# ** Run the file using "sh xowa_get_and_make.sh"
# ** Run the xowa_dev.jar
# *** (Windows)  java -jar xowa_dev.jar
# *** (Linux)    SWT_GTK3=0 && java -jar xowa_dev.jar
# *** (Mac OS X) java -Xmx256m -d64 -XstartOnFirstThread -jar xowa_dev.jar

# == ENVIRONMENT VARIABLES ==
JAVA_JDK_VERSION=1.8
# PLAT_NAME must be one of the following: windows_64,linux_64,macosx_64
PLAT_NAME=windows_64
# ROOT_DIR should be created beforehand, and should be in "/" format
ROOT_DIR=c:/xowa_dev
# these directories MUST be changed to wherever they exist on your system
ANT_BINARY=/cygdrive/c/dev/apache_ant/bin/ant
export JAVA_HOME=/cygdrive/c/dev/java/jdk_1_8_x64

# == REMOVE PREVIOUS ARTIFACTS ==
rm -rf ./master
rm -rf ./src
rm -rf ./dev
rm -rf ./bin
rm -rf ./file
rm -rf ./user
rm -rf ./wiki
rm -rf ./xowa_dev.jar

# == DOWNLOAD XOWA SOURCE ==
# get src and unzip
curl -LO https://api.github.com/repos/gnosygnu/xowa/tarball/master
mkdir src
# unzip; note that "strip 1" removes container folder EX: gnosygnu-123456
tar -zxf master -C ./src --strip 1

# == REORGANIZE DOWNLOAD FILES ==
# move /res/ to /root/ folder
mv src/res/* .
# needed for Windows only (cygwin defaults to non-execute permissions)
chmod -R 755 ./bin/$PLAT_NAME/xulrunner

# remove test files
find ./src -type f -name '*Test.java' -delete
find ./src -type f -name '*_tst.java' -delete
find ./src -type f -name '*_fxt.java' -delete
find ./src -type f -name '*_mok.java' -delete

# == BUILD XOWA ==
$ANT_BINARY -v -buildfile ./dev/make/ant/xowa_compile.xml  -Droot_dir=$ROOT_DIR -Djdk=$JAVA_JDK_VERSION 
$ANT_BINARY -v -buildfile ./dev/make/ant/xowa_make_jar.xml -Droot_dir=$ROOT_DIR -Dplat_name=$PLAT_NAME -Dxowa_jar_path=$ROOT_DIR/xowa_dev.jar
