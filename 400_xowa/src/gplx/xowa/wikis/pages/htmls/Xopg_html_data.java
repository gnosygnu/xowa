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
package gplx.xowa.wikis.pages.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.wikis.pages.skins.*; import gplx.xowa.wikis.pages.tags.*; import gplx.xowa.wikis.pages.lnkis.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.heads.*; import gplx.xowa.addons.htmls.tocs.*;
import gplx.xowa.xtns.pagebanners.*; import gplx.xowa.xtns.indicators.*;
public class Xopg_html_data {
	public Xopg_lnki_list		Redlink_list()		{return redlink_list;} private final    Xopg_lnki_list redlink_list = new Xopg_lnki_list();

	public boolean				Html_restricted() {return html_restricted;} private boolean html_restricted = true;
	public void					Html_restricted_(boolean v) {html_restricted = v;} public void Html_restricted_n_() {Html_restricted_(Bool_.N);}  public void Html_restricted_y_() {Html_restricted_(Bool_.Y);}
	public byte[]				Display_ttl() {
		return	(	display_ttl_vnt != null		// -{T}- was in document
				&&	display_ttl == null			// {{DISPLAYTITLE}} does not exist
				&&	lang_convert_content		// __NOCONVERTCONTENT__ exists
				&&	lang_convert_title			// __NOCONVERTTITLE__ exists
				)
			? display_ttl_vnt					// return variant title; DATE:2014-08-29
			: display_ttl						// return normal title
			;
	}		
	public Xopg_html_data		Display_ttl_(byte[] v) {display_ttl = v; return this;} private byte[] display_ttl;
	public boolean				Mode_wtxt_shown() {synchronized (this) {return mode_wtxt_shown;}} public void Mode_wtxt_shown_y_() {synchronized (this) {this.mode_wtxt_shown = true;}} private boolean mode_wtxt_shown; 
	public byte[]				Display_ttl_vnt() {return display_ttl_vnt;} public void Display_ttl_vnt_(byte[] v) {display_ttl_vnt = v;} private byte[] display_ttl_vnt;
	public byte[]				Content_sub() {return content_sub;} public void Content_sub_(byte[] v) {content_sub = v;} private byte[] content_sub;
	public Xopg_page_heading	Page_heading() {return page_heading;} private final    Xopg_page_heading page_heading = new Xopg_page_heading();
	public String				Bmk_pos() {return html_bmk_pos;} public void Bmk_pos_(String v) {html_bmk_pos = v;} private String html_bmk_pos;
	public Bry_bfr				Portal_div_xtn() {return portal_div_xtn;} private Bry_bfr portal_div_xtn = Bry_bfr_.Reset(255);
	public byte[]				Edit_preview_w_dbg() {return Bry_.Add(xtn_scribunto_dbg, edit_preview);} public void Edit_preview_(byte[] v) {edit_preview = v;} private byte[] edit_preview = Bry_.Empty;
	public int					Lnke_autonumber_next() {return lnke_autonumber++;} private int lnke_autonumber = 1;
	public int					Sect_uid() {return sect_uid;} private int sect_uid = -1; public int Sect_uid_next() {return ++sect_uid;}
	public boolean              Cbk_enabled()  {return cbk_enabled;} private boolean cbk_enabled; public void Cbk_enabled_(boolean v) {this.cbk_enabled = v;}
	public boolean              Js_enabled()   {return js_enabled;} private boolean js_enabled; public void Js_enabled_(boolean v) {this.js_enabled = v;}
	public boolean				Hdump_exists() {return hdump_exists;} private boolean hdump_exists; public void Hdump_exists_(boolean v) {this.hdump_exists = v;}
	public byte[]				Catpage_data() {return catpage_data;} private byte[] catpage_data; public void Catpage_data_(byte[] v) {this.catpage_data = v;}

	public boolean				Writing_hdr_for_toc() {return writing_hdr_for_toc;} private boolean writing_hdr_for_toc; public void Writing_hdr_for_toc_y_() {writing_hdr_for_toc = Bool_.Y;} public void Writing_hdr_for_toc_n_() {writing_hdr_for_toc = Bool_.N;}
	public Xoh_toc_mgr			Toc_mgr()	{return toc_mgr;}	private final    Xoh_toc_mgr toc_mgr = new Xoh_toc_mgr(); 

	public boolean				Lang_convert_content() {return lang_convert_content;} public void Lang_convert_content_(boolean v) {lang_convert_content = v;} private boolean lang_convert_content = true;
	public boolean				Lang_convert_title() {return lang_convert_title;} public void Lang_convert_title_(boolean v) {lang_convert_title = v;} private boolean lang_convert_title = true;
	public Xopg_xtn_skin_mgr	Xtn_skin_mgr() {return xtn_skin_mgr;} private Xopg_xtn_skin_mgr xtn_skin_mgr = new Xopg_xtn_skin_mgr();
	public Indicator_html_bldr	Indicators() {return indicators;} private final    Indicator_html_bldr indicators = new Indicator_html_bldr();
	public int					Xtn_gallery_next_id() {return ++xtn_gallery_next_id;} private int xtn_gallery_next_id = -1;
	public boolean				Xtn_gallery_packed_exists() {return xtn_gallery_packed_exists;} public void Xtn_gallery_packed_exists_y_() {xtn_gallery_packed_exists = true;} private boolean xtn_gallery_packed_exists;
	public boolean				Xtn_imap_exists() {return xtn_imap_exists;} public void Xtn_imap_exists_y_() {xtn_imap_exists = true;} private boolean xtn_imap_exists;
	public int					Xtn_imap_next_id() {return ++xtn_imap_next_id;} private int xtn_imap_next_id;	// NOTE: must keep separate imap_id b/c html_elem_id is not always set;
	public byte[]				Xtn_search_text() {return xtn_search_txt;} public void Xtn_search_text_(byte[] v) {xtn_search_txt = v;} private byte[] xtn_search_txt = Bry_.Empty;
	public byte[]				Xtn_scribunto_dbg() {return xtn_scribunto_dbg;} public void Xtn_scribunto_dbg_(byte[] v) {xtn_scribunto_dbg = Bry_.Add(xtn_scribunto_dbg, v);} private byte[] xtn_scribunto_dbg = Bry_.Empty;
	public Pgbnr_itm			Xtn_pgbnr() {return xtn_pgbnr;} public void Xtn_pgbnr_(Pgbnr_itm v) {xtn_pgbnr = v;} private Pgbnr_itm xtn_pgbnr;
	public Xoh_head_mgr			Head_mgr() {return module_mgr;} private Xoh_head_mgr module_mgr = new Xoh_head_mgr();
	public boolean					Skip_parse() {return skip_parse;} public void Skip_parse_(boolean v) {skip_parse = v;} private boolean skip_parse;
	public Xopg_tag_mgr			Custom_head_tags() {return head_tags;} private final    Xopg_tag_mgr head_tags = new Xopg_tag_mgr(Bool_.Y);
	public Xopg_tag_mgr			Custom_tail_tags() {return tail_tags;} private final    Xopg_tag_mgr tail_tags = new Xopg_tag_mgr(Bool_.N);
	public byte[]				Custom_html() {return custom_html;} public Xopg_html_data Custom_html_(byte[] v) {custom_html = v; return this;} private byte[] custom_html;
	public byte[]				Custom_body() {return custom_body;} public Xopg_html_data Custom_body_(byte[] v) {custom_body = v; return this;} private byte[] custom_body;
	public byte[]				Custom_tab_name() {return custom_tab_name;} public Xopg_html_data Custom_tab_name_(byte[] v) {custom_tab_name = v; return this;} private byte[] custom_tab_name;
	public void Clear() {
		redlink_list.Clear();
		toc_mgr.Clear();

		html_restricted = true;
		display_ttl = content_sub = display_ttl_vnt = null;
		lang_convert_content = lang_convert_title = true;
		lnke_autonumber = 1;
		sect_uid = -1;
		xtn_skin_mgr.Clear();
		xtn_gallery_packed_exists = false;
		xtn_imap_next_id = 0; xtn_gallery_next_id = -1;
		xtn_imap_exists = false;
		xtn_search_txt = Bry_.Empty;
		xtn_scribunto_dbg = Bry_.Empty;
		xtn_pgbnr = null;
		module_mgr.Clear();
		custom_html = custom_tab_name = null;
		indicators.Clear();
		this.mode_wtxt_shown = false;
	}
	public void Init_by_page(Xoa_ttl ttl) {
		redlink_list.Disabled_(ttl.Ns().Id_is_module());
	}
}
