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
public class DbTstRow {
	public int Idx() {return idx;} public DbTstRow Idx_(int val) {idx = val; return this;} int idx = -1;
	public DbTstDat[] Dat() {return dat;} DbTstDat[] dat = null;
	public static DbTstRow vals_only_(Object... ary) {
		DbTstRow rv = new DbTstRow();
		int len = Array_.Len(ary);
		rv.dat = new DbTstDat[len];
		for (int i = 0; i < len; i++)
			rv.dat[i] = DbTstDat.new_().Val_(ary[i]);
		return rv;
	}
	public static DbTstRow new_() {return new DbTstRow();} DbTstRow() {}
}
