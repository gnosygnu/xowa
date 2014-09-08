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
package gplx.criterias; import gplx.*;
public abstract class Criteria_bool_base implements Criteria {
	public abstract byte Crt_tid();
	public abstract boolean Matches(Object curVal);
	public String OpLiteral() {return opLiteral;} private String opLiteral;
	public Criteria Lhs() {return lhs;} Criteria lhs;
	public Criteria Rhs() {return rhs;} Criteria rhs;
	public String XtoStr() {return String_.Concat(lhs.XtoStr(), " ", this.opLiteral, " ", rhs.XtoStr());}
	@gplx.Internal protected void ctor_Criteria_base(String opLiteral, Criteria lhs, Criteria rhs) {this.opLiteral = opLiteral; this.lhs = lhs; this.rhs = rhs;}
	public static Criteria_bool_base as_(Object obj) {return obj instanceof Criteria_bool_base ? (Criteria_bool_base)obj : null;}
}
class Criteria_and extends Criteria_bool_base {
	@Override public byte Crt_tid() {return Criteria_.Tid_not;}
	@Override public boolean Matches(Object curVal) {return this.Lhs().Matches(curVal) && this.Rhs().Matches(curVal);}
	public Criteria_and(Criteria lhs, Criteria rhs) {this.ctor_Criteria_base("AND", lhs, rhs);}
}
class Criteria_or extends Criteria_bool_base {
	@Override public byte Crt_tid() {return Criteria_.Tid_or;}
	@Override public boolean Matches(Object curVal) {return this.Lhs().Matches(curVal) || this.Rhs().Matches(curVal);}
	public Criteria_or(Criteria lhs, Criteria rhs) {this.ctor_Criteria_base("OR", lhs, rhs);}
}
class Criteria_const implements Criteria {
	public byte Crt_tid() {return Criteria_.Tid_const;}
	public boolean Matches(Object comp) {return val;} private boolean val;
	public String XtoStr() {return String_.Concat(" IS ", Bool_.Xto_str_lower(val));}
	public Criteria_const(boolean val) {this.val = val;}
}
class Criteria_not implements Criteria {
	public byte Crt_tid() {return Criteria_.Tid_not;}
	public boolean Matches(Object obj) {return !criteria.Matches(obj);}
	public String XtoStr() {return String_.Concat_any(" NOT ", criteria.XtoStr());}
	Criteria criteria;
	public Criteria_not(Criteria v) {this.criteria = v;}
}
