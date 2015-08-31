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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
public class Xob_cmd_keys {
	public static final String
	  Key_text_init					= "text.init"				// "import.sql.init"
	, Key_text_page					= "text.page"				// "import.sql.page"
	, Key_text_css					= "text.css"
	, Key_text_search_cmd			= "text.search.cmd"			// "import.sql.search_title.cmd"
	, Key_text_search_wkr			= "text.search"				// "import.sql.search_title.wkr"
	, Key_text_cat_core_v1			= "text.cat.core.v1"		// "import.sql.category_v1"
	, Key_text_cat_core				= "text.cat.core"			// "import.sql.category_registry"
	, Key_text_cat_link				= "text.cat.link"			// "import.sql.categorylinks"
	, Key_text_cat_hidden			= "text.cat.hidden"			// "import.sql.hiddencat"
	, Key_text_term					= "text.term"				// "import.sql.term"
	, Key_wiki_redirect				= "wiki.redirect"			// "wiki.redirect"
	, Key_wiki_image				= "wiki.image"				// "wiki.image"
	, Key_wiki_page_dump_make		= "wiki.page_dump.make"		// "file.page_dump"
	, Key_wiki_page_dump_drop		= "wiki.page_dump.drop"
	, Key_wiki_pagelink				= "wiki.pagelink"
	, Key_file_lnki_temp			= "file.lnki_temp"
	, Key_file_lnki_regy			= "file.lnki_regy"
	, Key_file_page_regy			= "file.page_regy"
	, Key_file_orig_regy			= "file.orig_regy"
	, Key_file_xfer_temp_thumb		= "file.xfer_temp.thumb"
	, Key_file_xfer_temp_orig		= "file.xfer_temp.orig"
	, Key_file_xfer_regy			= "file.xfer_regy"
	, Key_file_xfer_regy_update		= "file.xfer_regy_update"
	, Key_file_fsdb_make			= "file.fsdb_make"
	, Key_file_fsdb_reduce			= "file.fsdb_reduce"
	, Key_file_orig_reg				= "file.orig_reg"
	, Key_file_xfer_update			= "file.xfer_update"
	, Key_html_redlinks				= "html.redlinks"
	, Key_util_cleanup				= "util.cleanup"			// "core.cleanup"
	, Key_util_download				= "util.download"			// "file.download"
	, Key_util_xml_dump				= "util.xml_dump"
	, Key_wbase_json_dump			= "wbase.json_dump"
	, Key_wbase_qid					= "wbase.qid"				// "text.wdata.qid"
	, Key_wbase_pid					= "wbase.pid"				// "text.wdata.pid"
	, Key_wbase_db					= "wbase.db"				// "wiki.wdata_db"
	, Key_site_meta					= "util.site_meta"
	, Key_tdb_text_init				= "tdb.text.init"			// "core.init"
	, Key_tdb_make_page				= "tdb.text.page"			// "core.make_page"
	, Key_tdb_make_id				= "core.make_id"
	, Key_tdb_make_search_title		= "core.make_search_title"
	, Key_tdb_make_category			= "core.make_category"
	, Key_tdb_calc_stats			= "core.calc_stats"
	, Key_tdb_core_term				= "tdb.text.term"			// "core.term"
	, Key_tdb_text_cat_link			= "ctg.link_sql"
	, Key_tdb_ctg_link_idx			= "ctg.link_idx"
	, Key_tdb_cat_hidden_sql		= "ctg.hiddencat_sql"
	, Key_tdb_cat_hidden_ttl		= "ctg.hiddencat_ttl"
	, Key_tdb_text_wdata_qid		= "tdb.text.wdata.qid"
	, Key_tdb_text_wdata_pid		= "tdb.text.wdata.pid"
	, Key_diff_regy_exec			= "file.diff_regy.exec"
	, Key_diff_regy_make			= "file.diff_regy.make"
	, Key_exec_sql					= "import.sql.exec_sql"
	, Key_deploy_zip				= "deploy.zip"
	, Key_deploy_copy				= "deploy.copy"
	, Key_decompress_bz2			= "core.decompress_bz2"
	;
}
 