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
public class Dbui_cells_fmtr implements gplx.core.brys.Bfr_arg {
	private final    Dbui_cell_fmtr cell_fmtr = new Dbui_cell_fmtr();
	private final    Dbui_btn_fmtr btn_fmtr = new Dbui_btn_fmtr();
	private Dbui_btn_itm[] btns;
	private byte[] row_key; private Dbui_row_itm row_itm;
	public void Ctor(Dbui_val_fmtr val_fmtr, Dbui_btn_itm[] btns) {
		cell_fmtr.Ctor(val_fmtr); this.btns = btns;
	}
	public Dbui_cells_fmtr Init(byte[] row_key, Dbui_row_itm row_itm) {
		this.row_key = row_key; this.row_itm = row_itm;
		return this;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		fmtr.Bld_bfr_many(bfr, cell_fmtr.Init(row_key, row_itm), btn_fmtr.Init(row_key, btns));
	}
	private static final    Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "    <div class='xo_drag_handle xo_cell'></div>~{vals}"
	, "    <div class='xo_cell'>~{btns}"
	, "    </div>"
	), "vals", "btns");
}
class Dbui_cell_fmtr implements gplx.core.brys.Bfr_arg {
	private byte[] row_key; private Dbui_row_itm row_itm;
	private Dbui_val_fmtr val_fmtr;
	public void Ctor(Dbui_val_fmtr val_fmtr) {this.val_fmtr = val_fmtr;}
	public Dbui_cell_fmtr Init(byte[] row_key, Dbui_row_itm row_itm) {this.row_key = row_key; this.row_itm = row_itm; return this;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		Dbui_col_itm[] cols = row_itm.Tbl().Cols();
		Dbui_val_itm[] vals = row_itm.Vals(); int len = vals.length;
		for (int i = 0; i < len; ++i) {
			Dbui_val_itm val = vals[i];
			fmtr.Bld_bfr_many(bfr, row_key, i, val_fmtr.Init(cols[i], row_key, val));
		}
	}
	private static final    Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "    <div class='xo_cell'>~{html}</div>"
	), "row_key", "val_idx", "html");
}
class Dbui_btn_fmtr implements gplx.core.brys.Bfr_arg {
	private byte[] row_key; private Dbui_btn_itm[] btns;
	public Dbui_btn_fmtr Init(byte[] row_key, Dbui_btn_itm[] btns) {
		this.row_key = row_key; this.btns = btns; return this;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = btns.length;
		Io_url img_dir = gplx.xowa.htmls.heads.Xoh_head_itm__dbui.Img_dir();
		for (int i = 0; i < len; ++i) {
			Dbui_btn_itm btn = btns[i];
			fmtr.Bld_bfr_many(bfr, row_key, btn.Cmd(), img_dir.GenSubFil(btn.Img()).To_http_file_bry(), btn.Text());
		}
	}
	private static final    Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "      <span><a href='javascript:~{btn_cmd}(\"~{row_key}\");'><img src='~{btn_img}' title='~{btn_text}'/></a></span>"
	), "row_key", "btn_cmd", "btn_img", "btn_text");
}
