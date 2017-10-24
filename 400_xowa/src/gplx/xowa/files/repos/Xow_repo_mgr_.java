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
package gplx.xowa.files.repos; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.apps.fsys.*; import gplx.xowa.files.exts.*;
import gplx.xowa.wikis.domains.*;
public class Xow_repo_mgr_ {
	public static void Assert_repos(Xoae_app app, Xowe_wiki wiki) {
		synchronized (app) {	// LOCK:app-level; DATE:2016-07-07
			Xoa_repo_mgr repo_mgr = app.File_mgr().Repo_mgr(); 
			Xoa_fsys_mgr app_fsys_mgr = app.Fsys_mgr();
			Xof_rule_mgr ext_rule_mgr = app.File_mgr().Ext_rules();
			byte[] domain_bry = wiki.Domain_bry();
			Xof_repo_itm repo_itm = repo_mgr.Get_by(domain_bry);
			if (repo_itm == null) {	// no repo for wiki exists; create it;
				repo_itm = new Xof_repo_itm(domain_bry, app_fsys_mgr, ext_rule_mgr, domain_bry);
				repo_itm.Root_str_(wiki.Fsys_mgr().Root_dir().Raw());	// NOTE: needed for mass_parse; ordinarily called by xowa.gfs; DATE:2016-07-07
				repo_mgr.Add(repo_itm);
			}
			Xowe_repo_mgr pair_mgr = wiki.File_mgr().Repo_mgr();
			if (pair_mgr.Repos_len() == 0) {	// no pairs defined; add at least 1
				Xof_repo_itm repo_src = repo_mgr.Get_by(File_repo_xowa_null);
				if (repo_src == null) {
					repo_itm = new Xof_repo_itm(File_repo_xowa_null, app_fsys_mgr, ext_rule_mgr, Xow_domain_tid_.Bry__home);
					repo_mgr.Add(repo_itm);
				}
				pair_mgr.Add_repo(File_repo_xowa_null, domain_bry);

				// add commons; needed for mass_parse, else multiple "repo_mgr.invalid_repo" when common files exist in user_cache; DATE:2016-07-07
				Xof_repo_itm commons_repo = repo_mgr.Get_by(Xow_domain_itm_.Bry__commons);
				if (commons_repo == null) {
					commons_repo = new Xof_repo_itm(Xow_domain_itm_.Bry__commons, app_fsys_mgr, ext_rule_mgr, domain_bry);
					commons_repo.Root_str_(app.Fsys_mgr().Wiki_dir().GenSubDir(Xow_domain_itm_.Str__commons).Raw());	// NOTE: needed for mass_parse; ordinarily called by xowa.gfs; DATE:2016-07-07
					repo_mgr.Add(commons_repo);
				}
				pair_mgr.Add_repo(Xow_domain_itm_.Bry__commons, Xow_domain_itm_.Bry__commons);
			}
		}
	}
	private static byte[] File_repo_xowa_null = Bry_.new_a7("xowa_repo_null");
}
