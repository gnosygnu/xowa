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
package gplx.xowa; import gplx.*;
import gplx.dbs.*;
import gplx.core.net.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.bldrs.infos.*;
public class Xoa_test_ {
	public static boolean Db_skip()			{return Bool_.N;}
	public static boolean Db_is_mem_dflt()		{return Bool_.Y;}
	public static void Db_init(Io_url sqlite_url) {Db_init(Db_is_mem_dflt(), sqlite_url);}
	public static void Init__db__edit(Xowe_wiki wiki) {
		Db__init__mem();
		wiki.Db_mgr_create_as_sql();
		wiki.Data__core_mgr().Init_by_make(Xowd_core_db_props.Test, Xob_info_session.Test);
	}
	public static void Init__db__view(gplx.xowa.wikis.Xowv_wiki wiki) {
		Db__init__mem();
		wiki.Init_by_make(Xowd_core_db_props.Test, Xob_info_session.Test);
//			wiki.Data__core_mgr().Init_by_make(Xowd_core_db_props.Test, Xob_info_session.Test);
	}
	public static void Db__init__mem() {
		Io_mgr.Instance.InitEngine_mem();
		Db_conn_bldr.Instance.Reg_default_mem();
	}
	public static void Db_init(boolean db_is_mem, Io_url sqlite_url) {
		if (db_is_mem)
			Db__init__mem();
		else {
			Io_mgr.Instance.DeleteDirDeep(sqlite_url);
			Db_conn_bldr.Instance.Reg_default_sqlite();
		}
	}
	public static void Inet__init() {
		Gfo_inet_conn_.new_prototype_(Gfo_inet_conn_.Tid__mem__hash);
	}
	public static Io_url Url_root()			{return Io_url_.Usr().GenSubDir_nest("xowa", "dev", "tst", "400_xowa");}
	public static Io_url Url_wiki_enwiki()	{return Url_root().GenSubDir_nest("root", "wiki", "en.wikipedia.org");}
	public static Io_url Url_file_enwiki()	{return Url_root().GenSubDir_nest("root", "file", "en.wikipedia.org");}
}
