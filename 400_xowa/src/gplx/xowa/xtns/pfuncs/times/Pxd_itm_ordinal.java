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
class Pxd_itm_ordinal extends Pxd_itm_base implements Pxd_itm_prototype {
	private final int ord_idx;
	public int Ord_val() { return ord_idx; }
	private final byte[] ord_name;
	public Pxd_itm_ordinal(int ary_idx, byte[] ord_name, int ord_idx) {
		this.ord_name = ord_name; 
		this.ord_idx = ord_idx;
		this.Ctor(ary_idx); 
	}
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_ord_name;}
	@Override public int Eval_idx() {return 99;}
	public Pxd_itm MakeNew(int ary_idx) {return new Pxd_itm_ordinal(ary_idx, ord_name, ord_idx);}
	@Override public boolean Eval(Pxd_parser state) {return true;}
	@Override public boolean Time_ini(Pxd_date_bldr bldr) {
		return true;
	}
}
class Pxd_itm_weekdays extends Pxd_itm_base implements Pxd_itm_prototype {
	private final byte[] ord_name;
	public Pxd_itm_weekdays(int ary_idx, byte[] ord_name) {
		this.ord_name = ord_name; 
		this.Ctor(ary_idx); 
	}
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_weekdays;}
	@Override public int Eval_idx() {return 99;}
	public Pxd_itm MakeNew(int ary_idx) {return new Pxd_itm_weekdays(ary_idx, ord_name);}
	@Override public boolean Eval(Pxd_parser state) {return true;}
	@Override public boolean Time_ini(Pxd_date_bldr bldr) {
		return true;
	}
}
