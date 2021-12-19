/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.criterias;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class Criteria_comp implements Criteria {
	public Criteria_comp(int comp_mode, Comparable val) {this.comp_mode = comp_mode; this.val = val;}
	public byte                Tid() {return Criteria_.Tid_comp;}
	public Comparable        Val() {return val;} private Comparable val;
	public void                Val_from_args(Hash_adp args) {throw ErrUtl.NewUnimplemented();}
	public void                Val_as_obj_(Object v) {val = (Comparable)v;}
	public int                Comp_mode() {return comp_mode;} private final int comp_mode;
	public boolean Matches(Object comp_obj) {
		return CompareAbleUtl.Is(comp_mode, CompareAbleUtl.as_(comp_obj), val);
	}

	public String ToStr() {
		String comp_sym = comp_mode < CompareAbleUtl.Same ? "<" : ">";
		String eq_sym = comp_mode % 2 == CompareAbleUtl.Same ? "=" : "";
		return StringUtl.ConcatObjs(comp_sym, eq_sym, " ", val);
	}
}
