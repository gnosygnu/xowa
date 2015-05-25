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
package gplx.xowa.ctgs; import gplx.*; import gplx.xowa.*;
class Xoctg_fmtr_all {
	public Xoctg_fmtr_all(byte tid) {this.Ctor(tid);}
	public byte Tid() {return tid;} private byte tid;
	public byte[] Div_id() {return div_id;} private byte[] div_id;
	public int Msg_id_label() {return msg_id_label;} private int msg_id_label;
	public int Msg_id_stats() {return msg_id_stats;} private int msg_id_stats;
	public boolean Grps_enabled() {return grps_enabled;} private boolean grps_enabled;
	public Xoctg_fmtr_itm Fmtr_itm() {return fmtr_itm;} private Xoctg_fmtr_itm fmtr_itm;
	public int Grp_max() {return grp_max;} public Xoctg_fmtr_all Grp_max_(int v) {grp_max = v; return this;} private int grp_max = Xoctg_html_mgr.Grp_max_default;
	public Bry_fmtr Html_all() {return html_all;} Bry_fmtr html_all = Bry_fmtr.new_();
	public Bry_fmtr Html_nav() {return html_nav;} Bry_fmtr html_nav = Bry_fmtr.new_();
	public Bry_fmtr Html_itm() {return html_itm;} Bry_fmtr html_itm = Bry_fmtr.new_();
	public Bry_fmtr Html_itm_missing() {return html_itm_missing;} Bry_fmtr html_itm_missing = Bry_fmtr.new_();
	public byte[] Url_arg_bgn() {return url_arg_bgn;} private byte[] url_arg_bgn = null;
	public byte[] Url_arg_end() {return url_arg_end;} private byte[] url_arg_end = null;
	private void Ctor(byte tid) {
		this.tid = tid;
		switch (tid) {
			case Xoa_ctg_mgr.Tid_subc: grps_enabled = Bool_.Y; msg_id_label = Xol_msg_itm_.Id_ctg_subc_label ; msg_id_stats = Xol_msg_itm_.Id_ctg_subc_count; div_id = Div_id_subc; url_arg_bgn = Url_arg_subc_bgn; url_arg_end = Url_arg_subc_end; this.fmtr_itm = Xoctg_fmtr_itm_subc._; break;
			case Xoa_ctg_mgr.Tid_page: grps_enabled = Bool_.Y; msg_id_label = Xol_msg_itm_.Id_ctg_page_header; msg_id_stats = Xol_msg_itm_.Id_ctg_page_count; div_id = Div_id_page; url_arg_bgn = Url_arg_page_bgn; url_arg_end = Url_arg_page_end; this.fmtr_itm = Xoctg_fmtr_itm_page._; break;
			case Xoa_ctg_mgr.Tid_file: grps_enabled = Bool_.Y; msg_id_label = Xol_msg_itm_.Id_ctg_file_header; msg_id_stats = Xol_msg_itm_.Id_ctg_file_count; div_id = Div_id_file; url_arg_bgn = Url_arg_file_bgn; url_arg_end = Url_arg_file_end; this.fmtr_itm = Xoctg_fmtr_itm_file._; break;
			default: throw Err_.unhandled(tid);
		}
		html_all.Fmt_(String_.Concat_lines_nl_skip_last
		(	""
		,	"<div id=\"~{div_id}\">"
		,	"  <h2>~{all_label}</h2>"
		,	"  <p>~{all_stats}</p>~{all_navs}"
		,	"  <div lang=\"~{lang_key}\" dir=\"~{lang_ltr}\" class=\"mw-content-~{lang_ltr}\">"
		,	"    <table style=\"width: 100%;\">"
		,	"      <tr style=\"vertical-align: top;\">~{grps}"
		,	"      </tr>"
		,	"    </table>"
		,	"  </div>~{all_navs}"
		,	"</div>"
		))
		.Keys_
		("div_id", "all_label", "all_stats", "all_navs", "lang_key", "lang_ltr", "grps");
		html_nav.Fmt_(String_.Concat_lines_nl_skip_last
		(	""
		,	"  (<a href=\"~{nav_href}\" class=\"xowa_nav\" title=\"~{nav_title}\">~{nav_text}</a>)"
		))
		.Keys_("nav_href", "nav_title", "nav_text");
		html_itm.Fmt_(String_.Concat_lines_nl_skip_last
		(	""
		,	"            <li><a href=\"~{itm_href}\"~{itm_atr_cls} title=\"~{itm_title}\">~{itm_text}</a></li>"
		))
		.Keys_("itm_href", "itm_title", "itm_text", "itm_id", "itm_atr_cls");
		html_itm_missing.Fmt_(String_.Concat_lines_nl_skip_last
		(	""
		,	"            <li class=\"xowa-missing-category-entry\"><span title=\"id not found: #~{itm_id} might be talk/user page\">~{itm_text} (missing)</li>"
		))
		.Keys_("itm_href", "itm_title", "itm_text", "itm_id");
		switch (tid) {
			case Xoa_ctg_mgr.Tid_subc:
				html_itm.Fmt_(String_.Concat_lines_nl_skip_last
				(	""
				,	"            <li>"
				,	"              <div class=\"CategoryTreeSection\">"
				,	"                <div class=\"CategoryTreeItem\">"
				,	"                  <span class=\"CategoryTreeBullet\">"
				,	"                    <span class=\"CategoryTreeToggle\" style=\"display: none;\" data-ct-title=\"~{itm_data_title}\" title=\"~{itm_title}\" data-ct-state=\"collapsed\">"
				,	"                    </span> "
				,	"                  </span>"
				,	"                  <a href=\"~{itm_href}\" class=\"CategoryTreeLabel  CategoryTreeLabelNs14 CategoryTreeLabelCategory\">~{itm_text}"
				,	"                  </a>"
				,	"                  <span title=\"~{itm_contains_title}\" dir=\"ltr\">~{itm_contains_text}"
				,	"                  </span>"
				,	"                </div>"
				,	"                <div class=\"CategoryTreeChildren\" style=\"display:none\"></div>"
				,	"              </div>"
				,	"            </li>"
				)).Keys_("itm_data_title", "itm_title", "itm_href", "itm_text","itm_contains_title", "itm_contains_text");
				break;
			case Xoa_ctg_mgr.Tid_page:
				break;
			case Xoa_ctg_mgr.Tid_file:
//					html_all.Fmt_(String_.Concat_lines_nl_skip_last
//					(	""
//					,	"<div id=\"~{div_id}\">"
//					,	"  <h2>~{all_label}</h2>"
//					,	"  <p>~{all_stats}</p>"
//					,	"  <ul id=\"xowa_gallery_ul_0\" class=\"gallery\">~{grps}"
//					,	"  </ul>"
//					,	"</div>"
//					));
				break;
			default: throw Err_.unhandled(tid);
		}
	}
	public byte[] Bld_bwd_fwd(Xowe_wiki wiki, Xoa_ttl ttl, Xoctg_view_grp view_grp) {
		if (view_grp.Total() < grp_max) return Bry_.Empty;
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_k004();
		Html_nav_bry(bfr, wiki, ttl, view_grp, Bool_.N);
		Html_nav_bry(bfr, wiki, ttl, view_grp, Bool_.Y);
		return bfr.To_bry_and_rls();
	}
	private void Html_nav_bry(Bry_bfr bfr, Xowe_wiki wiki, Xoa_ttl ttl, Xoctg_view_grp view_grp, boolean fill_at_bgn) {
		Bry_bfr href_bfr = wiki.Utl__bfr_mkr().Get_b512();
		Xoae_app app = wiki.Appe();
		app.Href_parser().Build_to_bfr(href_bfr, app, wiki.Domain_bry(), ttl);
		byte[] arg_idx_lbl = null; byte[] arg_sortkey = null;
		if (fill_at_bgn) {
			arg_idx_lbl = url_arg_bgn;
			arg_sortkey = view_grp.Itms_last_sortkey();
			if (arg_sortkey == null)
				arg_sortkey = view_grp.Itms_at_last().Sortkey();
		}
		else {
			arg_idx_lbl = url_arg_end;
			arg_sortkey = view_grp.Itms_at_first().Sortkey();
		}
		href_bfr.Add_byte(Byte_ascii.Question).Add(arg_idx_lbl).Add_byte(Byte_ascii.Eq);		// filefrom=
		href_bfr.Add(arg_sortkey);																// Abc
		href_bfr.Add_byte(Byte_ascii.Hash).Add(div_id);											// #mw-subcategories
		byte[] nav_href = href_bfr.To_bry_and_rls();
		byte[] nav_ttl = ttl.Full_url();
		int nav_text_id = fill_at_bgn ? Xol_msg_itm_.Id_next_results : Xol_msg_itm_.Id_prev_results;
		byte[] nav_text = wiki.Msg_mgr().Val_by_id_args(nav_text_id, grp_max);					// fill_at_bgn 200 / previous 200
		html_nav.Bld_bfr(bfr, nav_href, nav_ttl, nav_text);
	}
	public static final byte[]
		Url_arg_from		= Bry_.new_a7("from")
	,	Url_arg_until 		= Bry_.new_a7("until")
	,	Url_arg_subc_bgn 	= Bry_.new_a7("subcatfrom")
	,	Url_arg_subc_end	= Bry_.new_a7("subcatuntil")
	,	Url_arg_page_bgn 	= Bry_.new_a7("pagefrom")
	,	Url_arg_page_end 	= Bry_.new_a7("pageuntil")
	,	Url_arg_file_bgn 	= Bry_.new_a7("filefrom")
	,	Url_arg_file_end	= Bry_.new_a7("fileuntil")
	,	Div_id_subc			= Bry_.new_a7("mw-subcategories")
	,	Div_id_page			= Bry_.new_a7("mw-pages")
	,	Div_id_file			= Bry_.new_a7("mw-category-media")
	;
}
interface Xoctg_fmtr_itm extends Bry_fmtr_arg {
	int Grp_end_idx();
	boolean Grp_end_at_col();
	int Col_idx(); void Col_idx_(int col_idx, int col_bgn);
	void Init_from_all(Xowe_wiki wiki, Xol_lang lang, Xoctg_view_ctg ctg, Xoctg_fmtr_all mgr, Xoctg_view_grp itms_list, int itms_list_len);
	void Init_from_grp(byte[] grp_ttl, int i);
}
