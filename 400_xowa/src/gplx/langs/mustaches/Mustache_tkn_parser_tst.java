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
package gplx.langs.mustaches; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class Mustache_tkn_parser_tst {
	private final    Mustache_tkn_parser_fxt fxt = new Mustache_tkn_parser_fxt();
	@Test  public void Basic() {
		fxt.Test_parse("a{{b}}c", "ac");
	}
	@Test  public void Comment() {
		fxt.Test_parse("a{{!b}}c", "ac");
	}
}
class Mustache_tkn_parser_fxt {
	private final    Mustache_tkn_parser parser = new Mustache_tkn_parser();
	private final    Mustache_render_ctx ctx = new Mustache_render_ctx();
	private final    Mustache_bfr bfr = Mustache_bfr.New();
	public void Test_parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_a7(src_str);
		Mustache_tkn_itm actl_itm = parser.Parse(src_bry, 0, src_bry.length);
		actl_itm.Render(bfr, ctx);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
}
