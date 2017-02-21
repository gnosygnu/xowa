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
package gplx.xowa.bldrs.installs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.xowa.bldrs.wms.dumps.*;
public class Xoi_dump_mgr {
	public static boolean Import_bz2_by_stdout(Xoa_app app)	{return app.Cfg().Get_bool_app_or("xowa.bldr.import.apps.bz2_stdout.enabled", true);}	// CFG: Cfg__
	public static String[] Server_urls(Xoa_app app)	{
		String[] or = String_.Ary(Xowm_dump_file_.Server_your_org, Xowm_dump_file_.Server_wmf_https, Xowm_dump_file_.Server_c3sl, Xowm_dump_file_.Server_masaryk);	// promote your.org to primary url; DATE:2016-08-07
		return app.Cfg().Get_strary_app_or("xowa.bldr.import.dump_servers", ",", or);	// CFG: Cfg__
	}
}
