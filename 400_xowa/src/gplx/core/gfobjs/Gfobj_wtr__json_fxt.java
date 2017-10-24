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
	public Gfobj_fld Make__fld_bool		(String key, boolean val)				{return new Gfobj_fld_bool(key, val);}
	public Gfobj_fld Make__fld_str		(String key, String val)			{return new Gfobj_fld_str(key, val);}
	public Gfobj_fld Make__fld_int		(String key, int val)				{return new Gfobj_fld_int(key, val);}
	public Gfobj_fld Make__fld_long		(String key, long val)				{return new Gfobj_fld_long(key, val);}
	public Gfobj_fld Make__fld_double	(String key, double val)			{return new Gfobj_fld_double(key, val);}
	public Gfobj_fld Make__fld_nde(String key, Gfobj_fld... ary)	{
		Gfobj_nde nde = Make__nde(Gfobj_nde.New(), ary);
		Gfobj_fld_nde rv = new Gfobj_fld_nde(key, nde);
		return rv;
	}
	public Gfobj_fld Make__fld_ary		(String key, Object... ary)	{return new Gfobj_fld_ary(key, new Gfobj_ary(ary));}
	public Gfobj_ary Make__ary			(Object... ary)				{return new Gfobj_ary(ary);}
	public Gfobj_wtr__json_fxt Test__write(Gfobj_grp root, String... lines) {
		String[] expd = Json_doc.Make_str_ary_by_apos(lines);
		Gftest.Eq__ary(expd, Bry_.Ary(String_.SplitLines_nl(mgr.Write(root).To_str())), "json_write");
		return this;
	}
	public Gfobj_wtr__json_fxt Test__parse(String src, Gfobj_grp expd) {
		Gfobj_rdr__json rdr = new Gfobj_rdr__json();
		Gfobj_grp actl = rdr.Parse(Bry_.new_u8(Json_doc.Make_str_by_apos(src)));
		Gftest.Eq__ary(Bry_.Ary(String_.SplitLines_nl(mgr.Write(expd).To_str())), Bry_.Ary(String_.SplitLines_nl(mgr.Write(actl).To_str())), "json_write");
		return this;
	}
}
