/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
		Bry_bfr bfr = Bry_bfr_.New();
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
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return Gfo_invk_.Noop;}
}
