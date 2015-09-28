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
import gplx.xowa.parsers.vnts.*;
public class Xol_vnt_regy_fxt {		
	private final Xol_vnt_regy mgr = new_chinese();
	public String[] Make_lang_chain_cn() {return String_.Ary("zh-cn", "zh-hans", "zh-hant", "zh");}
	public void Test_match_any(boolean expd, String[] lang_chain, String[]... vnt_chain_ary) {
		int len = vnt_chain_ary.length;
		int lang_flag = mgr.Mask__calc(Bry_.Ary(lang_chain));
		for (int i = 0; i < len; ++i) {
			String[] vnt_chain = vnt_chain_ary[i];	// EX: -{zh;zh-hans;zh-hant}-
			int vnt_flag = mgr.Mask__calc(Bry_.Ary(vnt_chain));
			Tfds.Eq(expd, mgr.Mask__match_any(vnt_flag, lang_flag), String_.Concat_with_str(";", vnt_chain) + "<>" + String_.Concat_with_str(";", lang_chain));
		}
	}
	public void Test_calc(String[] ary, int expd) {
		Tfds.Eq(expd, mgr.Mask__calc(Bry_.Ary(ary)));
	}
	public void Test_sort(String[] vnt_ary, String[] expd) {
		int vnt_len = vnt_ary.length;
		Xop_vnt_rule_tkn[] rule_ary = new Xop_vnt_rule_tkn[vnt_len];
		for (int i = 0; i < vnt_len; ++i)
			rule_ary[i] = new Xop_vnt_rule_tkn(Bry_.Empty, Bry_.new_u8(vnt_ary[i]), gplx.xowa.parsers.Xop_tkn_itm_.Ary_empty);
		mgr.Mask__sort(rule_ary);
		for (int i = 0; i < vnt_len; ++i)
			vnt_ary[i] = String_.new_u8(rule_ary[i].Rule_lang());
		Tfds.Eq_ary_str(expd, vnt_ary);
	}
	public static Xol_vnt_regy new_chinese() {	// REF.MW:/languages/classes/LanguageZh.php|LanguageZh|__construct
		Xol_vnt_regy rv = new Xol_vnt_regy();
		new_chinese_vnt(rv, "zh"		, Xol_vnt_dir_.Tid__none, "zh-hans", "zh-hant", "zh-cn", "zh-tw", "zh-hk", "zh-sg", "zh-mo", "zh-my");
		new_chinese_vnt(rv, "zh-hans"	, Xol_vnt_dir_.Tid__uni	, "zh-cn", "zh-sg", "zh-my");
		new_chinese_vnt(rv, "zh-hant"	, Xol_vnt_dir_.Tid__uni	, "zh-tw", "zh-hk", "zh-mo");
		new_chinese_vnt(rv, "zh-cn"		, Xol_vnt_dir_.Tid__bi	, "zh-hans", "zh-sg", "zh-my");
		new_chinese_vnt(rv, "zh-hk"		, Xol_vnt_dir_.Tid__bi	, "zh-hant", "zh-mo", "zh-tw");
		new_chinese_vnt(rv, "zh-my"		, Xol_vnt_dir_.Tid__bi	, "zh-hans", "zh-sg", "zh-cn");
		new_chinese_vnt(rv, "zh-mo"		, Xol_vnt_dir_.Tid__bi	, "zh-hant", "zh-hk", "zh-tw");
		new_chinese_vnt(rv, "zh-sg"		, Xol_vnt_dir_.Tid__bi	, "zh-hans", "zh-cn", "zh-my");
		new_chinese_vnt(rv, "zh-tw"		, Xol_vnt_dir_.Tid__bi	, "zh-hant", "zh-hk", "zh-mo");
		return rv;
	}
	private static void new_chinese_vnt(Xol_vnt_regy regy, String key, int dir, String... fallbacks) {
		byte[] key_bry = Bry_.new_u8(key);
		Xol_vnt_itm itm = regy.Add(key_bry, Bry_.Ucase__all(key_bry));
		itm.Init(dir, Bry_.Ary(fallbacks));
	}
}
