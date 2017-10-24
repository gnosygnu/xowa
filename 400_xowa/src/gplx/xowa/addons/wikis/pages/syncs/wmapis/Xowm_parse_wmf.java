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
package gplx.xowa.addons.wikis.pages.syncs.wmapis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*;
import gplx.langs.jsons.*;
import gplx.xowa.files.downloads.*;
import gplx.xowa.apps.wms.apis.*;
public class Xowm_parse_wmf {
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private final    Json_parser json_parser = new Json_parser();
	public Xowm_parse_data Get_parse_or_null(Xof_download_wkr download_wkr, Xow_wiki wiki, Xoa_ttl page_ttl) {
		if (!gplx.core.ios.IoEngine_system.Web_access_enabled) return null;
		byte[] wiki_domain = wiki.Domain_bry();
		byte[] page_full_db = page_ttl.Full_db();

		// get core json
		byte[] core_json = Core_download(bfr, download_wkr, wiki_domain, page_full_db);
		Xowm_parse_data rv = Core_parse(json_parser, wiki_domain, page_full_db, core_json);

		// get redirect json
		byte[] redirect_json = Redirect_download(bfr, download_wkr, wiki_domain, page_full_db);
		Redirect_parse(rv, json_parser, wiki_domain, page_full_db, redirect_json);

		return rv;
	}
	private static byte[] Core_download(Bry_bfr bfr, Xof_download_wkr download_wkr, byte[] wiki_domain, byte[] page_full_db) {
		// build url; EX: "https://en.wikipedia.org/w/api.php?action=parse&format=json&redirects=1&page=Wikipedia:Main%20Page"
		Xowm_api_bldr.Bld_bgn(bfr, wiki_domain);
		bfr.Add_str_a7("action=parse&format=json&page=");	// NOTE:do not add redirects=1; want to get "actual" html, not "redirected" html; DATE:2017-05-06
		bfr.Add(page_full_db);

		// download
		return download_wkr.Download_xrg().Exec_as_bry(bfr.To_str_and_clear());
	}
	private static Xowm_parse_data Core_parse(Json_parser json_parser, byte[] wiki_domain, byte[] page_full_db, byte[] json) {
		Json_doc jdoc = json_parser.Parse(json);

		// get data
		Json_nde parse_nde = jdoc.Root_nde().Get_as_nde("parse");
		if (parse_nde == null) return null;	// handle pages that don't exist such as s.w:File:AnyFile.png; DATE:2016-11-15
		int page_id = parse_nde.Get_as_int("pageid");
		int revn_id = parse_nde.Get_as_int("revid");
		byte[] page_ttl = Xoa_ttl.Replace_spaces(parse_nde.Get_as_bry("title"));
		byte[] revn_html = parse_nde.Get_as_nde("text").Get_as_bry("*");
		
		return new Xowm_parse_data(wiki_domain, page_id, page_ttl, revn_id, revn_html);
	}
	private static byte[] Redirect_download(Bry_bfr bfr, Xof_download_wkr download_wkr, byte[] wiki_domain, byte[] page_full_db) {
		// build url; EX: "https://en.wikipedia.org/w/api.php?action=parse&format=json&redirects=1&page=EARTH&prop="
		Xowm_api_bldr.Bld_bgn(bfr, wiki_domain);
		bfr.Add_str_a7("action=parse&format=json&redirects=1&prop=&page=");	// NOTE:"prop=" will ignore all data except for redirect data
		bfr.Add(page_full_db);

		// download
		return download_wkr.Download_xrg().Exec_as_bry(bfr.To_str_and_clear());
	}
	private static void Redirect_parse(Xowm_parse_data rv, Json_parser json_parser, byte[] wiki_domain, byte[] page_full_db, byte[] json) {
		Json_doc jdoc = json_parser.Parse(json);

		// get data; "parse { redirects [{from, to}] }"
		Json_nde parse_nde = jdoc.Root_nde().Get_as_nde("parse");
		if (parse_nde == null) return;	// handle pages that don't exist such as s.w:File:AnyFile.png; DATE:2016-11-15
		Json_ary redirects_nde = parse_nde.Get_as_ary("redirects");
		if (redirects_nde == null || redirects_nde.Len() == 0) return;

		Json_nde redirect_nde = redirects_nde.Get_as_nde(0);
		byte[] redirect_to_bry = redirect_nde.Get_as_bry("to");
		if (redirect_to_bry != null)
			rv.Redirect_to_ttl = redirect_to_bry;
	}
}
