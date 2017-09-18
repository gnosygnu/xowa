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
public class IoUrlInfo_alias_tst {
	IoUrlInfo_alias alias;
	@Test  public void MapWntToWnt() {
		Make("usr:\\", "D:\\usr\\");
		tst_Xto_api("usr:\\dir\\fil.txt", "D:\\usr\\dir\\fil.txt");
		tst_OwnerDir("usr:\\dir\\", "usr:\\");
		tst_OwnerDir("usr:\\", "");
		tst_NameOnly("usr:\\", "usr");
	}
	@Test  public void MapToLnx() {
		Make("usr:\\", "/home/");
		tst_Xto_api("usr:\\dir\\fil.txt", "/home/dir/fil.txt");
	}
	@Test  public void MapLnxToWnt() {
		Make("usr:/", "C:\\usr\\");
		tst_Xto_api("usr:/dir/fil.txt", "C:\\usr\\dir\\fil.txt");
	}
	@Test  public void WntToWnt() {
		Make("C:\\", "X:\\");
		tst_Xto_api("C:\\dir\\fil.txt", "X:\\dir\\fil.txt");
		tst_NameOnly("C:\\", "C");
	}
	@Test  public void WntToLnx() {
		Make("C:\\", "/home/");
		tst_Xto_api("C:\\dir\\fil.txt", "/home/dir/fil.txt");
	}
	@Test  public void LnxToWnt() {
		Make("/home/", "C:\\");
		tst_Xto_api("/home/dir/fil.txt", "C:\\dir\\fil.txt");
		tst_NameOnly("/home/", "home");
		tst_NameOnly("/", "root");
	}
	void tst_Xto_api(String raw, String expd) {Tfds.Eq(expd, alias.Xto_api(raw));}
	void tst_OwnerDir(String raw, String expd) {Tfds.Eq(expd, alias.OwnerDir(raw));}
	void tst_NameOnly(String raw, String expd) {Tfds.Eq(expd, alias.NameOnly(raw));}
	void Make(String srcDir, String trgDir) {
		alias = IoUrlInfo_alias.new_(srcDir, trgDir, IoEngine_.SysKey);
	}
}
