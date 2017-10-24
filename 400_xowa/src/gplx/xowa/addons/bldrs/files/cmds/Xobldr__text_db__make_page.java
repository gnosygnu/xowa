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
package gplx.xowa.addons.bldrs.files.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.wikis.data.*; import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.wikis.dbs.*;
import gplx.xowa.addons.bldrs.files.dbs.*;
public class Xobldr__text_db__make_page extends Xob_cmd__base {
	public Xobldr__text_db__make_page(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		wiki.Init_assert();
		Xow_db_mgr db_mgr = wiki.Data__core_mgr();
		Io_url page_db_url = db_mgr.Db__core().Url();
		int len = db_mgr.Dbs__len();
		for (int i = 0; i < len; i++) {
			Xow_db_file db_file = db_mgr.Dbs__get_at(i);
			switch (db_file.Tid()) {
				case Xow_db_file_.Tid__wiki_solo:
				case Xow_db_file_.Tid__text_solo:
				case Xow_db_file_.Tid__text:
					Xob_page_dump_tbl tbl = new Xob_page_dump_tbl(db_file.Conn());
					tbl.Create_data(page_db_url, db_file.Id());
					break;
			}
		}
	}

	public static final String BLDR_CMD_KEY = "wiki.page_dump.make";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__text_db__make_page(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__text_db__make_page(bldr, wiki);}
}
