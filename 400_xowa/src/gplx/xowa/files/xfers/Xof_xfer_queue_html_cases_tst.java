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
import gplx.xowa.files.*; import gplx.xowa.files.imgs.*;
public class Xof_xfer_queue_html_cases_tst {
	Xof_xfer_queue_html_fxt fxt = new Xof_xfer_queue_html_fxt();
	@Before public void init() {
		fxt.Clear(true);
	}
	@Test  public void Png_missing() {
		fxt	.Lnki_orig_("A.png")
			.Trg(	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|z||0?0,0|")
				);
		fxt.tst();
	}
	@Test  public void Png_missing_2() {	// PURPOSE: orig is missing; do not download again; NOTE: simulating "do not download again" check by putting in thumb and making sure it doesn't get downloaded
		fxt.save_(fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|z||0?0,0|"));	// save reg file and mark file as missing
		fxt	.Lnki_thumb_("A.png", 90)
			.Src(	fxt.img_("mem/src/commons.wikimedia.org/thumb/7/70/A.png/90px-A.png", 90, 80))
			.Trg(	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|z||0?0,0|")	// NOTE: 90,80 should not show up
				);
		fxt.tst();
	}
	@Test  public void Png_encode() {	// PURPOSE: make sure \s is converted to _; also ' should not be encoded on trg; done
		fxt	.ini_page_create_commons			("File:A'b c.png");
		fxt	.Lnki_orig_("A'b c.png")
			.Src(	fxt.img_("mem/src/commons.wikimedia.org/9/9c/A%27b_c.png", 90, 80))
			.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/9/c/A'b_c.png", 90, 80)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/9/9c.csv", "A'b_c.png|0||1?90,80|")
				);
		fxt.tst();
	}
	@Test  public void Ogg_vid_thumb() {
		fxt	.ini_page_create_commons			("File:A.ogg");
		fxt	.Lnki_orig_("A.ogg")
			.Src(	fxt.img_("mem/src/commons.wikimedia.org/thumb/4/42/A.ogg/-1px--A.ogg.jpg", 300, 40))
			.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/4/2/A.ogg/300px.jpg", 300, 40)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/4/42.csv", "A.ogg|0||0?0,0|1?300,40")
				);
		fxt.tst();
	}
	@Test  public void Ogg_vid_missing_thumb() {
		fxt	.Lnki_orig_("A.ogg")
			.Src(	)
			.Trg(	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/4/42.csv", "A.ogg|z||0?0,0|0?0,0")	// NOTE: mark thumb not found (since xfer_mgr checked all repos)
				);
		fxt.tst();
	}
	@Test  public void Aud_do_not_download() {
		fxt.save_(fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/4/42.csv", "A.ogg|z||2?0,0|0?0,0"));		// save reg file and mark file as aud
		fxt	.Lnki_orig_("A.ogg")
			.Src(	fxt.img_("mem/src/commons.wikimedia.org/thumb/4/42/A.ogg/mid-A.ogg.jpg", 300, 40)		// simulate thumb (make sure test does not download)
				)
			.Trg(	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/4/42.csv", "A.ogg|z||2?0,0|0?0,0")		// 300,40 should not show up
				);
		fxt.tst();
	}
	@Test  public void Img_thumb_djvu() {// PURPOSE: exact djvu thumbs are not on server; always seems to retrieve 1 off;
		fxt	.ini_page_create_commons			("File:A.djvu");
		fxt	.App().File_mgr().Img_mgr().Wkr_convert_djvu_to_tiff_(Xof_img_wkr_convert_djvu_to_tiff_.new_mok(199, 299));
		fxt	.Lnki_thumb_("A.djvu", 200)
			.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/76/A.djvu", 1990, 2990)
				)
			.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/6/A.djvu/199px.jpg", 199, 299)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/76.csv", "A.djvu|0||1?0,0|1?199,299")
				);
		fxt.tst();
		fxt	.Lnki_thumb_("A.djvu", 200)
			.Html_src_("file:///mem/trg/commons.wikimedia.org/fit/7/6/A.djvu/199px.jpg")
			.Html_size_(200, 301)
			.tst();
	}
	@Test  public void Img_thumb_pdf() {// PURPOSE: download pdf thumb only; [[File:Physical world.pdf|thumb]]
		fxt	.ini_page_create_commons			("File:A.pdf");
		fxt	.Lnki_thumb_("A.pdf", 220)
			.Src(	fxt.img_("mem/src/commons.wikimedia.org/e/ef/A.pdf", 2200, 1700)
				,	fxt.img_("mem/src/commons.wikimedia.org/thumb/e/ef/A.pdf/page1-220px-A.pdf.jpg", 220, 170)
				)
			.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/e/f/A.pdf/220px.jpg", 220, 170)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/e/ef.csv", "A.pdf|0||2?0,0|1?220,170")
				);
		fxt.tst();
		fxt	.Lnki_thumb_("A.pdf", 220)
			.Html_src_("file:///mem/trg/commons.wikimedia.org/fit/e/f/A.pdf/220px.jpg")
			.Html_size_(220, 170)
			.tst();
	}
	@Test  public void Img_missing_wiki_0() {	// PURPOSE.outlier: page is in wiki_1 but file is actually in wiki_0; download from wiki_0; occurs when working with old commons/en.wikipedia.org against current wmf servers; EX: Mars Science Laboratory and File:Curiosity wheel pattern morse code.png; Curiosity rover
		fxt	.ini_page_create_en_wiki			("File:A.png");							// page is in en_wiki
		fxt	.Lnki_orig_("A.png")
			.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 900, 800))		// file is in commons
			.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 900, 800)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"		, "A.png|y||1?900,800|")
				);
		fxt.tst();
	}
//		@Test  public void Img_missing_wiki_1() {	// PURPOSE.outlier: page is in wiki_0 and in wiki_1; file is in wiki_1; EX:[[Image:Alcott-L.jpg|thumb|right|Louisa May Alcott]]
//			fxt	.ini_page_create_commons			("File:A.png");														// page is in commons also
//			fxt	.ini_page_create_en_wiki			("File:A.png");														// page is in en_wiki
//			fxt	.Lnki_thumb_("A.png", 220)
//				.Src(	fxt.img_("mem/src/en.wikipedia.org/thumb/7/70/A.png/220px-A.png", 220, 110))					// file is in en_wiki
//				.Trg(	fxt.img_("mem/trg/en.wikipedia.org/fit/7/0/A.png/220px.png", 220, 110)							// download en_wiki
//					,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"		, "2|A.png|y||2?0,0|1?220,110")
//					);
//			fxt.tst();
//		}
	@Test  public void Do_not_download_orig_0() {// PURPOSE: do not download orig if size can be inferred from thumb; EX: [[File:Vanadium etched.jpg|350x250px|Vanadium]]
		fxt	.ini_page_create_en_wiki			("File:A.png");
		fxt	.Lnki_thumb_("A.png", 350, 250)																			// requesting w=350 and h=250; note that h trumps w
			.Src(	fxt.img_("mem/src/en.wikipedia.org/thumb/7/70/A.png/350px-A.png", 350, 309)						// w=350 exists, but should not be used
				,	fxt.img_("mem/src/en.wikipedia.org/thumb/7/70/A.png/283px-A.png", 283, 250)						// h=250 exists, and should be used
				,	fxt.img_("mem/src/en.wikipedia.org/7/70/A.png", 3808, 3364)										// orig image exists, and should not be downloaded
				)
			.Trg(	fxt.img_("mem/trg/en.wikipedia.org/fit/7/0/A.png/283px.png"		, 283, 250)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"		, "A.png|y||2?0,0|1?283,250")	// note that orig does not exist
				);
		fxt.tst();
		fxt.fil_absent(		 "mem/trg/en.wikipedia.org/raw/7/0/A.png");
		fxt	.Lnki_thumb_("A.png", 350, 250)
			.Html_src_("file:///mem/trg/en.wikipedia.org/fit/7/0/A.png/283px.png")
			.tst();
	}
	@Test  public void Do_not_download_orig_1() {// PURPOSE: allow variance of 1 for thumbs (rounding errors); EX:<gallery>Image:President Woodrow Wilson portrait December 2 1912.jpg|US President Woodrow Wilson</gallery>
		fxt	.ini_page_create_en_wiki			("File:A.png");
		fxt	.Lnki_thumb_("A.png", 120, 120)																			// requesting 120,120 (gallery default); note that h trumps w
			.Src(	fxt.img_("mem/src/en.wikipedia.org/thumb/7/70/A.png/120px-A.png", 120, 146)						// w=120 exists, but should not be used
				,	fxt.img_("mem/src/en.wikipedia.org/thumb/7/70/A.png/99px-A.png",  99, 121)						// true width should be w=98, but b/c of rounding off of large 2976, getting 99 instead
				,	fxt.img_("mem/src/en.wikipedia.org/7/70/A.png", 2976, 3623)										// orig image exists, and should not be downloaded
				)
			.Trg(	fxt.img_("mem/trg/en.wikipedia.org/fit/7/0/A.png/99px.png"		, 99, 121)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"		, "A.png|y||2?0,0|1?99,121")	// note that orig does not exist
				);
		fxt.tst();
		fxt.fil_absent(		 "mem/trg/en.wikipedia.org/raw/7/0/A.png");
		fxt	.Lnki_thumb_("A.png", 120, 120)																			// note that 120 is requested
			.Html_src_("file:///mem/trg/en.wikipedia.org/fit/7/0/A.png/99px.png")									// note that image used is 99
			.Html_size_(98, 120)																					// note that view width is 98
			.tst();
	}
	@Test  public void Do_not_download_orig_3() {// PURPOSE.defect: account for multiple thumbs; EX: <gallery heights="380" widths="454">File:Rembrandt van Rijn-De Nachtwacht-1642.jpg|1642.</gallery>
		fxt	.ini_page_create_en_wiki			("File:A.png");
		fxt	.Lnki_thumb_("A.png", 454, 380)																			// standard request
			.Src(	fxt.img_("mem/src/en.wikipedia.org/thumb/7/70/A.png/454px-A.png", 454, 380)
				,	fxt.img_("mem/src/en.wikipedia.org/7/70/A.png", 3344, 2796)
				)
			.Trg(	fxt.img_("mem/trg/en.wikipedia.org/fit/7/0/A.png/454px.png"		, 454, 380)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"		, "A.png|y||2?0,0|1?454,380")	// note that orig does not exist
				);
		fxt.tst();
		fxt.fil_absent(		 "mem/trg/en.wikipedia.org/raw/7/0/A.png");
		fxt	.Lnki_thumb_("A.png", 718, 600)																			// this is the defect; 718 was not being brought down; instead 454 was being reused
			.Src(	fxt.img_("mem/src/en.wikipedia.org/thumb/7/70/A.png/718px-A.png", 718, 600)
				,	fxt.img_("mem/src/en.wikipedia.org/7/70/A.png", 3344, 2796)
				)
			.Trg(	fxt.img_("mem/trg/en.wikipedia.org/fit/7/0/A.png/718px.png"		, 718, 600)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"		, "A.png|y||2?0,0|1?454,380;1?718,600")	// note that orig does not exist
				)
			.tst();
	}
	@Test  public void Do_not_download_orig_height() {// PURPOSE: handles links with only height specified; EX: [[File:Fresh_Pesto.jpeg|x210px|center]]
		fxt	.ini_page_create_en_wiki			("File:A.png");
		fxt	.Lnki_thumb_("A.png", -1, 210)																			// height-only request
			.Src(	fxt.img_("mem/src/en.wikipedia.org/thumb/7/70/A.png/291px-A.png", 291, 210)
				,	fxt.img_("mem/src/en.wikipedia.org/thumb/7/70/A.png/220px-A.png", 220, 159)	
				,	fxt.img_("mem/src/en.wikipedia.org/7/70/A.png", 2910, 2100)
				)
			.Trg(	fxt.img_("mem/trg/en.wikipedia.org/fit/7/0/A.png/291px.png"		, 291, 210)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"		, "A.png|y||2?0,0|1?220,159;1?291,210")	// note that orig does not exist
				);
		fxt.tst();
		fxt	.Lnki_thumb_("A.png", -1, 210)																	
		.Html_src_("file:///mem/trg/en.wikipedia.org/fit/7/0/A.png/291px.png")									
		.Html_size_(291, 210)																					
		.tst();
	}
	@Test  public void Upright_size_incorrect() {// PURPOSE.fix: incorrect image size being brought down; EX: w:ASCII; [[Image:ASCII Code Chart.svg|thumb|right|upright=1.6]]; 264, but should be 350 
		fxt	.ini_page_create_en_wiki			("File:A.png");
		fxt	.Lnki_("A.png", true, -1, -1, 1.6, Xof_lnki_time.Null_as_int)											// upright
			.Src(	fxt.img_("mem/src/en.wikipedia.org/7/70/A.png", 830, 328)
				,	fxt.img_("mem/src/en.wikipedia.org/thumb/7/70/A.png/350px-A.png", 350, 138)	
				)
			.Trg(	fxt.img_("mem/trg/en.wikipedia.org/fit/7/0/A.png/350px.png"		, 350, 138)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"		, "A.png|y||2?0,0|1?350,138")
				);
		fxt.tst();
	}
	@Test  public void Width_height_retrieves_wrong_size() {// PURPOSE.fix: EX: c:Yellowstone Park; [[Image:YellowstoneLake.jpg|Yellowstone Lake|120x120px|thumb]];
		fxt	.ini_page_create_en_wiki			("File:A.png");
		fxt	.Lnki_thumb_("A.png", 120, 120)
			.Src(	fxt.img_("mem/src/en.wikipedia.org/7/70/A.png", 1756, 1204)
				,	fxt.img_("mem/src/en.wikipedia.org/thumb/7/70/A.png/120px-A.png", 119, 82)						// NOTE: wmf has 119px width, even though 120px file_name
				)
			.Trg(	fxt.img_("mem/trg/en.wikipedia.org/fit/7/0/A.png/119px.png"		, 119, 82)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"		, "A.png|y||2?0,0|1?119,82")
				);
		fxt.tst();
		fxt	.Lnki_thumb_("A.png", 120, 120)																	
		.Html_src_("file:///mem/trg/en.wikipedia.org/fit/7/0/A.png/119px.png")
		.Html_size_(120, 83)
		.tst();
	}
	@Test  public void Svg_thumb_can_be_bigger_than_orig__convert() {// PURPOSE: svg thumbs allowed to exceed orig in size; EX: w:Portal:Music; [[File:Treble a.svg|left|160px]]
		fxt	.ini_page_create_en_wiki			("File:A.svg");
		fxt	.Lnki_thumb_("A.svg", 220)																					// thumb = 220
			.Src(	fxt.svg_("mem/src/en.wikipedia.org/7/75/A.svg", 110, 100)											// orig = 110
				)
			.Trg(	fxt.img_("mem/trg/en.wikipedia.org/fit/7/5/A.svg/220px.png"		, 220, 200)							// thumb = 220; not limited to 110
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/75.csv"		, "A.svg|y||1?110,100|1?220,200")
				)
			.tst(
			);
		fxt	.Lnki_thumb_("A.svg", 220)																	
		.Html_src_("file:///mem/trg/en.wikipedia.org/fit/7/5/A.svg/220px.png")
		.Html_size_(220, 200)
		.tst();
	}
	@Test  public void Ogv_width_seek_again_should_dirty() { // PURPOSE: outlier case wherein (a) downloading thumb then (b) downloading thumb seek; (b) does not dirty file since (a) exists; PAGE:en.w:Wikipedia
		fxt.Src_en_wiki_repo().Ext_rules().Get_or_new(Xof_ext_.Bry_ogv).View_max_(0);
		Io_mgr.Instance.SaveFilStr("mem/xowa/file/#meta/en.wikipedia.org/d/d0.csv", "A.ogv|0||2?0,0|1?300,40\n");	// simulate (a)
		fxt	.Lnki_("A.ogv", true, -1, -1, -1, 5)															
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/thumb/d/d0/A.ogv/-1px-seek%3D5-A.ogv.jpg", 300, 40)	
				)
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/d/0/A.ogv/300px@5.jpg", 300, 40)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/d/d0.csv", "A.ogv|0||2?0,0|1?300,40@5")
			)
		.tst();
		fxt.Src_en_wiki_repo().Ext_rules().Get_or_new(Xof_ext_.Bry_ogv).View_max_(-1);
	}
	@Test   public void Webm() {
		fxt	.ini_page_create_commons			("File:A.webm");
		fxt	.Lnki_thumb_("A.webm", 220)
		.Src(	fxt.ogg_("mem/src/commons.wikimedia.org/3/34/A.webm")
			,	fxt.img_("mem/src/commons.wikimedia.org/thumb/3/34/A.webm/220px--A.webm.jpg", 300, 40)	
				)
		.Trg(	fxt.ogg_("mem/trg/commons.wikimedia.org/raw/3/4/A.webm")
			,	fxt.img_("mem/trg/commons.wikimedia.org/fit/3/4/A.webm/300px.jpg", 300, 40)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/3/34.csv", "A.webm|0||1?0,0|1?300,40")
			)
		.tst();
	}
	@Test  public void Ogv_thumb() {// d00d1d5019e37cc219a91a2f8ad47bfe
		fxt	.ini_page_create_commons			("File:A.ogv");
		fxt	.Lnki_orig_("A.ogv")
			.Src(	fxt.img_("mem/src/commons.wikimedia.org/thumb/d/d0/A.ogv/-1px--A.ogv.jpg", 300, 40))
			.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/d/0/A.ogv/300px.jpg", 300, 40)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/d/d0.csv", "A.ogv|0||0?0,0|1?300,40")
				)
			.tst();
		fxt	.Lnki_orig_("A.ogv")																	
		.Html_src_("file:///mem/trg/commons.wikimedia.org/fit/d/0/A.ogv/300px.jpg")
		.Html_size_(300, 40)
		.tst();
	}
	@Test  public void Thumbtime_ignored_if_non_media() { // PURPOSE: ignore thumbtime if not media; PAGE:en.w:Moon; EX:[[File:A.png|thumbtime=0.02]] DATE:2014-07-22
		fxt	.ini_page_create_en_wiki("File:A.png");
		fxt	.Lnki_("A.png", true, 90, Xof_img_size.Size__neg1, Xof_img_size.Size__neg1, 2)	// thumbtime of 2 specified; will be ignored below
			.Src(	fxt.img_("mem/src/en.wikipedia.org/thumb/7/70/A.png/90px-A.png", 90, 80))
			.Trg(	fxt.img_("mem/trg/en.wikipedia.org/fit/7/0/A.png/90px.png", 90, 80)
				,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|y||2?0,0|1?90,80")
				);
		fxt.tst();
	}

//		@Test  public void Ogg_full_skip() {	// DISABLED: 2012-12-03; not sure about logic
//			fxt	.ini_page_create_commons			("File:A.ogg");
//			fxt .En_wiki().File_mgr().Repo_mgr().Repos_get_at(0).Src().Ext_rules().Get_or_new(Xof_ext_.Bry_ogg).View_max_(0);		// set ogg to do not download
//			fxt	.Lnki_orig_("A.ogg")
//				.Src(	fxt.ogg_("mem/src/commons.wikimedia.org/4/2/A.ogg"))
//				.Trg(	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/4/42.csv", "2|A.ogg|0|2?0,0|")
//					);
//			fxt.tst();
//			fxt .En_wiki().File_mgr().Repo_mgr().Repos_get_at(0).Src().Ext_rules().Get_or_new(Xof_ext_.Bry_ogg).View_max_(1000);	// undo above
//		}
}
