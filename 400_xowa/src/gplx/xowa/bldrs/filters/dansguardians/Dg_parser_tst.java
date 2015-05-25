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
package gplx.xowa.bldrs.filters.dansguardians; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.filters.*;
import org.junit.*;
public class Dg_parser_tst {
	@Before public void init() {fxt.Init();} private Dg_parser_fxt fxt = new Dg_parser_fxt();
	@Test   public void One()				{fxt.Test_parse_line("<a><123>", fxt.Make_line(123, "a"));}
	@Test   public void Many()				{fxt.Test_parse_line("<a>,<b>,<c><-123>", fxt.Make_line(-123, "a", "b", "c"));}
	@Test   public void Score_0()			{fxt.Test_parse_line("<a><0>", fxt.Make_line(Dg_rule.Score_banned, "a"));}
	@Test   public void Noscore()			{fxt.Test_parse_line("<a>", fxt.Make_line(Dg_rule.Score_banned, "a"));}
	@Test   public void Noscore_2()			{fxt.Test_parse_line("<a>,<b>", fxt.Make_line(Dg_rule.Score_banned, "a", "b"));}
	@Test   public void Comment()			{fxt.Test_parse_line("# comment", Dg_rule.Itm_comment);}
	@Test   public void Blank()				{fxt.Test_parse_line("", Dg_rule.Itm_blank);}
	@Test   public void Invalid_line_bgn()	{fxt.Test_parse_line(" <a><1>", Dg_rule.Itm_invalid);}
	@Test   public void Dangling_word()		{fxt.Test_parse_line("<a", Dg_rule.Itm_invalid);}
	@Test   public void Dangling_score()	{fxt.Test_parse_line("<a><12", fxt.Make_line(Dg_rule.Score_banned, "a"));}
	@Test   public void Invalid_dlm()		{fxt.Test_parse_line("<a> <1>", fxt.Make_line(Dg_rule.Score_banned, "a"));}
	@Test   public void Invalid_dlm_2()		{fxt.Test_parse_line("<a>,<b><c><2>", fxt.Make_line(Dg_rule.Score_banned, "a", "b"));}
	@Test   public void Invalid_score()		{fxt.Test_parse_line("<a><1a>", fxt.Make_line(Dg_rule.Score_banned, "a"));}
//		@Test   public void Parse_dir() {
//			Dg_parser parser = new Dg_parser();
//			Gfo_usr_dlg_.I = Xoa_app_.usr_dlg_console_();
//			parser.Parse_dir(Io_url_.new_dir_("C:\\xowa\\bin\\any\\xowa\\bldr\\filters\simple.wikipedia.org\\Dansguardian\\\\"));
//		}
}
class Dg_parser_fxt {
	private final Dg_parser parser = new Dg_parser(); private final Bry_bfr bfr = Bry_bfr.reset_(32);
	private final Bry_bfr tmp_bfr = Bry_bfr.reset_(16);
	public void Init() {}
	public Dg_rule Make_line(int score, String... words) {return new Dg_rule(-1, -1, -1, Dg_rule.Tid_rule, null, score, Dg_word.Ary_new_by_str_ary(words));}
	public void Test_parse_line(String str, Dg_rule expd) {
		byte[] src = Bry_.new_u8(str);
		Dg_rule actl = parser.Parse_line("rel_path", 0, 0, src, 0, src.length);
		Tfds.Eq_str_lines(Xto_str(bfr, expd), Xto_str(bfr, actl));
	}
	private String Xto_str(Bry_bfr bfr, Dg_rule line) {
		bfr	.Add_str("score=").Add_int_variable(line.Score()).Add_byte_nl()
			.Add_str("words=").Add_str(String_.Concat_with_str(";", Dg_word.Ary_concat(line.Words(), tmp_bfr, Byte_ascii.Tick))).Add_byte_nl()
			;
		return bfr.Xto_str_and_clear();
	}
}
