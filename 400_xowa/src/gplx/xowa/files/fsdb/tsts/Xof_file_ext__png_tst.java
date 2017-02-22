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
import org.junit.*; import gplx.xowa.parsers.lnkis.*;
public class Xof_file_ext__png_tst {
	@Before public void init() {fxt.Reset();} private final Xof_file_fxt fxt = new Xof_file_fxt();
	@After public void term() {fxt.Rls();}
	@Test   public void Copy_thumb() {
		fxt.Init_orig_db(Xof_orig_arg.new_comm("A.png", 440, 400));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_thumb("A.png"));
		fxt.Exec_get(Xof_exec_arg.new_thumb("A.png").Rslt_orig_exists_y().Rslt_file_exists_y());
		fxt.Test_fsys("mem/root/common/thumb/7/0/A.png/220px.png", "220,200");
	}
	@Test   public void Copy_orig() {
		fxt.Init__orig_w_fsdb__commons_orig("A.png", 440, 400);
		fxt.Exec_get(Xof_exec_arg.new_orig("A.png").Rslt_orig_exists_y().Rslt_file_exists_y());
		fxt.Test_fsys("mem/root/common/orig/7/0/A.png", "440,400");
	}
	@Test   public void Copy_orig_w_width() {	// PURPOSE: if not thumb, but width is specified; do not download orig and convert; EX: [[File:The Earth seen from Apollo 17.jpg|250px]]
		fxt.Init_orig_db(Xof_orig_arg.new_comm("A.png", 440, 400));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_thumb("A.png", 220, 200));
		fxt.Exec_get(Xof_exec_arg.new_("A.png", Xop_lnki_type.Id_null, 220, Xop_lnki_tkn.Height_null).Rslt_orig_exists_y().Rslt_file_exists_y());
		fxt.Test_fsys("mem/root/common/thumb/7/0/A.png/220px.png", "220,200");
	}
	@Test   public void Make_thumb() {
		fxt.Init__orig_w_fsdb__commons_orig("A.png", 440, 400);
		fxt.Exec_get(Xof_exec_arg.new_thumb("A.png").Rslt_orig_exists_y().Rslt_file_exists_y().Rslt_file_resized_y());
		fxt.Test_fsys("mem/root/common/thumb/7/0/A.png/220px.png", "220,200");
	}
	@Test  public void Height_only() {	// PURPOSE.fix: height only was still using old infer-size code; EX:w:[[File:Upper and Middle Manhattan.jpg|x120px]]; DATE:2012-12-27
		fxt.Init_orig_db(Xof_orig_arg.new_comm("A.png", 12591, 1847));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_thumb("A.png", 887, 130));
		fxt.Exec_get(Xof_exec_arg.new_thumb("A.png", Xop_lnki_tkn.Width_null, 130));
		fxt.Test_fsys("mem/root/common/thumb/7/0/A.png/887px.png", "887,130");
	}
	@Test  public void Width_only_height_ignored() {// PURPOSE.fix: if height is not specified, do not recalc; needed when true scaled height is 150x151 but WM has 150x158; defect would discard 150x158; EX:[[File:Tokage_2011-07-15.jpg|150px]] simple.wikipedia.org/wiki/2011_Pacific_typhoon_season; DATE:2013-06-03
		fxt.Init_orig_db(Xof_orig_arg.new_comm("A.png", 4884, 4932));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_thumb("A.png", 150, 150));
		fxt.Exec_get(Xof_exec_arg.new_thumb("A.png", 150, Xop_lnki_tkn.Height_null));
		fxt.Test_fsys("mem/root/common/thumb/7/0/A.png/150px.png", "150,150");
	}
	@Test  public void Upright() {	// PURPOSE.fix: upright not working;  EX: w:Beethoven; [[File:Rudolf-habsburg-olmuetz.jpg|thumb|upright|]]
		fxt.Init_orig_db(Xof_orig_arg.new_comm("A.png", 1378, 1829));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_thumb("A.png", 170, 226));
		fxt.Exec_get(Xof_exec_arg.new_thumb("A.png", Xop_lnki_tkn.Width_null, Xop_lnki_tkn.Height_null).Lnki_upright_(Xof_img_size.Upright_default_marker));
		fxt.Test_fsys("mem/root/common/thumb/7/0/A.png/170px.png", "170,226");
	}
}
