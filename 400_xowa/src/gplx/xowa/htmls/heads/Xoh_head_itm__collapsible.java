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
package gplx.xowa.htmls.heads;
import gplx.types.basics.utls.BryUtl;
import gplx.xowa.*;
public class Xoh_head_itm__collapsible extends Xoh_head_itm__base {
	@Override public byte[] Key() {return Xoh_head_itm_.Key__collapsible;}
	@Override public int Flags() {return Flag__js_head_global;}
	@Override public void Write_js_head_global(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_global_ini_atr_val(Key_enabled		, true);
		wtr.Write_js_global_ini_atr_val(Key_collapsed	, wiki.Html_mgr().Head_mgr().Collapsible__collapsible());
		wtr.Write_js_global_ini_atr_msg(wiki			, Key_collapse);
		wtr.Write_js_global_ini_atr_msg(wiki			, Key_expand);
	}
	private static final byte[]
	  Key_enabled				= BryUtl.NewA7("collapsible-enabled")
	, Key_collapsed				= BryUtl.NewA7("collapsible-collapsed")
	, Key_collapse				= BryUtl.NewA7("collapsible-collapse")
	, Key_expand				= BryUtl.NewA7("collapsible-expand")
	;
}
