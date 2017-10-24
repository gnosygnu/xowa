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
package gplx.xowa.guis.menus.dom; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.menus.*;
import gplx.gfui.*; import gplx.gfui.kits.core.*;
import gplx.xowa.langs.*; import gplx.xowa.guis.cmds.*; import gplx.xowa.langs.msgs.*;
public class Xog_mnu_itm extends Xog_mnu_base {
	private Xoa_gui_mgr gui_mgr;
	public Xog_mnu_itm(Xoa_gui_mgr gui_mgr, String key) {
		this.gui_mgr = gui_mgr; this.key = key; this.key_bry = Bry_.new_u8(key);
		this.Ctor(gui_mgr);
	}
	public byte Tid() {return tid;} public Xog_mnu_itm Tid_(byte v) {tid = v; return this;} private byte tid;		
	@Override public boolean Tid_is_app_menu_grp() {return false;}
	public String Key() {return key;} private String key; private byte[] key_bry;
	public String Name() {return name;} public void Name_(String v) {this.name = v; Gui_text_calc_();} private String name = "";
	public String Shortcut() {return shortcut;} public void Shortcut_(String v) {this.shortcut = v; Gui_text_calc_();}  private String shortcut;
	public String Gui_text() {return gui_text;} public void Gui_text_calc_() {gui_text = Gui_text_calc(name, shortcut);} private String gui_text = "";
	public String[] Img_nest() {return img_nest;} private String[] img_nest = String_.Ary_empty;
	private String Cmd() {
		Xog_cmd_itm cmd_itm = gui_mgr.Cmd_mgr().Get_or_null(key);
		return cmd_itm == null ? "" : cmd_itm.Cmd();
	}
	public Xog_mnu_itm Cmd_(String cmd) {
		Xog_cmd_itm cmd_itm = gui_mgr.Cmd_mgr().Get_or_make(key);
		cmd_itm.Cmd_(cmd);
		return this;
	}
	public Gfui_mnu_itm Under_gui() {return under_gui;} public void Under_gui_(Gfui_mnu_itm v) {under_gui = v;} private Gfui_mnu_itm under_gui;
	private Xog_mnu_itm Init_by_clone(Xog_mnu_itm comp) {
		this.tid = comp.tid; this.name = comp.name; this.shortcut = comp.shortcut; this.img_nest = comp.img_nest; this.gui_text = comp.gui_text;
		return this;
	}
	public void Init_by_lang(Xol_lang_itm lang) {
		this.name			= Xol_msg_mgr_.Get_msg_val_gui_or(lang.Lang_mgr(), lang, Xog_cmd_itm_.Msg_pre_api, key_bry, Xog_cmd_itm_.Msg_suf_name, name);	// NOTE: custom cmds must use Get_val_or, not Get_val_or_null; DATE:2014-05-30
		this.shortcut		= Xol_msg_mgr_.Get_msg_val_gui_or(lang.Lang_mgr(), lang, Xog_cmd_itm_.Msg_pre_api, key_bry, Xog_cmd_itm_.Msg_suf_letter, shortcut);
		this.img_nest		= Img_nest_of(Xol_msg_mgr_.Get_msg_val_gui_or(lang.Lang_mgr(), lang, Xog_cmd_itm_.Msg_pre_api, key_bry, Xog_cmd_itm_.Msg_suf_image, ""));
		this.gui_text		= Gui_text_calc(name, shortcut);
	}
	public void Init_by_custom(String name, String shortcut, String img_nest_str) {
		this.name			= name;
		this.shortcut		= shortcut;
		this.img_nest		= Img_nest_of(img_nest_str);
		this.gui_text		= Gui_text_calc(name, shortcut);
	}	
	public static String Gui_text_calc(String name, String shortcut) {
		if (shortcut == null || String_.Len(shortcut) != 1) return name;			// shortcut is null or > 1 char; return name
		int pos = String_.FindFwd(String_.Lower(name), String_.Lower(shortcut));	// search for shortcut in name
		if (pos == String_.Find_none) return name;									// shortcut not found; return name; EX: "x" in "File" returns "File"
		return String_.Mid(name, 0, pos) + "&" + String_.Mid(name, pos);			// add & before shortcut; EX: "x" in "Exit" -> "E&xit"
	}
	public Xog_mnu_itm Clone() {
		Xog_mnu_itm rv = new Xog_mnu_itm(gui_mgr, key).Init_by_clone(this);
		rv.Evt_mgr_(this.Evt_mgr());
		return rv;
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_text))			return name;
		else if	(ctx.Match(k, Invk_text_))			Name_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_shortcut))		return shortcut;
		else if	(ctx.Match(k, Invk_shortcut_))		Shortcut_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_cmd))			return this.Cmd();
		else if	(ctx.Match(k, Invk_cmd_))			Cmd_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_img))			return String_.Concat_with_str("/", img_nest);
		else if	(ctx.Match(k, Invk_img_))			img_nest = Img_nest_of(m.ReadStr("v"));
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}
	private static final    String Invk_text = "text", Invk_text_ = "text_", Invk_shortcut = "shortcut", Invk_shortcut_ = "shortcut_", Invk_cmd = "cmd", Invk_cmd_ = "cmd_", Invk_img = "img", Invk_img_ = "img_"
	;
	public static final byte Tid_nil = 0, Tid_grp = 1, Tid_spr = 2, Tid_btn = 3, Tid_chk = 4, Tid_rdo = 5;
	private static String[] Img_nest_of(String img) {return String_.Len_eq_0(img) ? String_.Ary_empty : String_.Split(img, "/");}
	public static final    Xog_mnu_itm Null = new Xog_mnu_itm(null, "null");
}
