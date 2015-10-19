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
package gplx.dbs.engines.tdbs; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import org.junit.*;
import gplx.stores.dsvs.*; /*DsvDataWtr*/
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
