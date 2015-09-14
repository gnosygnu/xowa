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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.miscs.*;
public class Xop_vnt_lxr_tst {
	private Xop_vnt_lxr_fxt fxt = new Xop_vnt_lxr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Flag_unknown()					{fxt.Test_parse("-{X|b}-"								, fxt.vnt_().Flags_unknown_().Rule_("b"));}
	@Test  public void Flag_raw_basic()					{fxt.Test_parse("-{A|b}-"								, fxt.vnt_().Flags_codes_("A").Rule_("b"));}
	@Test  public void Flag_add_ws()					{fxt.Test_parse("-{ A |b}-"								, fxt.vnt_().Flags_codes_("A").Rule_("b"));}
	@Test  public void Flag_add_unknown()				{fxt.Test_parse("-{ A x |b}-"							, fxt.vnt_().Flags_unknown_().Rule_("b"));}
	@Test  public void Flag_langs_basic()				{fxt.Test_parse("-{zh-hans;zh-hant|b}-"					, fxt.vnt_().Flags_langs_("zh-hans", "zh-hant").Rule_("b"));}
	@Test  public void Flag_langs_semic()				{fxt.Test_parse("-{zh-hans;zh-hant;|b}-"				, fxt.vnt_().Flags_langs_("zh-hans", "zh-hant").Rule_("b"));}
	@Test  public void Flag_langs_ws()					{fxt.Test_parse("-{ zh-hans ; zh-hant ; |b}-"			, fxt.vnt_().Flags_langs_("zh-hans", "zh-hant").Rule_("b"));}
	@Test  public void Flag_unknown_1st()				{fxt.Test_parse("-{ zh-hans x ; zh-hant ; |b}-"			, fxt.vnt_().Flags_unknown_().Rule_("b"));}
	@Test  public void Flag_unknown_nth()				{fxt.Test_parse("-{ zh-hans ; zh-hant x; |b}-"			, fxt.vnt_().Flags_unknown_().Rule_("b"));}
	@Test  public void Flag_unknown_all()				{fxt.Test_parse("-{ zh-hans x ; zh-hant x;|b}-"			, fxt.vnt_().Flags_unknown_().Rule_("b"));}
	@Test  public void Flag_multiple()					{fxt.Test_parse("-{A|D|E|b}-"							, fxt.vnt_().Flags_codes_("A", "D", "E").Rule_("b"));}
	@Test  public void Rule_add_one()					{fxt.Test_parse("-{A|zh-hans:bcd}-"						, fxt.vnt_().Flags_codes_("A").Rule_("zh-hans", "bcd"));}
	@Test  public void Rule_add_one_semic()				{fxt.Test_parse("-{A|zh-hans:bcd;}-"					, fxt.vnt_().Flags_codes_("A").Rule_("zh-hans", "bcd"));}
	@Test  public void Rule_add_one_semic_empty()		{fxt.Test_parse("-{A|zh-hans:bcd;;}-"					, fxt.vnt_().Flags_codes_("A").Rule_("zh-hans", "bcd"));}
	@Test  public void Rule_add_one_ws()				{fxt.Test_parse("-{A|zh-hans : b c ;}-"					, fxt.vnt_().Flags_codes_("A").Rule_("zh-hans", "b c"));}
	@Test  public void Rule_add_many()					{fxt.Test_parse("-{A|zh-hans:b;zh-hant:c}-"				, fxt.vnt_().Flags_codes_("A").Rule_("zh-hans", "b").Rule_("zh-hant", "c"));}
	@Test  public void Macro_one()						{fxt.Test_parse("-{H|A1=>zh-hans:b;zh-hant:c}-"			, fxt.vnt_().Flags_codes_("H").Rule_("A1", "zh-hans", "b").Rule_("A1", "zh-hant", "c"));}
	@Test  public void Bidi()							{fxt.Test_parse("-{zh-hans:b;zh-hant:c}-"				, fxt.vnt_().Flags_none_().Rule_("zh-hans", "b").Rule_("zh-hant", "c"));}
	@Test  public void None()							{fxt.Test_parse("-{a}-"									, fxt.vnt_().Flags_none_().Rule_("a"));}
	@Test  public void Macro_mult() {
		fxt.Test_parse("-{H|A1=>zh-hans:b;zh-hant:c;A2=>zh-hans:d;zh-hant:e}-"
		, fxt.vnt_().Flags_codes_("H")
		.Rule_("A1", "zh-hans", "b").Rule_("A1", "zh-hant", "c")
		.Rule_("A2", "zh-hans", "d").Rule_("A2", "zh-hant", "e")
		);
	}

//		@Test  public void Disabled() {
//			Xop_fxt fxt = new Xop_fxt();
//			fxt.Wiki().Vnt_mgr().Set(null, null);
//			fxt.Test_parse_page_all_str("a-{b}-c", "a-{b}-c");
//		}
//		@Test  public void Enabled() {
//			Xoae_app app = Xoa_app_fxt.app_();
//			Xol_lang lang = new Xol_lang(app, Bry_.new_a7("zh"));
//			Xowe_wiki wiki = Xoa_app_fxt.wiki_(app, "zh.wikipedia.org", lang);
//			Xop_fxt fxt = new Xop_fxt(app, wiki);
//			fxt.Test_parse_page_all_str("a-{b}-c", "ac");
//			fxt.Wiki().Vnt_mgr().Set(null, null);	// set it back to null for other tests
//		}
}
class Xop_vnt_tkn_mok {
	private List_adp rules_list = List_adp_.new_();
	private List_adp flags_list = List_adp_.new_();
	public Xop_vnt_flag[] Flags() {
		if (flags == null) flags = (Xop_vnt_flag[])flags_list.To_ary(Xop_vnt_flag.class);
		return flags;
	}	private Xop_vnt_flag[] flags;
	public Xop_vnt_tkn_mok Flags_none_()					{flags_list.Clear(); return this;}
	public Xop_vnt_tkn_mok Flags_unknown_(String... v) {flags_list.Add(Xop_vnt_flag_.Flag_unknown); return this;}
	public Xop_vnt_tkn_mok Flags_langs_(String... v) {flags_list.Add(Xop_vnt_flag_.new_langs_(Bry_.Ary(v))); return this;}
	public Xop_vnt_tkn_mok Flags_codes_(String... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			byte[] bry = Bry_.new_a7(ary[i]);
			Xop_vnt_flag flag = (Xop_vnt_flag)Xop_vnt_flag_.Trie.Match_bgn(bry, 0, bry.length);
			flags_list.Add(flag);
		}
		return this;
	}
	public Xop_vnt_rule[] Rules() {
		if (rules == null) rules = (Xop_vnt_rule[])rules_list.To_ary(Xop_vnt_rule.class);
		return rules;
	}	private Xop_vnt_rule[] rules;
	public Xop_vnt_tkn_mok Rule_(String rule)											{return Rule_(Xop_vnt_rule.Null_lang, rule);}
	public Xop_vnt_tkn_mok Rule_(byte[] lang, String rule)								{return Rule_(Xop_vnt_rule.Null_macro, lang, new Xop_bry_tkn(-1, -1, Bry_.new_u8(rule)));}
	public Xop_vnt_tkn_mok Rule_(String lang, String rule)								{return Rule_(Xop_vnt_rule.Null_macro, Bry_.new_a7(lang), new Xop_bry_tkn(-1, -1, Bry_.new_u8(rule)));}
	public Xop_vnt_tkn_mok Rule_(String macro, String lang, String rule)				{return Rule_(Bry_.new_a7(macro), Bry_.new_a7(lang), new Xop_bry_tkn(-1, -1, Bry_.new_u8(rule)));}
	public Xop_vnt_tkn_mok Rule_(byte[] macro, byte[] lang, Xop_tkn_itm... tkns)	{rules_list.Add(new Xop_vnt_rule(macro, lang, tkns)); return this;}
}
class Xop_vnt_lxr_fxt {
	private Xop_fxt fxt;
	private Bry_bfr tmp_bfr = Bry_bfr.new_();
	public Xop_vnt_lxr_fxt Clear() {
		Xoae_app app = Xoa_app_fxt.app_();
		Xowe_wiki wiki = Xoa_app_fxt.wiki_(app, "zh.wikipedia.org");
		fxt = new Xop_fxt(app, wiki);
		Xop_vnt_lxr_fxt.Init_vnt_mgr(wiki.Lang().Vnt_mgr(), "zh-hans", "zh-hant");
		Xop_vnt_lxr_.set_(wiki);
		return this;
	}
	public Xop_vnt_tkn_mok vnt_() {return new Xop_vnt_tkn_mok();}
	public static void Init_vnt_mgr(Xol_vnt_mgr vnt_mgr, String... vnts_str) {
		byte[][] vnts_bry = Bry_.Ary(vnts_str);
		int vnts_bry_len = vnts_bry.length;
		for (int i = 0; i < vnts_bry_len; i++)
			vnt_mgr.Get_or_new(vnts_bry[i]);
		vnt_mgr.Convert_ttl_init();
	}
	public Xop_vnt_lxr_fxt Test_parse(String raw, Xop_vnt_tkn_mok expd) {
		byte[] raw_bry = Bry_.new_u8(raw);
		Xop_root_tkn root = fxt.Exec_parse_page_all_as_root(raw_bry);
		Xop_vnt_tkn actl = (Xop_vnt_tkn)root.Subs_get(0);
		Test_vnt_tkn(raw_bry, expd, actl);
		return this;
	}
	private void Test_vnt_tkn(byte[] raw_bry, Xop_vnt_tkn_mok expd, Xop_vnt_tkn actl) {
		Tfds.Eq(Vnt_flag_ary_to_str(tmp_bfr, expd.Flags()), Vnt_flag_ary_to_str(tmp_bfr, actl.Vnt_flags()), "flags");
		Tfds.Eq(Vnt_rule_ary_to_str(tmp_bfr, raw_bry, expd.Rules()), Vnt_rule_ary_to_str(tmp_bfr, raw_bry, actl.Vnt_rules()), "rules");
	}
	private String Vnt_flag_ary_to_str(Bry_bfr bfr, Xop_vnt_flag[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Xop_vnt_flag itm = ary[i];
			byte itm_tid = itm.Tid();
			if (itm_tid == Xop_vnt_flag_.Tid_lang)
				Vnt_flag_lang_to_bfr(bfr, itm);
			else
				bfr.Add_str(Xop_vnt_flag_.Xto_name(itm_tid)).Add_byte(Byte_ascii.Semic);
		}
		return bfr.Xto_str_and_clear();
	}
	private void Vnt_flag_lang_to_bfr(Bry_bfr bfr, Xop_vnt_flag itm) {
		byte[][] ary = itm.Langs();
		int len = ary.length;
		for (int i = 0; i < len; i++)
			bfr.Add(ary[i]).Add_byte(Byte_ascii.Semic);
	}
	private String Vnt_rule_ary_to_str(Bry_bfr bfr, byte[] src, Xop_vnt_rule[] ary) {
		if (ary == null) return "";
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Xop_vnt_rule itm = ary[i];
			if (itm.Rule_macro() != Xop_vnt_rule.Null_macro)	// macro exists
				bfr.Add(itm.Rule_macro()).Add_str("=>");
			if (itm.Rule_lang() != Xop_vnt_rule.Null_lang)		// lang exists
				bfr.Add(itm.Rule_lang()).Add_byte(Byte_ascii.Colon);
			Xop_tkn_itm[] subs = itm.Rule_subs();
			int subs_len = subs.length;
			for (int j = 0; j < subs_len; j++) {
				Xop_tkn_itm sub = subs[j];
				if (sub.Tkn_tid() == Xop_tkn_itm_.Tid_bry)		// tests uses Xop_tkn_bry
					bfr.Add(((Xop_bry_tkn)sub).Val());
				else
					bfr.Add_mid(src, sub.Src_bgn(), sub.Src_end());
			}
			bfr.Add_byte(Byte_ascii.Semic);
		}
		return bfr.Xto_str_and_clear();
	}
}
