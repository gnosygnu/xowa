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
public class Xowd_db_file_ {
	public static final int Id_core = 0;
	public static final byte
	  Tid_core = 1, Tid_text = 2, Tid_cat = 3, Tid_search_core = 4, Tid_wbase = 5	// SERIALIZED:v1
	, Tid_cat_core	=  6, Tid_cat_link  =  7										// SERIALIZED:v2
	, Tid_wiki_solo	=  8, Tid_text_solo =  9
	, Tid_html_solo = 10, Tid_html_data = 11
	, Tid_file_solo = 12, Tid_file_core = 13, Tid_file_data = 14, Tid_file_user = 15
	;
	private static final String
	  Key_core = "core", Key_text = "text", Key_cat = "xtn.category", Key_search_core = "xtn.search.core", Key_wbase = "core.wbase"
	, Key_cat_core = "xtn.category.core", Key_cat_link = "xtn.category.link"
	, Key_text_solo = "text.solo", Key_wiki_solo = "wiki.solo"
	, Key_html_solo = "html.solo", Key_html_data = "html"
	, Key_file_solo = "file.solo", Key_file_core = "file.core", Key_file_data = "file.data", Key_file_user = "file.user"
	;
	public static String To_key(byte v) {
		switch (v) {
			case Tid_core:			return Key_core;
			case Tid_text:			return Key_text;
			case Tid_cat:			return Key_cat;
			case Tid_search_core:	return Key_search_core;
			case Tid_wbase:			return Key_wbase;
			case Tid_cat_core:		return Key_cat_core;
			case Tid_cat_link:		return Key_cat_link;
			case Tid_wiki_solo:		return Key_wiki_solo;
			case Tid_text_solo:		return Key_text_solo;
			case Tid_html_solo:		return Key_html_solo;
			case Tid_html_data:		return Key_html_data;
			case Tid_file_solo:		return Key_file_solo;
			case Tid_file_core:		return Key_file_core;
			case Tid_file_data:		return Key_file_data;
			case Tid_file_user:		return Key_file_user;
			default:				throw Err_.new_unhandled(v);
		}
	}
}
