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
package gplx.xowa.xtns.wikias; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.htmls.*; import gplx.langs.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
public class Tabber_xnde implements Xox_xnde {
	private byte[] id;
	private Tabber_tab_itm[] tab_itms_ary;
	private static final    gplx.core.security.Hash_algo md5_hash = gplx.core.security.Hash_algo_.New__md5();
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_bgn);

		// split on "|-|"; EX: "A|-|B" -> tab_1='A'; tab_2='B'
		List_adp tab_itms_list = List_adp_.New();
		byte[] xnde_body = Xox_xnde_.Extract_body_or_null(src, xnde); if (xnde_body == null) return;
		this.id = Id_test == null ? md5_hash.Hash_bry_as_bry(xnde_body) : Id_test;
		byte[][] tab_itms = Bry_split_.Split(xnde_body, Spr__tab_itms);
		for (int i = 0; i < tab_itms.length; ++i) {
			byte[] tab_itm = tab_itms[i];
			tab_itm = Bry_.Trim(tab_itm);
			int tab_itm_len = tab_itm.length; if (tab_itm_len == 0) continue;

			// split on "="; EX: A=B -> tab_name='A'; tab_body = 'B'
			byte[] tab_head = null, tab_body = null;
			int eq_pos = Bry_find_.Find_fwd(tab_itm, Byte_ascii.Eq);
			if (eq_pos == Bry_find_.Not_found) {
				tab_head = tab_itm;
				tab_body = Bry_.Empty;
			}
			else {
				tab_head = Bry_.Mid(tab_itm, 0, eq_pos);
				tab_body = Bry_.Mid(tab_itm, eq_pos + 1, tab_itm_len);
				tab_body = Xop_parser_.Parse_text_to_html(wiki, ctx, ctx.Page(), ctx.Page().Ttl(), tab_body, false);
			}
			tab_itms_list.Add(new Tabber_tab_itm(Bool_.N, tab_head, tab_body));
		}
		tab_itms_ary = (Tabber_tab_itm[])tab_itms_list.To_ary_and_clear(Tabber_tab_itm.class);

		ctx.Page().Html_data().Head_mgr().Itm__tabber().Enabled_y_();
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		if (tab_itms_ary != null) Tabber_tab_itm.Write(bfr, id, tab_itms_ary);
	}

	public static byte[] Id_test;
	private static final    byte[] Spr__tab_itms = Bry_.new_a7("|-|");
}
