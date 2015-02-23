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
		fxt.Exec_get(Xof_exec_arg.new_orig("A.ogg").Exec_tid_(Xof_exec_tid.Tid_viewer_app).Rslt_orig_found().Rslt_fsdb_null().Rslt_conv_n());
	}
}
