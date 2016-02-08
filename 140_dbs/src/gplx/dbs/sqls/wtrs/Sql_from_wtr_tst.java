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
public class Sql_from_wtr_tst {
	private final Sql_core_wtr_fxt fxt = new Sql_core_wtr_fxt();
	@Test   public void Abrv() {
		fxt.Test__qry(Db_qry_.select_().Cols_all_().From_("tbl", "t"), "SELECT * FROM tbl t");
	}
	@Test   public void Db() {
		fxt.Test__qry(Db_qry_.select_().Cols_all_().From_("db", "tbl", "t"), "SELECT * FROM db.tbl t");
	}
	@Test   public void Join() {
		fxt.Test__qry
		( Db_qry_.select_().Cols_all_().From_("src", "s").Join_("trg", "t", Db_qry_.New_join__join("trg_id", "s", "src_id"))
		, "SELECT * FROM src s INNER JOIN trg t ON s.src_id = t.trg_id");
	}
}
