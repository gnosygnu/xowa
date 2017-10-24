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
package gplx.xowa.htmls.ns_files; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*; import gplx.xowa.files.*;
public class Xoh_ns_file_page_mgr_tst {
	private final    Xoh_ns_file_page_mgr_fxt fxt = new Xoh_ns_file_page_mgr_fxt();
	@Before public void init() {fxt.Reset();}
	@Test  public void Image() {
		fxt.Ttl_str_("Test.png").Html_src_("mem/file/cur.png").Html_orig_src_("mem/file/orig.png").Html_w_(300).Html_h_(200).Html_file_size_(100)
		.tst(String_.Concat_lines_nl_skip_last
		( Xoh_ns_file_page_mgr_fxt.Hdr
		, "<div class=\"fullImageLink\" id=\"file\">"
		, "  <a href=\"file:///mem/file/orig.png\" xowa_title=\"Test.png\">"
		, "    <img id=\"xoimg_0\" alt=\"Test.png\" src=\"file:///mem/file/cur.png\" width=\"300\" height=\"200\" />"
		, "  </a>"
		, "  <div class=\"mw-filepage-resolutioninfo\">Size of this preview: "
		, "    <a href=\"file:///mem/file/cur.png\" class=\"mw-thumbnail-link\" xowa_title=\"Test.png\">"
		, "      300 × 200 pixels"
		, "    </a>"
		, "    ."
//			, "    <span class=\"mw-filepage-other-resolutions\">"
//			, "      Other resolutions:"
//			, ""
//			, "    </span>"
		, "  </div>"
		, "</div>"
		, "<div class=\"fullMedia\">"
		, "  <a href=\"file:///mem/file/orig.png\" class=\"internal\" title=\"Test.png\" xowa_title=\"Test.png\">"
		, "    Full resolution"
		, "  </a>"
		, "  &#8206;"
		, "  <span class=\"fileInfo\">"
		, "    (0 × 0 pixels, file size: 100, MIME type: image/png)"
		, "  </span>"
		, "</div>"
		, ""		
		));
	}
	@Test  public void Audio() {
		fxt.Ttl_str_("Test.oga").Html_src_("mem/file/cur.oga").Html_orig_src_("mem/file/orig.oga").Html_w_(300).Html_h_(200).Html_file_size_(100)
		.tst(String_.Concat_lines_nl_skip_last
		( Xoh_ns_file_page_mgr_fxt.Hdr
		, "<div class=\"fullImageLink\" id=\"file\">"
		, "  <div><a href=\"file:///mem/file/orig.oga\" xowa_title=\"Test.oga\" class=\"xowa_media_play\" style=\"width:300px;max-width:300px;\" alt=\"Play sound\"></a></div>"
		, "</div>"
		, ""		
		));
	}
	@Test  public void Video() {
		fxt.Ttl_str_("Test.ogv").Html_src_("mem/file/thumb.png").Html_orig_src_("mem/file/orig.ogv").Html_w_(300).Html_h_(200).Html_file_size_(100)
		.tst(String_.Concat_lines_nl_skip_last
		(	Xoh_ns_file_page_mgr_fxt.Hdr
		,	"<div class=\"fullImageLink\" id=\"file\">"
		,	"  <div>"
		,	"    <a href=\"file:///mem/file/thumb.png\" class=\"image\" title=\"Test.ogv\">"
		,	"      <img id=\"xoimg_0\" src=\"file:///mem/file/thumb.png\" width=\"300\" height=\"200\" alt=\"\" />"
		,	"    </a>"
		,	"  </div>"
		,	"  <div><a href=\"file:///mem/file/orig.ogv\" xowa_title=\"Test.ogv\" class=\"xowa_media_play\" style=\"width:300px;max-width:300px;\" alt=\"Play sound\"></a></div>"
		,	"</div>"
		,	""		
		));
	}
}
class Xoh_ns_file_page_mgr_fxt {
	private final    Xoh_ns_file_page_mgr wkr = new Xoh_ns_file_page_mgr();
	private Xoae_app app; private Xowe_wiki wiki; private Xoh_file_page_wtr opt;
	private final    Xof_file_itm file = new Xof_fsdb_itm(); private final    Bry_bfr bfr = Bry_bfr_.New();
	public Xoh_ns_file_page_mgr_fxt Ttl_str_(String v) {this.ttl_str = v; return this;} private String ttl_str;
	public Xoh_ns_file_page_mgr_fxt Html_src_(String v) {this.html_src = v; return this;} private String html_src;
	public Xoh_ns_file_page_mgr_fxt Html_orig_src_(String v) {this.html_orig_src = v; return this;} private String html_orig_src;
	public Xoh_ns_file_page_mgr_fxt Html_w_(int v) {this.html_w = v; return this;} private int html_w;
	public Xoh_ns_file_page_mgr_fxt Html_h_(int v) {this.html_h = v; return this;} private int html_h;
	public Xoh_ns_file_page_mgr_fxt Html_file_size_(int v) {this.html_file_size = v; return this;} private int html_file_size;
	public void Reset() {
		if (app != null) return;
		app = Xoa_app_fxt.Make__app__edit();
		wiki = Xoa_app_fxt.Make__wiki__edit(app);
		opt = new Xoh_file_page_wtr();
	}
	public void tst(String expd) {
		byte[] ttl_bry = Bry_.new_u8(ttl_str);
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry);
		file.Init_at_gallery_end(html_w, html_h, Io_url_.mem_fil_(html_src), Io_url_.mem_fil_(html_orig_src));
//			file.Orig_ttl_and_redirect_(ttl_bry, Bry_.Empty);
		file.Init_at_orig(Byte_.Zero, wiki.Domain_bry(), ttl_bry, Xof_ext_.new_by_ttl_(ttl_bry), 0, 0, Bry_.Empty);
		file.Init_at_hdoc(0, Xof_html_elem.Tid_img);
		wkr.Bld_html(wiki, bfr, file, ttl, opt, Bry_.To_a7_bry(html_file_size, 0));	// TEST: must pass in elem_val b/c test only uses 2nd Bld_html while app uses 1st
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
	public static final    String Hdr = String_.Concat_lines_nl_skip_last("<br/>");
//		(	"<ul id=\"filetoc\">"
//		,	"  <li>"
//		,	"    <a href=\"#file\">"
//		,	"      File"
//		,	"    </a>"
//		,	"  </li>"
//		,	"  <li>"
//		,	"    <a href=\"#filehistory\">"
//		,	"      File history"
//		,	"    </a>"
//		,	"  </li>"
//		,	"  <li>"
//		,	"    <a href=\"#filelinks\">"
//		,	"      File usage on Commons"
//		,	"    </a>"
//		,	"  </li>"
//		,	"  <li>"
//		,	"    <a href=\"#globalusage\">"
//		,	"      File usage on other wikis"
//		,	"    </a>"
//		,	"  </li>"
//		,	"</ul>"
//		);
}

