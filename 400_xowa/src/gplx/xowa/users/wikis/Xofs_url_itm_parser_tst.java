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
package gplx.xowa.users.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import org.junit.*;
public class Xofs_url_itm_parser_tst {		
	@Before public void init() {fxt.Clear();} private Xofs_url_itm_parser_fxt fxt = new Xofs_url_itm_parser_fxt();
	@Test  public void Custom() 				{fxt.Test_parse_custom("/xowa/wiki/en.wikipedia.org/");}
	@Test  public void Lnx() 					{fxt.Init_dir_spr_lnx().Init_name("xowa", "/xowa")		.Test_parse("xowa-fs://~{xowa}/bin/any/", "/xowa/bin/any/");}
	@Test  public void Wnt() 					{fxt.Init_dir_spr_wnt().Init_name("xowa", "C:\\xowa")	.Test_parse("xowa-fs://~{xowa}/bin/any/", "C:\\xowa\\bin\\any\\");}
	@Test  public void Outliers() {
		fxt.Init_name("xowa", "/xowa");
		fxt.Test_parse("xowa-fs://ab"		, "ab");		// no subst
		fxt.Test_parse("xowa-fs://a~b"		, "a~b");		// tilde
		fxt.Test_parse("xowa-fs://a~{~{b"	, "a~{b");		// escape
		fxt.Test_parse("xowa-fs://ab~"		, "ab~");		// eos
		fxt.Test_parse("xowa-fs://ab~{~{"	, "ab~{");		// eos
	}
}
class Xofs_url_itm_parser_fxt {
	private Xofs_url_itm_parser parser;
	private Xofs_url_itm itm = new Xofs_url_itm();
	public void Clear() {
		parser = new Xofs_url_itm_parser();
	}
	public Xofs_url_itm_parser_fxt Init_name(String key, String val) {parser.Names_add(key, val); return this;}
	public Xofs_url_itm_parser_fxt Init_dir_spr_lnx() {parser.Dir_spr_(Op_sys.Lnx.Fsys_dir_spr_byte()); return this;}
	public Xofs_url_itm_parser_fxt Init_dir_spr_wnt() {parser.Dir_spr_(Op_sys.Wnt.Fsys_dir_spr_byte()); return this;}
	public void Test_parse_custom(String raw) {
		parser.Parse(itm, raw);
		Tfds.Eq(Bool_.N, itm.Tid_is_xowa());
		Tfds.Eq(raw, itm.Url());
	}
	public void Test_parse(String raw, String expd) {
		parser.Parse(itm, raw);
		Tfds.Eq(Bool_.Y, itm.Tid_is_xowa());
		Tfds.Eq(expd, itm.Url());
	}
}
