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
package gplx.xowa.htmls.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
public class Xow_hdump_mode {
	private final    int tid;
	private final    String key;
	private final    String gui;

	public Xow_hdump_mode(int tid, String key, String gui) {
		this.tid = tid; this.key = key; this.gui = gui;
	}
	public int Tid() {return tid;}
//		boolean			Tid_is_hdump_save() {return tid == Hdump_save.tid;}
	public boolean  Tid_is_custom() {return tid < Shown.tid;}

	public static final    Xow_hdump_mode
	  Swt_browser       = new Xow_hdump_mode(0, "swt_browser"	, "SWT Browser")
	, Hdump_save        = new Xow_hdump_mode(1, "hdump_save"	, "Saved for HTML DB")
	, Shown             = new Xow_hdump_mode(2, "shown"			, "Shown")
	, Hdump_load        = new Xow_hdump_mode(3, "hdump_load"	, "Loaded by HTML DB")
	;
	public static void Cfg__reg_type(gplx.xowa.addons.apps.cfgs.mgrs.types.Xocfg_type_mgr type_mgr) {
		type_mgr.Lists__add("list:xowa.wiki.hdumps.html_mode", To_kv(Shown), To_kv(Swt_browser), To_kv(Hdump_save), To_kv(Hdump_load));
	}
	private static Keyval To_kv(Xow_hdump_mode mode) {return Keyval_.new_(mode.key, mode.gui);}
	public static Xow_hdump_mode Parse(String v) {
		if		(String_.Eq(v, Shown.key))			return Shown;
		else if	(String_.Eq(v, Hdump_save.key))		return Hdump_save;
		else if	(String_.Eq(v, Hdump_load.key))		return Hdump_load;
		else if	(String_.Eq(v, Swt_browser.key))    return Swt_browser;
		else										throw Err_.new_unhandled(v);
	}
}
