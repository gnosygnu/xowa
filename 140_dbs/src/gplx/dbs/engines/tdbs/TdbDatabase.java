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
