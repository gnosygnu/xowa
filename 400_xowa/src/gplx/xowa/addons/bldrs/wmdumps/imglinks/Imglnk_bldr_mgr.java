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
package gplx.xowa.addons.bldrs.wmdumps.imglinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.wmdumps.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*;
import gplx.xowa.files.repos.*;
class Imglnk_bldr_mgr {
	private final    Db_conn conn;
	private final    Xowe_wiki wiki;
	public Imglnk_tmp_tbl Tmp_tbl() {return tmp_tbl;} private Imglnk_tmp_tbl tmp_tbl;
	public Imglnk_bldr_mgr(Xowe_wiki wiki) {
		wiki.Init_assert();
		this.wiki = wiki;
		this.conn = Xob_db_file.New__img_link(wiki).Conn();
		this.tmp_tbl = new Imglnk_tmp_tbl(conn);
		conn.Meta_tbl_remake(tmp_tbl);
		tmp_tbl.Create_tbl();
		tmp_tbl.Insert_bgn();
	}
	public void On_cmd_end() {
		// finalize txn; create idx
		tmp_tbl.Insert_end();
		tmp_tbl.Create_idx__img_ttl();

		// create reg_tbl
		Imglnk_reg_tbl reg_tbl = new Imglnk_reg_tbl(conn);
		conn.Meta_tbl_remake(reg_tbl);
		reg_tbl.Create_idx__src_ttl();
		reg_tbl.Insert(conn, Xof_repo_tid_.Tid__local , wiki);
		reg_tbl.Insert(conn, Xof_repo_tid_.Tid__remote, wiki.Appe().Wiki_mgr().Wiki_commons());
		reg_tbl.Create_idx__trg_ttl();

//			Imglnk_bulk_cmd__img_id.Bulk_exec(conn, Bool_.Y, wiki);
//			Imglnk_bulk_cmd__img_id.Bulk_exec(conn, Bool_.N, wiki.Appe().Wiki_mgr().Wiki_commons());
	}
}
