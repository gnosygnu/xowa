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
import gplx.langs.mustaches.*;
import gplx.core.gfobjs.*;
public class Xoedit_root implements Mustache_doc_itm {
	private final    Xoedit_nav_mgr nav_mgr;
	private final    Xoedit_grp[] grps;
	private final    String page_help;
	private final    boolean app_is_drd;
	public Xoedit_root(Xoedit_nav_mgr nav_mgr, String page_help, Xoedit_grp[] grps) {
		this.nav_mgr = nav_mgr;
		this.page_help = page_help;
		this.grps = grps;
		this.app_is_drd = gplx.core.envs.Op_sys.Cur().Tid_is_drd();
	}
	public Gfobj_nde To_nde(Bry_bfr tmp_bfr) {
		Gfobj_nde rv = Gfobj_nde.New();
		List_adp list = List_adp_.New();
		int len = grps.length;
		for (int i = 0; i < len; i++) {
			Xoedit_grp itm = grps[i];
			list.Add(itm.To_nde(tmp_bfr));
		}
		rv.Add_str("page_help", page_help);
		rv.Add_ary("grps", new Gfobj_ary((Gfobj_nde[])list.To_ary_and_clear(Gfobj_nde.class)));
		return rv;
	}
	public boolean Mustache__write(String k, Mustache_bfr bfr) {
		if		(String_.Eq(k, "page_help"))	bfr.Add_str_u8(page_help);
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String k) {
		if		(String_.Eq(k, "grps"))			return grps;
		else if	(String_.Eq(k, "nav_exists"))	return Mustache_doc_itm_.Ary__bool(nav_mgr.Itms().length > 1);	// NOTE: do not show combo if 0 or 1 item
		else if	(String_.Eq(k, "app_is_drd"))	return Mustache_doc_itm_.Ary__bool(app_is_drd);
		else if	(String_.Eq(k, "itms"))			return nav_mgr.Itms();
		return Mustache_doc_itm_.Ary__empty;
	}
}
