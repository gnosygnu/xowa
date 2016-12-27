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
package gplx.xowa.bldrs.installs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.xowa.bldrs.wms.dumps.*;
public class Xoi_dump_mgr {
	public static boolean Import_bz2_by_stdout(Xoa_app app)	{return app.Cfg().Get_bool_app_or("xowa.bldr.import.apps.bz2_stdout.enabled", true);}	// CFG: Cfg__
	public static String[] Server_urls(Xoa_app app)	{
		String[] or = String_.Ary(Xowm_dump_file_.Server_your_org, Xowm_dump_file_.Server_wmf_https, Xowm_dump_file_.Server_c3sl, Xowm_dump_file_.Server_masaryk);	// promote your.org to primary url; DATE:2016-08-07
		return app.Cfg().Get_strary_app_or("xowa.bldr.import.dump_servers", ",", or);	// CFG: Cfg__
	}
}
