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
package gplx.xowa.wikis.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xow_db_file_ {
	public static final int Uid__core = 0;
	public static final byte
	  Tid__core = 1, Tid__text = 2, Tid__cat = 3, Tid__search_core = 4, Tid__wbase = 5	// SERIALIZED:v1
	, Tid__cat_core		=  6, Tid__cat_link  =  7										// SERIALIZED:v2
	, Tid__wiki_solo	=  8, Tid__text_solo =  9
	, Tid__html_solo	= 10, Tid__html_data = 11
	, Tid__file_solo	= 12, Tid__file_core = 13, Tid__file_data = 14, Tid__file_user = 15
	, Tid__search_link	= 16, Tid__random = 17, Tid__css = 18
	, Tid__html_user	= 19
	;
	private static final String
	  Key__core = "core", Key__text = "text", Key__cat = "xtn.category", Key__search_core = "xtn.search.core", Key__wbase = "core.wbase"
	, Key__cat_core = "xtn.category.core", Key__cat_link = "xtn.category.link"
	, Key__text_solo = "text.solo", Key__wiki_solo = "wiki.solo"
	, Key__html_solo = "html.solo", Key__html_data = "html"
	, Key__file_solo = "file.solo", Key__file_core = "file.core", Key__file_data = "file.data", Key__file_user = "file.user"
	, Key__search_link = "xtn.search.link", Key__random = "xtn.random", Key__css = "xtn.css"
	, Key__html_user = "html.user"
	;
	public static String To_key(byte v) {
		switch (v) {
			case Tid__core:			return Key__core;
			case Tid__text:			return Key__text;
			case Tid__cat:			return Key__cat;
			case Tid__search_core:	return Key__search_core;
			case Tid__wbase:		return Key__wbase;
			case Tid__cat_core:		return Key__cat_core;
			case Tid__cat_link:		return Key__cat_link;
			case Tid__wiki_solo:	return Key__wiki_solo;
			case Tid__text_solo:	return Key__text_solo;
			case Tid__html_solo:	return Key__html_solo;
			case Tid__html_data:	return Key__html_data;
			case Tid__file_solo:	return Key__file_solo;
			case Tid__file_core:	return Key__file_core;
			case Tid__file_data:	return Key__file_data;
			case Tid__file_user:	return Key__file_user;
			case Tid__search_link:	return Key__search_link;
			case Tid__random:		return Key__random;
			case Tid__css:			return Key__css;
			case Tid__html_user:	return Key__html_user;
			default:				throw Err_.new_unhandled(v);
		}
	}
}
