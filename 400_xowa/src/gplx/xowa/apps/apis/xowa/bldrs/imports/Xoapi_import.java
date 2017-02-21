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
package gplx.xowa.apps.apis.xowa.bldrs.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.bldrs.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*;
import gplx.xowa.wikis.data.*;
public class Xoapi_import implements Gfo_invk {
	public long		Cat_link_db_max()		{return cat_link_db_max;}		private long cat_link_db_max		= Io_size_.To_long_by_int_mb(1500);		// 3.6 GB; v1
	public String	User_name()				{return user_name;}				private String user_name			= "anonymous";
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_cat_link_db_max)) 					return Io_size_.To_str_mb(cat_link_db_max);
		else if	(ctx.Match(k, Invk_cat_link_db_max_)) 					cat_link_db_max = Io_size_.To_long_by_msg_mb(m, cat_link_db_max);
		else if	(ctx.Match(k, Invk_user_name)) 							return user_name;
		else if	(ctx.Match(k, Invk_user_name_)) 						user_name = m.ReadStr("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_cat_link_db_max		= "cat_link_db_max"		, Invk_cat_link_db_max_		= "cat_link_db_max_"
	, Invk_user_name			= "user_name"			, Invk_user_name_			= "user_name_"
	;
}
