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
package gplx.xowa.xtns; import gplx.*; import gplx.xowa.*;
import gplx.xowa.langs.bldrs.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*;
public abstract class Xox_mgr_base implements Xox_mgr {
	public Xox_mgr_base() {
		this.enabled = Enabled_default();
	}
	public abstract	byte[]		Xtn_key();
	public			boolean		Enabled() {return enabled;} private boolean enabled;
	@gplx.Virtual public boolean		Enabled_default() {return true;}
	public void					Enabled_y_() {enabled = true; enabled_manually = true;} public void Enabled_n_() {enabled = false; enabled_manually = true;}	// TEST:
	@gplx.Virtual public void			Enabled_(boolean v) {enabled = v;}
	public boolean					Enabled_manually() {return enabled_manually;} private boolean enabled_manually;
	public abstract	Xox_mgr		Xtn_clone_new();
	@gplx.Virtual public void			Xtn_ctor_by_app(Xoae_app app) {}
	@gplx.Virtual public void			Xtn_ctor_by_wiki(Xowe_wiki wiki) {}
	@gplx.Virtual public void			Xtn_init_by_app(Xoae_app app) {}
	@gplx.Virtual public void			Xtn_init_by_wiki(Xowe_wiki wiki) {}

	@gplx.Virtual public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled))			return Yn.To_str(enabled);
		else if	(ctx.Match(k, Invk_enabled_))			{this.Enabled_(m.ReadYn("v")); enabled_manually = true;}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_enabled = "enabled", Invk_enabled_ = "enabled_";

	public static void Xtn_write_escape(Xoae_app app, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde)	{Xtn_write_escape(app, bfr, src, xnde.Src_bgn(), xnde.Src_end());}
	public static void Xtn_write_escape(Xoae_app app, Bry_bfr bfr, byte[] src)						{Xtn_write_escape(app, bfr, src, 0, src.length);}
	public static void Xtn_write_escape(Xoae_app app, Bry_bfr bfr, byte[] src, int bgn, int end)	{Xoh_html_wtr_escaper.Escape(app.Parser_amp_mgr(), bfr, src, bgn, end, true, false);}
	public static void Xtn_write_escape_pre(Xoae_app app, Bry_bfr bfr, byte[] src, int bgn, int end){Xoh_html_wtr_escaper.Escape(app.Parser_amp_mgr(), bfr, src, bgn, end, false, false);}
	public static void Xtn_write_unsupported(Xoae_app app, Xop_ctx ctx, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde, byte parse_content_tid) {
		bfr.Add(Xowa_not_implemented);
		Xox_mgr_base.Xtn_write_escape(app, bfr, src, xnde.Tag_open_bgn(), xnde.Tag_open_end());
		if (xnde.CloseMode() != Xop_xnde_tkn.CloseMode_pair) return;	// inline node
		switch (parse_content_tid) {
			case Parse_content_tid_escape:
				Xox_mgr_base.Xtn_write_escape(app, bfr, src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
				break;
			case Parse_content_tid_html:
				bfr.Add(ctx.Wiki().Parser_mgr().Main().Parse_text_to_html(ctx, Bry_.Mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn())));
				break;
			case Parse_content_tid_none: default:
				break;
		}
		Xox_mgr_base.Xtn_write_escape(app, bfr, src, xnde.Tag_close_bgn(), xnde.Tag_close_end());
	}
	public static void Xtn_load_i18n(Xowe_wiki wiki, byte[] xtn_key) {
		Xoae_app app = wiki.Appe();
		Io_url url = app.Fsys_mgr().Bin_xtns_dir().GenSubFil_nest(String_.new_u8(xtn_key), "i18n", wiki.Lang().Key_str() + ".json");
		Xob_i18n_parser.Load_msgs(false, wiki.Lang(), url);
	}
	private static final    byte[] Xowa_not_implemented = Bry_.new_a7("XOWA does not support this extension: ");
	public static final byte Parse_content_tid_none = 0, Parse_content_tid_escape = 1, Parse_content_tid_html = 2;
}
