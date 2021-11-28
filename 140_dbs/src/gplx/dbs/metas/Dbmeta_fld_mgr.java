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
package gplx.dbs.metas; import gplx.*; import gplx.dbs.*;
public class Dbmeta_fld_mgr {
	private final Ordered_hash hash = Ordered_hash_.New();
	public int				Len()					{return hash.Count();}
	public void				Clear()					{hash.Clear();}
	public void				Add(Dbmeta_fld_itm itm)	{hash.Add(itm.Name(), itm);}
	public boolean				Has(String name)		{return hash.Has(name);}
	public Dbmeta_fld_itm	Get_at(int idx)			{return (Dbmeta_fld_itm)hash.Get_at(idx);}
	public Dbmeta_fld_itm	Get_by(String name)		{return (Dbmeta_fld_itm)hash.Get_by(name);}
	public Dbmeta_fld_itm[]	To_ary()				{return hash.Count() == 0 ? Dbmeta_fld_itm.Ary_empty : (Dbmeta_fld_itm[])hash.To_ary(Dbmeta_fld_itm.class);}
	public Dbmeta_fld_list	To_fld_list() {
		Dbmeta_fld_list rv = new Dbmeta_fld_list();
		int len = hash.Count();
		for (int i = 0; i < len; ++i)
			rv.Add(Get_at(i));
		return rv;
	}
}
