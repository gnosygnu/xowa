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
package gplx.xowa.bldrs.wms.dumps;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.types.basics.wrappers.IntVal;
import gplx.types.errs.ErrUtl;
public class Xowm_dump_type_ {
	public static int parse_by_file(byte[] src) {return parse_by_file(src, 0, src.length);}
	public static int parse_by_file(byte[] src, int bgn, int end) {	// given "pages-articles.xml", get type from "pages-articles"; ignore ".xml" or ".bz2"
		Object o = regy.MatchBgn(src, bgn, end); if (o == null) throw ErrUtl.NewArgs("unknown dump file type", "src", src);
		return ((IntVal)o).Val();
	}
	public static final int Int__pages_articles = 1, Int__pages_meta_current = 2, Int__categorylinks = 3, Int__page_props = 4, Int__image = 5, Int__pagelinks = 6;
	public static final String Str__pages_articles = "pages-articles", Str__pages_meta_current = "pages-meta-current"
	, Str__categorylinks = "categorylinks", Str__page_props = "page_props", Str__image = "image", Str__pagelinks = "pagelinks"
	;
	private static final Btrie_slim_mgr regy = Btrie_slim_mgr.ci_a7()
	.Add_str_int(Str__pages_articles		, Int__pages_articles)
	.Add_str_int(Str__pages_meta_current	, Int__pages_meta_current)
	.Add_str_int(Str__categorylinks			, Int__categorylinks)
	.Add_str_int(Str__page_props			, Int__page_props)
	.Add_str_int(Str__image					, Int__image)
	.Add_str_int(Str__pagelinks				, Int__pagelinks)
	;
	public static String To_str(byte v) {
		switch (v) {
			case Int__pages_articles		: return Str__pages_articles;
			case Int__pages_meta_current	: return Str__pages_meta_current;
			case Int__categorylinks			: return Str__categorylinks;
			case Int__page_props			: return Str__page_props;
			case Int__image					: return Str__image;
			case Int__pagelinks				: return Str__pagelinks;
			default							: throw ErrUtl.NewUnhandled(v);
		}
	}
}
