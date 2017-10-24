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
package gplx.xowa.specials.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.core.net.*;
import gplx.xowa.htmls.*;
import gplx.xowa.wikis.*;
import gplx.xowa.langs.specials.*;
public class Xosp_special_mgr {
//		private final    Xowv_wiki wiki;
	private final    Hash_adp_bry hash;
	public Xosp_special_mgr(Xowv_wiki wiki) {
//			this.wiki = wiki;
		// hash.Add_str_obj(Xow_special_meta_.Ttl__statistics				, page_statistics);
		this.hash = Hash_adp_bry.cs();
	}
	public void Get_by_ttl(Xoh_page rv, Gfo_url url, Xoa_ttl ttl) {
//			Xosp_fbrow_rslt rslt = Xosp_fbrow_special.Gen(url.Qargs(), wiki.Appv().Wiki_mgr());
//			rv.Init(wiki, null, ttl, -1);
//			rv.Body_(rslt.Html_body());
//			rv.Html_head_xtn_(rslt.Html_head());
	}
	public void Get_by_url1(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		int slash_pos = Bry_find_.Find_fwd(ttl.Page_txt_wo_qargs(), Xoa_ttl.Subpage_spr);	// check for slash
		byte[] special_name = slash_pos == Bry_find_.Not_found
				? ttl.Base_txt_wo_qarg()							// no slash found; use base_txt; ignore qry args and just get page_names; EX: Search/Earth?fulltext=y; Allpages?from=Earth...
				: Bry_.Mid(ttl.Page_txt_wo_qargs(), 0, slash_pos);	// slash found; use root page; EX: Special:ItemByTitle/enwiki/Earth
		Object o = hash.Get_by_bry(special_name);
		if (o == null) {
			Xol_specials_itm special_itm = wiki.Lang().Specials_mgr().Get_by_alias(special_name);
			if (special_itm != null)
				o = hash.Get_by_bry(special_itm.Special());
		}
		if (o != null) {
			// Xow_special_page special = (Xow_special_page)o;
			// page.Revision_data().Modified_on_(Datetime_now.Get());
			// special.Special__gen(wiki, page, url, ttl);
		}
	}
}
