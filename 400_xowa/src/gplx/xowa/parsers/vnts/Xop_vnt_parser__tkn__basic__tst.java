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
import org.junit.*; import gplx.xowa.langs.vnts.*; import gplx.xowa.parsers.miscs.*;
public class Xop_vnt_parser__tkn__basic__tst {
	private final Xop_vnt_lxr_fxt fxt = new Xop_vnt_lxr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Add__flag__basic()				{fxt.Test_parse("-{A|b}-"								, fxt.vnt_().Flags_codes_("A").Rule_("b"));}
	@Test  public void Add__flag__ws()					{fxt.Test_parse("-{ A |b}-"								, fxt.vnt_().Flags_codes_("A").Rule_("b"));}
	@Test  public void Add__rule__semic_n()				{fxt.Test_parse("-{A|zh-hans:bcd}-"						, fxt.vnt_().Flags_codes_("A").Rule_("zh-hans", "bcd"));}
	@Test  public void Add__rule__semic_y()				{fxt.Test_parse("-{A|zh-hans:bcd;}-"					, fxt.vnt_().Flags_codes_("A").Rule_("zh-hans", "bcd"));}
	@Test  public void Add__rule__semic_empty()			{fxt.Test_parse("-{A|zh-hans:bcd;;}-"					, fxt.vnt_().Flags_codes_("A").Rule_("zh-hans", "bcd"));}
	@Test  public void Add__rule__ws()					{fxt.Test_parse("-{A|zh-hans : b c ;}-"					, fxt.vnt_().Flags_codes_("A").Rule_("zh-hans", "b c"));}
	@Test  public void Add__rule__many()				{fxt.Test_parse("-{A|zh-hans:b;zh-hant:c}-"				, fxt.vnt_().Flags_codes_("A").Rule_("zh-hans", "b").Rule_("zh-hant", "c"));}
	@Test  public void Langs__flag__semic_n()			{fxt.Test_parse("-{zh-hans;zh-hant|b}-"					, fxt.vnt_().Flags_langs_(Xop_vnt_tkn_mok.Mask__hans, Xop_vnt_tkn_mok.Mask__hant).Rule_("b"));}
	@Test  public void Langs__flag__semic_y()			{fxt.Test_parse("-{zh-hans;zh-hant;|b}-"				, fxt.vnt_().Flags_langs_(Xop_vnt_tkn_mok.Mask__hans, Xop_vnt_tkn_mok.Mask__hant).Rule_("b"));}
	@Test  public void Langs__flag__ws()				{fxt.Test_parse("-{ zh-hans ; zh-hant ; |b}-"			, fxt.vnt_().Flags_langs_(Xop_vnt_tkn_mok.Mask__hans, Xop_vnt_tkn_mok.Mask__hant).Rule_("b"));}
	@Test  public void Langs__unknown__1st()			{fxt.Test_parse("-{ zh-hans x ; zh-hant ; |b}-"			, fxt.vnt_().Flags_unknown_().Rule_("b"));}
	@Test  public void Langs__unknown__nth()			{fxt.Test_parse("-{ zh-hans ; zh-hant x; |b}-"			, fxt.vnt_().Flags_unknown_().Rule_("b"));}
	@Test  public void Langs__unknown__all()			{fxt.Test_parse("-{ zh-hans x ; zh-hant x;|b}-"			, fxt.vnt_().Flags_unknown_().Rule_("b"));}
	@Test  public void Multiple()						{fxt.Test_parse("-{A|D|E|b}-"							, fxt.vnt_().Flags_codes_("A", "D", "E").Rule_("b"));}
	@Test  public void Unknown__flag_only()				{fxt.Test_parse("-{a}-"									, fxt.vnt_().Flags_none_().Rule_("a"));}
	@Test  public void Unknown__flag_w_text()			{fxt.Test_parse("-{ A x |b}-"							, fxt.vnt_().Flags_unknown_().Rule_("b"));}
	@Test  public void Bidi__basic()					{fxt.Test_parse("-{zh-hans:a;zh-hant:b}-"				, fxt.vnt_().Flags_none_().Rule_("zh-hans", "a").Rule_("zh-hant", "b"));}
	@Test  public void Bidi__invalid__1st()				{fxt.Test_parse("-{zh-x:x;zh-hans:a;zh-hant:b}-"		, fxt.vnt_().Flags_none_().Rule_("zh-x:x;zh-hans:a;zh-hant:b"));}
	@Test  public void Bidi__invalid__nth()				{fxt.Test_parse("-{zh-hans:a;zh-x:x;zh-hant:b}-"		, fxt.vnt_().Flags_none_().Rule_("zh-hans", "a;zh-x:x").Rule_("zh-hant", "b"));}
//		@Test  public void Bidi__html() {
//			fxt.Test_parse("-{zh-cn:<span class='border:1px;text-align:center;'>text1</span>;zh-tw:<span class='border:1px;text-align:center;'>tex21</span>;}-"
//			, fxt.vnt_().Flags_none_().Rule_("zh-hans", "a;zh-x:x").Rule_("zh-hant", "b"));
//		}
}
class Xop_vnt_tkn_mok {
	private final List_adp rules_list = List_adp_.new_(), flags_list = List_adp_.new_();
	private Xop_vnt_flag[] flags;
	public Xop_vnt_flag[] Flags() {
		if (flags == null) flags = (Xop_vnt_flag[])flags_list.To_ary(Xop_vnt_flag.class);
		return flags;
	}
	public Xop_vnt_tkn_mok Flags_none_()						{flags_list.Clear(); return this;}
	public Xop_vnt_tkn_mok Flags_unknown_(String... v)	{flags_list.Add(Xop_vnt_flag_.Flag_unknown); return this;}
	public Xop_vnt_tkn_mok Flags_langs_(int... ary)		{flags_list.Add(Xop_vnt_flag.new_lang(Enm_.Add_int_ary(ary))); return this;}
	public Xop_vnt_tkn_mok Flags_codes_(String... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			byte[] bry = Bry_.new_a7(ary[i]);
			Xop_vnt_flag flag = (Xop_vnt_flag)Xop_vnt_flag_.Trie.Match_bgn(bry, 0, bry.length);
			flags_list.Add(flag);
		}
		return this;
	}
	public Xop_vnt_rule_tkn[] Rules() {
		if (rules == null) rules = (Xop_vnt_rule_tkn[])rules_list.To_ary(Xop_vnt_rule_tkn.class);
		return rules;
	}	private Xop_vnt_rule_tkn[] rules;
	public Xop_vnt_tkn_mok Rule_(String rule)											{return Rule_(Xop_vnt_rule_tkn.Null_lang, rule);}
	public Xop_vnt_tkn_mok Rule_(byte[] lang, String rule)								{return Rule_(Xop_vnt_rule_tkn.Null_macro, lang, new Xop_bry_tkn(-1, -1, Bry_.new_u8(rule)));}
	public Xop_vnt_tkn_mok Rule_(String lang, String rule)								{return Rule_(Xop_vnt_rule_tkn.Null_macro, Bry_.new_a7(lang), new Xop_bry_tkn(-1, -1, Bry_.new_u8(rule)));}
	public Xop_vnt_tkn_mok Rule_(String macro, String lang, String rule)				{return Rule_(Bry_.new_a7(macro), Bry_.new_a7(lang), new Xop_bry_tkn(-1, -1, Bry_.new_u8(rule)));}
	public Xop_vnt_tkn_mok Rule_(byte[] macro, byte[] lang, Xop_tkn_itm... tkns)	{rules_list.Add(new Xop_vnt_rule_tkn(macro, lang, tkns)); return this;}
	public static final int Mask__hans = 2, Mask__hant = 4;
}
class Xop_vnt_lxr_fxt {
	private Xop_fxt fxt;
	private Xol_vnt_regy vnt_regy;
	private final Bry_bfr tmp_bfr = Bry_bfr.new_();
	public Xop_vnt_lxr_fxt Clear() {
		Xoae_app app = Xoa_app_fxt.app_();
		Xowe_wiki wiki = Xoa_app_fxt.wiki_(app, "zh.wikipedia.org");
		fxt = new Xop_fxt(app, wiki);
		Xop_vnt_lxr_fxt.Init_vnt_mgr(wiki.Lang().Vnt_mgr(), "zh-hans", "zh-hant", "zh-cn");
		Xop_vnt_lxr_.Init(wiki);
		this.vnt_regy = wiki.Lang().Vnt_mgr().Regy();
		return this;
	}
	public Xop_vnt_tkn_mok vnt_() {return new Xop_vnt_tkn_mok();}
	public static void Init_vnt_mgr(Xol_vnt_mgr vnt_mgr, String... vnts_str) {
		byte[][] vnts_bry = Bry_.Ary(vnts_str);
		int vnts_bry_len = vnts_bry.length;
		for (int i = 0; i < vnts_bry_len; i++)
			vnt_mgr.Regy__get_or_new(vnts_bry[i]);
		vnt_mgr.Convert_mgr().Init(vnt_mgr.Regy());
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
		Tfds.Eq_str_lines(Vnt_rule_ary_to_str(tmp_bfr, raw_bry, expd.Rules()), Vnt_rule_ary_to_str(tmp_bfr, raw_bry, actl.Vnt_rules()), "rules");
	}
	private String Vnt_flag_ary_to_str(Bry_bfr bfr, Xop_vnt_flag[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Xop_vnt_flag itm = ary[i];
			int itm_tid = itm.Tid();
			if (itm_tid == Xop_vnt_flag_.Tid_lang)
				Vnt_flag_lang_to_bfr(bfr, itm);
			else
				bfr.Add_str(Xop_vnt_flag_.To_name(itm_tid)).Add_byte(Byte_ascii.Semic);
		}
		return bfr.Xto_str_and_clear();
	}
	private void Vnt_flag_lang_to_bfr(Bry_bfr bfr, Xop_vnt_flag itm) {
		int itm_mask = itm.Mask();
		for (int i = 0; i < 32; ++i) {
			int mask = gplx.core.brys.Bit_.Get_flag(i);
			if (Enm_.Has_int(mask, itm_mask)) {
				Xol_vnt_itm vnt = vnt_regy.Get_at(i);
				bfr.Add(vnt.Key()).Add_byte(Byte_ascii.Semic);
			}
		}
	}
	private String Vnt_rule_ary_to_str(Bry_bfr bfr, byte[] src, Xop_vnt_rule_tkn[] ary) {
		if (ary == null) return "";
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Xop_vnt_rule_tkn itm = ary[i];
			if (itm.Rule_macro() != Xop_vnt_rule_tkn.Null_macro)	// macro exists
				bfr.Add(itm.Rule_macro()).Add_str("=>");
			if (itm.Rule_lang() != Xop_vnt_rule_tkn.Null_lang)		// lang exists
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
			bfr.Add_byte(Byte_ascii.Semic).Add_byte_nl();
		}
		return bfr.Xto_str_and_clear();
	}
}
