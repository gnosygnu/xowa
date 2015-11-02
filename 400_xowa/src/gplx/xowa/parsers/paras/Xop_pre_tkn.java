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
package gplx.xowa.parsers.paras; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.htmls.*;
public class Xop_pre_tkn extends Xop_tkn_itm_base {
	public Xop_pre_tkn(int bgn, int end, byte pre_tid, Xop_tkn_itm pre_bgn_tkn) {
		this.Tkn_ini_pos(false, bgn, end);
		this.pre_tid = pre_tid;
	}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_pre;}
	public byte Pre_tid() {return pre_tid;} private byte pre_tid = Pre_tid_null;
	@Override public void Html__write(Bry_bfr bfr, Xoh_html_wtr wtr, Xowe_wiki wiki, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, Xoh_html_wtr_cfg cfg, Xop_tkn_grp grp, int sub_idx, byte[] src) {
		switch (pre_tid) {
			case Xop_pre_tkn.Pre_tid_bgn: bfr.Add(Html_tag_.Pre_lhs); break;		// '<pre>'
			case Xop_pre_tkn.Pre_tid_end: bfr.Add(Bry__pre__rhs); break;			// '\n</pre>\n\n'
		}
	}
	public static final byte Pre_tid_null = 0, Pre_tid_bgn = 1, Pre_tid_end = 2;
	private static final byte[] Bry__pre__rhs = Bry_.new_a7("\n</pre>\n\n");
}
