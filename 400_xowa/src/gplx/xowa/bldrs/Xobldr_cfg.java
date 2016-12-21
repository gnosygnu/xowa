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
import gplx.core.ios.*;
import gplx.xowa.wikis.data.*;
public class Xobldr_cfg {
	private static long layout_all_max		= 0;									// disable by default; may set to 200 MB in future
	private static long layout_text_max		= Io_size_.To_long_by_int_mb(1500);		// 1.0 GB
	private static long layout_html_max		= Io_size_.To_long_by_int_mb(1500);		// 1.0 GB
	private static long layout_file_max		= Io_size_.To_long_by_int_mb(1500);		// 1.0 GB
	private static boolean hzip_enabled		= Bool_.Y;
	private static boolean hzip_mode_is_b256	= Bool_.Y;

	public static byte Zip_mode__text(Xoa_app app) {return Zip_mode(app, Cfg__zip_mode__text);}
	public static byte Zip_mode__html(Xoa_app app) {return Zip_mode(app, Cfg__zip_mode__html);}
	private static byte Zip_mode(Xoa_app app, String key) {
		String val = app.Cfg().Get_str_app_or(key, "gzip");
		return gplx.core.ios.streams.Io_stream_tid_.To_tid(val);
	}
	public static long Max_size__text(Xoa_app app) {return Max_size(app, Cfg__max_size__text);}
	public static long Max_size__html(Xoa_app app) {return Max_size(app, Cfg__max_size__html);}
	public static long Max_size__file(Xoa_app app) {return Max_size(app, Cfg__max_size__file);}
	private static long Max_size(Xoa_app app, String key) {
		return app.Cfg().Get_long_app_or(key, Io_size_.To_long_by_int_mb(1500));
	}
	public static byte[] New_ns_file_map(long dump_file_size) {
		return dump_file_size < layout_text_max 
			? gplx.xowa.bldrs.cmds.Xob_ns_file_itm_parser.Ns_file_map__few
			: gplx.xowa.bldrs.cmds.Xob_ns_file_itm_parser.Ns_file_map__each; // DB.FEW: DATE:2016-06-07
	}
	public static Xowd_core_db_props New_props(Xoa_app app, String domain_str, long dump_file_size) {
		Xow_db_layout layout_text, layout_html, layout_file;
		if		(dump_file_size < layout_all_max)
			layout_text = layout_html = layout_file = Xow_db_layout.Itm_all;
		else {
			layout_text	= dump_file_size < layout_text_max ? Xow_db_layout.Itm_few : Xow_db_layout.Itm_lot;
			layout_html	= dump_file_size < layout_html_max ? Xow_db_layout.Itm_few : Xow_db_layout.Itm_lot;
			layout_file	= dump_file_size < layout_file_max ? Xow_db_layout.Itm_few : Xow_db_layout.Itm_lot;
		}
		return new Xowd_core_db_props(2, layout_text, layout_html, layout_file, Zip_mode__text(app), Zip_mode__html(app), hzip_enabled, hzip_mode_is_b256);
	}
	private static final String 
	  Cfg__zip_mode__text = "xowa.wiki.database.zip_mode.text"
	, Cfg__zip_mode__html = "xowa.wiki.database.zip_mode.html"
	, Cfg__max_size__text = "xowa.wiki.database.max_size.text"
	, Cfg__max_size__html = "xowa.wiki.database.max_size.html"
	, Cfg__max_size__file = "xowa.wiki.database.max_size.file"
	;
	public static final    byte[] Ns_file_map__each = Bry_.new_a7("<each>");
}
