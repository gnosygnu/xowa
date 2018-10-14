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
package gplx.xowa.xtns.cldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.core.tests.*;
public class Cldr_name_loader_tst {
	private final    Cldr_name_loader_fxt fxt = new Cldr_name_loader_fxt();
	@Test   public void Load_file_is_null() {
		fxt.Init__file("CldrNamesEn.json", "{}");
		fxt.Test__load_file_is_null(Bool_.N, "En");
		fxt.Test__load_file_is_null(Bool_.N, "en"); // NOTE: scrib will pass "en", but earlier implementation was trying to read CldrNamesen.json which failed on LNX; DATE:2018-10-14
	}
}
class Cldr_name_loader_fxt {
	private static final    String Dir_name = "mem/CldrNames/";
	private final    Cldr_name_loader name_loader = new Cldr_name_loader(Io_url_.mem_dir_(Dir_name));

	public void Init__file(String fil_name, String txt) {
		Io_mgr.Instance.SaveFilStr(Io_url_.new_fil_(Dir_name + fil_name), txt);
	}
	public void Test__load_file_is_null(boolean expd, String lang_key) {
		Cldr_name_file name_file = name_loader.Load(lang_key);
		Gftest.Eq__bool(expd, name_file == null);
	}
}
