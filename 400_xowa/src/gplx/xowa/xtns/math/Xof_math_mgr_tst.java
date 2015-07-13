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
package gplx.xowa.xtns.math; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Xof_math_mgr_tst {
	@Before public void init() {} Xof_math_subst_regy subst_regy = new Xof_math_subst_regy().Init();
	@Test  public void Basic()						{tst("a\\plusmn b"			, "a\\pm b");}
	@Test  public void Match_fails()				{tst("a\\plusmna b"			, "a\\plusmna b");}
	@Test  public void Part()						{tst("a\\part_t b"			, "a\\partial_t b");}	// PAGE:en.w:Faraday's law of induction
	@Test  public void Partial()					{tst("a\\partial_{x_i}"		, "a\\partial_{x_i}");}	// DEFECT: partial -> partialial
	private void tst(String src, String expd) {Tfds.Eq(expd, String_.new_u8(subst_regy.Subst(Bry_.new_u8(src))));}
}
