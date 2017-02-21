/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios; import gplx.*; import gplx.core.*;
import org.junit.*;
public class IoUrl_wnt_tst {
	IoUrlFxt fx = IoUrlFxt.new_();
	@Test  public void Raw() {
		fx.tst_Xto_gplx(Io_url_.wnt_fil_("C:\\dir\\fil.txt"), "C:\\dir\\fil.txt");
		fx.tst_Xto_gplx(Io_url_.wnt_dir_("C:\\dir\\"), "C:\\dir\\");
		fx.tst_Xto_gplx(Io_url_.wnt_dir_("C:\\dir")	, "C:\\dir\\"); // add \
	}
	@Test  public void Xto_api() {
		fx.tst_Xto_api(Io_url_.wnt_fil_("C:\\fil.txt"), "C:\\fil.txt");
		fx.tst_Xto_api(Io_url_.wnt_dir_("C:\\dir\\"), "C:\\dir");	// del \
		fx.tst_Xto_api(Io_url_.wnt_dir_("C:"), "C:");
	}
	@Test  public void OwnerRoot() {
		fx.tst_OwnerRoot(Io_url_.wnt_dir_("C:\\dir")		, "C:\\");
		fx.tst_OwnerRoot(Io_url_.wnt_dir_("C:\\fil.png")	, "C:\\");
		fx.tst_OwnerRoot(Io_url_.wnt_dir_("C:")			, "C:\\");
	}
	@Test  public void IsDir() {
		fx.tst_IsDir(Io_url_.wnt_dir_("C:\\dir\\"), true);
		fx.tst_IsDir(Io_url_.wnt_fil_("C:\\dir"), false);
		fx.tst_IsDir(Io_url_.wnt_fil_("C:\\fil.txt"), false);
	}
	@Test  public void OwnerDir() {
		fx.tst_OwnerDir(Io_url_.wnt_dir_("C:\\dir\\sub1"), Io_url_.wnt_dir_("C:\\dir"));
		fx.tst_OwnerDir(Io_url_.wnt_fil_("C:\\fil.txt"), Io_url_.wnt_dir_("C:"));
		fx.tst_OwnerDir(Io_url_.wnt_dir_("C:"), Io_url_.Empty);
//			fx.tst_OwnerDir(Io_url_.wnt_fil_("press enter to select this folder"), Io_url_.Empty);
	}
	@Test  public void NameAndExt() {
		fx.tst_NameAndExt(Io_url_.wnt_fil_("C:\\fil.txt"), "fil.txt");
		fx.tst_NameAndExt(Io_url_.wnt_dir_("C:\\dir"), "dir\\");
	}
	@Test  public void NameOnly() {
		fx.tst_NameOnly(Io_url_.wnt_fil_("C:\\fil.txt"), "fil");
		fx.tst_NameOnly(Io_url_.wnt_dir_("C:\\dir"), "dir");
		fx.tst_NameOnly(Io_url_.wnt_dir_("C:"), "C");
	}
	@Test  public void Ext() {
		fx.tst_Ext(Io_url_.wnt_fil_("C:\\fil.txt"), ".txt"); // fil
		fx.tst_Ext(Io_url_.wnt_fil_("C:\\fil.multiple.txt"), ".txt"); // multiple ext
		fx.tst_Ext(Io_url_.wnt_fil_("C:\\fil"), ""); // no ext
		fx.tst_Ext(Io_url_.wnt_dir_("C:\\dir"), "\\"); // dir
	}
	@Test  public void GenSubDir_nest() {
		fx.tst_GenSubDir_nest(Io_url_.wnt_dir_("C:"), fx.ary_("dir1", "sub1"), Io_url_.wnt_dir_("C:\\dir1\\sub1"));
	}
	@Test  public void GenNewExt() {
		fx.tst_GenNewExt(Io_url_.wnt_fil_("C:\\fil.gif"), ".png", Io_url_.wnt_fil_("C:\\fil.png")); // basic
		fx.tst_GenNewExt(Io_url_.wnt_fil_("C:\\fil.tst.gif"), ".png", Io_url_.wnt_fil_("C:\\fil.tst.png")); // last in multiple dotted
	}
	@Test  public void GenRelUrl_orEmpty() {
		fx.tst_GenRelUrl_orEmpty(Io_url_.wnt_fil_("C:\\root\\fil.txt")		, Io_url_.wnt_dir_("C:\\root")	, "fil.txt"); // fil
		fx.tst_GenRelUrl_orEmpty(Io_url_.wnt_dir_("C:\\root\\dir")			, Io_url_.wnt_dir_("C:\\root")	, "dir\\"); // dir
		fx.tst_GenRelUrl_orEmpty(Io_url_.wnt_fil_("C:\\root\\dir\\fil.txt")	, Io_url_.wnt_dir_("C:\\root")	, "dir\\fil.txt"); // fil: nested1			
		fx.tst_GenRelUrl_orEmpty(Io_url_.wnt_fil_("C:\\root\\dir\\fil.txt")	, Io_url_.wnt_dir_("C:")			, "root\\dir\\fil.txt"); // fil: nested2
	}
	@Test  public void GenParallel() {
		fx.tst_GenParallel(Io_url_.wnt_fil_("C:\\root1\\fil.txt"), Io_url_.wnt_dir_("C:\\root1"), Io_url_.wnt_dir_("D:\\root2"), Io_url_.wnt_fil_("D:\\root2\\fil.txt"));
		fx.tst_GenParallel(Io_url_.wnt_dir_("C:\\root1\\dir")	, Io_url_.wnt_dir_("C:\\root1"), Io_url_.wnt_dir_("D:\\root2"), Io_url_.wnt_dir_("D:\\root2\\dir"));
	}
}
class IoUrlFxt {
	public void tst_Xto_api(Io_url url, String expd) {Tfds.Eq(expd, url.Xto_api());}
	public void tst_OwnerRoot(Io_url url, String expd) {Tfds.Eq(expd, url.OwnerRoot().Raw());}
	public void tst_XtoNames(Io_url url, String... expdAry) {Tfds.Eq_ary(expdAry, url.XtoNames().To_str_ary());}
	public void tst_NameAndExt(Io_url url, String expd) {Tfds.Eq(expd, url.NameAndExt());}
	public void tst_Xto_gplx(Io_url url, String expd) {Tfds.Eq(expd, url.Raw());}
	public void tst_IsDir(Io_url url, boolean expd) {Tfds.Eq(expd, url.Type_dir());}
	public void tst_OwnerDir(Io_url url, Io_url expd) {Tfds.Eq_url(expd, url.OwnerDir());}
	public void tst_NameOnly(Io_url url, String expd) {Tfds.Eq(expd, url.NameOnly());}
	public void tst_Ext(Io_url url, String expd) {Tfds.Eq(expd, url.Ext());}
	public void tst_GenSubDir_nest(Io_url rootDir, String[] parts, Io_url expd) {Tfds.Eq(expd, rootDir.GenSubDir_nest(parts));}
	public void tst_GenNewExt(Io_url url, String ext, Io_url expd) {Tfds.Eq_url(expd, url.GenNewExt(ext));}
	public void tst_GenRelUrl_orEmpty(Io_url url, Io_url rootDir, String expd) {Tfds.Eq(expd, url.GenRelUrl_orEmpty(rootDir));}
	public void tst_GenParallel(Io_url url, Io_url oldRoot, Io_url newRoot, Io_url expd) {Tfds.Eq_url(expd, url.GenParallel(oldRoot, newRoot));}

	public String[] ary_(String... ary) {return String_.Ary(ary);}
	public static IoUrlFxt new_() {return new IoUrlFxt();}	IoUrlFxt() {}
}
