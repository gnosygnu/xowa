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
package gplx.xowa.wikis.domains;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import org.junit.*;
public class Xow_domain_uid__tst {
	@Before public void init() {fxt.Clear();} private final Xow_domain_uid__fxt fxt = new Xow_domain_uid__fxt();
	@Test public void Basic() {
		fxt.Test(Xow_domain_uid_.Tid_commons	, "commons.wikimedia.org"	, "", Xow_domain_tid_.Tid__commons);
		fxt.Test(100							, "en.wikipedia.org"		, "en", Xow_domain_tid_.Tid__wikipedia);
	}
}
class Xow_domain_uid__fxt {
	public void Clear() {}
	public void Test(int tid, String domain_str, String expd_lang, int expd_tid) {
		byte[] domain_bry = BryUtl.NewA7(domain_str);
		Xow_domain_itm actl_domain = Xow_domain_uid_.To_domain(tid);
		GfoTstr.Eq(domain_bry					, actl_domain.Domain_bry());
		GfoTstr.Eq(BryUtl.NewA7(expd_lang)		, actl_domain.Lang_actl_key());
		GfoTstr.EqObj(expd_tid						, actl_domain.Domain_type_id());
		GfoTstr.EqObj(tid, Xow_domain_uid_.To_int(actl_domain));
	}
}
