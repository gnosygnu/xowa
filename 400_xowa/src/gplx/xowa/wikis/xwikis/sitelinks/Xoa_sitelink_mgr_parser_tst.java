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
package gplx.xowa.wikis.xwikis.sitelinks;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import org.junit.*;
public class Xoa_sitelink_mgr_parser_tst {
	private final Xoa_sitelink_mgr_parser_fxt fxt = new Xoa_sitelink_mgr_parser_fxt();
	@Before public void init() {fxt.Clear();}
	@Test public void Basic() {
		String raw = StringUtl.ConcatLinesNlSkipLast
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
	@Test public void Move() {
		String raw = StringUtl.ConcatLinesNlSkipLast
		( "0|Tier 0"
		, "1|de|German"
		, "0|Tier 1"
		, "1|ar|Arabic"
		);
		fxt.Exec_parse(raw);
		raw = StringUtl.ConcatLinesNlSkipLast
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
	private final Xoa_sitelink_mgr mgr = new Xoa_sitelink_mgr();
	private final Xoa_sitelink_mgr_parser parser;
	private final BryWtr tmp_bfr = BryWtr.New();
	public void Clear() {mgr.Grp_mgr().Clear();}
	public Xoa_sitelink_mgr_parser_fxt() {
		this.parser = new Xoa_sitelink_mgr_parser(mgr);
	}
	public void Exec_parse(String raw) {
		byte[] src = BryUtl.NewU8(raw);
		parser.Load_by_bry(src);
	}
	public void Test_parse(String expd) {
		mgr.Grp_mgr().To_bfr(tmp_bfr);
		GfoTstr.EqLines(expd, tmp_bfr.ToStrAndClear());
	}
}
