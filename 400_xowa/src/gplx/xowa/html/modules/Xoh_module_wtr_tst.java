/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.html.modules; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import org.junit.*;
import gplx.xowa.gui.*;
public class Xoh_module_wtr_tst {
	@Before public void init() {fxt.Clear();} private Xoh_module_wtr_fxt fxt = new Xoh_module_wtr_fxt();
	@Test   public void Globals_none() {
		Xoh_module_wtr wtr = fxt.Wtr();
		wtr.Write_js_head_global_bgn();
		wtr.Write_js_head_global_end();
		fxt.Test("");
	}
	@Test   public void Globals_some() {
		Xoh_module_wtr wtr = fxt.Wtr();
		wtr.Write_js_head_global_bgn();
		fxt.Exec_Write_js_global_ini_atr_val("key_1", "val_1");
		fxt.Exec_Write_js_global_ini_atr_val("key_2", "val_2");
		fxt.Exec_Write_js_global_ini_atr_val("key_3", "apos_'_1");
		wtr.Write_js_head_global_end();
		fxt.Test(String_.Concat_lines_nl_skip_last
		( ""
		, "var xowa_global_values = {"
		, "  'key_1' : 'val_1',"
		, "  'key_2' : 'val_2',"
		, "  'key_3' : 'apos_\\'_1',"
		, "}"
		));
	}
}
class Xoh_module_wtr_fxt {		
	private Bry_bfr bfr = Bry_bfr.reset_(255);
	public Xoh_module_wtr Wtr() {return wtr;} private Xoh_module_wtr wtr = new Xoh_module_wtr();
	public void Clear() {
		wtr.Init(bfr);
	}
	public void Exec_Write_js_global_ini_atr_val(String key, String val) {wtr.Write_js_global_ini_atr_val(Bry_.new_utf8_(key), Bry_.new_utf8_(val));}
	public void Test(String expd) {
		Tfds.Eq_str_lines(expd, bfr.XtoStrAndClear());
	}
//		public void Init_msg(byte[] key, String val) {
//			wiki.Msg_mgr().Get_or_make(key).Atrs_set(Bry_.new_ascii_(val), false, false);
//		}
//		public void Test_write(String expd) {
//			mgr.Write(bfr, fxt.App(), wiki, fxt.Page());
//			Tfds.Eq_str_lines(expd, bfr.XtoStrAndClear());
//		}
}
