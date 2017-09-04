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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.primitives.*;
import gplx.xowa.xtns.scribunto.*;
public class Xot_invk_temp implements Xot_invk {
	private List_adp			list = List_adp_.New();
	private Hash_adp_bry		arg_key_hash;
	private Hash_adp			arg_idx_hash; private Int_obj_ref arg_idx_ref;
	Xot_invk_temp() {}
	public Xot_invk_temp(boolean root_frame) {this.root_frame = root_frame;}
	public Xot_invk_temp(byte defn_tid, byte[] src, Arg_nde_tkn name_tkn, int src_bgn, int src_end) {
		this.defn_tid = defn_tid; this.src = src;
		this.name_tkn = name_tkn; this.src_bgn = src_bgn; this.src_end = src_end;
	}
	public byte[] Src() {return src;} private byte[] src; public Xot_invk_temp Src_(byte[] src) {this.src = src; return this;}
	public byte Defn_tid() {return defn_tid;} private byte defn_tid = Xot_defn_.Tid_null;
	public boolean Frame_is_root() {return root_frame;} private boolean root_frame;
	public byte Frame_tid() {return scrib_tid;} public void Frame_tid_(byte v) {scrib_tid = v;} private byte scrib_tid;
	public byte[] Frame_ttl() {return frame_ttl;} public void Frame_ttl_(byte[] v) {frame_ttl = v;} private byte[] frame_ttl = Bry_.Empty;	// NOTE: set frame_ttl to non-null value; PAGE:en.w:Constantine_the_Great {{Christianity}}; DATE:2014-06-26
	public int Frame_lifetime() {return frame_lifetime;} public void Frame_lifetime_(int v) {frame_lifetime = v;} private int frame_lifetime;
	public boolean Rslt_is_redirect() {return rslt_is_redirect;} public void Rslt_is_redirect_(boolean v) {rslt_is_redirect = v;} private boolean rslt_is_redirect;
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public Arg_nde_tkn Name_tkn() {return name_tkn;} private Arg_nde_tkn name_tkn;
	public int Args_len() {return list.Count();}
	public Arg_nde_tkn Args_eval_by_idx(byte[] src, int idx) {			// NOTE: idx is base0
		return arg_idx_hash == null										// only true if no args, or all args are keys; EX: {{A|b=1|c=2}}
			? null
			: (Arg_nde_tkn)arg_idx_hash.Get_by(arg_idx_ref.Val_(idx));	// lookup int in hash; needed b/c multiple identical keys should retrieve last, not first; EX: {{A|1=a|1=b}}; PAGE:el.d:ἔχω DATE:2014-07-23
	}
	public Arg_nde_tkn Args_get_by_idx(int i) {return (Arg_nde_tkn)list.Get_at(i);}
	public Arg_nde_tkn Args_get_by_key(byte[] src, byte[] key) {
		return arg_key_hash == null ? null : (Arg_nde_tkn)arg_key_hash.Get_by_bry(key);
	}
	public void Args_add(Arg_nde_tkn arg) {list.Add(arg);}
	public void Args_add_by_key(byte[] key, Arg_nde_tkn arg) {
		if (arg_key_hash == null) arg_key_hash = Hash_adp_bry.cs();	// PERF: lazy
		arg_key_hash.Add_if_dupe_use_nth(key, arg);
		int key_as_int = Bry_.To_int_or(key, Int_.Min_value);
		if (key_as_int != Int_.Min_value)						// key is int; add it to arg_idx_hash; EX:{{A|1=a}} vs {{A|a}}; DATE:2014-07-23
			Arg_idx_hash_add(key_as_int - List_adp_.Base1, arg);
	}
	public void Args_add_by_idx(Arg_nde_tkn arg) {Arg_idx_hash_add(++args_add_by_idx, arg);} private int args_add_by_idx = -1;	// NOTE: args_add_by_idx needs to be a separate variable; keeps track of args which don't have a key;
	private void Arg_idx_hash_add(int int_key, Arg_nde_tkn arg) {
		if (arg_idx_hash == null) {
			arg_idx_hash = Hash_adp_.New();
			arg_idx_ref = Int_obj_ref.New_neg1();
		}
		arg_idx_hash.Add_if_dupe_use_nth(Int_obj_ref.New(int_key), arg);	// Add_if_dupe_use_nth to keep latest version; needed for {{A|1=a|1=b}} DATE:2014-07-23
	}

	public static final    Xot_invk_temp Page_is_caller = new Xot_invk_temp(true);	// SEE NOTE_2
	public static final    Xot_invk Null_frame = null;
}
/*
NOTE_1:
The nth arg refers to the nth non-keyd arg;
EX:
defn is {{{1}}}
invk is {{test|key1=a|b}}
{{{1}}} is b b/c b is the 1st non-keyd arg (even though it is the 2nd arg)
*/
/*
NOTE_2:
Consider a Page with the following Wiki text in the Preview box
WIKI: "a {{mwo_concat|{{{1}}}|b}} c"
TEXT: "a {{{1}}}b c"

Note that in order to resolve mwo_concat we need to pass in an Xot_invk
This Xot_invk is the "Page_is_caller" ref
Note this has no parameters and is always empty

Does this static ref have multi-threaded issues? DATE:2017-09-01
*/
