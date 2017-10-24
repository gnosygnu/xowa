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
public class Dbmeta_tbl_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New();
	private final    Dbmeta_reload_cmd load_cmd;
	public Dbmeta_tbl_mgr(Dbmeta_reload_cmd load_cmd) {this.load_cmd = load_cmd;}
	public int				Len()					{return hash.Count();}
	public boolean			Has(String name)		{return hash.Has(name);}
	public Dbmeta_tbl_itm	Get_at(int i)			{return (Dbmeta_tbl_itm)hash.Get_at(i);}
	public Dbmeta_tbl_itm	Get_by(String name)		{return (Dbmeta_tbl_itm)hash.Get_by(name);}
	public void				Add(Dbmeta_tbl_itm itm)	{hash.Add_if_dupe_use_nth(itm.Name(), itm);}
	public void				Clear()					{hash.Clear();}
	public Dbmeta_tbl_mgr	Load_all()				{load_cmd.Load_all(); return this;}
}
