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
package gplx.xowa.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.langs.msgs.*;
class Xoctg_fmtr_itm_subc extends Xoctg_fmtr_itm_base {
	@Override public void Bld_html(Bry_bfr bfr, Xowe_wiki wiki, Xoh_wtr_ctx hctx, Xoctg_view_itm itm, Xoa_ttl ttl, byte[] ttl_page, Xoh_href_parser href_parser, Bry_fmtr html_itm) {
		byte[] itm_href = wiki.Html__href_wtr().Build_to_bry(wiki, ttl);
		int sub_ctgs = 0;	// itm.Subs_ctgs();
		int sub_pages = 0;	// itm.Subs_pages();
		int sub_files = 0;	// itm.Subs_files();
		byte[] contains_title = msg_mgr.Val_by_id_args(Xol_msg_itm_.Id_ctgtree_subc_counts, sub_ctgs, sub_pages, sub_files);
		byte[] contains_text = Bld_contains_text(sub_ctgs, sub_pages, sub_files);
		html_itm.Bld_bfr_many(bfr, ttl.Page_db(), ttl_page, itm_href, ttl_page, contains_title, contains_text);
	}
	byte[] Bld_contains_text(int sub_ctgs, int sub_pages, int sub_files) {
		if (sub_ctgs == 0 && sub_pages == 0 && sub_files == 0) return Bry_.Empty;
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b128();
		bfr.Add_byte(Byte_ascii.Paren_bgn);
		Bld_contains_text_itm(bfr, Xol_msg_itm_.Id_ctgtree_subc_counts_ctg, sub_ctgs);
		Bld_contains_text_itm(bfr, Xol_msg_itm_.Id_ctgtree_subc_counts_page, sub_pages);
		Bld_contains_text_itm(bfr, Xol_msg_itm_.Id_ctgtree_subc_counts_file, sub_files);
		bfr.Add_byte(Byte_ascii.Paren_end);
		return bfr.To_bry_and_rls();
	}
	private void Bld_contains_text_itm(Bry_bfr bfr, int msg_id, int val) {
		if (val == 0) return;
		if (bfr.Len() > 1) bfr.Add(Bld_contains_text_itm_dlm);	// NOTE: 1 b/c Paren_bgn is always added
		bfr.Add(msg_mgr.Val_by_id_args(msg_id, val));
	}	static final    byte[] Bld_contains_text_itm_dlm = Bry_.new_a7(", "); 		
}
