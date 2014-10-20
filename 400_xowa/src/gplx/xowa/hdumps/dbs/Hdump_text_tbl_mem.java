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
package gplx.xowa.hdumps.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import gplx.dbs.*;
public class Hdump_text_tbl_mem extends Hdump_text_tbl { 	private HashAdp pages = HashAdp_.new_();
	@Override public int Insert(int page_id, int tid, byte[] data) {
		Hdump_text_row row = new Hdump_text_row(page_id, tid, data);
		ListAdp rows = Get_or_new(pages, page_id);
		rows.Add(row);
		return data.length;
	}
	@Override public void Select_by_page(ListAdp rv, int page_id) {
		ListAdp rows = Get_or_new(pages, page_id);
		int len = rows.Count();
		for (int i = 0; i < len; ++i) {
			Hdump_text_row row = (Hdump_text_row)rows.FetchAt(i);
			rv.Add(row);
		}
	}
	@Override public void Delete_by_page(int page_id) {pages.Del(page_id);}
	private static ListAdp Get_or_new(HashAdp pages, int page_id) {
		ListAdp rv = (ListAdp)pages.Fetch(page_id);
		if (rv == null) {
			rv = ListAdp_.new_();
			pages.Add(page_id, rv);
		}
		return rv;
	}
	public static final Db_provider Null_provider = null;
}
