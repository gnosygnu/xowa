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
package gplx.dbs.metas; import gplx.dbs.*;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
public class Dbmeta_fld_mgr {
	private final Ordered_hash hash = Ordered_hash_.New();
	public int				Len()					{return hash.Len();}
	public void				Clear()					{hash.Clear();}
	public void				Add(DbmetaFldItm itm)	{hash.Add(itm.Name(), itm);}
	public boolean				Has(String name)		{return hash.Has(name);}
	public DbmetaFldItm Get_at(int idx)			{return (DbmetaFldItm)hash.GetAt(idx);}
	public DbmetaFldItm Get_by(String name)		{return (DbmetaFldItm)hash.GetByOrNull(name);}
	public DbmetaFldItm[]	To_ary()				{return hash.Len() == 0 ? DbmetaFldItm.AryEmpty : (DbmetaFldItm[])hash.ToAry(DbmetaFldItm.class);}
	public DbmetaFldList To_fld_list() {
		DbmetaFldList rv = new DbmetaFldList();
		int len = hash.Len();
		for (int i = 0; i < len; ++i)
			rv.Add(Get_at(i));
		return rv;
	}
}
