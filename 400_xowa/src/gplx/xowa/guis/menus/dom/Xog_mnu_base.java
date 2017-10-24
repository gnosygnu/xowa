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
import gplx.gfui.*; import gplx.gfui.imgs.*;
import gplx.xowa.langs.*; import gplx.xowa.guis.cmds.*;
public abstract class Xog_mnu_base implements Gfo_invk {
	private List_adp list = List_adp_.New();
	public Xog_mnu_base() {evt_mgr = new Xog_mnu_evt_mgr(this);}
	public Xog_mnu_evt_mgr Evt_mgr() {return evt_mgr;} private Xog_mnu_evt_mgr evt_mgr;
	public void Evt_mgr_(Xog_mnu_evt_mgr v) {this.evt_mgr = v;}
	public Xoa_gui_mgr Gui_mgr() {return gui_mgr;} private Xoa_gui_mgr gui_mgr;
	public abstract boolean Tid_is_app_menu_grp();
	public void Ctor(Xoa_gui_mgr gui_mgr) {this.gui_mgr = gui_mgr;}
	public void Clear() {
		int len = list.Count();
		for (int i = 0; i < len; i++) {
			Xog_mnu_itm itm = (Xog_mnu_itm)list.Get_at(i);
			itm.Clear();
		}
		list.Clear();
	}
	public int Len() {return list.Count();}
	public Xog_mnu_itm Get_at(int i) {return (Xog_mnu_itm)list.Get_at(i);}
	public Xog_mnu_itm Add_btn_default(String key) {return Add_itm_default(Xog_mnu_itm.Tid_btn, key);}
	public Xog_mnu_itm Add_chk_default(String key) {return Add_itm_default(Xog_mnu_itm.Tid_chk, key);}
	public Xog_mnu_itm Add_rdo_default(String key) {return Add_itm_default(Xog_mnu_itm.Tid_rdo, key);}
	public Xog_mnu_itm Add_grp_default(String key) {return Add_itm_default(Xog_mnu_itm.Tid_grp, key);}
	private Xog_mnu_itm Add_itm_default(byte tid, String key) {
		Xog_mnu_itm itm = gui_mgr.Menu_mgr().Regy().Get_or_make(key);
		itm.Tid_(tid);
		list.Add(itm);
		return itm;
	}
	public Xog_mnu_itm Add_btn(String key, String text, String shortcut, String img, String cmd)	{
		Xog_mnu_itm rv = Add_itm(Xog_mnu_itm.Tid_btn, key, text, shortcut, img);
		rv.Cmd_(cmd);
		return rv;
	}
	public Xog_mnu_itm Add_chk(String key, String text, String shortcut, String img, String cmd)	{
		Xog_mnu_itm rv = Add_itm(Xog_mnu_itm.Tid_chk, key, text, shortcut, img);
		rv.Cmd_(cmd);
		return rv;
	}
	public Xog_mnu_itm Add_rdo(String key, String text, String shortcut, String img, String cmd)	{
		Xog_mnu_itm rv = Add_itm(Xog_mnu_itm.Tid_rdo, key, text, shortcut, img);
		rv.Cmd_(cmd);
		return rv;
	}
	public Xog_mnu_itm Add_grp(String key, String text, String shortcut, String img)				{return Add_itm(Xog_mnu_itm.Tid_grp, key, text, shortcut, img);}
	private Xog_mnu_itm Add_itm(byte tid, String key, String text, String shortcut, String img) {
		Xog_mnu_itm itm = gui_mgr.Menu_mgr().Regy().Get_or_make(key);
		itm.Tid_(tid);
		list.Add(itm);
		itm.Init_by_custom(text, shortcut, img);
		return itm;
	}
	public Xog_mnu_itm Add_spr() {
		String key = "xowa.spr" + Int_.To_str(list.Count());
		Xog_mnu_itm rv = new Xog_mnu_itm(gui_mgr, key).Tid_(Xog_mnu_itm.Tid_spr);
		list.Add(rv);
		return rv;
	}
	@gplx.Virtual public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_clear))				this.Clear();
//			else if	(ctx.Match(k, Invk_add))				return Add_itm_default(Xog_mnu_itm.Tid_nil, m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_add_spr))			return Add_spr();
		else if	(ctx.Match(k, Invk_add_grp_default))	return Add_grp_default(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_add_grp))			return Add_grp(m.ReadStr("key"), m.ReadStr("text"), m.ReadStr("shortcut"), m.ReadStrOr("img", ""));
		else if	(ctx.Match(k, Invk_add_btn_default))	return Add_btn_default(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_add_btn))			return Add_btn(m.ReadStr("key"), m.ReadStr("text"), m.ReadStr("shortcut"), m.ReadStr("img"), m.ReadStr("cmd"));
		else if	(ctx.Match(k, Invk_add_chk_default))	return Add_chk_default(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_add_chk))			return Add_chk(m.ReadStr("key"), m.ReadStr("text"), m.ReadStr("shortcut"), m.ReadStr("img"), m.ReadStr("cmd"));
		else if	(ctx.Match(k, Invk_add_rdo_default))	return Add_rdo_default(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_add_rdo))			return Add_rdo(m.ReadStr("key"), m.ReadStr("text"), m.ReadStr("shortcut"), m.ReadStr("img"), m.ReadStr("cmd"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String 
	  Invk_clear = "clear"
	, Invk_add_spr = "add_spr"
	, Invk_add_grp_default = "add_grp_default", Invk_add_grp = "add_grp"
	, Invk_add_btn_default = "add_btn_default", Invk_add_btn = "add_btn"
	, Invk_add_chk_default = "add_chk_default", Invk_add_chk = "add_chk"
	, Invk_add_rdo_default = "add_rdo_default", Invk_add_rdo = "add_rdo"
	;
	public static void Update_grp_by_lang(Xog_mnu_bldr bldr, Xol_lang_itm lang, Xog_mnu_base grp) {
		int len = grp.Len();
		for (int i = 0; i < len; i++) {
			Xog_mnu_itm itm = grp.Get_at(i);
			itm.Init_by_lang(lang);
			if (itm.Under_gui() != null) {
				itm.Under_gui().Text_(itm.Gui_text());
				ImageAdp img = grp.Tid_is_app_menu_grp()
					? ImageAdp_.Null						// set image to null if window menu grp
					: bldr.Get_img(itm.Img_nest())
					;
				itm.Under_gui().Img_(img);
			}
			if (itm.Tid() == Xog_mnu_itm.Tid_grp)
				Update_grp_by_lang(bldr, lang, itm);
		}
	}
}
