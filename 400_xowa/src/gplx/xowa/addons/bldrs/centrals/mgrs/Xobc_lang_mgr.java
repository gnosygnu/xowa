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
