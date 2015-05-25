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
package gplx.xowa.files.repos; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.apps.fsys.*; import gplx.xowa.files.exts.*; import gplx.xowa.wikis.*;
public class Xow_repo_mgr_ {
	public static void Assert_repos(Xoae_app app, Xowe_wiki wiki) {
		Xoa_repo_mgr repo_mgr = app.File_mgr().Repo_mgr(); 
		Xoa_fsys_mgr app_fsys_mgr = app.Fsys_mgr();
		Xof_rule_mgr ext_rule_mgr = app.File_mgr().Ext_rules();
		byte[] domain_bry = wiki.Domain_bry();
		Xof_repo_itm repo_itm = repo_mgr.Get_by(domain_bry);
		if (repo_itm == null) {	// no repo for wiki exists; create it;
			repo_itm = new Xof_repo_itm(domain_bry, app_fsys_mgr, ext_rule_mgr, domain_bry);
			repo_mgr.Add(repo_itm);
		}
		Xowe_repo_mgr pair_mgr = wiki.File_mgr().Repo_mgr();
		if (pair_mgr.Repos_len() == 0) {	// no pairs defined; add at least 1
			Xof_repo_itm repo_src = repo_mgr.Get_by(File_repo_xowa_null);
			if (repo_src == null) {
				repo_itm = new Xof_repo_itm(File_repo_xowa_null, app_fsys_mgr, ext_rule_mgr, Xow_domain_type_.Key_bry_home);
				repo_mgr.Add(repo_itm);
			}
			pair_mgr.Add_repo(File_repo_xowa_null, domain_bry);
		}
	}
	private static byte[] File_repo_xowa_null = Bry_.new_a7("xowa_repo_null");
}
