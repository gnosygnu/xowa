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
package gplx.xowa.bldrs.cmds.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.core.stores.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.wikis.dbs.*; import gplx.xowa.files.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.origs.*;
import gplx.xowa.bldrs.wkrs.*;
public class Xob_orig_regy_update_cmd extends Xob_itm_basic_base implements Xob_cmd {	// downloads latest orig data
	public Xob_orig_regy_update_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return KEY_oimg;} public static final String KEY_oimg = "oimg.orig_qry";
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
//			Xof_orig_mgr qry_mgr = new Xof_orig_mgr();
		Db_conn conn = Xob_db_file.new__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		Xob_orig_regy_update_bmk_mgr bmk = new Xob_orig_regy_update_bmk_mgr();
		bmk.Init(conn, this.Cmd_key(), true, false, true);
		bmk.Load();
//			Xof_fsdb_itm itm = new Xof_fsdb_itm();
		DataRdr rdr = DataRdr_.Null;
		try {
			// rdr = Select(conn, bmk.Repo_prv(), bmk.Ttl_prv());
			while (rdr.MoveNextPeer()) {
				// Load_itm(itm, rdr);
				// QueryItm
			}
		}
		finally {
			rdr.Rls();
		}
		/*		
		DataRdr rdr = Get(repo_prv, ttl_prv);
		while (rdr.MoveNextPeer()) {
			Itm itm = Itm.load_(rdr);
			qry.Call(itm);
		}
		*/
	}
	public DataRdr Select(Db_conn p, byte prv_repo_id, byte[] prv_ttl) {
		String sql = String_.Concat_lines_nl_skip_last
		(	"SELECT	lnki_ttl"
		,	"FROM	orig_regy"	
		,	"WHERE	lnki_repo >= '" + Byte_.To_str(prv_repo_id) + "'"
		,	"AND    lnki_ttl > '" + prv_ttl + "'"
		,	"AND	oimg_orig_page_id = -1;"
		);
		Db_qry select_qry = Db_qry_sql.rdr_(sql);
		return p.Exec_qry_as_rdr(select_qry);
	}
	public void Cmd_run() {}
	public void Cmd_end() {}
	public void Cmd_term() {}
}
