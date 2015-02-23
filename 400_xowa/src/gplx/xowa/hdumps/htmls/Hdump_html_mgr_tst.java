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
import org.junit.*; import gplx.xowa.hdumps.core.*; import gplx.xowa.hdumps.dbs.*; import gplx.xowa.files.*;
import gplx.xowa2.gui.*;
public class Hdump_html_mgr_tst {
	@Before public void init() {
		fxt.Clear();
		fxt.Init_data_img_basic("A.png", 0, 220, 110);
	}	private Hdump_html_mgr_fxt fxt = new Hdump_html_mgr_fxt();
	@Test  public void Img() {
		fxt	.Init_body("<img xowa_img='0' />")
			.Test_html("<img src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/220px.png' width='220' height='110' />");
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
			, "          <img src=\"file:///mem/xowa/bin/any/xowa/file/mw.file/info.png\" width=\"22\" height=\"22\" />"
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
			, "          <img src=\"file:///mem/xowa/bin/any/xowa/file/mw.file/magnify-clip.png\" width=\"15\" height=\"11\" alt=\"\" />"
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
			, "          <img src=\"file:///mem/xowa/bin/any/xowa/file/mw.file/play.png\" width=\"22\" height=\"22\" alt=\"Play sound\" />"
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
		fxt	.Init_data_img_gly("A.png", 0, 220, 110, 155, 150, 15);
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
	@Test  public void Redlink() {
		fxt	.Init_data_redlink(2, 1, 2);
		fxt	.Init_body(String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/A\" xowa_redlink='1'>A</a>"
		, "<a href=\"/wiki/B\" xowa_redlink='2'>B</a>"
		, "<a href=\"/wiki/C\" xowa_redlink='3'>C</a>"
		))
		.Test_html(String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/A\" class='new'>A</a>"
		, "<a href=\"/wiki/B\" class='new'>B</a>"
		, "<a href=\"/wiki/C\">C</a>"
		));
	}
}
class Hdump_html_mgr_fxt {
	private Hdump_html_body html_mgr;
	private Bry_bfr bfr = Bry_bfr.reset_(255);
	private ListAdp img_list = ListAdp_.new_();
	private Xowe_wiki wiki;
	public Xog_page Hpg() {return hpg;} private Xog_page hpg = new Xog_page();
	public void Clear() {
		Xoae_app app = Xoa_app_fxt.app_();
		wiki = Xoa_app_fxt.wiki_tst_(app);
		html_mgr = wiki.Db_mgr().Hdump_mgr().Html_mgr();
	}
	public void Clear_imgs() {img_list.Clear();}
	public Hdump_html_mgr_fxt Init_body(String body) {hpg.Page_body_(Bry_.new_utf8_(body)); return this;}
	public Hdump_html_mgr_fxt Init_data_gly(int uid, int box_max) {hpg.Gly_itms().Add(uid, new Hdump_data_gallery(uid, box_max)); return this;}
	public Hdump_html_mgr_fxt Init_data_img_basic(String ttl, int html_uid, int html_w, int html_h) {
		Hdump_data_img__basic img = new Hdump_data_img__basic();
		img.Init_by_base(Bry_.new_utf8_(ttl), html_uid, html_w, html_h, Hdump_data_img__base.File_repo_id_commons, Xof_ext_.Id_png, Bool_.N, html_w, Xof_doc_thumb.Null, Xof_doc_page.Null);
		img_list.Add(img);
		return this;
	}
	public Hdump_html_mgr_fxt Init_data_img_gly(String ttl, int html_uid, int html_w, int html_h, int box_w, int img_w, int img_pad) {
		Hdump_data_img__gallery img = new Hdump_data_img__gallery();
		img.Init_by_gallery(box_w, img_w, img_pad);
		img.Init_by_base(Bry_.new_utf8_(ttl), html_uid, html_w, html_h, Hdump_data_img__base.File_repo_id_commons, Xof_ext_.Id_png, Bool_.N, html_w, Xof_doc_thumb.Null, Xof_doc_page.Null);
		img_list.Add(img);
		return this;
	}
	public Hdump_html_mgr_fxt Init_data_redlink(int max, int... uids) {
		int[] ary = new int[max + ListAdp_.Base1];
		int uids_len = uids.length;
		for (int i = 0; i < uids_len; ++i) {
			int uid = uids[i];
			ary[uid] = 1;
		}
		hpg.Redlink_uids_(ary);
		return this;
	}
	public Hdump_html_mgr_fxt Test_html(String expd) {
		if (img_list.Count() > 0) hpg.Img_itms_((Hdump_data_img__base[])img_list.Xto_ary_and_clear(Hdump_data_img__base.class));
		html_mgr.Init_by_page(wiki.Domain_bry(), hpg).Write(bfr);
		Tfds.Eq_str_lines(expd, bfr.Xto_str_and_clear());
		return this;
	}
}
