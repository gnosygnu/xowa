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
package gplx.xowa.apps.fsys; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.xowa.apps.gfs.*;
import gplx.xowa.wikis.domains.*;
public class Xoa_fsys_mgr implements Gfo_invk {
	public Xoa_fsys_mgr(String plat_name, Io_url root_dir, Io_url wiki_dir, Io_url file_dir, Io_url css_dir, Io_url http_root) {
		this.root_dir = root_dir; this.wiki_dir = wiki_dir; this.file_dir = file_dir; this.css_dir = css_dir; this.http_root = http_root;
		this.bin_plat_dir			= root_dir.GenSubDir("bin").GenSubDir(plat_name);
		this.bin_any_dir			= root_dir.GenSubDir("bin").GenSubDir("any");
		this.bin_xowa_dir			= bin_any_dir.GenSubDir("xowa");
		this.bin_xowa_file_dir		= bin_xowa_dir.GenSubDir_nest("file");
		this.bin_xtns_dir			= bin_xowa_dir.GenSubDir_nest("xtns");
		this.bin_addon_dir			= bin_xowa_dir.GenSubDir_nest("addon");
		this.cfg_app_fil			= bin_xowa_dir.GenSubFil_nest("cfg", "app", "xowa.gfs");
		this.cfg_lang_core_dir		= bin_xowa_dir.GenSubDir_nest("cfg", "lang", "core");
		this.cfg_wiki_core_dir		= bin_xowa_dir.GenSubDir_nest("cfg", "wiki", "core");
		this.cfg_site_meta_fil		= bin_xowa_dir.GenSubFil_nest("cfg", "wiki", "site_meta.sqlite3");
		this.home_wiki_dir			= bin_xowa_dir.GenSubDir_nest("wiki", Xow_domain_itm_.Str__home);
		this.url_finder             = new Xoa_url_finder(this);
	}
	public Io_url Root_dir()					{return root_dir;} private final    Io_url root_dir;					// EX: /xowa/
	public Io_url Wiki_dir()					{return wiki_dir;} private final    Io_url wiki_dir;					// EX: /xowa/wiki/
	public Io_url File_dir()					{return file_dir;} private final    Io_url file_dir;					// EX: /xowa/file/
	public Io_url Css_dir()						{return css_dir;} private final    Io_url css_dir;						// EX: /xowa/user/anonymous/wiki/
	public Io_url Bin_plat_dir()				{return bin_plat_dir;} private final    Io_url bin_plat_dir;			// EX: /xowa/bin/lnx_64/
	public Io_url Bin_any_dir()					{return bin_any_dir;} private final    Io_url bin_any_dir;				// EX: /xowa/bin/any
	public Io_url Bin_xowa_dir()				{return bin_xowa_dir;} private final    Io_url bin_xowa_dir;			// EX: /xowa/bin/any/xowa
	public Io_url Bin_xowa_file_dir()			{return bin_xowa_file_dir;} private final    Io_url bin_xowa_file_dir;
	public Io_url Bin_xtns_dir()				{return bin_xtns_dir;} private final    Io_url bin_xtns_dir;
	public Io_url Bin_addon_dir()				{return bin_addon_dir;} private final    Io_url bin_addon_dir;
	public Io_url Cfg_lang_core_dir()			{return cfg_lang_core_dir;} private final    Io_url cfg_lang_core_dir;
	public Io_url Cfg_wiki_core_dir()			{return cfg_wiki_core_dir;} private final    Io_url cfg_wiki_core_dir;
	public Io_url Cfg_site_meta_fil()			{return cfg_site_meta_fil;} private final    Io_url cfg_site_meta_fil;
	public Io_url Wiki_css_dir(String wiki)		{return css_dir.GenSubDir_nest(wiki, "html");}							// EX: /xowa/temp/simple.wikipedia.org/html/xowa_common.css
	public Io_url Wiki_file_dir(String wiki)	{return file_dir.GenSubDir_nest(wiki);}									// EX: /xowa/temp/simple.wikipedia.org/orig/
	public Io_url Home_wiki_dir()				{return home_wiki_dir;} private final    Io_url home_wiki_dir;
	public Io_url Cfg_app_fil()					{return cfg_app_fil;} private final    Io_url cfg_app_fil;
	public Io_url Http_root()					{return http_root;} private final    Io_url http_root;					// EX: file:///xowa/ or file:///android_asset/xowa/
	public Xoa_url_finder Url_finder()          {return url_finder;} private final    Xoa_url_finder url_finder;
	public void Init_by_app(Gfo_invk app_mgr_invk) {this.app_mgr_invk = app_mgr_invk;} private Gfo_invk app_mgr_invk;	// for gfs and app.launcher
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_apps))					return app_mgr_invk;
		else if	(ctx.Match(k, Invk_root_dir))				return root_dir;
		else	return Gfo_invk_.Rv_unhandled;
	}
	private static final    String Invk_apps = "apps", Invk_root_dir = "root_dir";
	public static Xoa_fsys_mgr New_by_plat(String plat_name, Io_url root_dir) { // TEST:
		return new Xoa_fsys_mgr(plat_name, root_dir, root_dir.GenSubDir("wiki"), root_dir.GenSubDir("file"), root_dir.GenSubDir("css"), root_dir.GenSubDir("html"));
	}
}
