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
package gplx.xowa.langs.parsers;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import org.junit.*;
public class Xol_csv_parser_tst {
	Xol_csv_parser_fxt fxt = new Xol_csv_parser_fxt();
	@Before public void init()	{fxt.Clear();}
	@Test public void Save()			{fxt.Tst_save("a\r\n\t|d", "a\\r\\n\\t\\u007Cd");}
	@Test public void Load()			{fxt.Tst_load("a\r\n\t|d", "a\\r\\n\\t\\u007Cd");}
	@Test public void Save_backslash()	{fxt.Tst_save("a\\\\n", "a\\\\\\\\n");}
	@Test public void Load_backslash()	{fxt.Tst_load("a\\\\n", "a\\\\\\\\n");}
	@Test public void Utf()			{fxt.Tst_load(" ", "\\u00c2\\u00a0");}	// NOTE: 1st String is nbsp;
}
class Xol_csv_parser_fxt {
	Xol_csv_parser parser = Xol_csv_parser.Instance; BryWtr tmp_bfr = BryWtr.NewAndReset(255);
	public void Clear() {}
	public void Tst_save(String raw, String expd) {		
		parser.Save(tmp_bfr, BryUtl.NewU8(raw));
		GfoTstr.EqObj(expd, tmp_bfr.ToStrAndClear());
	}
	public void Tst_load(String expd, String raw_str) {
		byte[] raw = BryUtl.NewU8(raw_str);
		parser.Load(tmp_bfr, raw, 0, raw.length);
		GfoTstr.EqObj(expd, tmp_bfr.ToStrAndClear());
	}
}
