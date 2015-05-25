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
package gplx.gfui; import gplx.*;
public class GfuiElemList {
	public int Count()						{return hash.Count();}
	public GfuiElem Get_at(int idx)		{return (GfuiElem)hash.Get_at(idx);}
	public GfuiElem Get_by(String key)		{return (GfuiElem)hash.Get_by(key);}
	public void Add(GfuiElem box)			{Add_exec(box);}
	public void DelOrFail(GfuiElem box)		{Del_exec(box);}
	public void Del_at(int idx)				{Del_exec(Get_at(idx));}
	public int IndexOfA(GfuiElem box)		{return hash.Idx_of(box);}
	public void Move_to(int src, int trg)	{hash.Move_to(src, trg);}
	public void Clear() {
		for (int i = 0; i < this.Count(); i++)
			Del_exec(this.Get_at(i));
		hash.Clear();
	}
	void Add_exec(GfuiElem box) {
		String key = box.Key_of_GfuiElem(); if (String_.Eq(key, String_.Empty)) throw Err_.new_("box does not have key").Add("type", box.getClass()).Add("owner", owner.Key_of_GfuiElem()).Add("ownerSubs", owner.SubElems().Count());
		hash.Add(key, box);
		owner.UnderElem().Core().Controls_add(box.UnderElem());
		box.OwnerElem_(owner).OwnerWin_(owner.OwnerWin()); // needed b/c box may be added after form is loaded
		GfoEvMgr_.SubSame(box, GfuiElemKeys.IptRcvd_evt, owner);	// bubble iptEvts to owner
	}
	void Del_exec(GfuiElem box) {
		String key = box.Key_of_GfuiElem(); if (!hash.Has(key)) throw Err_.missing_key_(key);
		hash.Del(key);
		owner.UnderElem().Core().Controls_del(box.UnderElem());
		owner.IptBnds().Cfgs_delAll();
		box.Dispose();
	}
	GfuiElem owner; Ordered_hash hash = Ordered_hash_.new_();
	public static GfuiElemList new_(GfuiElem owner) {
		GfuiElemList rv = new GfuiElemList();
		rv.owner = owner;
		return rv;
	}	GfuiElemList() {}
}
