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
package gplx.xowa.xtns.pfuncs.times;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateNow;
import gplx.types.commons.GfoDateUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.tmpls.Xot_invk;
import gplx.xowa.xtns.pfuncs.Pf_func;
import gplx.xowa.xtns.pfuncs.Pf_func_base;
public class Pft_func_date_name extends Pf_func_base {
	public Pft_func_date_name(int id, int date_tid, int seg_idx, int base_idx) {this.id = id; this.date_tid = date_tid; this.seg_idx = seg_idx; this.base_idx = base_idx;} private int date_tid, seg_idx, base_idx;
	@Override public int Id() {return id;} private int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pft_func_date_name(id, date_tid, seg_idx, base_idx).Name_(name);}
	@Override public void Func_evaluate(BryWtr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		GfoDate date = GfoDateUtl.MinValue;
	    switch (date_tid) {
	        case Pft_func_date_int.Date_tid_lcl: date = GfoDateNow.Get(); break;
	        case Pft_func_date_int.Date_tid_utc: date = GfoDateNow.Get().ToUtc(); break;
	        case Pft_func_date_int.Date_tid_rev: date = ctx.Page().Db().Page().Modified_on(); break;
			default: throw ErrUtl.NewUnhandled(date_tid);
	    }
		byte[] val = ctx.Wiki().Msg_mgr().Val_by_id(base_idx + date.Segment(seg_idx));
		bfr.Add(val);
		// translator.Translate(base_idx, date.Segment(seg_idx), bfr);
	}
}
