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
package gplx.langs.xmls; import gplx.*; import gplx.langs.*;
import org.junit.*;
import gplx.core.ios.*; import gplx.core.texts.*;
public class HierStrBldr_tst {
	@Before public void setup() {bldr = new HierStrBldr();}  HierStrBldr bldr;
	@Test  public void Hier0() {
		bldr.Ctor("/root/", "dir_{0}/", "idx_{0}.csv", "000");
		tst_MakeName( 0, "/root/idx_000.csv");
		tst_MakeName( 1, "/root/idx_001.csv");
		tst_MakeName(10, "/root/idx_010.csv");
	}
	@Test  public void Hier1() {
		bldr.Ctor("/root/", "dir_{0}/", "idx_{0}.csv", "000", 10);
		tst_MakeName( 0, "/root/dir_000/idx_000.csv");
		tst_MakeName( 1, "/root/dir_000/idx_001.csv");
		tst_MakeName(10, "/root/dir_010/idx_010.csv");
	}
	@Test  public void Hier2() {
		bldr.Ctor("/root/", "dir_{0}/", "idx_{0}.csv", "000", 5, 10);
		tst_MakeName(  0, "/root/dir_000/dir_000/idx_000.csv");
		tst_MakeName(  1, "/root/dir_000/dir_000/idx_001.csv");
		tst_MakeName( 10, "/root/dir_000/dir_010/idx_010.csv");
		tst_MakeName( 49, "/root/dir_000/dir_040/idx_049.csv");
		tst_MakeName( 50, "/root/dir_050/dir_050/idx_050.csv");
		tst_MakeName( 99, "/root/dir_050/dir_090/idx_099.csv");
		tst_MakeName(100, "/root/dir_100/dir_100/idx_100.csv");
		tst_MakeName(110, "/root/dir_100/dir_110/idx_110.csv");
	}
	void tst_MakeName(int val, String expd) {Tfds.Eq(expd, bldr.GenStrIdxOnly(val));}
}
