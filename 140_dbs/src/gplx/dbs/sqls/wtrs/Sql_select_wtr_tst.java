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
package gplx.dbs.sqls.wtrs; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
import org.junit.*;
public class Sql_select_wtr_tst {
	private final Sql_core_wtr_fxt fxt = new Sql_core_wtr_fxt();
	@Test   public void Offset__automatically_add_limit() {
		fxt.Test__qry(Db_qry_.select_tbl_("tbl").Offset_(1), "SELECT * FROM tbl LIMIT -1 OFFSET 1");
	}
	@Test   public void Offset__do_not_overwrite_limit() {
		fxt.Test__qry(Db_qry_.select_tbl_("tbl").Limit_(20).Offset_(1), "SELECT * FROM tbl LIMIT 20 OFFSET 1");
	}
}
