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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import gplx.core.intls.ucas.*;
public class Xoctg_collation_mgr {
	private final    Xow_wiki wiki;
	private Xoctg_collation_wkr wkr;
	public Xoctg_collation_mgr(Xow_wiki wiki) {
		this.wiki = wiki;
		this.wkr = new Xoctg_collation_wkr__uppercase(wiki);	// REF:https://noc.wikimedia.org/conf/InitialiseSettings.php.txt|wgCategoryCollation|default
	}
	public void Collation_name_(String v) {
		this.wkr = Xoctg_collation_wkr_.Make(wiki, v);
	}
	public byte[] Get_sortkey(byte[] src) {
		return wkr.Get_sortkey(src);
	}
}
