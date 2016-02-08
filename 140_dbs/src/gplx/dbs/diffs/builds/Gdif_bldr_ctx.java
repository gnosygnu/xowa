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
package gplx.dbs.diffs.builds; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
import gplx.dbs.diffs.itms.*;
public class Gdif_bldr_ctx {
	public Gdif_bldr_ctx() {}
	public Gdif_core Core;
	public Gdif_job_itm Cur_job;
	public Gdif_cmd_itm Cur_cmd;
	public Gdif_txn_itm Cur_txn;
	public int Cur_cmd_count;
	public int Cur_txn_count;
	public Gdif_bldr_ctx Init(Gdif_core core, Gdif_job_itm cur_job) {
		this.Core = core; this.Cur_job = cur_job;
		return this;
	}
	public void Clear() {
		Cur_cmd_count = 0; Cur_txn_count = 0;
		Cur_job = null;
		Cur_cmd = null;
		Cur_txn = null;
	}
}
