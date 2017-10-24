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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Scrib_err_filter_mgr_tst {
	@Before public void init() {fxt.Clear();} private final Scrib_err_filter_mgr_fxt fxt = new Scrib_err_filter_mgr_fxt();
	@Test   public void Basic() {
		fxt.Exec_add(11, "Mod_1", "Fnc_1", "Err_11", "Comm_11");
		fxt.Exec_add(12, "Mod_1", "Fnc_2", "Err_12", "Comm_12");
		fxt.Exec_add(21, "Mod_2", "Fnc_1", "Err_21", "Comm_21");
		fxt.Test_match_y("Mod_1", "Fnc_2", "Err_12a");
		fxt.Test_match_y("Mod_2", "Fnc_1", "Err_21a");
		fxt.Test_match_n("Mod_1", "Fnc_1", "x");
		fxt.Test_match_n("Mod_1", "Fnc_3", "x");
		fxt.Test_match_n("Mod_3", "Fnc_1", "x");
		fxt.Test_print(String_.Concat_lines_nl
		( "0|11|Mod_1|Fnc_1|Err_11|Comm_11"
		, "1|12|Mod_1|Fnc_2|Err_12|Comm_12"
		, "1|21|Mod_2|Fnc_1|Err_21|Comm_21"
		));
	}
}
class Scrib_err_filter_mgr_fxt {
	private final Scrib_err_filter_mgr err_mgr = new Scrib_err_filter_mgr();
	public void Clear() {err_mgr.Clear();}
	public void Exec_add(int expd, String mod, String fnc, String err, String comment) {err_mgr.Add(expd, mod, fnc, err, comment);}
	public void Test_match_y(String mod, String fnc, String err) {Test_match(Bool_.Y, mod, fnc, err);}
	public void Test_match_n(String mod, String fnc, String err) {Test_match(Bool_.N, mod, fnc, err);}
	private void Test_match(boolean expd, String mod, String fnc, String err) {
		Tfds.Eq(expd, err_mgr.Match(mod, fnc, err));
	}
	public void Test_print(String expd) {
		Tfds.Eq_str_lines(expd, err_mgr.Print());
	}
}
