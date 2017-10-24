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
package gplx.xowa.drds; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.data.*;
import gplx.xowa.htmls.*; import gplx.xowa.wikis.data.tbls.*; 
public class Xowd_data_tstr {
	public void Wiki_(Xow_wiki wiki) {this.wiki = wiki;} private Xow_wiki wiki;
	public void Page__insert(int page_id, String ttl_str, String modified_on) {Page__insert(page_id, ttl_str, modified_on, Bool_.N, 0, page_id, 0, 0);}
	public void Page__insert(int page_id, String ttl_str, String modified_on, boolean page_is_redirect, int page_len, int random_int, int text_db_id, int html_db_id) {
		Xoa_ttl ttl = wiki.Ttl_parse(Bry_.new_u8(ttl_str));
		wiki.Data__core_mgr().Tbl__page().Insert(page_id, ttl.Ns().Id(), ttl.Page_db(), page_is_redirect, DateAdp_.parse_iso8561(modified_on), page_len, page_id, text_db_id, html_db_id);
	}
	public void Html__insert(int page_id, String html) {
		Xow_db_file html_db = wiki.Data__core_mgr().Db__html();
		if (html_db == null) {
			html_db = wiki.Data__core_mgr().Db__core();
			html_db.Tbl__html().Create_tbl();
		}
		byte[] html_bry = Bry_.new_u8(html);
		Xoh_page hpg = new Xoh_page();
		hpg.Db().Html().Html_bry_(html_bry);
		byte[] data = html_bry;
		html_db.Tbl__html().Insert(page_id, 0, gplx.core.ios.streams.Io_stream_tid_.Tid__raw, gplx.xowa.htmls.core.hzips.Xoh_hzip_dict_.Hzip__none, Bry_.Empty, Bry_.Empty, Bry_.Empty, data);
	}
	public void Text__insert(int page_id, String text) {
		Xow_db_file db = wiki.Data__core_mgr().Db__text();
		if (db == null) {
			db = wiki.Data__core_mgr().Db__core();
			db.Tbl__text().Create_tbl();
		}
			db.Tbl__text().Create_tbl();
		byte[] text_bry = Bry_.new_u8(text);
		db.Tbl__text().Insert_bgn();
		db.Tbl__text().Insert_cmd_by_batch(page_id, text_bry);
		db.Tbl__text().Insert_end();
	}
}
