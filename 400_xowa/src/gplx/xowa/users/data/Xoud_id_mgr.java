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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
public class Xoud_id_mgr {
	private Xoud_cfg_mgr cfg_mgr;
	public Xoud_id_mgr(Xoud_cfg_mgr cfg_mgr) {this.cfg_mgr = cfg_mgr;}
	public int	Get_next(String key)			{return cfg_mgr.Select_int_or(Grp_key, key, 1);}
	public void Set_next(String key, int v)		{cfg_mgr.Upsert_int(Grp_key, key, v);}
	public int	Get_next_and_save(String key)	{
		int rv = Get_next(key);
		Set_next(key, rv + 1);
		return rv;
	}
	private static final String Grp_key = "gplx.next_id";
}
