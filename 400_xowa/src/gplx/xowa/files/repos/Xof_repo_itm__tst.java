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
import org.junit.*; import gplx.core.tests.*;
import gplx.core.envs.*;
import gplx.xowa.apps.fsys.*;
import gplx.xowa.files.exts.*;
public class Xof_repo_itm__tst {
	private final    Xof_repo_itm__fxt fxt = new Xof_repo_itm__fxt();
	@Test 	public void Gen_name_trg__long() {
		// make a windows repo with a long directory name
		Xof_repo_itm repo = fxt.Make__repo(Op_sys.Wnt.Os_name(), "C:\\xowa0123456789", "commons.wikimedia.org");

		// mark it as wnt repo and reduce max ttl_len from 160 to 99 for shorter test vars
		repo.Fsys_is_wnt_(true).Url_max_len_(99);

		// short: full name
		fxt.Test__gen_name_trg(repo, "A.png", "A.png");

		// long: truncated_ttl + md5
		fxt.Test__gen_name_trg(repo, "0123456789012345678901234567890123456789.png", "0123456789_965d037bf686058361510201be299ed4.png");

		// extremely long; md5 only
		repo.Root_str_("C:\\xowa012345678901234567890123456789");
		fxt.Test__gen_name_trg(repo, "0123456789012345678901234567890123456789.png", "965d037bf686058361510201be299ed4.png");
	}
}
class Xof_repo_itm__fxt {
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public Xof_repo_itm Make__repo(String plat_name, String root_dir_str, String wiki_domain) {
		String key = "test_repo";
		Io_url root_dir = Io_url_.new_dir_(root_dir_str);
		Xoa_fsys_mgr fsys_mgr = Xoa_fsys_mgr.New_by_plat(plat_name, root_dir);
		Xof_repo_itm repo = new Xof_repo_itm(Bry_.new_u8(key), fsys_mgr, new Xof_rule_mgr(), Bry_.new_u8(wiki_domain));
		repo.Root_str_(root_dir.Raw());
		return repo;
	}
	public void Test__gen_name_trg(Xof_repo_itm repo, String ttl_str, String expd) {
		byte[] ttl_bry = Bry_.new_u8(ttl_str);
		byte[] md5 = Xof_file_wkr_.Md5(ttl_bry);
		Gftest.Eq__str(expd, repo.Gen_name_trg(tmp_bfr, ttl_bry, md5, Xof_ext_.new_by_ttl_(ttl_bry)));
	}
}
