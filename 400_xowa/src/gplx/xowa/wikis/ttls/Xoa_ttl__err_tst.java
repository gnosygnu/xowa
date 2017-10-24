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
