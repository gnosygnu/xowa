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
public interface Xoui_val_fmtr {
	Xoui_val_fmtr Init(Xoui_col_itm col, Xoui_val_itm val);
}
class Xoui_val_fmtr__view implements Bry_fmtr_arg, Xoui_val_fmtr {
	private Xoui_val_itm val;
	public Xoui_val_fmtr Init(Xoui_col_itm col, Xoui_val_itm val) {this.val = val; return this;}
	public void XferAry(Bry_bfr bfr, int idx) {
		bfr.Add(val.Html());
	}
}
class Xoui_val_fmtr__edit implements Bry_fmtr_arg, Xoui_val_fmtr {
	private Xoui_col_itm col; private Xoui_val_itm val;
	public Xoui_val_fmtr Init(Xoui_col_itm col, Xoui_val_itm val) {this.col = col; this.val = val; return this;}
	public void XferAry(Bry_bfr bfr, int idx) {
		switch (col.Type()) {
			case Xoui_col_itm.Type_id_str: fmtr_str.Bld_bfr_many(bfr, col.Key(), val.Data().length * 8, val.Data()); break;
			default: throw Err_.not_implemented_();
		}
	}
	private static final Bry_fmtr fmtr_str = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "    <input class='xoui_cell' xoui_col='~{col_key}' style='width:~{width}px' value='~{value}' />"
	), "col_key", "width", "value");
}
