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
package gplx.xowa.xtns.imaps.htmls;
import gplx.types.custom.brys.fmts.fmtrs.*;
import gplx.types.basics.utls.StringUtl;
public class Imap_html_fmtrs {
	public static final BryFmtr
	  All = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( "<div id=\"imap_div_~{imap_id}\" class=\"noresize\"~{desc_style}>~{map}~{img}"
	, "    </div>"
	), "imap_id", "desc_style", "map", "img"
	)
	, Map = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "      <map name=\"imageMap_1_~{imap_id}\">~{shapes}"
	, "      </map>"
	), "imap_id", "shapes"
	)
	, Area = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( "\n        <area href=\"~{href}\" shape=\"~{shape}\" coords=\"~{coords}\" alt=\"~{title}\" title=\"~{title}\"/>"
	), "href", "shape", "coords", "title")
	, Img_anchor_none = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "      <img id=\"xoimg_~{img_elem_id}\" alt=\"~{img_alt}\"~{img_core}~{img_cls} usemap=\"#imageMap_1_~{imap_id}\"/>"
	), "imap_id", "img_elem_id", "img_alt", "img_core", "img_cls", "anchor_href", "anchor_title"
	)
	, Img_anchor_lnki = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "      <a href=\"~{anchor_href}\" title=\"~{anchor_title}\">"
	, "        <img id=\"xoimg_~{img_elem_id}\" alt=\"~{img_alt}\"~{img_core}~{img_cls} usemap=\"#imageMap_1_~{imap_id}\"/>"
	, "      </a>"
	), "imap_id", "img_elem_id", "img_alt", "img_core", "img_cls", "anchor_href", "anchor_title"
	)
	, Img_anchor_lnke = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "      <a href=\"~{anchor_href}\" title=~{anchor_title} class=\"plainlinks\" rel=\"nofollow\">"
	, "        <img id=\"xoimg_~{img_elem_id}\" alt=\"~{img_alt}\"~{img_core}~{img_cls} usemap=\"#imageMap_1_~{imap_id}\"/>"
	, "      </a>"
	), "imap_id", "img_elem_id", "img_alt", "img_core", "img_cls", "anchor_href", "anchor_title"
	)
	, Desc_style = BryFmtr.New(" style=\"height:~{div_h}px; width: ~{div_w}px;\"", "div_w", "div_h")
	, Desc_main = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "      <div style=\"margin-left:~{margin_left}px; margin-top:~{margin_top}px; text-align:left;\">"
	, "        <a href=\"~{img_href}\" title=\"~{msg_desc}\">"
	, "          <img alt=\"~{msg_desc}\" src=\"~{icon_url}\" style=\"border: none;\" ~{data_xowa_hdump}/>"
	, "        </a>"
	, "      </div>"
	), "margin_left", "margin_top", "img_href", "msg_desc", "icon_url", "data_xowa_hdump"
	);
}
