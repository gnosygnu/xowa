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
package gplx.xowa.mws; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.core.tests.*; import gplx.core.btries.*;
public class Xomw_sanitizer__tst {
	private final    Xomw_sanitizer__fxt fxt = new Xomw_sanitizer__fxt();
	@Test   public void Text()                  {fxt.Test__normalize_char_references("abc"                      , "abc");}
	@Test   public void Dec()                   {fxt.Test__normalize_char_references("&#08;"                    , "&amp;#08;");}
	@Test   public void Dec__invalid()          {fxt.Test__normalize_char_references("&#09;"                    , "&#9;");}
	@Test   public void Hex()                   {fxt.Test__normalize_char_references("&#xFF;"                   , "&#xff;");}
	@Test   public void Entity()                {fxt.Test__normalize_char_references("&alpha;"                  , "&#945;");}
	@Test   public void Entity__lt()            {fxt.Test__normalize_char_references("&lt;"                     , "&lt;");}
	@Test   public void Invalid()               {fxt.Test__normalize_char_references("&(invalid);"              , "&amp;(invalid);");}
	@Test   public void Many() {
		fxt.Test__normalize_char_references
		( "a &#09; b &alpha; c &#xFF; d &(invalid); e"
		, "a &#9; b &#945; c &#xff; d &amp;(invalid); e"
		);
	}
}
class Xomw_sanitizer__fxt {
	private final    Xomw_sanitizer sanitizer = new Xomw_sanitizer();
	private final    Bry_bfr tmp = Bry_bfr_.New();
	public void Test__normalize_char_references(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		sanitizer.Normalize_char_references(tmp, Bool_.Y, src_bry, 0, src_bry.length);
		Gftest.Eq__str(expd, tmp.To_str_and_clear());
	}
}
