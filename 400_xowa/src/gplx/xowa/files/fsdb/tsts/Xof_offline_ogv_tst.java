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
public class Xof_offline_ogv_tst {
	@Before public void init() {if (fxt.Db_skip()) return; fxt.Reset();} private Xof_file_fxt fxt = new Xof_file_fxt();
	@After public void term() {if (fxt.Db_skip()) return; fxt.Rls();}
	@Test   public void Copy_orig() {
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_commons("A.ogv", 440, 400));
		fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_commons_thumb("A.ogv", 440, 400));
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_orig("A.ogv").Rslt_reg_missing_reg().Rslt_qry_mock_().Rslt_bin_fsdb_().Rslt_cnv_n_());
		fxt.Test_fsys("mem/root/common/thumb/d/0/A.ogv/440px.jpg", "440,400");
		fxt.Test_regy_pass("A.ogv");
	}
	@Test   public void Copy_orig_w_thumbtime() {
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_commons("A.ogv", 440, 400));
		fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_commons_thumb("A.ogv", 440, 400, 10));
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_orig("A.ogv").Lnki_thumbtime_(10).Rslt_reg_missing_reg().Rslt_qry_mock_().Rslt_bin_fsdb_().Rslt_cnv_n_());
		fxt.Test_fsys("mem/root/common/thumb/d/0/A.ogv/440px-10.jpg", "440,400");
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_thumb("A.png", Xop_lnki_tkn.Width_null, 130));
		fxt.Test_regy_pass("A.ogv");
	}
}
