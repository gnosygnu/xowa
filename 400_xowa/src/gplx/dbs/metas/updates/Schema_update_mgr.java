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
package gplx.dbs.metas.updates; import gplx.*; import gplx.dbs.*; import gplx.dbs.metas.*;
public class Schema_update_mgr {		
	private List_adp cmds = List_adp_.new_();
	public void Add(Schema_update_cmd cmd) {cmds.Add(cmd);}
	public void Update(Schema_db_mgr schema_mgr, Db_conn conn) {
		int cmds_len = cmds.Count();
		for (int i = 0; i < cmds_len; ++i) {
			Schema_update_cmd cmd = (Schema_update_cmd)cmds.Get_at(i);
			try {cmd.Exec(schema_mgr, conn);}
			catch (Exception e) {
				Gfo_usr_dlg_.I.Warn_many("", "", "failed to run update cmd; name=~{0} err=~{1}", cmd.Name(), Err_.Message_gplx_brief(e));
			}
		}
	}
}
