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
import gplx.String_;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.sql.*;
//public class Bug_Utf8 {
//	public static void main(String[] s) throws SQLException, java.io.UnsupportedEncodingException {
//		// init connection
//		Connection conn = DriverManager.getConnection
//			( "jdbc:mysql://localhost/bug_utf8?characterEncoding=UTF8"
////			( "jdbc:mysql://localhost/bug_utf8?useUnicode=true&characterEncoding=UTF8&character_set_client=UTF8&character_set_database=UTF8&character_set_results=UTF8&character_set_server=UTF8&character_set_system=UTF8"
//			, "root"
//			, "mysql7760"
//			
//		);		
//		
//		// retrieve resultSet
//		Statement cmd = conn.createStatement();
//		cmd.execute("select * from simple_table;");
//		ResultSet rdr = cmd.getResultSet();
//		
//		// get value
//		rdr.next();
//		String name = rdr.getNString("name");
//
//		// output results
//		System.out.println("name=" + name + " len=" + name.getBytes().length + " 0=" + name.getBytes()[0]);
//		System.out.println("name=" + name + " len=" + name.getBytes("UTF8").length + " 0=" + name.getBytes("UTF8")[0] + " 1=" + name.getBytes("UTF8")[1]);
//
//		Charset utf8charset = Charset.forName("UTF-8");
//		Charset iso88591charset = Charset.forName("ISO-8859-1");
//
//		ByteBuffer inputBuffer = ByteBuffer.wrap(name.getBytes());
//
//		// decode ISO-8559-1
//		CharBuffer data = iso88591charset.decode(inputBuffer);
//
//		// encode UTF-8
//		ByteBuffer outputBuffer = utf8charset.encode(data);
//		byte[] outputData = outputBuffer.array();
//		name = new String(outputData, "UTF-8");
//		System.out.println("name=" + name + " len=" + name.getBytes().length + " 0=" + name.getBytes()[0]);
//		System.out.println("name=" + name + " len=" + name.getBytes("UTF8").length + " 0=" + name.getBytes("UTF8")[0] + " 1=" + name.getBytes("UTF8")[1]);
//	}
//}
/*
Hi all. The topic is pretty straightforward, but I've been staring at it for quite some time.

I'm trying to retrieve non-English characters from a MySQL database in UTF-8. In my example below, I use "à" (U+00E0: Latin Small Letter A With Grave) but I've also tried with random Japanese characters (Hex=E7A798). I'm new to java/jdbc, so I may be missing something basic, but I've searched for quite a while, and not discovered anything.

I've made sure that my database was created in UTF-8, and that my connection is in UTF8. I've tried enabling all jdbc connection string options (see commented line below) and it makes no difference. I've also tried System.setProperty("file.encoding", "UTF-8");.

The odd thing is that somehow the code below works when I run it from a JUnit test. (the actual results match the expected ones)
Any help would be appreciated. Thanks in advance.

[list]SQL to create data[/list]
[code]
CREATE DATABASE bug_utf8 CHARACTER SET utf8 COLLATE utf8_general_ci;
USE bug_utf8;
DROP TABLE IF EXISTS simple_table;
CREATE TABLE simple_table (name	varchar(255) NOT NULL);
INSERT INTO simple_table (name) VALUES ('à');
SELECT Hex(name) from simple_table; -- returns C3A0
[/code]

[list]Java code[/list]
[code]
import java.sql.*;
public class Bug_Utf8 {
	public static void main(String[] s) throws SQLException, java.io.UnsupportedEncodingException {
		// init connection
		Connection conn = DriverManager.getConnection
			( "jdbc:mysql://localhost/bug_utf8?useUnicode=true&characterEncoding=UTF8"
//			( "jdbc:mysql://localhost/bug_utf8?useUnicode=true&characterEncoding=UTF8&character_set_client=UTF8&character_set_database=UTF8&character_set_results=UTF8&character_set_server=UTF8&character_set_system=UTF8"
			, "root"
			, "yourpassword"
		);		
		
		// retrieve resultSet
		Statement cmd = conn.createStatement();
		cmd.execute("select * from simple_table;");
		ResultSet rdr = cmd.getResultSet();
		
		// get value
		rdr.next();
		String name = rdr.getNString("name");

		// output results
		// actual
		System.out.println("name=" + name + " len=" + name.getBytes().length + " 0=" + name.getBytes()[0]);

		// expecting: C3 A0
		System.out.println("name=" + name + " len=" + name.getBytes("UTF8").length + " 0=" + name.getBytes("UTF8")[0] + " 1=" + name.getBytes("UTF8")[1]);
	}
}
[/code]
[list]Environment Details[/list]
OS: Windows XP SP3
MySQL: Server version: 5.1.40-community MySQL Community Server (GPL)
Java: java version "1.6.0_20"
JDBC: 5.1.12
JUnit: 4_4.5 v20090824
IDE: Eclipse 20090920-1017 (not that it should matter)
*/
import java.sql.*;

public class Bug_Utf8 {
	public static void main(String[] args) throws Exception {
		Like();
	}
	public static void Utf8() throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists people;");
		stat.executeUpdate("create table people (name, occupation);");
		PreparedStatement prep = conn.prepareStatement("insert into people values (?, ?);");
		
		prep.setString(1, "Gandhi");
		prep.setString(2, "politics");
		prep.addBatch();
		prep.setString(1, "Turing");
		prep.setString(2, "computers");
		prep.addBatch();
		prep.setString(1, "Wittgenstein");
		prep.setString(2, "smartypants");
		prep.addBatch();
		
		conn.setAutoCommit(false);
		prep.executeBatch();
		conn.setAutoCommit(true);
		
		ResultSet rs = stat.executeQuery("select * from people;");
		while (rs.next()) {
			System.out.println("name = " + rs.getString("name"));
			System.out.println("job = " + rs.getString("occupation"));
		}
		rs.close();
		conn.close();
	}
	private static void Like() throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/xowa/wiki/en.wikipedia.org/en.wikipedia.org-xtn.search.link-title-ns.000-db.001.xowa");
		conn.createStatement().execute("ATTACH DATABASE 'C:\\xowa\\wiki\\en.wikipedia.org\\en.wikipedia.org-core.xowa' AS page_db");
		conn.createStatement().execute("ATTACH DATABASE 'C:\\xowa\\wiki\\en.wikipedia.org\\en.wikipedia.org-xtn.search.core.xowa' AS word_db");

		String sql = "SELECT * FROM word_db.search_word INDEXED BY temp WHERE word_text LIKE 'bug%' AND link_score_max > 0 AND link_score_min < 1000000 LIMIT 1";
		PreparedStatement stat = conn.prepareStatement(sql);
		
		ResultSet rs = stat.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getObject(1).toString());
		}
		rs.close();
		conn.close();
	}
}