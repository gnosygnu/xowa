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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.pages.*;
public class Xoh_page_wtr_mgr implements GfoInvkAble {
	private final Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
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
	public Bry_fmtr Page_read_fmtr() {return page_read_fmtr;} private Bry_fmtr page_read_fmtr = Bry_fmtr.new_("", Fmtr_keys);
	public Bry_fmtr Page_edit_fmtr() {return page_edit_fmtr;} private Bry_fmtr page_edit_fmtr = Bry_fmtr.new_("", Fmtr_keys);
	public Bry_fmtr Page_html_fmtr() {return page_html_fmtr;} private Bry_fmtr page_html_fmtr = Bry_fmtr.new_("", Fmtr_keys);
	public byte[] Edit_rename_div_bry(Xoa_ttl ttl) {return div_edit_rename_fmtr.Bld_bry_many(tmp_bfr, ttl.Full_db());}
	public void Init_css_urls(Io_url css_common_url, Io_url css_wiki_url) {
		this.css_common_bry = css_common_url.To_http_file_bry();
		this.css_wiki_bry = css_wiki_url.To_http_file_bry();
	}
	public void Init_(boolean v) {init = v;} private boolean init = true;
	public byte[] Gen(Xoae_page page, byte output_tid) {
		Xoh_page_wtr_wkr wtr = Wkr(output_tid);
		Xowe_wiki wiki = page.Wikie();
		if (init) {
			init = false;
			page_read_fmtr.Eval_mgr_(wiki.Eval_mgr());
			page_edit_fmtr.Eval_mgr_(wiki.Eval_mgr());
			page_html_fmtr.Eval_mgr_(wiki.Eval_mgr());
		}
		Bry_bfr tmp_bfr = Xoa_app_.Utl__bfr_mkr().Get_m001();
		byte[] bry = wtr.Write_page(page, wiki.Parser_mgr().Ctx(), tmp_bfr);
		tmp_bfr.Mkr_rls();
		return bry;
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
		else	return GfoInvkAble_.Rv_unhandled;
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
	private static final String[] Fmtr_keys = new String[] 
	{ "app_root_dir", "app_version", "app_build_date", "xowa_mode_is_server"
	, "page_id", "page_name", "page_title", "page_modified_on_msg"
	, "html_css_common_path", "html_css_wiki_path", "xowa_head"
	, "page_lang_ltr", "page_indicators", "page_content_sub", "page_jumpto", "page_body_cls", "html_content_editable"
	, "page_data", "page_langs"
	, "portal_div_personal", "portal_div_ns", "portal_div_view"
	, "portal_div_logo", "portal_div_home", "portal_div_xtn"
	, "portal_div_wikis", "portal_sidebar"
	, "edit_div_rename", "edit_div_preview", "js_edit_toolbar"		
	};
}
/*
NOTE_1:xowa_anchor_button
. used for media (WP forces javascript with oggplayer. wanted "simpler" model)
. display:inline-block; must be set for centering to work; see USSR and anthem; (display:block; must be enabled in order for padding to work)
. text-align:center; forces img to be in center

General notes:
. contentSub div is needed; PAGE:en.w:Battle of Spotsylvania Court House
*/
