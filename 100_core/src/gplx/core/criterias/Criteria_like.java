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
import gplx.texts.*; /*RegxPatn_cls_like*/
public class Criteria_like implements Criteria {
	@gplx.Internal protected Criteria_like(boolean negated, RegxPatn_cls_like pattern) {
		this.negated = negated; this.pattern = pattern;
	}
	public byte					Tid() {return Criteria_.Tid_like;}
	public boolean					Negated() {return negated;} private final boolean negated;
	public RegxPatn_cls_like	Pattern() {return pattern;} private RegxPatn_cls_like pattern;
	public void					Val_from_args(Hash_adp args) {throw Err_.new_unimplemented();}
	public void					Val_as_obj_(Object v) {this.pattern = (RegxPatn_cls_like)v;}
	public boolean Matches(Object compObj) {
		String comp = String_.as_(compObj); if (comp == null) throw Err_.new_type_mismatch(String.class, compObj);
		boolean rv = pattern.Matches(comp);
		return negated ? !rv : rv;
	}
	public String XtoStr() {return String_.Concat_any("LIKE ", pattern);}
	public static Criteria_like as_(Object obj) {return obj instanceof Criteria_like ? (Criteria_like)obj : null;}
}