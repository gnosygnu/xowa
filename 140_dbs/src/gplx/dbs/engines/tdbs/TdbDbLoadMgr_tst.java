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
import org.junit.*; import gplx.core.gfo_ndes.*; import gplx.core.type_xtns.*;
import gplx.core.stores.*; /*DsvDataRdr*/ import gplx.langs.dsvs.*; /*DsvDataWtr*/
public class TdbDbLoadMgr_tst {
	@Before public void setup() {			
		Io_url dbInfo = Io_url_.mem_fil_("mem/dir/db0.dsv");
		db = TdbDatabase.new_(dbInfo);
		wtr = DsvDataWtr_.new_();
	}
	TdbDatabase db; TdbDbLoadMgr loadMgr = TdbDbLoadMgr.new_(); TdbDbSaveMgr saveMgr = TdbDbSaveMgr.new_();
	DataRdr rdr; DataWtr wtr;
	@Test  public void ReadDbFiles() {
		String raw = String_.Concat_lines_crlf
			(	"=======DIF======================, ,\" \",//"
			,	"_files, ,\" \",#"
			,	"==DEF==DIF======================, ,\" \",//"
			,	"int," + StringClassXtn.Key_const + "," + StringClassXtn.Key_const + ", ,\" \",$"
			,	"id,url,format, ,\" \",@"
			,	"==DATA=DIF======================, ,\" \",//"
			,	"1,mem/dir/db0.dsv,dsv"
			,	"2,C:\\file.dsv,dsv"
			);
		rdr = rdr_(raw);

		db.Files().DataObj_Rdr(rdr);
		Tfds.Eq(db.Files().Count(), 2);
		TdbFile file2 = db.Files().Get_by_or_fail(2);
		Tfds.Eq(file2.Path().Raw(), "C:\\file.dsv");

		db.Files().DataObj_Wtr(wtr);
		Tfds.Eq(wtr.To_str(), raw);
	}
	@Test  public void ReadDbTbls() {
		String raw = String_.Concat_lines_crlf
			(	"=======DIF======================, ,\" \",//"
			,	"_tables, ,\" \",#"
			,	"==DEF==DIF======================, ,\" \",//"
			,	"int," + StringClassXtn.Key_const + ",int, ,\" \",$"
			,	"id,name,file_id, ,\" \",@"
			,	"==DATA=DIF======================, ,\" \",//"
			,	"1,tbl1,1"
			);
		rdr = rdr_(raw);

		db.Tables().DataObj_Rdr(rdr, db.Files());
		Tfds.Eq(db.Tables().Count(), 1);
		TdbTable table = db.Tables().Get_by_or_fail("tbl1");
		Tfds.Eq(table.Name(), "tbl1");
		Tfds.Eq(table.File().Id(), 1);

		db.Tables().DataObj_Wtr(wtr);
		Tfds.Eq(wtr.To_str(), raw);
	}
	@Test  public void ReadTbl() {
		String raw = String_.Concat_lines_crlf
			(	"=======DIF======================, ,\" \",//"
			,	"tbl0, ,\" \",#"
			,	"==DEF==DIF======================, ,\" \",//"
			,	"int," + StringClassXtn.Key_const + ", ,\" \",$"
			,	"id,name, ,\" \",@"
			,	"==DATA=DIF======================, ,\" \",//"
			,	"0,me"
			);
		rdr = rdr_(raw);

		db.MakeTbl("tbl0", TdbFile.MainFileId);
		db.Tables().Get_by_or_fail(rdr.NameOfNode()).DataObj_Rdr(rdr);
		Tfds.Eq(db.Tables().Count(), 1);
		TdbTable tbl = db.Tables().Get_by_or_fail("tbl0");
		Tfds.Eq(tbl.Rows().Count(), 1);

		GfoNde row = tbl.Rows().FetchAt_asGfoNde(0);
		Tfds.Eq(row.Read("id"), 0);
		Tfds.Eq(row.Read("name"), "me");

		tbl.DataObj_Wtr(wtr);
		Tfds.Eq(wtr.To_str(), raw);
	}
	DataRdr rdr_(String raw) {
		DataRdr rdr = DsvDataRdr_.dsv_(raw);
		rdr.MoveNextPeer(); // must move next as cur is not set and ReadProcs assume cur is set
		return rdr;
	}
}
