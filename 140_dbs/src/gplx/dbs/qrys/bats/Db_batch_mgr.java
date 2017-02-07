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
package gplx.dbs.qrys.bats; import gplx.*; import gplx.dbs.*; import gplx.dbs.qrys.*;
public class Db_batch_mgr {
	public Db_batch_grp		Conn_bgn() {return conn_bgn;} private final    Db_batch_grp conn_bgn = new Db_batch_grp(Db_batch_grp.Tid__conn_bgn);
	public Db_batch_grp		Conn_end() {return conn_end;} private final    Db_batch_grp conn_end = new Db_batch_grp(Db_batch_grp.Tid__conn_end);
	public Db_batch_grp		Txn_end () {return txn_end;}  private final    Db_batch_grp txn_end  = new Db_batch_grp(Db_batch_grp.Tid__txn_end);
	public void Copy(String src_tid, Db_batch_mgr src) {
		conn_bgn.Copy(src.conn_bgn);
		conn_end.Copy(src.conn_end);
		txn_end.Copy(src.txn_end);
	}
}
