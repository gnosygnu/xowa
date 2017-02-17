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

	public static void Insert__import(Xowe_wiki wiki) {
		Db_cfg_tbl cfg_tbl = wiki.Data__core_mgr().Db__core().Tbl__cfg();
		cfg_tbl.Insert_bry(Xowd_cfg_key_.Grp__wiki_init, Xowd_cfg_key_.Key__init__bldr_version, wiki.Props().Bldr_version());
		cfg_tbl.Insert_bry(Xowd_cfg_key_.Grp__wiki_init, Xowd_cfg_key_.Key__init__siteinfo_misc, wiki.Props().Siteinfo_misc());
		cfg_tbl.Insert_bry(Xowd_cfg_key_.Grp__wiki_init, Xowd_cfg_key_.Key__init__siteinfo_mainpage, wiki.Props().Siteinfo_mainpage());
	}
	public static void Insert__create(Xowe_wiki wiki)     {Insert__create(wiki.Data__core_mgr().Db__core().Tbl__cfg(), wiki.Domain_str(), wiki.Domain_str(), wiki.Props().Main_page());}
	public static void Insert__create(Db_cfg_tbl cfg_tbl, String domain, String name, byte[] main_page) {
		cfg_tbl.Insert_str(Xowd_cfg_key_.Grp__empty    , Xowd_cfg_key_.Key__wiki_core__domain    , domain);
		cfg_tbl.Insert_str(Xowd_cfg_key_.Grp__empty    , Xowd_cfg_key_.Key__wiki_core__name      , name);
		cfg_tbl.Insert_bry(Xowd_cfg_key_.Grp__wiki_init, Xowd_cfg_key_.Key__init__main_page      , main_page);
		cfg_tbl.Insert_str(Xowd_cfg_key_.Grp__wiki_init, Xowd_cfg_key_.Key__init__modified_latest, Datetime_now.Get().XtoStr_fmt(DateAdp_.Fmt_iso8561_date_time));
	}
}
