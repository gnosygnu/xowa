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
package gplx.core.gfobjs; import gplx.*; import gplx.core.*;
public class Gfobj_nde implements Gfobj_grp {
	private Ordered_hash subs;
	public byte				Grp_tid()									{return Gfobj_grp_.Grp_tid__nde;}
	public int				Len()										{return subs == null ? 0 : subs.Len();}
	public Gfobj_fld		Get_at(int i)								{return subs == null ? null : (Gfobj_fld)subs.Get_at(i);}
	public Gfobj_fld		Get_by(String k)							{return subs == null ? null : (Gfobj_fld)subs.Get_by(k);}
	public Gfobj_nde		Add_fld(Gfobj_fld fld)						{if (subs == null) subs = Ordered_hash_.New(); subs.Add(fld.Key(), fld); return this;}
	public Gfobj_nde		Add_int(String key, int val)				{return Add_fld(new Gfobj_fld_int(key, val));}
	public Gfobj_nde		Add_long(String key, long val)				{return Add_fld(new Gfobj_fld_long(key, val));}
	public Gfobj_nde		Add_str(String key, String val)				{return Add_fld(new Gfobj_fld_str(key, val));}
	public Gfobj_nde		Add_nde(String key, Gfobj_nde val)			{return Add_fld(new Gfobj_fld_nde(key, val));}
	public Gfobj_nde		Add_ary(String key, Gfobj_ary val)			{return Add_fld(new Gfobj_fld_ary(key, val));}
	public Gfobj_nde		New_nde(String key)							{Gfobj_nde rv		= new Gfobj_nde();			Add_fld(new Gfobj_fld_nde(key, rv)); return rv;}
	public Gfobj_ary_nde	New_ary_nde(String key)						{Gfobj_ary_nde rv	= new Gfobj_ary_nde(null);	Add_fld(new Gfobj_fld_ary(key, rv)); return rv;}
	public static Gfobj_nde New()										{return new Gfobj_nde();}
}
