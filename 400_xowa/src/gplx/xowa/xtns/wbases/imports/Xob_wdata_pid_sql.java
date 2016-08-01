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
package gplx.xowa.xtns.wbases.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.xowa.wikis.data.*; import gplx.dbs.*; import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.tbls.*;
public class Xob_wdata_pid_sql extends Xob_wdata_pid_base {
	private Xowd_wbase_pid_tbl tbl;
	@Override public String Page_wkr__key() {return gplx.xowa.bldrs.Xob_cmd_keys.Key_wbase_pid;}
	@Override public void Pid_bgn() {
		Xow_db_mgr db_mgr = wiki.Data__core_mgr();
		tbl = db_mgr.Db__wbase().Tbl__wbase_pid();
		tbl.Create_tbl();
		tbl.Insert_bgn();
	}
	@Override public void Pid_add(byte[] lang_key, byte[] ttl, byte[] pid) {
		tbl.Insert_cmd_by_batch(lang_key, ttl, pid);
	}
	@Override public void Pid_end() {
		tbl.Insert_end();
		tbl.Create_idx();
	}
}
