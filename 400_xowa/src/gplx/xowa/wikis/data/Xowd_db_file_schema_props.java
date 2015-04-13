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
import gplx.dbs.cfgs.*;
public class Xowd_db_file_schema_props {
	Xowd_db_file_schema_props(boolean search__word__page_count_exists) {
		this.search__word__page_count_exists = search__word__page_count_exists;
	}
	public boolean Search__word__page_count_exists() {return search__word__page_count_exists;} private final boolean search__word__page_count_exists;
	public static Xowd_db_file_schema_props make_() {return new Xowd_db_file_schema_props(Bool_.Y);}
	public static Xowd_db_file_schema_props load_(Db_cfg_tbl tbl) {
		boolean search__word__page_count_exists = tbl.Select_yn_or(Grp, Key__col_search_word_page_count, Bool_.N);
		return new Xowd_db_file_schema_props(search__word__page_count_exists);
	}
	public static final String Grp = Xow_cfg_consts.Grp__wiki_schema;
	public static final String
	  Key__col_page_html_text_id		= "col.page.page_html_text_id"
	, Key__tbl_css_core					= "tbl.css_core"						// VERSION:2.4.1
	, Key__col_search_word_page_count	= "col.search_word.word_page_count"		// VERSION:2.4.2
	;
}
