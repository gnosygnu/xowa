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
import gplx.core.primitives.*;
class Dpl_itm_keys {
	public static byte Parse(byte[] src, int bgn, int end, byte or) {
		Object o = keys.Get_by_mid(src, bgn, end);
		return o == null ? or : ((Byte_obj_val)o).Val();
	}
	public static byte Parse(byte[] bry, byte or) {
		Object o = keys.Get_by_bry(bry);
		return o == null ? or : ((Byte_obj_val)o).Val();
	}
	public static boolean Parse_as_bool(byte[] bry, boolean or) {
		byte key = Dpl_itm_keys.Parse(bry, Key_null);
		switch (key) {
			case Dpl_itm_keys.Key_false:	return false;
			case Dpl_itm_keys.Key_true:		return true;
			case Dpl_itm_keys.Key_null:		return or;
			default:						throw Err_.new_unhandled(String_.new_u8(bry));	// shouldn't happen; should always go to or;
		}
	}
	public static final byte
	  Key_null = Byte_.Max_value_127
	, Key_category = 1
	, Key_notcategory = 2
	, Key_ns = 3
	, Key_count = 4
	, Key_offset = 5
	, Key_imagewidth = 6
	, Key_imageheight = 7
	, Key_imagesperow = 8
	, Key_mode = 9
	, Key_gallery = 10
	, Key_none = 11
	, Key_ordered = 12
	, Key_unordered = 13
	, Key_inline = 14
	, Key_gallerycaption = 15
	, Key_galleryshowfilesize = 16
	, Key_galleryshowfilename = 17
	, Key_order = 18
	, Key_ordermethod = 19
	, Key_lastedit = 20
	, Key_length = 21
	, Key_created = 22
	, Key_sortkey = 23
	, Key_categorysortkey = 24
	, Key_popularity = 25
	, Key_categoryadd = 26
	, Key_redirects = 27
	, Key_include = 28
	, Key_only = 29
	, Key_exclude = 30
	, Key_stablepages = 31
	, Key_qualitypages = 32
	, Key_suppresserrors = 33
	, Key_addfirstcategorydate = 34
	, Key_shownamespace = 35
	, Key_googlehack = 36
	, Key_nofollow = 37
	, Key_descending = 38
	, Key_ascending = 39
	, Key_false = 40
	, Key_true = 41
	;
	private static final Hash_adp_bry keys = Hash_adp_bry.ci_a7()
	.Add_str_byte("category", Dpl_itm_keys.Key_category)
	.Add_str_byte("notcategory", Dpl_itm_keys.Key_notcategory)
	.Add_str_byte("namespace", Dpl_itm_keys.Key_ns)
	.Add_str_byte("count", Dpl_itm_keys.Key_count)
	.Add_str_byte("offset", Dpl_itm_keys.Key_offset)
	.Add_str_byte("imagewidth", Dpl_itm_keys.Key_imagewidth)
	.Add_str_byte("imageheight", Dpl_itm_keys.Key_imageheight)
	.Add_str_byte("imagesperow", Dpl_itm_keys.Key_imagesperow)
	.Add_str_byte("mode", Dpl_itm_keys.Key_mode)
	.Add_str_byte("gallery", Dpl_itm_keys.Key_gallery)
	.Add_str_byte("none", Dpl_itm_keys.Key_none)
	.Add_str_byte("ordered", Dpl_itm_keys.Key_ordered)
	.Add_str_byte("unordered", Dpl_itm_keys.Key_unordered)
	.Add_str_byte("inline", Dpl_itm_keys.Key_inline)
	.Add_str_byte("gallerycaption", Dpl_itm_keys.Key_gallerycaption)
	.Add_str_byte("galleryshowfilesize", Dpl_itm_keys.Key_galleryshowfilesize)
	.Add_str_byte("galleryshowfilename", Dpl_itm_keys.Key_galleryshowfilename)
	.Add_str_byte("order", Dpl_itm_keys.Key_order)
	.Add_str_byte("ordermethod", Dpl_itm_keys.Key_ordermethod)
	.Add_str_byte("lastedit", Dpl_itm_keys.Key_lastedit)
	.Add_str_byte("length", Dpl_itm_keys.Key_length)
	.Add_str_byte("created", Dpl_itm_keys.Key_created)
	.Add_str_byte("sortkey", Dpl_itm_keys.Key_sortkey)
	.Add_str_byte("categorysortkey", Dpl_itm_keys.Key_categorysortkey)
	.Add_str_byte("popularity", Dpl_itm_keys.Key_popularity)
	.Add_str_byte("categoryadd", Dpl_itm_keys.Key_categoryadd)
	.Add_str_byte("redirects", Dpl_itm_keys.Key_redirects)
	.Add_str_byte("include", Dpl_itm_keys.Key_include)
	.Add_str_byte("only", Dpl_itm_keys.Key_only)
	.Add_str_byte("exclude", Dpl_itm_keys.Key_exclude)
	.Add_str_byte("stablepages", Dpl_itm_keys.Key_stablepages)
	.Add_str_byte("qualitypages", Dpl_itm_keys.Key_qualitypages)
	.Add_str_byte("suppresserrors", Dpl_itm_keys.Key_suppresserrors)
	.Add_str_byte("addfirstcategorydate", Dpl_itm_keys.Key_addfirstcategorydate)
	.Add_str_byte("shownamespace", Dpl_itm_keys.Key_shownamespace)
	.Add_str_byte("googlehack", Dpl_itm_keys.Key_googlehack)
	.Add_str_byte("nofollow", Dpl_itm_keys.Key_nofollow)
	.Add_str_byte("descending", Dpl_itm_keys.Key_descending)
	.Add_str_byte("ascending", Dpl_itm_keys.Key_ascending)
	.Add_str_byte("false", Dpl_itm_keys.Key_false)
	.Add_str_byte("true", Dpl_itm_keys.Key_true)
	;
}
