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
public class Xoh_file_page implements GfoInvkAble {
	public Xoh_file_page() {
		html_main.Fmt_(String_.Concat_lines_nl_skip_last
		( "<ul id=\"filetoc\">"
		, "  <li>"
		, "    <a href=\"#file\">"
		, "      File"
		, "    </a>"
		, "  </li>"
		, "  <li>"
		, "    <a href=\"#filehistory\">"
		, "      File history"
		, "    </a>"
		, "  </li>"
		, "  <li>"
		, "    <a href=\"#filelinks\">"
		, "      File usage on Commons"
		, "    </a>"
		, "  </li>"
		, "  <li>"
		, "    <a href=\"#globalusage\">"
		, "      File usage on other wikis"
		, "    </a>"
		, "  </li>"
		, "</ul>"
		, "~{media}"
		));
		html_main_img.Fmt_(String_.Concat_lines_nl_skip_last
		( "<div class=\"fullImageLink\" id=\"file\">"
		, "  <a href=\"~{orig_href}\" xowa_title=\"~{thumb_name}\">"
		, "    <img id=\"xowa_file_img_~{elem_id}\" alt=\"~{thumb_ttl}\" src=\"~{thumb_href}\" width=\"~{thumb_width}\" height=\"~{thumb_height}\" />"
		, "  </a>"
		, "  <div class=\"mw-filepage-resolutioninfo\">Size of this preview: "
		, "    <a href=\"~{thumb_href}\" class=\"mw-thumbnail-link\">"
		, "      ~{thumb_width} × ~{thumb_height} pixels"
		, "    </a>"
		, "    ."
		, "    <span class=\"mw-filepage-other-resolutions\">"
		, "      Other resolutions:"
		, "~{section_alts}"
		, "    </span>"
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
		));
		html_main_aud.Fmt_(String_.Concat_lines_nl_skip_last
		( "<div class=\"fullImageLink\" id=\"file\">"
		, "  <div>" 
		, "    <a href=\"~{lnki_url}\" xowa_title=\"~{lnki_title}\" class=\"xowa_anchor_button\" style=\"width:~{play_width}px;max-width:~{play_max_width}px;\">"
		, "      <img src=\"~{play_icon}\" width=\"22\" height=\"22\" alt=\"Play sound\" />" 
		, "    </a>" 
		, "  </div>"
		, "</div>"
		, ""
		));
		html_main_vid.Fmt_(String_.Concat_lines_nl_skip_last
		( "<div class=\"fullImageLink\" id=\"file\">"
		, "  <div>" 
		, "    <a href=\"~{lnki_href}\" class=\"~{lnki_class}\" title=\"~{lnki_title}\">"
		, "      <img id=\"xowa_file_img_~{elem_id}\" src=\"~{lnki_src}\" width=\"~{lnki_width}\" height=\"~{lnki_height}\" alt=\"~{lnki_alt}\" />"
		, "    </a>"
		, "  </div>"
		, "  <div>" 
		, "    <a href=\"~{lnki_url}\" xowa_title=\"~{lnki_title}\" class=\"xowa_anchor_button\" style=\"width:~{play_width}px;max-width:~{play_max_width}px;\">"
		, "      <img src=\"~{play_icon}\" width=\"22\" height=\"22\" alt=\"Play sound\" />" 
		, "    </a>" 
		, "  </div>"
		, "</div>"
		, ""
		));
		html_alts.Fmt_(String_.Concat_lines_nl_skip_last
		( "      <a href=\"~{thumb_href}\" class=\"mw-thumbnail-link\">"
		, "        ~{thumb_width} × ~{thumb_height}"
		, "      </a>"
		, "      ~{thumb_dlm} "
		, ""
		));
	}
	public Int_2_ref	Size_main() {return size_main;} private Int_2_ref size_main = new Int_2_ref(800, 600);
	public Int_2_ref[]	Size_alts() {return size_alts;} private Int_2_ref[] size_alts = new Int_2_ref[] {new Int_2_ref(320, 240), new Int_2_ref(640, 480), new Int_2_ref(800, 600), new Int_2_ref(1024, 768), new Int_2_ref(1280, 1024)};
	public byte[] Html_alt_dlm_dflt()		{return html_alt_dlm_dflt;} private byte[] html_alt_dlm_dflt = Bry_.new_ascii_("|");
	public byte[] Html_alt_dlm_last()		{return html_alt_dlm_last;} private byte[] html_alt_dlm_last = Bry_.new_ascii_(".");
	public Bry_fmtr	Html_main() {return html_main;} private final Bry_fmtr html_main = Bry_fmtr.new_("", "media");
	public Bry_fmtr	Html_main_img() {return html_main_img;} private final Bry_fmtr html_main_img = Bry_fmtr.new_("", "orig_width", "orig_height", "orig_href", "orig_file_size", "orig_mime_type", "elem_id", "thumb_width", "thumb_height", "thumb_href", "thumb_ttl", "thumb_name", "section_alts");
	public Bry_fmtr	Html_main_aud() {return html_main_aud;} private final Bry_fmtr html_main_aud = Bry_fmtr.new_("", "lnki_url", "lnki_title", "play_width", "play_max_width", "play_icon");
	public Bry_fmtr	Html_main_vid() {return html_main_vid;} private final Bry_fmtr html_main_vid = Bry_fmtr.new_("", "elem_id", "lnki_href", "lnki_class", "lnki_title", "lnki_src", "lnki_width", "lnki_height", "lnki_alt", "lnki_url", "play_width", "play_max_width", "play_icon");
	public Bry_fmtr	Html_alts() {return html_alts;} private final Bry_fmtr html_alts = Bry_fmtr.new_("", "thumb_width", "thumb_height", "thumb_href", "thumb_dlm");
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_html_main_))				html_main.Fmt_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_html_main_img_))			html_main_img.Fmt_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_html_main_aud_))			html_main_aud.Fmt_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_html_main_vid_))			html_main_vid.Fmt_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_html_alts_))				html_alts.Fmt_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_html_alt_dlm_dflt_))		html_alt_dlm_dflt = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_html_alt_dlm_last_))		html_alt_dlm_last = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_size_main_))				size_main = Int_2_ref.parse_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_size_alts_))				size_alts = Int_2_ref.parse_ary_(m.ReadStr("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_html_main_ = "html_main_", Invk_html_main_img_ = "html_main_img_", Invk_html_main_aud_ = "html_main_aud_", Invk_html_main_vid_ = "html_main_vid_", Invk_html_alts_ = "html_alts_"
	, Invk_size_main_ = "size_main_" , Invk_size_alts_ = "size_alts_", Invk_html_alt_dlm_dflt_ = "html_alt_dlm_dflt_", Invk_html_alt_dlm_last_ = "html_alt_dlm_last_";
}

