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
package gplx.dbs.engines; import gplx.dbs.*;
import gplx.frameworks.tests.GfoDbTstr;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.commons.GfoDateUtl;
import gplx.types.commons.GfoDecimalUtl;
import org.junit.*; import gplx.core.type_xtns.*; import gplx.core.stores.*;
public class db_DataTypes_tst {
	DataTypes_base_fxt fx = new DataTypes_base_fxt();
	@Test public void Mysql() {if (GfoDbTstr.SkipDb) return;
		fx.Select_FloatStr_("0.333333");
		fx.RunAll(Db_conn_fxt.Mysql());
	}
	@Test public void Tdb() {if (GfoDbTstr.SkipDb) return;
		fx.Select_FloatStr_(FloatUtl.ToStr(FloatUtl.Div(1, 3)));
		fx.RunAll(Db_conn_fxt.Tdb("110_dbs_multiple_data_types.dsv"));
	}
	@Test public void Postgres() {if (Db_conn_fxt.SkipPostgres) return;
		fx.Select_FloatStr_("0.33333334");
		fx.RunAll(Db_conn_fxt.Postgres());
	}
	@Test public void Sqlite() {if (GfoDbTstr.SkipDb) return;
		fx.Select_FloatStr_("0.33333334");
		fx.RunAll(Db_conn_fxt.Sqlite());
	}
	/*
DROP TABLE dbs_multiple_data_types;
CREATE TABLE dbs_multiple_data_types (
unique_id	int
,	full_name	varchar(255)
,	is_active	bit
,	last_update	timestamp
,	quantity	float(24)
,	amount		decimal(12, 3)
);
INSERT INTO dbs_multiple_data_types VALUES (1, 'John Doe', B'1', '3/30/2006 10:22 PM', 0.333333343, 12.345);
	*/
}
class DataTypes_base_fxt {
	public Db_conn Conn() {return conn;} Db_conn conn;
	public DataTypes_base_fxt() {}
	public void Rls() {conn.Rls_conn();}
	public String Select_FloatStr() {return select_FloatStr;} public DataTypes_base_fxt Select_FloatStr_(String v) {select_FloatStr = v; return this;} private String select_FloatStr;
	public void RunAll(Db_conn conn) {
		this.conn = conn;
		this.Select_hook(select_FloatStr);
		conn.Rls_conn();
	}
	public void Select_hook(String floatStr) {
		DataRdr rdr = conn.Exec_qry_as_old_rdr(Db_qry_.select_tbl_("dbs_multiple_data_types"));

		rdr.MoveNextPeer();
		GfoTstr.EqObj(rdr.ReadInt("unique_id"), 1);
		GfoTstr.EqObj(rdr.ReadStr("full_name"), "John Doe");
		GfoTstr.EqObj(rdr.ReadBool("is_active"), true);
		GfoTstr.Eq(rdr.ReadDate("last_update"), GfoDateUtl.ParseGplx("2006-03-30 22:22:00.000"));
		GfoTstr.EqObj(floatStr, ObjectUtl.ToStrOrEmpty(rdr.ReadFloat("quantity")));
		GfoTstr.Eq(rdr.ReadDecimal("amount"), GfoDecimalUtl.NewByParts(12, 345));
	}
	public void UpdateDate_hook() {
		conn.Exec_qry(Db_qry_.update_("dbs_multiple_data_types", Db_crt_.New_eq("unique_id", 1)).Val_obj("last_update", DateAdpClassXtn.Instance.XtoDb(GfoDateUtl.ParseGplx("20091115 220000.000"))));

		DataRdr rdr = conn.Exec_qry_as_old_rdr(Db_qry_.select_tbl_("dbs_multiple_data_types"));
		rdr.MoveNextPeer();
		GfoTstr.Eq(rdr.ReadDate("last_update"), GfoDateUtl.ParseGplx("20091115 220000.000"));
	}
}
