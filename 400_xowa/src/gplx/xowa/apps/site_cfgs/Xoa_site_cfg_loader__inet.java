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
package gplx.xowa.apps.site_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.core.net.*; import gplx.xowa.bldrs.wms.*;
import gplx.langs.jsons.*;
import gplx.xowa.wikis.domains.*;
public class Xoa_site_cfg_loader__inet implements Xoa_site_cfg_loader {
	private final    Gfo_inet_conn inet_conn; private final    Json_parser json_parser;
	private String api_url; private boolean call_api = true; private Json_doc jdoc;
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public Xoa_site_cfg_loader__inet(Gfo_inet_conn inet_conn, Json_parser json_parser) {this.inet_conn = inet_conn; this.json_parser = json_parser;}
	public int Tid() {return Xoa_site_cfg_loader_.Tid__inet;}
	public void Load_csv__bgn(Xoa_site_cfg_mgr mgr, Xow_wiki wiki) {
		this.call_api = true;
		this.jdoc = null;
		this.api_url = Bld_url(tmp_bfr, wiki.Domain_str(), mgr.Data_hash(), mgr.Itm_ary());
	}
	public String Api_url() {return api_url;}
	public byte[] Load_csv(Xoa_site_cfg_mgr mgr, Xow_wiki wiki, Xoa_site_cfg_itm__base itm) {
		if (Int_.In(wiki.Domain_tid(), Xow_domain_tid_.Tid__home, Xow_domain_tid_.Tid__other)) return null;	// do not call api if "home", "other"; for "wikia" and other non-wm wikis
		if (call_api) {
			call_api = false;
			byte[] json_bry = Xowm_api_mgr.Call_by_url(Xoa_app_.Usr_dlg(), inet_conn, wiki.Domain_str(), api_url);
			if (json_bry != null) jdoc = json_parser.Parse(json_bry);
		}
		if (jdoc == null) return null;
		Json_itm js_itm = jdoc.Get_grp_many(Bry__query, itm.Key_bry()); if (js_itm == null) return null;
		return itm.Parse_json(wiki, js_itm);
	}
	public static String Bld_url(Bry_bfr tmp_bfr, String domain_str, Hash_adp_bry db_hash, Xoa_site_cfg_itm__base[] itm_ary) {
		boolean first = true;
		int len = itm_ary.length;
		for (int i = 0; i < len; ++i) {
			Xoa_site_cfg_itm__base itm = itm_ary[i];
			// if (db_hash.Has(itm_key)) continue;	// TOMBSTONE: always add itm to url, even if in db; note that fallback gets saved to db; DATE:2016-04-13
			if (first)
				first = false;
			else
				tmp_bfr.Add_byte_pipe();
			tmp_bfr.Add(itm.Key_bry());
		}			
		return first ? null : Xowm_api_mgr.Bld_api_url(domain_str, Qarg__bgn + tmp_bfr.To_str_and_clear());
	}
	private static final    byte[] Bry__query = Bry_.new_a7("query");
	public static final String
	  Qarg__all							= "action=query&format=json&rawcontinue=&meta=siteinfo&siprop=general|namespaces|statistics|interwikimap|namespacealiases|specialpagealiases|libraries|extensions|skins|magicwords|functionhooks|showhooks|extensiontags|protocols|defaultoptions|languages"
	, Qarg__bgn							= "action=query&format=json&rawcontinue=&meta=siteinfo&siprop="
	, Qarg__extensiontags				= "extensiontags"
	, Qarg__interwikimap				= "interwikimap"
	;
}
