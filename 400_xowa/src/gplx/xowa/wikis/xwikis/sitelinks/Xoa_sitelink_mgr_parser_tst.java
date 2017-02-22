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
package gplx.xowa.wikis.xwikis.sitelinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*;
import org.junit.*;
public class Xoa_sitelink_mgr_parser_tst {
	private final    Xoa_sitelink_mgr_parser_fxt fxt = new Xoa_sitelink_mgr_parser_fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Basic() {
		String raw = String_.Concat_lines_nl_skip_last
		( "0|Tier 0"
		, "1|de|German"
		, "1|en|English"
		, "0|Tier 1"
		, "1|ar|Arabic"
		, "1|ca|Catalan"
		);
		fxt.Exec_parse(raw);
		fxt.Test_parse(raw);
	}
	@Test   public void Move() {
		String raw = String_.Concat_lines_nl_skip_last
		( "0|Tier 0"
		, "1|de|German"
		, "0|Tier 1"
		, "1|ar|Arabic"
		);
		fxt.Exec_parse(raw);
		raw = String_.Concat_lines_nl_skip_last
		( "0|Tier 0"
		, "1|ar|Arabic"
		, "0|Tier 1"
		, "1|de|German"
		);
		fxt.Exec_parse(raw);
		fxt.Test_parse(raw);
	}
}
class Xoa_sitelink_mgr_parser_fxt {
	private final    Xoa_sitelink_mgr mgr = new Xoa_sitelink_mgr();
	private final    Xoa_sitelink_mgr_parser parser;
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public void Clear() {mgr.Grp_mgr().Clear();}
	public Xoa_sitelink_mgr_parser_fxt() {
		this.parser = new Xoa_sitelink_mgr_parser(mgr);
	}
	public void Exec_parse(String raw) {
		byte[] src = Bry_.new_u8(raw);
		parser.Load_by_bry(src);
	}
	public void Test_parse(String expd) {
		mgr.Grp_mgr().To_bfr(tmp_bfr);
		Tfds.Eq_str_lines(expd, tmp_bfr.To_str_and_clear());
	}
}
