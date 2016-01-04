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
package gplx.dbs.diffs; import gplx.*; import gplx.dbs.*;
public class Gfdb_diff_tbl {		
	public Gfdb_diff_tbl(String name, Db_meta_fld[] keys, Db_meta_fld[] vals, Db_rdr rdr) {
		this.name = name; this.keys = keys; this.vals = vals; this.rdr = rdr;
		int keys_len = keys.length; int vals_len = vals.length;
		this.flds = new Db_meta_fld[keys_len + vals_len];
		for (int i = 0; i < keys_len; ++i)
			flds[i] = keys[i];
		for (int i = 0; i < vals_len; ++i) 
			flds[i + keys_len] = vals[i];
	}
	public String Name() {return name;} private final String name;
	public Db_meta_fld[] Flds() {return flds;} private final Db_meta_fld[] flds;
	public Db_meta_fld[] Keys() {return keys;} private final Db_meta_fld[] keys;
	public Db_meta_fld[] Vals() {return vals;} private final Db_meta_fld[] vals;
	public Db_rdr Rdr() {return rdr;} private final Db_rdr rdr;
}
