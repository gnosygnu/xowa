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
import gplx.xowa.html.modules.*;
public class Xoa_html_data {
	private OrderedHash ctg_hash;
	public Xoh_module_mgr		Module_mgr() {return module_mgr;} private Xoh_module_mgr module_mgr = new Xoh_module_mgr();
	public byte[]				Content_sub() {return html_content_sub;} public void Content_sub_(byte[] v) {html_content_sub = v;} private byte[] html_content_sub;
	public Bry_bfr				Portal_div_xtn() {return portal_div_xtn;} private Bry_bfr portal_div_xtn = Bry_bfr.reset_(255);
	public int					Lnke_autonumber_next() {return lnke_autonumber++;} private int lnke_autonumber = 1;
	public String				Bmk_pos() {return html_bmk_pos;} public void Bmk_pos_(String v) {html_bmk_pos = v;} private String html_bmk_pos;
	public boolean					Restricted() {return html_restricted;} private boolean html_restricted = true;
	public void					Restricted_(boolean v) {html_restricted = v;} public void Restricted_n_() {Restricted_(Bool_.N);}  public void Restricted_y_() {Restricted_(Bool_.Y);}
	public byte[]				Edit_preview() {return edit_preview;} public void Edit_preview_(byte[] v) {edit_preview = v;} private byte[] edit_preview = Bry_.Empty;
	public byte[]				Search_text() {return search_text;} public void Search_text_(byte[] v) {search_text = v;} private byte[] search_text = Bry_.Empty;
	public byte[]				Custom_html() {return custom_html;} public Xoa_html_data Custom_html_(byte[] v) {custom_html = v; return this;} private byte[] custom_html;
	public byte[]				Custom_name() {return custom_name;} public Xoa_html_data Custom_name_(byte[] v) {custom_name = v; return this;} private byte[] custom_name;
	public byte[]				Custom_head_end() {return custom_head_end;}
	public void Custom_head_end_concat(byte[] v) {
		if (v == null)
			custom_head_end = v;
		else
			custom_head_end = Bry_.Add(custom_head_end, v);
	}	private byte[] custom_head_end;
	public byte[]				Custom_html_end() {return custom_html_end;}
	public void Custom_html_end_concat(byte[] v) {
		if (v == null)
			custom_html_end = v;
		else
			custom_html_end = Bry_.Add(custom_html_end, v);
	}	private byte[] custom_html_end;
	public void Clear() {
		if (ctg_hash != null) ctg_hash.Clear();
		module_mgr.Clear();
		lnke_autonumber = 1;
		html_restricted = true;
		html_content_sub = Bry_.Empty;
		search_text = Bry_.Empty;
		custom_html_end = custom_head_end = custom_html = custom_name = null;
	}
	public byte[][] Ctgs_to_ary() {return ctg_hash == null ? Bry_.Ary_empty : (byte[][])ctg_hash.XtoAry(byte[].class);}
	public void Ctgs_add(Xoa_ttl ttl) {
		if (ctg_hash == null) ctg_hash = OrderedHash_.new_bry_();
		byte[] ttl_bry = ttl.Page_txt();
		if (ctg_hash.Has(ttl_bry)) return;
		ctg_hash.Add(ttl_bry, ttl_bry);
	}
}
