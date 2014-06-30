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
package gplx.xowa; import gplx.*;
import org.junit.*;
import gplx.ios.*; import gplx.xowa.files.*;
public class Xof_xfer_queue_bldr_wmf_tst {
	Xof_xfer_queue_bldr_fxt fxt = new Xof_xfer_queue_bldr_fxt();
	@Before public void init() {fxt.Clear(true);}
	@Test  public void Png_orig() {
		fxt.Rdr("A.png|||10000|90|80|8|0||0,-1,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 900, 800))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 900, 800)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"	, "A.png|0||1?900,800|")
			)
		.tst();
	}
	@Test  public void Png_thumb() {
		fxt.Rdr("A.png|||10000|90|80|8|0||8,45,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/thumb/7/70/A.png/45px-A.png", 45, 40))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/0/A.png/45px.png", 45, 40)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|0||2?90,80|1?45,40")
			)
		.tst();
	}
	@Test  public void Png_many() {
		fxt.Rdr("A.png|||10000|90|80|8|0||0,-1,-1;8,45,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 90, 80))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 90, 80)
			,	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/0/A.png/45px.png", 45, 40)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|0||1?90,80|1?45,40")
			)
		.tst();
	}
//		// Test: Thumb_resizeRaw; thumb does not exist; download orig and resize
//		// Test: Thumb_upright specified	tstr.Lnki_("[[File:A.png|thumb|upright]]").Src_xfer_("170,160", "mem/wiki/c0/thumb/7/70/A.png/170px-A.png")
//		// Test: Thumb_height only specified	"[[File:A.png|thumb|x50px]]").Src_xfer_("200,100", "mem/wiki/c0/7/70/A.png" -> 100,50
	@Test  public void Svg_orig() {
		fxt.Rdr("A.svg|||10000|90|80|8|0||0,-1,-1")
		.Src(	fxt.svg_("mem/src/commons.wikimedia.org/7/75/A.svg", 90, 80))
		.Trg(	fxt.svg_("mem/trg/commons.wikimedia.org/raw/7/5/A.svg", 90, 80)					// NOTE: needs to download orig before converting
			,	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/5/A.svg/90px.png", 90, 80)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/75.csv", "A.svg|0||1?90,80|1?90,80")
			)
		.tst();
	}
	@Test  public void Svg_thumb() {
		fxt.Rdr("A.svg|||10000|90|80|8|0||8,45,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/thumb/7/75/A.svg/45px-A.svg.png", 45, 40))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/5/A.svg/45px.png", 45, 40)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/75.csv", "A.svg|0||2?90,80|1?45,40")
			)
		.tst();
	}
	@Test  public void Nil_size_thumb() {
		fxt.Rdr("A.png|||0|0|0|0|0||8,45,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/thumb/7/70/A.png/45px-A.png", 45, 40))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/0/A.png/45px.png", 45, 40)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|0||2?0,0|1?45,40")
			)
		.tst();
	}
	@Test  public void Nil_size_orig() {
		fxt.Rdr("A.png|||0|0|0|0|0||0,-1,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 90, 80))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 90, 80)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|0||1?90,80|")
			)
		.tst();
	}
	@Test  public void Nil_height() {
		fxt.Rdr("A.png|||0|0|0|0|0||8,-1,80")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 220, 160)
			,	fxt.img_("mem/src/commons.wikimedia.org/thumb/7/70/A.png/220px-A.png", 220, 160)	// NOTE: make sure default thumb is ignored; i.e.: 220 should not be substituted for -1; EX.WP:Paris;[[File:IMA-Ile-St-Louis.jpg|thumb|x220]] 
			)
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 220, 160)
			,	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/0/A.png/110px.png", 110, 80)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|0||1?220,160|1?220,160;1?110,80")
			)
		.tst();
	}
	@Test  public void Nil_width_thumb() {
		fxt.Rdr("A.png|||0|0|0|0|0||8,-1,80")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 180, 160)
			,	fxt.img_("mem/src/commons.wikimedia.org/thumb/7/70/A.png/220px-A.png", 220, 196)	// NOTE: make sure default thumb is ignored
			,	fxt.img_("mem/src/commons.wikimedia.org/thumb/7/70/A.png/90px-A.png", 90, 80)	// NOTE: make sure default thumb is ignored
			)
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/0/A.png/90px.png", 90, 80)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|0||2?0,0|1?220,196;1?90,80")
			)
		.tst();
	}
	@Test  public void Nil_width_wrong_width() {
		fxt.Rdr("A.svg|||0|0|0|0|0||8,65,50")
		.Src(	fxt.svg_("mem/src/commons.wikimedia.org/7/75/A.svg", 160, 160))
		.Trg(	fxt.svg_("mem/trg/commons.wikimedia.org/raw/7/5/A.svg", 160, 160)
			,	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/5/A.svg/50px.png", 50, 50)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/75.csv", "A.svg|0||1?160,160|1?50,50")
			)
		.tst();
	}
	@Test  public void Nil_width_wrong_width_2() {		// EX.WP: [[Image:Gnome-mime-audio-openclipart.svg|65x50px|center|link=|alt=]]
		fxt.Rdr("A.svg|||0|0|0|0|0||8,65,50")
		.Src(	fxt.svg_("mem/src/commons.wikimedia.org/7/75/A.svg", 160, 160)
			,	fxt.img_("mem/src/commons.wikimedia.org/thumb/7/75/A.svg/65px-A.svg.png", 65, 60))
		.Trg(	fxt.svg_("mem/trg/commons.wikimedia.org/raw/7/5/A.svg", 160, 160)
			,	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/5/A.svg/50px.png", 50, 50)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/75.csv", "A.svg|0||1?160,160|1?50,50")
			)
		.tst();
	}
	@Test  public void Svg_thumb_irregular() {// EX.WP:{{Olympic Summer Games Host Cities}};[[File:Flag of the United States.svg|22x20px]]
		fxt.Rdr("A.svg|||0|0|0|8|0||8,22,20")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/thumb/7/75/A.svg/22px-A.svg.png", 22, 12)
			)
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/5/A.svg/22px.png", 22, 12)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/75.csv", "A.svg|0||2?0,0|1?22,12")
			)
		.tst();
	}
	@Test  public void Rounding_converts_to_double() {// bugfix wherein (double) is needed else integer division loses precision; EX.WP:{{Olympic Summer Games Host Cities}};[[File:Flag of the United States.svg|22x20px]]
		fxt.Rdr("A.svg|||0|0|0|8|0||8,22,12")
		.Src(	fxt.svg_("mem/src/commons.wikimedia.org/7/75/A.svg", 1235, 650))
		.Trg(	fxt.svg_("mem/trg/commons.wikimedia.org/raw/7/5/A.svg", 1235, 650)
			,	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/5/A.svg/22px.png", 22, 12)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/75.csv", "A.svg|0||1?1235,650|1?22,12")
			)
		.tst();
	}
	@Test  public void Nil_width_thumb_missing() {
		fxt.Rdr("A.png|||0|0|0|0|0||8,-1,80")
		.Src()
		.Trg(fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|x||2?0,0|"))
		.tst();
	}
	@Test  public void Missing_do_not_redownload() {
		fxt.Rdr("A.png|||0|0|0|0|0||8,45,40")
		.Src(	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|x||0?0,0|")			// create reg and mark file missing
			,	fxt.img_("mem/src/commons.wikimedia.org/thumb/7/70/A.png/45px-A.png", 45, 40))			// create thumb (but should be ignored)
		.Trg(	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|x||0?0,0|"))			// note that 45,40 thumb does not show up
		.tst();
	}
	@Test  public void Ogg() {
		fxt.Rdr("A.ogg|||0|0|0|0|0||0,-1,-1")	// NOTE: ignore size even if set in lnk  
		.Src(	fxt.ogg_("mem/src/commons.wikimedia.org/4/42/A.ogg")
			,	fxt.img_("mem/src/commons.wikimedia.org/thumb/4/42/A.ogg/mid-A.ogg.jpg", 300, 40)
			)
		.Trg(	fxt.ogg_("mem/trg/commons.wikimedia.org/raw/4/2/A.ogg")
			,	fxt.img_("mem/trg/commons.wikimedia.org/fit/4/2/A.ogg/300px.jpg", 300, 40)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/4/42.csv", "A.ogg|0||1?0,0|1?300,40"
			)
			)
		.tst();
	}
	@Test  public void Ogv_width_standard() {
		fxt.Rdr("A.ogv|||0|0|0|0|0||8,-1,-1")	// NOTE: size matters for retrieval of thumb
		.Src(	fxt.ogg_("mem/src/commons.wikimedia.org/d/d0/A.ogv")
			,	fxt.img_("mem/src/commons.wikimedia.org/thumb/d/d0/A.ogv/mid-A.ogv.jpg", 300, 40)	
				)
		.Trg(	fxt.ogg_("mem/trg/commons.wikimedia.org/raw/d/0/A.ogv")
			,	fxt.img_("mem/trg/commons.wikimedia.org/fit/d/0/A.ogv/300px.jpg", 300, 40)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/d/d0.csv", "A.ogv|0||1?0,0|1?300,40")
			)
		.tst();
	}
	@Test  public void Ogv_width_custom() {	// EX.WP:Earth;Northwest coast of United States to Central South America at Night.ogv|250px
		fxt.Rdr("A.ogv|||0|0|0|0|0||8,250,-1")
		.Src(	fxt.ogg_("mem/src/commons.wikimedia.org/d/d0/A.ogv")
			,	fxt.img_("mem/src/commons.wikimedia.org/thumb/d/d0/A.ogv/mid-A.ogv.jpg", 640, 425)	
				)
		.Trg(	fxt.ogg_("mem/trg/commons.wikimedia.org/raw/d/0/A.ogv")
			,	fxt.img_("mem/trg/commons.wikimedia.org/fit/d/0/A.ogv/640px.jpg", 640, 425)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/d/d0.csv", "A.ogv|0||1?0,0|1?640,425")
			)
		.tst();
	}
	@Test  public void Ogv_width_seek() {
		fxt.Rdr("A.ogv|||0|0|0|0|0||8,-1,-1,thumbtime=5")	// NOTE: size matters for retrieval of thumb
		.Src(	fxt.ogg_("mem/src/commons.wikimedia.org/d/d0/A.ogv")
			,	fxt.img_("mem/src/commons.wikimedia.org/thumb/d/d0/A.ogv/seek%3D5-A.ogv.jpg", 300, 40)	
				)
		.Trg(	fxt.ogg_("mem/trg/commons.wikimedia.org/raw/d/0/A.ogv")
			,	fxt.img_("mem/trg/commons.wikimedia.org/fit/d/0/A.ogv/300px@5.jpg", 300, 40)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/d/d0.csv", "A.ogv|0||1?0,0|1?300,40@5")
			)
		.tst();
	}
	@Test  public void Djvu() {
		fxt.Rdr("A.djvu|||10000|0|0|8|0||8,45,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/thumb/7/76/A.djvu/page1-45px-A.djvu.jpg", 45, 40))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/6/A.djvu/45px.jpg", 45, 40)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/76.csv", "A.djvu|0||2?0,0|1?45,40")
			)
		.tst();
	}
	@Test  public void Upright() {	// PURPOSE: upright can generate thumb that is larger than orig; thumb is not on wmf, so orig must be used EX.WP:Saint Petersburg [[File:Georgyj Poltavchenko.jpeg|thumb|left|upright]]
		fxt.Rdr("A.png|||0|0|0|0|0||8,-1,-1,upright=1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 148, 186))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 148, 186)
//				,	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/70/A.png/130px.png", 130, 163)	// FUTURE: should delete these since they are not used
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|0||1?148,186|1?148,186"	// NOTE: upright=1 calculates width of 220; but since orig < 220, use orig
				)
			)
		.tst();
		fxt.tst_html_src("file:///mem/trg/commons.wikimedia.org/fit/7/0/A.png/148px.png");	// NOTE: changed from raw/A.png to fit/148px.png; DATE:2014-05-23
	}
	@Test  public void Url_encoding_valid() {	// PURPOSE: A%2Cb should (a) not become A%252Cb on file_system but (b) should have md5 calculated on A,b and (c) should be copied down as "A,b"
		fxt.Rdr("A%2Cb.png|||10000|90|80|8|0||0,-1,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/d/d6/A%2Cb.png", 90, 80))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/d/6/A,b.png", 90, 80)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/d/d6.csv", "A,b.png|0||1?90,80|")
			)
		.tst();
	}
	@Test  public void Url_encoding_invalid() {	// PURPOSE: for invalid url encoding (%); note that A%b should not become A%25b on file_system
//			Tfds.Write_bry(Xof_xfer_itm_.Md5_calc(Bry_.new_ascii_("A%b.png")));
		fxt.Rdr("A%b.png|||10000|90|80|8|0||0,-1,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/9/98/A%25b.png", 90, 80))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/9/8/A%b.png", 90, 80)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/9/98.csv", "A%b.png|0||1?90,80|")
			)
		.tst();
	}
	@Test  public void Rounding() {	// PURPOSE: bug; 3000,3002 image gets scaled to 99,99 instead of 100,100; EX.WP: Solar System#Visual summary
		fxt.Rdr("A.png|||10000|3000|3002|8|0||0,100,100")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 3000, 3002))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 3000, 3002)
			,	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/0/A.png/100px.png", 100, 100)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|0||1?3000,3002|1?100,100"
				)
			)
		.tst();
	}
	@Test  public void Char_plus() {
		fxt.Rdr("A+b.png|||10000|90|80|8|0||0,-1,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/d/d3/A%2Bb.png", 90, 80))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/d/3/A+b.png", 90, 80)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/d/d3.csv", "A+b.png|0||1?90,80|"
				)
			)
		.tst();
	}
	@Test  public void Pdf_thumb() {
		fxt.Rdr("A.pdf|||10000|0|0|8|0||0,45,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/thumb/e/ef/A.pdf/page1-45px-A.pdf.jpg", 45, 40))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/e/f/A.pdf/45px.jpg", 45, 40)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/e/ef.csv", "A.pdf|0||2?0,0|1?45,40"
				)
			)
		.tst();
	}
}
class Xof_xfer_queue_bldr_fxt extends Xof_xfer_queue_html_fxt {	Xof_xfer_itm xfer_itm = new Xof_xfer_itm();
	Xof_xfer_queue queue = new Xof_xfer_queue();
	@gplx.New public Xof_xfer_queue_bldr_fxt Src(Io_fil... v) {return (Xof_xfer_queue_bldr_fxt)Src_base(v);}
	@gplx.New public Xof_xfer_queue_bldr_fxt Trg(Io_fil... v) {return (Xof_xfer_queue_bldr_fxt)Trg_base(v);}
	public Xof_xfer_queue_bldr_fxt Rdr(String... v) {rdr = String_.Concat_lines_nl(v); return this;} private String rdr;
	@Override public void Clear(boolean src_repo_is_wmf) {
		super.Clear(src_repo_is_wmf);
		bldr = Xoa_app_fxt.bldr_(this.App());
		wkr = new Xobc_img_run_xfer(bldr, this.En_wiki());
		GfoInvkAble_.InvkCmd_val(wkr, Xobc_img_run_xfer.Invk_rdr_dir_, "mem/rdr/");
	}	private Xob_bldr bldr; Xobc_img_run_xfer wkr;
	@gplx.New public void tst() {
		ini_src_fils();
		Io_mgr._.SaveFilStr(wkr.Rdr_dir().GenSubFil("0000.csv"), rdr);
		wkr.Cmd_bgn(bldr);
		wkr.Cmd_run();
		wkr.Cmd_end();
		tst_trg_fils();
	}
	public void tst_html_src(String expd) {
		boolean found = wkr.Xfer_itm().Atrs_calc_for_html();
		Tfds.Eq(expd, String_.new_utf8_(wkr.Xfer_itm().Html_view_src()));
		Tfds.Eq(true, found, "img not found");
	}
}
///*
//		@Test  public void Thumb_resizeRaw() {	// thumb does not exist; download raw and resize
//			tstr.Lnki_("[[File:A.png|thumb]]").Src_xfer_("440,400", "mem/wiki/c0/7/70/A.png")
//				.Expd_xfer_fail_
//				(	"mem/wiki/c0/thumb/7/70/A.png/220px-A.png"
//				,	"mem/wiki/en/thumb/7/70/A.png/220px-A.png"
//				)
//				.Expd_xfer_pass_
//				(	"mem/xowa/raw/7/0/A.png/440_400.png"
//				,	"mem/xowa/fit/7/0/A.png/220_200.png")
//				.Expd_html_src_("file:///mem/xowa/fit/7/0/A.png/220_200.png")
//				.Expd_fit_reg_
//				(	"-1,-1|0|440,400"
//				,	"220,-1|1|220,200"
//				)
//				.tst()
//				;
//		}
//		@Test  public void Thumb_upright() {
//			tstr.Lnki_("[[File:A.png|thumb|upright]]").Src_xfer_("170,160", "mem/wiki/c0/thumb/7/70/A.png/170px-A.png")
//				.Expd_xfer_fail_()
//				.Expd_xfer_pass_("mem/xowa/fit/7/0/A.png/170_160.png")
//				.Expd_html_src_("file:///mem/xowa/fit/7/0/A.png/170_160.png")
//				.Expd_fit_reg_
//				(	"170,-1|1|170,160"
//				)
//				.tst()
//				;
//		}
//		@Test  public void Thumb_height() {
//			tstr.Lnki_("[[File:A.png|thumb|x50px]]").Src_xfer_("200,100", "mem/wiki/c0/7/70/A.png")
//				.Expd_xfer_fail_()
//				.Expd_xfer_pass_
//				(	"mem/xowa/raw/7/0/A.png/200_100.png"
//				,	"mem/xowa/fit/7/0/A.png/100_50.png"
//				)
//				.Expd_html_src_("file:///mem/xowa/fit/7/0/A.png/100_50.png")
//				.Expd_fit_reg_
//				(	"-1,-1|0|200,100"
//				,	"-1,50|1|100,50"
//				)
//				.tst()
//				;
//		}
//		@Test  public void Thumb_height_no_resize() {
//			tstr.Lnki_("[[File:A.png|thumb|x100px]]").Src_xfer_("200,100", "mem/wiki/c0/7/70/A.png")
//				.Expd_xfer_fail_()
//				.Expd_xfer_pass_("mem/xowa/raw/7/0/A.png/200_100.png")
//				.Expd_html_src_("file:///mem/xowa/raw/7/0/A.png/200_100.png")
//				.Expd_fit_reg_
//				(	"-1,-1|0|200,100"
//				,	"-1,100|0|200,100"
//				)
//				.tst()
//				;
//		}
//		@Test  public void Thumb_width_basic() {
//			tstr.Lnki_("[[File:A.png|thumb|100px]]").Src_xfer_("100,50", "mem/wiki/c0/thumb/7/70/A.png/100px-A.png")
//				.Expd_xfer_fail_()
//				.Expd_xfer_pass_("mem/xowa/fit/7/0/A.png/100_50.png")
//				.Expd_html_src_("file:///mem/xowa/fit/7/0/A.png/100_50.png")
//				.Expd_fit_reg_
//				(	"100,-1|1|100,50"
//				)
//				.tst()
//				;
//		}
//		@Test  public void Thumb_resize_width_too_long() {	// lnki width > raw; just use raw	// WP.EX:[[File:Adhanema Lasva.jpg|thumb|300px|The Firman given to the Bosnian Franciscans]]; src=149,408
//			tstr.Lnki_("[[File:A.png|thumb|200px]]").Src_xfer_("100,50", "mem/wiki/c0/7/70/A.png")
//				.Expd_xfer_fail_
//				(	"mem/wiki/c0/thumb/7/70/A.png/200px-A.png"
//				,	"mem/wiki/en/thumb/7/70/A.png/200px-A.png")
//				.Expd_xfer_pass_
//				(	"mem/xowa/raw/7/0/A.png/100_50.png"
//				)
//				.Expd_html_src_("file:///mem/xowa/raw/7/0/A.png/100_50.png")
//				.Expd_fit_reg_
//				(	"-1,-1|0|100,50"
//				,	"200,-1|0|100,50"
//				)
//				.tst()
//				;
//		}
//		@Test  public void Thumb_resize_width_is_wrong() {	// size does not match scaling; recalc; // WP.EX:[[File:Firma B.Ohiggins.svg|128x80px|alt=|Bernardo O'Higgins's signature]]; src=720,196
//			tstr.Lnki_("[[File:A.png|thumb|128x80px]]").Src_xfer_("720,196", "mem/wiki/c0/7/70/A.png")
//				.Expd_xfer_fail_()
//				.Expd_xfer_pass_("mem/xowa/raw/7/0/A.png/720_196.png", "mem/xowa/fit/7/0/A.png/128_34.png")
//				.Expd_html_src_("file:///mem/xowa/fit/7/0/A.png/128_34.png")
//				.Expd_fit_reg_
//				(	"-1,-1|0|720,196"
//				,	"128,80|1|128,34"
//				)
//				.tst()
//				;
//		}
//		@Test  public void Svg_height() {
//			tstr.Lnki_("[[File:A.svg|x20px]]")
//				.Src_xfer_(tstr.file_svg(80, 100)	, "mem/wiki/c0/7/75/A.svg")
//				.Expd_xfer_fail_()
//				.Expd_xfer_pass_
//				(	"mem/xowa/raw/7/5/A.svg/80_100.svg"
//				,	"mem/xowa/fit/7/5/A.svg/16_20.png"
//				)
//				.Expd_html_src_("file:///mem/xowa/fit/7/5/A.svg/16_20.png")
//				.Expd_fit_reg_
//				(	"-1,20|1|16,20"
//				)
//				.tst()
//				;
//		}
//		@Test  public void Svg_height_overrides_width() {
//			tstr.Lnki_("[[File:A.svg|20x20px]]")
//				.Src_xfer_(tstr.file_svg(80, 100)	, "mem/wiki/c0/7/75/A.svg")
//				.Expd_xfer_fail_()
//				.Expd_xfer_pass_
//				(	"mem/xowa/raw/7/5/A.svg/80_100.svg"
//				,	"mem/xowa/fit/7/5/A.svg/16_20.png"
//				)
//				.Expd_html_src_("file:///mem/xowa/fit/7/5/A.svg/16_20.png")
//				.Expd_fit_reg_
//				(	"20,20|1|16,20"
//				)
//				.tst()
//				;
//		}
//		@Test  public void Svg_resize_width_too_long() {
//			tstr.Lnki_("[[File:A.svg|50x40px]]")
//				.Src_xfer_(tstr.file_svg(40, 40)	, "mem/wiki/c0/7/75/A.svg")
//				.Expd_xfer_pass_
//				(	"mem/xowa/raw/7/5/A.svg/40_40.svg"
//				,	"mem/xowa/fit/7/5/A.svg/40_40.png")
//				.Expd_html_src_("file:///mem/xowa/fit/7/5/A.svg/40_40.png")
//				.Expd_fit_reg_
//				(	"50,40|1|40,40"
//				)
//				.tst()
//				;
//		}
//*/
