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
import gplx.core.brys.*;
class Pxd_itm_meridian extends Pxd_itm_base implements Pxd_itm_prototype {
	private final boolean pm;
	public Pxd_itm_meridian(int ary_idx, boolean pm) {this.Ctor(ary_idx); this.pm = pm;}
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_meridian;} 
	@Override public int Eval_idx() {return 19;}
	@Override public boolean Time_ini(Pxd_date_bldr bldr) {return true;}
	public Pxd_itm MakeNew(int ary_idx) {return new Pxd_itm_meridian(ary_idx, pm);}
	@Override public boolean Eval(Pxd_parser state) {
		Pxd_itm[] tkns = state.Tkns();

		// init some vars
		Pxd_itm itm = null;
		boolean colon_found = false;

		// get previous item before meridian, skipping ws
		int itm_idx;
		for (itm_idx = this.Ary_idx() - 1; itm_idx > -1; --itm_idx) {
			itm = tkns[itm_idx];
			if (itm.Tkn_tid() != Pxd_itm_.Tid_ws)
				break;
		}

		// null-check to handle error cases like "AM"
		if (itm == null) {
			return Fail(state, "Nothing found before meridian");
		}
		else {
			// itm is not int, then err; EX: "monday AM"
			if (itm.Tkn_tid() != Pxd_itm_.Tid_int) {
				return Fail(state, "Text found before meridian");
			}
			// itm is an int
			else {
				// int_itm may be minute / seconds if there are colons; EX "12AM" vs "12:00AM" or "12:00:00AM"
				if (itm_idx > 0 && tkns[itm_idx - 1].Tkn_tid() == Pxd_itm_.Tid_colon) {
					colon_found = true;
					itm_idx--;
					// make sure int exists before ":"
					if (itm_idx > 0 && tkns[itm_idx - 1].Tkn_tid() == Pxd_itm_.Tid_int) {
						itm = tkns[itm_idx - 1];
						itm_idx--;
						// check again for ":"
						if (itm_idx > 0 && tkns[itm_idx - 1].Tkn_tid() == Pxd_itm_.Tid_colon) {
							itm_idx--;
							// make sure int exists before ":"
							if (itm_idx > 0 && tkns[itm_idx - 1].Tkn_tid() == Pxd_itm_.Tid_int) {
								itm = tkns[itm_idx - 1];
							}
							else {
								return Fail(state, "Text found before 2nd colon");
							}
						}
					}
					else {
						return Fail(state, "Text found before 1st colon");
					}
				}

				// get hour
				Pxd_itm_int hour_itm = (Pxd_itm_int)itm;
				int hour = hour_itm.Val();

				// invalid digit; fail
				if (hour == 0 || hour > 12) {
					return Fail(state, "Invalid digit for meridian: "  + Int_.To_str(hour));
				}
				else {
					// update hour
					if (pm) {
						// convert "1 PM" -> hour=13
						hour_itm.Val_(hour + 12);
					}
					else { // convert "12 AM" -> 00:00
						if (hour == 12) {
							hour_itm.Val_(0);
						}
					}

					// if no colon, eval int now else int will get eval'd as year; EX: "12 AM" will be interpreted as "12" year
					if (!colon_found)
						return Pxd_eval_seg.Eval_as_h(state, hour_itm);
					else {
						// colon exists; don't need to do anything else b/c parser will handle normally; i.e.: "12:00 PM" is just like "12:00"
					}
				}
			}
		}
		
		return true;
	}
	private boolean Fail(Pxd_parser state, String err) {
		// MW just throws "Invalid time."; add extra description message for debugging purposes
		state.Err_set(Pft_func_time_log.Invalid_time, Bfr_arg_.New_bry(err));
		return false;
	}
}
