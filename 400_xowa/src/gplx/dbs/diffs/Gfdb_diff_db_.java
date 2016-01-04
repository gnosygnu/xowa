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
public class Gfdb_diff_db_ {
	public static final String
	  Fld__diff_site		= "diff_site"		// -1 for single-site merge; 0+ for multiple-site merges where 0+ is defined in a registry
	, Fld__diff_time		= "diff_time"		// -1 for single-time merge; 0+ for multiple-time merges where 0+ is defined in a registry
	, Fld__diff_db_trg		= "diff_db_trg"		// -1 for single-db tables; 0+ for multiple-db tables
	, Fld__diff_db_src		= "diff_db_src"		// -1 for I,U,D; 0+ for M
	, Fld__diff_type		= "diff_type"		// I,U,D,M
	, Fld__diff_uid			= "diff_uid"		// 0+
	;
	public static final byte
	  Tid__insert = 0
	, Tid__update = 1
	, Tid__delete = 2
	, Tid__move   = 3
	;
}
