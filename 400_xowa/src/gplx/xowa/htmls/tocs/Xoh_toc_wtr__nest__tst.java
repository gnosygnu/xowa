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
public class Xoh_toc_wtr__nest__tst {
	@Before public void init() {fxt.Clear();} private final    Xoh_toc_wtr_fxt fxt = new Xoh_toc_wtr_fxt();
	@Test   public void Xnde__xnde() {	// <sup> removed but not <small>
		fxt.Test__convert("a <sup>b<small>c</small>d</sup> e", "a b<small>c</small>d e");
	}
	@Test   public void Xnde__inline() {	// PURPOSE: do not render inline xndes; EX: Magnetic_resonance_imaging
		fxt.Test__convert("a<span id='b'>b<br/></span>", "ab");
	}
	@Test   public void Xnde__lnki() {	// <small> and <a> removed
		fxt.Test__convert("<small><a href=\"/wiki/A\">b</a></small>", "b");
	}
	@Test   public void Lnki__xnde() {
		fxt.Test__convert("<a href=\"/wiki/A\">b<i>c</i>d</a>", "b<i>c</i>d");
	}
}
