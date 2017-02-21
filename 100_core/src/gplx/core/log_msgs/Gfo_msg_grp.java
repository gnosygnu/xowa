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
	List_adp subs = List_adp_.New();
}
