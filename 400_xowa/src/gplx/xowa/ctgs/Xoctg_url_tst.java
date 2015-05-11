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
package gplx.xowa.ctgs; import gplx.*; import gplx.xowa.*;
import org.junit.*;
public class Xoctg_url_tst {
	@Before public void init() {fxt.Clear();} private Xoctg_url_fxt fxt = new Xoctg_url_fxt();
	@Test   public void Basic() {
		fxt.Test_parse("A?subcatfrom=B&filefrom=C&pagefrom=D", fxt.url().Grp_idxs_("B", "C", "D").Grp_fwds_(Bool_.Y_byte, Bool_.Y_byte, Bool_.Y_byte));
		fxt.Test_parse("A?subcatuntil=B&fileuntil=C&pageuntil=D", fxt.url().Grp_idxs_("B", "C", "D").Grp_fwds_(Bool_.N_byte, Bool_.N_byte, Bool_.N_byte));
		fxt.Test_parse("A?from=B", fxt.url().Grp_idxs_("B", "B", "B").Grp_fwds_(Bool_.Y_byte, Bool_.Y_byte, Bool_.Y_byte));
		fxt.Test_parse("A?until=B", fxt.url().Grp_idxs_("B", "B", "B").Grp_fwds_(Bool_.N_byte, Bool_.N_byte, Bool_.N_byte));
	}
}
class Xoctg_url_fxt {
	public Xoctg_url_chkr url() {return expd.Clear();} private Xoctg_url_chkr expd;
	public void Clear() {
		if (parser == null) {
			parser = new Xoa_url_parser();
			page_url = Xoa_url.blank_();
			ctg_url = new Xoctg_url();
			expd = new Xoctg_url_chkr();
		}
	}	private Xoa_url_parser parser; Xoa_url page_url; Xoctg_url ctg_url;
	public void Test_parse(String url_str, Xoctg_url_chkr expd) {
		parser.Parse(page_url, Bry_.new_utf8_(url_str));
		ctg_url.Parse(Gfo_usr_dlg_.Test(), page_url);
		expd.Chk(ctg_url);
		expd.Clear();
	}
}
class Xoctg_url_chkr {
	public Xoctg_url_chkr Grp_idxs_(String subc, String file, String page) {
		grp_idxs[Xoa_ctg_mgr.Tid_subc] = Bry_.new_ascii_(subc);
		grp_idxs[Xoa_ctg_mgr.Tid_file] = Bry_.new_ascii_(file);
		grp_idxs[Xoa_ctg_mgr.Tid_page] = Bry_.new_ascii_(page);
		return this;
	}	byte[][] grp_idxs = new byte[Xoa_ctg_mgr.Tid__max][];
	public Xoctg_url_chkr Grp_fwds_(byte subc, byte file, byte page) {
		grp_fwds[Xoa_ctg_mgr.Tid_subc] = subc;
		grp_fwds[Xoa_ctg_mgr.Tid_file] = file;
		grp_fwds[Xoa_ctg_mgr.Tid_page] = page;
		return this;
	}	byte[] grp_fwds = new byte[Xoa_ctg_mgr.Tid__max];		
	public void Chk(Xoctg_url actl) {
		Tfds.Eq_ary_str(String_.Ary(grp_idxs), String_.Ary(actl.Grp_idxs()));
		Tfds.Eq_ary(grp_fwds, actl.Grp_fwds());
	}
	public Xoctg_url_chkr Clear() {
		int len = Xoa_ctg_mgr.Tid__max;
		for (int i = 0; i < len; i++) {
			grp_idxs[i] = null;
			grp_fwds[i] = Bool_.__byte;
		}
		return this;
	}
}
