/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.files.fsdb.tsts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Xof_file_ext__webp_tst {
	@Before public void init() {fxt.Reset();} private final Xof_file_fxt fxt = new Xof_file_fxt();
	@After public void term() {fxt.Rls();}
	@Test  public void Make_orig() {
		fxt.Init_orig_db(Xof_orig_arg.new_comm("A.webp", 440, 400));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_orig("A.webp", 440, 400));
		fxt.Exec_get(Xof_exec_arg.new_orig("A.webp").Rslt_orig_exists_y().Rslt_file_exists_y().Rslt_file_resized_y());
		fxt.Test_fsys("mem/root/common/thumb/d/9/A.webp/440px.png", "440,400");
	}
	@Test  public void Make_thumb() {
		fxt.Init_orig_db(Xof_orig_arg.new_comm("A.webp", 440, 400));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_orig("A.webp", 440, 400));
		fxt.Exec_get(Xof_exec_arg.new_thumb("A.webp").Rslt_orig_exists_y().Rslt_file_exists_y().Rslt_file_resized_y());
		fxt.Test_fsys("mem/root/common/thumb/d/9/A.webp/220px.png", "220,200");
	}
}
