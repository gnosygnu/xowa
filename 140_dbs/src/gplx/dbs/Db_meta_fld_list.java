/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.dbs; import gplx.*;
public class Db_meta_fld_list {
	private final OrderedHash flds = OrderedHash_.new_();
	private final ListAdp keys = ListAdp_.new_();
	public void Clear() {flds.Clear(); keys.Clear();}
	public Db_meta_fld Get_by(String name) {return (Db_meta_fld)flds.Fetch(name);}
	public String[] To_str_ary()			{if (str_ary == null) str_ary = (String[])keys.Xto_ary(String.class); return str_ary;} private String[] str_ary;
	public Db_meta_fld[] To_fld_ary()		{if (fld_ary == null) fld_ary = (Db_meta_fld[])flds.Xto_ary(Db_meta_fld.class); return fld_ary;} private Db_meta_fld[] fld_ary;
	public String[] To_str_ary_wo_autonum()	{
		int len = flds.Count();
		ListAdp rv = ListAdp_.new_();
		for (int i = 0; i < len; ++i) {
			Db_meta_fld fld = (Db_meta_fld)flds.FetchAt(i);
			if (fld.Autonum()) continue;
			rv.Add(fld.Name());
		}
		return (String[])rv.Xto_ary(String.class);
	} 
	public String[] To_str_ary_exclude(String[] ary) {
		HashAdp ary_hash = HashAdp_.new_();
		ListAdp rv = ListAdp_.new_();
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; ++i) {
			String ary_itm = ary[i];
			ary_hash.Add(ary_itm, ary_itm);
		}
		int fld_len = flds.Count();
		for (int i = 0; i < fld_len; ++i) {
			Db_meta_fld fld = (Db_meta_fld)flds.FetchAt(i);
			String fld_key = fld.Name();
			if (ary_hash.Has(fld_key)) continue;
			rv.Add(fld_key);
		}
		return rv.XtoStrAry();
	}
	public boolean Has(String key)							{return flds.Has(key);}
	public String Add_bool(String name)					{return Add(name, Db_meta_fld.Tid_bool,		Len_null, Bool_.N, Bool_.N, Bool_.N, Db_meta_fld.Default_value_null);}
	public String Add_byte(String name)					{return Add(name, Db_meta_fld.Tid_byte,		Len_null, Bool_.N, Bool_.N, Bool_.N, Db_meta_fld.Default_value_null);}
	public String Add_short(String name)				{return Add(name, Db_meta_fld.Tid_short,	Len_null, Bool_.N, Bool_.N, Bool_.N, Db_meta_fld.Default_value_null);}
	public String Add_int(String name)					{return Add(name, Db_meta_fld.Tid_int,		Len_null, Bool_.N, Bool_.N, Bool_.N, Db_meta_fld.Default_value_null);}
	public String Add_int_pkey(String name)				{return Add(name, Db_meta_fld.Tid_int,		Len_null, Bool_.N, Bool_.Y, Bool_.N, Db_meta_fld.Default_value_null);}
	public String Add_int_pkey_autonum(String name)		{return Add(name, Db_meta_fld.Tid_int,		Len_null, Bool_.N, Bool_.Y, Bool_.Y, Db_meta_fld.Default_value_null);}
	public String Add_int_autonum(String name)			{return Add(name, Db_meta_fld.Tid_int,		Len_null, Bool_.N, Bool_.Y, Bool_.N, Db_meta_fld.Default_value_null);}
	public String Add_int_dflt(String name, int dflt)	{return Add(name, Db_meta_fld.Tid_int,		Len_null, Bool_.N, Bool_.N, Bool_.N, dflt);}
	public String Add_long(String name)					{return Add(name, Db_meta_fld.Tid_long,		Len_null, Bool_.N, Bool_.N, Bool_.N, Db_meta_fld.Default_value_null);}
	public String Add_float(String name)				{return Add(name, Db_meta_fld.Tid_float,	Len_null, Bool_.N, Bool_.N, Bool_.N, Db_meta_fld.Default_value_null);}
	public String Add_double(String name)				{return Add(name, Db_meta_fld.Tid_double,	Len_null, Bool_.N, Bool_.N, Bool_.N, Db_meta_fld.Default_value_null);}
	public String Add_str(String name, int len)			{return Add(name, Db_meta_fld.Tid_str,		     len, Bool_.N, Bool_.N, Bool_.N, Db_meta_fld.Default_value_null);}
	public String Add_str_pkey(String name, int len)	{return Add(name, Db_meta_fld.Tid_str,		     len, Bool_.N, Bool_.Y, Bool_.N, Db_meta_fld.Default_value_null);}
	public String Add_str_null(String name, int len)	{return Add(name, Db_meta_fld.Tid_str,		     len, Bool_.Y, Bool_.N, Bool_.N, Db_meta_fld.Default_value_null);}
	public String Add_str_dflt(String name, int len, String dflt)
														{return Add(name, Db_meta_fld.Tid_str,		     len, Bool_.N, Bool_.N, Bool_.N, dflt);}
	public String Add_text(String name)					{return Add(name, Db_meta_fld.Tid_text,		Len_null, Bool_.N, Bool_.N, Bool_.N, Db_meta_fld.Default_value_null);}
	public String Add_bry(String name)					{return Add(name, Db_meta_fld.Tid_bry,		Len_null, Bool_.N, Bool_.N, Bool_.N, Db_meta_fld.Default_value_null);}
	public String Add(String name, int tid, int len, boolean nullable, boolean primary, boolean autoincrement, Object default_value) {
		Db_meta_fld fld = new Db_meta_fld(name, tid, len, nullable, primary, autoincrement, default_value);
		Add_itm(fld);
		return name;
	}
	public void Add_itm(Db_meta_fld fld) {
		String name = fld.Name();
		flds.Add(name, fld);
		keys.Add(name);
	}
	private static final int Len_null = Db_meta_fld.Len_null;
	public static Db_meta_fld_list new_() {return new Db_meta_fld_list();}
}
