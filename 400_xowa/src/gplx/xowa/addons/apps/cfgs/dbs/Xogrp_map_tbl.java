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
package gplx.xowa.addons.apps.cfgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
public class Xogrp_map_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__map_src, fld__map_trg, fld__map_sort;
	private final    Db_conn conn;
	public Xogrp_map_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "cfg_grp_map";
		this.fld__map_src			= flds.Add_int("map_src");
		this.fld__map_trg			= flds.Add_int("map_trg");
		this.fld__map_sort			= flds.Add_int("map_sort");
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Upsert(int map_src, int map_trg, int map_sort) {
		Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld__map_src), map_src, map_trg, map_sort);
	}
	public void Select_stub() {
		Db_rdr rdr = Db_rdr_.Empty;
		rdr.Read_int(fld__map_src);
		rdr.Read_int(fld__map_trg);
		rdr.Read_int(fld__map_sort);
	}
	public void Rls() {}
}
/*
grp_0 -> Name of page
grp_1 -> H2
grp_2 -> H3
...
*/
