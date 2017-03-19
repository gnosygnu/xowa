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
import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.skins.*; import gplx.xowa.wikis.pages.lnkis.*; import gplx.xowa.wikis.pages.redirects.*;
import gplx.xowa.files.*;
import gplx.xowa.langs.*;
import gplx.xowa.htmls.heads.*; import gplx.xowa.htmls.sections.*; import gplx.xowa.addons.htmls.tocs.*;
import gplx.xowa.wikis.pages.dbs.*; import gplx.xowa.wikis.pages.hdumps.*; import gplx.xowa.wikis.pages.htmls.*; import gplx.xowa.wikis.pages.wtxts.*;
public class Xoh_page implements Xoa_page {
	// core
	public Xow_wiki					Wiki()				{return wiki;}				private Xow_wiki wiki;
	public Xoa_url					Url()				{return page_url;}			private Xoa_url page_url;
	public Xoa_ttl					Ttl()				{return page_ttl;}			private Xoa_ttl page_ttl;
	public Xopg_db_data				Db()				{return db;}				private final    Xopg_db_data db = new Xopg_db_data();
	public Xopg_redirect_mgr		Redirect_trail()	{return redirect;}			private final    Xopg_redirect_mgr redirect = new Xopg_redirect_mgr();
	public Xopg_html_data			Html_data()			{return html;}				private final    Xopg_html_data html = new Xopg_html_data();
	public Xopg_wtxt_data			Wtxt()				{return wtxt;}				private final    Xopg_wtxt_data wtxt = new Xopg_wtxt_data();
	public Xopg_hdump_data			Hdump_mgr()			{return hdump;}				private final    Xopg_hdump_data hdump = new Xopg_hdump_data();
	public Xol_lang_itm				Lang()				{return lang;}				private Xol_lang_itm lang;
	private Guid_adp page_guid;
	public Guid_adp Page_guid() {
		if (page_guid == null) {
			page_guid = Guid_adp_.New();
		}
		return page_guid;
	}

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
	public byte[]					Url_bry_safe()		{return Xoa_page_.Url_bry_safe(page_url, wiki, page_ttl);}
	public void Ctor_by_hview(Xow_wiki wiki, Xoa_url page_url, Xoa_ttl page_ttl, int page_id) {
		this.wiki = wiki; this.page_url = page_url; this.page_ttl = page_ttl; this.page_id = page_id; 
		this.lang = wiki.Lang();
		this.Clear();
		html.Redlink_list().Disabled_(page_ttl.Ns().Id_is_module());	// never redlink in Module ns; particularly since Lua has multi-line comments for [[ ]]
		html.Toc_mgr().Init(gplx.xowa.htmls.core.htmls.tidy.Xow_tidy_mgr_interface_.Noop, wiki.Lang().Msg_mgr().Itm_by_id_or_null(gplx.xowa.langs.msgs.Xol_msg_itm_.Id_toc).Val(), page_url.Raw());
	}
	public Xoh_page Ctor_by_hdiff(Bry_bfr tmp_bfr, Xoae_page page, byte[] toc_label) {
		this.wiki = page.Wiki(); this.page_url = page.Url(); this.page_ttl = page.Ttl(); this.page_id = page.Db().Page().Id();			
		this.lang = wiki.Lang();

		db.Html().Html_bry_(page.Db().Html().Html_bry());

		Xopg_html_data html = page.Html_data();
		html.Init_by_page(page.Ttl());
		Xoh_head_mgr mod_mgr = html.Head_mgr();	
		head_mgr.Init(mod_mgr.Itm__mathjax().Enabled(), mod_mgr.Itm__popups().Bind_hover_area(), mod_mgr.Itm__gallery().Enabled(), mod_mgr.Itm__hiero().Enabled());
		this.display_ttl = html.Display_ttl();
		this.content_sub = html.Content_sub();
		this.sidebar_div = Xoh_page_.Save_sidebars(tmp_bfr, page, html);

		html.Toc_mgr().Init(page.Wikie().Html_mgr().Tidy_mgr(), toc_label, page_url.Page_bry());	// NOTE: do not pass in noop tidy_mgr, else broken TOC html will never get corrected during hdump; DATE:2016-08-14
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
		wtxt.Clear();
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
