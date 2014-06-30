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
public class Xof_offline_redirect_tst {
	@Before public void init() {if (fxt.Db_skip()) return; fxt.Reset();} private Xof_file_fxt fxt = new Xof_file_fxt();
	@After public void term() {if (fxt.Db_skip()) return; fxt.Rls();}
	@Test  public void Same_wiki_orig_copy() {
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_en_wiki("A.png", 440, 400));
		fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_en_wiki_orig("A.png", 440, 400));
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_en_wiki_redirect("B.png", "A.png"));
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_orig("B.png").Rslt_bin_fsdb_());
		fxt.Test_fsys("mem/root/enwiki/orig/7/0/A.png", "440,400");
		fxt.Test_regy("B.png", Xof_fsdb_arg_reg_get.new_().Init_en_wiki(440, 400, "A.png"));
	}
	@Test  public void Same_wiki_thumb_copy() {
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_en_wiki("A.png", 440, 400));
		fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_en_wiki_thumb("A.png", 220, 200));
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_en_wiki_redirect("B.png", "A.png"));
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_thumb("B.png", 220).Rslt_bin_fsdb_());
		fxt.Test_fsys("mem/root/enwiki/thumb/7/0/A.png/220px.png", "220,200");
	}
	@Test  public void Same_wiki_thumb_make() {
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_en_wiki("A.png", 440, 400));
		fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_en_wiki_orig("A.png", 440, 400));
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_en_wiki_redirect("B.png", "A.png"));
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_thumb("B.png", 220).Rslt_bin_fsdb_().Rslt_cnv_y_());
		fxt.Test_fsys("mem/root/enwiki/thumb/7/0/A.png/220px.png", "220,200");
	}
	@Test  public void Diff_wiki_orig_copy() {
		if (fxt.Db_skip()) return;
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_commons("A.png", 440, 400));
		fxt.Init_bin_fsdb(Xof_fsdb_arg_init_bin.new_().Init_commons_orig("A.png", 440, 400));
		fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_commons_redirect("B.png", "A.png"));
		fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_orig("B.png").Rslt_bin_fsdb_());
		fxt.Test_fsys("mem/root/common/orig/7/0/A.png", "440,400");
		fxt.Test_regy("B.png", Xof_fsdb_arg_reg_get.new_().Init_commons(440, 400, "A.png"));
	}
//		@Test  public void Cross_wiki() {
//			fxt.Init_qry_xowa__bin_fsdb__commons_orig("A.png", 440, 400);
//			fxt.Init_qry_xowa(Xof_fsdb_arg_init_qry.new_().Init_en_wiki_redirect("B.png", "A.png"));
//			fxt.Exec_get(Xof_fsdb_arg_exec_get.new_().Init_orig("B.png").Rslt_bin_fsdb_());
//			fxt.Test_fsys("mem/root/common/orig/7/0/A.png", "440,400");

//			fxt	.ini_page_create_en_wiki_redirect	("File:B.png", "File:A.png");
//			fxt	.ini_page_create_commons			("File:A.png");
//			fxt	.Lnki_orig_("B.png")
//				.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 900, 800))
//				.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 900, 800)
//					,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/5/57.csv"		, "B.png|y|A.png|1?900,800|")
//					);
//			fxt.tst();
//		}
}
