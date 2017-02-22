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
import gplx.xowa.files.bins.*;
public class Xof_file_ext__ogg_tst {
	@Before public void init() {fxt.Reset();} private final Xof_file_fxt fxt = new Xof_file_fxt();
	@After public void term() {fxt.Rls();}
//		@Test   public void Video() {	// disabled; .ogg will never be video; DATE:2014-02-02
//			fxt.Init_orig_db(Xof_orig_arg.new_comm("A.ogg", 440, 400));
//			fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_thumb("A.ogg", 440, 400));	// create a thumb at 440,400 (needed for preview of video)
//			fxt.Exec_get(Xof_exec_arg.new_orig("A.ogg").Rslt_orig_missing().Rslt_fsdb_xowa().Rslt_conv_n());
//			fxt.Test_fsys("mem/root/common/thumb/4/2/A.ogg/440px.jpg", "440,400");
//			Xof_fsdb_itm itm = fxt.Test_regy_pass("A.ogg");
//			fxt.Test_itm_ext(itm, Xof_ext_.Id_ogv);// chk that file is now ogv
//		}
	@Test   public void Audio() {
		fxt.Init_orig_db(Xof_orig_arg.new_comm("A.ogg", 0, 0));	// audio has no size
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_thumb("A.ogg", 440, 400));	// create a thumb but it should never be used
		fxt.Exec_get(Xof_exec_arg.new_orig("A.ogg").Exec_tid_(Xof_exec_tid.Tid_viewer_app).Rslt_orig_exists_y().Rslt_file_exists_n().Rslt_file_resized_n());
	}
}
