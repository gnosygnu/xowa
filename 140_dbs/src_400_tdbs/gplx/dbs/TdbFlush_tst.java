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
import gplx.ios.*; /*IoMgrFxt*/
public class TdbFlush_tst {
	@Before public void setup() {
		Io_mgr._.InitEngine_mem();
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
		TdbEngine engine = (TdbEngine)TdbEngine._.Make_new(connectInfo);
		engine.Conn_open();
		return engine;
	}
	public TdbFile run_MakeFile(TdbEngine engine, Io_url url) {return engine.Db().MakeFile(url);}
	public TdbTable run_MakeTbl(TdbEngine engine, String tblName, int srcId) {
		TdbTable rv = engine.Db().MakeTbl(tblName, srcId);
		rv.Flds().Add("id", IntClassXtn._);
		return rv;
	}
	public void run_InsertRow(TdbEngine engine, String tblName, int idVal) {
		Db_qry_insert cmd = Db_qry_insert.new_().BaseTable_(tblName);
		cmd.Arg_("id", idVal);
		engine.Execute(cmd);
	}

	public void tst_FilesCount(TdbEngine engine, int count) {Tfds.Eq(engine.Db().Files().Count(), count);}
	public void tst_File(TdbEngine engine, int index, int id, Io_url url, String format) {
		TdbFile src = engine.Db().Files().FetchOrFail(id);
		Tfds.Eq(src.Path().Raw(), url.Raw());
	}
	public static TdbEngineFxt new_() {return new TdbEngineFxt();} TdbEngineFxt() {}
}
class IoMgrFxt {
	public void run_UpdateFilModifiedTime(Io_url url, DateAdp val) {Io_mgr._.UpdateFilModifiedTime(url, val);}
	public void tst_QueryFilModified(boolean expdMatch, Io_url url, DateAdp expt) {
		IoItmFil filItem = Io_mgr._.QueryFil(url);
		DateAdp actl = filItem.ModifiedTime();
		boolean actlMatch = String_.Eq(expt.XtoStr_gplx(), actl.XtoStr_gplx());
		Tfds.Eq(expdMatch, actlMatch, expt.XtoStr_gplx() + (expdMatch ? "!=" : "==") + actl.XtoStr_gplx());
	}
	public void tst_Exists(boolean expd, Io_url url) {Tfds.Eq(expd, Io_mgr._.ExistsFil(url));}

	public static IoMgrFxt new_() {return new IoMgrFxt();} IoMgrFxt() {}
}
