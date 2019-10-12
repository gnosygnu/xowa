cd ./src/xowa_maven

# install files from xowa.git to local repo; note that these files aren't in any maven repository
echo "* XOWA: installing non-Maven repo artifacts"
$mvn install:install-file -Dfile=bin/any/java/jtidy/jtidy_xowa.jar -DgroupId=xowa -DartifactId=jtidy_xowa -Dversion=r938 -Dpackaging=jar
$mvn install:install-file -Dfile=bin/any/java/luaj/luaj_xowa.jar -DgroupId=xowa -DartifactId=luaj_xowa -Dversion=2.0 -Dpackaging=jar
$mvn install:install-file -Dfile=bin/any/java/lucene/5.3.0.drd/lucene-analyzers-common-5.3.0-mobile-2.jar -DgroupId=org.apache.lucene -DartifactId=lucene-analyzers-common-mobile -Dversion=5.3.0 -Dpackaging=jar
$mvn install:install-file -Dfile=bin/any/java/lucene/5.3.0.drd/lucene-core-5.3.0-mobile-2.jar -DgroupId=org.apache.lucene -DartifactId=lucene-core-mobile -Dversion=5.3.0 -Dpackaging=jar
$mvn install:install-file -Dfile=bin/any/java/lucene/5.3.0.drd/lucene-highlighter-5.3.0-mobile-2.jar -DgroupId=org.apache.lucene -DartifactId=lucene-highlighter-mobile -Dversion=5.3.0 -Dpackaging=jar
$mvn install:install-file -Dfile=bin/any/java/lucene/5.3.0.drd/lucene-memory-5.3.0-mobile-2.jar -DgroupId=org.apache.lucene -DartifactId=lucene-memory-mobile -Dversion=5.3.0 -Dpackaging=jar
$mvn install:install-file -Dfile=bin/any/java/lucene/5.3.0.drd/lucene-queries-5.3.0-mobile-2.jar -DgroupId=org.apache.lucene -DartifactId=lucene-queries-mobile -Dversion=5.3.0 -Dpackaging=jar
$mvn install:install-file -Dfile=bin/any/java/lucene/5.3.0.drd/lucene-queryparser-5.3.0-mobile-2.jar -DgroupId=org.apache.lucene -DartifactId=lucene-queryparser-mobile -Dversion=5.3.0 -Dpackaging=jar
$mvn install:install-file -Dfile=bin/any/java/jdbc/sqlite/sqlite-jdbc-3.18.0.jar -DgroupId=xowa -DartifactId=sqllite -Dversion=3.18.0 -Dpackaging=jar
$mvn install:install-file -Dfile=bin/any/java/getopt/utils-1.0.jar -DgroupId=xowa -DartifactId=getopt -Dversion=1.0.0 -Dpackaging=jar
$mvn install:install-file -Dfile=bin/any/java/jacksum/jacksum.jar -DgroupId=xowa -DartifactId=jacksum -Dversion=1.0.0 -Dpackaging=jar

echo "* XOWA: running"
$mvn clean
$mvn formatter:format
$mvn compile 
$mvn -Dtest="gplx.**" test
$ant -v -buildfile build.xml -Dplat_name=$plat_name

echo "* XOWA: launching xowa"
java -jar xowa_maven.jar
