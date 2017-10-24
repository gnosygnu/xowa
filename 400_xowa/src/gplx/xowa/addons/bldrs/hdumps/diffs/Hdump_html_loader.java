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
package gplx.xowa.addons.bldrs.hdumps.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.hdumps.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.htmls.*;
class Hdump_html_loader {
	private final    Xowe_wiki wiki;
	private Io_stream_zip_mgr stream_zip_mgr = new Io_stream_zip_mgr();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    Xoh_page tmp_hpg = new Xoh_page();
	private final    Xoa_url tmp_url; private final    Xoa_ttl tmp_ttl;

	public Hdump_html_loader(Xowe_wiki wiki) {
		this.wiki = wiki;
		this.tmp_url = wiki.Utl__url_parser().Parse(Bry_.new_a7("temp"));
		this.tmp_ttl = wiki.Ttl_parse(Bry_.new_a7("temp"));
	}

	public byte[] Load(int page_id, int html_db_id) {
		// load html_bry from db
		Xow_db_file html_db = wiki.Data__core_mgr().Dbs__get_by_id_or_fail(html_db_id);
		tmp_hpg.Ctor_by_hview(wiki, tmp_url, tmp_ttl, page_id);
		html_db.Tbl__html().Select_by_page(tmp_hpg);

		// unzip it
		byte[] html_hzip = stream_zip_mgr.Unzip(Io_stream_tid_.Tid__gzip, tmp_hpg.Db().Html().Html_bry());
		return wiki.Html__hdump_mgr().Load_mgr().Decode_as_bry(tmp_bfr, tmp_hpg, html_hzip, true);
	}
}
