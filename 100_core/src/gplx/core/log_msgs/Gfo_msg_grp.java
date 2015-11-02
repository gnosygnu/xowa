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
public class Gfo_msg_grp implements Gfo_msg_obj {
	public Gfo_msg_grp(Gfo_msg_grp owner, int uid, byte[] key) {
		this.owner = owner; this.uid = uid; this.key = key; this.key_str = String_.new_a7(key);
		if (owner != null) {
			owner.subs.Add(this);
			path = Gfo_msg_grp_.Path(owner.path, key);
		}
		else
			path = Bry_.Empty;
	}
	public void Subs_clear() {subs.Clear();}
	public Gfo_msg_grp Owner() {return owner;} Gfo_msg_grp owner;
	public int Uid() {return uid;} int uid;
	public byte[] Key() {return key;} private byte[] key;
	public String Key_str() {return key_str;} private String key_str;
	public byte[] Path() {return path;} private byte[] path;
	public String Path_str() {return String_.new_a7(path);}
	public Gfo_msg_obj Subs_get_by_key(String sub_key) {
		int subs_len = subs.Count();
		for (int i = 0; i < subs_len; i++) {
			Gfo_msg_obj sub = (Gfo_msg_obj)subs.Get_at(i);
			if (String_.Eq(sub_key, sub.Key_str())) return sub;
		}
		return null;
	}
	public void Subs_add(Gfo_msg_itm item) {subs.Add(item);}
	List_adp subs = List_adp_.new_();
}
