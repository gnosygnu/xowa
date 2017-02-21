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
package gplx.xowa.addons.wikis.pages.syncs.core.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*; import gplx.xowa.addons.wikis.pages.syncs.core.*;
import gplx.core.tests.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
public class Xosync_hdoc_parser__fxt {
	private final    Xosync_update_mgr mgr = new Xosync_update_mgr();		
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    Xoh_page hpg = new Xoh_page();
	private Xowe_wiki wiki;
	public void Clear() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		Xoa_app_fxt.repo2_(app, wiki);
		mgr.Init_by_app(app);
		mgr.Init_by_page(wiki, hpg);
	}
	public Xosync_hdoc_parser__fxt Exec__parse(String raw) {
		mgr.Parse(hpg, wiki, Bry_.Empty, Bry_.new_u8(raw));
		return this;
	}
	public Xosync_hdoc_parser__fxt Test__html(String expd) {
		Gftest.Eq__ary__lines(expd, hpg.Db().Html().Html_bry(), "converted html");
		return this;
	}
	public Xof_fsdb_itm Make__fsdb(boolean repo_is_commons, boolean file_is_orig, String file_ttl, int file_w, double file_time, int file_page) {
		return Make__fsdb(repo_is_commons, file_is_orig, file_ttl, Xof_ext_.new_by_ttl_(Bry_.new_u8(file_ttl)), file_w, file_time, file_page);
	}
	public Xof_fsdb_itm Make__fsdb(boolean repo_is_commons, boolean file_is_orig, String file_ttl, String file_ext, int file_w, double file_time, int file_page) {
		return Make__fsdb(repo_is_commons, file_is_orig, file_ttl, Xof_ext_.new_by_ext_(Bry_.new_u8(file_ext)), file_w, file_time, file_page);
	}
	public Xof_fsdb_itm Make__fsdb(boolean repo_is_commons, boolean file_is_orig, String file_ttl, Xof_ext file_ext, int file_w, double file_time, int file_page) {
		Xof_fsdb_itm itm = new Xof_fsdb_itm();
		itm.Init_by_wm_parse(wiki.Domain_itm().Abrv_xo(), repo_is_commons, file_is_orig, Bry_.new_u8(file_ttl), file_ext, file_w, file_time, file_page);
		return itm;
	}
	public Xosync_hdoc_parser__fxt Test__fsdb(Xof_fsdb_itm expd) {
		Xof_fsdb_itm actl = (Xof_fsdb_itm)hpg.Hdump_mgr().Imgs().Get_at(0);
		Gftest.Eq__str(To_str(tmp_bfr, expd), To_str(tmp_bfr, actl));
		return this;
	}
	public static String To_str(Bry_bfr tmp_bfr, Xof_fsdb_itm itm) {
		To_bfr(tmp_bfr, itm);
		return tmp_bfr.To_str_and_clear();
	}
	private static void To_bfr(Bry_bfr bfr, Xof_fsdb_itm itm) {
		bfr.Add_str_a7(itm.Orig_repo_id() == Xof_repo_tid_.Tid__remote ? "remote" : "local").Add_byte_pipe();
		bfr.Add_str_a7(itm.File_is_orig() ? "orig" : "thumb").Add_byte_pipe();
		bfr.Add(itm.Orig_ttl()).Add_byte_pipe();
		bfr.Add(itm.Orig_ext().Ext()).Add_byte_pipe();
		bfr.Add_int_variable(itm.File_w()).Add_byte_pipe();
		bfr.Add_double(itm.Lnki_time()).Add_byte_pipe();
		bfr.Add_int_variable(itm.Lnki_page()).Add_byte_pipe();
	}
}
