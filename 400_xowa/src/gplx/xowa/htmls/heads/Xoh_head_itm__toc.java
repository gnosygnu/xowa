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
package gplx.xowa.htmls.heads; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.guis.*;
public class Xoh_head_itm__toc extends Xoh_head_itm__base {
	@Override public byte[] Key() {return Xoh_head_itm_.Key__toc;}
	@Override public int Flags() {return Flag__js_head_global | Flag__js_tail_script;}
	@Override public void Write_js_head_global(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_global_ini_atr_val(Key_exists		, true);
		wtr.Write_js_global_ini_atr_val(Key_collapsed	, wiki.Html_mgr().Head_mgr().Collapsible__toc() ? Val_collapsed_y : Val_collapsed_n);
		wtr.Write_js_global_ini_atr_msg(wiki			, Key_showtoc);
		wtr.Write_js_global_ini_atr_msg(wiki			, Key_hidetoc);
	}
	@Override public void Write_js_tail_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
//			wtr.Write_js_line(Xoh_head_itm__popups.Jquery_init);
//			wtr.Write_js_line(Xoh_head_itm__popups.Mw_init);
//			wtr.Write_js_tail_load_lib(app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("xowa", "html", "modules", "mw.toc", "mw.toc.js"));
	}
	private static final    byte[] 
	  Key_exists				= Bry_.new_a7("toc-enabled")
	, Key_collapsed				= Bry_.new_a7("mw_hidetoc")
	, Val_collapsed_y			= Bry_.new_a7("1")
	, Val_collapsed_n			= Bry_.new_a7("0")
	;
	public static final    byte[] 
	  Key_showtoc				= Bry_.new_a7("showtoc")
	, Key_hidetoc				= Bry_.new_a7("hidetoc")
	;
}
