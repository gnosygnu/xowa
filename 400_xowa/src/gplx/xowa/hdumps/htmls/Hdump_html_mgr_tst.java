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
package gplx.xowa.hdumps.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import org.junit.*; import gplx.xowa.hdumps.core.*; import gplx.xowa.hdumps.dbs.*;
public class Hdump_html_mgr_tst {
	@Before public void init() {
		fxt.Clear();
		fxt.Init_data_img_basic(0, 220, 110, "A.png", "commons.wikimedia.org/thumb/7/0/A.png/220.png");
	}	private Hdump_html_mgr_fxt fxt = new Hdump_html_mgr_fxt();
	@Test  public void Img() {
		fxt	.Init_body("<img xowa_img='0' />")
			.Test_html("<img src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/220.png' width='220' height='110' />");
	}
	@Test  public void Img_style() {
		fxt	.Init_body("<div xowa_img_style='0'>")
			.Test_html("<div style='width:220px;'>");
	}
	@Test  public void File_info() {
		fxt	.Init_body("<xowa_info id='0'/>")
			.Test_html(String_.Concat_lines_nl_skip_last
			( ""
			, "      <div>"
			, "        <a href=\"/wiki/File:A.png\" class=\"image\" title=\"About this file\">"
			, "          <img src=\"file:///mem/xowa/user/test_user/app/img/file/info.png\" width=\"22\" height=\"22\" />"
			, "        </a>"
			, "      </div>"
			));
	}
	@Test  public void File_mgnf() {
		fxt	.Init_body("<xowa_mgnf id='0'/>")
			.Test_html(String_.Concat_lines_nl_skip_last
			( ""
			, "      <div class=\"magnify\">"
			, "        <a href=\"/wiki/File:A.png\" class=\"internal\" title=\"A.png\">"
			, "          <img src=\"file:///mem/xowa/user/test_user/app/img/file/magnify-clip.png\" width=\"15\" height=\"11\" alt=\"\" />"
			, "        </a>"
			, "      </div>"
			));
	}
	@Test  public void File_play() {
		fxt	.Init_body("<xowa_play id='0'/>")
			.Test_html(String_.Concat_lines_nl_skip_last
			( ""
			, "      <div>"
			, "        <a id=\"xowa_file_play_0\" href=\"/wiki/File:A.png\" xowa_title=\"A.png\" class=\"xowa_anchor_button\" style=\"width:220px;max-width:1024px;\">"
			, "          <img src=\"file:///mem/xowa/user/test_user/app/img/file/play.png\" width=\"22\" height=\"22\" alt=\"Play sound\" />"
			, "        </a>"
			, "      </div>"
			));
	}
	@Test  public void Hiero_dir() {
		fxt	.Init_body("<img src='~{xowa_hiero_dir}hiero_a&A1.png' />")
			.Test_html("<img src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_a&A1.png' />");
	}
	@Test  public void Gallery() {
		fxt.Clear_imgs();
		fxt	.Init_data_gly(0, 800);
		fxt	.Init_data_img_gly(0, 220, 110, "A.png", "commons.wikimedia.org/thumb/7/0/A.png/220.png", 155, 150, 15);
		fxt	.Init_body(String_.Concat_lines_nl_skip_last
		( "<ul xowa_gly_box_max='0'>"
		, "  <li class='gallerybox' xowa_gly_box_w='0'>"
		, "    <div xowa_gly_box_w='0'>"
		, "      <div class='thumb' xowa_gly_img_w='0'>"
		, "        <div xowa_gly_img_pad='0'>"
		))
		.Test_html(String_.Concat_lines_nl_skip_last
		( "<ul style=\"max-width:800px;_width:800px;\">"
		, "  <li class='gallerybox' style=\"width:155px;\">"
		, "    <div style=\"width:155px;\">"
		, "      <div class='thumb' style=\"width:150px;\">"
		, "        <div style=\"margin:15px auto;\">"
		));
	}
}
class Hdump_html_mgr_fxt {
	private Hdump_html_mgr html_mgr;
	private Bry_bfr bfr = Bry_bfr.reset_(255);
	private ListAdp img_list = ListAdp_.new_();
	private Xow_wiki wiki;
	public Hdump_page Hpg() {return hpg;} private Hdump_page hpg = new Hdump_page();
	public void Clear() {
		Xoa_app app = Xoa_app_fxt.app_();
		wiki = Xoa_app_fxt.wiki_tst_(app);
		html_mgr = wiki.Db_mgr().Hdump_mgr().Html_mgr();
	}
	public void Clear_imgs() {img_list.Clear();}
	public Hdump_html_mgr_fxt Init_body(String body) {hpg.Page_body_(Bry_.new_utf8_(body)); return this;}
	public Hdump_html_mgr_fxt Init_data_gly(int uid, int box_max) {hpg.Gly_itms().Add(uid, new Hdump_data_gallery(uid, box_max)); return this;}
	public Hdump_html_mgr_fxt Init_data_img_basic(int uid, int w, int h, String ttl, String src) {img_list.Add(new Hdump_data_img__basic().Init_by_base(uid, w, h, Bry_.new_utf8_(ttl), Bry_.new_utf8_(src))); return this;}
	public Hdump_html_mgr_fxt Init_data_img_gly(int uid, int w, int h, String ttl, String src, int box_w, int img_w, int img_pad) {img_list.Add(new Hdump_data_img__gallery().Init_by_gallery(box_w, img_w, img_pad).Init_by_base(uid, w, h, Bry_.new_utf8_(ttl), Bry_.new_utf8_(src))); return this;}
	public Hdump_html_mgr_fxt Test_html(String expd) {
		if (img_list.Count() > 0) hpg.Img_itms_((Hdump_data_img__base[])img_list.XtoAryAndClear(Hdump_data_img__base.class));
		html_mgr.Write(bfr, wiki, hpg);
		Tfds.Eq_str_lines(expd, bfr.XtoStrAndClear());
		return this;
	}
}
