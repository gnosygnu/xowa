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
package gplx.xowa.bldrs.wms.revs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
import gplx.langs.jsons.*;
class Wmapi_itm_json_wtr {
	public Json_wtr Wtr() {return wtr;} private final Json_wtr wtr = new Json_wtr();
	public byte[] To_bry(Wmapi_itm__pge[] ary) {
		wtr.Clear();
		Write_query(ary);
		return wtr.To_bry_and_clear();
	}
	private void Write_query(Wmapi_itm__pge[] ary) {
		wtr.Doc_nde_bgn();
		wtr.Nde_bgn("query");
		wtr.Nde_bgn("pages");
		for (Wmapi_itm__pge itm : ary)
			Write_page(itm);
		wtr.Nde_end();
		wtr.Nde_end();
		wtr.Doc_nde_end();
	}
	private void Write_page(Wmapi_itm__pge itm) {
		wtr.Nde_bgn(Int_.To_str(itm.Page_id()));
		wtr.Kv_int("pageid", itm.Page_id());
		wtr.Kv_int("ns", itm.Page_ns());
		wtr.Kv_bry("title", itm.Page_ttl());
		Write_sub_rvns(itm.Rvn_ary());
		Write_sub_ttls(itm.Tml_ary(), "templates");
		Write_sub_ttls(itm.Img_ary(), "images");
		Write_sub_ctgs(itm.Ctg_ary());
		wtr.Nde_end();
	}
	private void Write_sub_rvns(Wmapi_itm__rvn[] rvn_ary) {
		int len = rvn_ary.length; if (len == 0) return;
		wtr.Ary_bgn("revisions");
		for (int i = 0; i < len; ++i) {
			Wmapi_itm__rvn rvn = rvn_ary[i];
			wtr.Doc_nde_bgn();
			wtr.Kv_int("revid", rvn.Rvn_id());
			wtr.Kv_int("parentid", -1);
			wtr.Kv_bry("timestamp", rvn.Rvn_time());
			wtr.Kv_int("size", rvn.Rvn_len());
			wtr.Kv_bry("user", rvn.Rvn_user());
			wtr.Kv_bry("parsedcomment", rvn.Rvn_note());
			wtr.Doc_nde_end();
		}
		wtr.Ary_end();
	}
	private void Write_sub_ttls(Wmapi_itm__ttl[] ttl_ary, String ary_name) {
		int len = ttl_ary.length; if (len == 0) return;
		wtr.Ary_bgn(ary_name);
		for (int i = 0; i < len; ++i) {
			Wmapi_itm__ttl ttl = ttl_ary[i];
			wtr.Doc_nde_bgn();
			wtr.Kv_int("ns", ttl.Ns_id());
			wtr.Kv_bry("title", ttl.Ttl_bry());
			wtr.Doc_nde_end();
		}
		wtr.Ary_end();
	}
	private void Write_sub_ctgs(Wmapi_itm__ctg[] ctg_ary) {
		int len = ctg_ary.length; if (len == 0) return;
		wtr.Ary_bgn("categories");
		for (int i = 0; i < len; ++i) {
			Wmapi_itm__ctg ctg = ctg_ary[i];
			wtr.Doc_nde_bgn();
			wtr.Kv_int("ns", ctg.Ctg_ns());
			wtr.Kv_bry("title", ctg.Ctg_ttl());
			wtr.Kv_bry("sortkey", ctg.Ctg_sortkey());
			wtr.Kv_bool_as_mw("sortkeyprefix", ctg.Ctg_sortprefix());
			wtr.Kv_bry("timestamp", ctg.Ctg_time());
			wtr.Kv_bool_as_mw("hidden", ctg.Ctg_hidden());
			wtr.Doc_nde_end();
		}
		wtr.Ary_end();
	}
}
