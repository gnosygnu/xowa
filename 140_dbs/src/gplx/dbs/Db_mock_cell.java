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
public class Db_mock_cell {
	public int Idx() {return idx;} public Db_mock_cell Idx_(int val) {idx = val; return this;} int idx = -1;
	public String Fld() {return fld;} public Db_mock_cell Fld_(String v) {fld = v; return this;} private String fld = null;
	public Object Val() {return val;} public Db_mock_cell Val_(Object v) {val = v; return this;} Object val = null;
	public static Db_mock_cell new_() {return new Db_mock_cell();} Db_mock_cell() {}
}
