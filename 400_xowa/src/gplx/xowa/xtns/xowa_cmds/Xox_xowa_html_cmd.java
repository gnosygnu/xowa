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
package gplx.xowa.xtns.xowa_cmds;
import gplx.langs.htmls.Gfh_tag_;
import gplx.types.basics.utls.BryLni;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.htmls.core.htmls.Xoh_html_wtr;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.Xop_root_tkn;
import gplx.xowa.parsers.htmls.Mwh_atr_itm;
import gplx.xowa.parsers.htmls.Mwh_atr_itm_owner2;
import gplx.xowa.parsers.xndes.Xop_xnde_tkn;
import gplx.xowa.wikis.pages.htmls.Xopg_html_data;
import gplx.xowa.wikis.pages.tags.Xopg_tag_itm;
import gplx.xowa.wikis.pages.tags.Xopg_tag_mgr;
import gplx.xowa.xtns.Xox_xnde;
import gplx.xowa.xtns.Xox_xnde_;
public class Xox_xowa_html_cmd implements Xox_xnde, Mwh_atr_itm_owner2 {
	private byte tag_pos = Xo_custom_html_pos_.Tid__head_end, tag_type = Xo_custom_html_type_.Tid__css_code;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, byte xatr_id) {
		switch (xatr_id) {
			case Xatr__pos:				tag_pos		= Xo_custom_html_pos_.To_tid(xatr.Val_as_bry()); break;
			case Xatr__type:			tag_type	= Xo_custom_html_type_.To_tid(xatr.Val_as_bry()); break;
			default:					throw ErrUtl.NewUnhandled(xatr_id);
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		if (!wiki.Sys_cfg().Xowa_cmd_enabled()) {	// not allowed; warn and exit
			wiki.Appe().Usr_dlg().Warn_many("", "", "xowa_html command only allowed in xowa_home");
			return;
		}

		// parse xnde for tag_name, tag_body
		Xox_xnde_.Parse_xatrs(wiki, this, xatr_hash, src, xnde);
		byte[] name = Xo_custom_html_type_.To_bry(tag_type);
		int raw_bgn = xnde.Tag_open_end(), raw_end = xnde.Tag_close_bgn();
		raw_bgn = BryFind.FindFwdWhileWs(src, raw_bgn, raw_end);
		raw_end = BryFind.FindBwdSkipWs(src, raw_end, raw_bgn);
		if (src[raw_bgn] == AsciiByte.Nl) ++raw_bgn;
		if (src[raw_bgn] == AsciiByte.Nl) ++raw_bgn;
		byte[] raw = BryLni.Mid(src, raw_bgn, raw_end);
		
		// add to custom tags
		Xopg_html_data html_data = ctx.Page().Html_data();
		Xopg_tag_mgr tag_mgr = tag_pos == Xo_custom_html_pos_.Tid__tail ? html_data.Custom_tail_tags() : html_data.Custom_head_tags();
		Xopg_tag_itm tag_itm = BryLni.Eq(name, Gfh_tag_.Bry__style) ? Xopg_tag_itm.New_css_code(raw) : Xopg_tag_itm.New_js_code(raw);
		tag_mgr.Add(tag_itm);
	}
	public void Xtn_write(BryWtr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {}

	public Xop_root_tkn Xtn_root()	{throw ErrUtl.NewUnimplemented("xowa_html_cmd.xtn_root should not be called");}
	public byte[] Xtn_html()		{throw ErrUtl.NewUnimplemented("xowa_html_cmd.xtn_html should not be called");}

	private static final byte Xatr__pos = 1, Xatr__type = 2;
	private static final Hash_adp_bry xatr_hash = Hash_adp_bry.ci_a7().Add_str_byte("pos", Xatr__pos).Add_str_byte("type", Xatr__type);
}
