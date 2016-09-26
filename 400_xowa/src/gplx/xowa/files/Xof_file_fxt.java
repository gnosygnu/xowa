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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*;
import gplx.xowa.files.origs.*; import gplx.xowa.files.repos.*;
public class Xof_file_fxt {
	private final    Xowe_wiki wiki;
	Xof_file_fxt(Xowe_wiki wiki) {
		this.wiki = wiki;
		Io_mgr.Instance.InitEngine_mem();
		Db_conn_bldr.Instance.Reg_default_mem();
		wiki.File_mgr().Version_2_y_();
	}
	public static Xof_file_fxt new_(Xowe_wiki wiki) {return new Xof_file_fxt(wiki);}
	public static Xof_file_fxt new_all(Xowe_wiki wiki) {return new Xof_file_fxt(wiki).Init_repos().Init_cache().Init_orig();}
	public Xof_file_fxt Init_repos() {
		Xow_repo_mgr_.Assert_repos(wiki.Appe(), wiki);
		return this;
	}
	public Xof_file_fxt Init_cache() {
		wiki.App().User().User_db_mgr().Init_by_app(Bool_.N, wiki.App().Fsys_mgr().Root_dir().GenSubFil_nest("user", "xowa.user.anonymous.sqlite3"));
		return this;
	}
	public Xof_file_fxt Init_orig() {
		Db_conn conn = Db_conn_bldr.Instance.Get_or_new(Io_url_.mem_fil_("mem/xowa/wiki/" + wiki.Domain_str() + "/orig.xowa")).Conn();
		Xof_orig_tbl orig_tbl = new Xof_orig_tbl(conn, Bool_.Y);
		orig_tbl.Create_tbl();
		wiki.File_mgr().Orig_mgr().Init_by_wiki(wiki, Xof_fsdb_mode.New__v2__gui(), new Xof_orig_tbl[] {orig_tbl}, Xof_url_bldr.new_v2());
		return this;
	}
	public void Exec_orig_add(boolean repo_is_commons, String orig_ttl, int orig_ext_id, int orig_w, int orig_h, String orig_redirect) {
		byte repo_tid = repo_is_commons ? Xof_repo_tid_.Tid__remote : Xof_repo_tid_.Tid__local;
		wiki.File_mgr().Orig_mgr().Insert(repo_tid, Bry_.new_u8(orig_ttl), orig_ext_id, orig_w, orig_h, Bry_.new_u8(orig_redirect));
	}
}
