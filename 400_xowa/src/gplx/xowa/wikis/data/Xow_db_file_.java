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
	;
	private static final String
	  Key__core = "core", Key__text = "text", Key__cat = "xtn.category", Key__search_core = "xtn.search.core", Key__wbase = "core.wbase"
	, Key__cat_core = "xtn.category.core", Key__cat_link = "xtn.category.link"
	, Key__text_solo = "text.solo", Key__wiki_solo = "wiki.solo"
	, Key__html_solo = "html.solo", Key__html_data = "html"
	, Key__file_solo = "file.solo", Key__file_core = "file.core", Key__file_data = "file.data", Key__file_user = "file.user"
	, Key__search_link = "xtn.search.link", Key__random = "xtn.random", Key__css = "xtn.css"
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
			default:				throw Err_.new_unhandled(v);
		}
	}
}
