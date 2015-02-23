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
package gplx.xowa.bldrs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*;
public class Xob_lnki_temp_wkr_tst {
	private Xob_lnki_temp_wkr_fxt fxt = new Xob_lnki_temp_wkr_fxt();
	@Test   public void Xto_commons() {
		fxt.Init_Xto_commons(true);
		fxt.Test_Xto_commons("a", "A");
		fxt.Test_Xto_commons("A", null);

		fxt.Init_Xto_commons(false);
		fxt.Test_Xto_commons("a", null);
		fxt.Test_Xto_commons("A", null);
	}
}
class Xob_lnki_temp_wkr_fxt {
	private boolean wiki_ns_file_is_case_match_all;
	private Xowe_wiki commons_wiki;
	public Xob_lnki_temp_wkr_fxt Init_Xto_commons(boolean wiki_ns_file_is_case_match_all) {
		Xoae_app app = Xoa_app_fxt.app_();
		this.wiki_ns_file_is_case_match_all = wiki_ns_file_is_case_match_all;
		this.commons_wiki = Xoa_app_fxt.wiki_tst_(app);	// commons_wiki will default to Xow_ns.Id_file of case_match_1st
		return this;
	}

	public void Test_Xto_commons(String ttl, String expd) {
		Tfds.Eq(expd, String_.new_utf8_(Xob_lnki_temp_wkr.Xto_commons(wiki_ns_file_is_case_match_all, commons_wiki, Bry_.new_utf8_(ttl))));
	}
}
