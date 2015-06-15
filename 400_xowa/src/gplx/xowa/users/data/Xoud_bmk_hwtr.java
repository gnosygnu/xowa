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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.xowa.wikis.*; import gplx.xowa.html.wtrs.*;
class Xoud_bmk_hwtr implements Bry_fmtr_arg {
//		private Xoa_wiki_mgr wiki_mgr; private Xoh_lnki_bldr lnki_bldr;
//		private Xoa_url_parser url_parser;
//		private Xoud_bmk_row[] row_ary;
	public void Init_by_app(Xoa_app app) {
//			Xoae_app app; app.Url_parser()
//			this.wiki_mgr = app.Wiki_mgri();
//			this.lnki_bldr = app.Html__lnki_bldr();
	}
	public void Write(Bry_bfr bfr, Xoud_bmk_row[] row_ary) {
//			this.row_ary = row_ary;
		grp_fmtr.Bld_bfr_many(bfr, this);
	}
	public void XferAry(Bry_bfr bfr, int idx) {
//			int row_ary_len = row_ary.length;
//			for (int i = 0; i < row_ary_len; ++i) {
//				Xoud_bmk_row row = row_ary[i];
//				byte[] wiki_bry = Bry_.new_u8(row.Wiki());
//				Xow_wiki wiki = wiki_mgr.Get_by_key_or_make_2(wiki_bry);
//				byte[] page_bry = Bry_.new_u8(row.Page());
//				byte[] page_url = lnki_bldr.Href_(wiki, page_bry).Caption_(Xoa_ttl.Replace_unders(page_bry)).Bld_to_bry();
//				row_fmtr.Bld_bfr_many(bfr, wiki_bry, page_url, row.Anch(), row.Qarg());
//			}
	}
	private static final Bry_fmtr grp_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl
	( "<table class='wikitable'>"
	, "  <tr>"
	, "    <th>Wiki</th>"
	, "    <th>Page</th>"
	, "    <th>Anchor</th>"
	, "    <th>Qargs</th>"
	, "    <th></th>"
	, "  </tr>~{rows}"
	, "</table>"
	), "rows");
//		private static final Bry_fmtr row_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl
//		( "  <tr>"
//		, "    <td>~{wiki}</td>"
//		, "    <td>~{page}</td>"
//		, "    <td>~{anchor}</td>"
//		, "    <td>~{qarg}</td>"
//		, "    <td>delete_btn</td>"
//		, "  </tr>"
//		), "wiki", "page", "anchor", "qarg");
}
