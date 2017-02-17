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
