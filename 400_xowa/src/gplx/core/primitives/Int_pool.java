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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
public class Int_pool {
	private final    List_adp available_list = List_adp_.New(); private int available_len;
	// private final    Bry_bfr dbg_bfr = Bry_bfr_.New();
	private int uid_max = -1;
	public void Clear() {
		synchronized (available_list) {
			available_list.Clear();
			available_len = 0;
			uid_max = -1;
		}
	}
	public int Get_next() {
		synchronized (available_list) {
			if (available_len == 0) {
				// dbg_bfr.Add_str("+:u:").Add_int_variable(uid_max + 1).Add_byte_nl();
				return ++uid_max;
			}
			else {
				Int_obj_val val = (Int_obj_val)List_adp_.Pop_last(available_list);
				--available_len;
				// dbg_bfr.Add_str("+:a:").Add_int_variable(val.Val()).Add_byte_nl();
				return val.Val();
			}
		}
	}
	public void Del(int v) {
		if (v > uid_max) throw Err_.new_("core", "value is greater than range", "value", v, "max", uid_max);
		synchronized (available_list) {
			if (available_len == 0 && v == uid_max) {
				--this.uid_max;
				// dbg_bfr.Add_str("-:m:").Add_int_variable(v).Add_byte_nl();
				return;
			}
			if (available_len == uid_max) {
				available_list.Add(new Int_obj_val(v));
				available_list.Sort();
				for (int i = 0; i < available_len; ++i) {
					Int_obj_val itm = (Int_obj_val)available_list.Get_at(i);
					if (i != itm.Val())
						throw Err_.new_("core", "available_list out of order", "contents", available_list.To_str());
				}
				// dbg_bfr.Add_str("-:c:").Add_int_variable(v).Add_byte_nl();
				this.Clear();
			}
			else {
				// dbg_bfr.Add_str("-:a:").Add_int_variable(v).Add_byte_nl();
				available_list.Add(new Int_obj_val(v));
				++available_len;
			}
		}
	}
}
