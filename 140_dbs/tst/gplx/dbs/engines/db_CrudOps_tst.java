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
package gplx.dbs.engines; import gplx.*; import gplx.dbs.*;
import org.junit.*; import gplx.dbs.qrys.*;
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
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Val_int("id", 1).Val_str("name", "John Doe"));
		fx.tst_ExecRdrTbl(1, "dbs_crud_ops");
		fx.tst_RowAry(0, 1, "John Doe");
	}
	public void UpdateOne_hook() {
		this.Init();
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Val_int("id", 1).Val_str("name", "John Doe"));
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Val_int("id", 2).Val_str("name", "John Doe"));

		fx.tst_ExecDml(1, Db_qry_.update_common_("dbs_crud_ops", Db_crt_.New_eq("id", 2), Keyval_.new_("name", "Jane Smith")));
		fx.tst_ExecRdrTbl(2, "dbs_crud_ops");
		fx.tst_RowAry(0, 1, "John Doe");
		fx.tst_RowAry(1, 2, "Jane Smith");
	}
	public void UpdateMany_hook() {
		this.Init();
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Val_int("id", 1).Val_str("name", "John Doe"));
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Val_int("id", 2).Val_str("name", "John Doe"));

		fx.tst_ExecDml(2, Db_qry_.update_common_("dbs_crud_ops", Db_crt_.New_eq("name", "John Doe"), Keyval_.new_("name", "Jane Smith")));
		fx.tst_ExecRdrTbl(2, "dbs_crud_ops");
		fx.tst_RowAry(0, 1, "Jane Smith");
		fx.tst_RowAry(1, 2, "Jane Smith");
	}
	public void DeleteOne_hook() {
		this.Init();
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Val_int("id", 1).Val_str("name", "John Doe"));
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Val_int("id", 2).Val_str("name", "Jane Smith"));

		fx.tst_ExecDml(1, Db_qry_.delete_("dbs_crud_ops", Db_crt_.New_eq("id", 2)));
		fx.tst_ExecRdrTbl(1, "dbs_crud_ops");
		fx.tst_RowAry(0, 1, "John Doe");
	}
	public void DeleteMany_hook() {
		this.Init();
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Val_int("id", 1).Val_str("name", "John Doe"));
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Val_int("id", 2).Val_str("name", "Jane Smith"));

		fx.tst_ExecDml(2, Db_qry_.delete_tbl_("dbs_crud_ops"));
		fx.tst_ExecRdrTbl(0, "dbs_crud_ops");
	}
	public void SelectLike_hook() {
		this.Init();
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Val_int("id", 1).Val_str("name", "John Doe"));

		fx.tst_ExecRdr(1, Db_qry_.select_cols_("dbs_crud_ops", Db_crt_.New_like("name", "John%")));
		fx.tst_RowAry(0, 1, "John Doe");
	}
	public void SelectLikeForFileName_hook() {
		this.Init();
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Val_int("id", 1).Val_str("name", "file%"));
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Val_int("id", 1).Val_str("name", "file|%"));
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Val_int("id", 1).Val_str("name", "file test"));

		fx.tst_ExecRdr(1, Db_qry_.select_cols_("dbs_crud_ops", Db_crt_.New_like("name", "file|%")));
		fx.tst_RowAry(0, 1, "file%");
	}
	public void InsertUnicode_hook() {
		this.Init();
		String val = "Ω";
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Val_int("id", 3).Val_obj_type("name", val, Db_val_type.Tid_nvarchar));
		Db_qry__select_cmd select = Db_qry_.select_val_("dbs_crud_ops", "name", Db_crt_.New_eq("id", 3));
		Tfds.Eq(val, ExecRdr_val(select));

		fx.tst_ExecDml(1, Db_qry_.update_("dbs_crud_ops", Db_crt_.Wildcard).Val_obj_type("name", val + "a", Db_val_type.Tid_nvarchar));
		Tfds.Eq(val + "a", ExecRdr_val(select));
	}
	public void Backslash_hook() {
		this.Init();
		String val = "\\";
		fx.tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Val_int("id", 3).Val_str("name", val));
		Tfds.Eq(val, ExecRdr_val(Db_qry_.select_val_("dbs_crud_ops", "name", Db_crt_.New_eq("id", 3))));
		Tfds.Eq(val, ExecRdr_val(Db_qry_.select_val_("dbs_crud_ops", "name", Db_crt_.New_eq("name", "\\"))));
	}
	String ExecRdr_val(Db_qry__select_cmd select) {return (String)Db_qry_.Exec_as_obj(fx.Conn(), select);}
}
