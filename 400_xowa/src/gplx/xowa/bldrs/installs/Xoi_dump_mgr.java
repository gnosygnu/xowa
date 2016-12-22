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
//		public String[]		Server_urls()				{return server_urls;}			private String[] server_urls = String_.Ary(Xowm_dump_file_.Server_your_org, Xowm_dump_file_.Server_wmf_https, Xowm_dump_file_.Server_c3sl, Xowm_dump_file_.Server_masaryk);	// promote your.org to primary url; DATE:2016-08-07
//		public void Init_by_app(Xoa_app app) {
//			app.Cfg().Bind_many_app(this, Cfg__server_urls);
//		}
//		public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
//			if		(ctx.Match(k, Cfg__server_urls))						server_urls = m.ReadStrAryIgnore("v", ",", "\n");
//			else	return Gfo_invk_.Rv_unhandled;
//			return this;
//		}
//		private static final String 
//		  Cfg__server_urls					= "xowa.wiki.import.dump_servers"
//		;

	public static boolean Import_bz2_by_stdout(Xoa_app app)	{return app.Cfg().Get_bool_app_or("xowa.wiki.import.bz2.stdout_enabled", true);}	// CFG: Cfg__
	public static String[] Server_urls(Xoa_app app)	{
		String[] or = String_.Ary(Xowm_dump_file_.Server_your_org, Xowm_dump_file_.Server_wmf_https, Xowm_dump_file_.Server_c3sl, Xowm_dump_file_.Server_masaryk);	// promote your.org to primary url; DATE:2016-08-07
		return app.Cfg().Get_strary_app_or("xowa.wiki.import.dump_servers", ",", or);	// CFG: Cfg__
	}
}
