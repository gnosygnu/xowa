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
import org.junit.*;
public class Xof_file_redirect_tst {
	@Before public void init() {fxt.Reset();} private final Xof_file_fxt fxt = new Xof_file_fxt();
	@After public void term() {fxt.Rls();}
	@Test  public void Same_wiki_orig_copy() {
		fxt.Init_orig_db(Xof_orig_arg.new_wiki("A.png", 440, 400));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_wiki_orig("A.png", 440, 400));
		fxt.Init_orig_db(Xof_orig_arg.new_wiki_redirect("B.png", "A.png"));
		fxt.Exec_get(Xof_exec_arg.new_orig("B.png").Rslt_file_exists_y());
		fxt.Test_fsys("mem/root/enwiki/orig/7/0/A.png", "440,400");
	}
	@Test  public void Same_wiki_thumb_copy() {
		fxt.Init_orig_db(Xof_orig_arg.new_wiki("A.png", 440, 400));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_wiki_thumb("A.png", 220, 200));
		fxt.Init_orig_db(Xof_orig_arg.new_wiki_redirect("B.png", "A.png"));
		fxt.Exec_get(Xof_exec_arg.new_thumb("B.png", 220).Rslt_file_exists_y());
		fxt.Test_fsys("mem/root/enwiki/thumb/7/0/A.png/220px.png", "220,200");
	}
	@Test  public void Same_wiki_thumb_make() {
		fxt.Init_orig_db(Xof_orig_arg.new_wiki("A.png", 440, 400));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_wiki_orig("A.png", 440, 400));
		fxt.Init_orig_db(Xof_orig_arg.new_wiki_redirect("B.png", "A.png"));
		fxt.Exec_get(Xof_exec_arg.new_thumb("B.png", 220).Rslt_file_exists_y().Rslt_file_resized_y());
		fxt.Test_fsys("mem/root/enwiki/thumb/7/0/A.png/220px.png", "220,200");
	}
	@Test  public void Diff_wiki_orig_copy() {
		fxt.Init_orig_db(Xof_orig_arg.new_comm("A.png", 440, 400));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_orig("A.png", 440, 400));
		fxt.Init_orig_db(Xof_orig_arg.new_comm_redirect("B.png", "A.png"));
		fxt.Exec_get(Xof_exec_arg.new_orig("B.png").Rslt_file_exists_y());
		fxt.Test_fsys("mem/root/common/orig/7/0/A.png", "440,400");
	}
//		@Test  public void Cross_wiki() {
//			fxt.Init__orig_w_fsdb__commons_orig("A.png", 440, 400);
//			fxt.Init_orig_db(Xof_orig_arg.new_wiki_redirect("B.png", "A.png"));
//			fxt.Exec_get(Xof_exec_arg.new_orig("B.png").Rslt_fsdb_xowa());
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
