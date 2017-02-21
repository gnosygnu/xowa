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
package gplx.core.criterias; import gplx.*; import gplx.core.*;
public abstract class Criteria_bool_base implements Criteria {
	@gplx.Internal protected void Ctor(String op_literal, Criteria lhs, Criteria rhs) {this.op_literal = op_literal; this.lhs = lhs; this.rhs = rhs;}
	public abstract byte	Tid();
	public abstract boolean	Matches(Object curVal);
	public void				Val_from_args(Hash_adp args) {lhs.Val_from_args(args); rhs.Val_from_args(args);}
	public void				Val_as_obj_(Object v) {throw Err_.new_unimplemented();}
	public String			To_str() {return String_.Concat(lhs.To_str(), " ", this.op_literal, " ", rhs.To_str());}
	public String			Op_literal() {return op_literal;} private String op_literal;
	public Criteria			Lhs() {return lhs;} private Criteria lhs;
	public Criteria			Rhs() {return rhs;} private Criteria rhs;
	public static Criteria_bool_base as_(Object obj) {return obj instanceof Criteria_bool_base ? (Criteria_bool_base)obj : null;}
}
class Criteria_and extends Criteria_bool_base {
	public Criteria_and(Criteria lhs, Criteria rhs) {this.Ctor("AND", lhs, rhs);}
	@Override public byte	Tid() {return Criteria_.Tid_and;}
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
	public String			To_str() {return String_.Concat(" IS ", Bool_.To_str_lower(val));}
}
