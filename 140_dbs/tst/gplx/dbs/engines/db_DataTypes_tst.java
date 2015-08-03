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
package gplx.dbs.engines; import gplx.*; import gplx.dbs.*;
import org.junit.*;
public class db_DataTypes_tst {
	DataTypes_base_fxt fx = new DataTypes_base_fxt();
	@Test  public void Mysql() {if (Tfds.SkipDb) return;
		fx.Select_FloatStr_("0.333333");
		fx.RunAll(Db_conn_fxt.Mysql());
	}
	@Test  public void Tdb() {if (Tfds.SkipDb) return;
		fx.Select_FloatStr_(Float_.Xto_str(Float_.Div(1, 3)));
		fx.RunAll(Db_conn_fxt.Tdb("110_dbs_multiple_data_types.dsv"));
	}
	@Test  public void Postgres() {if (Db_conn_fxt.SkipPostgres) return;
		fx.Select_FloatStr_("0.33333334");
		fx.RunAll(Db_conn_fxt.Postgres());
	}
	@Test  public void Sqlite() {if (Tfds.SkipDb) return;
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
		DataRdr rdr = Db_qry_.select_tbl_("dbs_multiple_data_types").Exec_qry_as_rdr(conn);

		rdr.MoveNextPeer();
		Tfds.Eq(rdr.ReadInt("unique_id"), 1);
		Tfds.Eq(rdr.ReadStr("full_name"), "John Doe");
		Tfds.Eq(rdr.ReadBool("is_active"), true);
		Tfds.Eq_date(rdr.ReadDate("last_update"), DateAdp_.parse_gplx("2006-03-30 22:22:00.000"));
		Tfds.Eq(floatStr, Object_.Xto_str_strict_or_empty(rdr.ReadFloat("quantity")));
		Tfds.Eq_decimal(rdr.ReadDecimal("amount"), Decimal_adp_.parts_(12, 345));
	}
	public void UpdateDate_hook() {
		conn.Exec_qry(Db_qry_.update_("dbs_multiple_data_types", Db_crt_.eq_("unique_id", 1)).Arg_obj_("last_update", DateAdpClassXtn._.XtoDb(DateAdp_.parse_gplx("20091115 220000.000"))));

		DataRdr rdr = Db_qry_.select_tbl_("dbs_multiple_data_types").Exec_qry_as_rdr(conn);
		rdr.MoveNextPeer();
		Tfds.Eq_date(rdr.ReadDate("last_update"), DateAdp_.parse_gplx("20091115 220000.000"));
	}
}
