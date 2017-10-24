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
import gplx.core.ios.*;
import gplx.xowa.wikis.data.*;
public class Xobldr_cfg {
	private static long layout_all_max		= 0;									// disable by default; may set to 200 MB in future
	private static boolean hzip_enabled		= Bool_.Y;
	private static boolean hzip_mode_is_b256	= Bool_.Y;

	public static byte Zip_mode__text(Xoa_app app) {return Zip_mode(app, "xowa.bldr.db.zip_mode.text");}	// CFG: Cfg__
	public static byte Zip_mode__html(Xoa_app app) {return Zip_mode(app, "xowa.bldr.db.zip_mode.html");}	// CFG: Cfg__
	private static byte Zip_mode(Xoa_app app, String key) {
		String val = app.Cfg().Get_str_app_or(key, "gzip");
		return gplx.core.ios.streams.Io_stream_tid_.To_tid(val);
	}
	public static long Max_size__text(Xoa_app app) {return Max_size(app, "xowa.bldr.db.max_size.text");}	// CFG: Cfg__
	public static long Max_size__html(Xoa_app app) {return Max_size(app, "xowa.bldr.db.max_size.html");}	// CFG: Cfg__
	public static long Max_size__file(Xoa_app app) {return Max_size(app, "xowa.bldr.db.max_size.file");}	// CFG: Cfg__
	private static long Max_size(Xoa_app app, String key) {
		long rv = app.Cfg().Get_long_app_or(key, Io_size_.To_long_by_int_mb(1500));
		return rv * Io_mgr.Len_mb;
	}
	public static long Layout_size__text(Xoa_app app) {return Layout_size(app, "xowa.bldr.db.layout_size.text");}	// CFG: Cfg__
	public static long Layout_size__html(Xoa_app app) {return Layout_size(app, "xowa.bldr.db.layout_size.html");}	// CFG: Cfg__
	public static long Layout_size__file(Xoa_app app) {return Layout_size(app, "xowa.bldr.db.layout_size.file");}	// CFG: Cfg__
	private static long Layout_size(Xoa_app app, String key) {
		long rv = app.Cfg().Get_long_app_or(key, Io_size_.To_long_by_int_mb(1500));
		return rv * Io_mgr.Len_mb;
	}
	public static byte[] New_ns_file_map(Xoa_app app, long dump_file_size) {
		return dump_file_size < Layout_size__text(app)
			? gplx.xowa.bldrs.cmds.Xob_ns_file_itm_parser.Ns_file_map__few
			: gplx.xowa.bldrs.cmds.Xob_ns_file_itm_parser.Ns_file_map__each; // DB.FEW: DATE:2016-06-07
	}
	public static Xowd_core_db_props New_props(Xoa_app app, String domain_str, long dump_file_size) {
		Xow_db_layout layout_text, layout_html, layout_file;
		if		(dump_file_size < layout_all_max)
			layout_text = layout_html = layout_file = Xow_db_layout.Itm_all;
		else {
			layout_text	= dump_file_size < Layout_size__text(app) ? Xow_db_layout.Itm_few : Xow_db_layout.Itm_lot;
			layout_html	= dump_file_size < Layout_size__html(app) ? Xow_db_layout.Itm_few : Xow_db_layout.Itm_lot;
			layout_file	= dump_file_size < Layout_size__file(app) ? Xow_db_layout.Itm_few : Xow_db_layout.Itm_lot;
		}
		return new Xowd_core_db_props(2, layout_text, layout_html, layout_file, Zip_mode__text(app), Zip_mode__html(app), hzip_enabled, hzip_mode_is_b256);
	}
	public static final    byte[] Ns_file_map__each = Bry_.new_a7("<each>");
}
