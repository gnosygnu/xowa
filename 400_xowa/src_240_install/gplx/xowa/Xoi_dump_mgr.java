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
package gplx.xowa; import gplx.*;
import gplx.ios.*;
import gplx.xowa.wmfs.dumps.*;
public class Xoi_dump_mgr implements GfoInvkAble {
	public String[] Server_urls() {return server_urls;} private String[] server_urls = String_.Ary(Xowm_dump_file_.Server_wmf_https, Xowm_dump_file_.Server_your_org, Xowm_dump_file_.Server_c3sl, Xowm_dump_file_.Server_masaryk);
	public String[] Custom_cmds() {return custom_cmds;} private String[] custom_cmds = String_.Ary(Xoi_cmd_wiki_download.Key_wiki_download, Xoi_cmd_wiki_import.KEY);
	public byte Data_storage_format()	{return data_storage_format;} public Xoi_dump_mgr Data_storage_format_(byte v) {data_storage_format = v; return this;} private byte data_storage_format = gplx.ios.Io_stream_.Tid_gzip;
	public long Db_text_max()			{return db_text_max;}			private long db_text_max			= (long)3000 * Io_mgr.Len_mb;
	public long Db_categorylinks_max()	{return db_categorylinks_max;}	private long db_categorylinks_max	= (long)3600 * Io_mgr.Len_mb;
	public long Db_wikidata_max()		{return db_wikidata_max;}		private long db_wikidata_max		= (long)3600 * Io_mgr.Len_mb;
	public byte Wiki_storage_type()		{return wiki_storage_type;}		private byte wiki_storage_type = Wiki_storage_type_sqlite;
	public boolean Wiki_storage_type_is_sql()	{return wiki_storage_type == Wiki_storage_type_sqlite;}
	public String Db_ns_map() {return db_ns_map;} private String db_ns_map = "Template~Module";
	public boolean Css_wiki_update() {return css_wiki_update;} private boolean css_wiki_update = true;
	public boolean Css_commons_download() {return css_commons_download;} private boolean css_commons_download = true; // changed from false to true; DATE:2014-10-19
	public boolean Delete_xml_file() {return delete_xml_file;} private boolean delete_xml_file = true;
	public byte Search_version() {return search_version;} private byte search_version = gplx.xowa.specials.search.Xows_page__search.Version_2;
	public boolean Import_bz2_by_stdout() {return import_bz2_by_stdout;} private boolean import_bz2_by_stdout = true;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_server_urls))						return String_.Concat_with_str(",\n", server_urls);
		else if	(ctx.Match(k, Invk_server_urls_))						server_urls = m.ReadStrAryIgnore("v", ",", "\n");
		else if (ctx.Match(k, Invk_custom_cmds))						return String_.Concat_with_str(",", custom_cmds);
		else if	(ctx.Match(k, Invk_custom_cmds_))						custom_cmds = String_.Ary_filter(m.ReadStrAry("v", ","), Xoi_cmd_mgr.Wiki_cmds_valid);
		else if (ctx.Match(k, Invk_data_storage_format))				return Io_stream_.Obsolete_to_str(data_storage_format);
		else if	(ctx.Match(k, Invk_data_storage_format_))				data_storage_format = Io_stream_.Obsolete_to_tid(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_data_storage_format_list))			return Options_data_storage_format_list;
		else if	(ctx.Match(k, Invk_db_text_max))						return db_text_max / Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_db_text_max_))						db_text_max = m.ReadLong("v") * Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_db_categorylinks_max))				return db_categorylinks_max / Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_db_categorylinks_max_))				db_categorylinks_max = m.ReadLong("v") * Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_db_wikidata_max))					return db_wikidata_max / Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_db_wikidata_max_))					db_wikidata_max = m.ReadLong("v") * Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_db_ns_map))							return db_ns_map;
		else if	(ctx.Match(k, Invk_db_ns_map_))							db_ns_map = m.ReadStr("v");
		else if	(ctx.Match(k, Invk_wiki_storage_type))					return Wiki_storage_type_str(wiki_storage_type);
		else if	(ctx.Match(k, Invk_wiki_storage_type_))					wiki_storage_type = Wiki_storage_type_parse(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_wiki_storage_type_list))				return Options_storage_type_list;
		else if	(ctx.Match(k, Invk_css_wiki_update))					return Yn.Xto_str(css_wiki_update);
		else if	(ctx.Match(k, Invk_css_wiki_update_))					css_wiki_update = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_css_commons_download))				return Yn.Xto_str(css_commons_download);
		else if	(ctx.Match(k, Invk_css_commons_download_))				css_commons_download = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_delete_xml_file))					return Yn.Xto_str(delete_xml_file);
		else if	(ctx.Match(k, Invk_delete_xml_file_))					delete_xml_file = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_search_version))						return Options_search_version_str(search_version);
		else if	(ctx.Match(k, Invk_search_version_))					search_version = Options_search_version_parse(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_search_version_list))				return Options_search_version_list;
		else if	(ctx.Match(k, Invk_import_bz2_by_stdout))				return Yn.Xto_str(import_bz2_by_stdout);
		else if	(ctx.Match(k, Invk_import_bz2_by_stdout_))				import_bz2_by_stdout = m.ReadYn("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_server_urls = "server_urls", Invk_server_urls_ = "server_urls_", Invk_custom_cmds = "custom_cmds", Invk_custom_cmds_ = "custom_cmds_"
	, Invk_data_storage_format = "data_storage_format", Invk_data_storage_format_ = "data_storage_format_", Invk_data_storage_format_list = "data_storage_format_list"
	, Invk_db_text_max = "db_text_max", Invk_db_text_max_ = "db_text_max_", Invk_db_categorylinks_max = "db_categorylinks_max", Invk_db_categorylinks_max_ = "db_categorylinks_max_", Invk_db_wikidata_max = "db_wikidata_max", Invk_db_wikidata_max_ = "db_wikidata_max_"
	, Invk_db_ns_map = "db_ns_map", Invk_db_ns_map_ = "db_ns_map_"
	, Invk_wiki_storage_type = "wiki_storage_type", Invk_wiki_storage_type_ = "wiki_storage_type_", Invk_wiki_storage_type_list = "wiki_storage_type_list"
	, Invk_css_wiki_update = "css_wiki_update", Invk_css_wiki_update_ = "css_wiki_update_"
	, Invk_css_commons_download = "css_commons_download", Invk_css_commons_download_ = "css_commons_download_"
	, Invk_delete_xml_file = "delete_xml_file", Invk_delete_xml_file_ = "delete_xml_file_"
	, Invk_search_version = "search_version", Invk_search_version_ = "search_version_", Invk_search_version_list = "search_version_list"
	, Invk_import_bz2_by_stdout = "import_bz2_by_stdout", Invk_import_bz2_by_stdout_ = "import_bz2_by_stdout_"
	;
	private static KeyVal[] Options_data_storage_format_list = KeyVal_.Ary(KeyVal_.new_(".xdat", "text"), KeyVal_.new_(".gz", "gzip"), KeyVal_.new_(".bz2", "bzip2"));	// removed .zip; DATE:2014-05-13; updated aliases; DATE:2014-06-20
	static final byte Wiki_storage_type_xdat = 1, Wiki_storage_type_sqlite = 2;
	private static final KeyVal[] Options_storage_type_list = KeyVal_.Ary(KeyVal_.new_("sqlite"), KeyVal_.new_("xdat"));	// DEPRECATED: KeyVal_.new_("xdat"); DATE:2015-03-30
	public static String Wiki_storage_type_str(byte v) {
		switch (v) {
			case Xoi_dump_mgr.Wiki_storage_type_xdat	: return "xdat";
			case Xoi_dump_mgr.Wiki_storage_type_sqlite	: return "sqlite";
			default										: throw Err_.new_unhandled(v);
		}
	}
	public static byte Wiki_storage_type_parse(String v) {
		if		(String_.Eq(v, "xdat"))				return Xoi_dump_mgr.Wiki_storage_type_xdat;
		else if	(String_.Eq(v, "sqlite"))			return Xoi_dump_mgr.Wiki_storage_type_sqlite;
		else										throw Err_.new_unhandled(v);
	}
	private static final KeyVal[] Options_search_version_list = KeyVal_.Ary(KeyVal_.new_("1"), KeyVal_.new_("2")); 
	public static String Options_search_version_str(byte v)		{return Byte_.Xto_str(v);}
	public static byte Options_search_version_parse(String v)	{return Byte_.parse_(v);}
}
