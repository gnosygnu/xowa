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
package gplx.dbs.engines.mems; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
public class Mem_row implements Gfo_invk {
	private final    Ordered_hash hash = Ordered_hash_.New();
	private final    Ordered_hash flds = Ordered_hash_.New();
	public int		Len() {return hash.Len();}
	public String	Fld_at(int i) {return (String)flds.Get_at(i);}
	public Object	Get_at(int i) {return hash.Get_at(i);}
	public Object	Get_by(String key) {return hash.Get_by(key);}
	public Object	Get_by_or_dbnull(String key) {
		Object rv = hash.Get_by(key);
		return rv == null ? Db_null.Instance : rv;
	}
	public void		Set_by(String key, Object val)		{hash.Add_if_dupe_use_nth(key, val); flds.Add_if_dupe_use_1st(key, key);}
	public void		Add(String key, Object val)			{hash.Add(key, val); flds.Add_if_dupe_use_1st(key, key);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		Object rv = Get_by(k);
		if (rv == null) return Gfo_invk_.Rv_unhandled;
		return rv;
	}
	public static final    Mem_row[] Ary_empty = new Mem_row[0];
        public static final    Mem_row Null_row = new Mem_row();
}
