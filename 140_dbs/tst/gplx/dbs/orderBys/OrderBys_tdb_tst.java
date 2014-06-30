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
package gplx.dbs.orderBys; import gplx.*; import gplx.dbs.*;
import org.junit.*;
public class OrderBys_tdb_tst extends OrderBys_base_tst {
	@Override protected Db_provider provider_() {return Db_provider_fxt.Tdb("120_dbs_joins.dsv");}
	@Test  public void Basic() {
		Basic_hook();
	}
	@Test  public void SameVals() {
		SameVals_hook();
	}
}
//namespace gplx.dbs.crudOps {
//	import org.junit.*;
//	public class CrudOps_tdb_tst {
//		@Before public void setup() {fx = new CrudOpsFxt(Db_provider_fxt.Tdb("100_dbs_crud_ops.dsv"));} CrudOpsFxt fx;
//		@Test  public void FlushToDisk() {
//			fx.Fx().tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 2).Arg_("name", "you"));
//			Db_qry_flush.new_("dbs_crud_ops").Exec_qry(fx.Fx().Provider());
//		}
//	}
//}