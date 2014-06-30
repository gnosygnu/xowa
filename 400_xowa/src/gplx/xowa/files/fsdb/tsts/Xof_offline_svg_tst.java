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
public class Xof_offline_svg_tst {
	@Before public void init() {if (fxt.Db_skip()) return; fxt.Reset();} private Xof_file_fxt fxt = new Xof_file_fxt();
	@After public void term() {if (fxt.Db_skip()) return; fxt.Rls();}
	@Test   public void Make_orig() {
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_commons("A.svg", 440, 400));
		fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_commons_orig("A.svg", 440, 400));
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_orig("A.svg").Rslt_reg_missing_reg().Rslt_qry_mock_().Rslt_bin_fsdb_().Rslt_cnv_y_());
		fxt.Test_fsys("mem/root/common/thumb/7/5/A.svg/440px.png", "440,400");
		fxt.Test_regy_pass("A.svg");
	}
	@Test   public void Make_thumb() {
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_commons("A.svg", 440, 400));
		fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_commons_orig("A.svg", 440, 400));
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_thumb("A.svg").Rslt_reg_missing_reg().Rslt_qry_mock_().Rslt_bin_fsdb_().Rslt_cnv_y_());
		fxt.Test_fsys("mem/root/common/thumb/7/5/A.svg/220px.png", "220,200");
		fxt.Test_regy_pass("A.svg");
	}
	@Test  public void Thumb_can_be_bigger_than_orig() {// PURPOSE: svg thumbs allowed to exceed orig in size; EX: w:Portal:Music; [[File:Treble a.svg|left|160px]]
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa__bin_fsdb__commons_orig("A.svg", 110, 100);							// create orig of 110,100
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_thumb("A.svg", 330).Rslt_bin_fsdb_());	// get 330
		fxt.Test_fsys("mem/root/common/thumb/7/5/A.svg/330px.png", "330,300");
	}
}
