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
import gplx.core.ios.*;
import gplx.xowa.wikis.domains.*;
public class Xowm_dump_file {
	public Xowm_dump_file(String domain_str, String dump_date, String dump_type_str) {
		this.dump_date = dump_date; this.dump_type_str = dump_type_str;
		this.domain_itm = Xow_domain_itm_.parse(Bry_.new_u8(domain_str));
		this.dump_abrv = Xow_abrv_wm_.To_abrv(domain_itm);
		this.file_name = Bld_file_name(dump_abrv, dump_date, dump_type_str);
	}
	public Xow_domain_itm Domain_itm()	{return domain_itm;} private final Xow_domain_itm domain_itm;	// EX: en.wikipedia.org
	public byte[] Dump_abrv()			{return dump_abrv;} private final byte[] dump_abrv;				// EX: enwiki
	public String Dump_date()			{return dump_date;} private String dump_date;						// EX: 20150807
	public String Dump_type_str()		{return dump_type_str;} private final String dump_type_str;		// EX: pages-articles
	public String Server_url()			{return server_url;} private String server_url;						// EX: https://dumps.wikimedia.org
	public String File_url()			{return file_url;} private String file_url;							// EX: https://dumps.wikimedia.org/enwiki/20150807/enwiki-20150807-pages-articles.xml.bz2
	public String File_name()			{return file_name;} private String file_name;						// EX: enwiki-20150807-pages-articles.xml.bz2
	public long File_len()				{return file_len;} private long file_len;							// EX: 10 GB
	public DateAdp File_modified()		{return file_modified;} private DateAdp file_modified;				// EX: 2015-08-10 20:12:34 
	public void Dump_date_(String v) {dump_date = v;}
	public void Server_url_(String server_url) {
		this.server_url = server_url;
		String dump_dir_url = String_.new_u8(Xowm_dump_file_.Bld_dump_dir_url(Bry_.new_u8(server_url), dump_abrv, Bry_.new_u8(dump_date)));
		this.file_url = dump_dir_url + file_name;
	}
	public boolean Connect() {
		IoEngine_xrg_downloadFil args = Io_mgr.Instance.DownloadFil_args("", Io_url_.Empty);
		boolean rv = Connect_exec(args, file_url);
		// WMF changed dumping approach to partial dumps; this sometimes causes /latest/ to be missing page_articles; try to get earlier dump; DATE:2015-07-09
		if (	!rv	// not found
			&&	String_.In(server_url, Xowm_dump_file_.Server_wmf_http, Xowm_dump_file_.Server_wmf_https)	// server is dumps.wikimedia.org
			&&	String_.Eq(dump_date, Xowm_dump_file_.Date_latest)	// request dump was latest
			) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "wmf.dump:latest not found; url=~{0}", file_url);
			byte[] abrv_wm_bry = Xow_abrv_wm_.To_abrv(domain_itm);
			String new_dump_root = Xowm_dump_file_.Server_wmf_https + String_.new_u8(abrv_wm_bry) + "/";	// EX: http://dumps.wikimedia.org/enwiki/
			byte[] wiki_dump_dirs_src = args.Exec_as_bry(new_dump_root);
			if (wiki_dump_dirs_src == null) {Xoa_app_.Usr_dlg().Warn_many("", "", "could not connect to dump server; url=~{0}", new_dump_root); return false;}
			String[] dates = gplx.xowa.bldrs.wms.dump_pages.Xowmf_wiki_dump_dirs_parser.Parse(domain_itm.Domain_bry(), wiki_dump_dirs_src);
			int dates_len = dates.length;
			for (int i = dates_len - 1; i > -1; --i) {
				String new_dump_date = dates[i];
				if (String_.Eq(new_dump_date, Xowm_dump_file_.Date_latest)) continue;	// skip latest; assume it is bad
				String new_dump_file = String_.Replace(file_name, Xowm_dump_file_.Date_latest, new_dump_date);	// replace "-latest-" with "-20150602-";
				String new_file_url = new_dump_root + new_dump_date + "/" + new_dump_file;
				rv = Connect_exec(args, new_file_url);
				if (rv) {
					Xoa_app_.Usr_dlg().Note_many("", "", "wmf.dump:dump found; url=~{0}", new_file_url);
					dump_date = new_dump_date;
					file_name = new_dump_file;
					file_url = new_file_url;
					break;
				}
				else
					Xoa_app_.Usr_dlg().Warn_many("", "", "wmf.dump:dump not found; url=~{0}", new_file_url);
			}
		}
		return rv;
	}
	private boolean Connect_exec(IoEngine_xrg_downloadFil args, String cur_file_url) {			
		boolean rv = args.Src_last_modified_query_(true).Exec_meta(cur_file_url);
		long tmp_file_len = args.Src_content_length();
		DateAdp tmp_file_modified = args.Src_last_modified();
		Xoa_app_.Usr_dlg().Note_many("", "", "wmf.dump:connect rslts; url=~{0} result=~{1} fil_len=~{2} file_modified=~{3} server_url=~{4} dump_date=~{5}", cur_file_url, rv, tmp_file_len, tmp_file_modified == null ? "<<NULL>>" : tmp_file_modified.XtoStr_fmt_yyyy_MM_dd_HH_mm_ss(), server_url, dump_date);
		if (rv) {
			if (tmp_file_modified != null && tmp_file_modified.Year() <= 1970) return false;	// url has invalid file; note that dumps.wikimedia.org currently returns back an HTML page with "404 not found"; rather than try to download and parse this (since content may change), use the date_modified which always appears to be UnixTime 0; DATE:2015-07-21
			file_len = tmp_file_len;
			file_modified = tmp_file_modified;
		}
		return rv;
	}
	private static String Bld_file_name(byte[] dump_abrv, String dump_date, String dump_type_str) {
		byte[] dump_type_bry = Bry_.new_u8(dump_type_str);
		int dump_type_int = Xowm_dump_type_.parse_by_file(dump_type_bry);
		byte[] dump_file_ext = Xowm_dump_file_.Ext_xml_bz2;
		switch (dump_type_int) {
			case Xowm_dump_type_.Int__page_props: case Xowm_dump_type_.Int__categorylinks: case Xowm_dump_type_.Int__image: case Xowm_dump_type_.Int__pagelinks:
				dump_file_ext = Xowm_dump_file_.Ext_sql_gz;
				break;
		}
		return String_.new_u8(Xowm_dump_file_.Bld_dump_file_name(dump_abrv, Bry_.new_u8(dump_date), dump_type_bry, dump_file_ext));
	}
}
