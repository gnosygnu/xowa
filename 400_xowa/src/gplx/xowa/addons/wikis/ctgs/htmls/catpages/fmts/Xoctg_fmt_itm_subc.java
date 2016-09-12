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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.fmts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.langs.msgs.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*;
import gplx.xowa.users.history.*;
class Xoctg_fmt_itm_subc extends Xoctg_fmt_itm_base {
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	@Override public void Bld_html(Bry_bfr bfr, Xow_wiki wiki, Xou_history_mgr history_mgr, Xoh_href_parser href_parser, Xoctg_catpage_itm itm, Xoa_ttl ttl) {
		byte[] itm_href = wiki.Html__href_wtr().Build_to_bry(wiki, ttl);
		int count_subcs = 0;
		int count_pages = 0;
		int count_files = 0;
		Xow_msg_mgr msg_mgr = wiki.Msg_mgr();
		byte[] contains_title = wiki.Msg_mgr().Val_by_id_args(Xol_msg_itm_.Id_ctgtree_subc_counts, count_subcs, count_pages, count_files);
		byte[] contains_text = Bld_contains_text(msg_mgr, count_subcs, count_pages, count_files);
		byte[] ttl_page = ttl.Page_txt();
		fmt_exists.Bld_many(bfr, ttl.Page_db(), ttl_page, itm_href, ttl_page, contains_title, contains_text);
	}
	private byte[] Bld_contains_text(Xow_msg_mgr msg_mgr, int count_subcs, int count_pages, int count_files) {
		if (count_subcs == 0 && count_pages == 0 && count_files == 0) return Bry_.Empty;
		tmp_bfr.Add_byte(Byte_ascii.Paren_bgn);
		Bld_contains_text_itm(tmp_bfr, msg_mgr, Xol_msg_itm_.Id_ctgtree_subc_counts_ctg, count_subcs);
		Bld_contains_text_itm(tmp_bfr, msg_mgr, Xol_msg_itm_.Id_ctgtree_subc_counts_page, count_pages);
		Bld_contains_text_itm(tmp_bfr, msg_mgr, Xol_msg_itm_.Id_ctgtree_subc_counts_file, count_files);
		tmp_bfr.Add_byte(Byte_ascii.Paren_end);
		return tmp_bfr.To_bry_and_clear();
	}
	private void Bld_contains_text_itm(Bry_bfr bfr, Xow_msg_mgr msg_mgr, int msg_id, int val) {
		if (val == 0) return;
		if (bfr.Len() > 1) bfr.Add(Bld_contains_text_itm_dlm);	// NOTE: 1 b/c Paren_bgn is always added
		bfr.Add(msg_mgr.Val_by_id_args(msg_id, val));
	}	private static final    byte[] Bld_contains_text_itm_dlm = Bry_.new_a7(", "); 		
	private static final    Bry_fmt
	  fmt_exists = Bry_fmt.Auto_nl_skip_last
	( ""
	, "            <li>"
	, "              <div class=\"CategoryTreeSection\">"
	, "                <div class=\"CategoryTreeItem\">"
	, "                  <span class=\"CategoryTreeBullet\">"
	, "                    <span class=\"CategoryTreeToggle\" style=\"display: none;\" data-ct-title=\"~{itm_data_title}\" title=\"~{itm_title}\" data-ct-state=\"collapsed\">"
	, "                    </span> "
	, "                  </span>"
	, "                  <a href=\"~{itm_href}\" class=\"" + Xoa_ctg_mgr.Html__cls__str + "\">~{itm_text}"
	, "                  </a>"
	, "                  <span title=\"~{itm_contains_title}\" dir=\"ltr\">~{itm_contains_text}"
	, "                  </span>"
	, "                </div>"
	, "                <div class=\"CategoryTreeChildren\" style=\"display:none\"></div>"
	, "              </div>"
	, "            </li>"
	);
}
