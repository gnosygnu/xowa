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
import gplx.xowa.langs.vnts.converts.*; import gplx.xowa.parsers.vnts.*;
public class Xol_vnt_mgr implements Gfo_invk {
	public Xol_vnt_mgr(Xol_lang_itm lang) {
		this.lang = lang;
		this.convert_lang = new Vnt_convert_lang(convert_mgr, regy);
	}
	public Xol_lang_itm				Lang() {return lang;} private final    Xol_lang_itm lang;
	public Xol_convert_mgr			Convert_mgr() {return convert_mgr;} private final    Xol_convert_mgr convert_mgr = new Xol_convert_mgr();
	public Vnt_convert_lang			Convert_lang() {return convert_lang;} private final    Vnt_convert_lang convert_lang;
	public Xol_vnt_regy				Regy() {return regy;} private final    Xol_vnt_regy regy = new Xol_vnt_regy();	// EX:zh;zh-hans;zh-hant;zh-cn;zh-hk;zh-mo;zh-sg;zh-tw
	public Xol_vnt_itm				Cur_itm() {return cur_itm;} private Xol_vnt_itm cur_itm;						// EX:zh-cn
	public boolean						Enabled() {return enabled;} private boolean enabled = false;
	public String					Html__lnki_style() {return html__lnki_style;} private String html__lnki_style = "";	// style for showing vnt lnkis in different colors
	public void						Enabled_(boolean v) {this.enabled = v;}	// TEST
	public Xol_vnt_itm				Regy__get_or_new(byte[] key) {
		Xol_vnt_itm rv = regy.Get_by(key);
		if (rv == null) {
			byte[] name = lang.Msg_mgr().Itm_by_key_or_new(Bry_.Add(Msg_variantname, key)).Val();
			rv = regy.Add(key, name);
			enabled = true;	// NOTE: mark enabled if any vnts have been added
		}
		return rv;
	}
	public void Cur_itm_(byte[] v) {
		if (Bry_.Len_eq_0(v)) return;	// Cfg is empty by default
		this.cur_itm = regy.Get_by(v); if (cur_itm == null) throw Err_.new_("lang.vnt", "vnt not found", "key", v);
	} 
	public void Init_end() {
		int len = regy.Len();
		for (int i = 0; i < len; ++i) {	// calc fallback_flag; needs to be run after all items added, b/c "zh-cn" added before "zh-sg" but "zh-cn" can have "zh-sg" as fallback
			Xol_vnt_itm itm = regy.Get_at(i);
			Xol_lang_itm vnt_lang = lang.Lang_mgr().Get_by_or_load(itm.Key());	// load vnt's language in order to get fallback; EX: "zh-mo" to get "zh-hant|zh-hk|zh-tw"
			itm.Mask__fallbacks_(regy, vnt_lang.Fallback_bry_ary());
		}
		convert_mgr.Init(regy);			// needs to run at end b/c converts are added one at a time
	}		
	private void Limit_visibility(byte[][] ary) {
		int regy_len = regy.Len();
		for (int i = 0; i < regy_len; ++i)
			regy.Get_at(i).Visible_(Bool_.N);
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; ++i)
			regy.Get_by(ary[i]).Visible_(Bool_.Y);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))					return Regy__get_or_new(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_init_end))				Init_end();
		else if	(ctx.Match(k, Invk_vnt_grp_))				Limit_visibility(m.ReadBryAry("v", Byte_ascii.Pipe));
		else if	(ctx.Match(k, Invk_cur_vnt_))				Cur_itm_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_html_style_))			html__lnki_style = m.ReadStr("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_get = "get", Invk_init_end = "init_end", Invk_cur_vnt_ = "cur_vnt_", Invk_vnt_grp_ = "vnt_grp_", Invk_html_style_ = "html_style_";
	private static final    byte[] Msg_variantname = Bry_.new_a7("variantname-");
}
