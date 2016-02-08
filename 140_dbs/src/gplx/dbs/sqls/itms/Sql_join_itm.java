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
package gplx.dbs.sqls.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
public class Sql_join_itm {
	public Sql_join_itm(String trg_fld, String src_tbl, String src_fld) {
		this.Trg_fld = trg_fld;
		this.Src_tbl = src_tbl;
		this.Src_fld = src_fld;
	}
	public final String Src_tbl;
	public final String Src_fld;
	public final String Trg_fld;

	public static final Sql_join_itm[] Ary__empty = new Sql_join_itm[0];
}
