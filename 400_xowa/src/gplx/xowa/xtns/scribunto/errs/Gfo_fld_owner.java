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
package gplx.xowa.xtns.scribunto.errs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import gplx.core.criterias.*;
public interface Gfo_fld_owner {
	int		Fld_val_as_int(int fld_idx);
	String	Fld_val_as_str(int fld_idx);
	Object	Fld_val_as_obj(int fld_idx);
}
interface Gfo_match {
	boolean Match(Gfo_fld_owner owner);
}
class Gfo_match_ {
	public boolean Match(Gfo_match root, Gfo_fld_owner owner) {
		return root.Match(owner);
	}
}
class Gfo_match_int_1 implements Gfo_match {
	public Gfo_match_int_1(int idx, Gfo_comp_op_1 comp, int rhs) {this.idx = idx; this.comp = comp; this.rhs = rhs;}
	private int idx; private int rhs;
	private Gfo_comp_op_1 comp;
	public boolean Match(Gfo_fld_owner owner) {
		int lhs = owner.Fld_val_as_int(idx);
		return comp.Comp_int(lhs, rhs);
	}
}
class Gfo_match_obj implements Gfo_match {
	public Gfo_match_obj(int idx, Gfo_comp_op_1 comp, Gfo_val rhs) {this.idx = idx; this.comp = comp; this.rhs = rhs;}
	private int idx; Gfo_val rhs;
	private Gfo_comp_op_1 comp;
	public boolean Match(Gfo_fld_owner owner) {
		Gfo_val lhs = (Gfo_val)owner.Fld_val_as_obj(idx);
		return lhs.Match_1(comp, rhs);
	}
}
class Gfo_fld_crt implements Criteria {
	public byte Tid() {return Criteria_.Tid_wrapper;}
	public byte Fld_idx() {return fld_idx;} private byte fld_idx;
	public Criteria Crt() {return crt;} private Criteria crt;
	public boolean Matches(Object o) {			
		Gfo_fld_owner owner = (Gfo_fld_owner)o;
		Object comp = owner.Fld_val_as_obj(fld_idx);
		return crt.Matches(comp);
	}
	public void				Val_from_args(Hash_adp args) {throw Err_.new_unimplemented();}
	public void				Val_as_obj_(Object v) {throw Err_.new_unimplemented();}
	public String To_str() {return String_.Concat(Byte_.To_str(fld_idx), " ", crt.To_str());}
	public static Gfo_fld_crt new_(byte fld_idx, Criteria crt) {
		Gfo_fld_crt rv = new Gfo_fld_crt();
		rv.fld_idx = fld_idx; rv.crt = crt;
		return rv;
	}
}
