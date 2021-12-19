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
package gplx.xowa.wikis.dbs;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.GfsCtx;
import gplx.libs.files.Io_mgr;
import gplx.types.commons.GfoDate;
import gplx.libs.files.Io_url;
import gplx.xowa.*;
import gplx.core.ios.streams.*;
import gplx.xowa.apps.gfs.*;
import gplx.xowa.addons.wikis.ctgs.*;
import gplx.xowa.wikis.data.*;
public class Xodb_mgr_sql implements Xodb_mgr, Gfo_invk {
	private final Xowe_wiki wiki;
	public Xodb_mgr_sql(Xowe_wiki wiki) {
		this.wiki = wiki;
		this.core_data_mgr = new Xow_db_mgr(wiki.Fsys_mgr().Root_dir(), wiki.Domain_str());
		this.load_mgr = new Xodb_load_mgr_sql(this);
		this.save_mgr = new Xodb_save_mgr_sql(this);
	}
	public byte Tid() {return Tid_sql;} public String Tid_name() {return "sqlite3";} public static final byte Tid_sql = 1;		
	public Xow_db_mgr Core_data_mgr() {return core_data_mgr;} private final Xow_db_mgr core_data_mgr;
	public Xodb_load_mgr Load_mgr() {return load_mgr;} private final Xodb_load_mgr_sql load_mgr;
	public Xodb_save_mgr Save_mgr() {return save_mgr;} private final Xodb_save_mgr_sql save_mgr;
	public byte Category_version() {return category_version;} private byte category_version = Xoa_ctg_mgr.Version_null;
	public GfoDate Dump_date_query() {
		GfoDate rv = wiki.Props().Modified_latest(); if (rv != null) return rv;
		Io_url url = core_data_mgr.Db__core().Url();
		return Io_mgr.Instance.QueryFil(url).ModifiedTime();
	}
	public void Category_version_update(boolean version_is_1) {
		String grp = Xowd_cfg_key_.Grp__wiki_init;
		String key = Xoa_gfs_wtr_.Write_func_chain(Xowe_wiki.Invk_db_mgr, Xodb_mgr_sql.Invk_category_version);
		core_data_mgr.Tbl__cfg().Delete_val(grp, key);// always delete ctg version
		category_version = version_is_1 ? Xoa_ctg_mgr.Version_1 : Xoa_ctg_mgr.Version_2;
		core_data_mgr.Tbl__cfg().Insert_byte(grp, key, category_version);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_data_storage_format))				return Io_stream_tid_.Obsolete_to_str(core_data_mgr.Props().Zip_tid_text());
		else if	(ctx.Match(k, Invk_data_storage_format_))				{}	// SERIALIZED:000.sqlite3|xowa_cfg; ignore; read from Xow_db_props
		else if	(ctx.Match(k, Invk_category_version))					return category_version;
		else if	(ctx.Match(k, Invk_category_version_))					category_version = m.ReadByte("v");
		else if	(ctx.Match(k, Invk_search_version))						{return 1;} // return this.Search_version(); // REMOVED: DATE:2016-02-26
		else if	(ctx.Match(k, Invk_tid_name))							return this.Tid_name();
		return this;
	}
	public static final String 
	  Invk_data_storage_format = "data_storage_format", Invk_data_storage_format_ = "data_storage_format_"	// SERIALIZED:000.sqlite3|xowa_cfg
	, Invk_category_version = "category_version", Invk_category_version_ = "category_version_"				// SERIALIZED:000.sqlite3|xowa_cfg
	, Invk_search_version = "search_version"
	, Invk_tid_name = "tid_name"
	;
}
