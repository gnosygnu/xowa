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
package gplx.gfui.controls.elems;
import gplx.frameworks.evts.Gfo_evt_mgr_;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.StringUtl;
public class GfuiElemList {
	public int Count()						{return hash.Len();}
	public GfuiElem Get_at(int idx)		{return (GfuiElem)hash.GetAt(idx);}
	public GfuiElem Get_by(String key)		{return (GfuiElem)hash.GetByOrNull(key);}
	public void Add(GfuiElem box)			{Add_exec(box);}
	public void DelOrFail(GfuiElem box)		{Del_exec(box);}
	public void Del_at(int idx)				{Del_exec(Get_at(idx));}
	public int IndexOfA(GfuiElem box)		{return hash.IdxOf(box);}
	public void Move_to(int src, int trg)	{hash.MoveTo(src, trg);}
	public void Clear() {
		for (int i = 0; i < this.Count(); i++)
			Del_exec(this.Get_at(i));
		hash.Clear();
	}
	void Add_exec(GfuiElem box) {
		String key = box.Key_of_GfuiElem(); if (StringUtl.Eq(key, StringUtl.Empty)) throw ErrUtl.NewArgs("box does not have key", "type", box.getClass(), "owner", owner.Key_of_GfuiElem(), "ownerSubs", owner.SubElems().Count());
		hash.Add(key, box);
		owner.UnderElem().Core().Controls_add(box.UnderElem());
		box.OwnerElem_(owner).OwnerWin_(owner.OwnerWin()); // needed b/c box may be added after form is loaded
		Gfo_evt_mgr_.Sub_same(box, GfuiElemKeys.IptRcvd_evt, owner);	// bubble iptEvts to owner
	}
	void Del_exec(GfuiElem box) {
		String key = box.Key_of_GfuiElem(); if (!hash.Has(key)) throw ErrUtl.NewMissingKey(key);
		hash.Del(key);
		owner.UnderElem().Core().Controls_del(box.UnderElem());
		owner.IptBnds().Cfgs_delAll();
		box.Dispose();
	}
	GfuiElem owner; Ordered_hash hash = Ordered_hash_.New();
	public static GfuiElemList new_(GfuiElem owner) {
		GfuiElemList rv = new GfuiElemList();
		rv.owner = owner;
		return rv;
	}	GfuiElemList() {}
}
