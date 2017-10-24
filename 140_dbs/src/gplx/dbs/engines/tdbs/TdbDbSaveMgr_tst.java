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
import org.junit.*; import gplx.core.stores.*;
import gplx.langs.dsvs.*; import gplx.core.type_xtns.*;
public class TdbDbSaveMgr_tst {
	@Before public void setup() {
		Io_url dbInfo = Io_url_.mem_fil_("mem/dir/db0.dsv");
		db = TdbDatabase.new_(dbInfo);
		wtr.Clear();
	}	TdbDatabase db; TdbDbSaveMgr saveMgr = TdbDbSaveMgr.new_(); DataWtr wtr = DsvDataWtr_.new_();
	@Test  public void WriteDbFils() {
		String expd = String_.Concat_lines_crlf
			(	""	
			,	""
			,	"================================, ,\" \",//"
			,	"_files, ,\" \",#"
			,	"================================, ,\" \",//"
			,	"int," + StringClassXtn.Key_const + "," + StringClassXtn.Key_const + ", ,\" \",$"
			,	"id,url,format, ,\" \",@"
			,	"================================, ,\" \",//"
			,	"1,mem/dir/db0.dsv,dsv"
			);
		db.Files().DataObj_Wtr(wtr);
		String actl = wtr.To_str();
		Tfds.Eq(expd, actl);
	}
	@Test  public void WriteDbTbls() {
		String expd = String_.Concat_lines_crlf
			(	""
			,	""
			,	"================================, ,\" \",//"
			,	"_tables, ,\" \",#"
			,	"================================, ,\" \",//"
			,	"int," + StringClassXtn.Key_const + ",int, ,\" \",$"
			,	"id,name,file_id, ,\" \",@"
			,	"================================, ,\" \",//"
			);
		db.Tables().DataObj_Wtr(wtr);
		String actl = wtr.To_str();
		Tfds.Eq(expd, actl);			
	}
	@Test  public void WriteTbl() {
		String expd = String_.Concat_lines_crlf
			(	""
			,	""
			,	"================================, ,\" \",//"
			,	"tbl, ,\" \",#"
			,	"================================, ,\" \",//"
			,	"int," + StringClassXtn.Key_const + ", ,\" \",$"
			,	"id,name, ,\" \",@"
			,	"================================, ,\" \",//"
			);
		TdbTable tbl = db.MakeTbl("tbl", TdbFile.MainFileId);
		tbl.Flds().Add("id", IntClassXtn.Instance);
		tbl.Flds().Add("name", StringClassXtn.Instance);

		tbl.DataObj_Wtr(wtr);
		String actl = wtr.To_str();
		Tfds.Eq(expd, actl);			
	}
}
