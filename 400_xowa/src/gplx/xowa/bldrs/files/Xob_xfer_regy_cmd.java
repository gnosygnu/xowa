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
package gplx.xowa.bldrs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.files.*; import gplx.xowa.bldrs.oimgs.*;
public class Xob_xfer_regy_cmd extends Xob_itm_basic_base implements Xob_cmd {
	public Xob_xfer_regy_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return KEY_oimg;} public static final String KEY_oimg = "file.xfer_regy";
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run() {
		Db_conn conn = Xodb_db_file.init__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		Xob_xfer_regy_tbl.Create_table(conn);
		Xob_xfer_regy_tbl.Create_data(usr_dlg, conn);
		Xob_xfer_regy_tbl.Create_index(usr_dlg, conn);
		Xob_xfer_regy_log_tbl.Create_table(conn);
		conn.Txn_mgr().Txn_end_all();
	}
	public void Cmd_end() {}
	public void Cmd_print() {}
}
