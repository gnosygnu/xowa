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
import org.junit.*;
import gplx.fsdb.*;
public class Xob_diff_regy_exec_cmd_tst {
	private Xob_diff_regy_exec_cmd_fxt fxt = new Xob_diff_regy_exec_cmd_fxt();
	@Test   public void Xto_commons() {
		fxt.Test_build_url("enwiki", 1, "atr", "enwiki-001-atr.sql");
		fxt.Test_parse_url("/file/enwiki-001-atr.sql", "enwiki", 1, Fsdb_db_tid_.Tid_atr);
	}
//		@Test  public void Smoke() {
//			Xoae_app app = Xoa_app_fxt.app_(Io_url_.new_dir_("C:\\xowa\\"), "wnt");
//			Xowe_wiki wiki = Xoa_app_fxt.wiki_tst_(app);
//			Xob_diff_regy_make_cmd cmd = new Xob_diff_regy_make_cmd(app.Bldr(), wiki);
//			cmd.Cmd_run();
//		}
}
class Xob_diff_regy_exec_cmd_fxt {
	public void Test_build_url(String wiki_domain, int fsdb_db_id, String fsdb_db_type, String expd) {
		Tfds.Eq(expd, Xob_diff_regy_sql_runner.Build_url(wiki_domain, fsdb_db_id, fsdb_db_type));
	}
	public void Test_parse_url(String raw, String expd_wiki_domain, int expd_fsdb_db_id, byte expd_fsdb_db_tid) {
		Xob_diff_regy_sql_runner runner = new Xob_diff_regy_sql_runner();
		runner.Parse_url(Io_url_.new_any_(raw));
		Tfds.Eq(expd_wiki_domain, runner.Wiki_domain());
		Tfds.Eq(expd_fsdb_db_id, runner.Fsdb_db_id());
		Tfds.Eq(expd_fsdb_db_tid, runner.Fsdb_db_tid());
	}
}
