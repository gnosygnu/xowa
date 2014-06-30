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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
public class Xof_doc_page {
	public static final int		Null = -1;
	public static boolean		Null_y(int v) {return v == Null;}
	public static boolean		Null_n(int v) {return v != Null;}
	public static int		Db_load_int(DataRdr rdr, String fld) {return rdr.ReadInt(fld);}
	public static int		Db_save_int(int v) {return v;}
}
