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
