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
public class Xoedit_nav_mgr implements Mustache_doc_itm {		
	public Xoedit_nav_itm[] Itms() {return itms;} private final    Xoedit_nav_itm[] itms;
	public Xoedit_nav_mgr(Xoedit_nav_itm[] itms) {
		this.itms = itms;
	}
	public boolean Mustache__write(String k, Mustache_bfr bfr) {
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String k) {
		if		(String_.Eq(k, "itms"))		return itms;
		return Mustache_doc_itm_.Ary__empty;
	}
}
