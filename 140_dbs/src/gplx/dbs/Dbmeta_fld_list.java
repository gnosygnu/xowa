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
public class Dbmeta_fld_list {
	private final Ordered_hash flds = Ordered_hash_.New();
	private final List_adp keys = List_adp_.new_();
	public void Clear() {flds.Clear(); keys.Clear(); str_ary = null; fld_ary = null;}
	public Dbmeta_fld_itm Get_by(String name)	{return (Dbmeta_fld_itm)flds.Get_by(name);}
	public Dbmeta_fld_itm Get_at(int idx)		{return (Dbmeta_fld_itm)flds.Get_at(idx);}
	public String[] To_str_ary()			{if (str_ary == null) str_ary = (String[])keys.To_ary(String.class); return str_ary;} private String[] str_ary;
	public Dbmeta_fld_itm[] To_fld_ary()		{if (fld_ary == null) fld_ary = (Dbmeta_fld_itm[])flds.To_ary(Dbmeta_fld_itm.class); return fld_ary;} private Dbmeta_fld_itm[] fld_ary;
	public String[] To_str_ary_wo_autonum()	{
		int len = flds.Count();
		List_adp rv = List_adp_.new_();
		for (int i = 0; i < len; ++i) {
			Dbmeta_fld_itm fld = (Dbmeta_fld_itm)flds.Get_at(i);
			if (fld.Autonum()) continue;
			rv.Add(fld.Name());
		}
		return (String[])rv.To_ary(String.class);
	} 
	public String[] To_str_ary_exclude(String[] ary) {
		Hash_adp ary_hash = Hash_adp_.new_();
		List_adp rv = List_adp_.new_();
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; ++i) {
			String ary_itm = ary[i];
			ary_hash.Add(ary_itm, ary_itm);
		}
		int fld_len = flds.Count();
		for (int i = 0; i < fld_len; ++i) {
			Dbmeta_fld_itm fld = (Dbmeta_fld_itm)flds.Get_at(i);
			String fld_key = fld.Name();
			if (ary_hash.Has(fld_key)) continue;
			rv.Add(fld_key);
		}
		return rv.To_str_ary();
	}
	public boolean Has(String key)							{return flds.Has(key);}
	public String Add_bool(String name)					{return Add(Dbmeta_fld_itm.new_bool(name));}
	public String Add_byte(String name)					{return Add(Dbmeta_fld_itm.new_byte(name));}
	public String Add_short(String name)				{return Add(Dbmeta_fld_itm.new_short(name));}
	public String Add_int(String name)					{return Add(Dbmeta_fld_itm.new_int(name));}
	public String Add_int_pkey(String name)				{return Add(Dbmeta_fld_itm.new_int(name).Primary_y_());}
	public String Add_int_pkey_autonum(String name)		{return Add(Dbmeta_fld_itm.new_int(name).Primary_y_().Autonum_y_());}
	public String Add_int_autonum(String name)			{return Add(Dbmeta_fld_itm.new_int(name).Autonum_y_());}
	public String Add_int_dflt(String name, int dflt)	{return Add(Dbmeta_fld_itm.new_int(name).Default_(dflt));}
	public String Add_long(String name)					{return Add(Dbmeta_fld_itm.new_long(name));}
	public String Add_float(String name)				{return Add(Dbmeta_fld_itm.new_float(name));}
	public String Add_double(String name)				{return Add(Dbmeta_fld_itm.new_double(name));}
	public String Add_text(String name)					{return Add(Dbmeta_fld_itm.new_text(name));}
	public String Add_bry(String name)					{return Add(Dbmeta_fld_itm.new_bry(name));}
	public String Add_str(String name, int len)			{return Add(Dbmeta_fld_itm.new_str(name, len));}
	public String Add_date(String name)					{return Add(Dbmeta_fld_itm.new_str(name, 32));}
	public String Add_str_pkey(String name, int len)	{return Add(Dbmeta_fld_itm.new_str(name, len).Primary_y_());}
	public String Add_str_null(String name, int len)	{return Add(Dbmeta_fld_itm.new_str(name, len).Nullable_y_());}
	public String Add_str_dflt(String name, int len, String dflt)
														{return Add(Dbmeta_fld_itm.new_str(name, len).Default_(dflt));}
	public String Add(Dbmeta_fld_itm fld) {
		String name = fld.Name();
		flds.Add(name, fld);
		keys.Add(name);
		return name;
	}
	public static Dbmeta_fld_list new_() {return new Dbmeta_fld_list();}
}
