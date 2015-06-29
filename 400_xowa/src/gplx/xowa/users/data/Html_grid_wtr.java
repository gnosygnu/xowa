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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
class Hgrid_tbl_data {
	public Hgrid_tbl_data(Hgrid_col_data[] cols, Hgrid_row_data[] rows) {this.cols = cols; this.rows = rows;}
	public Hgrid_col_data[] Cols() {return cols;} private final Hgrid_col_data[] cols;
	public Hgrid_row_data[] Rows() {return rows;} private final Hgrid_row_data[] rows;
}
class Hgrid_col_data {
	public Hgrid_col_data(int type, String key, String display) {this.type = type; this.key = key; this.display = display;}
	public int Type() {return type;} private final int type;
	public String Key() {return key;} private final String key;
	public String Display() {return display;} private final String display;
	public static final int Type_id_str = 1;
}
class Hgrid_row_data {
	public Hgrid_row_data(Hgrid_tbl_data tbl, String pkey, Hgrid_cell_data[] cells, Hgrid_btn_data[] btns) {this.tbl = tbl; this.pkey = pkey; this.cells = cells; this.btns = btns;}
	public Hgrid_tbl_data Tbl() {return tbl;} private final Hgrid_tbl_data tbl;
	public String Pkey() {return pkey;} private final String pkey;
	public Hgrid_cell_data[] Cells() {return cells;} private Hgrid_cell_data[] cells;
	public Hgrid_btn_data[] Btns() {return btns;} private Hgrid_btn_data[] btns;
}
class Hgrid_cell_data {
	public Hgrid_cell_data(String data, String html) {this.data = data; this.html = html;}
	public String Data() {return data;} private final String data;
	public String Html() {return html;} private final String html;
}
class Hgrid_btn_data {
	public Hgrid_btn_data(String cmd, String img, String text) {this.cmd = cmd; this.img = img; this.text = text;}
	public String Cmd() {return cmd;} private final String cmd;
	public String Img() {return img;} private final String img;
	public String Text() {return text;} private final String text;
}
class Hgrid_tbl_fmtr {
	private final Hgrid_head_row_fmtr head_row_fmtr = new Hgrid_head_row_fmtr();
	private final Hgrid_data_rows_fmtr data_rows_fmtr = new Hgrid_data_rows_fmtr();
	public void Write(Bry_bfr bfr, Hgrid_tbl_data data) {
		tbl_fmtr.Bld_bry_many(bfr, head_row_fmtr.Init(data), data_rows_fmtr.Init(data));
	}
	private static final Bry_fmtr tbl_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<table class='wikitable'>"
	, "  <tr>~{head_cells}"
	, "  </tr>~{data_rows}"
	, "</table>"
	), "head_cells", "data_rows");
}
class Hgrid_head_row_fmtr implements Bry_fmtr_arg {
	private Hgrid_tbl_data data;
	public Hgrid_head_row_fmtr Init(Hgrid_tbl_data data) {this.data = data; return this;}
	public void XferAry(Bry_bfr bfr, int idx) {
		Hgrid_col_data[] cols = data.Cols(); int len = cols.length;
		for (int i = 0; i < len; ++i) {
			Hgrid_col_data col = cols[i];
			fmtr.Bld_bfr_many(bfr, col.Display());
		}
	}
	private static final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "    <th><span>~{display}</span></th>"
	), "display");
}
class Hgrid_data_rows_fmtr implements Bry_fmtr_arg {
	private final Hgrid_data_cell_fmtr cells_fmtr = new Hgrid_data_cell_fmtr();
	private Hgrid_tbl_data data;
	public Hgrid_data_rows_fmtr Init(Hgrid_tbl_data data) {this.data = data; return this;}
	public void XferAry(Bry_bfr bfr, int idx) {
		Hgrid_row_data[] rows = data.Rows(); int len = rows.length;
		for (int i = 0; i < len; ++i) {
			Hgrid_row_data row = rows[i];
			fmtr.Bld_bfr_many(bfr, i, row.Pkey(), cells_fmtr.Init(i, row));
		}
	}
	private static final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "  <tr id='xodb_~{id}' xodb_pkey='~{pkey}'>~{cells}~{cmds}"
	, "  </tr>"
	), "id", "pkey", "cells");
}
class Hgrid_data_cell_fmtr implements Bry_fmtr_arg {
	private int row_idx; private Hgrid_row_data data;
	public Hgrid_data_cell_fmtr Init(int row_idx, Hgrid_row_data data) {this.row_idx = row_idx; this.data = data; return this;}
	public void XferAry(Bry_bfr bfr, int idx) {
		Hgrid_cell_data[] cells = data.Cells(); int len = cells.length;
		Hgrid_col_data[] cols = data.Tbl().Cols();
		for (int i = 0; i < len; ++i) {
			Hgrid_cell_data cell = cells[i];
			fmtr.Bld_bfr_many(bfr, row_idx, cols[i].Key(), i, cell.Data(), cell.Html());
		}
	}
	private static final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "    <td><span id='xodb_~{row}_~{idx}' xodb_row='~{row}' xodb_col='~{col}' xodb_data='~{data}'>~{html}</span></td>"
	), "row", "col", "idx", "data", "html");
}
class Hgrid_row_btns_fmtr implements Bry_fmtr_arg {
	private int row_idx; private Hgrid_row_data data;
	public Hgrid_row_btns_fmtr Init(int row_idx, Hgrid_row_data data) {this.row_idx = row_idx; this.data = data; return this;}
	public void XferAry(Bry_bfr bfr, int idx) {
		Hgrid_btn_data[] btns = data.Btns(); int len = btns.length;
		for (int i = 0; i < len; ++i) {
			Hgrid_btn_data btn = btns[i];
			fmtr.Bld_bfr_many(bfr, row_idx, btn.Cmd(), btn.Img(), btn.Text());
		}
	}
	private static final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "      <span id='xodb_~{row}_btns'><a href='javascript:~{cmd}(~{row})'><img src='~{img}' title='~{text}'/></a></span>"
	), "row", "cmd", "img", "text");
}
/*
    <table class='wikitable'>
      <tr>
        <th width='100'><span>Name</span></th>
        <th width='300'><span>URL</span></th>
        <th></th>
      </tr>
      <tr id='xodb_0' xodb_id='100'>
        <td><span id='xodb_0_0' xodb_row='0' xodb_col='name' xodb_data='Earth'>Earth</span></td>
        <td><span id='xodb_0_1' xodb_row='0' xodb_col='url' xodb_data='en.wikipedia.org/wiki/Earth'><a href='en.wikipedia.org/wiki/Earth'>en.wikipedia.org/wiki/Earth</a></span></td>
        <td id='xodb_0_cmds'>
          <span id='xodb_0_edit'><a href='javascript:grid__edit_row(0);'><img src='edit.png' alt='edit' /></a></span><span id='xodb_0_delete'><a href='javascript:grid__delete_row(0);'><img src='delete.png' alt='delete' /></a></span>
        </td>
      </tr>
      <tr id='xodb_1' xodb_id='101'>
        <td><span id='xodb_1_0' xodb_row='1' xodb_col='name' xodb_data='Sun'>Sun</span></td>
        <td><span id='xodb_1_1' xodb_row='1' xodb_col='url' xodb_data='en.wikipedia.org/wiki/Sun'><a href='en.wikipedia.org/wiki/Sun'>en.wikipedia.org/wiki/Sun</a></span></td>
        <td id='xodb_1_cmds'>
          <span id='xodb_1_edit'><a href='javascript:grid__edit_row(1);'><img src='edit.png' alt='edit' /></a></span><span id='xodb_1_delete'><a href='javascript:grid__delete_row(1);'><img src='delete.png' alt='delete' /></a></span>
        </td>
      </tr>
    </table>
*/
