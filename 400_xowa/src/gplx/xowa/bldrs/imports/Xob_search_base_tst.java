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
package gplx.xowa.bldrs.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*;
public class Xob_search_base_tst {
	@Test  public void Split() {
		tst_Split("a", "a");
		tst_Split("a b", "a", "b");
		tst_Split("a b c", "a", "b", "c");
		tst_Split("a-b.c", "a", "b", "c");
		tst_Split("a A", "a");
		tst_Split("a_b", "a", "b");
		tst_Split("a (b)", "a", "b");
	}
	@Test  public void Title_word() {
		fxt.doc_ary_
		(	fxt.doc_wo_date_(2, "A b",  "text")
		,	fxt.doc_wo_date_(3, "B a",  "text")
		)
		.Fil_expd(fxt.fil_ns_sttl(Xow_ns_.Id_main, 0)
		,	"!!!!;|!!!!;|"
		,	"a|!!!!#;!!!!%|!!!!$;!!!!%"
		,	"b|!!!!#;!!!!%|!!!!$;!!!!%"
		,	""
		)
		.Fil_expd(fxt.fil_reg(Xow_ns_.Id_main, Xow_dir_info_.Tid_search_ttl)
		,	"0|a|b|2"
		,	""
		)
		.Run(new Xob_search_txt(fxt.Bldr(), this.fxt.Wiki()))
		;
	}	private Xob_fxt fxt = new Xob_fxt().Ctor_mem();
	private void tst_Split(String raw, String... expd) {
		OrderedHash list = OrderedHash_.new_(); Bry_bfr bfr = Bry_bfr.new_();
		byte[][] actl_bry = Xob_search_base.Split(fxt.App().Lang_mgr().Lang_en(), list, bfr, Bry_.new_utf8_(raw));
		String[] actl = new String[actl_bry.length];
		for (int i = 0; i < actl_bry.length; i++)
			actl[i] = String_.new_utf8_(actl_bry[i]);
		Tfds.Eq_ary(expd, actl);
	}
}
