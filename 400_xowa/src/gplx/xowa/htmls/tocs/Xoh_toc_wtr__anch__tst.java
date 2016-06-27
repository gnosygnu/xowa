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
public class Xoh_toc_wtr__anch__tst {
	@Before public void init() {fxt.Clear();} private final    Xoh_toc_wtr_fxt fxt = new Xoh_toc_wtr_fxt();
	@Test   public void Caption() {
		fxt.Test__convert("<a href=\"/wiki/A\">b</a>", "b");
	}
	@Test   public void Ref() {	// PURPOSE: ref contents should not print in TOC; DATE:2013-07-23
		fxt.Test__convert("a<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>", "a");
	}
	@Test   public void Category() { // PURPOSE: literal Category should show in in TOC; EX: de.w:1234; DATE:2014-01-21
		fxt.Test__convert("A<a href=\"/wiki/Category:B\">Category:B</a>", "ACategory:B");
	}
	@Test   public void File() { // PURPOSE: file should not show in in TOC; EX: tr.w:D�nya_Miraslari; DATE:2014-06-06
		fxt.Test__convert
		( "<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a> b"
		, "b");
	}
}
