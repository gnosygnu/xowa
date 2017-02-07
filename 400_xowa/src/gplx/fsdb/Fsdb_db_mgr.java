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
package gplx.fsdb; import gplx.*;
import gplx.dbs.*; import gplx.xowa.files.origs.*;
public interface Fsdb_db_mgr {
	boolean				File__schema_is_1();
	boolean				File__solo_file();
	String				File__cfg_tbl_name();
	Xof_orig_tbl[]		File__orig_tbl_ary();
	Fsdb_db_file		File__mnt_file();
	Fsdb_db_file		File__abc_file__at(int mnt_id);
	Fsdb_db_file		File__atr_file__at(int mnt_id);
	Fsdb_db_file		File__bin_file__at(int mnt_id, int bin_id, String file_name);
	Fsdb_db_file		File__bin_file__new(int mnt_id, String file_name);
}
