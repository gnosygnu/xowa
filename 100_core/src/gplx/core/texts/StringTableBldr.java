/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.texts; import gplx.*; import gplx.core.*;
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
		if (i < cols.Count()) return StringTableCol.as_(cols.Get_at(i));
		StringTableCol col = StringTableCol.new_();
		col.Halign_(defaultHalign);
		cols.Add(i, col);
		return col;
	}
	public String To_str() {
		sb.Clear();
		for (int rowI = 0; rowI < rows.Count(); rowI++) {
			String[] row = (String[])rows.Get_at(rowI);
			for (int colI = 0; colI < row.length; colI++) {
				if (colI != 0) sb.Add(" ");
				StringTableCol col = StringTableCol.as_(cols.Get_at(colI)); if (col == null) throw Err_.new_missing_idx(colI, cols.Count());
				sb.Add(col.PadCell(row[colI]));
			}
			sb.Add(String_.CrLf);
		}
		return sb.To_str_and_clear();
	}
	
	public static StringTableBldr new_() {return new StringTableBldr();} StringTableBldr() {}
	Ordered_hash cols = Ordered_hash_.New();
	List_adp rows = List_adp_.New();
	String_bldr sb = String_bldr_.new_();
}
