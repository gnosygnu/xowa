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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pft_func_date_name extends Pf_func_base {
	public Pft_func_date_name(int id, int date_tid, int seg_idx, int base_idx) {this.id = id; this.date_tid = date_tid; this.seg_idx = seg_idx; this.base_idx = base_idx;} private int date_tid, seg_idx, base_idx;
	@Override public int Id() {return id;} private int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pft_func_date_name(id, date_tid, seg_idx, base_idx).Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		DateAdp date = DateAdp_.MinValue;
	    switch (date_tid) {
	        case Pft_func_date_int.Date_tid_lcl: date = Datetime_now.Get(); break;
	        case Pft_func_date_int.Date_tid_utc: date = Datetime_now.Get().XtoUtc(); break;
	        case Pft_func_date_int.Date_tid_rev: date = ctx.Page().Db().Page().Modified_on(); break;
			default: throw Err_.new_unhandled(date_tid);
	    }
		byte[] val = ctx.Wiki().Msg_mgr().Val_by_id(base_idx + date.Segment(seg_idx));
		bfr.Add(val);
		// translator.Translate(base_idx, date.Segment(seg_idx), bfr);
	}
}
