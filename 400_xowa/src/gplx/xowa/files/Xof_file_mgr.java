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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*;
import gplx.xowa.files.commons.*; import gplx.xowa.files.exts.*; import gplx.xowa.files.caches.*; import gplx.xowa.files.imgs.*;
import gplx.xowa.bldrs.wms.*;
import gplx.xowa.xtns.math.*;
public class Xof_file_mgr implements Gfo_invk {
	public Xoa_repo_mgr			Repo_mgr() {return repo_mgr;} private Xoa_repo_mgr repo_mgr;
	public Xof_img_mgr			Img_mgr() {return img_mgr;} private final    Xof_img_mgr img_mgr = new Xof_img_mgr();
	public Xof_cache_mgr		Cache_mgr() {return cache_mgr;} private Xof_cache_mgr cache_mgr;
	public Xof_rule_mgr			Ext_rules() {return ext_rules;} private final    Xof_rule_mgr ext_rules = new Xof_rule_mgr();
	public void Ctor_by_app(Xoae_app app) {
		Gfo_usr_dlg usr_dlg = app.Usr_dlg();
		this.cache_mgr = new Xof_cache_mgr(usr_dlg, app.Wiki_mgr(), repo_mgr);
		this.repo_mgr = new Xoa_repo_mgr(app.Fsys_mgr(), ext_rules); 
		img_mgr.Init_by_app(app.Wmf_mgr(), app.Prog_mgr());
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_repos))				return repo_mgr;
		else if	(ctx.Match(k, Invk_img_mgr))			return img_mgr;
		else if	(ctx.Match(k, Invk_ext_rules))			return ext_rules;
		else if	(ctx.Match(k, Invk_cache_mgr))			return cache_mgr;
		else											return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_repos = "repos", Invk_img_mgr= "img_mgr", Invk_ext_rules = "ext_rules", Invk_cache_mgr = "cache_mgr";
}
