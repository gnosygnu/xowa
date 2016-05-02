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
import gplx.langs.jsons.*;
public class Gfobj_wtr__json {
	private final    Json_wtr wtr = new Json_wtr();
	public Gfobj_wtr__json Opt_ws_(boolean v) {wtr.Opt_ws_(v); return this;}
	public Bry_bfr Bfr() {return wtr.Bfr();}
	public String To_str() {return wtr.To_str_and_clear();}
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
			case Gfobj_fld_.Fld_tid__int:		wtr.Kv_int(itm.Key()	, ((Gfobj_fld_int)itm).As_int()); break;
			case Gfobj_fld_.Fld_tid__long:		wtr.Kv_long(itm.Key()	, ((Gfobj_fld_long)itm).As_long()); break;
			case Gfobj_fld_.Fld_tid__nde:		wtr.Nde_bgn(itm.Key()); Write_nde(((Gfobj_fld_nde)itm).As_nde()); wtr.Nde_end();break;
			case Gfobj_fld_.Fld_tid__ary:		wtr.Ary_bgn(itm.Key()); Write_ary(((Gfobj_fld_ary)itm).As_ary()); wtr.Ary_end();break;
			default: throw Err_.new_unhandled_default(itm.Fld_tid());
		}
	}
	private void Write_ary(Gfobj_ary ary) {
		int len = ary.Len();
		byte ary_tid = ary.Ary_tid();
		switch (ary_tid) {
			case Gfobj_ary_.Ary_tid__str:
				String[] ary_str = ((Gfo_ary_str)ary).Ary_str();
				for (int i = 0; i < len; ++i)
					wtr.Ary_itm_str(ary_str[i]);
				break;
			case Gfobj_ary_.Ary_tid__int:
				int[] ary_int = ((Gfo_ary_int)ary).Ary_int();
				for (int i = 0; i < len; ++i)
					wtr.Ary_itm_obj(ary_int[i]);
				break;
			case Gfobj_ary_.Ary_tid__nde:
				Gfobj_nde[] ary_nde = ((Gfobj_ary_nde)ary).Ary_nde();
				for (int i = 0; i < len; ++i) {
					wtr.Nde_bgn_ary();
					Write_nde(ary_nde[i]);
					wtr.Nde_end();
				}
				break;
			case Gfobj_ary_.Ary_tid__ary:
				Gfobj_ary[] ary_ary = ((Gfo_ary_ary)ary).Ary_ary();
				for (int i = 0; i < len; ++i) {
					wtr.Ary_bgn_ary();
					Write_ary(ary_ary[i]);
					wtr.Ary_end();
				}
				break;
			default: throw Err_.new_unhandled_default(ary_tid);
		}
	}
}
