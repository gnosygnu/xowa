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
public class TdbDatabase {
	public String Name() {return name;} public void Name_set(String v) {name = v;} private String name = "xmpl";
	public Io_url DbUrl() {return dbInfo;} Io_url dbInfo;
	public TdbFileList Files() {return files;} TdbFileList files;
	public TdbTableList Tables() {return tables;} TdbTableList tables;
	@gplx.Internal protected boolean IsNew() {return isNew;} @gplx.Internal protected void IsNew_set(boolean v) {isNew = v;} private boolean isNew;
	@gplx.Internal protected TdbFile MakeFile(Io_url url) {
		TdbFile rv = TdbFile.new_(FileId_next++, url);
		files.Add(rv);
		return rv;
	}
	@gplx.Internal protected TdbTable MakeTbl(String name, int fileId) {
		TdbFile file = files.Get_by_or_fail(fileId);
		TdbTable rv = TdbTable.new_(TableId_next++, name, file);
		tables.Add(rv);
		return rv;
	}

	public static TdbDatabase new_(Io_url dbInfo) {TdbDatabase rv = new TdbDatabase(); rv.ctor(dbInfo); return rv;}
	void ctor(Io_url dbInfo) {
		this.dbInfo = dbInfo;
		TdbFile mainFile = TdbFile.new_(TdbFile.MainFileId, dbInfo);
		files = TdbFileList.new_(dbInfo, mainFile);
		tables = TdbTableList.new_(dbInfo);
	}
	int FileId_next = TdbFile.MainFileId + 1;
	int TableId_next = 1;
//		public static Io_url UrlOf(Db_conn_info url) {return Io_url_.new_any_(url.ServerName());}
}
