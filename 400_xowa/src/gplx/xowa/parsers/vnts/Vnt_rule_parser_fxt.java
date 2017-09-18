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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.langs.vnts.*;
class Vnt_rule_parser_fxt {
	private final    Vnt_rule_parser parser = new Vnt_rule_parser(); private final    Vnt_rule_undi_mgr undis = new Vnt_rule_undi_mgr(); private final    Vnt_rule_bidi_mgr bidis = new Vnt_rule_bidi_mgr();
	private final    Bry_bfr bfr = Bry_bfr_.New_w_size(255);
	public Vnt_rule_parser_fxt() {
		Xol_vnt_regy vnt_regy = new Xol_vnt_regy();
		vnt_regy.Add(Bry_.new_a7("x1"), Bry_.new_a7("lang1"));
		vnt_regy.Add(Bry_.new_a7("x2"), Bry_.new_a7("lang2"));
		vnt_regy.Add(Bry_.new_a7("x3"), Bry_.new_a7("lang3"));
		parser.Init(null, vnt_regy);
	}
	public void Test_parse(String raw, String... expd_ary) {
		byte[] src = Bry_.new_u8(raw);
		parser.Clear(undis, bidis, src);
		parser.Parse(src, 0, src.length);
		parser.To_bry__dbg(bfr);
		Tfds.Eq_str_lines(String_.Concat_lines_nl_skip_last(expd_ary), bfr.To_str_and_clear());
	}
}
