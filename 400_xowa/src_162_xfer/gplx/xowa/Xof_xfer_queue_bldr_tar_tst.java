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
import gplx.ios.*; import gplx.gfui.*;
public class Xof_xfer_queue_bldr_tar_tst {
	Xof_xfer_queue_bldr_fxt fxt = new Xof_xfer_queue_bldr_fxt();
	@Before public void init() {fxt.Clear(false);}
	@Test  public void Png_orig() {
		fxt.Rdr("A.png|||10000|90|80|8|0||0,-1,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 90, 80))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 90, 80)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|0||1?90,80|")
			)
		.tst();
	}
	@Test  public void Png_thumb() {
		fxt.Rdr("A.png|||10000|90|80|8|0||8,45,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 90, 80))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/0/A.png/45px.png", 45, 40)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|0||1?90,80|1?45,40")
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
	@Test  public void Redirect() {
		fxt.Rdr("B.png|A.png||10000|90|80|8|0||0,-1,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 90, 80))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 90, 80)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|0||1?90,80|")
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/5/57.csv", "B.png|0|A.png|2?0,0|")
			)
		.tst();
	}
	@Test  public void Long_file_name() {
		String name = String_.Repeat("A", 200);
		fxt.Rdr(name + ".png|||10000|90|80|8|0||0,-1,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/1/14/" + name + ".png", 90, 80))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/1/4/" + String_.Repeat("A", (200 + 4) - (20 + 32 + 1 + 4)) + "_141b573216f8e4338f4f53409bd9d209.png", 90, 80) // SEE:NOTE_1
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/1/14.csv", name + ".png|0||1?90,80|")
			)
		.tst();
		fxt	.Lnki_thumb_(name + ".png", 200)
			.Html_src_("file:///mem/trg/commons.wikimedia.org/raw/1/4/" + String_.Repeat("A", (200 + 4) - (20 + 32 + 1 + 4)) + "_141b573216f8e4338f4f53409bd9d209.png")
			.tst();
	}
	@Test  public void Svg_orig() {
		fxt.Rdr("A.svg|||10000|90|80|8|0||0,-1,-1")
		.Src(	fxt.svg_("mem/src/commons.wikimedia.org/7/75/A.svg", 90, 80))
		.Trg(	fxt.svg_("mem/trg/commons.wikimedia.org/raw/7/5/A.svg", 90, 80)
			,	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/5/A.svg/90px.png", 90, 80)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/75.csv", "A.svg|0||1?90,80|1?90,80")
			)
		.tst();
	}
	@Test  public void Svg_thumb() {
		fxt.Rdr("A.svg|||10000|90|80|8|0||8,45,-1")
		.Src(	fxt.svg_("mem/src/commons.wikimedia.org/7/75/A.svg", 90, 80))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/5/A.svg/45px.png", 45, 40)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/75.csv", "A.svg|0||1?90,80|1?45,40")
			)
		.tst();
	}
	@Test  public void Invalid() {
		fxt.Rdr("A\"*b.jpg|||10000|90|80|8|0||8,45,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/0/0f/A__b.jpg", 90, 80))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/0/f/A__b.jpg/45px.jpg", 45, 40)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/0/0f.csv", "A\"*b.jpg|0||1?90,80|1?45,40")
			)
		.tst();
	}
	@Test  public void LowerCase() {
		fxt.Rdr("a.png|||10000|90|80|8|0||0,-1,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 90, 80))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 90, 80)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|0||1?90,80|")
			)
		.tst();
	}
	@Test  public void Nil_size_thumb() {
		fxt.Rdr("A.png|||0|0|0|0|0||8,45,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 90, 80))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/fit/7/0/A.png/45px.png", 45, 40)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|0||1?90,80|1?45,40"
				)
			)
		.tst();
	}
	@Test  public void Redirect_orig() {
		fxt.Rdr("B.png|A.png||0|0|0|0|0||0,-1,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/7/70/A.png", 90, 80))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/7/0/A.png", 90, 80)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv", "A.png|0||1?90,80|")
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/5/57.csv", "B.png|0|A.png|2?0,0|")
			)
		.tst();
	}
	@Test  public void Ogg() {
		fxt.Rdr("A.ogg|||0|0|0|0|0||0,50,100")	// NOTE: ignore size even if set in lnk  
		.Src(	fxt.ogg_("mem/src/commons.wikimedia.org/4/42/A.ogg"))
		.Trg(	fxt.ogg_("mem/trg/commons.wikimedia.org/raw/4/2/A.ogg")
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/4/42.csv", "A.ogg|0||1?0,0|")
			)
		.tst();
	}
	@Test  public void Char_apos() {	// PURPOSE: ' should not be url-encoded
		fxt.Rdr("A'b.png|||10000|90|80|8|0||0,-1,-1")
		.Src(	fxt.img_("mem/src/commons.wikimedia.org/6/66/A'b.png", 90, 80))
		.Trg(	fxt.img_("mem/trg/commons.wikimedia.org/raw/6/6/A'b.png", 90, 80)
			,	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/6/66.csv", "A'b.png|0||1?90,80|")
			)
		.tst();
	}
}
// Tfds.Write(gplx.xowa.files.Xof_xfer_itm_.Md5_(Bry_.new_utf8_("A,b.png")));

/*
NOTE_1:String_.Repeat("A", (200 + 4) - (24 + 20 + 32 + 1 + 4)) 

+
200:	"A" repeated 200 times
  4:	".png"
NOTE: 200 is 20 over max of 180

-
 20:	amount over 180
 32:	length of md5 hash; "141b573216f8e4338f4f53409bd9d209"
  1:	length of _
  4:	".png"
*/
