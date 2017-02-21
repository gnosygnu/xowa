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
package gplx.xowa.xtns.dynamicPageList; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
class Dpl_html_data {
	public byte Tid() {return tid;} private byte tid;
	public byte[] Grp_bgn() {return grp_bgn;} private byte[] grp_bgn;
	public byte[] Grp_end() {return grp_end;} private byte[] grp_end;
	public byte[] Itm_bgn() {return itm_bgn;} private byte[] itm_bgn;
	public byte[] Itm_end() {return itm_end;} private byte[] itm_end;

	public static final byte Tid_null = 0, Tid_none = 1, Tid_list_ol = 2, Tid_list_ul = 3, Tid_gallery = 4, Tid_inline = 5;
	public static final byte[]
	  Ul_bgn = Bry_.new_a7("<ul>"), Ul_end = Bry_.new_a7("</ul>")
	, Ol_bgn = Bry_.new_a7("<ol>"), Ol_end = Bry_.new_a7("</ol>")
	, Li_bgn = Bry_.new_a7("<li>"), Li_end = Bry_.new_a7("</li>")
	, Br = Bry_.new_a7("<br />")
	;
	private static final Dpl_html_data
	  Itm_gallery	= new_(Tid_gallery, null, null, null, null)
	, Itm_inline	= new_(Tid_inline, null, null, null, null)
	, Itm_none		= new_(Tid_none, null, null, null, Br)
	, Itm_ordered	= new_(Tid_list_ol, Ol_bgn, Ol_end, Li_bgn, Li_end)
	, Itm_unordered = new_(Tid_list_ul, Ul_bgn, Ul_end, Li_bgn, Li_end)
	;
	private static Dpl_html_data new_(byte tid, byte[] grp_bgn, byte[] grp_end, byte[] itm_bgn, byte[] itm_end) {
		Dpl_html_data rv = new Dpl_html_data();
		rv.tid = tid; rv.grp_bgn = grp_bgn; rv.grp_end = grp_end; rv.itm_bgn = itm_bgn; rv.itm_end = itm_end;
		return rv;
	}
	public static Dpl_html_data new_(byte key) {
		switch (key) {
		case Dpl_itm_keys.Key_gallery: 			return Itm_gallery;
		case Dpl_itm_keys.Key_inline: 			return Itm_inline;
		case Dpl_itm_keys.Key_none: 			return Itm_none;
		case Dpl_itm_keys.Key_ordered: 			return Itm_ordered;
		case Dpl_itm_keys.Key_unordered: 		return Itm_unordered;
		default:								throw Err_.new_unhandled(key);
		}
	}
}
