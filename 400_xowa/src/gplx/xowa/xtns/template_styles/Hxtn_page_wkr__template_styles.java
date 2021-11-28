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
package gplx.xowa.xtns.template_styles; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.dbs.*;
import gplx.xowa.htmls.*;
import gplx.xowa.htmls.hxtns.pages.*; import gplx.xowa.htmls.hxtns.blobs.*; import gplx.xowa.htmls.hxtns.wikis.*;
public class Hxtn_page_wkr__template_styles implements Hxtn_page_wkr {
	private final Hxtn_blob_tbl page_text_tbl;
	public int Id() {return Hxtn_page_mgr.Id__template_styles;}
	public String Key() {return "xowa.xtns.template_styles";}
	public Hxtn_page_wkr__template_styles(Hxtn_blob_tbl page_text_tbl) {
		this.page_text_tbl = page_text_tbl;
	}
	public void Load_by_page(Xoh_page hpg, Xoa_ttl ttl, int page_id) {
		byte[] template_bry = page_text_tbl.Select_text(Hxtn_blob_tbl.Blob_tid__wtxt, Hxtn_wiki_itm.Tid__self, page_id);
		hpg.Html_data().Custom_head_tags().Add(gplx.xowa.wikis.pages.tags.Xopg_tag_itm.New_css_code(template_bry));
	}
}
