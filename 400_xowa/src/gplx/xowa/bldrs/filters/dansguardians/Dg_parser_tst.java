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
package gplx.xowa.bldrs.filters.dansguardians;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import org.junit.*;
public class Dg_parser_tst {
	@Before public void init() {fxt.Init();} private Dg_parser_fxt fxt = new Dg_parser_fxt();
	@Test public void One()				{fxt.Test_parse_line("<a><123>", fxt.Make_line(123, "a"));}
	@Test public void Many()				{fxt.Test_parse_line("<a>,<b>,<c><-123>", fxt.Make_line(-123, "a", "b", "c"));}
	@Test public void Score_0()			{fxt.Test_parse_line("<a><0>", fxt.Make_line(Dg_rule.Score_banned, "a"));}
	@Test public void Noscore()			{fxt.Test_parse_line("<a>", fxt.Make_line(Dg_rule.Score_banned, "a"));}
	@Test public void Noscore_2()			{fxt.Test_parse_line("<a>,<b>", fxt.Make_line(Dg_rule.Score_banned, "a", "b"));}
	@Test public void Comment()			{fxt.Test_parse_line("# comment", Dg_rule.Itm_comment);}
	@Test public void Blank()				{fxt.Test_parse_line("", Dg_rule.Itm_blank);}
	@Test public void Invalid_line_bgn()	{fxt.Test_parse_line(" <a><1>", Dg_rule.Itm_invalid);}
	@Test public void Dangling_word()		{fxt.Test_parse_line("<a", Dg_rule.Itm_invalid);}
	@Test public void Dangling_score()	{fxt.Test_parse_line("<a><12", fxt.Make_line(Dg_rule.Score_banned, "a"));}
	@Test public void Invalid_dlm()		{fxt.Test_parse_line("<a> <1>", fxt.Make_line(Dg_rule.Score_banned, "a"));}
	@Test public void Invalid_dlm_2()		{fxt.Test_parse_line("<a>,<b><c><2>", fxt.Make_line(Dg_rule.Score_banned, "a", "b"));}
	@Test public void Invalid_score()		{fxt.Test_parse_line("<a><1a>", fxt.Make_line(Dg_rule.Score_banned, "a"));}
//		@Test public void Parse_dir() {
//			Dg_parser parser = new Dg_parser();
//			Gfo_usr_dlg_.I = Xoa_app_.New__usr_dlg__console();
//			parser.Parse_dir(Io_url_.new_dir_("C:\\xowa\\bin\\any\\xowa\\bldr\\filters\simple.wikipedia.org\\Dansguardian\\\\"));
//		}
}
class Dg_parser_fxt {
	private final Dg_parser parser = new Dg_parser(); private final BryWtr bfr = BryWtr.NewAndReset(32);
	private final BryWtr tmp_bfr = BryWtr.NewAndReset(16);
	public void Init() {}
	public Dg_rule Make_line(int score, String... words) {return new Dg_rule(-1, -1, -1, Dg_rule.Tid_rule, null, score, Dg_word.Ary_new_by_str_ary(words));}
	public void Test_parse_line(String str, Dg_rule expd) {
		byte[] src = BryUtl.NewU8(str);
		Dg_rule actl = parser.Parse_line("rel_path", 0, 0, src, 0, src.length);
		GfoTstr.EqLines(Xto_str(bfr, expd), Xto_str(bfr, actl));
	}
	private String Xto_str(BryWtr bfr, Dg_rule line) {
		bfr	.AddStrA7("score=").AddIntVariable(line.Score()).AddByteNl()
			.AddStrA7("words=").AddStrU8(StringUtl.ConcatWith(";", Dg_word.Ary_concat(line.Words(), tmp_bfr, AsciiByte.Tick))).AddByteNl()
			;
		return bfr.ToStrAndClear();
	}
}
