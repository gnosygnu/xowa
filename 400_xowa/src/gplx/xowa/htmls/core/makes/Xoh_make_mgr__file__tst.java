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
//namespace gplx.xowa.htmls.core.makes {
//	import org.junit.*;
//	public class Xoh_make_mgr__file__tst {
//		private final Xoh_make_mgr_fxt fxt = new Xoh_make_mgr_fxt();
//		@Before public void init() {
//			fxt.Clear();
//			fxt.Init_data_img_basic("A.png", 0, 220, 110);
//		}
//		@Test  public void Img() {
//			fxt	.Init_body("<img xowa_img='0' />")
//				.Test_html("<img src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/220px.png' width='220' height='110' />");
//		}
//		@Test  public void Img_style() {
//			fxt	.Init_body("<div xowa_img_style='0'>")
//				.Test_html("<div style='width:220px;'>");
//		}
//		@Test  public void File_info() {
//			fxt	.Init_body("<xowa_info id='0'/>")
//				.Test_html(String_.Concat_lines_nl_skip_last
//				( ""
//				, "      <div>"
//				, "        <a href=\"/wiki/File:A.png\" class=\"image\" title=\"About this file\">"
//				, "          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/info.png\" width=\"22\" height=\"22\" />"
//				, "        </a>"
//				, "      </div>"
//				));
//		}
//		@Test  public void File_mgnf() {
//			fxt	.Init_body("<xowa_mgnf id='0'/>")
//				.Test_html(String_.Concat_lines_nl_skip_last
//				( ""
//				, "      <div class=\"magnify\">"
//				, "        <a href=\"/wiki/File:A.png\" class=\"internal\" title=\"A.png\">"
//				, "          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/magnify-clip.png\" width=\"15\" height=\"11\" alt=\"\" />"
//				, "        </a>"
//				, "      </div>"
//				));
//		}
//		@Test  public void File_play() {
//			fxt	.Init_body("<xowa_play id='0'/>")
//				.Test_html(String_.Concat_lines_nl_skip_last
//				( ""
//				, "      <div>"
//				, "        <a id=\"xowa_file_play_0\" href=\"/wiki/File:A.png\" xowa_title=\"A.png\" class=\"xowa_anchor_button\" style=\"width:220px;max-width:1024px;\">"
//				, "          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/play.png\" width=\"22\" height=\"22\" alt=\"Play sound\" />"
//				, "        </a>"
//				, "      </div>"
//				));
//		}
//		@Test  public void Hiero_dir() {
//			fxt	.Init_body("<img src='~{xowa_hiero_dir}hiero_a&A1.png' />")
//				.Test_html("<img src='file:///mem/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_a&A1.png' />");
//		}
//		@Test  public void Gallery() {
//			fxt.Clear_imgs();
//			fxt	.Init_data_gly(0, 800);
//			fxt	.Init_data_img_gly("A.png", 0, 220, 110, 155, 150, 15);
//			fxt	.Init_body(String_.Concat_lines_nl_skip_last
//			( "<ul xowa_gly_box_max='0'>"
//			, "  <li class='gallerybox' xowa_gly_box_w='0'>"
//			, "    <div xowa_gly_box_w='0'>"
//			, "      <div class='thumb' xowa_gly_img_w='0'>"
//			, "        <div xowa_gly_img_pad='0'>"
//			))
//			.Test_html(String_.Concat_lines_nl_skip_last
//			( "<ul style=\"max-width:800px;_width:800px;\">"
//			, "  <li class='gallerybox' style=\"width:155px;\">"
//			, "    <div style=\"width:155px;\">"
//			, "      <div class='thumb' style=\"width:150px;\">"
//			, "        <div style=\"margin:15px auto;\">"
//			));
//		}
//	}
//}
