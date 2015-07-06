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
public class Xoui_cells_fmtr implements Bry_fmtr_arg {
	private final Xoui_cell_fmtr cell_fmtr = new Xoui_cell_fmtr();
	private final Xoui_btn_fmtr btn_fmtr = new Xoui_btn_fmtr();
	private Xoui_btn_itm[] btns;
	private byte[] row_key; private Xoui_row_itm row_itm;
	public void Ctor(Xoui_val_fmtr val_fmtr, Xoui_btn_itm[] btns) {
		cell_fmtr.Ctor(val_fmtr); this.btns = btns;
	}
	public Xoui_cells_fmtr Init(byte[] row_key, Xoui_row_itm row_itm) {
		this.row_key = row_key; this.row_itm = row_itm;
		return this;
	}
	public void XferAry(Bry_bfr bfr, int idx) {
		fmtr.Bld_bfr_many(bfr, row_key, cell_fmtr.Init(row_key, row_itm), btn_fmtr.Init(row_key, btns));
	}
	private static final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "~{vals}"
	, "    <td id='~{row_key}_btns'>~{btns}"
	, "    </td>"
	), "row_key", "vals", "btns");
}
class Xoui_cell_fmtr implements Bry_fmtr_arg {
	private byte[] row_key; private Xoui_row_itm row_itm;
	private Xoui_val_fmtr val_fmtr;
	public void Ctor(Xoui_val_fmtr val_fmtr) {this.val_fmtr = val_fmtr;}
	public Xoui_cell_fmtr Init(byte[] row_key, Xoui_row_itm row_itm) {this.row_key = row_key; this.row_itm = row_itm; return this;}
	public void XferAry(Bry_bfr bfr, int idx) {
		Xoui_col_itm[] cols = row_itm.Tbl().Cols();
		Xoui_val_itm[] vals = row_itm.Vals(); int len = vals.length;
		for (int i = 0; i < len; ++i) {
			Xoui_val_itm val = vals[i];
			fmtr.Bld_bfr_many(bfr, row_key, i, val_fmtr.Init(cols[i], val));
		}
	}
	private static final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "    <td id='~{row_key}_~{val_idx}'>~{html}</td>"
	), "row_key", "val_idx", "html");
}
class Xoui_btn_fmtr implements Bry_fmtr_arg {
	private byte[] row_key; private Xoui_btn_itm[] btns;
	public Xoui_btn_fmtr Init(byte[] row_key, Xoui_btn_itm[] btns) {
		this.row_key = row_key; this.btns = btns; return this;
	}
	public void XferAry(Bry_bfr bfr, int idx) {
		int len = btns.length;
		Io_url img_dir = gplx.xowa.html.modules.Xoh_module_itm__xoui.Img_dir();
		for (int i = 0; i < len; ++i) {
			Xoui_btn_itm btn = btns[i];
			fmtr.Bld_bfr_many(bfr, row_key, btn.Key(), btn.Cmd(), img_dir.GenSubFil(btn.Img()).To_http_file_bry(), btn.Text());
		}
	}
	private static final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "      <span id='~{row_key}_~{btn_key}'><a href='javascript:~{btn_cmd}(\"~{row_key}\");'><img src='~{btn_img}' title='~{btn_text}'/></a></span>"
	), "row_key", "btn_key", "btn_cmd", "btn_img", "btn_text");
}
