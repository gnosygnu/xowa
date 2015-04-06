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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.data.tbls.*;
public class Xow_page_mgr {
	public void Create(Xowd_page_tbl core_tbl, Xowd_text_tbl text_tbl, int page_id, int ns_id, byte[] ttl_wo_ns, boolean redirect, DateAdp modified_on, byte[] text_zip_data, int text_raw_len, int random_int, int text_db_id, int html_db_id) {
		core_tbl.Insert_cmd_by_batch(page_id, ns_id, ttl_wo_ns, redirect, modified_on, text_raw_len, random_int, text_db_id, html_db_id);
		text_tbl.Insert_cmd_by_batch(page_id, text_zip_data);
	}
}
