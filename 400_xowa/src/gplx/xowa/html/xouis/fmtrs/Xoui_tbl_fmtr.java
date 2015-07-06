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
package gplx.xowa.html.xouis.fmtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.xouis.*;
import gplx.xowa.html.xouis.tbls.*;
public class Xoui_tbl_fmtr {
	private final Xoui_head_cell_fmtr head_cell_fmtr = new Xoui_head_cell_fmtr();
	private final Xoui_row_fmtr row_fmtr = new Xoui_row_fmtr();
	public void Write(Bry_bfr bfr, Xoui_tbl_itm tbl) {
		tbl_fmtr.Bld_bfr_many(bfr, tbl.Key(), head_cell_fmtr.Init(tbl), row_fmtr.Init(tbl));
	}
	private static final Bry_fmtr tbl_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "<table id='~{tbl_key}' class='xoui_tbl wikitable'>"
	, "  <tr>~{head_cells}"
	, "  </tr>~{data_rows}"
	, "</table>"
	), "tbl_key", "head_cells", "data_rows");
}
class Xoui_head_cell_fmtr implements Bry_fmtr_arg {
	private Xoui_tbl_itm tbl;
	public Xoui_head_cell_fmtr Init(Xoui_tbl_itm tbl) {this.tbl = tbl; return this;}
	public void XferAry(Bry_bfr bfr, int idx) {
		Xoui_col_itm[] cols = tbl.Cols(); int len = cols.length;
		for (int i = 0; i < len; ++i) {
			Xoui_col_itm col = cols[i];
			fmtr.Bld_bfr_many(bfr, col.Width(), col.Display());
		}
	}
	private static final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "    <th width=~{width}>~{display}</th>"
	), "width", "display");
}
class Xoui_row_fmtr implements Bry_fmtr_arg {
	private final Xoui_cells_fmtr cells_fmtr = new Xoui_cells_fmtr();
	private final Xoui_val_fmtr val_fmtr = Xoui_val_fmtr_.new_view();
	private final Bry_bfr row_key_bfr = Bry_bfr.new_(255);
	private Xoui_tbl_itm tbl;
	public Xoui_row_fmtr Init(Xoui_tbl_itm tbl) {this.tbl = tbl; return this;}
	public void XferAry(Bry_bfr bfr, int idx) {
		byte[] tbl_key = tbl.Key();
		Xoui_row_itm[] rows = tbl.Rows(); int len = rows.length;
		cells_fmtr.Ctor(val_fmtr, tbl.View_btns());
		for (int i = 0; i < len; ++i) {
			Xoui_row_itm row = rows[i];
			row_key_bfr.Add(tbl_key).Add_byte(Byte_ascii.Underline).Add_int_variable(i);
			byte[] row_key = row_key_bfr.Xto_bry_and_clear();
			fmtr.Bld_bfr_many(bfr, row_key, row.Pkey(), cells_fmtr.Init(row_key, row));
		}
	}
	private static final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <tr id='~{row_key}' class='xoui_row' xoui_pkey='~{pkey}'>~{cells}"
	, "  </tr>"
	), "row_key", "pkey", "cells");
}
