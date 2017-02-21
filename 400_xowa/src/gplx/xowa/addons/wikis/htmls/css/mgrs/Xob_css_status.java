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
package gplx.xowa.addons.wikis.htmls.css.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.htmls.*; import gplx.xowa.addons.wikis.htmls.css.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.bldrs.css.*;
public class Xob_css_status {
	public int Update_tid() {return update_tid;} private int update_tid;
	public boolean Fs_exists() {return fs_exists;} private boolean fs_exists;
	public Io_url Fs_dir() {return fs_dir;} private Io_url fs_dir;
	public boolean Db_exists() {return db_exists;} private boolean db_exists;
	public void Fs_exists_(boolean fs_exists, Io_url fs_dir) {
		this.fs_exists = fs_exists;
		this.fs_dir = fs_dir;
	}
	public void Db_exists_(boolean db_exists) {
		this.db_exists = db_exists;
	}
	public void Update_tid_none_y_()	{this.update_tid = Update_tid_none;}
	public void Update_tid_wmf_y_()		{this.update_tid = Update_tid_wmf;}
	public void Update_tid_db_y_()		{this.update_tid = Update_tid_db;}
	public static final int Update_tid_none = 0, Update_tid_db = 1, Update_tid_wmf = 2;
	public static Xob_css_status Chk(Xow_wiki wiki, Io_url css_dir, String key) {
		Xob_css_status rv = new Xob_css_status();
		Chk_fs(rv, wiki);
		Chk_db(rv, wiki, css_dir);
		return rv;
	}
	private static void Chk_fs(Xob_css_status rv, Xow_wiki wiki) {
		Io_url css_dir		= wiki.App().Fsys_mgr().Wiki_css_dir(wiki.Domain_str());	// EX: /xowa/user/anonymous/wiki/en.wikipedia.org/html/
		Io_url css_fil_wiki = css_dir.GenSubFil(Xoa_css_extractor.Css_wiki_name);		// EX: /xowa/user/anonymous/wiki/en.wikipedia.org/html/xowa_wiki.css
		boolean exists = Io_mgr.Instance.ExistsFil(css_fil_wiki);
		rv.Fs_exists_(exists, css_dir);
	}
	private static void Chk_db(Xob_css_status rv, Xow_wiki wiki, Io_url css_dir) {
		Xow_db_mgr core_db_mgr = wiki.Data__core_mgr();
		if (	core_db_mgr == null
			||	core_db_mgr.Props() != null
			||	!core_db_mgr.Props().Schema_is_1()
			||	core_db_mgr.Tbl__cfg().Select_yn_or(Xowd_cfg_key_.Grp__wiki_schema, Xow_db_file_schema_props.Key__tbl_css_core, Bool_.N)
			) {
			rv.Db_exists_(false);
			if (rv.Fs_exists())
				rv.Update_tid_none_y_();	// v1_db and fs_exists; don't do update; legacy behavior
			else
				rv.Update_tid_wmf_y_();		// v1_db and fs_missing; update from wmf; legacy behavior
		}
		if (rv.Fs_exists()) {
			DateAdp fs_timestamp = Timestamp_load(css_dir);
			DateAdp db_timestamp = Datetime_now.Get();
			if (db_timestamp.compareTo(fs_timestamp) == CompareAble_.More)
				rv.Update_tid_db_y_();		// v2_db and later_version; update from db
			else
				rv.Update_tid_none_y_();	// v2_db and current version; noop
		}
	}
	public static void Timestamp_save(Io_url css_dir, DateAdp time) {
		Io_mgr.Instance.SaveFilStr(css_dir.GenSubFil(Timestamp_filename), time.XtoStr_fmt_yyyyMMdd_HHmmss());
	}
	public static DateAdp Timestamp_load(Io_url css_dir) {
		String rv = Io_mgr.Instance.LoadFilStr(css_dir.GenSubFil(Timestamp_filename));
		return rv == null ? DateAdp_.MinValue : DateAdp_.parse_iso8561_or(rv, DateAdp_.MinValue);
	}
	private static final String Timestamp_filename = "xowa.css.timestamp.txt";
}
