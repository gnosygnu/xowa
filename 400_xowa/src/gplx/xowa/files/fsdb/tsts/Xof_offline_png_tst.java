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
public class Xof_offline_png_tst {
	@Before public void init() {if (fxt.Db_skip()) return; fxt.Reset(Bool_.__byte);} private Xof_file_fxt fxt = new Xof_file_fxt();
	@After public void term() {if (fxt.Db_skip()) return; fxt.Rls();}
	@Test   public void Copy_thumb() {
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_commons("A.png", 440, 400));
		fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_commons_thumb("A.png"));
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_thumb("A.png").Rslt_reg_missing_reg().Rslt_qry_mock_().Rslt_bin_fsdb_());
		fxt.Test_fsys("mem/root/common/thumb/7/0/A.png/220px.png", "220,200");
		fxt.Test_regy("A.png", Xof_fsdb_arg_reg_get.new_().Init_commons(440, 400));
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_thumb("A.png").Rslt_reg_found_orig());	// get it again; make sure url is found in reg
	}
	@Test   public void Copy_orig() {
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_commons("A.png", 440, 400));
		fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_commons_orig("A.png", 440, 400));
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_orig("A.png").Rslt_reg_missing_reg().Rslt_qry_mock_().Rslt_bin_fsdb_());
		fxt.Test_fsys("mem/root/common/orig/7/0/A.png", "440,400");
		fxt.Test_regy_pass("A.png");
	}
	@Test   public void Copy_orig_w_width() {	// PURPOSE: type if not thumb, but width is specified; do not download orig and convert; EX: [[File:The Earth seen from Apollo 17.jpg|250px]]
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_commons("A.png", 440, 400));
		fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_commons_thumb("A.png", 220, 200));
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init("A.png", Xop_lnki_type.Id_null, 220, Xop_lnki_tkn.Height_null).Rslt_reg_missing_reg().Rslt_qry_mock_().Rslt_bin_fsdb_());
		fxt.Test_fsys("mem/root/common/thumb/7/0/A.png/220px.png", "220,200");
		fxt.Test_regy_pass("A.png");
	}
	@Test   public void Make_thumb() {
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa__bin_fsdb__commons_orig("A.png", 440, 400);
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_thumb("A.png").Rslt_reg_missing_reg().Rslt_qry_mock_().Rslt_bin_fsdb_().Rslt_cnv_y_());
		fxt.Test_fsys("mem/root/common/thumb/7/0/A.png/220px.png", "220,200");
		fxt.Test_regy_pass("A.png");
	}
	@Test  public void Height_only() {	// PURPOSE.fix: height only was still using old infer-size code; EX:w:[[File:Upper and Middle Manhattan.jpg|x120px]]; DATE:2012-12-27
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_commons("A.png", 12591, 1847));
		fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_commons_thumb("A.png", 887, 130));
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_thumb("A.png", Xop_lnki_tkn.Width_null, 130));
		fxt.Test_fsys("mem/root/common/thumb/7/0/A.png/887px.png", "887,130");
	}
	@Test  public void Width_only_height_ignored() {// PURPOSE.fix: if height is not specified, do not recalc; needed when true scaled height is 150x151 but WM has 150x158; defect would discard 150x158; EX:[[File:Tokage_2011-07-15.jpg|150px]] simple.wikipedia.org/wiki/2011_Pacific_typhoon_season; DATE:2013-06-03
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_commons("A.png", 4884, 4932));
		fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_commons_thumb("A.png", 150, 150));
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_thumb("A.png", 150, Xop_lnki_tkn.Height_null));
		fxt.Test_fsys("mem/root/common/thumb/7/0/A.png/150px.png", "150,150");
	}
	@Test  public void Upright() {	// PURPOSE.fix: upright not working;  EX: w:Beethoven; [[File:Rudolf-habsburg-olmuetz.jpg|thumb|upright|]]
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_commons("A.png", 1378, 1829));
		fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_commons_thumb("A.png", 170, 226));
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_thumb("A.png", Xop_lnki_tkn.Width_null, Xop_lnki_tkn.Height_null).Lnki_upright_(Xof_img_size.Upright_default));
		fxt.Test_fsys("mem/root/common/thumb/7/0/A.png/170px.png", "170,226");
	}
}
