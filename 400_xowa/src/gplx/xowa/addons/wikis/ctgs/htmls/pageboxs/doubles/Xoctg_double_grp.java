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
import gplx.core.brys.*; import gplx.core.brys.fmts.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.users.history.*;
public class Xoctg_double_grp implements Bfr_arg {
	private byte[] lbl_ctg_help, lbl_ctg_text, lbl_hidden;
	public boolean Type_is_normal() {return type_is_normal;} private boolean type_is_normal;
	public Xoctg_double_itm Itms() {return itms;} private final    Xoctg_double_itm itms = new Xoctg_double_itm();
	public void Init_by_wiki(Xow_wiki wiki, Xou_history_mgr history_mgr, boolean type_is_normal) {
		this.type_is_normal = type_is_normal;
		lbl_ctg_text	= wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_ctg_tbl_hdr);
		lbl_ctg_help	= Xol_msg_mgr_.Get_msg_val(wiki, wiki.Lang(), Key_pagecategorieslink, Bry_.Ary_empty);
		lbl_hidden		= wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_ctg_tbl_hidden);
		itms.Init_by_wiki(wiki, history_mgr);
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (type_is_normal)
			Fmt__normal.Bld_many(bfr, lbl_ctg_help, lbl_ctg_text, itms);
		else
			Fmt__hidden.Bld_many(bfr, lbl_hidden, itms);
	}

	private static final    byte[] Key_pagecategorieslink = Bry_.new_a7("pagecategorieslink");
	private static final    Bry_fmt 
	  Fmt__normal = Bry_fmt.Auto_nl_skip_last
	( "" 
	, "<div id=\"mw-normal-catlinks\" class=\"mw-normal-catlinks\">"
	,   "<a href=\"/wiki/~{ctg_help_page}\" title=\"~{ctg_help_page}\">~{ctg_text}</a>:"
	,   "<ul>~{grp_itms}"
	,   "</ul>"
	, "</div>"
	)
	, Fmt__hidden = Bry_fmt.Auto_nl_skip_last
	( "" 
	, "<div id=\"mw-hidden-catlinks\" class=\"mw-hidden-catlinks mw-hidden-cats-user-shown\">~{hidden_ctg_txt}:"
	,   "<ul>~{grp_itms}"
	,   "</ul>"
	, "</div>"
	);
}
