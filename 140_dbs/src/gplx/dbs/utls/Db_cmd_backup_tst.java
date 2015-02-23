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
package gplx.dbs.utls; import gplx.*; import gplx.dbs.*;
import org.junit.*;
public class Db_cmd_backup_tst {
	@Test  public void Basic() {
		Tfds.Now_enabled_y_();
		Db_cmd_backup bkpWkr = Db_cmd_backup.new_()
			.ExeUrl_(Io_url_.new_any_("C:\\mysql\\mysqldump.exe"))
			.BkpDir_(Io_url_.new_any_("C:\\bkp\\"))
			.Usr_("username")
			.Pwd_("password")
			.DbName_("dbname").InitVars();
		Tfds.Eq("\"C:\\mysql\\mysqldump.exe\" -u username -ppassword dbname > C:\\bkp\\dbname_20010101_0000.sql", bkpWkr.CmdText());
	}
}
