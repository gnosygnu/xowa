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
public abstract class OrderBys_base_tst {
	@Before public void setup() {
		provider = provider_();
		fx.Provider_(provider);
		Db_qry_delete.new_().BaseTable_("dbs_crud_ops").Exec_qry(provider);
	}	protected Db_provider_fxt fx = new Db_provider_fxt();
	@After public void teardown() {provider.Rls();}
	protected abstract Db_provider provider_(); protected Db_provider provider;		
	protected void Basic_hook() {
		fx.tst_ExecDml(1, Db_qry_insert.new_().BaseTable_("dbs_crud_ops").Arg_("id", 1).Arg_("name", "you"));
		fx.tst_ExecDml(1, Db_qry_insert.new_().BaseTable_("dbs_crud_ops").Arg_("id", 0).Arg_("name", "me"));

		fx.tst_ExecRdr(2, Db_qry_select.new_().From_("dbs_crud_ops").OrderBy_("id", true));
		fx.tst_RowAry(0, 0, "me");
		fx.tst_RowAry(1, 1, "you");

		fx.tst_ExecRdr(2, Db_qry_select.new_().From_("dbs_crud_ops").OrderBy_("id", false));
		fx.tst_RowAry(0, 1, "you");
		fx.tst_RowAry(1, 0, "me");
	}
	protected void SameVals_hook() {
		fx.tst_ExecDml(1, Db_qry_insert.new_().BaseTable_("dbs_crud_ops").Arg_("id", 0).Arg_("name", "me"));
		fx.tst_ExecDml(1, Db_qry_insert.new_().BaseTable_("dbs_crud_ops").Arg_("id", 0).Arg_("name", "you"));

		fx.tst_ExecRdr(2, Db_qry_select.new_().From_("dbs_crud_ops").OrderBy_("id", true));
		fx.tst_RowAry(0, 0, "me");
		fx.tst_RowAry(1, 0, "you");

		fx.tst_ExecRdr(2, Db_qry_select.new_().From_("dbs_crud_ops").OrderBy_("id", false));
		fx.tst_RowAry(0, 0, "me");
		fx.tst_RowAry(1, 0, "you");
	}
}
