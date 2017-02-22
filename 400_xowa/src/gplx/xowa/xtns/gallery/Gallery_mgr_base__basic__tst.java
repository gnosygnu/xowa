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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
import gplx.xowa.htmls.*; import gplx.xowa.files.fsdb.*;
public class Gallery_mgr_base__basic__tst {
	private Gallery_mgr_base_fxt fxt = new Gallery_mgr_base_fxt();
	@Before public void init() {fxt.Reset();}
	@Test  public void Basic() {
		fxt.Test_html_str(String_.Concat_lines_nl_skip_last
		( "<gallery widths=200px heights=300px>"
		, "A.png|''a1''"
		, "B.png|''b1''"
		, "</gallery>"
		), String_.Concat_lines_nl_skip_last
		( "<ul id=\"xowa_gallery_ul_0\" class=\"gallery mw-gallery-traditional\">"
		, "  <li id=\"xowa_gallery_li_0\" class=\"gallerybox\" style=\"width:235px;\">"
		, "    <div style=\"width:235px;\">"
		, "      <div class=\"thumb\" style=\"width:230px;\">"
		, "        <div style=\"margin:15px auto;\">"
		, "          <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/200px.png\" width=\"200\" height=\"300\" /></a>"
		, "        </div>"
		, "      </div>"
		, "      <div class=\"gallerytext\"><p><i>a1</i>"
		, "</p>"
		, ""
		, "      </div>"
		, "    </div>"
		, "  </li>"
		, "  <li id=\"xowa_gallery_li_1\" class=\"gallerybox\" style=\"width:235px;\">"
		, "    <div style=\"width:235px;\">"
		, "      <div class=\"thumb\" style=\"width:230px;\">"
		, "        <div style=\"margin:15px auto;\">"
		, "          <a href=\"/wiki/File:B.png\" class=\"image\" xowa_title=\"B.png\"><img id=\"xoimg_1\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/5/7/B.png/200px.png\" width=\"200\" height=\"300\" /></a>"
		, "        </div>"
		, "      </div>"
		, "      <div class=\"gallerytext\"><p><i>b1</i>"
		, "</p>"
		, ""
		, "      </div>"
		, "    </div>"
		, "  </li>"
		, "</ul>"
		));
		fxt.Test_html_modules_js(String_.Concat_lines_nl_skip_last
		( ""
		, "  <link rel=\"stylesheet\" href=\"file:///mem/xowa/bin/any/xowa/html/res/src/mediawiki.page/mediawiki.page.gallery.css\" type='text/css'>"
		));
	}
	@Test  public void Tmpl() {
		fxt.Fxt().Init_defn_add("test_tmpl", "b");
		fxt.Test_html_frag("<gallery>File:A.png|a{{test_tmpl}}c</gallery>", "<div class=\"gallerytext\"><p>abc\n</p>");
	}
	@Test  public void Itm_defaults_to_120() {
		fxt.Test_html_frag("<gallery>File:A.png|a</gallery>", "<img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/120px.png\" width=\"120\" height=\"120\" />");
	}
	@Test   public void Height_fix() {
		fxt.Fxt().Wiki().File_mgr().Cfg_set(Xof_fsdb_mgr_cfg.Grp_xowa, Xof_fsdb_mgr_cfg.Key_gallery_fix_defaults, "y");
		fxt.Test_html_frag("<gallery heights=250>File:A.png|a<br/>c</gallery>", " width=\"120\" height=\"250\"");
		fxt.Test_html_frag("<div style=\"margin:15px auto;\">");
		fxt.Fxt().Wiki().File_mgr().Cfg_set(Xof_fsdb_mgr_cfg.Grp_xowa, Xof_fsdb_mgr_cfg.Key_gallery_fix_defaults, "n");
	}
	@Test   public void Alt() {
		fxt.Test_html_frag("<gallery>File:A.png|b|alt=c</gallery>"
		, "<img id=\"xoimg_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/120px.png\" width=\"120\" height=\"120\" />"
		, "<div class=\"gallerytext\"><p>b\n</p>"
		);
	}
	@Test   public void Link() {
		fxt.Test_html_frag("<gallery>File:A.png|b|link=c</gallery>", "<a href=\"/wiki/C\" class=\"image\"");
	}
	@Test  public void Page() {	// PURPOSE: page was not being set; PAGE:pt.s:Portal:Diccionario_geographico_do_Brazil; DATE:2015-04-16
		fxt.Test_html_frag
		( "<gallery>File:A.pdf|b|page=8</gallery>"
		, "A.pdf/120px-8.jpg"	// make sure page 8 shows up
		);
	}
	@Test   public void Alt_caption_multiple() {
		fxt.Test_html_frag("<gallery>File:A.png|alt=b|c[[d|e]]f</gallery>", "<div class=\"gallerytext\"><p>c<a href=\"/wiki/D\">ef</a>\n</p>");
	}
	@Test   public void Alt_escape_quote() {
		fxt.Test_html_frag("<gallery>File:A.png|b|alt=c\"d'e</gallery>", "alt=\"c&quot;d'e\"");
	}
	@Test   public void Caption_null() {	// PURPOSE: null caption causes page to fail; EX: de.w:Lewis Caroll; <gallery>Datei:A.png</gallery>; DATE:2013-10-09
		fxt.Test_html_frag("<gallery>File:A.png</gallery>", "<div class=\"gallerytext\">\n");
	}
	@Test   public void Ttl_has_no_ns() {	// PURPOSE: MW allows ttl to not have ns; DATE: 2013-11-18
		fxt.Test_html_frag("<gallery>A.png|b</gallery>", "<img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/120px.png\" width=\"120\" height=\"120\" />");	// make sure image is generated
	}
	@Test   public void Ref() {	// PURPOSE: <ref> inside <gallery> was not showing up in <references>; DATE:2013-10-09
		fxt.Test_html_frag("<gallery>File:A.png|<ref name='a'>b</ref></gallery><references/>"
		, "<div class=\"gallerytext\"><p><sup id=\"cite_ref-a_0-0\" class=\"reference\"><a href=\"#cite_note-a-0\">[1]</a></sup>\n</p>"
		, String_.Concat_lines_nl
		( "</ul><ol class=\"references\">"
		, "<li id=\"cite_note-a-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-a_0-0\">^</a></span> <span class=\"reference-text\">b</span></li>"
		, "</ol>"
		)
		);
	}
	@Test   public void Packed() {
		fxt.Test_html_frag("<gallery mode=packed heights=300px>File:A.png|a</gallery>", "<ul id=\"xowa_gallery_ul_0\" class=\"gallery mw-gallery-packed\">");
		fxt.Test_html_modules_js(String_.Concat_lines_nl_skip_last
		( ""
		, "  <link rel=\"stylesheet\" href=\"file:///mem/xowa/bin/any/xowa/html/res/src/mediawiki.page/mediawiki.page.gallery.css\" type='text/css'>"
		, "  <script type='text/javascript'>"
		, "    var xowa_global_values = {"
		, "      'gallery-packed-enabled' : true,"
		, "    }"
		, "  </script>"
		));
	}
	@Test   public void Missing() {
		fxt.Init_files_missing_y_();
		fxt.Test_html_frag("<gallery>File:A.png|b</gallery>", "<div class=\"thumb\" style=\"height:150px;\">A.png</div>");
	}
	@Test   public void Multiple() {	// PURPOSE.bug: multiple galleries should not use same gallery super; DATE:2014-04-13
		fxt.Test_html_frag("<gallery>File:A.png|a</gallery><gallery widths=180px>File:B.png|b</gallery>"
		, "src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/120px.png\" width=\"120\" height=\"120\" />"	// should not be 180px from gallery #2
		);
	}
	@Test   public void Link_is_empty() {	// PURPOSE: "link=" causes null pointer exception; DATE:2014-06-15
		fxt.Test_html_frag("<gallery>File:A.png|link=</gallery>", String_.Concat_lines_nl_skip_last
		(  "<div style=\"margin:15px auto;\">"
		, "          <img id=\"xoimg_0\" alt=\"A.png\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/120px.png\" width=\"120\" height=\"120\" />"
		));
	}
	@Test   public void Dangling_autcloses() {	// PURPOSE: dangling gallery should auto-close, not escape; PAGE:en.w:Wikipedia:Featured_pictures_thumbs_43 DATE:2014-08-23
		fxt.Test_html_frag("<gallery>File:A.png|b", "&lt;gallery&gt;File:A.png|b");
	}
	@Test   public void Nested() {	// PURPOSE: handle gallery nested inside ref; PAGE: es.w:Arquitectura_medieval DATE:2015-07-10
		fxt.Test_html_frag(String_.Concat_lines_nl_skip_last
		( "<gallery>"
		, "File:A.jpg|A1"
		, "File:B.jpg|B1 <ref>B2 <gallery>File:B11.jpg|B123./gallery></ref>"	// NOTE: nested gallery
		, "File:C.jpg|C1"
		, "</gallery>"
		), "C1"	// make sure that image after nested gallery appears
		);
	}
	@Test   public void Alt__quotes() {	// PURPOSE: file name with quotes will cause broken alt; PAGE:en.w:en.w:Alexandria,_Romania; DATE:2015-12-27
		fxt.Test_html_frag("<gallery>File:A\"b.png</gallery>", "alt=\"A&quot;b.png\"");	// NOTE: not 'alt="A"b.png"'
	}
	@Test   public void Invalid() {	// PURPOSE: ignore invalid file names; DATE:2016-01-12
		fxt.Test_html_str("<gallery>File:#A.png|a</gallery>"
		, String_.Concat_lines_nl_skip_last
		( "<ul id=\"xowa_gallery_ul_0\" class=\"gallery mw-gallery-traditional\">"
		, "</ul>"
		));
	}
	@Test   public void Hdump__div_1_w() {// PURPOSE: handle hdump and div_1_width == 115 instead of 15; PAGE:en.w:National_Gallery_of_Art; DATE:2016-06-19
		fxt.Fxt().Hctx_(gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx.Hdump);
		fxt.Fxt().Wiki().File__fsdb_mode().Tid__v2__bld__y_();	// NOTE: must set to v2 mode; dflt will call old v1 img code which "guesses" at html_h;
		fxt.Test_html_frag("<gallery widths=200px heights=200px perrow=5>File:A.png|a</gallery>", "<div style=\"margin:15px auto;\">");
	}
}
class Gallery_mgr_base_fxt {
	public void Reset() {
		fxt.Wiki().Xtn_mgr().Init_by_wiki(fxt.Wiki());
		Gallery_mgr_wtr.File_found_mode = Bool_.Y_byte;
	}
	public Xop_fxt Fxt() {return fxt;} private final    Xop_fxt fxt = new Xop_fxt();
	public void Init_files_missing_y_() {
		Gallery_mgr_wtr.File_found_mode = Bool_.N_byte;
	}
	public void Test_html_str(String raw, String expd)						{fxt.Test_html_full_str(raw, expd);}
	public void Test_html_frag(String raw, String... expd_frags)		{fxt.Test_html_full_frag(raw, expd_frags);}	// TODO_OLD: change to wiki_str; currently uids do not get reset if wiki
	public void Test_html_frag_n(String raw, String... expd_frags)	{fxt.Test_html_full_frag_n(raw, expd_frags);}
	public void Test_html_modules_js(String expd)							{
		fxt.Page().Html_data().Head_mgr().Itm__globals().Enabled_n_();
		fxt.Test_html_modules_js(expd);
	}
}
