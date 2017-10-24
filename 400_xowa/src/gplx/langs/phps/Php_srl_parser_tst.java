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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class Php_srl_parser_tst {
	Php_srl_parser_fxt fxt = new Php_srl_parser_fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Nil()  				{fxt.Test_parse("N;", fxt.itm_nil_());}
	@Test   public void Bool_y()  			{fxt.Test_parse("b:1;", fxt.itm_bool_y_());}
	@Test   public void Bool_n() 			{fxt.Test_parse("b:0;", fxt.itm_bool_n_());}
	@Test   public void Num_int()			{fxt.Test_parse("i:123;", fxt.itm_int_(123));}
	@Test   public void Num_int_neg()		{fxt.Test_parse("i:-123;", fxt.itm_int_(-123));}
	@Test   public void Num_double()		{fxt.Test_parse("d:1.23;", fxt.itm_double_(1.23d));}
	@Test   public void Num_double_inf_pos(){fxt.Test_parse("d:INF;", fxt.itm_double_(Double_.Inf_pos));}
	@Test   public void Num_double_exp()	{fxt.Test_parse("d:1.2e+2;", fxt.itm_double_(120));}
	@Test   public void Num_double_nan()	{fxt.Test_parse("d:NAN;", fxt.itm_double_(Double_.NaN));}
	@Test   public void Str_len_3()			{fxt.Test_parse("s:3:\"abc\";", fxt.itm_str_("abc"));}
	@Test   public void Str_len_4()			{fxt.Test_parse("s:4:\"abcd\";", fxt.itm_str_("abcd"));}
	@Test   public void Str_len_0()			{fxt.Test_parse("s:0:\"\";", fxt.itm_str_(""));}
	@Test   public void Ary_empty()			{fxt.Test_parse("a:0:{}", fxt.itm_ary_());}
	@Test   public void Ary_flat_one()		{fxt.Test_parse("a:1:{i:1;i:9;}", fxt.itm_ary_().Subs_add(fxt.itm_kvi_(1, fxt.itm_int_(9))));}
	@Test   public void Ary_flat_many()		{
		fxt.Test_parse(String_.Concat
		(	"a:3:{"
		,	"i:1;i:9;"
		,	"i:2;i:8;"
		,	"i:3;i:7;"
		,	"}"), fxt.itm_ary_().Subs_add_many
		(	fxt.itm_kvi_(1, fxt.itm_int_(9))
		,	fxt.itm_kvi_(2, fxt.itm_int_(8))
		,	fxt.itm_kvi_(3, fxt.itm_int_(7))
		));
	}
	@Test  public void Ary_nest_one() {
		fxt.Test_parse(String_.Concat
		(	"a:1:{"
		,		"i:1;"
		,		"a:2:{"
		,			"i:1;i:9;"
		,			"i:2;i:8;"
		,		"}"
		,	"}"
		)
		,	fxt.itm_ary_().Subs_add_many
		(		fxt.itm_kvi_(1, fxt.itm_ary_().Subs_add_many
		(			fxt.itm_kvi_(1, fxt.itm_int_(9))
		,			fxt.itm_kvi_(2, fxt.itm_int_(8))
		))));
	}
	@Test   public void Ary_key_str() {
		fxt.Test_parse(String_.Concat
		(	"a:1:{"
		,	"s:3:\"abc\";"
		,	"i:987;"
		,	"}"), fxt.itm_ary_().Subs_add_many
		(	fxt.itm_kvs_("abc", fxt.itm_int_(987))
		));
	}
	@Test  public void Func() {
		fxt.Test_parse("O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:123;}", fxt.itm_func_(123));
	}
	@Test   public void Smoke() {
//			fxt.Test_parse("a:2:{s:6:\"values\";a:1:{i:1;a:9:{s:21:\"makeProt"+"ectedEnvFuncs\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:2;}s:3:\"log\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:3;}s:14:\"clearLogBuffer\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:4;}s:5:\"setup\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:5;}s:5:\"clone\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:6;}s:15:\"getCurrentFrame\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:7;}s:13:\"executeModule\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:8;}s:15:\"executeFunction\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:9;}s:12:\"getLogBuffer\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:10;}}}s:2:\"op\";s:6:\"return\";}");
	}
}
class Php_srl_parser_fxt {
	public void Clear() {
		parser = new Php_srl_parser();
		factory = parser.Factory();				
	}	Php_srl_parser parser; Php_srl_factory factory; Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
	public Php_srl_itm itm_nil_()						{return factory.Nil();}
	public Php_srl_itm itm_bool_n_()					{return factory.Bool_n();}
	public Php_srl_itm itm_bool_y_()					{return factory.Bool_y();}
	public Php_srl_itm itm_int_(int v)					{return factory.Int(-1, -1, v);}
	public Php_srl_itm itm_double_(double v)			{return factory.Double(-1, -1, v);}
	public Php_srl_itm itm_str_(String v)				{return factory.Str(-1, -1, v);}
	public Php_srl_itm itm_func_(int v)					{return factory.Func(-1, -1, v);}
	public Php_srl_itm_ary itm_ary_()					{return factory.Ary(-1, -1);}
	public Php_srl_itm_kv itm_kvi_(int k, Php_srl_itm v){return factory.Kv().Key_(itm_int_(k)).Val_(v);}
	public Php_srl_itm_kv itm_kvs_(String k, Php_srl_itm v){return factory.Kv().Key_(itm_str_(k)).Val_(v);}
	public void Test_parse(String raw_str, Php_srl_itm... expd_ary) {
		byte[] raw = Bry_.new_u8(raw_str);
		Php_srl_itm_ary root = parser.Parse(raw);
		Php_srl_itm root_sub = root.Subs_get_at(0).Val();
		root_sub.Xto_bfr(tmp_bfr, 0);
		String actl = tmp_bfr.To_str_and_clear();
		String expd = Xto_str(expd_ary, 0, expd_ary.length);
		Tfds.Eq_str_lines(expd, actl, actl);
	}
	String Xto_str(Php_srl_itm[] ary, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Php_srl_itm itm = ary[i];
			itm.Xto_bfr(tmp_bfr, 0);
		}
		return tmp_bfr.To_str_and_clear();
	}
}
