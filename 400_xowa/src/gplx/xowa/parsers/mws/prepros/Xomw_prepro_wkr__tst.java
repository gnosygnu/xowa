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
package gplx.xowa.parsers.mws.prepros; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.mws.*;
import org.junit.*;
public class Xomw_prepro_wkr__tst {
	private final    Xomw_prepro_wkr__fxt fxt = new Xomw_prepro_wkr__fxt();
	@Test  public void Text() {
		fxt.Test__parse("abc", "<root>abc</root>");
	}
	@Test  public void Brack() {
		fxt.Test__parse("a[[b]]c", "<root>a[[b]]c</root>");
	}
	@Test  public void Template() {
		fxt.Test__parse("a{{b}}c", "<root>a<template lineStart=\"1\"><title>b</title></template>c</root>");
	}
	@Test  public void Tplarg() {
		fxt.Test__parse("a{{{b}}}c", "<root>a<tplarg lineStart=\"1\"><title>b</title></tplarg>c</root>");
	}
	@Test  public void Comment() {
		fxt.Test__parse("a<!--b-->c", "<root>a<comment>&lt;!--b--&gt;</comment>c</root>");
	}
	@Test  public void Comment__nl__ws() {
		fxt.Test__parse("xo\n <!--1--> \n <!--2--> \nz", "<root>xo\n<comment> &lt;!--1--&gt; \n</comment><comment> &lt;!--2--&gt; \n</comment>z</root>");
	}
	@Test  public void Ext__pre() {
		fxt.Test__parse("a<pre id=\"1\">b</pre>c", "<root>a<ext><name>pre</name><attr> id=&quot;1&quot;</attr><inner>b</inner><close>&lt;/pre&gt;</close></ext>c</root>");
	}
/*
TODO:
* for_inclusion; <onlyinclude> in String
* heading.general
* heading.EOS: "==a" (no closing ==)
* ignored tags
*/
}
class Xomw_prepro_wkr__fxt {
	private final    Xomw_prepro_wkr wkr = new Xomw_prepro_wkr();
	private boolean for_inclusion = false;
	public void Init__for_inclusion_y_() {for_inclusion = true;}
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		byte[] actl = wkr.Preprocess_to_xml(src_bry, for_inclusion);
		Tfds.Eq_str_lines(expd, String_.new_u8(actl), src_str);
	}
}
