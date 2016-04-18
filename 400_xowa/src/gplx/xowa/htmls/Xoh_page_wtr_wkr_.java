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
import gplx.langs.htmls.*; import gplx.xowa.xtns.relatedSites.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.parsers.utils.*;
public class Xoh_page_wtr_wkr_ {
	public static byte[] Bld_page_content_sub(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Bry_bfr tmp_bfr) {
		byte[] subpages = app.Html_mgr().Page_mgr().Subpages_bldr().Bld(wiki.Ns_mgr(), page.Ttl());
		byte[] page_content_sub = page.Html_data().Content_sub();		// contentSub exists; SEE: {{#isin}}
		byte[] redirect_msg = Xop_redirect_mgr.Bld_redirect_msg(app, wiki, page.Redirected_ttls());			
		return Bry_.Add(subpages, page_content_sub, redirect_msg);
	}
	public static byte[] Bld_page_name(Bry_bfr tmp_bfr, Xoa_ttl ttl, byte[] display_ttl) {
		if (display_ttl != null) return display_ttl;	// display_ttl explicitly set; use it
		if (ttl.Ns().Id() == Xow_ns_.Tid__special) {		// special: omit query args, else excessively long titles: EX:"Special:Search/earth?fulltext=y&xowa page index=1"
			tmp_bfr.Add(ttl.Ns().Name_ui_w_colon()).Add(ttl.Page_txt_wo_qargs());
			return tmp_bfr.To_bry_and_clear();
		}
		else
			return ttl.Full_txt_w_ttl_case();				// NOTE: include ns with ttl as per defect d88a87b3
	}
	public static void Bld_head_end(Bry_bfr html_bfr, Bry_bfr tmp_bfr, Xoae_page page) {
		byte[] head_end = page.Html_data().Custom_head_tags().To_html(tmp_bfr);
		if (Bry_.Len_eq_0(head_end)) return;
		int insert_pos = Bry_find_.Find_fwd(html_bfr.Bfr(), Gfh_tag_.Head_rhs);
		if (insert_pos == Bry_find_.Not_found) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "could not find </head>");
			return;
		}
		html_bfr.Insert_at(insert_pos, head_end);
	}
	public static void Bld_html_end(Bry_bfr html_bfr, Bry_bfr tmp_bfr, Xoae_page page) {
		byte[] html_end = page.Html_data().Custom_tail_tags().To_html(tmp_bfr);
		if (html_end == null) return;
		int insert_pos = Bry_find_.Find_bwd(html_bfr.Bfr(), Gfh_tag_.Html_rhs, html_bfr.Len());
		if (insert_pos == Bry_find_.Not_found) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "could not find </html>");
			return;
		}
		html_bfr.Insert_at(insert_pos, html_end);
	}
}
