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
package gplx.xowa.addons.apps.cfgs.specials.edits.objs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.edits.*;
import gplx.langs.mustaches.*;
import gplx.core.gfobjs.*;
public class Xoedit_root implements Mustache_doc_itm {
	private final    Xoedit_nav_mgr nav_mgr;
	private final    Xoedit_grp[] grps;
	public Xoedit_root(Xoedit_nav_mgr nav_mgr, Xoedit_grp[] grps) {
		this.nav_mgr = nav_mgr;
		this.grps = grps;
	}
	public Gfobj_nde To_nde() {
		Gfobj_nde rv = Gfobj_nde.New();
		List_adp list = List_adp_.New();
		int len = grps.length;
		for (int i = 0; i < len; i++) {
			Xoedit_grp itm = grps[i];
			list.Add(itm.To_nde());
		}
		rv.Add_ary("grps", new Gfobj_ary((Gfobj_nde[])list.To_ary_and_clear(Gfobj_nde.class)));
		return rv;
	}
	public boolean Mustache__write(String k, Mustache_bfr bfr) {
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "grps"))		return grps;
		else if	(String_.Eq(key, "nav_exists"))	return Mustache_doc_itm_.Ary__bool(nav_mgr.Itms().length > 1);	// NOTE: do not show combo if 0 or 1 item
		else if	(String_.Eq(key, "itms"))		return nav_mgr.Itms();
		return Mustache_doc_itm_.Ary__empty;
	}
}
