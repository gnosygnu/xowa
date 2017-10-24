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
package gplx.xowa.guis.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
public class Xog_cmd_ctg {
	public Xog_cmd_ctg(int tid, String key_str) {this.tid = tid; this.key_str = key_str; this.key_bry = Bry_.new_u8(key_str);}
	public int Tid() {return tid;} private int tid;
	public String Key_str() {return key_str;} private String key_str;
	public byte[] Key_bry() {return key_bry;} private byte[] key_bry;
	public String Name() {return name;} public Xog_cmd_ctg Name_(String v) {name = v; return this;} private String name;
	public String Info() {return info;} public Xog_cmd_ctg Info_(String v) {info = v; return this;} private String info;
}
class Xog_ctg_itm_ {
	public static final int
	  Tid__max			= 15
	, Tid_null			=  0
	, Tid_app			=  1
	, Tid_nav			=  1
	, Tid_nav_pages		=  2
	, Tid_font			=  3
	, Tid_page			=  4
	, Tid_edit			=  5
	, Tid_selection		=  6
	, Tid_browser		=  7
	, Tid_tabs			=  8
	, Tid_html			=  9
	, Tid_net			= 10
	, Tid_bookmarks		= 11
	, Tid_history		= 12
	, Tid_xtns			= 13
	, Tid_custom		= 14
	;
	public static final Xog_cmd_ctg[] Ary = new Xog_cmd_ctg[Tid__max];
	public static final Xog_cmd_ctg
	  Itm_null				= new_(Tid_null				, "xowa.null")
	, Itm_app				= new_(Tid_app				, "xowa.app")
	, Itm_nav				= new_(Tid_nav				, "xowa.nav")
	, Itm_nav_pages			= new_(Tid_nav_pages		, "xowa.nav.pages")
	, Itm_font				= new_(Tid_font				, "xowa.font")
	, Itm_page				= new_(Tid_page				, "xowa.page")
	, Itm_edit				= new_(Tid_edit				, "xowa.edit")
	, Itm_selection			= new_(Tid_selection		, "xowa.selection")
	, Itm_browser			= new_(Tid_browser			, "xowa.browser")
	, Itm_tabs				= new_(Tid_tabs				, "xowa.tabs")
	, Itm_html				= new_(Tid_html				, "xowa.html")
	, Itm_net				= new_(Tid_net				, "xowa.net")
	, Itm_bookmarks			= new_(Tid_bookmarks		, "xowa.bookmarks")
	, Itm_history			= new_(Tid_history			, "xowa.history")
	, Itm_xtns				= new_(Tid_xtns				, "xowa.xtns")
	, Itm_custom			= new_(Tid_custom			, "custom")
	;
	private static Xog_cmd_ctg new_(int tid, String code) {
		Xog_cmd_ctg rv = new Xog_cmd_ctg(tid, code);
		Ary[tid] = rv;
		return rv;
	}
}
