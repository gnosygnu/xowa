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
package gplx.xowa.addons.builds.files.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*; import gplx.xowa.addons.builds.files.*;
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
		Tfds.Eq(expd, String_.new_u8(Xobldr__lnki_temp__create.Xto_commons(wiki_ns_file_is_case_match_all, commons_wiki, Bry_.new_u8(ttl))));
	}
}
