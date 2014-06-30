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
package gplx.html; import gplx.*;
import org.junit.*;
public class Html_parser_tst {		
	@Before public void init() {fxt.Clear();} private Xoh_parser_fxt fxt = new Xoh_parser_fxt();
	@Test   public void One() 				{fxt.Test_parse_find_all("<a id='id0'></a>", "id0");}
	@Test   public void Many() 				{fxt.Test_parse_find_all("<a id='id0'></a><a id='id1'></a><a id='id2'></a>", "id0", "id1", "id2");}
	@Test   public void Inline() 			{fxt.Test_parse_find_all("<a id='id0'/>", "id0");}
	@Test   public void Mix()				{fxt.Test_parse_find_all("012<a id='id0'></a>id=id2<a id='id1'/>345<a id='id2'></a>abc", "id0", "id1", "id2");}
	@Test   public void Quote_double() 		{fxt.Test_parse_find_all("<a id='id''0'/>", "id'0");}
	@Test   public void Quote_escape() 		{fxt.Test_parse_find_all("<a id='id\\'0'/>", "id'0");}
}
class Xoh_parser_fxt {
	public void Clear() {
		if (parser == null) {
			parser = new Html_parser();			
		}
	}	private Html_parser parser;
	public Xoh_parser_fxt Test_parse_find_all(String raw_str, String... expd) {return Test_parse_find(raw_str, Html_parser.Wildcard_str, Html_parser.Wildcard_str, expd);}
	public Xoh_parser_fxt Test_parse_find(String raw_str, String find_key, String find_val, String... expd) {
		byte[] raw = Bry_.new_ascii_(raw_str);
		Html_nde[] actl_ndes = parser.Parse_as_ary(raw, 0, raw.length, Bry_.new_ascii_(find_key), Bry_.new_ascii_(find_val));
		String[] actl = Xto_ids(raw, actl_ndes);
		Tfds.Eq_ary_str(expd, actl);
		return this;
	}
	private String[] Xto_ids(byte[] src, Html_nde[] ary) {
		int len = ary.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; i++) {
			Html_nde itm = ary[i];
			String atr_val = itm.Atrs_val_by_key_str("id");
			rv[i] = atr_val;
		}
		return rv;
	}
}
