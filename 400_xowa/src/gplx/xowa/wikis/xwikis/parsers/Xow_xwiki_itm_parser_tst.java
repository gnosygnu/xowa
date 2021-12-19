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
package gplx.xowa.wikis.xwikis.parsers;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.wikis.xwikis.*;
import org.junit.*; import gplx.xowa.wikis.domains.*;
public class Xow_xwiki_itm_parser_tst {
	private final Xow_xwiki_itm_parser_fxt fxt = new Xow_xwiki_itm_parser_fxt();
	@Test public void Manual() {
		fxt.Exec_parse(StringUtl.ConcatLinesNlSkipLast
		( "0|a|https://a.org/~{0}|A"
		));
		fxt.Test_parse(StringUtl.ConcatLinesNlSkipLast
		( "a|https://a.org/~{0}|A"
		));
	}
	@Test public void Mw_domain() {
		fxt.Exec_parse(StringUtl.ConcatLinesNlSkipLast
		( "1|w|en.wikipedia.org|Wikipedia"
		));
		fxt.Test_parse(StringUtl.ConcatLinesNlSkipLast
		( "w|https://en.wikipedia.org/wiki/~{0}|Wikipedia"
		));
	}
	@Test public void Wm_peer() {
		fxt.Exec_parse(StringUtl.ConcatLinesNlSkipLast
		( "2|wikt|wiktionary|Wiktionary"
		));
		fxt.Test_parse(StringUtl.ConcatLinesNlSkipLast
		( "wikt|https://en.wiktionary.org/wiki/~{0}|Wiktionary"
		));
	}
	@Test public void Wm_lang() {
		fxt.Exec_parse(StringUtl.ConcatLinesNlSkipLast
		( "3|fr|fr|French"
		));
		fxt.Test_parse(StringUtl.ConcatLinesNlSkipLast
		( "fr|https://fr.wikipedia.org/wiki/~{0}|French"
		));
	}
	@Test public void Multiple() {
		fxt.Exec_parse(StringUtl.ConcatLinesNlSkipLast
		( "2|wikt;wiktionary|wiktionary|Wiktionary"
		));
		fxt.Test_parse(StringUtl.ConcatLinesNlSkipLast
		( "wikt|https://en.wiktionary.org/wiki/~{0}|Wiktionary"
		, "wiktionary|https://en.wiktionary.org/wiki/~{0}|Wiktionary"
		));
	}
	@Test public void Default_name() {
		fxt.Exec_parse(StringUtl.ConcatLinesNlSkipLast
		( "2|wikt|wiktionary|"
		));
		fxt.Test_parse(StringUtl.ConcatLinesNlSkipLast
		( "wikt|https://en.wiktionary.org/wiki/~{0}|English Wiktionary"
		));
	}
}
class Xow_xwiki_itm_parser_fxt {
	private final Xow_xwiki_itm_parser parser = new Xow_xwiki_itm_parser();
	private final BryWtr tmp_bfr = BryWtr.New();
	public Xow_xwiki_itm_parser_fxt() {
		parser.Init_by_wiki(Xow_domain_itm_.parse(BryUtl.NewA7("en.wikipedia.org")));
	}
	public void Exec_parse(String raw) {
		byte[] src = BryUtl.NewU8(raw);
		parser.Load_by_bry(src);
	}
	public void Test_parse(String expd) {
		GfoTstr.EqLines(expd, To_str());
	}
	private String To_str() {
		Ordered_hash list = parser.Xwiki_list();
		int len = list.Len();
		for (int i = 0; i < len; ++i) {
			Xow_xwiki_itm itm = (Xow_xwiki_itm)list.GetAt(i);
			tmp_bfr.Add(itm.Key_bry()).AddBytePipe();
			tmp_bfr.Add(itm.Url_fmt()).AddBytePipe();
			tmp_bfr.Add(itm.Domain_name()).AddByteNl();
		}
		list.Clear();
		return tmp_bfr.ToStrAndClear();
	}
}
