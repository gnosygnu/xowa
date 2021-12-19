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
public class Dbmeta_idx_mgr {
	private final Ordered_hash hash = Ordered_hash_.New();
	public int				Len()					{return hash.Len();}
	public boolean				Has(String name)		{return hash.Has(name);}
	public Dbmeta_idx_itm	Get_at(int idx)			{return (Dbmeta_idx_itm)hash.GetAt(idx);}
	public Dbmeta_idx_itm	Get_by(String name)		{return (Dbmeta_idx_itm)hash.GetByOrNull(name);}
	public void				Add(Dbmeta_idx_itm itm)	{hash.Add(itm.Name(), itm);}
	public void				Clear()					{hash.Clear();}
	public Dbmeta_idx_itm[]	To_ary()				{return (Dbmeta_idx_itm[])hash.ToAry(Dbmeta_idx_itm.class);}
}
