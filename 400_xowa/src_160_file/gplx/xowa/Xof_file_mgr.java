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
package gplx.xowa; import gplx.*;
import gplx.xowa.xtns.math.*;
import gplx.xowa.files.fsdb.caches.*;
import gplx.dbs.*; import gplx.xowa2.files.commons.*;
public class Xof_file_mgr implements GfoInvkAble {
	public void Init_app(Xoa_app app, Gfo_usr_dlg usr_dlg) {
		this.app = app;
		img_mgr.Init_app(app); repo_mgr = new Xoa_repo_mgr(app); 
		math_mgr = new Xof_math_mgr(app);
		math_mgr.Init(app);
		download_mgr = new Xoaf_download_mgr(app);
		cache_mgr = new Cache_mgr(app);
	}
	public Xoa_app App() {return app;} private Xoa_app app;
	public Xoa_repo_mgr	Repo_mgr() {return repo_mgr;} private Xoa_repo_mgr repo_mgr;
	public Xof_img_mgr Img_mgr() {return img_mgr;} private Xof_img_mgr img_mgr = new Xof_img_mgr();
	public Xof_math_mgr Math_mgr() {return math_mgr;} private Xof_math_mgr math_mgr;
	public Xoft_rule_mgr Ext_rules() {return ext_rules;} private Xoft_rule_mgr ext_rules = new Xoft_rule_mgr();
	public Xoaf_download_mgr Download_mgr() {return download_mgr;} private Xoaf_download_mgr download_mgr;
	public Cache_mgr Cache_mgr() {return cache_mgr;} private Cache_mgr cache_mgr;
	public void Init_by_app() {
		if (!Env_.Mode_testing())
			cache_mgr.Db_init(app.User().Db_mgr());
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_repos))				return repo_mgr;
		else if	(ctx.Match(k, Invk_img_mgr))			return img_mgr;
		else if	(ctx.Match(k, Invk_ext_rules))			return ext_rules;
		else if	(ctx.Match(k, Invk_math))				return math_mgr;
		else if	(ctx.Match(k, Invk_download))			return download_mgr;
		else if	(ctx.Match(k, Invk_cache_mgr))			return cache_mgr;
		else											return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_repos = "repos", Invk_img_mgr= "img_mgr", Invk_ext_rules = "ext_rules", Invk_math = "math", Invk_download = "download", Invk_cache_mgr = "cache_mgr";
}
