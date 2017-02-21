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
package gplx.core.consoles; import gplx.*; import gplx.core.*;
public class Gfo_cmd_arg_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New();
	private final    List_adp err_list = List_adp_.New(), tmp_vals = List_adp_.New();
	public String[] Orig_ary() {return orig_ary;} private String[] orig_ary;
	public void Reset() {
		hash.Clear();
		this.Clear();
	}
	public void Clear() {
		int len = hash.Count();
		for (int i = 0; i < len; ++i) {
			Gfo_cmd_arg_itm itm = (Gfo_cmd_arg_itm)hash.Get_at(i);
			itm.Clear();
		}
		err_list.Clear();
	}
	public Gfo_cmd_arg_mgr Reg_many(Gfo_cmd_arg_itm... ary) {for (Gfo_cmd_arg_itm itm : ary) Reg(itm); return this;}
	public int Len() {return hash.Count();}
	public boolean Has(String k) {
		Gfo_cmd_arg_itm arg = (Gfo_cmd_arg_itm)hash.Get_by(k);
		return arg != null && arg.Dirty();
	}
	public boolean Get_by_as_bool(String k) {
		Gfo_cmd_arg_itm arg = (Gfo_cmd_arg_itm)hash.Get_by(k);
		return arg != null && arg.Val() != null && arg.Val_as_bool();
	}
	public Gfo_cmd_arg_itm Get_at(int i)		{return (Gfo_cmd_arg_itm)hash.Get_at(i);}
	public Gfo_cmd_arg_itm Get_by(String key)	{return (Gfo_cmd_arg_itm)hash.Get_by(key);}
	public void Parse(String[] orig_ary) {
		this.Clear();
		this.orig_ary = orig_ary; int orig_len = orig_ary.length;
		Gfo_cmd_arg_itm cur_itm = null;
		int orig_idx = 0;
		while (true) {
			boolean done = orig_idx == orig_len;
			String itm = done ? "" : orig_ary[orig_idx++];
			boolean itm_is_key = String_.Has_at_bgn(itm, Key_prefix);	// has "--" -> is key
			if (	cur_itm != null				// pending itm
				&&	(itm_is_key || done)) {		// cur arg is key ("--key2"), or all done
				cur_itm.Val_(Gfo_cmd_arg_mgr_.Parse_ary_to_str(this, cur_itm.Val_tid(), tmp_vals.To_str_ary_and_clear()));
				cur_itm = null;
			}
			if (done) break;
			if (itm_is_key) {
				String key = String_.Mid(itm, prefix_len);
				Object o = hash.Get_by(key);	if (o == null)			{Errs__add(Gfo_cmd_arg_mgr_.Err__key__unknown	, key); continue;} 
				cur_itm = (Gfo_cmd_arg_itm)o;	if (cur_itm.Dirty())	{Errs__add(Gfo_cmd_arg_mgr_.Err__key__duplicate	, key); continue;}
			}
			else {
				if (cur_itm == null)	{Errs__add(Gfo_cmd_arg_mgr_.Err__key__missing, itm); continue;}	// should only happen if 1st itm is not "--%"
				tmp_vals.Add(itm);
			}
		}

		// calc .Reqd and .Dflt
		int len = hash.Count();
		for (int i = 0; i < len; ++i) {
			cur_itm = (Gfo_cmd_arg_itm)hash.Get_at(i);
			if (!cur_itm.Dirty()) {					// arg not passed
				if		(cur_itm.Reqd())			// arg required but no value passed; add error
					Errs__add(Gfo_cmd_arg_mgr_.Err__val__required, cur_itm.Key());
				else if	(cur_itm.Dflt() != null)	// arg has default
					cur_itm.Val_(cur_itm.Dflt());
			}
		}
	}
	public boolean Errs__exist() {return err_list.Count() > 0;}
	public void Errs__add(String key, String val) {err_list.Add(key + ": " + val);}
	public String[] Errs__to_str_ary() {return err_list.To_str_ary();}
	public Gfo_cmd_arg_itm[] To_ary() {return (Gfo_cmd_arg_itm[])hash.To_ary(Gfo_cmd_arg_itm.class);}
	private void Reg(Gfo_cmd_arg_itm defn) {hash.Add(defn.Key(), defn);}
	public static final String Key_prefix = "--"; private static final int prefix_len = 2;
}
