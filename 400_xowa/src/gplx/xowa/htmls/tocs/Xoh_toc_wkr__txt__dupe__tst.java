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
package gplx.xowa.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*; import gplx.core.tests.*;
public class Xoh_toc_wkr__txt__dupe__tst {
	@Before public void init() {fxt.Clear();} private final    Xoh_toc_wkr__txt__fxt fxt = new Xoh_toc_wkr__txt__fxt();
	@Test   public void Basic() {
		fxt.Test__anch("a"		, "a");
		fxt.Test__anch("a"		, "a_2");
	}
	@Test   public void Case_insensitive() {
		fxt.Test__anch("a"		, "a");
		fxt.Test__anch("A"		, "A_2");
	}
	@Test   public void Autonumber_exists() {	// PAGE:fr.w:Itanium; EX: Itanium,Itanium_2,Itanium
		fxt.Test__anch("a"		, "a");
		fxt.Test__anch("a_2"	, "a_2");
		fxt.Test__anch("a"		, "a_3");
	}
	@Test   public void Autonumber_exists_2() {
		fxt.Test__anch("a_2"	, "a_2");
		fxt.Test__anch("a"		, "a");
		fxt.Test__anch("a"		, "a_3");
		fxt.Test__anch("a"		, "a_4");
		fxt.Test__anch("a_2"	, "a_2_2");
	}
}
