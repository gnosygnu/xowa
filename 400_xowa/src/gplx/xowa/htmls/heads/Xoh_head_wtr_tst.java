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
package gplx.xowa.htmls.heads;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import org.junit.*;
public class Xoh_head_wtr_tst {
	@Before public void init() {fxt.Clear();} private Xoh_head_wtr_fxt fxt = new Xoh_head_wtr_fxt();
	@Test public void Globals_none() {
		Xoh_head_wtr wtr = fxt.Wtr();
		wtr.Write_js_head_global_bgn();
		wtr.Write_js_head_global_end();
		fxt.Test("");
	}
	@Test public void Globals_some() {
		Xoh_head_wtr wtr = fxt.Wtr();
		wtr.Write_js_head_global_bgn();
		fxt.Exec_Write_js_global_ini_atr_val("key_1", "val_1");
		fxt.Exec_Write_js_global_ini_atr_val("key_2", "val_2");
		fxt.Exec_Write_js_global_ini_atr_val("key_3", "apos_'_1");
		wtr.Write_js_head_global_end();
		fxt.Test(StringUtl.ConcatLinesNlSkipLast
		( ""
		, "var xowa_global_values = {"
		, "  'key_1' : 'val_1',"
		, "  'key_2' : 'val_2',"
		, "  'key_3' : 'apos_\\'_1',"
		, "}"
		));
	}
}
class Xoh_head_wtr_fxt {		
	private BryWtr bfr = BryWtr.NewAndReset(255);
	public Xoh_head_wtr Wtr() {return wtr;} private Xoh_head_wtr wtr = new Xoh_head_wtr();
	public void Clear() {
		wtr.Init(bfr);
	}
	public void Exec_Write_js_global_ini_atr_val(String key, String val) {wtr.Write_js_global_ini_atr_val(BryUtl.NewU8(key), BryUtl.NewU8(val));}
	public void Test(String expd) {
		GfoTstr.EqLines(expd, bfr.ToStrAndClear());
	}
//		public void Init_msg(byte[] key, String val) {
//			wiki.Msg_mgr().Get_or_make(key).Atrs_set(Bry_.new_a7(val), false, false);
//		}
//		public void Test_write(String expd) {
//			mgr.Write(bfr, fxt.App(), wiki, fxt.Page());
//			Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
//		}
}
