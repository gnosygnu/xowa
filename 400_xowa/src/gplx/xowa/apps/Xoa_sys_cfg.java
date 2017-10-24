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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
import gplx.xowa.langs.*;
public class Xoa_sys_cfg implements Gfo_invk {
	private Xoae_app app;
	public Xoa_sys_cfg(Xoae_app app) {this.app = app;}
	public byte[] Lang() {return lang_key;}
	public Xoa_sys_cfg Lang_(byte[] v) {
		lang_key = Xol_lang_stub_.Get_by_key_or_en(v).Key();
		if (app.Stage() == gplx.xowa.apps.Xoa_stage_.Tid_launch) {	// do not update user lang unless launched; DATE:2014-05-26
			Xol_lang_itm lang = app.Lang_mgr().Get_by_or_load(lang_key);
			app.Usere().Lang_(lang);
			app.Usere().Wiki().Html_mgr().Portal_mgr().Init();
		}
		return this;
	}	private byte[] lang_key = Xol_lang_itm_.Key_en;
	public void Init_by_app(Xoa_app app) {
		app.Cfg().Type_mgr().Lists__add("list:" + Cfg__lang, Options_list_lang_.new_());
		app.Cfg().Bind_many_app(this, Cfg__lang);
	}
	private static final String Cfg__lang = "xowa.gui.app.lang";
	public int Options_version() {return options_version;} public Xoa_sys_cfg Options_version_(int v) {options_version = v; return this;} private int options_version = 1;
	public Keyval[] Options_lang_list() {if (options_lang_list == null) options_lang_list = Options_list_lang_.new_(); return options_lang_list;} private Keyval[] options_lang_list;
	public long Free_mem_when() {return free_mem_when;} long free_mem_when;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_version))			return Xoa_app_.Version;
		else if	(ctx.Match(k, Invk_build_date))			return Xoa_app_.Build_date;
		else if	(ctx.Match(k, Invk_free_mem_when_))		free_mem_when = gplx.core.ios.Io_size_.parse_or(m.ReadStr("v"), Io_mgr.Len_mb * 5);
		else if	(ctx.Match(k, Invk_lang))				return lang_key;
		else if	(ctx.Match(k, Invk_lang_))				Lang_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_lang_list))			return Options_lang_list();
		else if	(ctx.Match(k, Invk_options_version))	return options_version;
		else if	(ctx.Match(k, Invk_options_version_))	options_version = m.ReadInt("v");
		else if	(ctx.Match(k, Cfg__lang))				Lang_(m.ReadBry("v"));
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_version = "version", Invk_build_date = "build_date", Invk_free_mem_when_ = "free_mem_when_", Invk_options_version = "options_version", Invk_options_version_ = "options_version_"
		, Invk_lang = "lang", Invk_lang_ = "lang_", Invk_lang_list = "lang_list";
}
class Options_list_lang_ {
	public static Keyval[] new_() {
		Ordered_hash translated = Ordered_hash_.New_bry();
		List_adp untranslated = List_adp_.New();
		Add_itm_many(translated, Xol_lang_stub_.Id_en, Xol_lang_stub_.Id_de, Xol_lang_stub_.Id_pl, Xol_lang_stub_.Id_zh_hans, Xol_lang_stub_.Id_zh_hant); // add langs with translations first, so they alphabetize to top of list			
		int len = Xol_lang_stub_.Id__max;
		for (int i = 0; i < len; i++) {	// add rest of langs, but sort by code
			Xol_lang_stub itm = Xol_lang_stub_.Get_by_id(i);
			if (translated.Has(itm.Key())) continue;
			untranslated.Add(itm);
		}
		untranslated.Sort_by(Xol_lang_stub_.Comparer_key);

		Keyval[] rv = new Keyval[len];
		int translated_max = translated.Count();
		for (int i = 0; i < translated_max; i++)
			rv[i] = new_itm((Xol_lang_stub)translated.Get_at(i));

		for (int i = translated_max; i < len; i++)
			rv[i] = new_itm((Xol_lang_stub)untranslated.Get_at(i - translated_max));
		return rv;
	}
	private static Keyval new_itm(Xol_lang_stub itm) {
		String key_str = String_.new_u8(itm.Key());
		String name_str = String_.new_u8(itm.Canonical_name());
		return Keyval_.new_(key_str, name_str + " [" + key_str + "]");
	}
	private static void Add_itm_many(Ordered_hash translated, int... langs) {
		int langs_len = langs.length;
		for (int i = 0; i < langs_len; i++) {
			Xol_lang_stub itm = Xol_lang_stub_.Get_by_id(langs[i]);
			translated.Add_if_dupe_use_nth(itm.Key(), itm);
		}
	}
}
