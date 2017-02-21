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
package gplx.dbs;
import gplx.*; import gplx.core.consoles.*; import gplx.core.envs.Env_;
import gplx.core.envs.System_;
import gplx.dbs.engines.sqlite.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.sql.*;
public class SqliteDbMain {
	public static void main(String[] args) throws Exception {
		SqliteDbMain main = new SqliteDbMain();
//		main.JdbcInit(args);
//		main.Read();
//		main.Mass_upload(Io_url_.new_dir_("J:\\gplx\\xowl\\file\\all#meta\\en.wikipedia.org\\"));
//		main.CreateMany(20, 0);
		main.CreateMany(20, 1000000 + 1);
	}// 179,167,161,147,160,165,159
/*
'5281' '189'
'5266' '189' 
 
'5640' '177'
'5719' '174'  
'5766' '173' 
*/

//	private void JdbcInit(String[] args) {
//		try {
//        Class.forName("SQLite.JDBCDriver");
//		}
//		catch (Exception e) {
//			ConsoleAdp._.WriteLine(e.getMessage());
//		}		
//	}
	private void CreateMany(int number, int base_val) {
		long time_bgn = System_.Ticks();
		Db_conn provider = Db_conn_pool.Instance.Get_or_new(Db_conn_info_.sqlite_(Io_url_.new_fil_("E:\\test.sqlite3")));		
		String tbl_sql = String_.Concat_lines_nl
		( "CREATE TABLE fsdb_xtn_thm"
		, "( thm_id            integer             NOT NULL    PRIMARY KEY"
		, ", thm_owner_id      integer             NOT NULL"
		, ", thm_w             integer             NOT NULL"
		, ", thm_h             integer             NOT NULL"
		, ", thm_thumbtime     integer             NOT NULL"
		, ", thm_bin_db_id     integer             NOT NULL"
		, ", thm_size          bigint              NOT NULL"
		, ", thm_modified      varchar(14)         NOT NULL"
		, ", thm_hash          varchar(40)         NOT NULL"
		, ");"
		);
		Sqlite_engine_.Tbl_create_and_delete(provider, "fsdb_xtn_thm", tbl_sql);
//		provider.Txn_mgr().Txn_bgn();
		Db_stmt stmt = Db_stmt_.new_insert_(provider, "fsdb_xtn_thm", "thm_id", "thm_owner_id", "thm_w", "thm_h", "thm_thumbtime", "thm_bin_db_id", "thm_size", "thm_modified", "thm_hash");
		for (int i = 0; i < number; i++) {
			stmt.Clear()
				.Val_int(base_val + i)
				.Val_int(base_val + i)
				.Val_int(220)
				.Val_int(200)
				.Val_int(-1)
				.Val_int(15)
				.Val_long(23456)
				.Val_str("")
				.Val_str("")
				.Exec_insert();
		}
		long time_elapsed = (System_.Ticks() - time_bgn);	
//		provider.Txn_mgr().Txn_end();
		provider.Rls_conn();
		Tfds.Write(time_elapsed, number / time_elapsed);
		// 250; 260
		Tfds.Write("");
	}
	Connection conn; PreparedStatement stmt;
	void Read() {
		try {
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:J:\\gplx\\xowl\\file\\all#meta\\en.wikipedia.org\\meta.db");
		Statement stat = conn.createStatement();
		
//		stat.executeUpdate("DROP TABLE temp;");
//		stat.executeUpdate("CREATE TABLE temp (ttl varchar(1024));");
//		PreparedStatement prep = conn.prepareStatement("INSERT INTO temp VALUES (?);");
//		conn.setAutoCommit(false);
//		prep.setString(1, "Rembrandt auto 1627.jpg"); prep.addBatch();
//		prep.setString(1, "Rembrandt van Rijn 184.jpg"); prep.addBatch();
//		prep.setString(1, "Rembrandt laughing.jpg"); prep.addBatch();
//		prep.setString(1, "Rembrandt van Rijn 199.jpg"); prep.addBatch();
//		prep.setString(1, "Rembrandt Harmensz. van Rijn 144.jpg"); prep.addBatch();
//		prep.setString(1, "Self-portrait at 34 by Rembrandt.jpg"); prep.addBatch();
//		prep.setString(1, "Selfportrait Rembrandt1641.jpg"); prep.addBatch();
//		prep.setString(1, "Rembrandt Harmensz. van Rijn 127b.jpg"); prep.addBatch();
//		prep.setString(1, "Rembrandt Harmensz. van Rijn 132.jpg"); prep.addBatch();
//		prep.setString(1, "Rembrandt - Self Portrait111.jpg"); prep.addBatch();
//		prep.setString(1, "Rembrandt self portrait.jpg"); prep.addBatch();
//		prep.setString(1, "Rembrant Self-Portrait, 1660.jpg"); prep.addBatch();
//		prep.setString(1, "Rembrandt van rijn-self portrait.jpg"); prep.addBatch();
//		prep.setString(1, "Rembrandt, Auto-portrait, 1660.jpg"); prep.addBatch();
//		prep.setString(1, "Rembrandt van Rijn 142 version 02.jpg"); prep.addBatch();
//		prep.setString(1, "Rembrandt Harmensz. van Rijn 135.jpg"); prep.addBatch();
//		prep.executeBatch();
//		conn.setAutoCommit(true);
//		ResultSet rs = stat.executeQuery("SELECT TOP 10 files.* FROM files JOIN temp ON files.ttl = temp.ttl;");
//		ResultSet rs = stat.executeQuery("SELECT files.* FROM files LIMIT 100;");
		ResultSet rs = stat.executeQuery("SELECT files.* FROM files WHERE ttl IN ('380CHANGI.jpg', '20120523Palmen_Hockenheim1.jpg') ;");
		while (rs.next()) {
		  System.out.println("ttl = " + rs.getString("ttl") + "; orig_w = " + rs.getString("orig_w") + "; orig_h = " + rs.getString("orig_h"));
		}
		rs.close();		
		}catch(Exception e) {
			Err_.Noop(e);
		}
	}
	void Index() {
		try {
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:J:\\gplx\\xowl\\file\\all#meta\\en.wikipedia.org\\meta.db");
		Statement stat = conn.createStatement();
		stat.executeUpdate("PRAGMA synchronous=OFF");
		stat.executeUpdate("PRAGMA count_changes=OFF");
		stat.executeUpdate("PRAGMA journal_mode=MEMORY");
		stat.executeUpdate("PRAGMA temp_store=MEMORY");
		conn.setAutoCommit(false);
		stat.executeUpdate("CREATE INDEX files_ndx ON files (ttl);");
		conn.commit();
		conn.setAutoCommit(true);
		}catch(Exception e) {
			Err_.Noop(e);
		}
	}
	void Mass_upload(Io_url dir) {
		try {
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:J:\\gplx\\xowl\\file\\all#meta\\en.wikipedia.org\\meta.db");
		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists files;");
		String sql = String_.Concat_lines_nl
		(	"CREATE TABLE files"
		,	"(	ttl				varchar(1024)"
		,	",	redirect		varchar(1024)"
		,	",	ext				int"
		,	",	orig_mode		int"
		,	",	orig_w			int"
		,	",	orig_h			int"
		,	",	thumbs			varchar(2048)"		// assuming 10 bytes per thumb, roughly 200 thumbs
		,	");"
		);
		stat.executeUpdate(sql);
	
		Console_adp__sys.Instance.Write_str_w_nl(Datetime_now.Get().XtoStr_fmt_yyyyMMdd_HHmmss_fff());
//		stat.executeUpdate("BEGIN TRANSACTION");
		stat.executeUpdate("PRAGMA synchronous=OFF");
		stat.executeUpdate("PRAGMA count_changes=OFF");
		stat.executeUpdate("PRAGMA journal_mode=MEMORY");
		stat.executeUpdate("PRAGMA temp_store=MEMORY");
		conn.setAutoCommit(false);
		stmt = conn.prepareStatement("insert into files values (?, ?, ?, ?, ?, ?, ?);");
		Iterate_dir(dir);
//		stat.executeUpdate("COMMIT TRANSACTION");
		stmt.executeBatch();
		conn.commit();
		conn.setAutoCommit(true);
		}catch(Exception e) {
			Err_.Noop(e);
		}
	}
	void Iterate_dir(Io_url dir) {
		Io_url[] urls = Io_mgr.Instance.QueryDir_args(dir).DirInclude_().ExecAsUrlAry();
		int urls_len = urls.length;
		Console_adp__sys.Instance.Write_str_w_nl(dir.Raw());
		boolean is_root = false;
		for (int i = 0; i < urls_len; i++) {
			Io_url url = urls[i];
			if (url.Type_dir())
				Iterate_dir(url);
			else {
				try {
					is_root = true;
					Insert_file(url);
				}catch(Exception e) {
					Err_.Noop(e);
				}
			}
		}
		try {
			if (is_root) {
				stmt.executeBatch();
				stmt.clearBatch();
			}			
		}catch(Exception e) {
			Err_.Noop(e);
		}
	}
	void Insert_file(Io_url url) {
		if (String_.EqNot(url.Ext(), ".csv")) return;
		String raw = Io_mgr.Instance.LoadFilStr(url);
		String[] lines = String_.SplitLines_nl(raw);
		int lines_len = lines.length;
		for (int i = 0; i < lines_len; i++) {
			String line = lines[i];
			Insert_line(line);
		}
	}
	void Insert_line(String line) {
		try {
		String[] flds = String_.Split(line, '|');
		int flds_len = flds.length;
		if (flds_len == 0) return;
		stmt.setString(1, flds[2]);
		if (flds_len == 4)
			stmt.setString(2, flds[3]);
		if (flds_len > 4) {
			stmt.setInt(3, Bry_.new_a7(flds[3])[0] - 32);
			byte[] orig = Bry_.new_a7(flds[4]);
			int orig_mode = orig[0] - Byte_ascii.Num_0;
			int comma_pos = Bry_find_.Find_fwd(orig, Byte_ascii.Comma);
			int orig_w = Bry_.To_int_or(orig, 2, comma_pos, -1);
			int orig_h = Bry_.To_int_or(orig, comma_pos + 1, orig.length, -1);
			stmt.setInt(4, orig_mode);			
			stmt.setInt(5, orig_w);			
			stmt.setInt(6, orig_h);
			if (flds_len > 5)
				stmt.setString(7, flds[4]);
		}
		stmt.addBatch();
		}catch(Exception e) {
			Err_.Noop(e);
		}
	}
}