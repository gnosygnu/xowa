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
package gplx.xowa.wmfs.dumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
import gplx.core.btries.*; import gplx.core.primitives.*;
public class Xowm_dump_type_ {
	public static int parse_by_file(byte[] src) {return parse_by_file(src, 0, src.length);}
	public static int parse_by_file(byte[] src, int bgn, int end) {	// given "pages-articles.xml", get type from "pages-articles"; ignore ".xml" or ".bz2"
		Object o = regy.Match_bgn(src, bgn, end); if (o == null) throw Err_.new_("wm.dump", "unknown dump file type", "src", src);
		return ((Int_obj_val)o).Val();
	}
	public static final int Int__pages_articles = 1, Int__pages_meta_current = 2, Int__categorylinks = 3, Int__page_props = 4, Int__image = 5;
	public static final String Str__pages_articles = "pages-articles", Str__pages_meta_current = "pages-meta-current"
	, Str__categorylinks = "categorylinks", Str__page_props = "page_props", Str__image = "image"
	;
	private static final Btrie_slim_mgr regy = Btrie_slim_mgr.ci_a7()
	.Add_str_int(Str__pages_articles		, Int__pages_articles)
	.Add_str_int(Str__pages_meta_current	, Int__pages_meta_current)
	.Add_str_int(Str__categorylinks			, Int__categorylinks)
	.Add_str_int(Str__page_props			, Int__page_props)
	.Add_str_int(Str__image					, Int__image)
	;
	public static String To_str(byte v) {
		switch (v) {
			case Int__pages_articles		: return Str__pages_articles;
			case Int__pages_meta_current	: return Str__pages_meta_current;
			case Int__categorylinks			: return Str__categorylinks;
			case Int__page_props			: return Str__page_props;
			case Int__image					: return Str__image;
			default							: throw Err_.new_unhandled(v);
		}
	}
}
