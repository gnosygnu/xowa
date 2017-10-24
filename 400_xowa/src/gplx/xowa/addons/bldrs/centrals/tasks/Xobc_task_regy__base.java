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
package gplx.xowa.addons.bldrs.centrals.tasks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import gplx.core.gfobjs.*;
public abstract class Xobc_task_regy__base {
	private final    Ordered_hash hash = Ordered_hash_.New();
	protected final    Xobc_task_mgr task_mgr;
	public Xobc_task_regy__base(Xobc_task_mgr task_mgr, String name) {this.task_mgr = task_mgr; this.name = name;}
	public String					Name()					{return name;} private final    String name;
	public int						Len()					{return hash.Len();}
	public void						Add(Xobc_task_itm t)	{hash.Add(t.Task_id(), t);}
	public void						Clear()					{hash.Clear();}
	public Xobc_task_itm			Get_at(int i)			{return (Xobc_task_itm)hash.Get_at(i);}
	public Xobc_task_itm			Get_by(int i)			{return (Xobc_task_itm)hash.Get_by(i);}
	public void						Del_by(int i)			{hash.Del(i);}
	public void						Sort()					{hash.Sort();}

	public void Save_to(Gfobj_ary ary) {Save_to(ary, (Xobc_task_itm[])hash.To_ary(Xobc_task_itm.class));}
	public void Save_to(Gfobj_ary ary, Xobc_task_itm[] itms) {
		int len = itms.length;
		Gfobj_nde[] sub_ndes = new Gfobj_nde[len];
		for (int i = 0; i < len; ++i) {
			Gfobj_nde sub_nde = sub_ndes[i] = Gfobj_nde.New();
			itms[i].Save_to(sub_nde);
		}
		ary.Ary_(sub_ndes);
	}
}
