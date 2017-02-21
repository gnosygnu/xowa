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
package gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.doubles; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.*;
import gplx.core.brys.*; import gplx.core.brys.fmts.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.users.history.*;
import gplx.xowa.addons.wikis.ctgs.htmls.*;
public class Xoctg_double_box implements Bfr_arg {
	public Xoctg_double_grp Grp_normal() {return grp_normal;} private final    Xoctg_double_grp grp_normal = new Xoctg_double_grp();
	public Xoctg_double_grp Grp_hidden() {return grp_hidden;} private final    Xoctg_double_grp grp_hidden = new Xoctg_double_grp();
	public void Init_by_wiki(Xow_wiki wiki) {
		Xou_history_mgr history_mgr = wiki.App().User().History_mgr();
		grp_normal.Init_by_wiki(wiki, history_mgr, Bool_.Y);
		grp_hidden.Init_by_wiki(wiki, history_mgr, Bool_.N);
	}
	public void Write_pagebox(Bry_bfr bfr, Xoctg_pagebox_itm[] ary) {
		grp_normal.Itms().Itms__clear();
		grp_hidden.Itms().Itms__clear();

		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xoctg_pagebox_itm itm = ary[i];
			Xoctg_double_grp list = itm.Hidden() ? grp_hidden : grp_normal; 
			list.Itms().Itms__add(itm);
		}
		this.Bfr_arg__add(bfr);
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		Fmt__all.Bld_many(bfr, grp_normal, grp_hidden);
	}
	private static final    Bry_fmt 
	  Fmt__all = Bry_fmt.Auto_nl_skip_last
	( "<div id=\"catlinks\" class=\"catlinks\">~{grp_normal}~{grp_hidden}"
	, "</div>"
	);
}
