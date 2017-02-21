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
