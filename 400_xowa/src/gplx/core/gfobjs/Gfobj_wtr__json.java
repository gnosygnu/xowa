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
public class Gfobj_wtr__json {
	private final    Json_wtr wtr = new Json_wtr();
	public Gfobj_wtr__json Opt_ws_(boolean v) {wtr.Opt_ws_(v); return this;}
	public Gfobj_wtr__json Opt_backslash_2x_(boolean v) {wtr.Opt_backslash_2x_(v); return this;}
	public Bry_bfr Bfr() {return wtr.Bfr();}
	public String To_str() {return wtr.To_str_and_clear();}
	public void Save(Io_url url) {
		Io_mgr.Instance.SaveFilBry(url, wtr.To_bry_and_clear());
	}
	public Gfobj_wtr__json Write(Gfobj_grp root) {
		switch (root.Grp_tid()) {
			case Gfobj_grp_.Grp_tid__nde:
				wtr.Doc_nde_bgn();
				Write_nde((Gfobj_nde)root);
				wtr.Doc_nde_end();
				break;
			case Gfobj_grp_.Grp_tid__ary:
				wtr.Doc_ary_bgn();
				Write_ary((Gfobj_ary)root);
				wtr.Doc_ary_end();
				break;
			default:
				throw Err_.new_unhandled_default(root.Grp_tid());
		}
		return this;
	}
	private void Write_nde(Gfobj_nde nde) {
		int len = nde.Len();
		for (int i = 0; i < len; ++i) {
			Gfobj_fld fld = (Gfobj_fld)nde.Get_at(i);
			Write_fld(fld); 
		}
	}
	private void Write_fld(Gfobj_fld itm) {
		switch (itm.Fld_tid()) {
			case Gfobj_fld_.Fld_tid__str:		wtr.Kv_str(itm.Key()	, ((Gfobj_fld_str)itm).As_str()); break;
			case Gfobj_fld_.Fld_tid__bry:		wtr.Kv_bry(itm.Key()	, ((Gfobj_fld_bry)itm).As_bry()); break;
			case Gfobj_fld_.Fld_tid__int:		wtr.Kv_int(itm.Key()	, ((Gfobj_fld_int)itm).As_int()); break;
			case Gfobj_fld_.Fld_tid__long:		wtr.Kv_long(itm.Key()	, ((Gfobj_fld_long)itm).As_long()); break;
			case Gfobj_fld_.Fld_tid__bool:		wtr.Kv_bool(itm.Key()	, ((Gfobj_fld_bool)itm).As_bool()); break;
			case Gfobj_fld_.Fld_tid__double:	wtr.Kv_double(itm.Key()	, ((Gfobj_fld_double)itm).As_double()); break;
			case Gfobj_fld_.Fld_tid__nde:		wtr.Nde_bgn(itm.Key()); Write_nde(((Gfobj_fld_nde)itm).As_nde()); wtr.Nde_end();break;
			case Gfobj_fld_.Fld_tid__ary:		wtr.Ary_bgn(itm.Key()); Write_ary(((Gfobj_fld_ary)itm).As_ary()); wtr.Ary_end();break;
			default: throw Err_.new_unhandled_default(itm.Fld_tid());
		}
	}
	private void Write_ary(Gfobj_ary ary) {
		int len = ary.Len();
		Object[] ary_obj = ((Gfobj_ary)ary).Ary_obj();
		for (int i = 0; i < len; ++i) {
			Object sub_itm = ary_obj[i];
			Class<?> sub_itm_type = Type_.Type_by_obj(sub_itm);
			if		(Type_.Eq(sub_itm_type, Gfobj_ary.class)) {
				wtr.Ary_bgn_ary();
				Write_ary((Gfobj_ary)sub_itm);
				wtr.Ary_end();
			}
			else if (Type_.Eq(sub_itm_type, Gfobj_nde.class)) {
				wtr.Nde_bgn_ary();
				Write_nde((Gfobj_nde)sub_itm);
				wtr.Nde_end();
			}
			else
				wtr.Ary_itm_obj(sub_itm);
		}
	}
}
