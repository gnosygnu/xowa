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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import org.junit.*; import gplx.xowa.files.*;
public class Xoh_file_wtr__image__basic__tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {fxt.Reset();}
	@Test  public void Img__full() {	// PURPOSE: full with title was outputting invalid html; DATE:2013-12-31
		fxt.Wtr_cfg().Lnki__title_(true);
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png]]"
		, String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"	// NOTE: used to output class=\"image\"A.png 
		));
		fxt.Wtr_cfg().Lnki__title_(false);
	}
	@Test  public void Xowa_title__quotes() {	// PURPOSE: xowa_title should encode quotes DATE:2015-11-27
		fxt.Test_parse_page_wiki_str
		( "[[File:A%22b.png]]"
		, String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/File:A%22b.png\" class=\"image\" xowa_title=\"A%22b.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/d/4/A%22b.png\" width=\"0\" height=\"0\" /></a>"
		));
	}
	@Test  public void Img__embed() {
		fxt.Test_parse_page_wiki_str("[[File:A.png|9x8px|alt=abc]]", Xop_fxt.html_img_none("File:A.png", "abc", "file:///mem/wiki/repo/trg/thumb/7/0/A.png/9px.png", "A.png"));
	}
	@Test   public void Embed_audio() {
		fxt.Test_parse_page_wiki_str("[[File:A.jpg|thumb|b[[File:C.ogg|right|140x140px]]d]]", String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
		, "    <a href=\"/wiki/File:A.jpg\" class=\"image\" xowa_title=\"A.jpg\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/6/7/A.jpg/220px.jpg\" width=\"0\" height=\"0\" /></a>"
		, "    <div class=\"thumbcaption\">"
		, "<div class=\"magnify\"><a href=\"/wiki/File:A.jpg\" class=\"internal\" title=\"Enlarge\"></a></div>b    <div class=\"xowa_media_div\">"
		, "      <div><a href=\"/wiki/File:C.ogg\" class=\"image\" title=\"C.ogg\" xowa_title=\"C.ogg\"><img id=\"xoimg_1\" alt=\"\" src=\"\" width=\"140\" height=\"140\" /></a>"
		, "      </div>"
		, "<div><a id=\"xowa_file_play_1\" href=\"file:///mem/wiki/repo/trg/orig/d/3/C.ogg\" xowa_title=\"C.ogg\" class=\"xowa_media_play\" style=\"width:138px;max-width:140px;\" alt=\"Play sound\"></a></div>"
		, "    </div>d"
		, "    </div>"
		, "  </div>"
		, "</div>"
		));
	}
	@Test  public void Img__none() {	// NOTE: floatnone is WP behavior; MW omits div tag
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|none|20x30px|b]]"
		, String_.Concat_lines_nl_skip_last
		( "<div class=\"floatnone\">"
		, "<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"b\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/20px.png\" width=\"20\" height=\"30\" /></a></div>"
		));
	}
	@Test  public void Img__thumb_none() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|thumb|none|b]]"
		, Img_thumb_str("none")
		);
	}
	@Test  public void Img__thumb_ltr() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|thumb|b]]"
		, Img_thumb_str("right")
		);
	}
	@Test  public void Img__thumb_rtl() {
		fxt.Wiki().Lang().Dir_ltr_(false);
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|thumb|b]]"
		, Img_thumb_str("left")
		);
		fxt.Wiki().Lang().Dir_ltr_(true);
	}
	private String Img_thumb_str(String align) {
		return	String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb t" + align + "\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
		, "    <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/220px.png\" width=\"0\" height=\"0\" /></a>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.png\" class=\"internal\" title=\"Enlarge\"></a></div>b"
		, "    </div>"
		, "  </div>"
		, "</div>"
		, ""
		);
	}
	@Test  public void Img__frame() {	// PURPOSE: lnki with "frame" is same as thumb; DATE:2013-12-23
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|frame|220x110px|b]]"
		, String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
		, "    <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/220px.png\" width=\"220\" height=\"110\" /></a>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.png\" class=\"internal\" title=\"Enlarge\"></a></div>b"
		, "    </div>"
		, "  </div>"
		, "</div>"
		, ""
		));
	}
	@Test  public void Img__frame_and_thumb() {	// PURPOSE: lnki with "frame and thumb" was not showing box due to bit-adding; PAGE:en.w:History_of_Western_Civilization DATE:2015-04-16
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|frame|thumb|220x110px|b]]"	// NOTE: frame AND thumb
		, String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
		, "    <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/220px.png\" width=\"220\" height=\"110\" /></a>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.png\" class=\"internal\" title=\"Enlarge\"></a></div>b"
		, "    </div>"
		, "  </div>"
		, "</div>"
		, ""
		));
	}
	@Test  public void Thm__alt_is_ws() {	// PURPOSE: alt with space should not output <hr>; EX:[[File:A.png|thumb|alt= ]]; en.w:Bird; DATE:2015-12-28
		fxt.Test_parse_page_all_str
		( "[[File:A.png|thumb|220x110px|alt= ]]"
		, String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
		, "    <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\" \" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/220px.png\" width=\"220\" height=\"110\" /></a>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.png\" class=\"internal\" title=\"Enlarge\"></a></div>"
		, "    </div>"
		, "  </div>"
		, "</div>"
		, ""
		));
	}
	@Test  public void Cls_border() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|border]]"
		, "<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" class=\"thumbborder\" /></a>");
	}
	@Test  public void Cls_custom() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|class=abc]]"
		, "<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" class=\"abc\" /></a>");
	}
	@Test  public void Cls_border_custom() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|border|class=abc]]"
		, "<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" class=\"thumbborder abc\" /></a>");
	}
	@Test  public void Lnki_full_svg() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.svg|a|alt=b]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/File:A.svg\" class=\"image\" xowa_title=\"A.svg\"><img id=\"xoimg_0\" alt=\"b\" src=\"file:///mem/wiki/repo/trg/thumb/7/5/A.svg/-1px.png\" width=\"0\" height=\"0\" /></a>"	// HACK: tries to get orig_w which is not available
		));		
	}
	@Test  public void Lnki_file_alt_link() {	// PURPOSE: lnki in caption should not create alt="b<a href="c">cd</a>"
		fxt.Test_parse_page_wiki_str("[[File:A.png|thumb|alt=b [[c]] d]]", String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
		, "    <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"b c d\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/220px.png\" width=\"0\" height=\"0\" /></a>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.png\" class=\"internal\" title=\"Enlarge\"></a></div>"
		, "    </div>"
		, "    <div class=\"xowa_alt_text\">"
		, "    <hr/>"
		, "    <div class=\"thumbcaption\">b <a href=\"/wiki/C\">c</a> d"
		, "    </div>"
		, "    </div>"
		, "  </div>"
		, "</div>"
		, ""
		));
	}
	@Test  public void Pre_in_caption() {	// PURPOSE: ignore pre if in caption; PAGE:s.w:Virus; DATE:2015-03-31
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "[[File:A.png|thumb|a\n b]]"	// "\n " is pre
		), String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
		, "    <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/220px.png\" width=\"0\" height=\"0\" /></a>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.png\" class=\"internal\" title=\"Enlarge\"></a></div>a"						// no pre
		, " b"
		, "    </div>"
		, "  </div>"
		, "</div>"
		, ""
		));
		fxt.Init_para_n_();
	}
	@Test  public void Img__title() {
		fxt.Wtr_cfg().Lnki__title_(true);
		Tst_img_title("[[File:A.png|frameless|a b]]", "a b");
		Tst_img_title("[[File:A.png|thumb|a b]]", "Enlarge");	// caption should not replace text
		fxt.Wtr_cfg().Lnki__title_(false);
	}
	@Test   public void Title_escape() {	// PURPOSE: escape quotes in title; PAGE:none; DATE:2014-10-27
		fxt.Wtr_cfg().Lnki__title_(true);
		fxt.Test_parse_page_wiki_str("[[A\"B]]", "<a href=\"/wiki/A%22B\" title=\"A&quot;B\">A\"B</a>");
		fxt.Wtr_cfg().Lnki__title_(false);
	}
	@Test  public void Img__title__caption_has_lnki() {	// PURPOSE: caption with lnki should show in title; PAGE:en.w:Earth; DATE:2014-08-06
		fxt.Wtr_cfg().Lnki__title_(true);
		Tst_img_title("[[File:A.png|frameless|[[A]]]]"		, "A");		// ttl only
		Tst_img_title("[[File:A.png|frameless|[[A|B]]]]"	, "B");		// caption
		Tst_img_title("[[File:A.png|frameless|[[A]]b]]"		, "Ab");	// tail
		fxt.Wtr_cfg().Lnki__title_(false);
	}
	@Test  public void Lnki_alt_is_text() {	// PURPOSE: (a) alt should default to caption; (b) alt should not show html chars (like <a src=")
		fxt.Wtr_cfg().Lnki__title_(true);
		fxt.Test_parse_page_all_str
		( "[[File:A.png|a[[b]]c]]"
		, "<a href=\"/wiki/File:A.png\" class=\"image\" title=\"aBc\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"abc\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
		);
		fxt.Wtr_cfg().Lnki__title_(false);
	}
	@Test  public void Alt_ignore_apos() {// PURPOSE: alt should ignore apos; EX: [[File:A.png|''A'']] should have alt of A; DATE:2013-10-25
		fxt.Wtr_cfg().Lnki__title_(true);
		fxt.Test_parse_page_all_str
			( "[[File:A.png|''b'']]"
			, "<a href=\"/wiki/File:A.png\" class=\"image\" title=\"b\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"b\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
			);
		fxt.Wtr_cfg().Lnki__title_(false);
	}
	@Test  public void Alt_ignore_lnke() {// PURPOSE: alt should ignore lnke
		fxt.Wtr_cfg().Lnki__title_(true);
		fxt.Test_parse_page_all_str
			( "[[File:A.png|b[http://c.org d] e]]"
			, "<a href=\"/wiki/File:A.png\" class=\"image\" title=\"bd e\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"bd e\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
			);
		fxt.Wtr_cfg().Lnki__title_(false);
	}
	@Test  public void Alt_ignore_list() {// PURPOSE: alt should ignore list
		fxt.Wtr_cfg().Lnki__title_(true);
		fxt.Test_parse_page_all_str
		( "[[File:A.png|b\n*c]]"
		, "<a href=\"/wiki/File:A.png\" class=\"image\" title=\"bc\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"b*c\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
		);
		fxt.Wtr_cfg().Lnki__title_(false);
	}
	@Test  public void Alt_ignore_tblw() {// PURPOSE: alt should ignore tblw
		fxt.Wtr_cfg().Lnki__title_(true);
		fxt.Test_parse_page_all_str
			( "[[File:A.png|\n{|\n|-\n|b\n|}\n]]"
			, "<a href=\"/wiki/File:A.png\" class=\"image\" title=\"b \" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"   b  \" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
			);
		fxt.Wtr_cfg().Lnki__title_(false);
	}
	@Test  public void Alt_ignore_para() {// PURPOSE: alt should ignore para
		fxt.Wtr_cfg().Lnki__title_(true);
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str
			( "[[File:A.png|b\nc]]"
			, String_.Concat_lines_nl
			( "<p><a href=\"/wiki/File:A.png\" class=\"image\" title=\"b c\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"b c\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
			, "</p>"
			));
		fxt.Init_para_n_();
		fxt.Wtr_cfg().Lnki__title_(false);
	}
	@Test  public void Lnki_empty_alt_is_omitted() {// PURPOSE: empty alt should be ignored; DATE:2013-07-30
		fxt.Wtr_cfg().Lnki__title_(true);
		fxt.Test_parse_page_all_str
		( "[[File:A.png|a|alt=]]"
		, "<a href=\"/wiki/File:A.png\" class=\"image\" title=\"a\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
		);
		fxt.Wtr_cfg().Lnki__title_(false);
	}
	@Test   public void Href_anchor_leading_space() {	// PURPOSE: space before anchor should be preserved, not " " -> "#"
		fxt.Test_parse_page_all_str("[[A #b]]", "<a href=\"/wiki/A#b\">A #b</a>");
	}
	@Test   public void Href_anchor_leading_space_ns() {	// PURPOSE: same as above, but with ns; DATE:2013-08-29
		fxt.Test_parse_page_all_str("[[Help:A   #b]]", "<a href=\"/wiki/Help:A#b\">Help:A #b</a>");
	}
	@Test   public void Href_anchor_leading_ns_lc() {	// PURPOSE: same as above but with lc title
		fxt.Test_parse_page_all_str("[[Help:a#b]]", "<a href=\"/wiki/Help:A#b\">Help:A#b</a>");
	}
	@Test   public void Href_anchor_leading_space_ns_lc() {	// PURPOSE: same as above but with lc title
		fxt.Test_parse_page_all_str("[[Help:a #b]]", "<a href=\"/wiki/Help:A#b\">Help:A #b</a>");
	}
	@Test  public void Lnki_caption_nested_file() { // PURPOSE: nested lnki in caption breaks alt with html chars; EX:de.w:Wien; DATE:2013-12-16
		fxt.Wtr_cfg().Lnki__title_(true);
		fxt.Test_parse_page_wiki_str("[[File:A.png|none|[[File:B.png|20px|d]] c]]", String_.Concat_lines_nl_skip_last
			( "<div class=\"floatnone\">"
			, "<a href=\"/wiki/File:A.png\" class=\"image\" title=\"d c\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"d c\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a></div>"
			, ""
			));
		fxt.Wtr_cfg().Lnki__title_(false);
	}
	@Test  public void Link__file() {	// PURPOSE.FIX: link=file:/// was creating "href='/wiki/file'" handle IPA links; EX:[[File:Speakerlink-new.svg|11px|link=file:///C:/xowa/file/commons.wikimedia.org/orig/c/7/a/3/En-LudwigVanBeethoven.ogg|Listen]]; PAGE:en.w:Beethoven DATE:2015-12-28
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|11px|link=file:///C:/A.ogg|b]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"file:///C:/A.ogg\" class=\"image\" xowa_title=\"A.ogg\">"
		+ "<img id=\"xoimg_0\" alt=\"b\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/11px.png\" width=\"11\" height=\"0\" />"
		+ "</a>"
		));		
	}
	@Test  public void Link__empty() {	// empty link should not create anchor; EX:[[File:A.png|link=|abc]]; PAGE:en.w:List_of_counties_in_New_York; DATE:2016-01-10
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|11px|link=|abc]]", String_.Concat_lines_nl_skip_last
		( "<img id=\"xoimg_0\" alt=\"abc\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/11px.png\" width=\"11\" height=\"0\" />"
		));		
	}
	@Test  public void Link__lc() {	// links to items in same Srch_rslt_cbk should automatically title-case words; DATE:2016-01-11
		fxt.Init_xwiki_add_wiki_and_user_("en", "en.wikipedia.org");
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|11px|link=en:Help:a?b=c#d|abc]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/Help:A?b=c#d\" class=\"image\" xowa_title=\"A.png\">"	// "Help:A" not "Help:a"
		+ "<img id=\"xoimg_0\" alt=\"abc\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/11px.png\" width=\"11\" height=\"0\" />"
		+ "</a>"));
		fxt.Init_xwiki_clear();
	}
	@Test  public void Redirect() {// PURPOSE: redirect should use trg_lnki, not src_lnki; DATE:2016-08-10
		Xof_file_fxt file_fxt = Xof_file_fxt.new_all(fxt.Wiki());
		file_fxt.Exec_orig_add(Bool_.Y, "A.png", Xof_ext_.Id_png, 320, 300, "B.png");
		fxt.Wiki().File__fsdb_mode().Tid__v2__mp__y_();

		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|320px|bcd|alt=efg]]"
		, "<a href=\"/wiki/File:B.png\" class=\"image\" xowa_title=\"B.png\"><img id=\"xoimg_0\" alt=\"efg\" src=\"file:///mem/wiki/repo/trg/orig/5/7/B.png\" width=\"320\" height=\"300\" /></a>"
		);

		fxt.Wiki().File__fsdb_mode().Tid__v2__bld__y_();
	}
	@Test  public void Imap() {
		Xof_file_fxt file_fxt = Xof_file_fxt.new_all(fxt.Wiki());
		file_fxt.Exec_orig_add(Bool_.Y, "A.png", Xof_ext_.Id_png, 320, 300, "");
		fxt.Wiki().File__fsdb_mode().Tid__v2__mp__y_();
		fxt.Hctx_(gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx.Hdump);

		fxt.Test_parse_page_wiki_str
		( String_.Concat_lines_nl_skip_last
		( "<imagemap>"
		, "File:A.png|thumb|320px|a1"
		, "circle 0 0 5 [[B|b1]]"
		, "rect 0 0 4 8 [[C|c1]]"
		, "desc none"
		, "</imagemap>"
		)
		, String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div class=\"thumbinner\" style=\"width:320px;\">"
		, "    <div id=\"imap_div_0\" class=\"noresize\">"
		, "      <map name=\"imageMap_1_1\">"
		, "        <area href=\"/wiki/B\" shape=\"circle\" coords=\"0,0,5\" alt=\"b1\" title=\"b1\"/>"
		, "        <area href=\"/wiki/C\" shape=\"rect\" coords=\"0,0,4,8\" alt=\"c1\" title=\"c1\"/>"
		, "      </map>"
		, "      <img src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"320\" height=\"300\" alt=\"\" usemap=\"#imagemap_1_1\"/>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.png\" class=\"internal\" title=\"Enlarge\"></a></div>a1"
		, "    </div>"
		, "  </div>"
		, "</div>"
		));

		fxt.Wiki().File__fsdb_mode().Tid__v2__bld__y_();
	}
	private void Tst_img_title(String raw, String expd_ttl) {
		String actl = fxt.Exec_parse_page_wiki_as_str(raw);
		String actl_ttl = null;
		int title_bgn = String_.FindFwd(actl, " title=\"");
		if (title_bgn != String_.Find_none) {
			title_bgn += String_.Len(" title=\"");
			int title_end = String_.FindFwd(actl, "\"", title_bgn);
			if (title_end != String_.Find_none) actl_ttl = String_.Mid(actl, title_bgn, title_end);
		}
		Tfds.Eq(expd_ttl, actl_ttl, actl);
	}
}
