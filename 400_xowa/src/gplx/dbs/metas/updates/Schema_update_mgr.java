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
package gplx.dbs.metas.updates; import gplx.*; import gplx.dbs.*; import gplx.dbs.metas.*;
public class Schema_update_mgr {		
	private List_adp cmds = List_adp_.New();
	public void Add(Schema_update_cmd cmd) {cmds.Add(cmd);}
	public void Update(Schema_db_mgr schema_mgr, Db_conn conn) {
		int cmds_len = cmds.Count();
		for (int i = 0; i < cmds_len; ++i) {
			Schema_update_cmd cmd = (Schema_update_cmd)cmds.Get_at(i);
			try {cmd.Exec(schema_mgr, conn);}
			catch (Exception e) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to run update cmd; name=~{0} err=~{1}", cmd.Name(), Err_.Message_gplx_full(e));
			}
		}
	}
}
