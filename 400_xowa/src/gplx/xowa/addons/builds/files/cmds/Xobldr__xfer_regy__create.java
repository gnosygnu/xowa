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
package gplx.xowa.addons.builds.files.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*; import gplx.xowa.addons.builds.files.*;
import gplx.dbs.*; import gplx.xowa.addons.builds.files.dbs.*;
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
	@Override public Xob_cmd Cmd_new(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__xfer_regy__create(bldr, wiki);}
}
