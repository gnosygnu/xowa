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
		Xowd_db_file html_db = wiki.Data__core_mgr().Db__html();
		if (html_db == null) {
			html_db = wiki.Data__core_mgr().Db__core();
			html_db.Tbl__html().Create_tbl();
		}
		byte[] html_bry = Bry_.new_u8(html);
		Xoh_page gui_page = new Xoh_page();
		gui_page.Body_(html_bry);
		byte[] data = html_bry;
		html_db.Tbl__html().Insert(page_id, 0, gplx.core.ios.Io_stream_.Tid_raw, gplx.xowa.htmls.core.hzips.Xoh_hzip_dict_.Hzip__none, Bry_.Empty, Bry_.Empty, Bry_.Empty, data);
	}
}
