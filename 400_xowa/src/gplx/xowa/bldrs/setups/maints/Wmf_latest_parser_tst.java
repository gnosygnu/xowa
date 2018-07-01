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
package gplx.xowa.bldrs.setups.maints; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.setups.*;
import org.junit.*; import gplx.core.ios.*;
public class Wmf_latest_parser_tst {
	@Before public void init() {fxt.Clear();} private Wmf_latest_parser_fxt fxt = new Wmf_latest_parser_fxt();
	@Test  public void Parse() {
		fxt.Test_parse
		( "\n<a href=\"enwiki-latest-pages-articles.xml.bz2\">enwiki-latest-pages-articles.xml.bz2</a>               15-Jan-2015 05:43         11575640561\r\n"
		, fxt.itm("enwiki-latest-pages-articles.xml.bz2", "2015-01-15 05:43", "10.781 GB")
		);
	}
//		@Test   public void Smoke() {
//			Wmf_latest_parser parser = new Wmf_latest_parser();
//			parser.Parse(Io_mgr.Instance.LoadFilBry("C:\\wmf_latest.html"));
//			Tfds.Dbg(String_.Concat_lines_nl(Wmf_latest_parser_fxt.Xto_str_ary(parser.To_ary())));
//		}
}
class Wmf_latest_parser_fxt {
	public void Clear() {}
	private Wmf_latest_parser parser = new Wmf_latest_parser();
	public Wmf_latest_itm itm(String name, String date, String size) {return new Wmf_latest_itm(Bry_.new_a7(name), DateAdp_.parse_iso8561(date), Io_size_.parse_or(size, 0));}
	public void Test_parse(String raw, Wmf_latest_itm... expd) {
		parser.Parse(Bry_.new_a7(raw));
		Wmf_latest_itm[] actl = parser.To_ary();
		Tfds.Eq_str_lines(String_.Concat_lines_nl(Xto_str_ary(expd)), String_.Concat_lines_nl(Xto_str_ary(actl)));
	}
	public static String[] Xto_str_ary(Wmf_latest_itm[] ary) {
		int len = ary.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; i++)
			rv[i] = Xto_str(ary[i]); 
		return rv;
	}
	public static String Xto_str(Wmf_latest_itm itm) {
		return String_.Concat_with_str("\n", String_.new_a7(itm.Name()), itm.Date().XtoStr_fmt_iso_8561(), Io_size_.To_str(itm.Size()));			
	}
}
