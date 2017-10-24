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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.fmts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*; import gplx.core.intls.ucas.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*;	
public class Xoctg_fmt_ltr implements gplx.core.brys.Bfr_arg {	// "A", "B", "C cont."
	private final    Xoctg_fmt_itm_base itm_fmt;
	private Xoctg_catpage_grp grp;
	private byte[] msg__list_continues;
	private Uca_ltr_extractor ltr_extractor;
	public Xoctg_fmt_ltr(Xoctg_fmt_itm_base itm_fmt) {
		this.itm_fmt = itm_fmt;
	}
	public void Init_from_grp(Xow_wiki wiki, Xoctg_catpage_grp grp, Uca_ltr_extractor ltr_extractor) {
		this.grp = grp;
		this.msg__list_continues = wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_list_continues);
		this.ltr_extractor = ltr_extractor;
		itm_fmt.Init_from_ltr(wiki, grp, ltr_extractor);
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int itm_idx = 0;
		int itm_end = grp.Itms__len();
		int itms_len = itm_end - itm_idx; if (itms_len == 0) return;	// no items; exit

		int col_idx = 0;			// col idx; EX: 3 cols; idx = 0, 1, 2
		boolean start_new_col = true;
		byte[] ltr_prv = Bry_.Empty;

		// loop itms until no more itms
		while (itm_idx < itm_end) {
			Xoctg_catpage_itm itm = grp.Itms__get_at(itm_idx);

			// get ltr_head; EX: "C" or "C cont."
			byte[] itm_sortkey = itm.Sortkey_handle();
			// byte[] ltr_cur = gplx.core.intls.Utf8_.Get_char_at_pos_as_bry(itm_sortkey, 0);
			byte[] ltr_cur = ltr_extractor.Get_1st_ltr(itm_sortkey);
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
