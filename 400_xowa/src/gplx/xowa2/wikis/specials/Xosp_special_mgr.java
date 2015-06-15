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
package gplx.xowa2.wikis.specials; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.wikis.*;
import gplx.xowa.*; import gplx.xowa2.gui.*;
import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.file_browsers.*;
public class Xosp_special_mgr {
	private final Xowv_wiki wiki;
	private final Hash_adp_bry hash;
	public Xosp_special_mgr(Xowv_wiki wiki) {
		this.wiki = wiki;
//			this.hash = Hash_adp_bry.ci_utf8_(wiki.Lang().Case_mgr());
		this.hash = Hash_adp_bry.cs_();
	}
	public void Get_by_ttl(Xog_page rv, Gfo_url url, Xoa_ttl ttl) {
		Xosp_fbrow_rslt rslt = Xosp_fbrow_special.Gen(url.Args(), wiki.Appv().Wiki_mgr());
		rv.Init(wiki, -1, null, ttl);
		rv.Page_body_(rslt.Html_body());
		rv.Html_head_xtn_(rslt.Html_head());
	}
	public void Get_by_url(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		int slash_pos = Bry_finder.Find_fwd(ttl.Page_txt_wo_qargs(), Xoa_ttl.Subpage_spr);	// check for slash
		byte[] special_name = slash_pos == Bry_.NotFound
				? ttl.Base_txt_wo_qarg()							// no slash found; use base_txt; ignore qry args and just get page_names; EX: Search/Earth?fulltext=y; Allpages?from=Earth...
				: Bry_.Mid(ttl.Page_txt_wo_qargs(), 0, slash_pos);	// slash found; use root page; EX: Special:ItemByTitle/enwiki/Earth
		Object o = hash.Get_by_bry(special_name);
		if (o == null) {
			gplx.xowa.Xol_specials_itm special_itm = wiki.Lang().Specials_mgr().Get_by_alias(special_name);
			if (special_itm != null)
				o = hash.Get_by_bry(special_itm.Special());
		}
		if (o != null) {
			// Xows_page special = (Xows_page)o;
			// page.Revision_data().Modified_on_(DateAdp_.Now());
			// special.Special_gen(wiki, page, url, ttl);
		}
	}
}
