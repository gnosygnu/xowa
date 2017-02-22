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
package gplx.xowa.files.xfers; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import org.junit.*;
import gplx.core.ios.*; import gplx.gfui.*; import gplx.xowa.files.*;
public class Xof_xfer_queue_html_basic_tst {
	Xof_xfer_queue_html_fxt fxt = new Xof_xfer_queue_html_fxt();
	@Before public void init() {fxt.Clear(true);}
	@Test  public void Main_orig() {
		fxt	.ini_page_create_en_wiki("File:A.png");
		fxt	.Lnki_orig_("A.png")
			.Src(	fxt.img_("mem/src/en.wikipedia.org/7/70/A.png", 900, 800))
			.Trg(	fxt.img_("mem/trg/en.wikipedia.org/raw/7/0/A.png", 900, 800)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|y||1?900,800|")
				);
		fxt.tst();
	}
	@Test  public void Main_thumb_download() {
		fxt	.ini_page_create_en_wiki("File:A.png");
		fxt	.Lnki_thumb_("A.png", 90)
			.Src(	fxt.img_("mem/src/en.wikipedia.org/thumb/7/70/A.png/90px-A.png", 90, 80))
			.Trg(	fxt.img_("mem/trg/en.wikipedia.org/fit/7/0/A.png/90px.png", 90, 80)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|y||2?0,0|1?90,80")
				);
		fxt.tst();
	}
	@Test  public void Main_thumb_convert() {
		fxt	.ini_page_create_en_wiki("File:A.png");
		fxt	.Lnki_thumb_("A.png", 90)
			.Src(	fxt.img_("mem/src/en.wikipedia.org/7/70/A.png", 900, 800))
			.Trg(	fxt.img_("mem/trg/en.wikipedia.org/raw/7/0/A.png", 900, 800)
				,	fxt.img_("mem/trg/en.wikipedia.org/fit/7/0/A.png/90px.png", 90, 80)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|y||1?900,800|1?90,80")
				);
		fxt.tst();
	}
	@Test  public void Ptr_orig() {
		fxt	.ini_page_create_en_wiki			("File:A.png");
		fxt	.ini_page_create_en_wiki_redirect	("File:B.png", "File:A.png");
		fxt	.Lnki_orig_("B.png")
			.Src(	fxt.img_("mem/src/en.wikipedia.org/7/70/A.png", 900, 800))
			.Trg(	fxt.img_("mem/trg/en.wikipedia.org/raw/7/0/A.png", 900, 800)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/5/57.csv", "B.png|y|A.png|1?900,800|")
				);
		fxt.tst();
		fxt	.Lnki_orig_("B.png")
			.Html_src_("file:///mem/trg/en.wikipedia.org/raw/7/0/A.png")
			.tst();
	}
	@Test  public void Ptr_thumb_download() {
		fxt	.ini_page_create_en_wiki			("File:A.png");
		fxt	.ini_page_create_en_wiki_redirect	("File:B.png", "File:A.png");
		fxt	.Lnki_thumb_("B.png", 90)
			.Src(	fxt.img_("mem/src/en.wikipedia.org/thumb/7/70/A.png/90px-A.png", 90, 80))
			.Trg(	fxt.img_("mem/trg/en.wikipedia.org/fit/7/0/A.png/90px.png", 90, 80)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/5/57.csv", "B.png|y|A.png|2?0,0|1?90,80")
				);
		fxt.tst();
	}
	@Test  public void Ptr_thumb_convert() {
		fxt	.ini_page_create_en_wiki			("File:A.png");
		fxt	.ini_page_create_en_wiki_redirect	("File:B.png", "File:A.png");
		fxt	.Lnki_thumb_("B.png", 90)
			.Src(	fxt.img_("mem/src/en.wikipedia.org/7/70/A.png", 900, 800))
			.Trg(	fxt.img_("mem/trg/en.wikipedia.org/raw/7/0/A.png", 900, 800)
				,	fxt.img_("mem/trg/en.wikipedia.org/fit/7/0/A.png/90px.png", 90, 80)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/5/57.csv", "B.png|y|A.png|1?900,800|1?90,80")
				);
		fxt.tst();
	}
	@Test  public void Vrtl_orig() {
		fxt	.ini_page_create_commons			("File:A.png");
		fxt	.Lnki_orig_("A.png")
			.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 900, 800))
			.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 900, 800)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"		, "A.png|0||1?900,800|")
				);
		fxt.tst();
	}
	@Test  public void Vrtl_thumb_download() {
		fxt	.ini_page_create_commons			("File:A.png");
		fxt	.Lnki_thumb_("A.png", 90)
			.Src(	fxt.img_("mem/src/commons.wikimedia.org/thumb/7/70/A.png/90px-A.png", 90, 80))
			.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/0/A.png/90px.png", 90, 80)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"		, "A.png|0||2?0,0|1?90,80")
				);
		fxt.tst();
	}
	@Test  public void Vrtl_thumb_convert() {
		fxt	.ini_page_create_commons			("File:A.png");
		fxt	.Lnki_thumb_("A.png", 90)
			.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 900, 800))
			.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 900, 800)
				,	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/0/A.png/90px.png", 90, 80)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"		, "A.png|0||1?900,800|1?90,80")
				);
		fxt.tst();
	}
	@Test  public void Vrtl_ptr_orig() {
		fxt	.ini_page_create_commons_redirect	("File:B.png", "File:A.png");
		fxt	.ini_page_create_commons			("File:A.png");
		fxt	.Lnki_orig_("B.png")
			.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 900, 800))
			.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 900, 800)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/5/57.csv"		, "B.png|0|A.png|1?900,800|")
				);
		fxt.tst();
	}
	@Test  public void Ptr_vrtl_orig() {
		fxt	.ini_page_create_en_wiki_redirect	("File:B.png", "File:A.png");
		fxt	.ini_page_create_commons			("File:A.png");
		fxt	.Lnki_orig_("B.png")
			.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 900, 800))
			.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 900, 800)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/5/57.csv"		, "B.png|y|A.png|1?900,800|")
				);
		fxt.tst();
	}
}
