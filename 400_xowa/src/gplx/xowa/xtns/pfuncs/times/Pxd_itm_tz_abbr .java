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
class Pxd_itm_tz_abbr extends Pxd_itm_base implements Pxd_itm_prototype {
	private final    int tz_idx;
	private final    byte[] tz_abbr;
	private final    int tz_offset;
	public int Tz_offset() { return tz_offset; }
	public byte[] Tz_abbr() { return tz_abbr; }
	public Pxd_itm_tz_abbr(int ary_idx, byte[] tz_abbr, int tz_idx, int offset) {
		this.tz_abbr = tz_abbr; 
		this.tz_idx = tz_idx;
		this.tz_offset = offset;
		this.Ctor(ary_idx); 
		this.Seg_idx_(DateAdp_.SegIdx_tz);
	}
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_tz_abbr;}
	@Override public int Eval_idx() {return 90;}
	public Pxd_itm MakeNew(int ary_idx) {return new Pxd_itm_tz_abbr(ary_idx, tz_abbr, tz_idx, tz_offset);}
	@Override public boolean Eval(Pxd_parser state) {
		// just to check how often used
		if (tz_offset != 0)
			return false;
		return true;
	}
	@Override public boolean Time_ini(Pxd_date_bldr bldr) {
		DateAdp cur = bldr.To_date();
		cur = cur.Add_second(-tz_offset);
		bldr.By_date(cur);
		return true;
	}
}
