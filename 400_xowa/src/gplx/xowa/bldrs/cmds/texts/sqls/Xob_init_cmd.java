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
package gplx.xowa.bldrs.cmds.texts.sqls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.xowa.bldrs.*; import gplx.xowa.apps.apis.xowa.bldrs.imports.*;
import gplx.xowa.xtns.wbases.imports.*;
public class Xob_init_cmd extends Xob_init_base {
	public Xob_init_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Ctor(bldr, wiki);}
	@Override public String Cmd_key() {return Xob_cmd_keys.Key_text_init;}
	@Override public void Cmd_ini_wdata(Xob_bldr bldr, Xowe_wiki wiki) {
		bldr.Cmd_mgr().Add_cmd(wiki, Xob_cmd_keys.Key_wbase_qid);
		bldr.Cmd_mgr().Add_cmd(wiki, Xob_cmd_keys.Key_wbase_pid);
	}
	@Override public void Cmd_init(Xob_bldr bldr) {
		super.Cmd_init(bldr);
//			gplx.dbs.qrys.bats.Db_batch__journal_wal.Batch__init(gplx.dbs.Db_conn_pool.Instance.Batch_mgr());
	}

	@Override public void Cmd_run_end(Xowe_wiki wiki) {
		if (gplx.xowa.wikis.data.Xow_db_file__core_.Find_core_fil_or_null(wiki) != null)
			throw wiki.Appe().Bldr().Usr_dlg().Fail_many("", "", "directory must not contain any .xowa or .sqlite3 files: dir=~{0}", wiki.Fsys_mgr().Root_dir().Raw());
		Xowe_wiki_.Create(wiki, wiki.Import_cfg().Src_rdr_len(), wiki.Import_cfg().Src_fil().NameOnly());
	}
	@Override public void Cmd_term() {
		super.Cmd_term();
//			gplx.dbs.qrys.bats.Db_batch__journal_wal.Batch__term(gplx.dbs.Db_conn_pool.Instance.Batch_mgr());
//			gplx.dbs.Db_conn_pool.Instance.Rls_all();
	}
}
