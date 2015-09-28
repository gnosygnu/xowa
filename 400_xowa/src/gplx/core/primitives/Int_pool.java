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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
public class Int_pool {
	private final List_adp available_list = List_adp_.new_(); private int available_len;
	// private final Bry_bfr dbg_bfr = Bry_bfr.new_();
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
				available_list.Add(Int_obj_val.new_(v));
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
				available_list.Add(Int_obj_val.new_(v));
				++available_len;
			}
		}
	}
}
