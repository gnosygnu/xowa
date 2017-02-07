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
package gplx.xowa.addons.bldrs.files.cksums.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*; import gplx.xowa.addons.bldrs.files.cksums.*;
import gplx.dbs.*; import gplx.fsdb.meta.*;
public class Xocksum_cksum_db {
	public Xocksum_cksum_db(Db_conn conn) {
		this.conn = conn;
		this.tbl__cksum = new Xocksum_cksum_tbl(conn);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public Xocksum_cksum_tbl Tbl__cksum() {return tbl__cksum;} private final    Xocksum_cksum_tbl tbl__cksum;

	public static Xocksum_cksum_db Get(Xowe_wiki wiki) {
		return new Xocksum_cksum_db(wiki.File__fsdb_core().File__abc_file__at(Fsm_mnt_mgr.Mnt_idx_main).Conn());
	}
}
