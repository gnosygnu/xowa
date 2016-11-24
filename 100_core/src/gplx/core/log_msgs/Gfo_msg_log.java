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
package gplx.core.log_msgs; import gplx.*; import gplx.core.*;
public class Gfo_msg_log {
	public Gfo_msg_log(String root_key) {root = new Gfo_msg_root(root_key);} Gfo_msg_root root;
	public int Ary_len() {return ary_idx;}
	public Gfo_msg_data Ary_get(int i) {return ary[i];}
	public Gfo_msg_log Clear() {
		synchronized (this) {	// TS: DATE:2016-07-06
			for (int i = 0; i < ary_idx; i++)
				ary[i].Clear();
			ary_idx = 0;
			return this;
		}
	}
	public Gfo_msg_log Add_str_warn_key_none(String grp, String itm, byte[] src, int pos)								{return Add_str(Gfo_msg_itm_.Cmd_warn, grp, itm, null, src, pos, pos + 1, null);}
	public Gfo_msg_log Add_str_warn_key_none(String grp, String itm, byte[] src, int bgn, int end)						{return Add_str(Gfo_msg_itm_.Cmd_warn, grp, itm, null, src, bgn, end, null);}
	public Gfo_msg_log Add_str_warn_fmt_none(String grp, String itm, String fmt)										{return Add_str(Gfo_msg_itm_.Cmd_warn, grp, itm, fmt , Bry_.Empty, -1, -1, null);}
	public Gfo_msg_log Add_str_warn_fmt_none(String grp, String itm, String fmt, byte[] src, int pos)					{return Add_str(Gfo_msg_itm_.Cmd_warn, grp, itm, fmt , src, pos, pos + 1, null);}
	public Gfo_msg_log Add_str_warn_fmt_none(String grp, String itm, String fmt, byte[] src, int bgn, int end)			{return Add_str(Gfo_msg_itm_.Cmd_warn, grp, itm, fmt , src, bgn, end, null);}
	public Gfo_msg_log Add_str_warn_fmt_many(String grp, String itm, String fmt, Object... vals)					{return Add_str(Gfo_msg_itm_.Cmd_warn, grp, itm, fmt , Bry_.Empty, -1, -1, vals);}
	Gfo_msg_log Add_str(byte cmd, String owner_key, String itm, String fmt, byte[] src, int bgn, int end, Object[] vals) {
		synchronized (this) {	// TS: DATE:2016-07-06
			if (ary_idx >= ary_max) ary_expand();
			ary[ary_idx++] = root.Data_new_many(cmd, src, bgn, end, owner_key, itm, fmt, vals);
			return this;
		}
	}
	public Gfo_msg_log Add_itm_none(Gfo_msg_itm itm, byte[] src, int bgn, int end)										{return Add_itm(itm, src, bgn, end, null);}
	public Gfo_msg_log Add_itm_many(Gfo_msg_itm itm, byte[] src, int bgn, int end, Object... val_ary)			{return Add_itm(itm, src, bgn, end, val_ary);}
	Gfo_msg_log Add_itm(Gfo_msg_itm itm, byte[] src, int bgn, int end, Object[] vals) {
		synchronized (this) {	// TS: DATE:2016-07-06
			if (ary_idx >= ary_max) ary_expand();
			ary[ary_idx++] = root.Data_new_many(itm, src, bgn, end, vals);
			return this;
		}
	}
	void ary_expand() {
		int new_max = ary_max == 0 ? 2 : ary_max * 2;  
		ary = (Gfo_msg_data[])Array_.Expand(ary, new Gfo_msg_data[new_max], ary_max);
		ary_max = new_max;			
	}
	Gfo_msg_data[] ary = Gfo_msg_data.Ary_empty; int ary_idx, ary_max;
        public static Gfo_msg_log Test() {return new Gfo_msg_log("test");}
}
