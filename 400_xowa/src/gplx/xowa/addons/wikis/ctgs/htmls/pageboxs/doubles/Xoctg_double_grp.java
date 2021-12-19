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
package gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.doubles;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.itms.BryFmt;
import gplx.xowa.*;
import gplx.xowa.langs.msgs.*;
import gplx.xowa.users.history.*;
public class Xoctg_double_grp implements BryBfrArg {
	private Xow_wiki wiki;
	private byte[] lbl_ctg_help;
	public boolean Type_is_normal() {return type_is_normal;} private boolean type_is_normal;
	public Xoctg_double_itm Itms() {return itms;} private final Xoctg_double_itm itms = new Xoctg_double_itm();
	public void Init_by_wiki(Xow_wiki wiki, Xou_history_mgr history_mgr, boolean type_is_normal) {
		this.type_is_normal = type_is_normal;
		this.wiki = wiki;
		this.lbl_ctg_help = Xol_msg_mgr_.Get_msg_val(wiki, wiki.Lang(), Key_pagecategorieslink, BryUtl.AryEmpty);
		itms.Init_by_wiki(wiki, history_mgr);
	}
	public void AddToBfr(BryWtr bfr) {
		int count =  itms.Itms__count();
		if (type_is_normal) {
			byte[] lbl_ctg_text = wiki.Msg_mgr().Val_by_key_args(Key_pagecategories, count);
			Fmt__normal.Bld_many(bfr, lbl_ctg_help, lbl_ctg_text, itms);
		}
		else {
			byte[] lbl_hidden = wiki.Msg_mgr().Val_by_id_args(Xol_msg_itm_.Id_hidden_categories, count); // use "hidden-categories" instead of "hidden-category-category" ISSUE#:674 DATE:2020-03-04
			Fmt__hidden.Bld_many(bfr, lbl_hidden, itms);
		}
	}

	private static final byte[]
	  Key_pagecategorieslink = BryUtl.NewA7("pagecategorieslink")
	, Key_pagecategories     = BryUtl.NewA7("pagecategories")
	;
	private static final BryFmt
	  Fmt__normal = BryFmt.Auto_nl_skip_last
	( "" 
	, "<div id=\"mw-normal-catlinks\" class=\"mw-normal-catlinks\">"
	,   "<a href=\"/wiki/~{ctg_help_page}\" title=\"~{ctg_help_page}\">~{ctg_text}</a>:"
	,   "<ul>~{grp_itms}"
	,   "</ul>"
	, "</div>"
	)
	, Fmt__hidden = BryFmt.Auto_nl_skip_last
	( "" 
	, "<div id=\"mw-hidden-catlinks\" class=\"mw-hidden-catlinks mw-hidden-cats-user-shown\">~{hidden_ctg_txt}:"
	,   "<ul>~{grp_itms}"
	,   "</ul>"
	, "</div>"
	);
}
