/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.wikis.domains; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*;
public class Xow_domain_uid__tst {
	@Before public void init() {fxt.Clear();} private final Xow_domain_uid__fxt fxt = new Xow_domain_uid__fxt();
	@Test  public void Basic() {
		fxt.Test(Xow_domain_uid_.Tid_commons	, "commons.wikimedia.org"	, "", Xow_domain_type_.Tid_commons);
		fxt.Test(100							, "en.wikipedia.org"		, "en", Xow_domain_type_.Tid_wikipedia);
	}
}
class Xow_domain_uid__fxt {
	public void Clear() {}
	public void Test(int tid, String domain_str, String expd_lang, int expd_tid) {
		byte[] domain_bry = Bry_.new_a7(domain_str);
		Xow_domain actl_domain = Xow_domain_uid_.To_domain(tid);
		Tfds.Eq_bry(domain_bry					, actl_domain.Domain_bry());
		Tfds.Eq_bry(Bry_.new_a7(expd_lang)	, actl_domain.Lang_key());
		Tfds.Eq(expd_tid						, actl_domain.Domain_tid());
		Tfds.Eq(tid, Xow_domain_uid_.To_int(actl_domain));
	}
}
