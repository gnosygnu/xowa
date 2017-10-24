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
package gplx.xowa.addons.bldrs.centrals.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import gplx.core.gfobjs.*;
import gplx.xowa.addons.bldrs.centrals.tasks.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.langs.*;
public class Xobc_filter_mgr {
	private String lang_key_str = Xow_domain_itm_.Lang_key__all, type_key_str = Xow_domain_itm_.Type_key__all;
	public Xobc_task_itm[] Filter(Xobc_task_regy__base task_list) {return Filter(task_list, lang_key_str, type_key_str);}
	public Xobc_task_itm[] Filter(Xobc_task_regy__base task_list, String lang_key_str, String type_key_str) {
		this.lang_key_str = lang_key_str;
		this.type_key_str = type_key_str;

		List_adp tmp = List_adp_.New();

		// loop tasks and find matches
		int len = task_list.Len();
		for (int i = 0; i < len; ++i) {
			Xobc_task_itm task = (Xobc_task_itm)task_list.Get_at(i);
			Xobc_task_key task_key_itm = Xobc_task_key.To_itm(task.Task_key());
			Xow_domain_itm task_domain = task_key_itm.Wiki_domain_itm();
			if (	Xow_domain_itm_.Match_lang(task_domain, lang_key_str)
				&&	Xow_domain_itm_.Match_type(task_domain, type_key_str)
				)
				tmp.Add(task);
		}

		return (Xobc_task_itm[])tmp.To_ary_and_clear(Xobc_task_itm.class);
	}
	public Gfobj_nde Make_init_msg() {
		Gfobj_nde root = Gfobj_nde.New();
		root.New_nde("langs").Add_str("active", lang_key_str);
		root.New_nde("types").Add_str("active", type_key_str);
		return root;
	}
}
