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
package gplx.xowa.htmls.bridges.dbuis.fmtrs;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.fmts.fmtrs.*;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.htmls.bridges.dbuis.tbls.*;
public interface Dbui_val_fmtr {
	Dbui_val_fmtr Init(Dbui_col_itm col, byte[] row_id, Dbui_val_itm val);
}
class Dbui_val_fmtr__view implements BryBfrArg, Dbui_val_fmtr {
	private Dbui_val_itm val;
	public Dbui_val_fmtr Init(Dbui_col_itm col, byte[] row_id, Dbui_val_itm val) {this.val = val; return this;}
	public void AddToBfr(BryWtr bfr) {
		bfr.Add(val.Html());
	}
}
class Dbui_val_fmtr__edit implements BryBfrArg, Dbui_val_fmtr {
	private Dbui_col_itm col; private byte[] row_id; private Dbui_val_itm val;
	public Dbui_val_fmtr Init(Dbui_col_itm col, byte[] row_id, Dbui_val_itm val) {this.col = col; this.row_id = row_id; this.val = val; return this;}
	public void AddToBfr(BryWtr bfr) {
		switch (col.Type()) {
			case Dbui_col_itm.Type_id_str:	input_fmtr_str.BldToBfrMany(bfr, col.Key(), col.Width(), val.Data(), row_id); break;
			case Dbui_col_itm.Type_id_text: textarea_fmtr_str.BldToBfrMany(bfr, col.Key(), col.Width(), val.Data(), row_id); break;
			default: throw ErrUtl.NewUnimplemented();
		}
	}
	private static final BryFmtr input_fmtr_str = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "    <input class='dbui_cell xo_resizable_col' dbui_col='~{col_key}' style='border:1px solid black; width:~{width}px' value='~{value}' onkeyup='Dbui__edit__keyup(event, \"~{row_id}\");'/>"
	), "col_key", "width", "value", "row_id");
	private static final BryFmtr textarea_fmtr_str = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "    <textarea class='dbui_cell' dbui_col='~{col_key}' style='border:1px solid black; width:~{width}px; height:18px;' onkeyup='Dbui__edit__keyup(event, \"~{row_id}\");'>~{value}</textarea>"
	), "col_key", "width", "value", "row_id");
}
