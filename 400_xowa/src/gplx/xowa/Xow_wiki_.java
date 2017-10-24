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
import gplx.core.ios.streams.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.nss.*;
import gplx.xowa.bldrs.infos.*;
import gplx.xowa.langs.*;
public class Xow_wiki_ {
	public static void Create_sql_backend(Xow_wiki wiki, gplx.xowa.wikis.data.Xowd_core_db_props core_db_props, gplx.xowa.bldrs.infos.Xob_info_session session) {
		if (wiki.Type_is_edit()) {
			Xowe_wiki wikie = (Xowe_wiki)wiki;
			wikie.Db_mgr_create_as_sql();	// edit-wikis are created with text-backend; convert to sql
			wiki.Data__core_mgr().Init_by_make(core_db_props, session);	// make core_db			
		}
		else {
			Xowv_wiki wikiv = (Xowv_wiki)wiki;
			wikiv.Init_by_make(core_db_props, session);	// make core_db			
		}
	}
}
