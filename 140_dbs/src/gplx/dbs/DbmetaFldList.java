/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.dbs;
import gplx.Hash_adp;
import gplx.Hash_adp_;
import gplx.List_adp;
import gplx.List_adp_;
import gplx.objects.lists.GfoIndexedList;
public class DbmetaFldList {
	private final GfoIndexedList<String, DbmetaFldItm> flds = new GfoIndexedList<>();
	public void Clear()                                            {flds.Clear();}
	public int Len()                                               {return flds.Len();}
	public boolean Has(String key)                                 {return flds.Has(key);}
	public DbmetaFldItm GetByOrNull(String name)                   {return flds.GetByOrNull(name);}
	public DbmetaFldItm GetAt(int idx)                             {return flds.GetAt(idx);}
	public void Del(String key)                                    {flds.DelBy(key);}
	public void AddAt(int pos, DbmetaFldItm fld)                   {flds.AddAt(pos, fld.Name(), fld);}
	public String AddBool(String name)                             {return Add(DbmetaFldItm.NewBool(name));}
	public String AddByte(String name)                             {return Add(DbmetaFldItm.NewByte(name));}
	public String AddShort(String name)                            {return Add(DbmetaFldItm.NewShort(name));}
	public String AddInt(String name)                              {return Add(DbmetaFldItm.NewInt(name));}
	public String AddIntPkey(String name)                          {return Add(DbmetaFldItm.NewInt(name).PrimarySetY());}
	public String AddIntPkeyAutonum(String name)                   {return Add(DbmetaFldItm.NewInt(name).PrimarySetY().AutonumSetY());}
	public String AddIntDflt(String name, int dflt)                {return Add(DbmetaFldItm.NewInt(name).DefaultValSet(dflt));}
	public String AddLong(String name)                             {return Add(DbmetaFldItm.NewLong(name));}
	public String AddLongNull(String name)                         {return Add(DbmetaFldItm.NewLong(name).NullableSetNull());}
	public String AddFloat(String name)                            {return Add(DbmetaFldItm.NewFloat(name));}
	public String AddDouble(String name)                           {return Add(DbmetaFldItm.NewDouble(name));}
	public String AddText(String name)                             {return Add(DbmetaFldItm.NewText(name));}
	public String AddBry(String name)                              {return Add(DbmetaFldItm.NewBry(name));}
	public String AddStr(String name, int len)                     {return Add(DbmetaFldItm.NewStr(name, len));}
	public String AddDate(String name)                             {return Add(DbmetaFldItm.NewStr(name, 32));}
	public String AddStrPkey(String name, int len)                 {return Add(DbmetaFldItm.NewStr(name, len).PrimarySetY());}
	public String AddStrNull(String name, int len)                 {return Add(DbmetaFldItm.NewStr(name, len).NullableSetNull());}
	public String AddStrDflt(String name, int len, String dflt)    {return Add(DbmetaFldItm.NewStr(name, len).DefaultValSet(dflt));}
	public DbmetaFldList BldInt(String name)                     {AddInt(name); return this;}
	public DbmetaFldList BldStr(String name)                     {return BldStr(name, 255);}
	public DbmetaFldList BldStr(String name, int len)            {AddStr(name, len); return this;}
	public String Add(DbmetaFldItm fld) {
		String name = fld.Name();
		flds.Add(name, fld);
		return name;
	}

	public DbmetaFldList Clone() {
		DbmetaFldList rv = new DbmetaFldList();
		int len = this.Len();
		for (int i = 0; i < len; ++i)
			rv.Add(this.GetAt(i));
		return rv;
	}
	public DbmetaFldItm[] ToFldAry() {return flds.ToAry(DbmetaFldItm.class);}
	public String[] ToStrAry() {
		int len = flds.Len();
		String[] strAry = new String[len];
		for (int i = 0; i < len; ++i) {
			DbmetaFldItm fld = (DbmetaFldItm)flds.GetAt(i);
			strAry[i] = fld.Name();
		}
		return strAry;
	}
	public String[] ToStrAryWoAutonum()    {
		int len = flds.Len();
		List_adp rv = List_adp_.New();
		for (int i = 0; i < len; ++i) {
			DbmetaFldItm fld = (DbmetaFldItm)flds.GetAt(i);
			if (fld.Autonum()) continue;
			rv.Add(fld.Name());
		}
		return (String[])rv.ToAry(String.class);
	} 
	public String[] ToStrAryExclude(String[] ary) {
		Hash_adp ary_hash = Hash_adp_.New();
		List_adp rv = List_adp_.New();
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; ++i) {
			String ary_itm = ary[i];
			ary_hash.Add(ary_itm, ary_itm);
		}
		int fld_len = flds.Len();
		for (int i = 0; i < fld_len; ++i) {
			DbmetaFldItm fld = (DbmetaFldItm)flds.GetAt(i);
			String fld_key = fld.Name();
			if (ary_hash.Has(fld_key)) continue;
			rv.Add(fld_key);
		}
		return rv.ToStrAry();
	}
}
