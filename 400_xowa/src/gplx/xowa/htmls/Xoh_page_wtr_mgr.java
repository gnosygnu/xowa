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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.wikis.pages.*;
public class Xoh_page_wtr_mgr implements Gfo_invk {
	private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(255), html_bfr = Bry_bfr_.Reset(Io_mgr.Len_mb);
	private Xoh_page_wtr_wkr edit_wtr, html_wtr, read_wtr;
	public Xoh_page_wtr_mgr(boolean html_capable) {
		this.html_capable = html_capable;
		this.read_wtr = new Xoh_page_wtr_wkr(this, Xopg_page_.Tid_read);
		this.edit_wtr = new Xoh_page_wtr_wkr(this, Xopg_page_.Tid_edit);
		this.html_wtr = new Xoh_page_wtr_wkr(this, Xopg_page_.Tid_html);
	}
	public boolean Html_capable() {return html_capable;} public Xoh_page_wtr_mgr Html_capable_(boolean v) {html_capable = v; return this;} private boolean html_capable;
	public byte[] Css_common_bry() {return css_common_bry;} private byte[] css_common_bry;
	public byte[] Css_wiki_bry() {return css_wiki_bry;} private byte[] css_wiki_bry;
	public byte[] Css_night_bry(boolean nightmode_enabled) {return nightmode_enabled ? css_night_bry : Bry_.Empty;} private byte[] css_night_bry;
	public boolean Scripting_enabled() {return scripting_enabled;} private boolean scripting_enabled;
	public Bry_fmtr Page_read_fmtr() {return page_read_fmtr;} private Bry_fmtr page_read_fmtr = Bry_fmtr.new_("", Fmtr_keys);
	public Bry_fmtr Page_edit_fmtr() {return page_edit_fmtr;} private Bry_fmtr page_edit_fmtr = Bry_fmtr.new_("", Fmtr_keys);
	public Bry_fmtr Page_html_fmtr() {return page_html_fmtr;} private Bry_fmtr page_html_fmtr = Bry_fmtr.new_("", Fmtr_keys);
	public byte[] Edit_rename_div_bry(Xoa_ttl ttl) {return div_edit_rename_fmtr.Bld_bry_many(tmp_bfr, ttl.Full_db());}
	public void Init_css_urls(Xoa_app app, String wiki_domain, Io_url css_common_url, Io_url css_wiki_url) {
		this.css_common_bry = css_common_url.To_http_file_bry();
		this.css_wiki_bry = css_wiki_url.To_http_file_bry();

		// xowa_night.css;
		Io_url css_night_url = app.Fsys_mgr().Url_finder().Find_by_css_or(wiki_domain, "xowa_night.css", String_.Ary("bin", "any", "xowa", "html", "css", "nightmode"), true);
		this.css_night_bry = Bry_.new_u8("<link rel=\"stylesheet\" href=\"" + css_night_url.To_http_file_str() + "\" type=\"text/css\">");
	}
	public void Init_(boolean v) {init = v;} private boolean init = true;
	public void Init_by_wiki(Xow_wiki wiki) {
		wiki.App().Cfg().Bind_many_wiki(this, wiki, Cfg__scripting_enabled);
	}
	public byte[] Gen(Xoae_page page, byte output_tid) {return Gen(page, Xoh_page_html_source_.Noop, output_tid);}
	public byte[] Gen(Xoae_page page, Xoh_page_html_source page_html_source, byte output_tid) {
		Xoh_page_wtr_wkr wtr = Wkr(output_tid);
		Xowe_wiki wiki = page.Wikie();
		if (init) {
			init = false;
			page_read_fmtr.Eval_mgr_(wiki.Eval_mgr());
			page_edit_fmtr.Eval_mgr_(wiki.Eval_mgr());
			page_html_fmtr.Eval_mgr_(wiki.Eval_mgr());
		}
		wtr.Write_page(html_bfr, page, wiki.Parser_mgr().Ctx(), page_html_source);
		return html_bfr.To_bry_and_clear_and_rls();
	}
	public Xoh_page_wtr_wkr Wkr(byte output_tid) {
		switch (output_tid) {
			case Xopg_page_.Tid_edit: return edit_wtr;
			case Xopg_page_.Tid_html: return html_wtr;
			case Xopg_page_.Tid_read: return read_wtr;
			default: throw Err_.new_unhandled(output_tid);
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_page_read_))						page_read_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_page_edit_))						page_edit_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_page_html_))						page_html_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_xowa_div_edit_rename_))			div_edit_rename_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Cfg__scripting_enabled))				scripting_enabled = m.ReadYn("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private Bry_fmtr div_edit_rename_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl
	(	"  <input id='xowa_edit_rename_box' width='120' height='20' />" 
	,	"  <a href='xowa-cmd:app.gui.main_win.page_edit_rename;' class='xowa_anchor_button' style='width:100px;max-width:1024px;'>" 
	,	"    Rename page" 
	,	"  </a>"
	,	"  <a href='/wiki/Special:MovePage?wpOldTitle=~{src_full_db}' class='xowa_anchor_button' style='width:100px;max-width:1024px;'>"
	,	"    Special:MovePage" 
	,	"  </a>"
	),	"src_full_db");
	public static final String Invk_page_read_ = "page_read_", Invk_page_edit_ = "page_edit_", Invk_page_html_ = "page_html_", Invk_xowa_div_edit_rename_ = "xowa_div_edit_rename_";
	private static final    String[] Fmtr_keys = new String[] 
	{ "app_root_dir", "app_version", "app_build_date", "xowa_mode_is_server"
	, "page_id", "page_ttl_full", "page_name", "page_heading", "page_modified_on_msg"
	, "html_css_common_path", "html_css_wiki_path", "html_css_night_tag", "xowa_head"
	, "page_lang_ltr", "page_indicators", "page_content_sub", "page_jumpto", "page_pgbnr", "page_body_cls", "html_content_editable"
	, "page_data", "page_langs"
	, "portal_div_footer"
	, "portal_div_personal", "portal_div_ns", "portal_div_view"
	, "portal_div_logo", "portal_div_home", "portal_div_xtn"
	, "portal_div_admin", "portal_div_wikis", "portal_sidebar"
	, "edit_div_rename", "edit_div_preview", "js_edit_toolbar"		
	};
	private static final String Cfg__scripting_enabled = "xowa.html.scripting.enabled";
}
/*
NOTE_1:xowa_anchor_button
. used for media (WP forces javascript with oggplayer. wanted "simpler" model)
. display:inline-block; must be set for centering to work; see USSR and anthem; (display:block; must be enabled in order for padding to work)
. text-align:center; forces img to be in center

General notes:
. contentSub div is needed; PAGE:en.w:Battle of Spotsylvania Court House
*/
