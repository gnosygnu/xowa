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
package gplx.xowa.htmls.bridges.dbuis.fmtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.bridges.*; import gplx.xowa.htmls.bridges.dbuis.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.htmls.bridges.dbuis.tbls.*;
public class Dbui_tbl_fmtr {
	private final    Dbui_head_cell_fmtr head_cell_fmtr = new Dbui_head_cell_fmtr();
	private final    Dbui_row_fmtr row_fmtr = new Dbui_row_fmtr();
	public void Write(Bry_bfr bfr, Dbui_tbl_itm tbl, byte[] origin_html, byte[] delete_confirm_msg, Dbui_row_itm[] rows) {
		tbl_fmtr.Bld_bfr_many(bfr, tbl.Key(), Dbui_tbl_itm_.Calc_width(tbl), origin_html, delete_confirm_msg, head_cell_fmtr.Init(tbl), row_fmtr.Init(tbl, rows));
	}
	private static final    Bry_fmtr tbl_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "<div class='xo_tbl xo_sortable' data-dbui-tbl_key='~{tbl_key}'~{delete_confirm_msg} style='width: ~{width}px;'>"
	, "  <div class='xo_row xo_header'>"
	, "    <div class='xo_origin xo_resizable_col' style='width:20px'>~{origin}</div>~{head_cells}"
	, "  </div>~{data_rows}"
	, "</div>"
	), "tbl_key", "width", "origin", "delete_confirm_msg", "head_cells", "data_rows");
}
class Dbui_head_cell_fmtr implements gplx.core.brys.Bfr_arg {
	private Dbui_tbl_itm tbl;
	public Dbui_head_cell_fmtr Init(Dbui_tbl_itm tbl) {this.tbl = tbl; return this;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		Dbui_col_itm[] cols = tbl.Cols(); int len = cols.length;
		for (int i = 0; i < len; ++i) {
			Dbui_col_itm col = cols[i];
			fmtr.Bld_bfr_many(bfr, col.Width(), col.Display());
		}
		bfr.Add_str_a7("\n    <div class='xo_head xo_resizable_col' style='width:50px;'>&nbsp;</div>");	// btns headers
	}
	private static final    Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "    <div class='xo_head xo_resizable_col' style='width:~{width}px;'>~{display}</div>"
	), "width", "display");
}
class Dbui_row_fmtr implements gplx.core.brys.Bfr_arg {
	private final    Dbui_cells_fmtr cells_fmtr = new Dbui_cells_fmtr();
	private final    Dbui_val_fmtr val_fmtr = Dbui_val_fmtr_.new_view();
	private final    Bry_bfr row_key_bfr = Bry_bfr_.New_w_size(255);
	private Dbui_tbl_itm tbl; private Dbui_row_itm[] rows;
	public Dbui_row_fmtr Init(Dbui_tbl_itm tbl, Dbui_row_itm[] rows) {this.tbl = tbl; this.rows = rows; return this;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		byte[] tbl_key = tbl.Key();
		int len = rows.length;
		cells_fmtr.Ctor(val_fmtr, tbl.View_btns());
		for (int i = 0; i < len; ++i) {
			Dbui_row_itm row = rows[i];
			row_key_bfr.Add(tbl_key).Add_byte(Byte_ascii.Underline).Add_int_variable(i);
			byte[] row_key = row_key_bfr.To_bry_and_clear();
			fmtr.Bld_bfr_many(bfr, row_key, row.Pkey(), cells_fmtr.Init(row_key, row));
		}
	}
	private static final    Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <div id='~{row_key}' class='xo_row xo_draggable' data-dbui-row_pkey='~{pkey}'>~{cells}"
	, "  </div>"
	), "row_key", "pkey", "cells");
}
