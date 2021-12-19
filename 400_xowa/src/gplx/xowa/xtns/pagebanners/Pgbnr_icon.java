/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.pagebanners;

import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.itms.BryFmt;
import gplx.types.basics.utls.StringUtl;
import gplx.langs.mustaches.Mustache_bfr;
import gplx.langs.mustaches.Mustache_doc_itm;
import gplx.langs.mustaches.Mustache_doc_itm_;

public class Pgbnr_icon implements Mustache_doc_itm {
	private final byte[] name, title, href, html;
	public Pgbnr_icon(BryWtr tmp_bfr, byte[] name, byte[] title, byte[] href) {
		this.name = name; this.title = title; this.href = href;
		fmt.Bld_many(tmp_bfr, name, title);
		this.html = tmp_bfr.ToBryAndClear();
	}
	public byte[] Name() {return name;}
	public byte[] Title() {return title;}
	public byte[] Href() {return href;}
	public byte[] Html() {return html;}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(StringUtl.Eq(key, "name"))			bfr.Add_bry(name);
		else if	(StringUtl.Eq(key, "title"))			bfr.Add_bry(title);
		else if	(StringUtl.Eq(key, "url"))			bfr.Add_bry(href);
		else if	(StringUtl.Eq(key, "html"))			bfr.Add_bry(html);
		else										return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {return Mustache_doc_itm_.Ary__empty;}
	public static final Pgbnr_icon[] Ary_empty = new Pgbnr_icon[0];
	public static final BryFmt fmt = BryFmt.New
	( BryUtlByWtr.NewU8NlSwapApos("<span aria-disabled='false' title='~{title}' class='oo-ui-widget oo-ui-widget-enabled oo-ui-iconElement-icon oo-ui-icon-~{name} oo-ui-iconElement oo-ui-iconWidget'></span>")
	, "name", "title"
	);
}
