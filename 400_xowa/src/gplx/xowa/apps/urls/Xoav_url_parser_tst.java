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
package gplx.xowa.apps.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*;
public class Xoav_url_parser_tst {		
	@Before public void init() {fxt.Clear();} private final Xoav_url_parser_fxt fxt = new Xoav_url_parser_fxt();
	@Test   public void Page() {
		fxt.Exec_parse_xo_href("http:/wiki/Earth").Test_wiki("en.wikipedia.org").Test_page("Earth");
	}
	@Test   public void Site() {
		fxt.Exec_parse_xo_href("http:/site/en.wikipedia.org/wiki/Earth").Test_wiki("en.wikipedia.org").Test_page("Earth");
	}
}
class Xoav_url_parser_fxt {
	private Xoav_url_parser url_parser = new Xoav_url_parser(); private Xoav_url url = new Xoav_url();
	public void Clear() {
		cur_wiki = Bry_.new_a7("en.wikipedia.org");
		url.Clear();
	}
	public Xoav_url_parser_fxt Init_cur_wiki(String v) {cur_wiki = Bry_.new_u8(v); return this;} private byte[] cur_wiki;
	public Xoav_url_parser_fxt Test_wiki(String v) {Tfds.Eq_bry(Bry_.new_u8(v), url.Wiki_bry()); return this;}
	public Xoav_url_parser_fxt Test_page(String v) {Tfds.Eq_bry(Bry_.new_u8(v), url.Page_bry()); return this;}
	public Xoav_url_parser_fxt Exec_parse_xo_href(String src_str) {
		byte[] src_bry = Bry_.new_u8(src_str);
		url_parser.Parse_xo_href(url, src_bry, cur_wiki);
		return this;
	}
}
