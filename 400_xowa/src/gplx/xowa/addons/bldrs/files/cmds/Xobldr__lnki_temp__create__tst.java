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
package gplx.xowa.addons.bldrs.files.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import org.junit.*;
public class Xobldr__lnki_temp__create__tst {
	private Xobldr__lnki_temp__create__fxt fxt = new Xobldr__lnki_temp__create__fxt();
	@Test   public void Xto_commons() {
		fxt.Init__to_commons(true);
		fxt.Test__to_commons("a", "A");
		fxt.Test__to_commons("A", null);
		fxt.Init__to_commons(false);
		fxt.Test__to_commons("a", null);
		fxt.Test__to_commons("A", null);
	}
}
class Xobldr__lnki_temp__create__fxt {
	private boolean wiki_ns_file_is_case_match_all;
	private Xowe_wiki commons_wiki;
	public Xobldr__lnki_temp__create__fxt Init__to_commons(boolean wiki_ns_file_is_case_match_all) {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki_ns_file_is_case_match_all = wiki_ns_file_is_case_match_all;
		this.commons_wiki = Xoa_app_fxt.Make__wiki__edit(app);	// commons_wiki will default to Xow_ns.Id_file of case_match_1st
		return this;
	}
	public void Test__to_commons(String ttl, String expd) {
		Tfds.Eq(expd, String_.new_u8(gplx.xowa.addons.bldrs.mass_parses.parses.utls.Xomp_lnki_temp_wkr.To_commons_ttl(wiki_ns_file_is_case_match_all, commons_wiki, Bry_.new_u8(ttl))));
	}
}
