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
package gplx.dbs.metas; import gplx.*; import gplx.dbs.*;
public class Meta_type_itm {
	public Meta_type_itm(int tid_ansi, int tid_sqlite, byte[] name, int len_1, int len_2) {
		this.tid_ansi = tid_ansi; this.tid_sqlite = tid_sqlite; this.name = name; this.len_1 = len_1; this.len_2 = len_2;
	}
	public int Tid_ansi() {return tid_ansi;} private final int tid_ansi;
	public int Tid_sqlite() {return tid_sqlite;} private final int tid_sqlite;
	public byte[] Name() {return name;} private final byte[] name;
	public int Len_1() {return len_1;} private final int len_1;
	public int Len_2() {return len_2;} private final int len_2;
}
