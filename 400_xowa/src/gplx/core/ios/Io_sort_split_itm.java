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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public class Io_sort_split_itm {
	public Io_sort_split_itm() {}
	public Io_sort_split_itm(Io_line_rdr rdr) {Set(rdr);}
	public int Row_bgn() {return row_bgn;} private int row_bgn;
	public int Row_end() {return row_end;} private int row_end;
	public int Key_bgn() {return key_bgn;} private int key_bgn;
	public int Key_end() {return key_end;} private int key_end;
	public byte[] Bfr() {return bfr;} private byte[] bfr;
	public void Set(Io_line_rdr rdr) {
		bfr = rdr.Bfr();
		row_bgn = rdr.Itm_pos_bgn();
		row_end = rdr.Itm_pos_end();
		key_bgn = rdr.Key_pos_bgn();
		key_end = rdr.Key_pos_end();
	}
	public void Rls() {bfr = null;}
}
