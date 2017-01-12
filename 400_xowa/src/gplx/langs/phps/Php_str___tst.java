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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
import org.junit.*; import gplx.core.tests.*;
public class Php_str___tst {
	private final    Php_str___fxt fxt = new Php_str___fxt();
	@Test  public void Strspn_fwd__byte() {
		fxt.Test__strspn_fwd__byte("aaaaab", Byte_ascii.Ltr_a, 0, -1, 5);	// basic
		fxt.Test__strspn_fwd__byte("aaaaab", Byte_ascii.Ltr_a, 1, -1, 4);	// bgn
		fxt.Test__strspn_fwd__byte("aaaaab", Byte_ascii.Ltr_a, 1,  2, 2);	// max
	}
	@Test  public void Strspn_fwd__space_or_tab() {
		fxt.Test__strspn_fwd__space_or_tab("     a", 0, -1, 5);	// basic
		fxt.Test__strspn_fwd__space_or_tab("     a", 1, -1, 4);	// bgn
		fxt.Test__strspn_fwd__space_or_tab("     a", 1,  2, 2);	// max
	}
	@Test  public void Strspn_bwd__byte() {
		fxt.Test__strspn_bwd__byte("aaaaab", Byte_ascii.Ltr_a, 5, -1, 5);	// basic
		fxt.Test__strspn_bwd__byte("aaaaab", Byte_ascii.Ltr_a, 4, -1, 4);	// bgn
		fxt.Test__strspn_bwd__byte("aaaaab", Byte_ascii.Ltr_a, 4,  2, 2);	// max
	}
	@Test  public void Strspn_bwd__space_or_tab() {
		fxt.Test__strspn_bwd__space_or_tab("     a", 5, -1, 5);	// basic
		fxt.Test__strspn_bwd__space_or_tab("     a", 4, -1, 4);	// bgn
		fxt.Test__strspn_bwd__space_or_tab("     a", 4,  2, 2);	// max
	}
}
class Php_str___fxt {
	public void Test__strspn_fwd__byte(String src_str, byte find, int bgn, int max, int expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Gftest.Eq__int(expd, Php_str_.Strspn_fwd__byte(src_bry, find, bgn, max, src_bry.length));
	}
	public void Test__strspn_fwd__space_or_tab(String src_str, int bgn, int max, int expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Gftest.Eq__int(expd, Php_str_.Strspn_fwd__space_or_tab(src_bry, bgn, max, src_bry.length));
	}
	public void Test__strspn_bwd__byte(String src_str, byte find, int bgn, int max, int expd) {
		Gftest.Eq__int(expd, Php_str_.Strspn_bwd__byte(Bry_.new_u8(src_str), find, bgn, max));
	}
	public void Test__strspn_bwd__space_or_tab(String src_str, int bgn, int max, int expd) {
		Gftest.Eq__int(expd, Php_str_.Strspn_bwd__space_or_tab(Bry_.new_u8(src_str), bgn, max));
	}
}
