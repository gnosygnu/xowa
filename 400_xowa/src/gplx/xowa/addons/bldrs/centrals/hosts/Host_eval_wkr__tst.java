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
package gplx.xowa.addons.bldrs.centrals.hosts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import org.junit.*; import gplx.core.tests.*; import gplx.xowa.wikis.domains.*;
public class Host_eval_wkr__tst {
	private final    Host_eval_wkr__fxt fxt = new Host_eval_wkr__fxt();
	@Test 	public void En_w()		{fxt.Test__resolve_quick("en.wikipedia.org"			, "Xowa_enwiki_latest");}
	@Test 	public void Fr_d()		{fxt.Test__resolve_quick("fr.wiktionary.org"		, "Xowa_frwiki_latest");}
	@Test 	public void Species()	{fxt.Test__resolve_quick("species.wikimedia.org"	, "Xowa_enwiki_latest");}
}
class Host_eval_wkr__fxt {
	public void Test__resolve_quick(String domain_str, String expd) {
		Host_eval_itm eval_itm = new Host_eval_itm();
		Xow_domain_itm domain_itm = Xow_domain_itm_.parse(Bry_.new_u8(domain_str));
		Gftest.Eq__bry(Bry_.new_u8(expd), eval_itm.Eval_dir_name(domain_itm));
	}
}