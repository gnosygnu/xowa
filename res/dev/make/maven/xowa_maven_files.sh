echo "* XOWA: regen xowa_maven root"
rm -rf ./src/xowa_maven
mkdir -p ./src/xowa_maven

echo "* XOWA: copying maven pom.xml and build.xml"
cp -rf ./pom.xml ./src/xowa_maven/
cp -rf ./build.xml ./src/xowa_maven/

echo "* XOWA: copy res to xowa_maven"
cp -R$verbose ./src/xowa/res             ./src/xowa_maven

echo "* XOWA: copy user / bin to runtime locations"
cp -R$verbose ./src/xowa_maven/res/user  ./src/xowa_maven
cp -R$verbose ./src/xowa_maven/res/bin   ./src/xowa_maven

echo "* XOWA: prepare source directories"
mkdir -p ./src/xowa_maven/src/main/java/gplx
mkdir -p ./src/xowa_maven/src/test/java/gplx

echo "* XOWA: create mavenize_xowa function"
mavenize_xowa () 
{
  echo "* XOWA: mavenizing $1"
  # main <- all non test files
  find $1 -depth -type f      \
    \( ! -name '*_tst.java'   \
    -a ! -name '*_fxt.java'   \
    -a ! -name '*_mok.java'   \
    -a ! -name '*Test.java'   \
    \) \
    -exec cp --parents -pr$verbose '{}' './src/xowa_maven/src/main/java/gplx/' ';'

  # main <- all test files
  find $1 -depth -type f   \
    \( -name '*_tst.java'  \
    -o -name '*_fxt.java'  \
    -o -name '*_mok.java'  \
    -o -name '*Test.java'  \
    \) \
    -exec cp --parents -pr$verbose '{}' './src/xowa_maven/src/test/java/gplx/' ';'
}

# package: baselib
# mavenize_xowa ./src/xowa/baselib/ # commenting out b/c of ClassName collision between baselib

# package: core
mavenize_xowa ./src/xowa/100_core/

# package: gfml
mavenize_xowa ./src/xowa/110_gfml/src_100_tkn/gplx/
mavenize_xowa ./src/xowa/110_gfml/src_200_type/gplx/
mavenize_xowa ./src/xowa/110_gfml/src_300_gdoc/gplx/
mavenize_xowa ./src/xowa/110_gfml/src_400_pragma/gplx/
mavenize_xowa ./src/xowa/110_gfml/src_500_build/gplx/
mavenize_xowa ./src/xowa/110_gfml/src_600_rdrWtr/gplx/
mavenize_xowa ./src/xowa/110_gfml/tst/gplx/

# package: 140_dbs
mavenize_xowa ./src/xowa/140_dbs/src/gplx/
# mavenize_xowa ./src/xowa/140_dbs/tst/gplx/ # tests in this folder require mysql and postgres server

# package: gfui
mavenize_xowa ./src/xowa/150_gfui/src/gplx/

# package: xowa
mavenize_xowa ./src/xowa/400_xowa/src/gplx/

# package: gflucene
mavenize_xowa ./src/xowa/gplx.gflucene/src/gplx/
