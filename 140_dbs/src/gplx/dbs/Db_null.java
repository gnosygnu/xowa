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
public class Db_null implements gplx.core.brys.Bfr_arg {
	public void Bfr_arg__add(Bry_bfr bfr) {bfr.Add_str_a7(Null_str);}
	@Override public String toString() {return Null_str;}
	public static final String Null_str = "NULL";
        public static final Db_null Instance = new Db_null(); Db_null() {}
}
