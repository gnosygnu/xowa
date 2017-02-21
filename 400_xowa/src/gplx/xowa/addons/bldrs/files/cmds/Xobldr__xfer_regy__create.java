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
import gplx.dbs.*; import gplx.xowa.addons.bldrs.files.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;	
public class Xobldr__xfer_regy__create extends Xob_cmd__base {
	public Xobldr__xfer_regy__create(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		Db_conn conn = Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		conn.Txn_bgn("bldr__xfer_regy");
		Xob_xfer_regy_tbl.Create_table(conn);
		Xob_xfer_regy_tbl.Create_data(usr_dlg, conn);
		Xob_xfer_regy_tbl.Create_index(usr_dlg, conn);
		Xob_xfer_regy_log_tbl.Create_table(conn);
		conn.Txn_end();
	}

	public static final String BLDR_CMD_KEY = "file.xfer_regy";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__xfer_regy__create(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__xfer_regy__create(bldr, wiki);}
}
