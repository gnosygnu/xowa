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
public class Criteria_eq implements Criteria {
	@gplx.Internal protected Criteria_eq(boolean negated, Object val) {this.negated = negated; this.val = val;}
	public byte			Tid()		{return Criteria_.Tid_eq;}
	public boolean			Negated()	{return negated;} private final boolean negated;
	public Object		Val()		{return val;} private Object val;
	public void			Val_as_obj_(Object v) {this.val = v;}
	public void			Val_from_args(Hash_adp args) {throw Err_.new_unimplemented();}
	public boolean Matches(Object comp) {
		Class<?> val_type = Type_adp_.ClassOf_obj(val);
		if (!Type_adp_.Eq_typeSafe(comp, val_type)) throw Err_.new_type_mismatch(val_type, comp);
		boolean rv = Object_.Eq(val, comp);
		return negated ? !rv : rv;
	}
	public String To_str() {return String_.Concat_any("= ", val);}
	public static Criteria_eq as_(Object obj) {return obj instanceof Criteria_eq ? (Criteria_eq)obj : null;}
}
