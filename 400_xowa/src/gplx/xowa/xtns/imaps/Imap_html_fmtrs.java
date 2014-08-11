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
package gplx.xowa.xtns.imaps; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
class Imap_html_fmtrs {
	public static final Bry_fmtr
	  All = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<div id='imap_div_~{imap_id}' class=\"noresize\"~{desc_style}>~{map}~{img}"
	, "    </div>"
	), "imap_id", "desc_style", "map", "img"
	)
	, Map = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "      <map name=\"imageMap_1_~{imap_id}\">~{shapes}"
	, "      </map>"
	), "imap_id", "shapes"
	)
	, Area = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "\n        <area href=\"~{href}\" shape=\"~{shape}\" coords=\"~{coords}\" alt=\"~{title}\" title=\"~{title}\"/>"
	), "href", "shape", "coords", "title")
	, Img_anchor_none = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "      <img id=\"xowa_file_img_~{img_elem_id}\" alt=\"~{img_alt}\" src=\"~{img_src}\" width=\"~{img_w}\" height=\"~{img_h}\"~{img_cls} usemap=\"#imageMap_1_~{imap_id}\"/>"
	), "imap_id", "img_elem_id", "img_alt", "img_src", "img_w", "img_h", "img_cls", "anchor_href", "anchor_title"
	)
	, Img_anchor_lnki = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "      <a href=\"~{anchor_href}\" title=\"~{anchor_title}\">"
	, "        <img id=\"xowa_file_img_~{img_elem_id}\" alt=\"~{img_alt}\" src=\"~{img_src}\" width=\"~{img_w}\" height=\"~{img_h}\"~{img_cls} usemap=\"#imageMap_1_~{imap_id}\"/>"
	, "      </a>"
	), "imap_id", "img_elem_id", "img_alt", "img_src", "img_w", "img_h", "img_cls", "anchor_href", "anchor_title"
	)
	, Img_anchor_lnke = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "      <a href=\"~{anchor_href}\" title=~{anchor_title} class=\"plainlinks\" rel=\"nofollow\">"
	, "        <img id=\"xowa_file_img_~{img_elem_id}\" alt=\"~{img_alt}\" src=\"~{img_src}\" width=\"~{img_w}\" height=\"~{img_h}\"~{img_cls} usemap=\"#imageMap_1_~{imap_id}\"/>"
	, "      </a>"
	), "imap_id", "img_elem_id", "img_alt", "img_src", "img_w", "img_h", "img_cls", "anchor_href", "anchor_title"
	)
	, Desc_style = Bry_fmtr.new_(" style=\"height:~{div_h}px; width: ~{div_w}px;\"", "div_w", "div_h")
	, Desc_main = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "      <div style=\"margin-left:~{margin_left}px; margin-top:~{margin_top}px; text-align:left;\">"
	, "        <a href=\"~{img_href}\" title=\"~{msg_desc}\">"
	, "          <img alt=\"~{msg_desc}\" src=\"~{icon_url}\" style=\"border: none;\" />"
	, "        </a>"
	, "      </div>"
	), "margin_left", "margin_top", "img_href", "msg_desc", "icon_url"
	)
	;
}
