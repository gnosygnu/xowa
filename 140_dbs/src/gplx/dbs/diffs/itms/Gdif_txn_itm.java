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
package gplx.dbs.diffs.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
public class Gdif_txn_itm {
	public Gdif_txn_itm(int job_id, int txn_id, int cmd_id, int owner_txn) {
		this.Job_id = job_id; this.Txn_id = txn_id; this.Cmd_id = cmd_id; this.Owner_txn = owner_txn;
	}
	public final int Job_id;
	public final int Txn_id;
	public final int Cmd_id;
	public final int Owner_txn;

	public static final int Owner_txn__null = 0;
}
