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
package gplx.xowa.xtns.hieros; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.wikis.*; import gplx.xowa.htmls.modules.*; import gplx.xowa.apps.fsys.*;
public class Hiero_xtn_mgr extends Xox_mgr_base implements Gfo_invk {
	@Override public boolean Enabled_default() {return true;}
	@Override public byte[] Xtn_key() {return Xtn_key_static;} public static final    byte[] Xtn_key_static = Bry_.new_a7("hiero");
	@Override public Xox_mgr Xtn_clone_new() {return new Hiero_xtn_mgr();}
	public static byte[] Img_src_dir;
	@Override public void Xtn_init_by_wiki(Xowe_wiki wiki) {}
	private static boolean xtn_init_done = false;
	public void Xtn_init_assert(Xowe_wiki wiki) {
		if (xtn_init_done) return;
		if (!Enabled()) return;
		Xoae_app app = wiki.Appe();
		Io_url ext_root_dir = Hiero_root_dir(app.Fsys_mgr());
		Img_src_dir = Bry_.new_u8(ext_root_dir.GenSubDir("img").To_http_file_str());
		app.Gfs_mgr().Run_url_for(this, ext_root_dir.GenSubFil_nest("data", "tables.gfs"));
		html_wtr = new Hiero_html_mgr(this);
		parser.Init();
		xtn_init_done = true;
	}
	@gplx.Internal protected Hiero_parser Parser() {return parser;} private static final    Hiero_parser parser = new Hiero_parser();
	@gplx.Internal protected Hiero_prefab_mgr Prefab_mgr() {return prefab_mgr;} private static final    Hiero_prefab_mgr prefab_mgr = new Hiero_prefab_mgr();
	@gplx.Internal protected Hiero_file_mgr File_mgr() {return file_mgr;} private static final    Hiero_file_mgr file_mgr = new Hiero_file_mgr();
	@gplx.Internal protected Hiero_phoneme_mgr Phoneme_mgr() {return phoneme_mgr;} private static final    Hiero_phoneme_mgr phoneme_mgr = new Hiero_phoneme_mgr();
	@gplx.Internal protected Hiero_html_mgr Html_wtr() {return html_wtr;} private static Hiero_html_mgr html_wtr;
	public void Clear() {
		prefab_mgr.Clear();
		file_mgr.Clear();
		phoneme_mgr.Clear();
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_prefabs))			return prefab_mgr;
		else if	(ctx.Match(k, Invk_files))				return file_mgr;
		else if	(ctx.Match(k, Invk_phonemes))			return phoneme_mgr;
		else	return super.Invk(ctx, ikey, k, m);
	}
	public static final String Invk_prefabs = "prefabs", Invk_files = "files", Invk_phonemes = "phonemes";
	public static void Hiero_root_dir_(Io_url v) {hiero_root_dir = v;} private static Io_url hiero_root_dir;
	public static Io_url Hiero_root_dir(Xoa_fsys_mgr fsys_mgr) {return hiero_root_dir == null ? fsys_mgr.Bin_xtns_dir().GenSubDir("Wikihiero") : hiero_root_dir;}
}
