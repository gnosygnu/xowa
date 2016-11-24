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
public class Gdif_db_ {
	public static final String
	  Fld__dif_txn			= "dif_txn"		// 0+ where 0+ is defined in a tbl
	, Fld__dif_uid			= "dif_uid"		// 0+
	, Fld__dif_type			= "dif_type"	// I,U,D,M
	, Fld__dif_db_trg		= "dif_db_trg"	// -1 for single-db tables; 0+ for multiple-db tables
	, Fld__dif_db_src		= "dif_db_src"	// -1 for I,U,D; 0+ for M
	;
	public static final byte
	  Tid__insert = 0
	, Tid__update = 1
	, Tid__delete = 2
	, Tid__move   = 3
	;
}
