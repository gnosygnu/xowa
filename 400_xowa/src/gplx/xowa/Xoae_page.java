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
package gplx.xowa; import gplx.*;
import gplx.xowa.langs.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.guis.*; import gplx.xowa.guis.views.*;
import gplx.xowa.files.*; import gplx.xowa.files.xfers.*;
import gplx.xowa.parsers.*; import gplx.xowa.wikis.pages.lnkis.*; import gplx.xowa.xtns.cites.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.pfuncs.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.addons.htmls.tocs.*; import gplx.xowa.htmls.modules.popups.*;
import gplx.xowa.wikis.pages.wtxts.*; import gplx.xowa.wikis.pages.dbs.*; import gplx.xowa.wikis.pages.redirects.*; import gplx.xowa.wikis.pages.hdumps.*; import gplx.xowa.wikis.pages.htmls.*;
public class Xoae_page implements Xoa_page {
	Xoae_page(Xowe_wiki wiki, Xoa_ttl ttl) {
		this.wiki = wiki; this.ttl = ttl;
		this.lang = wiki.Lang();	// default to wiki.lang; can be override later by wikitext
		html.Init_by_page(ttl);
		html.Toc_mgr().Init(wiki.Html_mgr().Tidy_mgr(), wiki.Lang().Msg_mgr().Itm_by_id_or_null(gplx.xowa.langs.msgs.Xol_msg_itm_.Id_toc).Val(), ttl.Page_db());
		Ttl_(ttl);
	}	Xoae_page() {}	// called by Empty
	public Xow_wiki					Wiki()				{return wiki;}
	public Xoa_ttl					Ttl()				{return ttl;} private Xoa_ttl ttl;
	public Xoae_page				Ttl_(Xoa_ttl v) {
		ttl = v;
		url.Wiki_bry_(wiki.Domain_bry()).Page_bry_(v.Full_db());	// NOTE:was Full_url, but caused url_bar to have url-decoded chars; DATE:2016-11-25
		return this;
	}
	public Xoa_url					Url()				{return url;} public Xoae_page Url_(Xoa_url v) {url = v; return this;} private Xoa_url url = Xoa_url.blank();
	public byte[]					Url_bry_safe()		{return Xoa_page_.Url_bry_safe(url, wiki, ttl);}
	public Xopg_db_data				Db()				{return db;}				private final    Xopg_db_data db = new Xopg_db_data();
	public Xopg_redirect_mgr		Redirect_trail()	{return redirect_trail;}	private final    Xopg_redirect_mgr redirect_trail = new Xopg_redirect_mgr();
	public Xopg_html_data			Html_data()			{return html;}				private final    Xopg_html_data html = new Xopg_html_data();
	public Xopg_hdump_data			Hdump_mgr()			{return hdump;}				private final    Xopg_hdump_data hdump = new Xopg_hdump_data();
	public Xopg_wtxt_data			Wtxt() {return wtxt;} private final    Xopg_wtxt_data wtxt = new Xopg_wtxt_data();

	public Xoa_page__commons_mgr	Commons_mgr() {return commons_mgr;} private final    Xoa_page__commons_mgr commons_mgr = new Xoa_page__commons_mgr();
	public void						Xtn_gallery_packed_exists_y_() {html.Xtn_gallery_packed_exists_y_();}
	public boolean						Xtn__timeline_exists() {return false;}	// drd always sets timeline
	public boolean					Xtn__gallery_exists() {return false;}	// drd does not need to set gallery.style.css
	private Guid_adp page_guid;
	public Guid_adp Page_guid() {
		if (page_guid == null) {
			page_guid = Guid_adp_.New();
		}
		return page_guid;
	}

	public Xowe_wiki				Wikie() {return wiki;} private Xowe_wiki wiki;
	public Xol_lang_itm				Lang() {return lang;} public Xoae_page Lang_(Xol_lang_itm v) {lang = v; return this;} private Xol_lang_itm lang;
	public Xopg_tab_data			Tab_data() {return tab_data;} private final    Xopg_tab_data tab_data = new Xopg_tab_data();
	public byte						Edit_mode() {return edit_mode;} private byte edit_mode; public void	Edit_mode_update_() {edit_mode = Xoa_page_.Edit_mode_update;}
	public Xop_root_tkn				Root() {return root;} public Xoae_page Root_(Xop_root_tkn v) {root = v; return this;} private Xop_root_tkn root;

	public Xoh_cmd_mgr				Html_cmd_mgr() {return html_cmd_mgr;} private Xoh_cmd_mgr html_cmd_mgr = new Xoh_cmd_mgr();
	public Xof_xfer_queue			File_queue() {return file_queue;} private Xof_xfer_queue file_queue = new Xof_xfer_queue();
	public List_adp					File_math() {return file_math;} private List_adp file_math = List_adp_.New();
	public List_adp					Lnki_list() {return lnki_list;} public void Lnki_list_(List_adp v) {this.lnki_list = v;} private List_adp lnki_list = List_adp_.New();
	public Ref_itm_mgr				Ref_mgr() {return ref_mgr;} private Ref_itm_mgr ref_mgr = new Ref_itm_mgr(); public void Ref_mgr_(Ref_itm_mgr v) {this.ref_mgr = v;}
	public Xopg_popup_mgr			Popup_mgr() {return popup_mgr;} private Xopg_popup_mgr popup_mgr = new Xopg_popup_mgr();

	public List_adp					Slink_list() {return slink_list;} private List_adp slink_list = List_adp_.New();
	public Wdata_external_lang_links_data Wdata_external_lang_links() {return wdata_external_lang_links;} private Wdata_external_lang_links_data wdata_external_lang_links = new Wdata_external_lang_links_data();
	public int						Bldr__ns_ord() {return bldr__ns_ord;} public void Bldr__ns_ord_(int v) {bldr__ns_ord = v;} private int bldr__ns_ord;
	public Xopg_tmpl_prepend_mgr	Tmpl_prepend_mgr() {return tmpl_prepend_mgr;} private Xopg_tmpl_prepend_mgr tmpl_prepend_mgr = new Xopg_tmpl_prepend_mgr();
	public void Clear_all() {Clear(true);}
	public void Clear(boolean clear_scrib) { // NOTE: this is called post-fetch but pre-wtxt; do not clear items set by post-fetch, such as id, ttl, redirected_ttls, data_raw
		db.Clear();
		redirect_trail.Clear();
		html.Clear();
		hdump.Clear();
		wtxt.Clear();

		lnki_list.Clear();
		file_math.Clear();
		file_queue.Clear();
		ref_mgr.Grps_clear();
		html_cmd_mgr.Clear();
		wdata_external_lang_links.Reset();
		if (clear_scrib) wiki.Parser_mgr().Scrib().When_page_changed(this);
		slink_list.Clear();
		tab_data.Clear();
		popup_mgr.Clear();
		tmpl_prepend_mgr.Clear();
		commons_mgr.Clear();
	}
	public static final    Xoae_page Empty = new Xoae_page();
	public static Xoae_page New(Xowe_wiki wiki, Xoa_ttl ttl)		{return new Xoae_page(wiki, ttl);}
	public static Xoae_page New_test(Xowe_wiki wiki, Xoa_ttl ttl)	{return new Xoae_page(wiki, ttl);}
	public static Xoae_page New_edit(Xowe_wiki wiki, Xoa_ttl ttl)	{
		Xoae_page rv = new Xoae_page(wiki, ttl);
		rv.edit_mode = Xoa_page_.Edit_mode_create;
		return rv;
	}
}
