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
import gplx.langs.jsons.*; import gplx.core.net.*;
class Xowm_rev_wkr__meta__wm implements Xowm_rev_wkr__meta {
	private final    Json_parser json_parser = new Json_parser();
	private final    Xowm_json_parser__page json_page_parser = new Xowm_json_parser__page();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(255);
	private final    Wmapi_itm__pge tmp_pge = new Wmapi_itm__pge();
	public Xowm_rev_wkr__meta__wm() {
		tmp_pge.Rvn_ary_(new Wmapi_itm__rvn());
	}
	public Gfo_inet_conn Inet_conn() {return inet_conn;} public void Inet_conn_(Gfo_inet_conn v) {this.inet_conn = v;} private Gfo_inet_conn inet_conn;
	public void Fetch_meta(String domain_str, Ordered_hash hash, int bgn, int end) {
		for (int i = bgn; i < end; ++i) {
			Wmapi_itm__pge itm = (Wmapi_itm__pge)hash.Get_at(i);
			if (i != bgn) tmp_bfr.Add_byte(Byte_ascii.Pipe);
			tmp_bfr.Add(itm.Page_ttl());
		}
		byte[] json = inet_conn.Download_as_bytes_or_null(Xowm_api_mgr.Bld_api_url(domain_str, "action=query&prop=revisions&rvprop=size|ids|timestamp|user|comment&format=json&rawcontinue=titles=" + tmp_bfr.To_str_and_clear()));
		Parse_doc(hash, json);
	}
	private void Parse_doc(Ordered_hash hash, byte[] json) {
		Json_doc jdoc = json_parser.Parse(json);
		Json_nde pages_nde = Json_nde.cast(jdoc.Get_grp_many(Jpath__query_pages));
		int len = pages_nde.Len();
		for (int i = 0; i < len; ++i) {
			Parse_page(hash, pages_nde.Get_at_as_kv(i).Val_as_nde());
		}
	}
	private void Parse_page(Ordered_hash hash, Json_nde page_nde) {
		json_page_parser.Parse("update", tmp_pge, page_nde);	// have to pass tmp_pge, b/c don't know which itm is in hash
		Wmapi_itm__pge hash_itm = (Wmapi_itm__pge)hash.Get_by(tmp_pge.Page_ttl()); if (hash_itm == null) return;
		hash_itm.Init_id(tmp_pge.Page_id());
		Wmapi_itm__rvn tmp_rvn = tmp_pge.Rvn_itm_last();
		hash_itm.Rvn_ary_(new Wmapi_itm__rvn());
		hash_itm.Rvn_itm_last().Init(tmp_rvn.Rvn_id(), tmp_rvn.Rvn_len(), tmp_rvn.Rvn_time(), tmp_rvn.Rvn_user(), tmp_rvn.Rvn_note());
	}
	private static final    byte[][] Jpath__query_pages = Bry_.Ary("query", "pages");
}
