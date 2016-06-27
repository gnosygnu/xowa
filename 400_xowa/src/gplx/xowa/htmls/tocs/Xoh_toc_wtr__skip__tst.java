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
public class Xoh_toc_wtr__skip__tst {
	@Before public void init() {fxt.Clear();} private final    Xoh_toc_wtr_fxt fxt = new Xoh_toc_wtr_fxt();
	@Test   public void Comment() {
		fxt.Test__convert("<!--a-->", "");
	}
	@Test   public void Comment__many() {
		fxt.Test__convert("1<!--2-->3<!--4-->5", "135");
	}
	@Test   public void Comment__dangling() {
		fxt.Test__convert("1<!--2-->3<!--4->5", "13<!--4->5");
	}
	@Test   public void Br() {
		fxt.Test__convert("a<br/>b", "ab");
	}
	@Test   public void H2() {
		fxt.Test__convert("a<h2>b</h2>c", "ac");
	}
	@Test   public void Li() {
		fxt.Test__convert("a<ul><li>b</li></ul>c", "ac");
	}
	@Test   public void Table() {
		fxt.Test__convert("a<table><tr><td>b</td></tr></table>c", "ac");
	}
	@Test   public void Xnde__small() {
		fxt.Test__convert("<small>a</small>", "a");
	}
	@Test   public void Xnde__sup() {
		fxt.Test__convert("<sup>a</sup>", "a");
	}
	@Test   public void Translate() {	// PURPOSE: <translate> is an xtn and parses its innerText separately; meanwhile, toc_mgr defaults to using the innerText to build toc; EX:Wikidata:Introduction; DATE:2013-07-16
		fxt.Test__convert("<translate><!--b-->ac</translate>", "ac");
	}
}
