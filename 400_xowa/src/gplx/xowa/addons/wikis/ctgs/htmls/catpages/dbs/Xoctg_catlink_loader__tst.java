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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import org.junit.*; import gplx.core.tests.*; import gplx.xowa.apps.urls.*;
import gplx.xowa.langs.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.langs.*;
public class Xoctg_catlink_loader__tst {
	private final    Xoctg_catlink_loader__fxt fxt = new Xoctg_catlink_loader__fxt();
	@Test   public void Build_sortkey_val__v4() {			// PURPOSE: remove "\n" and afterwards else will omit 1 record
		fxt.Test__build_sortkey_sql(4, "A\nA", "x'41'");	// fails if "x'410a41'"
	}
	@Test   public void Build_sortkey_val__v2() {			// PURPOSE: remove "\n" and afterwards else SQL will be malformed
		fxt.Test__build_sortkey_sql(2, "A\nA", "'A'");		// fails if "'A\nA'"
	}
}
class Xoctg_catlink_loader__fxt {
	public void Test__build_sortkey_sql(int version, String sortkey, String expd) {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app, "de.wikipedia.org");	// use "de.wikipedia.org" for simple "uppercase" collation
		Xoctg_collation_mgr collation_mgr = new Xoctg_collation_mgr(wiki);

		Bry_bfr bfr = Bry_bfr_.New();
		Gftest.Eq__str(expd, Xoctg_catlink_loader.Build_sortkey_val(bfr, Byte_.By_int(version), collation_mgr, Bry_.new_u8(sortkey)));
	}
}
