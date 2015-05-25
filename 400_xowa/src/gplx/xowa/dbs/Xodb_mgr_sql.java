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
package gplx.xowa.dbs; import gplx.*; import gplx.xowa.*;
import gplx.ios.*; import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.dbs.engines.sqlite.*;
import gplx.xowa.apps.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.ctgs.*; import gplx.xowa.html.hdumps.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
public class Xodb_mgr_sql implements Xodb_mgr, GfoInvkAble {
	public Xodb_mgr_sql(Xowe_wiki wiki) {
		this.wiki = wiki;
		this.core_data_mgr = new Xowd_db_mgr(wiki.Fsys_mgr().Root_dir(), wiki.Domain_itm());
		this.load_mgr = new Xodb_load_mgr_sql(this, core_data_mgr);
		this.save_mgr = new Xodb_save_mgr_sql(this, wiki.Page_mgr());
	}
	public byte Tid() {return Tid_sql;} public String Tid_name() {return "sqlite3";} public static final byte Tid_sql = 1;		
	public Xowd_db_mgr Core_data_mgr() {return core_data_mgr;} private final Xowd_db_mgr core_data_mgr;
	public Xowe_wiki Wiki() {return wiki;} private final Xowe_wiki wiki;
	public Xodb_load_mgr Load_mgr() {return load_mgr;} private final Xodb_load_mgr_sql load_mgr;
	public Xodb_save_mgr Save_mgr() {return save_mgr;} private final Xodb_save_mgr_sql save_mgr;
	public byte Category_version() {return category_version;} private byte category_version = Xoa_ctg_mgr.Version_null;
	public byte Search_version() {return load_mgr.Search_version();} public void Search_version_refresh() {load_mgr.Search_version_refresh();}
	public DateAdp Dump_date_query() {
		DateAdp rv = wiki.Props().Modified_latest(); if (rv != null) return rv;
		Io_url url = core_data_mgr.Db__core().Url();
		return Io_mgr.I.QueryFil(url).ModifiedTime();
	}
	public void Category_version_update(boolean version_is_1) {
		String grp = Xow_cfg_consts.Grp_wiki_init;
		String key = Xoa_gfs_mgr.Build_code(Xowe_wiki.Invk_db_mgr, Xodb_mgr_sql.Invk_category_version);
		core_data_mgr.Tbl__cfg().Delete_val(grp, key);// always delete ctg version
		category_version = version_is_1 ? Xoa_ctg_mgr.Version_1 : Xoa_ctg_mgr.Version_2;
		core_data_mgr.Tbl__cfg().Insert_byte(grp, key, category_version);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_data_storage_format))				return Io_stream_.Obsolete_to_str(core_data_mgr.Props().Zip_tid_text());
		else if	(ctx.Match(k, Invk_data_storage_format_))				{}	// SERIALIZED:000.sqlite3|xowa_cfg; ignore; read from Xow_db_props
		else if	(ctx.Match(k, Invk_category_version))					return category_version;
		else if	(ctx.Match(k, Invk_category_version_))					category_version = m.ReadByte("v");
		else if	(ctx.Match(k, Invk_search_version))						return this.Search_version();
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
