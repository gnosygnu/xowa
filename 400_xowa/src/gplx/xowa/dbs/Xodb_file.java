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
package gplx.xowa.dbs; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*;
public class Xodb_file {
	public Xodb_file(int id, byte tid) {this.id = id; this.tid = tid;}
	public int		Id()		{return id;}			private final int id;		// unique id in xowa_db
	public byte		Tid()		{return tid;}			private final byte tid;		// db type id
	public Io_url	Url()		{return url;}			public Xodb_file Url_(Io_url v) {url = v; return this;}				private Io_url url;
	public String	Url_rel()	{return url_rel;}		public Xodb_file Url_rel_(String v) {url_rel = v; return this;}		private String url_rel;
	public long		File_len()	{return file_len;}		public Xodb_file File_len_add(int v) {file_len += v; return this;}	private long file_len;
	public long		File_max()	{return file_max;}		public Xodb_file File_max_(long v) {file_max = v; return this;}		private long file_max;
	public byte		Cmd_mode()	{return cmd_mode;}		public Xodb_file Cmd_mode_(byte v) {cmd_mode = v; return this;}		private byte cmd_mode;
	public Db_url Connect() {return connect;}		public Xodb_file Connect_(Db_url v) {connect = v; return this;} private Db_url connect;
	public Db_conn Conn() {
		if (conn == null) conn = Db_conn_pool_old._.Get_or_new(connect);
		return conn;
	}	private Db_conn conn;
	public void Conn_(Db_conn p) {conn = p;}
	public void Rls() {
		if (conn == null) return;
		try {
			conn.Txn_mgr().Txn_end_all();	// close any open transactions
			conn.Conn_term();
		}	finally {conn = null;}
	}
	public static Xodb_file load_(int id, byte tid, String url) {return new Xodb_file(id, tid).Url_rel_(url).Cmd_mode_(Db_cmd_mode.Ignore);}
	public static Xodb_file make_(int id, byte tid, String url) {return new Xodb_file(id, tid).Url_rel_(url).Cmd_mode_(Db_cmd_mode.Create);}
}
