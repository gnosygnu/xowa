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
package gplx.xowa.addons.apps.cfgs.specials.edits.objs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.edits.*;
public class Xoedit_nde_hash {
	private final    Ordered_hash hash = Ordered_hash_.New(); 
	private final    Ordered_hash id_hash = Ordered_hash_.New(); 
	private final    List_adp deleted = List_adp_.New();
	public int Len() {
		return hash.Len();
	}
	public void Add(Xoedit_nde nde) {
		hash.Add(nde.Key(), nde);
		id_hash.Add(nde.Id(), nde);
	}
	public Xoedit_nde Get_at(int i) {
		return (Xoedit_nde)hash.Get_at(i);
	}
	public Xoedit_nde Get_by_or_fail(String key) {
		return (Xoedit_nde)hash.Get_by_or_fail(key);
	}
	public Xoedit_nde Get_by_or_fail(int id) {
		return (Xoedit_nde)id_hash.Get_by_or_fail(id);
	}
	public Xoedit_grp[] To_grp_ary_and_clear() {
		Xoedit_grp[] rv = (Xoedit_grp[])hash.To_ary_and_clear(Xoedit_grp.class);
		id_hash.Clear();
		return rv;
	}
	public void Delete_container_grps() {// remove container grps else headers with no items will show up
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			Xoedit_grp grp = (Xoedit_grp)hash.Get_at(i);
			if (grp.Itms().length == 0)
				deleted.Add(grp);
		}

		len = deleted.Len();
		for (int i = 0; i < len; i++) {
			Xoedit_grp grp = (Xoedit_grp)deleted.Get_at(i);
			hash.Del(grp.Key());
			id_hash.Del(grp.Id());
		}
	}

	public Xoedit_nde_hash Merge(Xoedit_nde_hash src) {
		int len = src.Len();
		for (int i = 0; i < len; i++) {
			this.Add(src.Get_at(i));
		}
		return this;
	}
	public void Deleted__add(Xoedit_nde nde) {
		deleted.Add(nde);
	}
	public void Deleted__commit() {
		int len = deleted.Len();
		for (int i = 0; i < len; i++) {
			Xoedit_nde nde = (Xoedit_nde)deleted.Get_at(i);
			hash.Del(nde.Key());
			id_hash.Del(nde.Id());
		}
	}
}
