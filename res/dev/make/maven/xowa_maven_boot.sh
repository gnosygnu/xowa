# PLAT_NAME must be one of the following: windows_64,linux_64,macosx_64
export plat_name=windows_64

# ROOT_DIR should be created beforehand, and should be in "/" format
export root=c:/xowa_dev

# java settings should match your machine
export jdk=1.7
export JAVA_HOME=C:/000/100_bin/100_os/200_runtime/100_java/100_jdk_1_8_x64

# directories should be set to whatever exists on your machine
export ant=C:/000/100_bin/500_dev/110_java/200_apache_ant/bin/ant
export mvn=C:/000/100_bin/500_dev/110_java/210_apache_maven/bin/mvn

# set "verbose=y" or "verbose="
export verbose=

# run other shell scripts
sh xowa_maven_download.sh
sh xowa_maven_files.sh
sh xowa_maven_mvn.sh
