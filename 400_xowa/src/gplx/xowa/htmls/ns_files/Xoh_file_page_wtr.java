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
import gplx.core.primitives.*; import gplx.core.brys.fmtrs.*;
public class Xoh_file_page_wtr {
	public int		Main_img_w() {return 800;}
	public int		Main_img_h() {return 600;}
	public Int_2_ref[]	Size_alts()		{return size_alts;} private Int_2_ref[] size_alts = new Int_2_ref[] {new Int_2_ref(320, 240), new Int_2_ref(640, 480), new Int_2_ref(800, 600), new Int_2_ref(1024, 768), new Int_2_ref(1280, 1024)};
	public byte[]	Html_alt_dlm_dflt()	{return html_alt_dlm_dflt;} private byte[] html_alt_dlm_dflt = Bry_.new_a7("|");
	public byte[]	Html_alt_dlm_last()	{return html_alt_dlm_last;} private byte[] html_alt_dlm_last = Bry_.new_a7(".");
	public Bry_fmtr	Html_main() {return html_main;} private final    Bry_fmtr html_main = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "~{commons_notice}<br/>"
//		, "<ul id=\"filetoc\">"
//		, "  <li>"
//		, "    <a href=\"#file\">"
//		, "      File"
//		, "    </a>"
//		, "  </li>"
//		, "  <li>"
//		, "    <a href=\"#filehistory\">"
//		, "      File history"
//		, "    </a>"
//		, "  </li>"
//		, "  <li>"
//		, "    <a href=\"#filelinks\">"
//		, "      File usage on Commons"
//		, "    </a>"
//		, "  </li>"
//		, "  <li>"
//		, "    <a href=\"#globalusage\">"
//		, "      File usage on other wikis"
//		, "    </a>"
//		, "  </li>"
//		, "</ul>"
	, "~{media}"
	)
	, "media", "commons_notice");
	public Bry_fmtr	Html_main_img() {return html_main_img;} private final    Bry_fmtr html_main_img = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<div class=\"fullImageLink\" id=\"file\">"
	, "  <a href=\"~{orig_href}\" xowa_title=\"~{thumb_name}\">"
	, "    <img id=\"xoimg_~{elem_id}\" alt=\"~{thumb_alt}\" src=\"~{thumb_href}\" width=\"~{thumb_width}\" height=\"~{thumb_height}\" />"
	, "  </a>"
	, "  <div class=\"mw-filepage-resolutioninfo\">Size of this preview: "
	, "    <a href=\"~{thumb_href}\" class=\"mw-thumbnail-link\" xowa_title=\"~{thumb_name}\">"
	, "      ~{thumb_width} × ~{thumb_height} pixels"
	, "    </a>"
	, "    ."
//		, "    <span class=\"mw-filepage-other-resolutions\">"
//		, "      Other resolutions:"
//		, "~{section_alts}"
//		, "    </span>"
	, "  </div>"
	, "</div>"
	, "<div class=\"fullMedia\">"
	, "  <a href=\"~{orig_href}\" class=\"internal\" title=\"~{thumb_name}\" xowa_title=\"~{thumb_name}\">"
	, "    Full resolution"
	, "  </a>"
	, "  &#8206;"
	, "  <span class=\"fileInfo\">"
	, "    (~{orig_width} × ~{orig_height} pixels, file size: ~{orig_file_size}, MIME type: ~{orig_mime_type})"
	, "  </span>"
	, "</div>"
	, ""
	), "orig_width", "orig_height", "orig_href", "orig_file_size", "orig_mime_type", "elem_id", "thumb_width", "thumb_height", "thumb_href", "thumb_alt", "thumb_name", "section_alts");
	public Bry_fmtr	Html_main_aud() {return html_main_aud;} private final    Bry_fmtr html_main_aud = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<div class=\"fullImageLink\" id=\"file\">"
	, "  <div><a href=\"~{lnki_url}\" xowa_title=\"~{xowa_title}\" class=\"xowa_media_play\" style=\"width:~{play_width}px;max-width:~{play_max_width}px;\" alt=\"Play sound\"></a></div>"
	, "</div>"
	, ""
	), "lnki_url", "xowa_title", "play_width", "play_max_width");
	public Bry_fmtr	Html_main_vid() {return html_main_vid;} private final    Bry_fmtr html_main_vid = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<div class=\"fullImageLink\" id=\"file\">"
	, "  <div>" 
	, "    <a href=\"~{lnki_href}\" class=\"~{lnki_class}\" title=\"~{xowa_title}\">"
	, "      <img id=\"xoimg_~{elem_id}\" src=\"~{lnki_src}\" width=\"~{lnki_width}\" height=\"~{lnki_height}\" alt=\"~{lnki_alt}\" />"
	, "    </a>"
	, "  </div>"
	, "  <div><a href=\"~{lnki_url}\" xowa_title=\"~{xowa_title}\" class=\"xowa_media_play\" style=\"width:~{play_width}px;max-width:~{play_max_width}px;\" alt=\"Play sound\"></a></div>"
	, "</div>"
	, ""
	), "elem_id", "lnki_href", "lnki_class", "xowa_title", "lnki_src", "lnki_width", "lnki_height", "lnki_alt", "lnki_url", "play_width", "play_max_width");
	public Bry_fmtr	Html_alts() {return html_alts;} private final    Bry_fmtr html_alts = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "      <a href=\"~{thumb_href}\" class=\"mw-thumbnail-link\" xowa_title=\"~{xowa_title}\">"
	, "        ~{thumb_width} × ~{thumb_height}"
	, "      </a>"
	, "      ~{thumb_dlm} "
	, ""
	), "thumb_width", "thumb_height", "thumb_href", "thumb_dlm", "xowa_title");
}

