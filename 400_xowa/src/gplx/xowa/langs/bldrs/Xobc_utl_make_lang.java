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
package gplx.xowa.langs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.log_msgs.*;
import gplx.xowa.apps.fsys.*;
import gplx.xowa.langs.*;
public class Xobc_utl_make_lang implements Gfo_invk {
	private final    Xoa_lang_mgr lang_mgr; private final    Xoa_fsys_mgr fsys_mgr; Xol_mw_lang_parser lang_parser;
	public Xobc_utl_make_lang(Xoa_lang_mgr lang_mgr, Xoa_fsys_mgr fsys_mgr, Gfo_msg_log msg_log) {
		this.lang_mgr = lang_mgr; this.fsys_mgr = fsys_mgr;
		kwd_mgr = new Xobc_utl_make_lang_kwds(lang_mgr);
		lang_parser = new Xol_mw_lang_parser(msg_log);
	}
	public Xobc_utl_make_lang_kwds Kwd_mgr() {return kwd_mgr;} private Xobc_utl_make_lang_kwds kwd_mgr;
	public Ordered_hash Manual_text_bgn_hash() {return manual_text_bgn_hash;} private final    Ordered_hash manual_text_bgn_hash = Ordered_hash_.New_bry();
	public Ordered_hash Manual_text_end_hash() {return manual_text_end_hash;} private final    Ordered_hash manual_text_end_hash = Ordered_hash_.New_bry();
	public void Bld_all() {
		Io_url lang_root = fsys_mgr.Cfg_lang_core_dir().OwnerDir();	// OwnerDir to get "/lang/" in "/cfg/lang/core/"
		lang_parser.Parse_mediawiki(lang_mgr, lang_root.GenSubDir("mediawiki"), kwd_mgr);
		kwd_mgr.Add_words();
		lang_parser.Save_langs(lang_mgr, lang_root.GenSubDir(Xol_mw_lang_parser.Dir_name_core), manual_text_bgn_hash, manual_text_end_hash);
		Gfo_usr_dlg_.Instance.Prog_many("", "", "done");
	}
	public void Parse_manual_text(byte[] key, byte[] text, Ordered_hash manual_text) {
		manual_text.Add(key, new byte[][] {key, text});
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_kwds))					return kwd_mgr;
		else if	(ctx.Match(k, Invk_build_all))				Bld_all();
		else if	(ctx.Match(k, Invk_manual_text_bgn))		Parse_manual_text(m.ReadBry("langs"), m.ReadBry("text"), manual_text_bgn_hash);
		else if	(ctx.Match(k, Invk_manual_text_end))		Parse_manual_text(m.ReadBry("langs"), m.ReadBry("text"), manual_text_end_hash);
		else												return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_kwds = "keywords", Invk_manual_text_bgn = "manual_text_bgn", Invk_manual_text_end = "manual_text_end", Invk_build_all = "build_all";
}
