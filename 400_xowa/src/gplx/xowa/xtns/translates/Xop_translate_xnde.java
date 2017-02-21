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
package gplx.xowa.xtns.translates; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
public class Xop_translate_xnde implements Xox_xnde {
	public Xop_root_tkn Xtn_root() {return xtn_root;} private Xop_root_tkn xtn_root;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		byte[] translate_src = Bry_.Mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
		translate_src = Bry_.Trim(translate_src, 0, translate_src.length);
		Xop_ctx sub_ctx = Xop_ctx.New__sub__reuse_page(ctx);
		xtn_root = wiki.Parser_mgr().Main().Parse_text_to_wdom_old_ctx(sub_ctx, translate_src, true);
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		html_wtr.Write_tkn_to_html(bfr, ctx, hctx, xtn_root.Root_src(), xnde, Xoh_html_wtr.Sub_idx_null, xtn_root);
	}
}
