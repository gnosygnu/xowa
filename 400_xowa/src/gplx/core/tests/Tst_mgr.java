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
package gplx.core.tests; import gplx.*; import gplx.core.*;
import gplx.core.strings.*;
public class Tst_mgr {
	public Tst_mgr ThrowError_n_() {throwError = false; return this;} private boolean throwError = true;
	public List_adp Results() {return results;} List_adp results = List_adp_.New();
	public Keyval_hash Vars() {return vars;} Keyval_hash vars = new Keyval_hash();
	public Object Vars_get_by_key(String key) {return vars.Get_val_or(key, null);}
	public String Vars_get_bry_as_str(String key, int bgn, int end) {
		byte[] bry = (byte[])vars.Get_val_or(key, null); if (bry == null) return String_.Empty;
		if (bgn < 0 || end > bry.length || end < bgn || end < 0) return "<<OUT OF BOUNDS>>";
		return String_.new_u8(Bry_.Mid(bry, bgn, end));
	}
	public int Tst_val(boolean skip, String path, String name, Object expd, Object actl) {
		Tst_itm itm = Tst_itm.eq_(skip, path, name, expd, actl);
		results.Add(itm);
		return itm.Pass() ? 0 : 1;
	}
	public int Tst_val_ary(boolean skip, String path, String name, Object expd, Object actl) {	
		Tst_itm itm = Tst_itm.eq_(skip, path, name, To_str(expd), To_str(actl));
		results.Add(itm);
		return itm.Pass() ? 0 : 1;
	}
	public void Tst_obj(Tst_chkr expd, Object actl) {
		results.Clear();
		int err = Tst_sub_obj(expd, actl, "", 0);
		if (throwError && err > 0) throw Err_.new_wo_type(Build());
	}
	public void Tst_ary(String ownerPath, Tst_chkr[] expd_ary, Object[] actl_ary) {
		results.Clear();
		Tst_ary_inner(ownerPath, expd_ary, actl_ary);
	}
	private void Tst_ary_inner(String ownerPath, Tst_chkr[] expd_ary, Object[] actl_ary) {
		int expd_ary_len = expd_ary.length, actl_ary_len = actl_ary.length;
		int max_len = expd_ary_len > actl_ary_len ? expd_ary_len : actl_ary_len;
		int err = 0;
		for (int i = 0; i < max_len; i++) {
			String path = ownerPath + Int_.To_str(i);
			Tst_chkr expd_obj = i < expd_ary_len ? expd_ary[i] : Tst_mgr.Null_chkr;
			Object actl_obj = i < actl_ary_len ? actl_ary[i] : "<NULL OBJ>";
			String actl_type = i < actl_ary_len ? Type_adp_.NameOf_obj(actl_obj) : "<NULL TYPE>";
			err += Tst_inner(expd_obj, actl_obj, actl_type, path, err);
		}
		if (throwError && err > 0) {
			String s = Build();
			throw Err_.new_wo_type(s);
		}
	}
	public int Tst_sub_obj(Tst_chkr expd, Object actl, String path, int err) {
		return Tst_inner(expd, actl, actl == null ? "<NULL>" : Type_adp_.NameOf_obj(actl), path, err);
	}
	public int Tst_sub_ary(Tst_chkr[] expd_subs, Object[] actl_subs, String path, int err) {
		Tst_ary_inner(path + ".", expd_subs, actl_subs);
		return err;
	}
	int Tst_inner(Tst_chkr expd_obj, Object actl_obj, String actl_type, String path, int err) {
		if (actl_obj == null || !Type_adp_.IsAssignableFrom(expd_obj.TypeOf(), actl_obj.getClass())) {
			results.Add(Tst_itm.fail_("!=", path, "<cast type>", Type_adp_.NameOf_type(expd_obj.TypeOf()), actl_type));
			return 1;
//				results.Add(Tst_itm.fail_("!=", path, "<cast value>", Object_.Xto_str_strict_or_null(expd_obj.ValueOf()), Object_.Xto_str_strict_or_null(actl_obj)));
		}
		else {
			return expd_obj.Chk(this, path, actl_obj);
		}
	}
	String To_str(Object ary) {	
		if (ary == null) return "<NULL>";
		int len = Array_.Len(ary);
		for (int i = 0; i < len; i++) {
			Object itm = Array_.Get_at(ary, i);
			ary_sb.Add(Object_.Xto_str_strict_or_null_mark(itm)).Add(",");
		}
		return ary_sb.To_str_and_clear();
	}	String_bldr ary_sb = String_bldr_.new_();
	String Build() {
		String_bldr sb = String_bldr_.new_();
		int comp_max = 0, path_max =0, name_max = 0;
		int len = results.Count();
		for (int i = 0; i < len; i++) {
			Tst_itm itm = (Tst_itm)results.Get_at(i);
			comp_max = Max(comp_max, itm.Comp());
			path_max = Max(path_max, itm.Path());
			name_max = Max(name_max, itm.Name());
		}
		for (int i = 0; i < len; i++) {
			Tst_itm itm = (Tst_itm)results.Get_at(i);
			sb.Add_fmt("\n{0}  {1}  {2}  '{3}'", String_.PadEnd(itm.Comp(), comp_max, " "), "#" + String_.PadEnd(itm.Path(), path_max, " "), "@" + String_.PadEnd(itm.Name(), name_max, " ") + ":", itm.Expd());
			if (!itm.Pass())
				sb.Add_fmt("\n{0}  {1}  {2}  '{3}'", String_.PadEnd("", comp_max, " "), " " + String_.PadEnd("", path_max, " "), " " + String_.PadEnd("", name_max, " ") + " ", itm.Actl());
		}
		return sb.To_str_and_clear();
	}
	int Max(int max, String s) {int len = String_.Len(s); return len > max ? len : max;}
	public static final    Tst_chkr Null_chkr = new Tst_chkr_null();
}
class Tst_itm {
	public boolean Pass() {return pass;} private boolean pass;
	public boolean Skip() {return skip;} private boolean skip;
	public String Comp() {return comp;} public Tst_itm Comp_(String v) {comp = v; return this;} private String comp = "";
	public String Path() {return path;} public Tst_itm Path_(String v) {path = v; return this;} private String path = "";
	public String Name() {return name;} public Tst_itm Name_(String v) {name = v; return this;} private String name = "";
	public String Expd() {return expd;} public Tst_itm Expd_(String v) {expd = v; return this;} private String expd = "";
	public String Actl() {return actl;} public Tst_itm Actl_(String v) {actl = v; return this;} private String actl = "";
	public static Tst_itm eq_(boolean skip, String path, String name, Object expd, Object actl) {
		boolean pass = skip ? true : Object_.Eq(expd, actl);
		String comp = pass ? "==" : "!=";
		String expd_str = Object_.Xto_str_strict_or_null_mark(expd);
		String actl_str = Object_.Xto_str_strict_or_null_mark(actl);
		if (skip) expd_str = actl_str;
		return new_(skip, pass, comp, path, name, expd_str, actl_str);
	}
	public static Tst_itm fail_(String comp, String path, String name, String expd, String actl) {return new_(false, false, comp, path, name, expd, actl);}
	public static Tst_itm new_(boolean skip, boolean pass, String comp, String path, String name, String expd, String actl) {
		Tst_itm rv = new Tst_itm();
		rv.skip = skip; rv.pass = pass; rv.comp = comp; rv.path = path; rv.name = name;; rv.expd = expd; rv.actl = actl;
		return rv;
	}
}
