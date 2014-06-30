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
import gplx.fsdb.*;
import gplx.xowa.files.bins.*;
public class Xof_offline_ogg_tst {
	@Before public void init() {if (fxt.Db_skip()) return; fxt.Reset();} private Xof_file_fxt fxt = new Xof_file_fxt();
	@After public void term() {if (fxt.Db_skip()) return; fxt.Rls();}
//		@Test   public void Video() {	// disabled; .ogg will never be video; DATE:2014-02-02
//			if (fxt.Db_skip()) return;
//			fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_commons("A.ogg", 440, 400));
//			fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_commons_thumb("A.ogg", 440, 400));	// create a thumb at 440,400 (needed for preview of video)
//			fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_orig("A.ogg").Rslt_reg_missing_reg().Rslt_qry_mock_().Rslt_bin_fsdb_().Rslt_cnv_n_());
//			fxt.Test_fsys("mem/root/common/thumb/4/2/A.ogg/440px.jpg", "440,400");
//			Xof_fsdb_itm itm = fxt.Test_regy_pass("A.ogg");
//			fxt.Test_itm_ext(itm, Xof_ext_.Id_ogv);// chk that file is now ogv
//		}
	@Test   public void Audio() {
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_commons("A.ogg", 0, 0));	// audio has no size
		fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_commons_thumb("A.ogg", 440, 400));	// create a thumb but it should never be used
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_orig("A.ogg").Rslt_reg_missing_reg().Rslt_qry_noop_().Rslt_bin_(Xof_bin_wkr_.Tid_null).Rslt_cnv_n_());
		Xof_fsdb_itm itm = fxt.Test_regy_pass("A.ogg");
		fxt.Test_itm_ext(itm, Xof_ext_.Id_ogg);// chk that file is now oga
	}
}
