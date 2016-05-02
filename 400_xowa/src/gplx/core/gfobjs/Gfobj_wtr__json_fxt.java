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
import gplx.core.tests.*; import gplx.langs.jsons.*;
public class Gfobj_wtr__json_fxt {
	protected final    Gfobj_wtr__json mgr = new Gfobj_wtr__json();
	public Gfobj_nde Make__nde(Gfobj_fld... ary)					{return Make__nde(Gfobj_nde.New(), ary);}
	private Gfobj_nde Make__nde(Gfobj_nde nde, Gfobj_fld[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Gfobj_fld fld = (Gfobj_fld)ary[i];
			nde.Add_fld(fld);
		}
		return nde;
	}
	public Gfobj_fld Make__fld_str(String key, String val)				{return new Gfobj_fld_str(key, val);}
	public Gfobj_fld Make__fld_int(String key, int val)					{return new Gfobj_fld_int(key, val);}
	public Gfobj_fld Make__fld_long(String key, long val)				{return new Gfobj_fld_long(key, val);}
	public Gfobj_fld Make__fld_nde(String key, Gfobj_fld... ary)	{
		Gfobj_nde nde = Make__nde(Gfobj_nde.New(), ary);
		Gfobj_fld_nde rv = new Gfobj_fld_nde(key, nde);
		return rv;
	}
	public Gfobj_fld Make__fld_ary_str(String key, String... ary)		{return new Gfobj_fld_ary(key, new Gfo_ary_str(ary));}
	public Gfobj_fld Make__fld_ary_int(String key, int... ary)		{return new Gfobj_fld_ary(key, new Gfo_ary_int(ary));}
	public Gfobj_fld Make__fld_ary_ary(String key, Gfobj_ary... ary)	{return new Gfobj_fld_ary(key, new Gfo_ary_ary(ary));}
	public Gfobj_fld Make__fld_ary_nde(String key, Gfobj_nde... ary)	{return new Gfobj_fld_ary(key, new Gfobj_ary_nde(ary));}
	public Gfobj_ary Make__ary_int(int... ary)						{return new Gfo_ary_int(ary);}
	public Gfobj_wtr__json_fxt Test__write(Gfobj_grp root, String... lines) {
		String[] expd = Json_doc.Make_str_ary_by_apos(lines);
		Gftest.Eq__ary(expd, Bry_.Ary(String_.SplitLines_nl(mgr.Write(root).To_str())), "json_write");
		return this;
	}
}
