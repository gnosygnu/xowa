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
package gplx.xowa.addons.wikis.searchs.searchers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import org.junit.*; import gplx.xowa.addons.wikis.searchs.parsers.*; import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
public class Srch_search_phrase_tst {
	private final    Srch_search_phrase_fxt fxt = new Srch_search_phrase_fxt();
	@Test   public void Word() 						{fxt.Test__auto_wildcard("a"		, "a*");}
	@Test   public void Paren_end() 				{fxt.Test__auto_wildcard("(a)"		, "(a*)");}
	@Test   public void Quoted() 					{fxt.Test__auto_wildcard("\"a\""	, "\"a\"");}
	@Test   public void Space() 					{fxt.Test__auto_wildcard(" "		, " ");}
	@Test   public void Not() 						{fxt.Test__auto_wildcard("-"		, "-");}
	@Test   public void And() 						{fxt.Test__auto_wildcard("+"		, "+");}
	@Test   public void Or() 						{fxt.Test__auto_wildcard(","		, ",");}
	@Test   public void Paren_bgn() 				{fxt.Test__auto_wildcard("("		, "(");}
	@Test   public void Star() 						{fxt.Test__auto_wildcard("*"		, "*");}
	@Test   public void Wildcard__exists__y() 		{fxt.Test__auto_wildcard("a*b"		, "a*b");}
	@Test   public void Wildcard__exists__escaped() {fxt.Test__auto_wildcard("a\\*b"	, "a\\*b*");}
	@Test   public void Wildcard__exists__n() 		{fxt.Test__auto_wildcard("a* bc"	, "a* bc*");}
	@Test   public void Escape() 					{fxt.Test__auto_wildcard("\\*"		, "\\**");}
	@Test   public void Escape__incomplete() 		{fxt.Test__auto_wildcard("a\\"		, "a\\");}
	@Test   public void Escape__escaped() 			{fxt.Test__auto_wildcard("a\\\\"	, "a\\\\*");}
}
class Srch_search_phrase_fxt {
	private final    Srch_crt_scanner_syms syms = Srch_crt_scanner_syms.Dflt;
	public Srch_search_phrase_fxt() {}
	public void Test__auto_wildcard(String src_str, String expd) {
		byte[] src_raw = Bry_.new_u8(src_str);
		byte[] actl = Srch_search_phrase.Auto_wildcard(src_raw, syms);
		Tfds.Eq(expd, String_.new_u8(actl));
	}
}
