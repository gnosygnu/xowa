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
package gplx.xowa.bldrs.installs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*;
import gplx.xowa.bldrs.wms.dumps.*;
public class Xoi_dump_mgr implements Gfo_invk {
	public String[] Server_urls() {return server_urls;} private String[] server_urls = String_.Ary(Xowm_dump_file_.Server_your_org, Xowm_dump_file_.Server_wmf_https, Xowm_dump_file_.Server_c3sl, Xowm_dump_file_.Server_masaryk);	// promote your.org to primary url; DATE:2016-08-07
	public boolean Import_bz2_by_stdout() {return import_bz2_by_stdout;} private boolean import_bz2_by_stdout = true;

	public String[] Custom_cmds() {return custom_cmds;} private String[] custom_cmds = String_.Ary(Xoi_cmd_wiki_download.Key_wiki_download, Xoi_cmd_wiki_import.KEY);
	public byte Data_storage_format()	{return data_storage_format;} public Xoi_dump_mgr Data_storage_format_(byte v) {data_storage_format = v; return this;} private byte data_storage_format = gplx.core.ios.streams.Io_stream_tid_.Tid__gzip;
	public long Db_text_max()			{return db_text_max;}			private long db_text_max			= (long)3000 * Io_mgr.Len_mb;
	public long Db_categorylinks_max()	{return db_categorylinks_max;}	private long db_categorylinks_max	= (long)3600 * Io_mgr.Len_mb;
	public long Db_wikidata_max()		{return db_wikidata_max;}		private long db_wikidata_max		= (long)3600 * Io_mgr.Len_mb;
	public String Db_ns_map() {return db_ns_map;} private String db_ns_map = "Template~Module";
	public boolean Css_wiki_update() {return css_wiki_update;} private boolean css_wiki_update = true;
	public boolean Css_commons_download() {return css_commons_download;} private boolean css_commons_download = true; // changed from false to true; DATE:2014-10-19
	public boolean Delete_xml_file() {return delete_xml_file;} private boolean delete_xml_file = true;
	public byte Search_version() {return search_version;} private byte search_version = gplx.xowa.addons.wikis.searchs.specials.Srch_special_page.Version_2;
	public int Page_rank_iterations() {return page_rank_iteration_max;} private int page_rank_iteration_max = 0;
	public void Init_by_wiki(Xow_wiki wiki) {
		wiki.App().Cfg().Bind_many_app(this
		, Cfg__server_urls, Cfg__download_xowa_commons, Cfg__delete_xml_file
		, Cfg__custom_wiki_commands, Cfg__bz2__stdout_enabled
		, Cfg__page_rank__iteration_max
		);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__server_urls))						server_urls = m.ReadStrAryIgnore("v", ",", "\n");
		else if	(ctx.Match(k, Cfg__download_xowa_commons))				css_commons_download = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__delete_xml_file))					delete_xml_file = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__custom_wiki_commands))				custom_cmds = String_.Ary_filter(m.ReadStrAry("v", ","), Xoi_cmd_mgr.Wiki_cmds_valid);
		else if	(ctx.Match(k, Cfg__bz2__stdout_enabled))				import_bz2_by_stdout = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__page_rank__iteration_max))			page_rank_iteration_max = m.ReadInt("v");

		else if (ctx.Match(k, Invk_data_storage_format))				return Io_stream_tid_.Obsolete_to_str(data_storage_format);
		else if	(ctx.Match(k, Invk_data_storage_format_))				data_storage_format = Io_stream_tid_.Obsolete_to_tid(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_data_storage_format_list))			return Options_data_storage_format_list;
		else if	(ctx.Match(k, Invk_db_text_max))						return db_text_max / Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_db_text_max_))						db_text_max = m.ReadLong("v") * Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_db_categorylinks_max))				return db_categorylinks_max / Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_db_categorylinks_max_))				db_categorylinks_max = m.ReadLong("v") * Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_db_wikidata_max))					return db_wikidata_max / Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_db_wikidata_max_))					db_wikidata_max = m.ReadLong("v") * Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_db_ns_map))							return db_ns_map;
		else if	(ctx.Match(k, Invk_db_ns_map_))							db_ns_map = m.ReadStr("v");
		else if	(ctx.Match(k, Invk_css_wiki_update))					return Yn.To_str(css_wiki_update);
		else if	(ctx.Match(k, Invk_css_wiki_update_))					css_wiki_update = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_search_version))						return Options_search_version_str(search_version);
		else if	(ctx.Match(k, Invk_search_version_))					search_version = Options_search_version_parse(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_search_version_list))				return Options_search_version_list;
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_data_storage_format = "data_storage_format", Invk_data_storage_format_ = "data_storage_format_", Invk_data_storage_format_list = "data_storage_format_list"
	, Invk_db_text_max = "db_text_max", Invk_db_text_max_ = "db_text_max_", Invk_db_categorylinks_max = "db_categorylinks_max", Invk_db_categorylinks_max_ = "db_categorylinks_max_", Invk_db_wikidata_max = "db_wikidata_max", Invk_db_wikidata_max_ = "db_wikidata_max_"
	, Invk_db_ns_map = "db_ns_map", Invk_db_ns_map_ = "db_ns_map_"
	, Invk_css_wiki_update = "css_wiki_update", Invk_css_wiki_update_ = "css_wiki_update_"
	, Invk_search_version = "search_version", Invk_search_version_ = "search_version_", Invk_search_version_list = "search_version_list"		
	;
	private static Keyval[] Options_data_storage_format_list = Keyval_.Ary(Keyval_.new_(".xdat", "text"), Keyval_.new_(".gz", "gzip"), Keyval_.new_(".bz2", "bzip2"));	// removed .zip; DATE:2014-05-13; updated aliases; DATE:2014-06-20

	private static final    Keyval[] Options_search_version_list = Keyval_.Ary(Keyval_.new_("1"), Keyval_.new_("2")); 
	public static String Options_search_version_str(byte v)		{return Byte_.To_str(v);}
	public static byte Options_search_version_parse(String v)	{return Byte_.parse(v);}

	private static final String 
	  Cfg__server_urls					= "xowa.wiki.import.general.dump_servers"
	, Cfg__download_xowa_commons		= "xowa.wiki.import.general.download_xowa_common"
	, Cfg__delete_xml_file				= "xowa.wiki.import.general.delete_xml_file"
	, Cfg__custom_wiki_commands			= "xowa.wiki.import.general.custom_wiki_commands"
	, Cfg__bz2__stdout_enabled			= "xowa.wiki.import.bz2.stdout_enabled"
	, Cfg__page_rank__iteration_max		= "xowa.wiki.import.page_rank.iteration_max"
	;
}
