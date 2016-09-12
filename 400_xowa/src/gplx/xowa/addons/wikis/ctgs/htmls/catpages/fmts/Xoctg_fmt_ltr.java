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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.fmts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*;
public class Xoctg_fmt_ltr implements gplx.core.brys.Bfr_arg {	// "A", "B", "C cont."
	private final    Xoctg_fmt_itm_base itm_fmt;
	private Xoctg_catpage_grp grp;
	private byte[] msg__list_continues;
	public Xoctg_fmt_ltr(Xoctg_fmt_itm_base itm_fmt) {
		this.itm_fmt = itm_fmt;
	}
	public void Init_from_grp(Xow_wiki wiki, Xoctg_catpage_grp grp) {
		this.grp = grp;
		this.msg__list_continues = wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_list_continues);
		itm_fmt.Init_from_ltr(wiki, grp);
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int itms_len = grp.Len(); if (itms_len == 0) return;	// no items; exit

		int itm_idx = grp.Bgn();	// itm idx; EX: idx=201 in len=500
		int col_idx = 0;			// col idx; EX: 3 cols; idx = 0, 1, 2
		boolean start_new_col = true;
		byte[] ltr_prv = Bry_.Empty;

		// loop itms until no more itms
		while (itm_idx < itms_len) {
			Xoctg_catpage_itm itm = grp.Itms()[itm_idx];

			// get ltr_head; EX: "C" or "C cont."
			byte[] itm_sortkey = itm.Sort_key();
			byte[] ltr_cur = gplx.core.intls.Utf8_.Get_char_at_pos_as_bry(itm_sortkey, 0);
			byte[] ltr_head = Bry_.Eq(ltr_prv, ltr_cur)
				? Bry_.Add(ltr_prv, Byte_ascii.Space_bry, msg__list_continues)	// new col uses same ltr as last itm in old col; add "cont."; EX: "C cont."
				: ltr_cur;	// else, just use ltr; EX: "C"				
			ltr_prv = ltr_cur;

			// start new column if needed
			if (start_new_col) {
				itm_fmt.Col_end_(itm_idx, col_idx++);	// set col_end; note col starts at itm_idx
				Fmt__col_bgn.Bld_many(bfr, 100 / Xoctg_fmt_itm_base.Cols_max);	// width:33%
			}

			// set ltr and idx
			itm_fmt.Set_ltr_and_bgn(ltr_prv, itm_idx);

			// loop until (a) end of ltr or (b) end of col
			Fmt__tbl.Bld_many(bfr, ltr_head, itm_fmt);
			itm_idx = itm_fmt.Loop_end_idx();
			start_new_col = itm_fmt.Loop_ends_at_col();

			// end column if needed
			if (start_new_col)
				Fmt__col_end.Bld_many(bfr);
		}
	}
	private static final    Bry_fmt
	 Fmt__tbl = Bry_fmt.Auto_nl_skip_last
	( ""
	, "          <h3>~{ltr_head}</h3>"	// EX: "A", "A cont."
	, "          <ul>~{itms}"
	, "          </ul>"
	)
	, Fmt__col_bgn = Bry_fmt.New("\n        <td style=\"width: ~{width}%;\">")
	, Fmt__col_end = Bry_fmt.New("\n        </td>");
}
