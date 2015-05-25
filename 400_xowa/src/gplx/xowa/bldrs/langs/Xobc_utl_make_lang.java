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
package gplx.xowa.bldrs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.xowa.apps.fsys.*; import gplx.xowa.langs.*;
public class Xobc_utl_make_lang implements GfoInvkAble {
	private final Xoa_lang_mgr lang_mgr; private final Xoa_fsys_mgr fsys_mgr; Xol_mw_lang_parser lang_parser;
	public Xobc_utl_make_lang(Xoa_lang_mgr lang_mgr, Xoa_fsys_mgr fsys_mgr, Gfo_msg_log msg_log) {
		this.lang_mgr = lang_mgr; this.fsys_mgr = fsys_mgr;
		kwd_mgr = new Xobc_utl_make_lang_kwds(lang_mgr);
		lang_parser = new Xol_mw_lang_parser(msg_log);
	}
	public Xobc_utl_make_lang_kwds Kwd_mgr() {return kwd_mgr;} private Xobc_utl_make_lang_kwds kwd_mgr;
	public Ordered_hash Manual_text_bgn_hash() {return manual_text_bgn_hash;} private final Ordered_hash manual_text_bgn_hash = Ordered_hash_.new_bry_();
	public Ordered_hash Manual_text_end_hash() {return manual_text_end_hash;} private final Ordered_hash manual_text_end_hash = Ordered_hash_.new_bry_();
	public void Bld_all() {
		Io_url lang_root = fsys_mgr.Cfg_lang_core_dir().OwnerDir();	// OwnerDir to get "/lang/" in "/cfg/lang/core/"
		lang_parser.Parse_mediawiki(lang_mgr, lang_root.GenSubDir("mediawiki"), kwd_mgr);
		kwd_mgr.Add_words();
		lang_parser.Save_langs(lang_mgr, lang_root.GenSubDir(Xol_mw_lang_parser.Dir_name_core), manual_text_bgn_hash, manual_text_end_hash);
		Gfo_usr_dlg_.I.Prog_many("", "", "done");
	}
	public void Parse_manual_text(byte[] langs_bry, byte[] text, Ordered_hash manual_text) {
		Ordered_hash langs = lang_mgr.Xto_hash(langs_bry);
		int langs_len = langs.Count();
		for (int i = 0; i < langs_len; i++) {
			Xoac_lang_itm itm = (Xoac_lang_itm)langs.Get_at(i);
			byte[] key_bry = itm.Key_bry();
			manual_text.Add(key_bry, new byte[][] {key_bry, text});
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_kwds))					return kwd_mgr;
		else if	(ctx.Match(k, Invk_build_all))				Bld_all();
		else if	(ctx.Match(k, Invk_manual_text_bgn))		Parse_manual_text(m.ReadBry("langs"), m.ReadBry("text"), manual_text_bgn_hash);
		else if	(ctx.Match(k, Invk_manual_text_end))		Parse_manual_text(m.ReadBry("langs"), m.ReadBry("text"), manual_text_end_hash);
		else												return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_kwds = "keywords", Invk_manual_text_bgn = "manual_text_bgn", Invk_manual_text_end = "manual_text_end", Invk_build_all = "build_all";
}
