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
package gplx.xowa.wikis.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*;
public class Xoa_ttl__err_tst {
	@Before public void init() {fxt.Clear();} private final    Xoa_ttl_fxt fxt = new Xoa_ttl_fxt();
	@Test   public void Invalid__angle()			{fxt.Parse("<!--a").Test__null();}
	@Test   public void Invalid__brace()			{fxt.Parse("[[a]]").Test__null();}
	@Test   public void Invalid__curly()			{fxt.Parse("{{a}}").Test__null();}
	@Test   public void Colon_is_last()				{fxt.Parse("Help:").Test__null();}
	@Test   public void Len_max()					{fxt.Parse(String_.Repeat("A", 512)).Test__page_txt(String_.Repeat("A", 512));}
	@Test   public void Len_0() {
		fxt.Parse("").Test__null();
		fxt.Parse(" ").Test__null();
		fxt.Parse("_").Test__null();
		fxt.Parse("_ _").Test__null();
	}
}
