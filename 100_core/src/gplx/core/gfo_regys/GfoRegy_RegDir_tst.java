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
package gplx.core.gfo_regys; import gplx.*; import gplx.core.*;
import org.junit.*;
public class GfoRegy_RegDir_tst {
	@Before public void setup() {
		regy = GfoRegy.new_();
		Io_mgr.Instance.InitEngine_mem();
		root = Io_url_.mem_dir_("mem/root");
	}	GfoRegy regy; Io_url root;
	@Test  public void Basic() {
		ini_fil("101_tsta.txt");
		ini_fil("102_tstb.txt");
		ini_fil("103_tstc.png");
		ini_fil("dir1", "104_tstd.txt");
		regy.RegDir(root, "*.txt", false, "_", ".");
		tst_Count(2);
		tst_Exists("tsta");
		tst_Exists("tstb");
	}
	@Test  public void Err_dupe() {
		ini_fil("101_tsta.txt");
		ini_fil("102_tsta.txt");
		try {regy.RegDir(root, "*.txt", false, "_", ".");}
		catch (Exception e) {Tfds.Err_has(e, GfoRegy.Err_Dupe); return;}
		Tfds.Fail_expdError();
	}
	@Test  public void Err_chopBgn() {
		ini_fil("123_");
		try {regy.RegDir(root, "*", false, "_", ".");}
		catch (Exception e) {Tfds.Err_has(e, GfoRegy.Err_ChopBgn); return;}
		Tfds.Fail_expdError();
	}
	@Test  public void Err_chopEnd() {
		ini_fil(".txt");
		try {regy.RegDir(root, "*.txt", false, "_", ".");}
		catch (Exception e) {Tfds.Err_has(e, GfoRegy.Err_ChopEnd); return;}
		Tfds.Fail_expdError();
	}
	void tst_Count(int expd) {Tfds.Eq(expd, regy.Count());}
	void tst_Exists(String expd) {
		GfoRegyItm itm = regy.FetchOrNull(expd);
		Tfds.Eq_nullNot(itm);
	}
	void ini_fil(String... nest) {Io_mgr.Instance.SaveFilStr(root.GenSubFil_nest(nest), "");}
}
