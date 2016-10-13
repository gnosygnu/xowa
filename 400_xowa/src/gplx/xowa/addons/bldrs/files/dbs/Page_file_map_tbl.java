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
package gplx.xowa.addons.bldrs.files.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.dbs.*;
public class Page_file_map_tbl implements Db_tbl {		
	public final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	public final    String fld_page_id, fld_fil_id, fld_thm_id, fld_sort_id, fld_count_of;
	public final    Db_conn conn;
	public Page_file_map_tbl(Db_conn conn, String tbl_name) {
		this.conn = conn;
		this.tbl_name = tbl_name;
		this.fld_page_id		= flds.Add_int("page_id");
		this.fld_fil_id			= flds.Add_int("fil_id");
		this.fld_thm_id			= flds.Add_int("thm_id");
		this.fld_sort_id		= flds.Add_int("sort_id");
		this.fld_count_of		= flds.Add_int("count_of");
		this.meta				= Dbmeta_tbl_itm.New(tbl_name, flds);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void	Create_tbl() {conn.Meta_tbl_create(meta);}
	public Dbmeta_tbl_itm Meta() {return meta;} private final    Dbmeta_tbl_itm meta;
	public void Rls() {}
}
