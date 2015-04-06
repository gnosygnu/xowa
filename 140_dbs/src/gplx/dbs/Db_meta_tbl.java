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
package gplx.dbs; import gplx.*;
import gplx.dbs.sqls.*;
public class Db_meta_tbl {
	public Db_meta_tbl(String name, Db_meta_fld[] flds, Db_meta_idx[] idxs) {
		if (idxs == null) idxs = Db_meta_idx.Ary_empty;	// empty params will pass idxs of null; set to idxs[0] else null ref when calling create_table
		this.name = name; this.flds = flds; this.idxs = idxs;
	}
	public String Name() {return name;} private final String name;		
	public Db_meta_fld[] Flds() {return flds;} private final Db_meta_fld[] flds;
	public Db_meta_idx[] Idxs() {return idxs;} private final Db_meta_idx[] idxs;
	public String To_sql_create() {return Db_sqlbldr__sqlite.I.Bld_create_tbl(this);}
	public static Db_meta_tbl new_(String name, Db_meta_fld_list flds, Db_meta_idx... idxs) {return new Db_meta_tbl(name, flds.To_fld_ary(), idxs);}
	public static Db_meta_tbl new_(String name, Db_meta_fld[] flds, Db_meta_idx... idxs) {return new Db_meta_tbl(name, flds, idxs);}
	public static Db_meta_tbl new_(String name, Db_meta_fld... flds) {return new Db_meta_tbl(name, flds, null);}
}
