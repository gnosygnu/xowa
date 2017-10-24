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
package gplx.dbs.conn_props; import gplx.*; import gplx.dbs.*;
public class Db_conn_props_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New();
	public boolean Has(String key) {return hash.Has(key);}
	public boolean Match(String key, String expd_val) {
		String actl_val = (String)hash.Get_by(key);
		return actl_val == null ? false : String_.Eq(expd_val,actl_val);
	}
	public void Add(String key, String val) {hash.Add(key, val);}
	public void Del(String key)				{hash.Del(key);}
}
