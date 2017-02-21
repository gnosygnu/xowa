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
package gplx.xowa.bldrs.wms.revs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
import gplx.langs.jsons.*;
class Xowm_json_parser__page extends Json_parser__list_nde__base {
	private Wmapi_itm__pge pge;
	private Xowm_json_parser__rev rev_nde_parser = new Xowm_json_parser__rev();
	private String nde_context;
	public Xowm_json_parser__page() {
		this.Ctor("pageid", "ns", "title", "revisions");
	}
	public void Parse(String context, Wmapi_itm__pge pge, Json_nde nde) {
		this.pge = pge;
		this.nde_context = context + ".page";
		this.Parse_nde(context, nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		int page_id = Kv__int(atrs, 0);
		Json_ary revs_ary = atrs[3].Val_as_ary(); if (revs_ary.Len() == 0) throw Err_.new_("rev.parser", "no revisions found", sub.Doc().Src());
		Json_nde rev_nde = revs_ary.Get_at_as_nde(0);
		pge.Init_id(page_id);
		pge.Init_ttl(Kv__int(atrs, 1), Kv__bry(atrs, 2));
		rev_nde_parser.Parse(nde_context, pge.Rvn_itm_last(), rev_nde);
	}
}
class Xowm_json_parser__rev extends Json_parser__list_nde__base {
	private Wmapi_itm__rvn rvn;
	public Xowm_json_parser__rev() {
		this.Ctor("revid", "parentid", "user", "anon", "timestamp", "size", "parsedcomment");// , "contentformat", "contentmodel", "*"
	}
	public void Parse(String context, Wmapi_itm__rvn rvn, Json_nde nde) {
		this.rvn = rvn;
		this.Parse_nde(context + ".rev", nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		rvn.Init(Kv__int(atrs, 0), Kv__int(atrs, 5), Kv__bry(atrs, 4), Kv__bry(atrs, 2), Kv__bry(atrs, 6));
	}
}
