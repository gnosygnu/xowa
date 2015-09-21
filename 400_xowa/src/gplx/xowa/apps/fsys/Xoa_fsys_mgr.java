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
package gplx.xowa.apps.fsys; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.xowa.apps.gfss.*;
import gplx.xowa.wikis.domains.*;
public class Xoa_fsys_mgr implements GfoInvkAble {
	public Xoa_fsys_mgr(String plat_name, Io_url root_dir, Io_url wiki_dir, Io_url file_dir, Io_url css_dir) {
		this.root_dir				= root_dir;
		this.wiki_dir 				= wiki_dir;
		this.file_dir				= file_dir;
		this.css_dir				= css_dir;
		this.bin_plat_dir			= root_dir.GenSubDir("bin").GenSubDir(plat_name);
		this.bin_any_dir			= root_dir.GenSubDir("bin").GenSubDir("any");
		this.bin_xowa_dir			= bin_any_dir.GenSubDir("xowa");
		this.bin_xowa_file_dir		= bin_xowa_dir.GenSubDir_nest("file");
		this.bin_xtns_dir			= bin_xowa_dir.GenSubDir_nest("xtns");
		this.cfg_app_fil			= bin_xowa_dir.GenSubFil_nest("cfg", "app", "xowa.gfs");
		this.cfg_lang_core_dir		= bin_xowa_dir.GenSubDir_nest("cfg", "lang", "core");
		this.cfg_wiki_core_dir		= bin_xowa_dir.GenSubDir_nest("cfg", "wiki", "core");
		this.cfg_site_meta_fil		= bin_xowa_dir.GenSubDir_nest("cfg", "wiki", "site_meta.sqlite3");
		this.home_wiki_dir			= bin_xowa_dir.GenSubDir_nest("wiki", Xow_domain_itm_.Str__home);
	}
	public Io_url Root_dir()					{return root_dir;} private final Io_url root_dir;
	public Io_url Wiki_dir()					{return wiki_dir;} private final Io_url wiki_dir;
	public Io_url File_dir()					{return file_dir;} private final Io_url file_dir;
	public Io_url Css_dir()						{return css_dir;} private final Io_url css_dir;
	public Io_url Bin_plat_dir()				{return bin_plat_dir;} private final Io_url bin_plat_dir;
	public Io_url Bin_any_dir()					{return bin_any_dir;} private final Io_url bin_any_dir;
	public Io_url Bin_xowa_dir()				{return bin_xowa_dir;} private final Io_url bin_xowa_dir;
	public Io_url Bin_xowa_file_dir()			{return bin_xowa_file_dir;} private final Io_url bin_xowa_file_dir;
	public Io_url Bin_xtns_dir()				{return bin_xtns_dir;} private final Io_url bin_xtns_dir;
	public Io_url Cfg_lang_core_dir()			{return cfg_lang_core_dir;} private final Io_url cfg_lang_core_dir;
	public Io_url Cfg_wiki_core_dir()			{return cfg_wiki_core_dir;} private final Io_url cfg_wiki_core_dir;
	public Io_url Cfg_site_meta_fil()			{return cfg_site_meta_fil;} private final Io_url cfg_site_meta_fil;
	public Io_url Bin_data_os_cfg_fil()			{return bin_plat_dir.GenSubFil_nest("xowa", "cfg", Xoa_gfs_mgr.Cfg_os);}
	public Io_url Wiki_css_dir(String wiki)		{return css_dir.GenSubDir_nest(wiki, "html");}	// EX: /xowa/temp/simple.wikipedia.org/html/xowa_common.css
	public Io_url Wiki_file_dir(String wiki)	{return file_dir.GenSubDir_nest(wiki);}			// EX: /xowa/temp/simple.wikipedia.org/orig/
	public Io_url Home_wiki_dir()				{return home_wiki_dir;} private final Io_url home_wiki_dir;
	public Io_url Cfg_app_fil()					{return cfg_app_fil;} private final Io_url cfg_app_fil;
	public void Init_by_app(GfoInvkAble app_mgr_invk) {this.app_mgr_invk = app_mgr_invk;} private GfoInvkAble app_mgr_invk; // for gfs and app.launcher
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_apps))					return app_mgr_invk;
		else if	(ctx.Match(k, Invk_root_dir))				return root_dir;
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_apps = "apps", Invk_root_dir = "root_dir";
}
