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
package gplx.xowa.wmfs.revs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
import gplx.core.json.*;
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
