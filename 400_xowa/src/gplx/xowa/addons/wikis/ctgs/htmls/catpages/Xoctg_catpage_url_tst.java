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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*;
import org.junit.*; import gplx.xowa.apps.urls.*;
public class Xoctg_catpage_url_tst {
	@Before public void init() {fxt.Clear();} private Xoctg_catpage_url_fxt fxt = new Xoctg_catpage_url_fxt();
	@Test   public void Basic() {
		fxt.Test_parse("A?subcatfrom=B&filefrom=C&pagefrom=D", fxt.url().Grp_idxs_("B", "C", "D").Grp_fwds_(Bool_.Y_byte, Bool_.Y_byte, Bool_.Y_byte));
		fxt.Test_parse("A?subcatuntil=B&fileuntil=C&pageuntil=D", fxt.url().Grp_idxs_("B", "C", "D").Grp_fwds_(Bool_.N_byte, Bool_.N_byte, Bool_.N_byte));
		fxt.Test_parse("A?from=B", fxt.url().Grp_idxs_("B", "B", "B").Grp_fwds_(Bool_.Y_byte, Bool_.Y_byte, Bool_.Y_byte));
		fxt.Test_parse("A?until=B", fxt.url().Grp_idxs_("B", "B", "B").Grp_fwds_(Bool_.N_byte, Bool_.N_byte, Bool_.N_byte));
	}
}
class Xoctg_catpage_url_fxt {
	public Xoctg_catpage_url_chkr url() {return expd.Clear();} private Xoctg_catpage_url_chkr expd;
	public void Clear() {
		if (parser == null) {
			Xoa_app app = Xoa_app_fxt.Make__app__edit();
			parser = app.User().Wikii().Utl__url_parser();
			page_url = Xoa_url.blank();
			ctg_url = new Xoctg_catpage_url();
			expd = new Xoctg_catpage_url_chkr();
		}
	}	private Xow_url_parser parser; Xoa_url page_url; Xoctg_catpage_url ctg_url;
	public void Test_parse(String url_str, Xoctg_catpage_url_chkr expd) {
		page_url = parser.Parse(Bry_.new_u8(url_str));
		ctg_url.Parse(Gfo_usr_dlg_.Test(), page_url);
		expd.Chk(ctg_url);
		expd.Clear();
	}
}
class Xoctg_catpage_url_chkr {
	public Xoctg_catpage_url_chkr Grp_idxs_(String subc, String file, String page) {
		grp_idxs[Xoa_ctg_mgr.Tid_subc] = Bry_.new_a7(subc);
		grp_idxs[Xoa_ctg_mgr.Tid_file] = Bry_.new_a7(file);
		grp_idxs[Xoa_ctg_mgr.Tid_page] = Bry_.new_a7(page);
		return this;
	}	byte[][] grp_idxs = new byte[Xoa_ctg_mgr.Tid__max][];
	public Xoctg_catpage_url_chkr Grp_fwds_(byte subc, byte file, byte page) {
		grp_fwds[Xoa_ctg_mgr.Tid_subc] = subc;
		grp_fwds[Xoa_ctg_mgr.Tid_file] = file;
		grp_fwds[Xoa_ctg_mgr.Tid_page] = page;
		return this;
	}	byte[] grp_fwds = new byte[Xoa_ctg_mgr.Tid__max];		
	public void Chk(Xoctg_catpage_url actl) {
		Tfds.Eq_ary_str(String_.Ary(grp_idxs), String_.Ary(actl.Grp_idxs()));
		Tfds.Eq_ary(grp_fwds, actl.Grp_fwds());
	}
	public Xoctg_catpage_url_chkr Clear() {
		int len = Xoa_ctg_mgr.Tid__max;
		for (int i = 0; i < len; i++) {
			grp_idxs[i] = null;
			grp_fwds[i] = Bool_.__byte;
		}
		return this;
	}
}
