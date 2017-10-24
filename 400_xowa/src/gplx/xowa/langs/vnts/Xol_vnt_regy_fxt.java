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
	public static void Init__vnt_mgr(Xol_vnt_mgr vnt_mgr, int cur_idx, String[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xol_vnt_itm itm = vnt_mgr.Regy__get_or_new(Bry_.new_a7(ary[i]));
			vnt_mgr.Lang().Lang_mgr().Get_by_or_load(itm.Key()).Fallback_bry_(Bry_.new_a7("zh-hans,zh-hant"));
		}
		vnt_mgr.Init_end();
		vnt_mgr.Cur_itm_(Bry_.new_a7(ary[cur_idx]));
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
