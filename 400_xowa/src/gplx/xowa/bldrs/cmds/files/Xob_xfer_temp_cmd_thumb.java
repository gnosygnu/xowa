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
import gplx.dbs.*; import gplx.xowa.files.*;
import gplx.xowa.bldrs.wkrs.*;
public class Xob_xfer_temp_cmd_thumb extends Xob_itm_basic_base implements Xob_cmd {
	public Xob_xfer_temp_cmd_thumb(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return Xob_cmd_keys.Key_file_xfer_temp_thumb;}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		Db_conn conn = Xob_db_file.new__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		Xob_xfer_temp_tbl.Create_table(conn);
		Db_stmt trg_stmt = Xob_xfer_temp_tbl.Insert_stmt(conn);
		conn.Txn_bgn("bldr__xfer_temp_thumb");
		DataRdr rdr = conn.Exec_sql_as_old_rdr(Sql_select_itm);
		Xob_xfer_temp_itm temp_itm = new Xob_xfer_temp_itm();
		Xof_img_size img_size = new Xof_img_size();
		byte[] cur_ttl = Bry_.Empty; byte cur_repo = Byte_.Max_value_127;
		while (rdr.MoveNextPeer()) {
			temp_itm.Clear();
			temp_itm.Load(rdr);
			if (Bry_.Eq(cur_ttl, temp_itm.Orig_file_ttl())) {	// same ttl; DATE:2015-03-22
				if (temp_itm.Orig_repo() != cur_repo)			// if repo is different, ignore 2nd; handles images in both repos; take 1st only (which should be local)
					continue;
			}
			else {												// new ttl; update ttl, repo
				cur_ttl = temp_itm.Orig_file_ttl();
				cur_repo = temp_itm.Orig_repo();
			}
			if (temp_itm.Chk(img_size))
				temp_itm.Insert(trg_stmt, img_size);
		}
		conn.Txn_end();
	}
	public void Cmd_run() {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	private static final String
		Sql_select_itm = String_.Concat_lines_nl
	(	"SELECT  l.lnki_id"
	,	",       l.lnki_tier_id"
	,	",       l.lnki_page_id"
	,	",       l.lnki_ext"
	,	",       l.lnki_type"
	,	",       l.lnki_src_tid"
	,	",       l.lnki_w"
	,	",       l.lnki_h"
	,	",       l.lnki_upright"
	,	",       l.lnki_time"
	,	",       l.lnki_page"
	,	",       l.lnki_count"
	,	",       o.orig_repo"
	,	",       o.orig_page_id"
	,	",       o.orig_file_ttl"
	,	",       o.orig_file_ext"
	,	",       o.orig_file_id"
	,	",       o.lnki_ttl"
	,	",       o.orig_w"
	,	",       o.orig_h"
	,	",       o.orig_media_type"
	,	",       o.orig_minor_mime"
	,	"FROM    lnki_regy l"
	,	"        JOIN orig_regy o ON o.lnki_ttl = l.lnki_ttl"
	,	"WHERE   o.orig_file_ttl IS NOT NULL"
	,	"ORDER BY o.orig_file_ttl, o.orig_repo DESC, l.lnki_w DESC"	// NOTE: local=1,common=0; DATE:2015-03-22
	);
}
