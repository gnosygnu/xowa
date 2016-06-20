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
package gplx.xowa.apps.apis.xowa.bldrs.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.bldrs.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.apps.apis.xowa.bldrs.imports.page_ranks.*;
public class Xoapi_import implements Gfo_invk {
	public Xoapi_page_rank Page_rank() {return page_rank;} private final    Xoapi_page_rank page_rank = new Xoapi_page_rank();
	public long		Layout_all_max()		{return layout_all_max;}		private long layout_all_max			= 0;									// disable by default; may set to 200 MB in future
	public long		Layout_text_max()		{return layout_text_max;}		private long layout_text_max		= Io_size_.To_long_by_int_mb(1500);		// 1.5 GB
	public long		Layout_html_max()		{return layout_html_max;}		private long layout_html_max		= Io_size_.To_long_by_int_mb(1500);		// 1.5 GB
	public long		Layout_file_max()		{return layout_file_max;}		private long layout_file_max		= Io_size_.To_long_by_int_mb(1500);		// 1.5 GB
	public long		Cat_link_db_max()		{return cat_link_db_max;}		private long cat_link_db_max		= Io_size_.To_long_by_int_mb(1500);		// 3.6 GB; v1
	public long		Text_db_max()			{return text_db_max;}			private long text_db_max			= Io_size_.To_long_by_int_mb(1500);		// 3.0 GB; v1
	public long		Html_db_max()			{return html_db_max;}			private long html_db_max			= Io_size_.To_long_by_int_mb(1500);		// 3.0 GB; v2; use same as text
	public long		File_db_max()			{return file_db_max;}			private long file_db_max			= Io_size_.To_long_by_int_mb(1500);		// 3.2 GB; v2
	public byte[]	Ns_file_map()			{return ns_file_map;}			private byte[] ns_file_map			= Ns_file_map__each;
	public byte		Zip_tid_text()			{return zip_tid_text;}			private byte zip_tid_text			= Io_stream_.Tid_gzip;
	public byte		Zip_tid_html()			{return zip_tid_html;}			private byte zip_tid_html			= Io_stream_.Tid_gzip;
	public boolean		Hzip_enabled()			{return hzip_enabled;}			private boolean hzip_enabled			= Bool_.Y;
	public boolean		Hzip_mode_is_b256()		{return hzip_mode_is_b256;}		private boolean hzip_mode_is_b256		= Bool_.Y;
	public String	User_name()				{return user_name;}				private String user_name			= "anonymous";
	public Xowd_core_db_props New_props(String domain_str, long dump_file_size) {
		Xow_db_layout layout_text, layout_html, layout_file;
		if		(dump_file_size < layout_all_max)
			layout_text = layout_html = layout_file = Xow_db_layout.Itm_all;
		else {
			layout_text	= dump_file_size < layout_text_max ? Xow_db_layout.Itm_few : Xow_db_layout.Itm_lot;
			layout_html	= dump_file_size < layout_html_max ? Xow_db_layout.Itm_few : Xow_db_layout.Itm_lot;
			layout_file	= dump_file_size < layout_file_max ? Xow_db_layout.Itm_few : Xow_db_layout.Itm_lot;
		}
		return new Xowd_core_db_props(2, layout_text, layout_html, layout_file, zip_tid_text, zip_tid_html, hzip_enabled, hzip_mode_is_b256);
	}
	public byte[] New_ns_file_map(long dump_file_size) {
		return dump_file_size < layout_text_max 
			? gplx.xowa.bldrs.cmds.Xob_ns_file_itm_parser.Ns_file_map__few
			: gplx.xowa.bldrs.cmds.Xob_ns_file_itm_parser.Ns_file_map__each; // DB.FEW: DATE:2016-06-07
		// return dump_file_size < layout_text_max ? Bry_.Empty : Ns_file_map__each;
	}
	public void		Zip_tid_text_raw_() {zip_tid_text = Io_stream_.Tid_raw;}	// TEST:
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_layout_all_max))						return Io_size_.To_str_mb(layout_all_max);
		else if	(ctx.Match(k, Invk_layout_all_max_))	 				layout_all_max = Io_size_.To_long_by_msg_mb(m, layout_all_max);
		else if	(ctx.Match(k, Invk_layout_text_max)) 					return Io_size_.To_str_mb(layout_text_max);
		else if	(ctx.Match(k, Invk_layout_text_max_)) 					layout_text_max = Io_size_.To_long_by_msg_mb(m, layout_text_max);
		else if	(ctx.Match(k, Invk_layout_html_max)) 					return Io_size_.To_str_mb(layout_html_max);
		else if	(ctx.Match(k, Invk_layout_html_max_)) 					layout_html_max = Io_size_.To_long_by_msg_mb(m, layout_html_max);
		else if	(ctx.Match(k, Invk_layout_file_max)) 					return Io_size_.To_str_mb(layout_file_max);
		else if	(ctx.Match(k, Invk_layout_file_max_)) 					layout_file_max = Io_size_.To_long_by_msg_mb(m, layout_file_max);
		else if	(ctx.Match(k, Invk_cat_link_db_max)) 					return Io_size_.To_str_mb(cat_link_db_max);
		else if	(ctx.Match(k, Invk_cat_link_db_max_)) 					cat_link_db_max = Io_size_.To_long_by_msg_mb(m, cat_link_db_max);
		else if	(ctx.Match(k, Invk_text_db_max)) 						return Io_size_.To_str_mb(text_db_max);
		else if	(ctx.Match(k, Invk_text_db_max_)) 						text_db_max = Io_size_.To_long_by_msg_mb(m, text_db_max);
		else if	(ctx.Match(k, Invk_html_db_max)) 						return Io_size_.To_str_mb(html_db_max);
		else if	(ctx.Match(k, Invk_html_db_max_)) 						html_db_max = Io_size_.To_long_by_msg_mb(m, html_db_max);
		else if	(ctx.Match(k, Invk_file_db_max)) 						return Io_size_.To_str_mb(file_db_max);
		else if	(ctx.Match(k, Invk_file_db_max_)) 						file_db_max = Io_size_.To_long_by_msg_mb(m, file_db_max);
		else if	(ctx.Match(k, Invk_ns_file_map)) 						return String_.new_u8(ns_file_map);
		else if	(ctx.Match(k, Invk_ns_file_map_)) 						ns_file_map = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_zip_tid_text)) 						return Io_stream_.To_str(zip_tid_text);
		else if	(ctx.Match(k, Invk_zip_tid_text_)) 						zip_tid_text = Io_stream_.To_tid(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_zip_tid_list)) 						return Options_zip_tid__list;
		else if	(ctx.Match(k, Invk_zip_tid_html)) 						return Io_stream_.To_str(zip_tid_html);
		else if	(ctx.Match(k, Invk_zip_tid_html_)) 						zip_tid_html = Io_stream_.To_tid(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_hzip_enabled)) 						return Yn.To_str(hzip_enabled);
		else if	(ctx.Match(k, Invk_hzip_enabled_)) 						hzip_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_hzip_mode_is_b256)) 					return Yn.To_str(hzip_mode_is_b256);
		else if	(ctx.Match(k, Invk_hzip_mode_is_b256_)) 				hzip_mode_is_b256 = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_user_name)) 							return user_name;
		else if	(ctx.Match(k, Invk_user_name_)) 						user_name = m.ReadStr("v");
		else if	(ctx.Match(k, Invk_page_rank)) 							return page_rank;
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final    Keyval[] Options_zip_tid__list = Keyval_.Ary(Keyval_.new_("raw", "text"), Keyval_.new_("gzip"), Keyval_.new_("bzip2"));
	private static final String
	  Invk_layout_all_max		= "layout_all_max"		, Invk_layout_all_max_		= "layout_all_max_"
	, Invk_layout_text_max		= "layout_text_max"		, Invk_layout_text_max_		= "layout_text_max_"
	, Invk_layout_html_max		= "layout_html_max"		, Invk_layout_html_max_		= "layout_html_max_"
	, Invk_layout_file_max		= "layout_file_max"		, Invk_layout_file_max_		= "layout_file_max_"
	, Invk_cat_link_db_max		= "cat_link_db_max"		, Invk_cat_link_db_max_		= "cat_link_db_max_"
	, Invk_text_db_max			= "text_db_max"			, Invk_text_db_max_			= "text_db_max_"
	, Invk_html_db_max			= "html_db_max"			, Invk_html_db_max_			= "html_db_max_"
	, Invk_file_db_max			= "file_db_max"			, Invk_file_db_max_			= "file_db_max_"
	, Invk_ns_file_map			= "ns_file_map"			, Invk_ns_file_map_			= "ns_file_map_"
	, Invk_zip_tid_text			= "zip_tid_text"		, Invk_zip_tid_text_		= "zip_tid_text_", Invk_zip_tid_list		= "zip_tid_list"
	, Invk_zip_tid_html			= "zip_tid_html"		, Invk_zip_tid_html_		= "zip_tid_html_"
	, Invk_user_name			= "user_name"			, Invk_user_name_			= "user_name_"
	, Invk_hzip_enabled			= "hzip_enabled"		, Invk_hzip_enabled_		= "hzip_enabled_"
	, Invk_hzip_mode_is_b256	= "hzip_mode_is_b256"	, Invk_hzip_mode_is_b256_	= "hzip_mode_is_b256_"
	, Invk_page_rank			= "page_rank"
	;
	public static final    byte[] Ns_file_map__each = Bry_.new_a7("<each>");
}
