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
import gplx.xowa.gui.*; import gplx.xowa.gui.views.*; import gplx.xowa.html.*; import gplx.xowa.pages.*;
import gplx.xowa.files.*; import gplx.xowa.xtns.cite.*; import gplx.xowa.xtns.wdatas.*; import gplx.xowa.parsers.lnkis.redlinks.*; import gplx.xowa.html.tocs.*;
import gplx.xowa.html.modules.popups.*; import gplx.xowa.html.hdumps.pages.*; import gplx.xowa.xtns.wdatas.pfuncs.*;
public class Xoae_page implements Xoa_page {
	Xoae_page(Xowe_wiki wiki, Xoa_ttl ttl) {
		this.wiki = wiki; this.ttl = ttl;
		this.lang = wiki.Lang();	// default to wiki.lang; can be override later by wikitext
		hdr_mgr = new Xow_hdr_mgr(this, Xoa_app_.Utl__encoder_mgr());
		redlink_lnki_list = new Xopg_redlink_lnki_list(ttl.Ns().Id_module());
		Ttl_(ttl);
	}	Xoae_page() {}	// called by Null
	public Xow_wiki					Wiki() {return wiki;}
	public Xoa_ttl					Ttl() {return ttl;} public Xoae_page Ttl_(Xoa_ttl v) {ttl = v; url.Wiki_bry_(wiki.Domain_bry()).Page_bry_(v.Full_url()); return this;} private Xoa_ttl ttl;
	public Xoa_url					Url() {return url;} public Xoae_page Url_(Xoa_url v) {url = v; return this;} private Xoa_url url = Xoa_url.blank_();
	public void						Xtn_gallery_packed_exists_y_() {html_data.Xtn_gallery_packed_exists_y_();}
	public boolean						Exists() {return !Missing();}

	public Xopg_revision_data		Revision_data() {return revision_data;} private Xopg_revision_data revision_data = new Xopg_revision_data();
	public Xowe_wiki				Wikie() {return wiki;} private Xowe_wiki wiki;
	public Xopg_redlink_lnki_list	Redlink_lnki_list() {return redlink_lnki_list;} private Xopg_redlink_lnki_list redlink_lnki_list;
	public Xol_lang					Lang() {return lang;} public Xoae_page Lang_(Xol_lang v) {lang = v; return this;} private Xol_lang lang;
	public Xopg_html_data			Html_data() {return html_data;} private Xopg_html_data html_data = new Xopg_html_data();
	public Xopg_tab_data			Tab_data() {return tab_data;} private final Xopg_tab_data tab_data = new Xopg_tab_data();
	public Xopg_hdump_data			Hdump_data() {return hdump_data;} private final Xopg_hdump_data hdump_data = new Xopg_hdump_data();
	public boolean						Missing() {return missing;} public Xoae_page Missing_() {return Missing_(true);} public Xoae_page Missing_(boolean v) {missing = v; return this;}  private boolean missing;
	public boolean						Redirected() {return redirected;} public Xoae_page Redirected_(boolean v) {redirected = v; return this;} private boolean redirected;
	public ListAdp					Redirected_ttls() {return redirected_ttls;} private ListAdp redirected_ttls = ListAdp_.new_();
	public byte[]					Redirected_ttls__itm_0() {return (byte[])redirected_ttls.FetchAt(0);}
	public byte[]					Redirected_src() {return redirected_src;} public void Redirected_src_(byte[] v) {this.redirected_src = v;}  private byte[] redirected_src;
	public byte						Edit_mode() {return edit_mode;} private byte edit_mode; public void	Edit_mode_update_() {edit_mode = Xoa_page_.Edit_mode_update;}
	public Xop_root_tkn				Root() {return root;} public Xoae_page Root_(Xop_root_tkn v) {root = v; return this;} private Xop_root_tkn root;
	public byte[]					Data_raw() {return data_raw;} public Xoae_page Data_raw_(byte[] v) {data_raw = v; return this;} private byte[] data_raw = Bry_.Empty;
	public Xow_hdr_mgr				Hdr_mgr() {return hdr_mgr;} private Xow_hdr_mgr hdr_mgr;
	public Xoh_cmd_mgr				Html_cmd_mgr() {return html_cmd_mgr;} private Xoh_cmd_mgr html_cmd_mgr = new Xoh_cmd_mgr();
	public byte[][]					Category_list() {return category_list;} public Xoae_page Category_list_(byte[][] v) {category_list = v; return this;} private byte[][] category_list = new byte[0][];
	public ListAdp					Lnki_list() {return lnki_list;} public void Lnki_list_(ListAdp v) {this.lnki_list = v;} private ListAdp lnki_list = ListAdp_.new_();
	public Xof_xfer_queue			File_queue() {return file_queue;} private Xof_xfer_queue file_queue = new Xof_xfer_queue();
	public ListAdp					File_math() {return file_math;} private ListAdp file_math = ListAdp_.new_();
	public Xof_lnki_file_mgr		Lnki_file_mgr() {return lnki_file_mgr;} private Xof_lnki_file_mgr lnki_file_mgr = new Xof_lnki_file_mgr();
	public Ref_itm_mgr				Ref_mgr() {return ref_mgr;} private Ref_itm_mgr ref_mgr = new Ref_itm_mgr();
	public Xopg_popup_mgr			Popup_mgr() {return popup_mgr;} private Xopg_popup_mgr popup_mgr = new Xopg_popup_mgr();
	public ListAdp					Slink_list() {return slink_list;} private ListAdp slink_list = ListAdp_.new_();
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
	public void Clear() { // NOTE: this is called post-fetch but pre-parse; do not clear items set by post-fetch, such as id, ttl, redirected_ttls, data_raw
		hdr_mgr.Clear();
		lnki_list.Clear();
		file_math.Clear();
		file_queue.Clear();
		ref_mgr.Grps_clear();
		html_cmd_mgr.Clear();
		hdump_data.Clear();
		wdata_external_lang_links.Reset();
		gplx.xowa.xtns.scribunto.Scrib_core.Core_page_changed(this);
		slink_list.Clear();
		html_data.Clear();
		lnki_file_mgr.Clear();
		tab_data.Clear();
		pages_recursed = false;
		tmpl_stack_ary = Bry_.Ary_empty;
		tmpl_stack_ary_len = tmpl_stack_ary_max = 0;
		popup_mgr.Clear();
		revision_data.Clear();
		tmpl_prepend_mgr.Clear();
	}
	public static final Xoae_page Null = null;
	public static final Xoae_page Empty = new Xoae_page().Missing_();
	public static Xoae_page test_(Xowe_wiki wiki, Xoa_ttl ttl)	{return new Xoae_page(wiki, ttl);}
	public static Xoae_page new_(Xowe_wiki wiki, Xoa_ttl ttl)	{return new Xoae_page(wiki, ttl);}
	public static Xoae_page create_(Xowe_wiki wiki, Xoa_ttl ttl)	{
		Xoae_page rv = new Xoae_page(wiki, ttl);
		rv.edit_mode = Xoa_page_.Edit_mode_create;
		return rv;
	}
}
