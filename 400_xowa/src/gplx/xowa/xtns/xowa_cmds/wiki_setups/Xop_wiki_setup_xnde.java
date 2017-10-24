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
package gplx.xowa.xtns.xowa_cmds.wiki_setups; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.xowa_cmds.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.tags.*; import gplx.xowa.wikis.pages.htmls.*;
public class Xop_wiki_setup_xnde implements Xox_xnde, Mwh_atr_itm_owner2 {
	private byte[] language;
	private byte[][] wikis_ary;
	private Xop_root_tkn root;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, byte xatr_id) {
		switch (xatr_id) {
			case Xatr__language:		language		= xatr.Val_as_bry(); break;
			case Xatr__wikis:			wikis_ary		= Bry_split_.Split(xatr.Val_as_bry(), Byte_ascii.Pipe_bry); break;
			default:					Gfo_usr_dlg_.Instance.Warn_many("", "", "unhandled xnde atr; key=~{0}", xatr.Key_bry()); break;
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		if (!wiki.Sys_cfg().Xowa_cmd_enabled()) {	// not allowed; warn and exit
			wiki.Appe().Usr_dlg().Warn_many("", "", "xowa_wiki_setup only allowed in xowa_home");
			return;
		}

		Xox_xnde_.Parse_xatrs(wiki, this, xatr_hash, src, xnde);
		this.root = new Xop_wiki_setup_mgr().Write(wiki.Appe(), wiki, ctx, ctx.Page(), language, wikis_ary);
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		html_wtr.Write_tkn_to_html(bfr, ctx, hctx, root.Root_src(), xnde, Xoh_html_wtr.Sub_idx_null, root);
	}

	public Xop_root_tkn Xtn_root()	{throw Err_.new_unimplemented_w_msg("xowa_wiki_setup.xtn_root should not be called");}
	public byte[] Xtn_html()		{throw Err_.new_unimplemented_w_msg("xowa_wiki_setup.xtn_html should not be called");}

	private static final byte Xatr__language = 1, Xatr__wikis = 2;
	private static final    Hash_adp_bry xatr_hash = Hash_adp_bry.ci_a7().Add_str_byte("language", Xatr__language).Add_str_byte("wikis", Xatr__wikis);
}
