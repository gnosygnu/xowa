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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.domains.*;
public class Xoa_wiki_regy {
	private Xoae_app app;
	private boolean init_needed = true;
	private Hash_adp_bry hash = Hash_adp_bry.cs();
	public Xoa_wiki_regy(Xoae_app app) {this.app = app;}
	public boolean Has(byte[] domain) {if (init_needed) Init(); return hash.Has(domain);}
	public void Clear() {
		hash.Clear();
		init_needed = true;
	}
	public boolean Url_is_invalid_domain(Xoa_url url) {
		if (!Bry_.Eq(url.Page_bry(), Xoa_page_.Main_page_bry)) return false;		// page is not "Main_Page"; assume not an invalid domain str; EX: "uk/wiki/Main_Page"
		if (	 Bry_.Eq(Xow_domain_tid_.Bry__home, url.Wiki_bry())				// wiki is "home"
			&&	!Bry_.Eq(Xow_domain_tid_.Bry__home, url.Raw()))					// raw is "home"; should be "home/wiki/Main_Page"; DATE:2014-02-09
			return false;															// special case to handle "home" which should mean "home" in any wiki, but "home/wiki/Main_Page" in home wiki
		return !this.Has(url.Wiki_bry());
	}
	private void Init() {
		init_needed = false;
		Io_url[] wiki_dirs = Io_mgr.Instance.QueryDir_args(app.Fsys_mgr().Wiki_dir()).DirInclude_(true).Recur_(false).ExecAsUrlAry();
		int wiki_dirs_len = wiki_dirs.length;
		for (int i = 0; i < wiki_dirs_len; i++) {
			Io_url wiki_dir = wiki_dirs[i];
			byte[] domain_bry = Bry_.new_u8(wiki_dir.NameOnly());
			hash.Add(domain_bry, domain_bry);
		}
	}
	public static void Make_wiki_dir(Xoa_app app, String domain_str) {	// TEST: fake wiki_dir for Parse_from_url_bar; DATE:2014-02-16
		Io_url wiki_dir = app.Fsys_mgr().Wiki_dir();
		Io_mgr.Instance.CreateDir(wiki_dir.GenSubDir(domain_str));
	}
}
