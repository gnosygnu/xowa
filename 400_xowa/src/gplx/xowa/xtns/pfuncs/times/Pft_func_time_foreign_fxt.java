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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
class Pft_func_time_foreign_fxt {
	private final Xop_fxt fxt = new Xop_fxt();
	public void Clear() {
		fxt.Reset();
		Tfds.Now_set(DateAdp_.new_(2012, 1, 2, 3, 4, 5, 6));
	}
	public void Term() {
		Tfds.Now_enabled_n_();
	}
	public Pft_func_time_foreign_fxt Init_msg(String key, String val) {
		Xol_msg_itm msg = fxt.Wiki().Msg_mgr().Get_or_make(Bry_.new_u8(key));
		msg.Atrs_set(Bry_.new_u8(val), false, false);
		return this;
	}
	public void Test_parse(String raw, String expd) {
		fxt.Test_parse_tmpl_str_test(raw, "{{test}}", expd);
	}
	public void Test_Roman(int v, String expd) {
		Bry_bfr bfr = Bry_bfr.new_(16);
		Pfxtp_roman.ToRoman(v, bfr);
		String actl = bfr.To_str_and_clear();
		Tfds.Eq(expd, actl);
	}
}
