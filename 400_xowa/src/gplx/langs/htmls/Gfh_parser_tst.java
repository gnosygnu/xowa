/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.htmls; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class Gfh_parser_tst {		
	@Before public void init() {fxt.Clear();} private Gfh_parser_fxt fxt = new Gfh_parser_fxt();
	@Test   public void One() 				{fxt.Test_parse_find_all("<a id='id0'></a>", "id0");}
	@Test   public void Many() 				{fxt.Test_parse_find_all("<a id='id0'></a><a id='id1'></a><a id='id2'></a>", "id0", "id1", "id2");}
	@Test   public void Inline() 			{fxt.Test_parse_find_all("<a id='id0'/>", "id0");}
	@Test   public void Mix()				{fxt.Test_parse_find_all("012<a id='id0'></a>id=id2<a id='id1'/>345<a id='id2'></a>abc", "id0", "id1", "id2");}
	@Test   public void Quote_double() 		{fxt.Test_parse_find_all("<a id='id''0'/>", "id'0");}
	@Test   public void Quote_escape() 		{fxt.Test_parse_find_all("<a id='id\\'0'/>", "id'0");}
}
class Gfh_parser_fxt {
	public void Clear() {
		if (parser == null) {
			parser = new Gfh_parser();			
		}
	}	private Gfh_parser parser;
	public Gfh_parser_fxt Test_parse_find_all(String raw_str, String... expd) {return Test_parse_find(raw_str, Gfh_parser.Wildcard_str, Gfh_parser.Wildcard_str, expd);}
	public Gfh_parser_fxt Test_parse_find(String raw_str, String find_key, String find_val, String... expd) {
		byte[] raw = Bry_.new_a7(raw_str);
		Gfh_nde[] actl_ndes = parser.Parse_as_ary(raw, 0, raw.length, Bry_.new_a7(find_key), Bry_.new_a7(find_val));
		String[] actl = Xto_ids(raw, actl_ndes);
		Tfds.Eq_ary_str(expd, actl);
		return this;
	}
	private String[] Xto_ids(byte[] src, Gfh_nde[] ary) {
		int len = ary.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; i++) {
			Gfh_nde itm = ary[i];
			String atr_val = itm.Atrs_val_by_key_str("id");
			rv[i] = atr_val;
		}
		return rv;
	}
}
