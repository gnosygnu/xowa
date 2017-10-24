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
import gplx.xowa.addons.bldrs.centrals.dbs.datas.*;
public class Xobc_lang_mgr {
	public Gfobj_ary Make_init_msg(Xobc_lang_regy_itm[] itms) {
		List_adp list = List_adp_.New();
		int len = itms.length;
		for (int i = 0; i < len; ++i) {
			Xobc_lang_regy_itm itm = itms[i];
			Gfobj_nde itm_nde = Gfobj_nde.New();
			list.Add(itm_nde);
			itm_nde.Add_str("key", itm.Key());
			itm_nde.Add_str("name", itm.Name());
		}
		return new Gfobj_ary((Gfobj_nde[])list.To_ary_and_clear(Gfobj_nde.class));
	}
}
