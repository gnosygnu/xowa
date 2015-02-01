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
package gplx.texts; import gplx.*;
import gplx.core.strings.*;
public class StringTableBldr {
	public void ClearRows() {rows.Clear();}
	public StringTableCol Col_(int i) {return FetchAtOrNew(i);}		
	public StringTableBldr DefaultHalign_(StringTableColAlign v) {defaultHalign = v; return this;} StringTableColAlign defaultHalign = StringTableColAlign.Left;
	public StringTableBldr Add(String... row) {
		rows.Add(row);
		for (int i = 0; i < row.length; i++) {
			StringTableCol col = FetchAtOrNew(i);
			col.AdjustFor(row[i]);
		}
		return this;
	}
	public StringTableCol FetchAtOrNew(int i) {
		if (i < cols.Count()) return StringTableCol.as_(cols.FetchAt(i));
		StringTableCol col = StringTableCol.new_();
		col.Halign_(defaultHalign);
		cols.Add(i, col);
		return col;
	}
	public String XtoStr() {
		sb.Clear();
		for (int rowI = 0; rowI < rows.Count(); rowI++) {
			String[] row = (String[])rows.FetchAt(rowI);
			for (int colI = 0; colI < row.length; colI++) {
				if (colI != 0) sb.Add(" ");
				StringTableCol col = StringTableCol.as_(cols.FetchAt(colI)); if (col == null) throw Err_.missing_idx_(colI, cols.Count());
				sb.Add(col.PadCell(row[colI]));
			}
			sb.Add(String_.CrLf);
		}
		return sb.Xto_str_and_clear();
	}
	
	public static StringTableBldr new_() {return new StringTableBldr();} StringTableBldr() {}
	OrderedHash cols = OrderedHash_.new_();
	ListAdp rows = ListAdp_.new_();
	String_bldr sb = String_bldr_.new_();
}
