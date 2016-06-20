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
package gplx.xowa.bldrs.cmds.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.core.ios.*; import gplx.xowa.wikis.tdbs.*;
import gplx.xowa.bldrs.wkrs.*;
public class Xoctg_hiddencat_ttl_wkr extends Xob_itm_dump_base implements Xob_cmd, Gfo_invk {
	public Xoctg_hiddencat_ttl_wkr(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki); this.make_fil_len = Io_mgr.Len_mb;} private Xob_sql_join_wkr_ctg_hidden join_wkr;
	public String Cmd_key() {return Xob_cmd_keys.Key_tdb_cat_hidden_ttl;}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		this.Init_dump(Xob_cmd_keys.Key_tdb_cat_hidden_ttl);
		src_sql_dir = wiki.Fsys_mgr().Tmp_dir().GenSubDir_nest(Xob_cmd_keys.Key_tdb_cat_hidden_sql, "make");
		join_wkr = new Xob_sql_join_wkr_ctg_hidden(bldr.App(), wiki, temp_dir, src_sql_dir);
	}	Io_url src_sql_dir;
	public void Cmd_run() {
		Xob_sql_join_mgr.Exec_join(join_wkr);		
	}
	public void Cmd_end() {
		join_wkr.Flush();
		Io_url_gen make_url_gen = Io_url_gen_.dir_(temp_dir.GenSubDir("make"));
		Xobdc_merger.Basic(bldr.Usr_dlg(), join_wkr.Dump_url_gen(), temp_dir.GenSubDir("sort"), sort_mem_len, Io_sort_split_itm_sorter.Instance, Io_line_rdr_key_gen_.first_pipe, new Io_sort_fil_basic(bldr.Usr_dlg(), make_url_gen, make_fil_len));
		if (delete_temp) Io_mgr.Instance.DeleteDirDeep(src_sql_dir);
	}
	public void Cmd_term() {}
}
class Xob_sql_join_wkr_ctg_hidden implements Xob_sql_join_wkr {
	public Xob_sql_join_wkr_ctg_hidden(Xoae_app app, Xowe_wiki wiki, Io_url temp_dir, Io_url src_sql_dir) {
		this.app = app; this.wiki = wiki;
		this.dump_url_gen = Io_url_gen_.dir_(temp_dir.GenSubDir("dump"));
		this.src_sql_dir = src_sql_dir;
	}	private Xoae_app app = null; Xowe_wiki wiki = null; Io_url src_sql_dir;
	public Io_url_gen Dump_url_gen() {return dump_url_gen;} Io_url_gen dump_url_gen;
	public Io_line_rdr New_main_rdr() {
		Io_url[] urls = Io_mgr.Instance.QueryDir_fils(src_sql_dir);
		return new Io_line_rdr(app.Usr_dlg(), urls).Key_gen_(Io_line_rdr_key_gen_.first_pipe);
	} 
	public Io_line_rdr New_join_rdr() {
		Io_url make_dir = wiki.Tdb_fsys_mgr().Url_site_dir(Xotdb_dir_info_.Tid_id);
		app.Usr_dlg().Prog_many("", "", "getting id files: ~{0}", make_dir.Raw());
		Io_url[] urls = Io_mgr.Instance.QueryDir_args(make_dir).Recur_().FilPath_("*.xdat").ExecAsUrlAry();
		return new Io_line_rdr(app.Usr_dlg(), urls).Key_gen_(Io_line_rdr_key_gen_.first_pipe).File_skip_line0_(true);
	} 
	public void Process_match(Io_line_rdr main, Io_line_rdr join, byte[] key_bry) {
		byte[] src = join.Bfr();
		int itm_end = join.Itm_pos_end();
		int pipe_pos = Bry_find_.Find_bwd(src, Byte_ascii.Pipe, itm_end);
		if (pipe_pos == Bry_find_.Not_found) throw Err_.new_wo_type("failed to find pipe for name", "excerpt", String_.new_u8(src, join.Itm_pos_bgn(), join.Itm_pos_end()));
		file_bfr.Add_mid(src, pipe_pos + 1, itm_end - 1).Add_byte_pipe();
		file_bfr.Add(key_bry).Add_byte_nl();
	}	private Bry_bfr file_bfr = Bry_bfr_.New();
	public void Flush() {
		Io_mgr.Instance.SaveFilBfr(dump_url_gen.Nxt_url(), file_bfr);
	}
}
