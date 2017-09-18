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
public class Xof_file_ext__ogv_tst {
	@Before public void init() {fxt.Reset();} private final Xof_file_fxt fxt = new Xof_file_fxt();
	@After public void term() {fxt.Rls();}
	@Test   public void Copy_orig() {
		fxt.Init_orig_db(Xof_orig_arg.new_comm("A.ogv", 440, 400));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_thumb("A.ogv", 440, 400));
		fxt.Exec_get(Xof_exec_arg.new_orig("A.ogv").Rslt_orig_exists_y().Rslt_file_exists_y().Rslt_file_resized_n());
		fxt.Test_fsys("mem/root/common/thumb/d/0/A.ogv/440px.jpg", "440,400");
	}
	@Test   public void Copy_orig_w_thumbtime() {
		fxt.Init_orig_db(Xof_orig_arg.new_comm("A.ogv", 440, 400));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_thumb("A.ogv", 440, 400, 10));
		fxt.Exec_get(Xof_exec_arg.new_orig("A.ogv").Lnki_time_(10).Rslt_orig_exists_y().Rslt_file_exists_y().Rslt_file_resized_n());
		fxt.Test_fsys("mem/root/common/thumb/d/0/A.ogv/440px-10.jpg", "440,400");
		// fxt.Exec_get(Xof_exec_arg.new_thumb("A.png", Xop_lnki_tkn.Width_null, 130)); DELETE: not needed; tests if new A.png can be resized from existing; DATE:2015-03-03
	}
}
