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