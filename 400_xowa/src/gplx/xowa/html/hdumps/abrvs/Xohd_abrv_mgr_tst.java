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
package gplx.xowa.html.hdumps.abrvs; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*;
import org.junit.*; import gplx.xowa.html.hdumps.core.*; import gplx.xowa.html.hdumps.data.*; import gplx.xowa.files.*;
import gplx.xowa2.gui.*;
public class Xohd_abrv_mgr_tst {
	@Before public void init() {
		fxt.Clear();
		fxt.Init_data_img_basic("A.png", 0, 220, 110);
	}	private final Xohd_abrv_mgr_fxt fxt = new Xohd_abrv_mgr_fxt();
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
class Xohd_abrv_mgr_fxt {
	private Xohd_abrv_mgr abrv_mgr;
	private Bry_bfr bfr = Bry_bfr.reset_(255);
	private ListAdp img_list = ListAdp_.new_();
	private Xowe_wiki wiki;
	public Xog_page Hpg() {return hpg;} private Xog_page hpg = new Xog_page();
	public void Clear() {
		Xoae_app app = Xoa_app_fxt.app_();
		wiki = Xoa_app_fxt.wiki_tst_(app);
		abrv_mgr = new Xohd_abrv_mgr(Gfo_usr_dlg_._, app.Fsys_mgr(), app.Utl__encoder_mgr().Fsys(), wiki.Domain_bry());
	}
	public void Clear_imgs() {img_list.Clear();}
	public Xohd_abrv_mgr_fxt Init_body(String body) {hpg.Page_body_(Bry_.new_utf8_(body)); return this;}
	public Xohd_abrv_mgr_fxt Init_data_gly(int uid, int box_max) {hpg.Gly_itms().Add(uid, new Xohd_data_itm__gallery_mgr(uid, box_max)); return this;}
	public Xohd_abrv_mgr_fxt Init_data_img_basic(String ttl, int html_uid, int html_w, int html_h) {
		Xohd_data_itm__img img = new Xohd_data_itm__img();
		img.Data_init_base(Bry_.new_utf8_(ttl), Xof_ext_.Id_png, Xop_lnki_type.Id_none, Xof_img_size.Null, Xof_img_size.Null, Xof_img_size.Upright_null
			, Xof_lnki_time.Null, Xof_lnki_page.Null
			, Xohd_data_itm__base.File_repo_id_commons, Bool_.N, html_w
			, html_uid, html_w, html_h
			);
		img_list.Add(img);
		return this;
	}
	public Xohd_abrv_mgr_fxt Init_data_img_gly(String ttl, int html_uid, int html_w, int html_h, int box_w, int img_w, int img_pad) {
		Xohd_data_itm__gallery_itm img = new Xohd_data_itm__gallery_itm();
		img.Data_init_gallery(box_w, img_w, img_pad);
		img.Data_init_base(Bry_.new_utf8_(ttl), Xof_ext_.Id_png, Xop_lnki_type.Id_none, Xof_img_size.Null, Xof_img_size.Null, Xof_img_size.Upright_null
			, Xof_lnki_time.Null, Xof_lnki_page.Null
			, Xohd_data_itm__base.File_repo_id_commons, Bool_.N, html_w
			, html_uid, html_w, html_h
			);
		img_list.Add(img);
		return this;
	}
	public Xohd_abrv_mgr_fxt Init_data_redlink(int max, int... uids) {
		int[] ary = new int[max + ListAdp_.Base1];
		int uids_len = uids.length;
		for (int i = 0; i < uids_len; ++i) {
			int uid = uids[i];
			ary[uid] = 1;
		}
		hpg.Redlink_uids_(ary);
		return this;
	}
	public Xohd_abrv_mgr_fxt Test_html(String expd) {
		if (img_list.Count() > 0) hpg.Img_itms_((Xohd_data_itm__base[])img_list.Xto_ary_and_clear(Xohd_data_itm__base.class));
		byte[] actl = abrv_mgr.Parse(bfr, hpg);
		Tfds.Eq_str_lines(expd, String_.new_utf8_(actl));
		return this;
	}
}
