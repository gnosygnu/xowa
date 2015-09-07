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
package gplx.xowa.wmfs.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
import gplx.core.json.*;
class Xowm_update_meta_wkr__wm {
	private final Json_parser json_parser = new Json_parser();
	private final Xowm_page_nde_parser nde_parser = new Xowm_page_nde_parser();
	private final Bry_bfr tmp_bfr = Bry_bfr.new_(255);
	private final Xowm_rev_itm tmp_rev_itm = new Xowm_rev_itm(null);
	public void Fetch_meta(Ordered_hash hash, int bgn, int end) {
		for (int i = bgn; i < end; ++i) {
			Xowm_rev_itm itm = (Xowm_rev_itm)hash.Get_at(i);
			if (i != bgn) tmp_bfr.Add_byte(Byte_ascii.Pipe);
			tmp_bfr.Add(itm.Ttl().Full_db());
		}
		byte[] json = Get_json(tmp_bfr.Xto_bry_and_clear());
		Parse_doc(hash, json);
	}
	private byte[] Get_json(byte[] titles_arg) {
		return null;
	}
	private void Parse_doc(Ordered_hash hash, byte[] json) {
		Json_doc jdoc = json_parser.Parse(json);
		Json_nde pages_nde = Json_nde.cast(jdoc.Get_grp(Jpath__query_pages));
		int len = pages_nde.Len();
		for (int i = 0; i < len; ++i) {
			Parse_page(hash, pages_nde.Get_at_as_kv(i).Val_as_nde());
		}
	}
	private void Parse_page(Ordered_hash hash, Json_nde page_nde) {
		nde_parser.Parse("update", tmp_rev_itm, page_nde);
	}
	private static final byte[][] Jpath__query_pages = Bry_.Ary("query", "pages");
}
class Xowm_page_nde_parser extends Json_parser__list_nde__base {
	private Xowm_rev_itm itm;
	private Xowm_rev_nde_parser rev_nde_parser = new Xowm_rev_nde_parser();
	private String nde_context;
	public Xowm_page_nde_parser() {
		this.Ctor("page_id", "ns", "title", "revisions");
	}
	public void Parse(String context, Xowm_rev_itm itm, Json_nde nde) {
		this.itm = itm;
		this.nde_context = context + ".page";
		this.Parse_nde(context, nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		int page_id = Kv__int(atrs, 0);
		Json_ary revs_ary = atrs[3].Val_as_ary(); if (revs_ary.Len() == 0) throw Err_.new_("rev.parser", "no revisions found", sub.Doc().Src());
		Json_nde rev_nde = revs_ary.Get_at_as_nde(0);
		rev_nde_parser.Parse(nde_context, itm, rev_nde, page_id);
	}
}
class Xowm_rev_nde_parser extends Json_parser__list_nde__base {
	private Xowm_rev_itm itm; private int page_id;
	public Xowm_rev_nde_parser() {
		this.Ctor("revid", "parentid", "timestamp", "size", "contentformat", "contentmodel", "user", "comment", "*");
	}
	public void Parse(String context, Xowm_rev_itm itm, Json_nde nde, int page_id) {
		this.itm = itm; this.page_id = page_id;
		this.Parse_nde(context + ".rev", nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		itm.Init_meta(page_id, Kv__int(atrs, 0), Kv__int(atrs, 3), Kv__bry(atrs, 2), Kv__bry(atrs, 5), Kv__bry(atrs, 6));
	}
}
