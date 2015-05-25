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
package gplx.core.criterias; import gplx.*; import gplx.core.*;
public class Criteria_fld implements Criteria {
	Criteria_fld(String key, Criteria crt) {this.key = key; this.crt = crt;}
	public byte			Tid() {return Criteria_.Tid_wrapper;}
	public String		Key() {return key;} private final String key;
	public Criteria		Crt() {return crt;} private final Criteria crt;
	public void			Val_as_obj_(Object v) {throw Err_.not_implemented_();}
	public void			Val_from_args(Hash_adp args) {
		List_adp list = (List_adp)args.Get_by(key); if (list == null) throw Err_.new_("criteria.fld key not found; key={0}", key);
		Object o = Fill_val(key, crt.Tid(), list);
		crt.Val_as_obj_(o);
	}
	public boolean			Matches(Object invkObj) {			
		GfoInvkAble invk = (GfoInvkAble)invkObj;
		if (key == Criteria_fld.Key_null) return crt.Matches(invkObj);
		Object comp = GfoInvkAble_.InvkCmd(invk, key);			
		return crt.Matches(comp);
	}
	public String		XtoStr() {return String_.Concat(key, " ", crt.XtoStr());}
	public static final String Key_null = null;
	public static Criteria_fld as_(Object obj) {return obj instanceof Criteria_fld ? (Criteria_fld)obj : null;}
	public static Criteria_fld new_(String key, Criteria crt) {return new Criteria_fld(key, crt);}
	public static Object Fill_val(String key, byte tid, List_adp list) {
		int len = list.Count();
		switch (tid) {
			case Criteria_.Tid_eq:			
			case Criteria_.Tid_comp:
			case Criteria_.Tid_like:
			case Criteria_.Tid_iomatch:
				if (len != 1) throw Err_.new_("list.len should be 1; key={0} tid={1} len={2}", key, tid, len);
				return list.Get_at(0);
			case Criteria_.Tid_between:
				if (len != 2) throw Err_.new_("list.len should be 2; key={0} tid={1} len={2}", key, tid, len);
				return new Object[] {list.Get_at(0), list.Get_at(1)};
			case Criteria_.Tid_in:
				if (len == 0) throw Err_.new_("list.len should be > 0; key={0} tid={1} len={2}", key, tid, len);
				return list.To_obj_ary();
			case Criteria_.Tid_const:
			case Criteria_.Tid_not:
			case Criteria_.Tid_and:
			case Criteria_.Tid_or:
				if (len != 0) throw Err_.new_("list.len should be == 0; key={0} tid={1} len={2}", key, tid, len);
				return key;					// no values to fill in; return back key
			case Criteria_.Tid_wrapper:		// not recursive
			case Criteria_.Tid_db_obj_ary:	// unsupported
			case Criteria_.Tid_custom:
			default:				throw Err_.unhandled(tid);
		}
	}
}
