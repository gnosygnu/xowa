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
public class Gfo_msg_root {
	public Gfo_msg_root(String root_key) {
		this.root_key = root_key;
		this.root = Gfo_msg_grp_.new_(Gfo_msg_grp_.Root, root_key);
	}	String root_key;
	public void Data_ary_clear() {
		for (int i = 0; i < data_ary_idx; i++)
			data_ary[i].Clear();
		data_ary_idx = 0;
	}
	public void Data_ary_len_(int v) {
		data_ary_len = v;
		data_ary = new Gfo_msg_data[data_ary_len];
		for (int i = 0; i < data_ary_len; i++)
			data_ary[i] = new Gfo_msg_data();
		data_ary_idx = 0;
	}	int data_ary_len; int data_ary_idx; Gfo_msg_data[] data_ary;
	public void Reset() {
		root.Subs_clear();
		owners.Clear();
		uid_list_next = uid_item_next = 0;
		Data_ary_clear();
	}
	public Gfo_msg_data Data_new_note_many(String owner_key, String key, String fmt, Object... vals) {return Data_new_many(Gfo_msg_itm_.Cmd_note, Bry_.Empty, -1, -1, owner_key, key, fmt, vals);}
	public Gfo_msg_data Data_new_many(byte cmd, String owner_key, String key, String fmt, Object[] vals) {return Data_new_many(cmd, Bry_.Empty, -1, -1, owner_key, key, fmt, vals);}
	public Gfo_msg_data Data_new_many(byte cmd, byte[] src, int bgn, int end, String owner_key, String key, String fmt, Object[] vals) {
		Object owner_obj = owners.Get_by(owner_key);
		Gfo_msg_grp owner = null;
		if (owner_obj == null) {
			owner = New_list_by_key(owner_key);
			owners.Add(owner_key, owner);
		}
		else
			owner = (Gfo_msg_grp)owner_obj;
		Gfo_msg_itm itm = (Gfo_msg_itm)owner.Subs_get_by_key(key);
		if (itm == null)
			itm = new Gfo_msg_itm(owner, uid_item_next++, cmd, Bry_.new_u8(key), fmt == null ? Bry_.Empty : Bry_.new_a7(fmt), false);
		return Data_new_many(itm, src, bgn, end, vals);
	}
	public Gfo_msg_data Data_new_many(Gfo_msg_itm itm, byte[] src, int bgn, int end, Object... vals) {return Data_get().Ctor_src_many(itm, src, bgn, end, vals);}
	public Gfo_msg_data Data_get() {
		return data_ary_idx < data_ary_len ? data_ary[data_ary_idx++] : new Gfo_msg_data();
	}
	Gfo_msg_grp New_list_by_key(String key) {
		String[] segs = String_.Split(key, '.');
		int segs_len = segs.length; int segs_last = segs_len - 1;
		Gfo_msg_grp cur_list = root;
		for (int i = 0; i < segs_last; i++) {
			String seg = segs[i];
			Gfo_msg_grp sub_list = (Gfo_msg_grp)cur_list.Subs_get_by_key(seg);
			if (sub_list == null)
				sub_list = new Gfo_msg_grp(cur_list, uid_list_next++, Bry_.new_a7(key));
			cur_list = sub_list;
		}
		return cur_list;
	}
	Gfo_msg_grp root;
	Ordered_hash owners = Ordered_hash_.New();
	int uid_list_next = 0;
	int uid_item_next = 0;
        public static final Gfo_msg_root Instance = new Gfo_msg_root("gplx");
}
