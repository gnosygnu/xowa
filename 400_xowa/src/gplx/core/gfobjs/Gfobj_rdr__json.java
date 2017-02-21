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
import gplx.langs.jsons.*;
public class Gfobj_rdr__json {
	private final    Json_parser parser = new Json_parser();
	public Gfobj_grp Load(Io_url url) {
		byte[] src = Io_mgr.Instance.LoadFilBryOrNull(url); if (src == null) return null;
		return this.Parse(src);
	}
	public Gfobj_grp Parse(byte[] src) {
		Json_doc jdoc = parser.Parse(src);
		if (jdoc.Root_grp().Tid() == Json_itm_.Tid__nde) {
			Gfobj_nde rv_nde = Gfobj_nde.New();
			Parse_nde((Json_nde)jdoc.Root_grp(), rv_nde);
			return rv_nde;
		}
		else {
			Gfobj_ary rv_ary = new Gfobj_ary(null);
			Parse_ary((Json_ary)jdoc.Root_grp(), rv_ary);
			return rv_ary;
		}
	}
	private void Parse_nde(Json_nde jnde, Gfobj_nde gnde) {
		int len = jnde.Len();
		for (int i = 0; i < len; ++i) {
			Json_kv kv = jnde.Get_at_as_kv(i);
			String key_str = kv.Key_as_str();
			Json_itm val = kv.Val();
			byte val_tid = val.Tid();
			switch (val_tid) {
				case Json_itm_.Tid__str:		gnde.Add_str	(key_str, ((Json_itm_str)val).Data_as_str()); break;
				case Json_itm_.Tid__bool:		gnde.Add_bool	(key_str, ((Json_itm_bool)val).Data_as_bool()); break;
				case Json_itm_.Tid__int:		gnde.Add_int	(key_str, ((Json_itm_int)val).Data_as_int()); break;
				case Json_itm_.Tid__long:		gnde.Add_long	(key_str, ((Json_itm_long)val).Data_as_long()); break;
				case Json_itm_.Tid__decimal:	gnde.Add_double	(key_str, ((Json_itm_decimal)val).Data_as_decimal().To_double()); break;
				case Json_itm_.Tid__null:		gnde.Add_str	(key_str, null); break;
				case Json_itm_.Tid__ary:
					Gfobj_ary sub_ary = new Gfobj_ary(null);
					gnde.Add_ary(key_str, sub_ary);
					Parse_ary(Json_ary.cast(val), sub_ary);
					break;
				case Json_itm_.Tid__nde:
					Gfobj_nde sub_gnde = Gfobj_nde.New();
					gnde.Add_nde(key_str, sub_gnde);
					Parse_nde(Json_nde.cast(val), sub_gnde);
					break;
				default:						throw Err_.new_unhandled_default(val_tid);
			}
		}
	}
	private void Parse_ary(Json_ary jry, Gfobj_ary gry) {
		int len = jry.Len();
		Object[] ary = new Object[len];
		gry.Ary_(ary);
		for (int i = 0; i < len; ++i) {
			Json_itm jsub = jry.Get_at(i);
			switch (jsub.Tid()) {
				case Json_itm_.Tid__ary: {
					Gfobj_ary sub_ary = new Gfobj_ary(null);
					Parse_ary(Json_ary.cast(jsub), sub_ary);
					ary[i] = sub_ary;
					break;
				}
				case Json_itm_.Tid__nde: {
					Gfobj_nde sub_ary = Gfobj_nde.New();
					Parse_nde(Json_nde.cast(jsub), sub_ary);
					ary[i] = sub_ary;
					break;
				}
				default:
					ary[i] = jsub.Data();
					break;
			}
		}
	}
}
