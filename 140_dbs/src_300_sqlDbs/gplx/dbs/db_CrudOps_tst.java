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
package gplx.dbs; import gplx.*;
import org.junit.*;
public class db_CrudOps_tst {
	CrudOpsFxt fx = new CrudOpsFxt();
	@Test  public void Mysql() {if (Tfds.SkipDb) return;
		fx.RunAll(Db_conn_fxt.Mysql());
	}
	@Test  public void Tdb() {if (Tfds.SkipDb) return;
		fx.RunAll(Db_conn_fxt.Tdb("100_dbs_crud_ops.dsv"));
	}
	@Test  public void Postgres() {if (Db_conn_fxt.SkipPostgres) return;
		fx.RunAll(Db_conn_fxt.Postgres());
	}
	@Test  public void Sqlite() {if (Tfds.SkipDb) return;
		fx.Fx().DmlAffectedAvailable_(false);
		fx.RunAll(Db_conn_fxt.Sqlite());
	}
}
class CrudOpsFxt {
	public Db_conn_fxt Fx() {return fx;} Db_conn_fxt fx = new Db_conn_fxt();
	void Init() {fx.ini_DeleteAll("dbs_crud_ops");}
	public void RunAll(Db_conn conn) {
		fx.Conn_(conn);
		Insert_hook();
		UpdateOne_hook();
		UpdateMany_hook();
		DeleteOne_hook();
		DeleteMany_hook();
		SelectLike_hook();
		SelectLikeForFileName_hook();
		InsertUnicode_hook();
		Backslash_hook();
		fx.Rls();
	}
	public void Insert_hook() {
		this.Init();
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 1).Arg_("name", "John Doe"));
		fx.tst_ExecRdrTbl(1, "dbs_crud_ops");
		fx.tst_RowAry(0, 1, "John Doe");
	}
	public void UpdateOne_hook() {
		this.Init();
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 1).Arg_("name", "John Doe"));
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 2).Arg_("name", "John Doe"));

		fx.tst_ExecDml(1, Db_qry_.update_common_("dbs_crud_ops", Db_crt_.eq_("id", 2), KeyVal_.new_("name", "Jane Smith")));
		fx.tst_ExecRdrTbl(2, "dbs_crud_ops");
		fx.tst_RowAry(0, 1, "John Doe");
		fx.tst_RowAry(1, 2, "Jane Smith");
	}
	public void UpdateMany_hook() {
		this.Init();
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 1).Arg_("name", "John Doe"));
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 2).Arg_("name", "John Doe"));

		fx.tst_ExecDml(2, Db_qry_.update_common_("dbs_crud_ops", Db_crt_.eq_("name", "John Doe"), KeyVal_.new_("name", "Jane Smith")));
		fx.tst_ExecRdrTbl(2, "dbs_crud_ops");
		fx.tst_RowAry(0, 1, "Jane Smith");
		fx.tst_RowAry(1, 2, "Jane Smith");
	}
	public void DeleteOne_hook() {
		this.Init();
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 1).Arg_("name", "John Doe"));
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 2).Arg_("name", "Jane Smith"));

		fx.tst_ExecDml(1, Db_qry_.delete_("dbs_crud_ops", Db_crt_.eq_("id", 2)));
		fx.tst_ExecRdrTbl(1, "dbs_crud_ops");
		fx.tst_RowAry(0, 1, "John Doe");
	}
	public void DeleteMany_hook() {
		this.Init();
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 1).Arg_("name", "John Doe"));
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 2).Arg_("name", "Jane Smith"));

		fx.tst_ExecDml(2, Db_qry_.delete_tbl_("dbs_crud_ops"));
		fx.tst_ExecRdrTbl(0, "dbs_crud_ops");
	}
	public void SelectLike_hook() {
		this.Init();
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 1).Arg_("name", "John Doe"));

		fx.tst_ExecRdr(1, Db_qry_.select_cols_("dbs_crud_ops", Db_crt_.like_("name", "John%")));
		fx.tst_RowAry(0, 1, "John Doe");
	}
	public void SelectLikeForFileName_hook() {
		this.Init();
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 1).Arg_("name", "file%"));
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 1).Arg_("name", "file|%"));
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 1).Arg_("name", "file test"));

		fx.tst_ExecRdr(1, Db_qry_.select_cols_("dbs_crud_ops", Db_crt_.like_("name", "file|%")));
		fx.tst_RowAry(0, 1, "file%");
	}
	public void InsertUnicode_hook() {
		this.Init();
		String val = "Ω";
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 3).Arg_obj_type_("name", val, Db_val_type.Tid_nvarchar));
		Db_qry_select select = Db_qry_.select_val_("dbs_crud_ops", "name", Db_crt_.eq_("id", 3));
		Tfds.Eq(val, ExecRdr_val(select));

		fx.tst_ExecDml(1, Db_qry_.update_("dbs_crud_ops", Db_crt_.Wildcard).Arg_obj_type_("name", val + "a", Db_val_type.Tid_nvarchar));
		Tfds.Eq(val + "a", ExecRdr_val(select));
	}
	public void Backslash_hook() {
		this.Init();
		String val = "\\";
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 3).Arg_("name", val));
		Tfds.Eq(val, ExecRdr_val(Db_qry_.select_val_("dbs_crud_ops", "name", Db_crt_.eq_("id", 3))));
		Tfds.Eq(val, ExecRdr_val(Db_qry_.select_val_("dbs_crud_ops", "name", Db_crt_.eq_("name", "\\"))));
	}
	String ExecRdr_val(Db_qry_select select) {return (String)select.ExecRdr_val(fx.Conn());}
}
