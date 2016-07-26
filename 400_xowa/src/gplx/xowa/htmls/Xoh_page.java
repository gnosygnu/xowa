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
import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.skins.*; import gplx.xowa.wikis.pages.lnkis.*; import gplx.xowa.wikis.pages.redirects.*;
import gplx.xowa.files.*;
import gplx.xowa.htmls.heads.*; import gplx.xowa.htmls.sections.*; import gplx.xowa.addons.htmls.tocs.*;
import gplx.xowa.wikis.pages.dbs.*; import gplx.xowa.wikis.pages.hdumps.*; import gplx.xowa.wikis.pages.htmls.*;
public class Xoh_page implements Xoa_page {
	// core
	public Xow_wiki					Wiki()				{return wiki;}			private Xow_wiki wiki;
	public Xoa_url					Url()				{return page_url;}		private Xoa_url page_url;
	public Xoa_ttl					Ttl()				{return page_ttl;}		private Xoa_ttl page_ttl;
	public Xopg_db_data				Db()				{return db;}			private final    Xopg_db_data db = new Xopg_db_data();
	public Xopg_redirect_mgr		Redirect()			{return redirect;}		private final    Xopg_redirect_mgr redirect = new Xopg_redirect_mgr();
	public Xopg_html_data			Html_data()			{return html;}			private final    Xopg_html_data html = new Xopg_html_data();
	public Xopg_hdump_data			Hdump_mgr()			{return hdump;}			private final    Xopg_hdump_data hdump = new Xopg_hdump_data();

	public void						Xtn_gallery_packed_exists_y_() {}
	public boolean					Xtn__timeline_exists() {return xtn__timeline_exists;} private boolean xtn__timeline_exists; public void Xtn__timeline_exists_y_() {xtn__timeline_exists = true;}
	public boolean					Xtn__gallery_exists() {return xtn__gallery_exists;} private boolean xtn__gallery_exists; public void Xtn__gallery_exists_y_() {xtn__gallery_exists = true;}

	// props
	public int						Page_id()			{return page_id;} private int page_id;
	public byte[]					Display_ttl()		{return display_ttl;} private byte[] display_ttl;
	public byte[]					Content_sub()		{return content_sub;} private byte[] content_sub;
	public byte[]					Sidebar_div()		{return sidebar_div;} private byte[] sidebar_div;
	public Xoh_section_mgr			Section_mgr()		{return section_mgr;} private final    Xoh_section_mgr section_mgr = new Xoh_section_mgr();
	public Xoh_img_mgr				Img_mgr()			{return img_mgr;} private Xoh_img_mgr img_mgr = new Xoh_img_mgr();
	public Xopg_module_mgr			Head_mgr()			{return head_mgr;} private Xopg_module_mgr head_mgr = new Xopg_module_mgr();

	// util
	public Xoa_page__commons_mgr	Commons_mgr()		{return commons_mgr;} private final    Xoa_page__commons_mgr commons_mgr = new Xoa_page__commons_mgr();
	public int						Exec_tid()			{return exec_tid;} private int exec_tid = Xof_exec_tid.Tid_wiki_page;
	public byte[]					Html_head_xtn()		{return html_head_xtn;} public void Html_head_xtn_(byte[] v) {html_head_xtn = v;} private byte[] html_head_xtn = Bry_.Empty;	// drd:web_browser
	public byte[]					Url_bry_safe()		{return page_url == null ? Bry_.Empty : page_url.To_bry(Bool_.Y, Bool_.Y);}
	public void Ctor_by_hview(Xow_wiki wiki, Xoa_url page_url, Xoa_ttl page_ttl, int page_id) {
		this.wiki = wiki; this.page_url = page_url; this.page_ttl = page_ttl; this.page_id = page_id; 
		this.Clear();
		html.Redlink_list().Disabled_(page_ttl.Ns().Id_is_module());	// never redlink in Module ns; particularly since Lua has multi-line comments for [[ ]]
		html.Toc_mgr().Init(wiki.Lang().Msg_mgr().Itm_by_id_or_null(gplx.xowa.langs.msgs.Xol_msg_itm_.Id_toc).Val(), page_url.Raw());
	}
	public Xoh_page Ctor_by_hdiff(Bry_bfr tmp_bfr, Xoae_page page) {
		this.wiki = page.Wiki(); this.page_url = page.Url(); this.page_ttl = page.Ttl(); this.page_id = page.Db().Page().Id();			

		db.Html().Html_bry_(page.Db().Html().Html_bry());

		Xopg_html_data html = page.Html_data();
		html.Init_by_page(page.Ttl());
		Xoh_head_mgr mod_mgr = html.Head_mgr();	
		head_mgr.Init(mod_mgr.Itm__mathjax().Enabled(), mod_mgr.Itm__popups().Bind_hover_area(), mod_mgr.Itm__gallery().Enabled(), mod_mgr.Itm__hiero().Enabled());
		this.display_ttl = html.Display_ttl();
		this.content_sub = html.Content_sub();
		this.sidebar_div = Xoh_page_.Save_sidebars(tmp_bfr, page, html);

		html.Toc_mgr().Init(Bry_.Empty, page_url.Page_bry());
		return this;
	}
	public void Ctor_by_db(int head_flag, byte[] display_ttl, byte[] content_sub, byte[] sidebar_div, int zip_tid, int hzip_tid, byte[] body) {
		head_mgr.Flag_(head_flag);
		this.display_ttl = display_ttl; this.content_sub = content_sub; this.sidebar_div = sidebar_div;
		db.Html().Html_bry_(body);
		db.Html().Zip_tids_(zip_tid, hzip_tid);
	}
	public void Clear() {
		redirect.Clear();
		html.Clear();
		hdump.Clear();
		db.Clear();

		display_ttl = content_sub = sidebar_div = Bry_.Empty;
		head_mgr.Clear(); commons_mgr.Clear();
		section_mgr.Clear(); img_mgr.Clear();
	}
	public static Xoh_page New_missing() {
		Xoh_page rv = new Xoh_page();
		rv.Db().Page().Exists_n_();
		return rv;
	}
}
