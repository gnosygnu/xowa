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
import gplx.dbs.cfgs.*; import gplx.xowa.bldrs.infos.*;
public class Xow_db_file_schema_props {
	Xow_db_file_schema_props(boolean search__word__page_count_exists, boolean wbase__qid__src_ttl_has_spaces) {
		this.search__word__page_count_exists = search__word__page_count_exists;
		this.wbase__qid__src_ttl_has_spaces = wbase__qid__src_ttl_has_spaces;
	}
	public boolean Search__word__page_count_exists() {return search__word__page_count_exists;} private final    boolean search__word__page_count_exists;
	public boolean Wbase__qid__src_ttl_has_spaces() {return wbase__qid__src_ttl_has_spaces;} private final    boolean wbase__qid__src_ttl_has_spaces;
	public static Xow_db_file_schema_props make_() {return new Xow_db_file_schema_props(Bool_.Y, Bool_.N);}
	public static Xow_db_file_schema_props load_(Db_cfg_tbl tbl, int tid, String version) {
		boolean search__word__page_count_exists = tbl.Select_yn_or(Grp, Key__col_search_word_page_count, Bool_.N);
		boolean wbase__qid__src_ttl_has_spaces = String_.In(version, "2.4.2.1", "2.4.3.1", "2.4.3.2");
		return new Xow_db_file_schema_props(search__word__page_count_exists, wbase__qid__src_ttl_has_spaces);
	}
	public static final String Grp = Xowd_cfg_key_.Grp__wiki_schema;
	public static final String
	  Key__tbl_css_core					= "tbl.css_core"						// VERSION:2.4.1
	, Key__col_search_word_page_count	= "col.search_word.word_page_count"		// VERSION:2.4.2
	;
}
