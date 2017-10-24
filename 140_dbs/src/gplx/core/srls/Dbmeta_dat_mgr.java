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
package gplx.core.srls; import gplx.*; import gplx.core.*;
import gplx.dbs.*; import gplx.dbs.metas.*;
public class Dbmeta_dat_mgr {
	private final Ordered_hash hash = Ordered_hash_.New();
	public Dbmeta_dat_mgr Clear() {hash.Clear(); return this;}
	public int Len() {return hash.Count();}
	public Dbmeta_dat_itm Get_at(int idx) {return (Dbmeta_dat_itm)hash.Get_at(idx);}
	public Dbmeta_dat_mgr Add_int(String key, int val) {
		Dbmeta_dat_itm itm = new Dbmeta_dat_itm(Dbmeta_fld_tid.Tid__int, key, val);
		hash.Add(key, itm);
		return this;
	}
}
