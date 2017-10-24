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
package gplx.dbs.sqls.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
public class Sql_select_fld_list {
	private final Ordered_hash hash = Ordered_hash_.New();
	public int Len()						{return hash.Count();}
	public Sql_select_fld_list Clear()		{hash.Clear();return this;}
	public Sql_select_fld Get_at(int i)		{return (Sql_select_fld)hash.Get_at(i);}
	public void Add(Sql_select_fld fld)		{hash.Add(fld.Alias, fld);}
}
