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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import org.junit.*;
public class Xof_file_wkr__tst {		
	private final Xof_file_wkr___fxt fxt = new Xof_file_wkr___fxt();
	@Test 	public void Ttl_standardize() {
		fxt.Test__ttl_standardize("Abc.png"		, "Abc.png");		// basic
		fxt.Test__ttl_standardize("A b.png"		, "A_b.png");		// spaces -> unders
		fxt.Test__ttl_standardize("A b c.png"	, "A_b_c.png");		// spaces -> unders; multiple
		fxt.Test__ttl_standardize("abc.png"		, "Abc.png");		// ucase 1st
	}
}
class Xof_file_wkr___fxt {
	public void Test__ttl_standardize(String src_str, String expd) {
		Tfds.Eq_bry(Bry_.new_u8(expd), Xof_file_wkr_.Ttl_standardize(Bry_.new_u8(src_str)));
	}
}
