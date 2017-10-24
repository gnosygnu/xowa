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
package gplx.xowa.specials.deletes; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.xowa.specials.*; import gplx.xowa.wikis.nss.*;
import gplx.core.net.qargs.*;
public class Xodel_page_special implements Xow_special_page {
	public void Special__gen(Xow_wiki wikii, Xoa_page pagei, Xoa_url url, Xoa_ttl ttl) {
		Xowe_wiki wiki = (Xowe_wiki)wikii; Xoae_page page = (Xoae_page)pagei;

		Gfo_qarg_mgr url_args = new Gfo_qarg_mgr().Init(url.Qargs_ary());
		byte[] page_title_bry = url_args.Read_bry_or_fail("delete");
		Xoa_ttl page_title = wiki.Ttl_parse(page_title_bry);

		gplx.xowa.addons.wikis.directorys.specials.items.bldrs.Xopg_db_mgr.Delete(wiki, page_title);

		wiki.Data_mgr().Redirect(page, wiki.Props().Main_page());
	}

	public static final String SPECIAL_KEY = "XowaPageDelete";
	public static final    byte[] Display_ttl = Bry_.new_a7("Delete Page");
	public Xow_special_meta Special__meta() {return new Xow_special_meta(Xow_special_meta_.Src__mw, SPECIAL_KEY);}
	public static final    Xow_special_page Prototype = new Xodel_page_special();
	public Xow_special_page Special__clone() {return this;}
}
