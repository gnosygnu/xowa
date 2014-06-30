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
package gplx.dbs; import gplx.*;
import gplx.criterias.*;
public class Db_crt_ {
	public static final Criteria Wildcard = Criteria_.All;
	public static Criteria eq_(String name, Object val) {return Criteria_wrapper.new_(name, Criteria_.eq_(val));}
	public static Criteria eqn_(String name, Object val) {return Criteria_wrapper.new_(name, Criteria_.eqn_(val));}
	public static Criteria lt_(String name, Comparable val) {return Criteria_wrapper.new_(name, Criteria_.lt_(val));}
	public static Criteria lte_(String name, Comparable val) {return Criteria_wrapper.new_(name, Criteria_.lte_(val));}
	public static Criteria mt_(String name, Comparable val) {return Criteria_wrapper.new_(name, Criteria_.mt_(val));}
	public static Criteria mte_(String name, Comparable val) {return Criteria_wrapper.new_(name, Criteria_.mte_(val));}
	public static Criteria between_(String name, Comparable lhs, Comparable rhs) {return Criteria_wrapper.new_(name, Criteria_.between_(lhs, rhs));}
	public static Criteria in_(String name, Object... vals) {return Criteria_wrapper.new_(name, Criteria_.in_(vals));}
	public static Criteria like_(String name, String pattern) {return Criteria_wrapper.new_(name, Criteria_.like_(pattern));}
	public static Criteria liken_(String name, String pattern) {return Criteria_wrapper.new_(name, Criteria_.liken_(pattern));}
	public static Criteria eqMany_(KeyVal... array) {
		Criteria rv = null;
		for (int i = 0; i < array.length; i++) {
			KeyVal pair = array[i];
			Criteria crt = Db_crt_.eq_(pair.Key(), pair.Val());
			rv = (i == 0)? crt : Criteria_.And(rv, crt);
		}
		return rv;
	}
	public static Criteria eq_(String name) {return Criteria_wrapper.new_(name, Criteria_.eq_(null));}
	public static Criteria eq_many_(String... ary) {
		Criteria rv = null;
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Criteria crt = Db_crt_.eq_(ary[i], null);
			rv = (i == 0)? crt : Criteria_.And(rv, crt);
		}
		return rv;
	}
	public static Criteria wrap_(String name, Criteria crt) {return Criteria_wrapper.new_(name, crt);}
}
