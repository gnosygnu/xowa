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
import gplx.xowa.langs.vnts.converts.*;
import gplx.xowa.parsers.vnts.*;
public class Xol_vnt_mgr implements GfoInvkAble {
	public Xol_vnt_mgr(Xol_lang lang) {this.lang = lang;}
	public Xol_lang					Lang() {return lang;} private final Xol_lang lang;
	public boolean						Enabled() {return enabled;} private boolean enabled = false;
	public Xol_vnt_regy				Regy() {return regy;} private final Xol_vnt_regy regy = new Xol_vnt_regy();	// EX:zh;zh-hans;zh-hant;zh-cn;zh-hk;zh-mo;zh-sg;zh-tw
	public byte[]					Cur_key() {return cur_key;} private byte[] cur_key;								// EX:zh-cn
	public Xol_vnt_itm				Cur_itm() {return cur_itm;} private Xol_vnt_itm cur_itm;
	public Xol_convert_mgr			Convert_mgr() {return convert_mgr;} private final Xol_convert_mgr convert_mgr = new Xol_convert_mgr();
	public String					Html__lnki_style() {return html__lnki_style;} private String html__lnki_style = "";	// style for showing vnt lnkis in different colors
	public Xol_vnt_itm				Regy__get_or_new(byte[] key) {
		Xol_vnt_itm rv = regy.Get_by(key);
		if (rv == null) {
			byte[] name = lang.Msg_mgr().Itm_by_key_or_new(Bry_.Add(Msg_variantname, key)).Val();
			rv = regy.Add(key, name);
			enabled = true;	// NOTE: mark enabled if any vnts have been added
		}
		return rv;
	}
	private void Limit_visibility(byte[][] ary) {
		int regy_len = regy.Len();
		for (int i = 0; i < regy_len; ++i)
			regy.Get_at(i).Visible_(Bool_.N);
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; ++i)
			regy.Get_by(ary[i]).Visible_(Bool_.Y);
	}
	public void Cur_vnt_(byte[] v) {
		this.cur_itm = regy.Get_by(v); if (v == null) throw Err_.new_("lang.vnt", "vnt not found", "key", v);
		this.cur_key = v;
		convert_mgr.Cur_vnt_(v);
	} 
	public void Init_by_wiki(Xowe_wiki wiki) {
		if (!enabled) return;
		Xop_vnt_lxr_.Init(wiki);
	}
	public void Init_end() {
		int len = regy.Len();
		for (int i = 0; i < len; ++i) {	// calc fallback_flag; needs to be run after all items added, b/c "zh-cn" added before "zh-sg" but "zh-cn" can have "zh-sg" as fallback
			Xol_vnt_itm itm = regy.Get_at(i);
			Xol_lang vnt_lang = lang.Lang_mgr().Get_by_key_or_load(itm.Key());	// load vnt's language in order to get fallback; EX: "zh-mo" to get "zh-hant|zh-hk|zh-tw"
			itm.Mask__fallbacks__calc(regy, vnt_lang.Fallback_bry_ary());
		}
		convert_mgr.Init(regy);			// needs to run at end b/c converts are added one at a time
	}		
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))					return Regy__get_or_new(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_init_end))				Init_end();
		else if	(ctx.Match(k, Invk_vnt_grp_))				Limit_visibility(m.ReadBryAry("v", Byte_ascii.Pipe));
		else if	(ctx.Match(k, Invk_cur_vnt_))				Cur_vnt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_html_style_))			html__lnki_style = m.ReadStr("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_get = "get", Invk_init_end = "init_end", Invk_cur_vnt_ = "cur_vnt_", Invk_vnt_grp_ = "vnt_grp_", Invk_html_style_ = "html_style_";
	private static final byte[] Msg_variantname = Bry_.new_a7("variantname-");
}
