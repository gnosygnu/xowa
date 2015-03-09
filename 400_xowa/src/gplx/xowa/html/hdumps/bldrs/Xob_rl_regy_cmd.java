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
package gplx.xowa.html.hdumps.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*;
import gplx.dbs.*; import gplx.xowa.bldrs.*; import gplx.xowa.html.hdumps.data.*;
import gplx.xowa.wikis.data.*;
public class Xob_rl_regy_cmd extends Xob_itm_basic_base implements Xob_cmd {
	private Rl_dump_tbl link_dump_tbl; private Xohd_page_html_tbl html_dump_tbl;
	private int commit_interval = 10000, commit_count = 0;
	public Xob_rl_regy_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return Cmd_key_const;} public static final String Cmd_key_const = "hdump.redlinks";
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		this.link_dump_tbl = Rl_dump_tbl.Get_or_new(wiki);
		this.html_dump_tbl = Xohd_page_html_tbl.Get_from_db__root(wiki);
	}
	public void Cmd_run() {
		Db_rdr rdr = link_dump_tbl.Select_missing();
		int cur_page_id = -1; Bry_bfr bfr = Bry_bfr.reset_(255);
		html_dump_tbl.Conn().Txn_bgn();
		while (rdr.Move_next()) {
			int src_page_id = rdr.Read_int(Rl_dump_tbl.Fld_src_page_id);
			if (cur_page_id != src_page_id) {
				if (cur_page_id != -1) Commit(cur_page_id, bfr);
				cur_page_id = src_page_id;
			}
			bfr.Add_int_variable(rdr.Read_int(Rl_dump_tbl.Fld_src_html_uid)).Add_byte_pipe();
		}
		Commit(cur_page_id, bfr);
		html_dump_tbl.Conn().Txn_end();
	}
	private void Commit(int cur_page_id, Bry_bfr bfr) {
		html_dump_tbl.Insert(cur_page_id, Xohd_page_html_row.Tid_redlink, bfr.Xto_bry_and_clear());
		++commit_count;
		if ((commit_count % commit_interval ) == 0)
			html_dump_tbl.Conn().Txn_commit();
	}
	public void Cmd_end() {
		html_dump_tbl.Conn().Conn_term();
		link_dump_tbl.Conn().Conn_term();
	}
	public void Cmd_print() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_commit_interval_))		commit_interval = m.ReadInt("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_commit_interval_ = "commit_interval_";			
}
