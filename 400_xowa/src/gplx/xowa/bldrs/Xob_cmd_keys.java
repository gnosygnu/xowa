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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
public class Xob_cmd_keys {
	public static final String
	  Key_text_init					= "text.init"				// "import.sql.init"
	, Key_text_page					= "text.page"				// "import.sql.page"
	, Key_text_css					= "text.css"
	, Key_text_search_cmd			= "text.search.cmd"			// "import.sql.search_title.cmd"
	, Key_text_search_wkr			= "text.search"				// "import.sql.search_title.wkr"
	, Key_text_term					= "text.term"				// "import.sql.term"
	, Key_html_redlinks				= "html.redlinks"
	, Key_util_cleanup				= "util.cleanup"			// "core.cleanup"
	, Key_util_download				= "util.download"			// "file.download"
	, Key_util_xml_dump				= "util.xml_dump"
	, Key_util_random				= "util.random"
	, Key_util_delete				= "util.delete"
	, Key_wbase_qid					= "wbase.qid"				// "text.wdata.qid"
	, Key_wbase_pid					= "wbase.pid"				// "text.wdata.pid"
	, Key_wbase_db					= "wbase.db"				// "wiki.wdata_db"
	, Key_site_meta					= "util.site_meta"
	, Key_diff_build				= "diff.build"
	, Key_diff_merge				= "diff.merge"
	, Key_text_delete_page			= "text.delete_page"

	, Key_tdb_text_init				= "tdb.text.init"			// "core.init"
	, Key_tdb_make_page				= "tdb.text.page"			// "core.make_page"
	, Key_tdb_make_id				= "core.make_id"
	, Key_tdb_calc_stats			= "core.calc_stats"
	, Key_tdb_text_wdata_qid		= "tdb.text.wdata.qid"
	, Key_tdb_text_wdata_pid		= "tdb.text.wdata.pid"
	, Key_exec_sql					= "import.sql.exec_sql"
	, Key_decompress_bz2			= "core.decompress_bz2"
	;
}
 