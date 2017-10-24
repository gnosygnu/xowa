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
package gplx.xowa.bldrs.wms.dumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.bldrs.installs.*;
public class Xowm_dump_file_ {
	public static Xowm_dump_file parse(byte[] src) {
		int src_len = src.length;
		int dash_0 = Bry_find_.Find_fwd(src, Byte_ascii.Dash, 0			, src_len); if (dash_0 == Bry_find_.Not_found) throw Err_.new_parse_type(Xowm_dump_file.class, String_.new_u8(src));
		int dash_1 = Bry_find_.Find_fwd(src, Byte_ascii.Dash, dash_0 + 1	, src_len); if (dash_1 == Bry_find_.Not_found) throw Err_.new_parse_type(Xowm_dump_file.class, String_.new_u8(src));
		byte[] domain_bry = Xow_abrv_wm_.Parse_to_domain_bry(Bry_.Mid(src, 0, dash_0));
		return new Xowm_dump_file(String_.new_u8(domain_bry), String_.new_u8(src, dash_0 + 1, dash_1), String_.new_u8(src, dash_1 + 1, src_len));
	}
	public static boolean Connect_first(Xowm_dump_file rv, String[] server_urls) {
		int len = server_urls.length;
		for (int i = 0; i < len; ++i) {
			String server_url = server_urls[i];
			rv.Server_url_(server_url);
			Override_dump_date(rv, server_url);
			if (rv.Connect()) return true;
		}
		return false;
	}
	private static void Override_dump_date(Xowm_dump_file rv, String dump_server) {
		String dump_date = rv.Dump_date();
		if (	String_.Eq(dump_date, Xowm_dump_file_.Date_latest)
			&&	(	String_.Eq(dump_server, Xowm_dump_file_.Server_c3sl)
				||	String_.Eq(dump_server, Xowm_dump_file_.Server_masaryk)
				)
			){
			Xoa_app_.Usr_dlg().Note_many("", "", "wmf.dump:dump date; server_url=~{0} dump_date=~{1}", dump_server, dump_date);
			Xoi_mirror_parser mirror_parser = new Xoi_mirror_parser();
			String dump_wiki_url = dump_server + String_.new_a7(rv.Dump_abrv()) + "/";
			byte[] dump_url_wiki_html = gplx.core.ios.IoEngine_xrg_downloadFil.new_("", Io_url_.Empty).Exec_as_bry(dump_wiki_url); if (Bry_.Len_eq_0(dump_url_wiki_html)) return;
			String[] dump_available_dates = mirror_parser.Parse(String_.new_u8(dump_url_wiki_html));
			String dump_dates_latest = Xoi_mirror_parser.Find_last_lte(dump_available_dates, dump_date);
			if (String_.Eq(dump_dates_latest, "")) return;	// nothing found
			rv.Dump_date_(dump_dates_latest);
		}
	}
	public static byte[] Bld_dump_dir_url(byte[] server_url, byte[] alias, byte[] date) {
		return Bry_.Add
		( server_url																	// "http://dumps.wikimedia.org/"
		, Bry_.Replace(alias, Byte_ascii.Dash, Byte_ascii.Underline), Bry_slash			// "simplewiki/"
		, date, Bry_slash																// "latest/"
		);
	}
	public static byte[] Bld_dump_file_name(byte[] alias, byte[] date, byte[] dump_file_type, byte[] ext) {
		return Bry_.Add
		( Bry_.Replace(alias, Byte_ascii.Dash, Byte_ascii.Underline), Bry_dash			// "simplewiki-"
		, date, Bry_dash																// "latest-"
		, dump_file_type																// "pages-articles"
		, ext																			// ".xml.bz2"
		);
	}
	private static final byte[] Bry_dash = new byte[] {Byte_ascii.Dash}, Bry_slash = new byte[] {Byte_ascii.Slash};
	public static final byte[] Ext_xml_bz2 = Bry_.new_a7(".xml.bz2");
	public static final byte[] Ext_sql_gz  = Bry_.new_a7(".sql.gz");
	public static final String 
	  Server_wmf_http		= "http://dumps.wikimedia.org/"
	, Server_wmf_https		= "https://dumps.wikimedia.org/"
	, Server_your_org		= "http://dumps.wikimedia.your.org/"
	, Server_c3sl			= "http://wikipedia.c3sl.ufpr.br/"
	, Server_masaryk		= "http://ftp.fi.muni.cz/pub/wikimedia/"
	, Date_latest			= "latest"
	;
}
