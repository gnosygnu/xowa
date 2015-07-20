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
public abstract class Criteria_bool_base implements Criteria {
	@gplx.Internal protected void Ctor(String op_literal, Criteria lhs, Criteria rhs) {this.op_literal = op_literal; this.lhs = lhs; this.rhs = rhs;}
	public abstract byte	Tid();
	public abstract boolean	Matches(Object curVal);
	public void				Val_from_args(Hash_adp args) {lhs.Val_from_args(args); rhs.Val_from_args(args);}
	public void				Val_as_obj_(Object v) {throw Err_.new_unimplemented();}
	public String			XtoStr() {return String_.Concat(lhs.XtoStr(), " ", this.op_literal, " ", rhs.XtoStr());}
	public String			Op_literal() {return op_literal;} private String op_literal;
	public Criteria			Lhs() {return lhs;} private Criteria lhs;
	public Criteria			Rhs() {return rhs;} private Criteria rhs;
	public static Criteria_bool_base as_(Object obj) {return obj instanceof Criteria_bool_base ? (Criteria_bool_base)obj : null;}
}
class Criteria_and extends Criteria_bool_base {
	public Criteria_and(Criteria lhs, Criteria rhs) {this.Ctor("AND", lhs, rhs);}
	@Override public byte	Tid() {return Criteria_.Tid_not;}
	@Override public boolean	Matches(Object curVal) {return this.Lhs().Matches(curVal) && this.Rhs().Matches(curVal);}
}
class Criteria_or extends Criteria_bool_base {
	public Criteria_or(Criteria lhs, Criteria rhs) {this.Ctor("OR", lhs, rhs);}
	@Override public byte	Tid() {return Criteria_.Tid_or;}
	@Override public boolean	Matches(Object curVal) {return this.Lhs().Matches(curVal) || this.Rhs().Matches(curVal);}
}
class Criteria_const implements Criteria {
	public Criteria_const(boolean val) {this.val = val;}
	public byte				Tid() {return Criteria_.Tid_const;}
	public boolean				Matches(Object comp) {return val;} private final boolean val;
	public void				Val_from_args(Hash_adp args) {;}
	public void				Val_as_obj_(Object v) {throw Err_.new_unimplemented();}
	public String			XtoStr() {return String_.Concat(" IS ", Bool_.Xto_str_lower(val));}
}
class Criteria_not implements Criteria {
	private final Criteria criteria;
	public Criteria_not(Criteria v) {this.criteria = v;}
	public byte				Tid() {return Criteria_.Tid_not;}
	public boolean				Matches(Object obj) {return !criteria.Matches(obj);}
	public void				Val_from_args(Hash_adp args) {criteria.Val_from_args(args);}
	public void				Val_as_obj_(Object v) {criteria.Val_as_obj_(v);}
	public String			XtoStr() {return String_.Concat_any(" NOT ", criteria.XtoStr());}
}
