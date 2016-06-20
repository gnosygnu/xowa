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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.xowa.langs.vnts.*;
public class Vnt_flag_parser_tst {
	private final    Vnt_flag_parser_fxt fxt = new Vnt_flag_parser_fxt();
	@Test   public void Basic()					{fxt.Test_parse("D"						, "D");}
	@Test   public void Multiple()				{fxt.Test_parse("+;S;E"					, "+;S;E");}
	@Test   public void Ws()					{fxt.Test_parse(" + ; S ; E "			, "+;S;E");}
	@Test   public void None()					{fxt.Test_parse(""						, "S");}
	@Test   public void Wrong()					{fxt.Test_parse("XYZ"					, "S");}
	@Test   public void Raw__limit()			{fxt.Test_parse("R;S"					, "R");}
	@Test   public void Name__limit()			{fxt.Test_parse("N;S"					, "N");}
	@Test   public void Del_limit()				{fxt.Test_parse("-;S"					, "-");}
	@Test   public void Title__also_macro_y()	{fxt.Test_parse("T"						, "H;T");}
	@Test   public void Title__also_macro_n()	{fxt.Test_parse("T;S"					, "S;T");}
	@Test   public void Hide__remove_all()		{fxt.Test_parse("H;S"					, "+;H");}
	@Test   public void Hide__keep_descrip()	{fxt.Test_parse("H;S;D"					, "+;H;D");}
	@Test   public void Hide__keep_title()		{fxt.Test_parse("H;S;T"					, "+;H;T");}
	@Test   public void Aout__also_show_all()	{fxt.Test_parse("A"						, "+;A;S");}
	@Test   public void Descrip__remove_show()	{fxt.Test_parse("D;S"					, "D");}
	@Test   public void Aout_w_descrip()		{fxt.Test_parse("A;D;S"					, "+;A;D");}
	@Test   public void Lang__one()				{fxt.Test_parse("zh-hans"				, "S;zh-hans");}
	@Test   public void Lang__many()			{fxt.Test_parse("zh-cn;zh-hk"			, "S;zh-cn;zh-hk");}
	@Test   public void Lang__many__ws()		{fxt.Test_parse(" zh-cn ; zh-hk "		, "S;zh-cn;zh-hk");}
	@Test   public void Lang__many__dupe()		{fxt.Test_parse("zh-cn;zh-cn"			, "S;zh-cn");}
	@Test   public void Lang__zap__codes()		{fxt.Test_parse("+;S;zh-hans;"			, "zh-hans");}
}
class Vnt_flag_parser_fxt {
	private final    Vnt_flag_parser parser = new Vnt_flag_parser();
	private final    Vnt_flag_code_mgr codes = new Vnt_flag_code_mgr(); private final    Vnt_flag_lang_mgr langs = new Vnt_flag_lang_mgr();
	private final    Xol_vnt_regy vnt_regy = Xol_vnt_regy_fxt.new_chinese();
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public void Test_parse(String raw, String expd) {
		byte[] src = Bry_.new_u8(raw);			
		parser.Parse(codes, langs, vnt_regy, src, 0, src.length);
		codes.To_bfr__dbg(bfr);
		langs.To_bfr__dbg(bfr);
		Tfds.Eq_str(expd, bfr.To_str_and_clear());
	}
}
