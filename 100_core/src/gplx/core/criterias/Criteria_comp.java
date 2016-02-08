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
public class Criteria_comp implements Criteria {
	@gplx.Internal protected Criteria_comp(int comp_mode, Comparable val) {this.comp_mode = comp_mode; this.val = val;}
	public byte				Tid() {return Criteria_.Tid_comp;}
	public Comparable		Val() {return val;} private Comparable val;
	public void				Val_from_args(Hash_adp args) {throw Err_.new_unimplemented();}
	public void				Val_as_obj_(Object v) {val = (Comparable)v;}
	public int				Comp_mode() {return comp_mode;} private final int comp_mode;
	public boolean Matches(Object comp_obj) {
		return CompareAble_.Is(comp_mode, CompareAble_.as_(comp_obj), val);
	}

	public String To_str() {
		String comp_sym = comp_mode < CompareAble_.Same ? "<" : ">";
		String eq_sym = comp_mode % 2 == CompareAble_.Same ? "=" : "";
		return String_.Concat_any(comp_sym, eq_sym, " ", val);
	}
}
