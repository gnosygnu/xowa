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
		byte[] json = Download(bfr, download_wkr, wiki_domain, page_full_db);
		return Parse(json_parser, wiki_domain, page_full_db, json);
	}
	private static byte[] Download(Bry_bfr bfr, Xof_download_wkr download_wkr, byte[] wiki_domain, byte[] page_full_db) {
		// build url; EX: "https://en.wikipedia.org/w/api.php?action=parse&format=json&redirects=1&page=Wikipedia:Main%20Page"
		Xowm_api_bldr.Bld_bgn(bfr, wiki_domain);
		bfr.Add_str_a7("action=parse&format=json&redirects=1&page=");	// NOTE:redirects=1 needed to resolve redirects
		bfr.Add(page_full_db);

		// download
		return download_wkr.Download_xrg().Exec_as_bry(bfr.To_str_and_clear());
	}
	private static Xowm_parse_data Parse(Json_parser json_parser, byte[] wiki_domain, byte[] page_full_db, byte[] json) {
		Json_doc jdoc = json_parser.Parse(json);

		// get data
		Json_nde parse_nde = jdoc.Root_nde().Get_as_nde("parse");
		if (parse_nde == null) return null;	// handle pages that don't exist such as s.w:File:AnyFile.png; DATE:2016-11-15
		int page_id = parse_nde.Get_as_int("pageid");
		int revn_id = parse_nde.Get_as_int("revid");
		byte[] page_ttl = Xoa_ttl.Replace_spaces(parse_nde.Get_as_bry("title"));
		byte[] redirect_src = Bry_.Eq(page_ttl, page_full_db) ? null : page_full_db;
		byte[] revn_html = parse_nde.Get_as_nde("text").Get_as_bry("*");
		
		return new Xowm_parse_data(wiki_domain, page_id, page_ttl, redirect_src, revn_id, revn_html);
	}
}
