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
package gplx.xowa.html.bridges.dbuis.fmtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.bridges.*; import gplx.xowa.html.bridges.dbuis.*;
import gplx.xowa.html.bridges.dbuis.tbls.*;
public interface Dbui_val_fmtr {
	Dbui_val_fmtr Init(Dbui_col_itm col, byte[] row_id, Dbui_val_itm val);
}
class Dbui_val_fmtr__view implements Bry_fmtr_arg, Dbui_val_fmtr {
	private Dbui_val_itm val;
	public Dbui_val_fmtr Init(Dbui_col_itm col, byte[] row_id, Dbui_val_itm val) {this.val = val; return this;}
	public void XferAry(Bry_bfr bfr, int idx) {
		bfr.Add(val.Html());
	}
}
class Dbui_val_fmtr__edit implements Bry_fmtr_arg, Dbui_val_fmtr {
	private Dbui_col_itm col; private byte[] row_id; private Dbui_val_itm val;
	public Dbui_val_fmtr Init(Dbui_col_itm col, byte[] row_id, Dbui_val_itm val) {this.col = col; this.row_id = row_id; this.val = val; return this;}
	public void XferAry(Bry_bfr bfr, int idx) {
		switch (col.Type()) {
			case Dbui_col_itm.Type_id_str:	input_fmtr_str.Bld_bfr_many(bfr, col.Key(), col.Width(), val.Data(), row_id); break;
			case Dbui_col_itm.Type_id_text: textarea_fmtr_str.Bld_bfr_many(bfr, col.Key(), col.Width(), val.Data(), row_id); break;
			default: throw Err_.new_unimplemented();
		}
	}
	private static final Bry_fmtr input_fmtr_str = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "    <input class='dbui_cell xo_resizable_col' dbui_col='~{col_key}' style='border:1px solid black; width:~{width}px' value='~{value}' onkeyup='Dbui__edit__keyup(event, \"~{row_id}\");'/>"
	), "col_key", "width", "value", "row_id");
	private static final Bry_fmtr textarea_fmtr_str = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "    <textarea class='dbui_cell' dbui_col='~{col_key}' style='border:1px solid black; width:~{width}px; height:18px;' onkeyup='Dbui__edit__keyup(event, \"~{row_id}\");'>~{value}</textarea>"
	), "col_key", "width", "value", "row_id");
}
