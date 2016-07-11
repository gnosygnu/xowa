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
package gplx.xowa; import gplx.*;
import gplx.xowa.langs.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.guis.*; import gplx.xowa.guis.views.*;
import gplx.xowa.files.*; import gplx.xowa.files.xfers.*;
import gplx.xowa.parsers.*; import gplx.xowa.wikis.pages.lnkis.*; import gplx.xowa.xtns.cites.*; import gplx.xowa.xtns.wdatas.*; import gplx.xowa.xtns.wdatas.pfuncs.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.tocs.*; import gplx.xowa.htmls.modules.popups.*;
import gplx.xowa.wikis.pages.dbs.*; import gplx.xowa.wikis.pages.redirects.*; import gplx.xowa.wikis.pages.hdumps.*; import gplx.xowa.wikis.pages.htmls.*;
public class Xoae_page implements Xoa_page {
	Xoae_page(Xowe_wiki wiki, Xoa_ttl ttl) {
		this.wiki = wiki; this.ttl = ttl;
		this.lang = wiki.Lang();	// default to wiki.lang; can be override later by wikitext
		hdr_mgr = new Xow_hdr_mgr(this);
		html.Init_by_page(ttl);
		Ttl_(ttl);
	}	Xoae_page() {}	// called by Empty
	public Xow_wiki					Wiki()				{return wiki;}
	public Xoa_ttl					Ttl()				{return ttl;} public Xoae_page Ttl_(Xoa_ttl v) {ttl = v; url.Wiki_bry_(wiki.Domain_bry()).Page_bry_(v.Full_url()); return this;} private Xoa_ttl ttl;
	public Xoa_url					Url()				{return url;} public Xoae_page Url_(Xoa_url v) {url = v; return this;} private Xoa_url url = Xoa_url.blank();
	public byte[]					Url_bry_safe()		{return url == null ? Bry_.Empty : url.Raw();}
	public Xopg_db_data				Db()				{return db;}			private final    Xopg_db_data db = new Xopg_db_data();
	public Xopg_redirect_data		Redirect()			{return redirect;}		private final    Xopg_redirect_data redirect = new Xopg_redirect_data();
	public Xopg_html_data			Html_data()			{return html;}			private final    Xopg_html_data html = new Xopg_html_data();
	public Xopg_hdump_data			Hdump_mgr()			{return hdump;}			private final    Xopg_hdump_data hdump = new Xopg_hdump_data();

	public Xoa_page__commons_mgr	Commons_mgr() {return commons_mgr;} private final    Xoa_page__commons_mgr commons_mgr = new Xoa_page__commons_mgr();
	public void						Xtn_gallery_packed_exists_y_() {html.Xtn_gallery_packed_exists_y_();}
	public boolean						Xtn__timeline_exists() {return false;}	// drd always sets timeline
	public boolean					Xtn__gallery_exists() {return false;}	// drd does not need to set gallery.style.css

	public Xowe_wiki				Wikie() {return wiki;} private Xowe_wiki wiki;
	public Xol_lang_itm				Lang() {return lang;} public Xoae_page Lang_(Xol_lang_itm v) {lang = v; return this;} private Xol_lang_itm lang;
	public Xopg_tab_data			Tab_data() {return tab_data;} private final    Xopg_tab_data tab_data = new Xopg_tab_data();
	public byte						Edit_mode() {return edit_mode;} private byte edit_mode; public void	Edit_mode_update_() {edit_mode = Xoa_page_.Edit_mode_update;}
	public Xop_root_tkn				Root() {return root;} public Xoae_page Root_(Xop_root_tkn v) {root = v; return this;} private Xop_root_tkn root;

	public Xoh_cmd_mgr				Html_cmd_mgr() {return html_cmd_mgr;} private Xoh_cmd_mgr html_cmd_mgr = new Xoh_cmd_mgr();
	public Xof_xfer_queue			File_queue() {return file_queue;} private Xof_xfer_queue file_queue = new Xof_xfer_queue();
	public List_adp					File_math() {return file_math;} private List_adp file_math = List_adp_.New();
	public Xow_hdr_mgr				Hdr_mgr() {return hdr_mgr;} private Xow_hdr_mgr hdr_mgr;
	public List_adp					Lnki_list() {return lnki_list;} public void Lnki_list_(List_adp v) {this.lnki_list = v;} private List_adp lnki_list = List_adp_.New();
	public Ref_itm_mgr				Ref_mgr() {return ref_mgr;} private Ref_itm_mgr ref_mgr = new Ref_itm_mgr(); public void Ref_mgr_(Ref_itm_mgr v) {this.ref_mgr = v;}
	public Xopg_popup_mgr			Popup_mgr() {return popup_mgr;} private Xopg_popup_mgr popup_mgr = new Xopg_popup_mgr();
	public byte[][]					Category_list() {return category_list;} public Xoae_page Category_list_(byte[][] v) {category_list = v; return this;} private byte[][] category_list = new byte[0][];
	public List_adp					Slink_list() {return slink_list;} private List_adp slink_list = List_adp_.New();
	public Wdata_external_lang_links_data Wdata_external_lang_links() {return wdata_external_lang_links;} private Wdata_external_lang_links_data wdata_external_lang_links = new Wdata_external_lang_links_data();
	public boolean						Pages_recursed() {return pages_recursed;} public void Pages_recursed_(boolean v) {pages_recursed = v; } private boolean pages_recursed;
	public int						Bldr__ns_ord() {return bldr__ns_ord;} public void Bldr__ns_ord_(int v) {bldr__ns_ord = v;} private int bldr__ns_ord;
	public Xopg_tmpl_prepend_mgr	Tmpl_prepend_mgr() {return tmpl_prepend_mgr;} private Xopg_tmpl_prepend_mgr tmpl_prepend_mgr = new Xopg_tmpl_prepend_mgr();
	public void						Tmpl_stack_del() {--tmpl_stack_ary_len;}
	public boolean						Tmpl_stack_add(byte[] key) {
		for (int i = 0; i < tmpl_stack_ary_len; i++) {
			if (Bry_.Match(key, tmpl_stack_ary[i])) return false;
		}
		int new_len = tmpl_stack_ary_len + 1;
		if (new_len > tmpl_stack_ary_max) {
			tmpl_stack_ary_max = new_len * 2;
			tmpl_stack_ary = (byte[][])Array_.Resize(tmpl_stack_ary, tmpl_stack_ary_max);
		}
		tmpl_stack_ary[tmpl_stack_ary_len] = key;
		tmpl_stack_ary_len = new_len;
		return true;
	}	private byte[][] tmpl_stack_ary = Bry_.Ary_empty; private int tmpl_stack_ary_len = 0, tmpl_stack_ary_max = 0;
	public void Clear_all() {Clear(true);}
	public void Clear(boolean clear_scrib) { // NOTE: this is called post-fetch but pre-parse; do not clear items set by post-fetch, such as id, ttl, redirected_ttls, data_raw
		db.Clear();
		redirect.Clear();
		html.Clear();
		hdump.Clear();

		hdr_mgr.Clear();
		lnki_list.Clear();
		file_math.Clear();
		file_queue.Clear();
		ref_mgr.Grps_clear();
		html_cmd_mgr.Clear();
		wdata_external_lang_links.Reset();
		if (clear_scrib) wiki.Parser_mgr().Scrib().Core_page_changed(this);
		slink_list.Clear();
		tab_data.Clear();
		pages_recursed = false;
		tmpl_stack_ary = Bry_.Ary_empty;
		tmpl_stack_ary_len = tmpl_stack_ary_max = 0;
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
