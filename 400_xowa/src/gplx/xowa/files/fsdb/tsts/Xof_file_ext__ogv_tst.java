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
