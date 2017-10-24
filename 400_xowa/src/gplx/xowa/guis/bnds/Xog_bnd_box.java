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
