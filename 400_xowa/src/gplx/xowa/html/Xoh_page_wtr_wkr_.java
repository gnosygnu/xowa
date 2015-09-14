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
package gplx.xowa.html; import gplx.*; import gplx.xowa.*;
import gplx.html.*; import gplx.xowa.xtns.relatedSites.*;
import gplx.xowa.parsers.utils.*;
public class Xoh_page_wtr_wkr_ {
	public static byte[] Bld_page_content_sub(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Bry_bfr tmp_bfr) {
		byte[] subpages = app.Html_mgr().Page_mgr().Subpages_bldr().Bld(wiki.Ns_mgr(), page.Ttl());
		byte[] page_content_sub = page.Html_data().Content_sub();		// contentSub exists; SEE: {{#isin}}
		byte[] redirect_msg = Xop_redirect_mgr.Bld_redirect_msg(app, wiki, page);			
		return Bry_.Add(subpages, page_content_sub, redirect_msg);
	}
	public static byte[] Bld_page_name(Bry_bfr tmp_bfr, Xoa_ttl ttl, byte[] display_ttl) {
		if (display_ttl != null) return display_ttl;	// display_ttl explicitly set; use it
		if (ttl.Ns().Id() == Xow_ns_.Id_special) {		// special: omit query args, else excessively long titles: EX:"Special:Search/earth?fulltext=y&xowa page index=1"
			tmp_bfr.Add(ttl.Ns().Name_txt_w_colon()).Add(ttl.Page_txt_wo_qargs());
			return tmp_bfr.Xto_bry_and_clear();
		}
		else
			return ttl.Full_txt();						// NOTE: include ns with ttl as per defect d88a87b3
	}
	public static void Bld_head_end(Bry_bfr html_bfr, Xoae_page page) {
		byte[] head_end = page.Html_data().Custom_head_end();
		if (head_end == null) return;
		int insert_pos = Bry_finder.Find_fwd(html_bfr.Bfr(), Html_tag_.Head_rhs);
		if (insert_pos == Bry_finder.Not_found) {
			Gfo_usr_dlg_.I.Warn_many("", "", "could not find </head>");
			return;
		}
		html_bfr.Insert_at(insert_pos, head_end);
	}
	public static void Bld_html_end(Bry_bfr html_bfr, Xoae_page page) {
		byte[] html_end = page.Html_data().Custom_html_end();
		if (html_end == null) return;
		int insert_pos = Bry_finder.Find_bwd(html_bfr.Bfr(), Html_tag_.Html_rhs, html_bfr.Len());
		if (insert_pos == Bry_finder.Not_found) {
			Gfo_usr_dlg_.I.Warn_many("", "", "could not find </html>");
			return;
		}
		html_bfr.Insert_at(insert_pos, html_end);
	}
}
