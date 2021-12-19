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
import gplx.types.basics.utls.IntUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
class Pxd_itm_dow_name extends Pxd_itm_base implements Pxd_itm_prototype {
	private final int dow_idx;
	private final byte[] dow_name;
	private int relative_adj = IntUtl.MaxValue;
	public Pxd_itm_dow_name(int ary_idx, byte[] dow_name, int dow_idx) {
		this.dow_name = dow_name; 
		this.dow_idx = dow_idx;
		this.Ctor(ary_idx); 
		this.Seg_idx_(GfoDateUtl.SegIdxDayOfWeek);
	}
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_dow_name;}
	@Override public int Eval_idx() {return 20;}
	public Pxd_itm MakeNew(int ary_idx) {return new Pxd_itm_dow_name(ary_idx, dow_name, dow_idx);}
	public int Dow_idx() {return dow_idx;}
	public void Relative_adj_(int v) {	// handled by relative_word; EX: "next tuesday"
		this.relative_adj = v;
	}
	@Override public boolean Eval(Pxd_parser state) {return true;}
	@Override public boolean Time_ini(Pxd_date_bldr bldr) {
		GfoDate cur = bldr.To_date();

		// adj = requested_dow - cur_dow; EX: requesting Friday, and today is Wednesday; adj = 2 (4 - 2); DATE:2014-05-02
		int adj = dow_idx - cur.DayOfWeek();

		// requested_dow is before cur_dow; add 7 to get the next day
		if (adj < 0) adj += 7;

		// "next" always adds 7 days
		if (relative_adj == 1)
			adj += 7;
		// "previous" always subtracts 7 days;
		else if (relative_adj == -1)
			adj -= 7;

		cur = cur.AddDay(adj);
		bldr.By_date(cur);
		return true;
	}
}
