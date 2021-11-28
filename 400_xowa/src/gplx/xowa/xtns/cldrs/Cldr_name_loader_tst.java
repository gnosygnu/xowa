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
	private final Cldr_name_loader_fxt fxt = new Cldr_name_loader_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Load_file_is_null() {
		fxt.Init__file("CldrNamesEn.json", "{}");
		fxt.Test__load_file_is_empty(Bool_.N, "En");
		fxt.Test__load_file_is_empty(Bool_.N, "en"); // NOTE: scrib will pass "en", but earlier implementation was trying to read CldrNamesen.json which failed on LNX; DATE:2018-10-14
	}
	@Test  public void Hyphen() {
		fxt.Init__file("CldrNamesEn_gb.json", "{}");
		fxt.Test__load_file_is_empty(Bool_.N, "en-gb");
		fxt.Test__load_file_is_empty(Bool_.Y, "en_gb");
	}
}
