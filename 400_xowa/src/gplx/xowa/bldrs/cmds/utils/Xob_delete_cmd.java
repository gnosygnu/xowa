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
package gplx.xowa.bldrs.cmds.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.dbs.*; import gplx.core.ios.*; import gplx.core.envs.*;
import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.wms.dumps.*;
public class Xob_delete_cmd extends Xob_cmd__base implements Xob_cmd {
	private String[] patterns_ary = String_.Ary_empty;
	public Xob_delete_cmd(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	public Xob_delete_cmd Patterns_ary_(String... v) {this.patterns_ary = v; return this;}
	@Override public String Cmd_key() {return Xob_cmd_keys.Key_util_delete;}
	@Override public void Cmd_run() {
		int len = patterns_ary.length; if (len == 0) return;

		// build filter EX: '*.xml|*.txt'
		Bry_bfr bfr = Bry_bfr.new_();
		for (int i = 0; i < len; ++i) {
			String pattern = patterns_ary[i];
			if (i != 0) bfr.Add_byte_pipe();
			bfr.Add_str_u8(pattern);
		}

		// get files; iterate and delete
		String file_pattern = bfr.To_str_and_clear();
		Io_url[] files = Io_mgr.Instance.QueryDir_args(wiki.Fsys_mgr().Root_dir()).Recur_(Bool_.N).FilPath_(file_pattern).ExecAsUrlAry();
		int files_len = files.length;
		for (int i = 0; i < files_len; ++i) {
			Io_url file = files[i];
			if (file.Ext() == ".sqlite3")
				Db_conn_bldr.Instance.Get_or_noop(file).Rls_conn();
			Io_mgr.Instance.DeleteFil(file);
		}
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return GfoInvkAble_.Null;}
}
