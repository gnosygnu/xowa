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
package gplx.xowa.xtns.math.texvcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*;
import org.junit.*; import gplx.xowa.xtns.math.texvcs.tkns.*; import gplx.xowa.xtns.math.texvcs.funcs.*;
public class Texvc_checker_tst {
	private final Texvc_checker_fxt fxt = new Texvc_checker_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Mathrm_tkns() {
		fxt.Test_check("\\mathrm\\frac{a}{b}"
		,	fxt.Mkr().func(0, 7, Texvc_func_itm_.Id__mathrm
			,	fxt.Mkr().func(7, 12, Texvc_func_itm_.Id__frac
				,	fxt.Mkr().curly(12, 15
					,	fxt.Mkr().text(13, 14)
					)
				,	fxt.Mkr().curly(15, 18
					,	fxt.Mkr().text(16, 17)
					)
				)
			)
		);
	}
	@Test   public void Mathrm() {
		fxt.Test_check("\\mathrm\\frac{a}{b}"	, "\\mathrm{\\frac{a}{b}}");
		fxt.Test_check("\\mathrm{\\frac{a}{b}}"	, "\\mathrm{\\frac{a}{b}}");
		fxt.Test_check("\\frac a b"	, "\\frac a b");
	}
}
class Texvc_checker_fxt extends Texvc_parser_fxt {		private final Texvc_checker checker = new Texvc_checker();
	public void Test_check(String src_str, Texvc_tkn... expd_tkns) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Texvc_root actl_root = this.Exec_parse(src_bry);
		checker.Check(src_bry, actl_root);
		Tfds.Eq_str_lines(Texvc_tkn_.Print_dbg_str(tmp_bfr, expd_tkns), actl_root.Print_dbg_str(tmp_bfr), src_str);
	}
	public void Test_check(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Texvc_root actl_root = this.Exec_parse(src_bry);
		checker.Check(src_bry, actl_root);			
		Tfds.Eq_str_lines(expd, actl_root.Print_tex_str(tmp_bfr));
	}
}
