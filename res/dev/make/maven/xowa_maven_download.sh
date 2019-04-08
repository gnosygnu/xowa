echo * XOWA: downloading latest xowa
cd $root
rm -rf ./src
mkdir src
cd src
git clone https://github.com/gnosygnu/xowa.git
cd ..

echo * XOWA: regen xowa_maven root
rm -rf ./src/xowa_maven
mkdir -p ./src/xowa_maven

echo * XOWA: copying maven files to root
cp -rf ./src/xowa/res/dev/make/maven/*.xowa_maven_download.sh  ./
cp -rf ./src/xowa/res/dev/make/maven/*.xowa_maven_files.sh     ./
cp -rf ./src/xowa/res/dev/make/maven/*.xowa_maven_mvn.sh       ./
cp -rf ./src/xowa/res/dev/make/maven/*.txt ./

echo * XOWA: copying maven pom.xml and build.xml
cp -rf ./src/xowa/res/dev/make/maven/*.xml ./src/xowa_maven
