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
package gplx.xowa.xtns.pagebanners; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.brys.fmtrs.*;
import gplx.langs.mustaches.*;
public class Pgbnr_icon implements Mustache_doc_itm {
	private final    byte[] name, title, href, html;
	public Pgbnr_icon(Bry_bfr tmp_bfr, byte[] name, byte[] title, byte[] href) {
		this.name = name; this.title = title; this.href = href;
		fmt.Bld_many(tmp_bfr, name, title);
		this.html = tmp_bfr.To_bry_and_clear();
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "name"))			bfr.Add_bry(name);
		else if	(String_.Eq(key, "title"))			bfr.Add_bry(title);
		else if	(String_.Eq(key, "url"))			bfr.Add_bry(href);
		else if	(String_.Eq(key, "html"))			bfr.Add_bry(html);
		else										return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {return Mustache_doc_itm_.Ary__empty;}
	public static final    Pgbnr_icon[] Ary_empty = new Pgbnr_icon[0];
	public static final    Bry_fmt fmt = Bry_fmt.New
	( Bry_.New_u8_nl_apos("<span aria-disabled='false' title='~{title}' class='oo-ui-widget oo-ui-widget-enabled oo-ui-iconElement-icon oo-ui-icon-~{name} oo-ui-iconElement oo-ui-iconWidget'></span>")
	, "name", "title"
	);
}
