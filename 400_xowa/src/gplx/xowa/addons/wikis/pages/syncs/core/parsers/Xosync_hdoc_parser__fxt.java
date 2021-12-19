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
package gplx.xowa.addons.wikis.pages.syncs.core.parsers;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.xowa.*;
import gplx.xowa.htmls.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
public class Xosync_hdoc_parser__fxt {
	private final BryWtr tmp_bfr = BryWtr.New();
	private final Xoh_page hpg = new Xoh_page();
	private Xowe_wiki wiki;
	public void Init(boolean print_errors) {
		if (print_errors)
			Gfo_usr_dlg_.Instance = Gfo_usr_dlg_.Test_console();
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		Xoa_app_fxt.repo2_(app, wiki);
	}
	public void Term() {
		Gfo_usr_dlg_.Instance = Gfo_usr_dlg_.Noop;
	}
	public Xosync_hdoc_parser__fxt Exec__parse(String raw) {
		Xosync_hdoc_parser parser = new Xosync_hdoc_parser();		
		byte[] result = parser.Parse_hdoc(wiki.Domain_itm(), BryUtl.Empty, hpg.Hdump_mgr().Imgs(), BryUtl.NewU8(raw));
		hpg.Db().Html().Html_bry_(result);
		return this;
	}
	public Xosync_hdoc_parser__fxt Test__html(String expd) {
		GfoTstr.EqLines(expd, hpg.Db().Html().Html_bry(), "converted html");
		return this;
	}
	public Xof_fsdb_itm Make__fsdb(boolean repo_is_commons, boolean file_is_orig, String file_ttl, int file_w, double file_time, int file_page) {
		return Make__fsdb(repo_is_commons, file_is_orig, file_ttl, Xof_ext_.new_by_ttl_(BryUtl.NewU8(file_ttl)), file_w, file_time, file_page);
	}
	public Xof_fsdb_itm Make__fsdb(boolean repo_is_commons, boolean file_is_orig, String file_ttl, String file_ext, int file_w, double file_time, int file_page) {
		return Make__fsdb(repo_is_commons, file_is_orig, file_ttl, Xof_ext_.new_by_ext_(BryUtl.NewU8(file_ext)), file_w, file_time, file_page);
	}
	public Xof_fsdb_itm Make__fsdb(boolean repo_is_commons, boolean file_is_orig, String file_ttl, Xof_ext file_ext, int file_w, double file_time, int file_page) {
		Xof_fsdb_itm itm = new Xof_fsdb_itm();
		itm.Init_by_wm_parse(wiki.Domain_itm().Abrv_xo(), repo_is_commons, file_is_orig, BryUtl.NewU8(file_ttl), file_ext, file_w, file_time, file_page);
		return itm;
	}
	public Xosync_hdoc_parser__fxt Test__fsdb(Xof_fsdb_itm expd) {
		Xof_fsdb_itm actl = (Xof_fsdb_itm)hpg.Hdump_mgr().Imgs().GetAt(0);
		GfoTstr.Eq(To_str(tmp_bfr, expd), To_str(tmp_bfr, actl));
		return this;
	}
	public static String To_str(BryWtr tmp_bfr, Xof_fsdb_itm itm) {
		To_bfr(tmp_bfr, itm);
		return tmp_bfr.ToStrAndClear();
	}
	private static void To_bfr(BryWtr bfr, Xof_fsdb_itm itm) {
		bfr.AddStrA7(itm.Orig_repo_id() == Xof_repo_tid_.Tid__remote ? "remote" : "local").AddBytePipe();
		bfr.AddStrA7(itm.File_is_orig() ? "orig" : "thumb").AddBytePipe();
		bfr.Add(itm.Orig_ttl()).AddBytePipe();
		bfr.Add(itm.Orig_ext().Ext()).AddBytePipe();
		bfr.AddIntVariable(itm.File_w()).AddBytePipe();
		bfr.AddDouble(itm.Lnki_time()).AddBytePipe();
		bfr.AddIntVariable(itm.Lnki_page()).AddBytePipe();
	}
}
