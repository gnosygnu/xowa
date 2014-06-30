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
public class Db_val_type {
	public static final byte // not serialized
	  Tid_null		= 0
	, Tid_bool		= 1
	, Tid_byte		= 2
	, Tid_int32		= 3
	, Tid_int64		= 4
	, Tid_date		= 5
	, Tid_decimal	= 6
	, Tid_float		= 7
	, Tid_double	= 8
	, Tid_bry		= 9
	, Tid_varchar	= 10
	, Tid_nvarchar	= 11
	, Tid_rdr		= 12
	;
}
