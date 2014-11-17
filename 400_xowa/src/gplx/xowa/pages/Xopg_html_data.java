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
package gplx.xowa.pages; import gplx.*; import gplx.xowa.*;
import gplx.html.*; import gplx.xowa.html.modules.*; import gplx.xowa.pages.skins.*; import gplx.xowa.xtns.indicators.*;
public class Xopg_html_data {
	private OrderedHash ctg_hash;
	public boolean					Html_restricted() {return html_restricted;} private boolean html_restricted = true;
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
	public Xopg_html_data Display_ttl_(byte[] v) {display_ttl = v; return this;} private byte[] display_ttl;
	public byte[]				Display_ttl_vnt() {return display_ttl_vnt;} public void Display_ttl_vnt_(byte[] v) {display_ttl_vnt = v;} private byte[] display_ttl_vnt;
	public byte[]				Content_sub() {return content_sub;} public void Content_sub_(byte[] v) {content_sub = v;} private byte[] content_sub;
	public String				Bmk_pos() {return html_bmk_pos;} public void Bmk_pos_(String v) {html_bmk_pos = v;} private String html_bmk_pos;
	public Bry_bfr				Portal_div_xtn() {return portal_div_xtn;} private Bry_bfr portal_div_xtn = Bry_bfr.reset_(255);
	public byte[]				Edit_preview_w_dbg() {return Bry_.Add(xtn_scribunto_dbg, edit_preview);} public void Edit_preview_(byte[] v) {edit_preview = v;} private byte[] edit_preview = Bry_.Empty;
	public int					Lnke_autonumber_next() {return lnke_autonumber++;} private int lnke_autonumber = 1;
	public boolean					Lang_convert_content() {return lang_convert_content;} public void Lang_convert_content_(boolean v) {lang_convert_content = v;} private boolean lang_convert_content = true;
	public boolean					Lang_convert_title() {return lang_convert_title;} public void Lang_convert_title_(boolean v) {lang_convert_title = v;} private boolean lang_convert_title = true;
	public Xopg_xtn_skin_mgr	Xtn_skin_mgr() {return xtn_skin_mgr;} private Xopg_xtn_skin_mgr xtn_skin_mgr = new Xopg_xtn_skin_mgr();
	public Indicator_html_bldr	Indicators() {return indicators;}
	public Indicator_html_bldr Indicators_or_new() {
		if (indicators == null)
			indicators = new Indicator_html_bldr();
		return indicators;
	}	private Indicator_html_bldr indicators;
	public int					Xtn_gallery_next_id() {return ++xtn_gallery_next_id;} private int xtn_gallery_next_id = -1;
	public boolean					Xtn_gallery_packed_exists() {return xtn_gallery_packed_exists;} public void Xtn_gallery_packed_exists_y_() {xtn_gallery_packed_exists = true;} private boolean xtn_gallery_packed_exists;
	public boolean					Xtn_imap_exists() {return xtn_imap_exists;} public void Xtn_imap_exists_y_() {xtn_imap_exists = true;} private boolean xtn_imap_exists;
	public int					Xtn_imap_next_id() {return ++xtn_imap_next_id;} private int xtn_imap_next_id;	// NOTE: must keep separate imap_id b/c html_elem_id is not always set;
	public byte[]				Xtn_search_text() {return xtn_search_txt;} public void Xtn_search_text_(byte[] v) {xtn_search_txt = v;} private byte[] xtn_search_txt = Bry_.Empty;
	public byte[]				Xtn_scribunto_dbg() {return xtn_scribunto_dbg;} public void Xtn_scribunto_dbg_(byte[] v) {xtn_scribunto_dbg = Bry_.Add(xtn_scribunto_dbg, v);} private byte[] xtn_scribunto_dbg = Bry_.Empty;
	public Xoh_module_mgr		Module_mgr() {return module_mgr;} private Xoh_module_mgr module_mgr = new Xoh_module_mgr();
	public byte[]				Custom_html() {return custom_html;} public Xopg_html_data Custom_html_(byte[] v) {custom_html = v; return this;} private byte[] custom_html;
	public byte[]				Custom_name() {return custom_name;} public Xopg_html_data Custom_name_(byte[] v) {custom_name = v; return this;} private byte[] custom_name;
	public byte[]				Custom_head_end() {return custom_head_end;}
	public byte[]				Custom_html_end() {return custom_html_end;}
	public void Custom_head_end_concat(byte[] v) {
		if (v == null)
			custom_head_end = v;
		else
			custom_head_end = Bry_.Add(custom_head_end, v);
	}	private byte[] custom_head_end;
	public void Custom_html_end_concat(byte[] v) {
		if (v == null)
			custom_html_end = v;
		else
			custom_html_end = Bry_.Add(custom_html_end, v);
	}	private byte[] custom_html_end;
	public void Clear() {
		html_restricted = true;
		display_ttl = content_sub = display_ttl_vnt = null;
		lang_convert_content = lang_convert_title = true;
		lnke_autonumber = 1;
		xtn_skin_mgr.Clear();
		xtn_gallery_packed_exists = false;
		xtn_imap_next_id = 0; xtn_gallery_next_id = -1;
		xtn_imap_exists = false;
		xtn_search_txt = Bry_.Empty;
		xtn_scribunto_dbg = Bry_.Empty;
		module_mgr.Clear();
		custom_html = custom_html_end = custom_head_end = custom_name = null;
		if (ctg_hash != null) ctg_hash.Clear();
		indicators = null;
	}
	public byte[][] Ctgs_to_ary() {return ctg_hash == null ? Bry_.Ary_empty : (byte[][])ctg_hash.Xto_ary(byte[].class);}
	public void Ctgs_add(Xoa_ttl ttl) {
		if (ctg_hash == null) ctg_hash = OrderedHash_.new_bry_();
		byte[] ttl_bry = ttl.Page_txt();
		if (ctg_hash.Has(ttl_bry)) return;
		ctg_hash.Add(ttl_bry, ttl_bry);
	}
}
