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
package gplx.xowa.apps.wms.apis.revisions; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.wms.*; import gplx.xowa.apps.wms.apis.*;
import gplx.langs.jsons.*;
import gplx.xowa.files.downloads.*;
public abstract class Xowm_revision_base {
}
class Xowm_revision_wmf extends Xowm_revision_base {
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private final    Json_parser json_parser = new Json_parser();
	public Xowm_revn_data Get_revn_or_null(Xof_download_wkr download_wkr, Xow_wiki wiki, Xoa_ttl page_ttl) {
		if (!gplx.core.ios.IoEngine_system.Web_access_enabled) return null;
		// create a ctx Object
		return Get_revn_by_page(download_wkr, wiki.Domain_bry(), page_ttl.Full_db());
	}
	private Xowm_revn_data Get_revn_by_page(Xof_download_wkr download_wkr, byte[] wiki_domain, byte[] ttl_full_db) {
		byte[] json = Download(bfr, download_wkr, wiki_domain, ttl_full_db);
		Xowm_revn_data rv = Parse_json(wiki_domain, json);
		// Parse_wtxt(rv, download_wkr, wiki_domain);
		return rv;
	}
	public void Parse_wtxt(Xowe_wiki wiki, byte[] ttl_full_db, byte[] wtxt) {
//			Xoae_page page = Xoae_page.New(wiki, wiki.Ttl_parse(ttl_full_db));
//			page.Db().Text().Text_bry_(wtxt);
//			page.Page_fetcher_(this);
//			wiki.Parser_mgr().Parse(page, true);
	}
	private byte[] Download(Bry_bfr bfr, Xof_download_wkr download_wkr, byte[] wiki_domain, byte[] ttl_full_db) {
		// build api; EX: "https://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=ids|timestamp|content&titles=Main%20Page
		Xowm_api_bldr.Bld_bgn(bfr, wiki_domain);
		bfr.Add_str_a7("action=query&prop=revisions&rvprop=ids|timestamp|content&titles=");
		bfr.Add(ttl_full_db);

		// download bry
		return download_wkr.Download_xrg().Exec_as_bry(bfr.To_str_and_clear());
	}
	private Xowm_revn_data Parse_json(byte[] wiki_domain, byte[] json) {
		Json_doc jdoc = json_parser.Parse(json);

		// get page_data; note that nde has key of page_id; EX: query:{pages:{1234:{}}}
		Json_nde page_nde = (Json_nde)jdoc.Get_grp_many(Path__query, Path__pages);	// /query/pages/
		page_nde = page_nde.Get_as_nde(0);											// {page_id}/
		int page_id = page_nde.Get_as_int("pageid");
		int page_ns = page_nde.Get_as_int("ns");
		byte[] page_ttl = page_nde.Get_as_bry("title");

		// get revn_data
		Json_nde revn_nde = page_nde.Get_as_nde("revisions");
		int revn_id = revn_nde.Get_as_int("revid");
		DateAdp revn_time = revn_nde.Get_as_date_by_utc("timestamp");
		byte[] revn_text = revn_nde.Get_as_bry("*");
		return new Xowm_revn_data(wiki_domain, page_id, page_ns, page_ttl, revn_id, revn_time, revn_text);
	}
	private final    byte[] Path__query = Bry_.new_a7("query"), Path__pages = Bry_.new_a7("pages");
}
/*
{
    "batchcomplete": "",
    "query": {
        "pages": {
            "15580374": {
                "pageid": 15580374,
                "ns": 0,
                "title": "Main Page",
                "revisions": [
                    {
                        "timestamp": "2015-12-26T10:03:53Z",
                        "contentformat": "text/x-wiki",
                        "contentmodel": "wikitext",
                        "*": "[[A]]"
                    }
                ]
            }
        }
    }
*/
