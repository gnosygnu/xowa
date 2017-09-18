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
package gplx.xowa.files.fsdb.tsts; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import org.junit.*;
public class Xof_file_ext__oga_tst {
	@Before public void init() {fxt.Reset();} private final Xof_file_fxt fxt = new Xof_file_fxt();
	@After public void term() {fxt.Rls();}
	@Test   public void Orig_page() {	// .oga is on page [[File:A.oga]]; check orig (since orig may redirect) but do not get file; (since file is not "viewable" immediately)
		fxt.Init_orig_db(Xof_orig_arg.new_comm_file("A.oga"));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_file("A.oga"));
		fxt.Exec_get(Xof_exec_arg.new_orig("A.oga").Rslt_orig_exists_y().Rslt_file_exists_n());
		fxt.Test_fsys_exists_n("mem/root/common/orig/4/f/A.oga");
	}
	@Test   public void Orig_app() {	// .oga is clicked; get file
		fxt.Init_orig_db(Xof_orig_arg.new_comm_file("A.oga"));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_file("A.oga"));
		fxt.Exec_get(Xof_exec_arg.new_orig("A.oga").Exec_tid_(Xof_exec_tid.Tid_viewer_app).Rslt_orig_exists_y().Rslt_file_exists_y());
		fxt.Test_fsys_exists_y("mem/root/common/orig/4/f/A.oga");
	}
}
