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
package gplx.dbs.engines.tdbs; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import org.junit.*;
import gplx.core.ios.*; /*IoMgrFxt*/ import gplx.dbs.qrys.*; import gplx.core.type_xtns.*;
public class TdbFlush_tst {
	@Before public void setup() {
		Io_mgr.Instance.InitEngine_mem();
		engine = fx_engine.run_MakeEngine(dbPath);
	}
	TdbEngine engine; Io_url dbPath = Io_url_.mem_fil_("mem/dir/db0.dsv"); DateAdp time = DateAdp_.parse_gplx("2001-01-01");
	TdbEngineFxt fx_engine = TdbEngineFxt.new_(); IoMgrFxt fx_io = IoMgrFxt.new_();
	@Test  public void FlushNewDb() {
		fx_engine.tst_FilesCount(engine, 1);
		fx_engine.tst_File(engine, 0, TdbFile.MainFileId, Io_url_.mem_fil_("mem/dir/db0.dsv"), "dsv");
		fx_io.tst_Exists(false, dbPath);

		engine.FlushAll();
		fx_io.tst_Exists(true, dbPath);
	}
	@Test  public void IgnoreFlushedDb() {
		engine.FlushAll();
		fx_io.tst_Exists(true, dbPath);
		fx_io.run_UpdateFilModifiedTime(dbPath, time);

		engine.FlushAll();
		fx_io.tst_QueryFilModified(true, dbPath, time);
	}
	@Test  public void FlushNewTbl() {
		engine.FlushAll();
		fx_engine.run_MakeTbl(engine, "tbl0", TdbFile.MainFileId);
		fx_io.run_UpdateFilModifiedTime(dbPath, time);

		engine.FlushAll();
		fx_io.tst_QueryFilModified(false, dbPath, time);
	}
	@Test  public void IgnoreFlushedTbl() {
		fx_engine.run_MakeTbl(engine, "tbl0", TdbFile.MainFileId);
		engine.FlushAll();
		fx_io.run_UpdateFilModifiedTime(dbPath, time);

		engine.FlushAll();
		fx_io.tst_QueryFilModified(true, dbPath, time);
	}
	@Test  public void FlushDirtyTbl() {
		fx_engine.run_MakeTbl(engine, "tbl0", TdbFile.MainFileId);
		engine.FlushAll();
		fx_io.run_UpdateFilModifiedTime(dbPath, time);

		fx_engine.run_InsertRow(engine, "tbl0", 1);
		engine.FlushAll();
		fx_io.tst_QueryFilModified(false, dbPath, time);
	}
	@Test  public void FlushDirtyFilOnly() {
		Io_url dbPathOther = Io_url_.mem_fil_("mem/dir/db1.dsv");
		TdbFile filOther = fx_engine.run_MakeFile(engine, dbPathOther); Tfds.Eq(false, Object_.Eq(filOther.Id(), TdbFile.MainFileId));
		fx_engine.run_MakeTbl(engine, "tbl0", TdbFile.MainFileId); fx_engine.run_MakeTbl(engine, "tbl1", filOther.Id());
		engine.FlushAll();
		fx_io.run_UpdateFilModifiedTime(dbPath, time); fx_io.run_UpdateFilModifiedTime(dbPathOther, time);

		fx_engine.run_InsertRow(engine, "tbl1", 1);
		engine.FlushAll();
		fx_io.tst_QueryFilModified(true, dbPath, time);
		fx_io.tst_QueryFilModified(false, dbPathOther, time);
	}
}
class TdbEngineFxt {
	public TdbEngine run_MakeEngine(Io_url url) {
		Db_conn_info connectInfo = Db_conn_info_.tdb_(url);
		TdbEngine engine = (TdbEngine)TdbEngine.Instance.New_clone(connectInfo);
		engine.Conn_open();
		return engine;
	}
	public TdbFile run_MakeFile(TdbEngine engine, Io_url url) {return engine.Db().MakeFile(url);}
	public TdbTable run_MakeTbl(TdbEngine engine, String tblName, int srcId) {
		TdbTable rv = engine.Db().MakeTbl(tblName, srcId);
		rv.Flds().Add("id", IntClassXtn.Instance);
		return rv;
	}
	public void run_InsertRow(TdbEngine engine, String tblName, int idVal) {
		Db_qry_insert cmd = new Db_qry_insert(tblName);
		cmd.Val_int("id", idVal);
		engine.Exec_as_obj(cmd);
	}

	public void tst_FilesCount(TdbEngine engine, int count) {Tfds.Eq(engine.Db().Files().Count(), count);}
	public void tst_File(TdbEngine engine, int index, int id, Io_url url, String format) {
		TdbFile src = engine.Db().Files().Get_by_or_fail(id);
		Tfds.Eq(src.Path().Raw(), url.Raw());
	}
	public static TdbEngineFxt new_() {return new TdbEngineFxt();} TdbEngineFxt() {}
}
class IoMgrFxt {
	public void run_UpdateFilModifiedTime(Io_url url, DateAdp val) {Io_mgr.Instance.UpdateFilModifiedTime(url, val);}
	public void tst_QueryFilModified(boolean expdMatch, Io_url url, DateAdp expt) {
		IoItmFil filItem = Io_mgr.Instance.QueryFil(url);
		DateAdp actl = filItem.ModifiedTime();
		boolean actlMatch = String_.Eq(expt.XtoStr_gplx(), actl.XtoStr_gplx());
		Tfds.Eq(expdMatch, actlMatch, expt.XtoStr_gplx() + (expdMatch ? "!=" : "==") + actl.XtoStr_gplx());
	}
	public void tst_Exists(boolean expd, Io_url url) {Tfds.Eq(expd, Io_mgr.Instance.ExistsFil(url));}

	public static IoMgrFxt new_() {return new IoMgrFxt();} IoMgrFxt() {}
}
