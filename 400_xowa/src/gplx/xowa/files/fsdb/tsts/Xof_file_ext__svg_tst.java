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
public class Xof_file_ext__svg_tst {
	@Before public void init() {fxt.Reset();} private final Xof_file_fxt fxt = new Xof_file_fxt();
	@After public void term() {fxt.Rls();}
	@Test   public void Make_orig() {
		fxt.Init_orig_db(Xof_orig_arg.new_comm("A.svg", 440, 400));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_orig("A.svg", 440, 400));
		fxt.Exec_get(Xof_exec_arg.new_orig("A.svg").Rslt_orig_found().Rslt_fsdb_xowa().Rslt_conv_y());
		fxt.Test_fsys("mem/root/common/thumb/7/5/A.svg/440px.png", "440,400");
	}
	@Test   public void Make_thumb() {
		fxt.Init_orig_db(Xof_orig_arg.new_comm("A.svg", 440, 400));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_orig("A.svg", 440, 400));
		fxt.Exec_get(Xof_exec_arg.new_thumb("A.svg").Rslt_orig_found().Rslt_fsdb_xowa().Rslt_conv_y());
		fxt.Test_fsys("mem/root/common/thumb/7/5/A.svg/220px.png", "220,200");
	}
	@Test  public void Thumb_can_be_bigger_than_orig() {// PURPOSE: svg thumbs allowed to exceed orig in size; EX: w:Portal:Music; [[File:Treble a.svg|left|160px]]
		fxt.Init__orig_w_fsdb__commons_orig("A.svg", 110, 100);							// create orig of 110,100
		fxt.Exec_get(Xof_exec_arg.new_thumb("A.svg", 330).Rslt_fsdb_xowa());	// get 330
		fxt.Test_fsys("mem/root/common/thumb/7/5/A.svg/330px.png", "330,300");
	}
}
