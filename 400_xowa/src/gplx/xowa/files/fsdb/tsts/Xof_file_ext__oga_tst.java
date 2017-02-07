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
