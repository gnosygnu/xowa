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
package gplx.xowa.wikis.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*;
public class Xowd_cfg_tbl_ {
	public static final String Tbl_name = "xowa_cfg";
	public static Db_cfg_tbl New(gplx.dbs.Db_conn conn)						{return New(conn, Tbl_name);}
	public static Db_cfg_tbl New(gplx.dbs.Db_conn conn, String tbl_name)	{return new Db_cfg_tbl(conn, tbl_name);}
	public static Db_cfg_tbl Get_or_null(Db_conn conn) {
		return conn.Meta_tbl_exists(Tbl_name) ? new Db_cfg_tbl(conn, Tbl_name) : null;
	}
	public static Db_cfg_tbl Get_or_fail(Db_conn conn) {
		Db_cfg_tbl rv = Get_or_null(conn);
		if (rv == null) throw Err_.New("xowa_cfg tbl does not exist: file={0}", conn.Conn_info().Raw());
		return rv;
	}

	public static void Upsert__import(Xowe_wiki wiki) {
		Db_cfg_tbl cfg_tbl = wiki.Data__core_mgr().Db__core().Tbl__cfg();
		cfg_tbl.Upsert_bry(Xowd_cfg_key_.Grp__wiki_init, Xowd_cfg_key_.Key__init__bldr_version, wiki.Props().Bldr_version());
		cfg_tbl.Upsert_bry(Xowd_cfg_key_.Grp__wiki_init, Xowd_cfg_key_.Key__init__siteinfo_misc, wiki.Props().Siteinfo_misc());
		cfg_tbl.Upsert_bry(Xowd_cfg_key_.Grp__wiki_init, Xowd_cfg_key_.Key__init__siteinfo_mainpage, wiki.Props().Siteinfo_mainpage());
	}
	public static void Upsert__create(Xowe_wiki wiki)     {Upsert__create(wiki.Data__core_mgr().Db__core().Tbl__cfg(), wiki.Domain_str(), wiki.Domain_str(), wiki.Props().Main_page());}
	public static void Upsert__create(Db_cfg_tbl cfg_tbl, String domain, String name, byte[] main_page) {
		cfg_tbl.Upsert_str(Xowd_cfg_key_.Grp__empty    , Xowd_cfg_key_.Key__wiki__core__domain      , domain);
		cfg_tbl.Upsert_str(Xowd_cfg_key_.Grp__empty    , Xowd_cfg_key_.Key__wiki__core__name        , name);
		cfg_tbl.Upsert_int(Xowd_cfg_key_.Grp__empty    , Xowd_cfg_key_.Key__wiki__upgrade__version  , gplx.xowa.addons.wikis.directorys.specials.items.bldrs.Xow_wiki_upgrade_.Upgrade_version__cur);
		cfg_tbl.Upsert_bry(Xowd_cfg_key_.Grp__wiki_init, Xowd_cfg_key_.Key__init__main_page         , main_page);
		cfg_tbl.Upsert_str(Xowd_cfg_key_.Grp__wiki_init, Xowd_cfg_key_.Key__init__modified_latest   , Datetime_now.Get().XtoStr_fmt(DateAdp_.Fmt_iso8561_date_time));
	}
}
