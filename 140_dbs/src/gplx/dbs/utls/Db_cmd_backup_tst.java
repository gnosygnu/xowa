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
package gplx.dbs.utls; import gplx.*; import gplx.dbs.*;
import org.junit.*;
public class Db_cmd_backup_tst {
	@Test  public void Basic() {
		Datetime_now.Manual_y_();
		Db_cmd_backup bkpWkr = Db_cmd_backup.new_()
			.ExeUrl_(Io_url_.new_any_("C:\\mysql\\mysqldump.exe"))
			.BkpDir_(Io_url_.new_any_("C:\\bkp\\"))
			.Usr_("username")
			.Pwd_("password")
			.DbName_("dbname").InitVars();
		Tfds.Eq("\"C:\\mysql\\mysqldump.exe\" -u username -ppassword dbname > C:\\bkp\\dbname_20010101_0000.sql", bkpWkr.CmdText());
	}
}
