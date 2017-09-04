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
import gplx.xowa.xtns.scribunto.*;
public class Xot_invk_mock implements Xot_invk {
	Xot_invk_mock(byte defn_tid, int idx_adj, byte[] frame_ttl) {
		this.defn_tid = defn_tid; this.idx_adj = idx_adj; this.frame_ttl = frame_ttl;
	}	private int idx_adj; // SEE NOTE_1:
	public int Src_bgn() {return -1;}
	public int Src_end() {return -1;}
	public byte Defn_tid() {return defn_tid;} private byte defn_tid = Xot_defn_.Tid_null;
	public boolean Frame_is_root() {return false;}
	public byte Frame_tid() {return scrib_tid;} public void Frame_tid_(byte v) {scrib_tid = v;} private byte scrib_tid;
	public byte[] Frame_ttl() {return frame_ttl;} public void Frame_ttl_(byte[] v) {frame_ttl = v;} private byte[] frame_ttl;
	public int Frame_lifetime() {return frame_lifetime;} public void Frame_lifetime_(int v) {frame_lifetime = v;} private int frame_lifetime;
	public boolean Rslt_is_redirect() {return rslt_is_redirect;} public void Rslt_is_redirect_(boolean v) {rslt_is_redirect = v;} private boolean rslt_is_redirect;
	public Arg_nde_tkn Name_tkn() {return Arg_nde_tkn.Null;}
	public int Args_len() {return args.Count() + idx_adj;} private Ordered_hash args = Ordered_hash_.New_bry();
	public Arg_nde_tkn Args_get_by_idx(int i) {return (Arg_nde_tkn)args.Get_at(i - idx_adj);}
	public Arg_nde_tkn Args_eval_by_idx(byte[] src, int idx) {// DUPE:MW_ARG_RETRIEVE
		int cur = 0, list_len = args.Count(); 
		if (idx >= list_len) return null;
		for (int i = 0; i < list_len; i++) {	// iterate over list to find nth *non-keyd* arg; SEE:NOTE_1
			Arg_nde_tkn nde = (Arg_nde_tkn)args.Get_at(i);
			if (nde.KeyTkn_exists()) {
				int key_int = Bry_.To_int_or(nde.Key_tkn().Dat_ary(), -1);
				if (key_int == -1)
					continue;
				else {	// key is numeric
					if (key_int + idx_adj - 1 == idx) {
						return nde;						
					}
					else {
						continue;
					}
				}
			}
			if ((cur + idx_adj) == idx) return nde;
			else ++cur;
		}
		return Args_get_by_key(src, Bry_.To_a7_bry(idx + 1, 1));
	}
	public Arg_nde_tkn Args_get_by_key(byte[] src, byte[] key) {return (Arg_nde_tkn)args.Get_by(key);}
	public static Xot_invk_mock new_(byte defn_tid, byte[] frame_ttl, Keyval... args)		{return new_(defn_tid, 1, frame_ttl, args);}
	public static Xot_invk_mock new_(byte[] frame_ttl, Keyval... args)					{return new_(Xot_defn_.Tid_null, 1, frame_ttl, args);}
	public static Xot_invk_mock preprocess_(byte[] frame_ttl, Keyval... args)				{return new_(Xot_defn_.Tid_null, 1, frame_ttl, args);}
	public static Xot_invk_mock test_(byte[] frame_ttl, Keyval... args)					{return new_(Xot_defn_.Tid_null, 0, frame_ttl, args);}
	public static Xot_invk_mock new_(byte defn_tid, int idx_adj, byte[] frame_ttl, Keyval... args) {
		Xot_invk_mock rv = new Xot_invk_mock(defn_tid, idx_adj, frame_ttl);
		int len = args.length;
		for (int i = 0; i < len; i++) {
			Keyval kv = args[i];
			String kv_key_str = kv.Key();
			Object kv_key_obj = kv.Key_as_obj();
			Arg_nde_tkn_mock nde_tkn = null;
			if		(Type_adp_.Eq_typeSafe(kv_key_obj, Int_.Cls_ref_type))					// key is int; EX: 1 => val
				nde_tkn = new Arg_nde_tkn_mock(null, kv.Val_to_str_or_empty());			// add w/o key
			else if	(Type_adp_.Eq_typeSafe(kv.Val(), Bool_.Cls_ref_type)) {					// val is boolean; EX: key => true || key => false
				boolean kv_val_bool = Bool_.Cast(kv.Val());
				if (kv_val_bool)
					nde_tkn = new Arg_nde_tkn_mock(kv_key_str, "1");					// true => 1 (PHP behavior)
				else
					continue;															// false => "" (PHP behavior), but dropped from list (emulate MW behavior) wherein Parser.php!argSubstitution silently drops keyVals with value of false; "if ( $text === false && $Object === false )"; DATE:2014-01-02
			}
			else																		// regular nde
				nde_tkn = new Arg_nde_tkn_mock(kv_key_str, kv.Val_to_str_or_empty());	// add regular key, val strings
			rv.args.Add_if_dupe_use_nth(Bry_.new_u8(kv_key_str), nde_tkn);
		}
		return rv;
	}
	public static final    Xot_invk_mock Null = new Xot_invk_mock(Xot_defn_.Tid_null, 1, Bry_.Empty);
}
/*
NOTE_1: Xot_invk_mock is being used as a container for two functions
(1) As a substitute for an Invk_tkn; EX: {{#invoke:Mod|Func|arg_1|arg_2}}
. in this case, idx_adj is 1 b/c args will always be 1-based
. EX: Eval_by_idx(1) should return "arg_1". This would be list[0]
. said another way, requested_idx - idx_adj = list_idx; or 1 - 1 = 0
. Hence, 1 is the idx_adj; 
(2) As a substitute for Xot_defn_tmpl_.CopyNew; which occurs in ExpandTemplate
. in this case, idx_adj is 0 b/c args are 0-based
. note that Xot_defn_tmpl_ creates a temporary Object, and its args are 0 based (it doesn't emulate the list[0] for the func_name
*/