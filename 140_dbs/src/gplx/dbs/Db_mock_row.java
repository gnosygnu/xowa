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
public class Db_mock_row {
	public int Idx() {return idx;} public Db_mock_row Idx_(int val) {idx = val; return this;} int idx = -1;
	public Db_mock_cell[] Dat() {return dat;} Db_mock_cell[] dat = null;
	public static Db_mock_row vals_only_(Object... ary) {
		Db_mock_row rv = new Db_mock_row();
		int len = Array_.Len(ary);
		rv.dat = new Db_mock_cell[len];
		for (int i = 0; i < len; i++)
			rv.dat[i] = Db_mock_cell.new_().Val_(ary[i]);
		return rv;
	}
	public static Db_mock_row new_() {return new Db_mock_row();} Db_mock_row() {}
}
