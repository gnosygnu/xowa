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
package gplx.xowa.guis.bnds; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
public class Xog_bnd_box {
	private final    Ordered_hash key_hash = Ordered_hash_.New();
	private final    Hash_adp cmd_hash = Hash_adp_.New();
	public Xog_bnd_box(int tid, String key) {
		this.tid = tid;
		this.key = key;
	}
	public int Tid() {return tid;} private final    int tid;
	public String Key() {return key;} private final    String key;
	public int Len() {return key_hash.Count();}
	public Xog_bnd_itm Get_at(int i) {return (Xog_bnd_itm)key_hash.Get_at(i);}
	public void Add(Xog_bnd_itm itm) {
		key_hash.Add_if_dupe_use_nth(itm.Key(), itm);	// Add_if_dupe_use_nth, else Xou_user_tst.Run fails; DATE:2014-05-15

		// add by cmd; needed b/c gfui.ipt_mgr hashes by cmd ("sandbox"), not key ("sandbox-1"); DATE:2016-12-25
		List_adp list = (List_adp)cmd_hash.Get_by(itm.Cmd());
		if (list == null) {
			list = List_adp_.New();
			cmd_hash.Add(itm.Cmd(), list);
		}
		list.Add(itm);
	}
	public List_adp Get_list_by_cmd(String cmd) {return (List_adp)cmd_hash.Get_by(cmd);}
	public void Del(String key) {
		// delete from key_hash
		Xog_bnd_itm itm = (Xog_bnd_itm)key_hash.Get_by(key);
		key_hash.Del(key);

		// delete from cmd_hash
		List_adp list = (List_adp)cmd_hash.Get_by(itm.Cmd());
		if (list != null) list.Del(itm);
	}
}
