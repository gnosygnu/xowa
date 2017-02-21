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
package gplx.xowa.langs.numbers; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.btries.*;
public class Xol_transform_mgr implements Gfo_invk {
	private Btrie_fast_mgr trie_k_to_v = Btrie_fast_mgr.cs();
	private Btrie_fast_mgr trie_v_to_k = Btrie_fast_mgr.cs();
	private Ordered_hash hash = Ordered_hash_.New_bry();
	private boolean empty = true;
	public void Clear() {hash.Clear(); trie_k_to_v.Clear(); trie_v_to_k.Clear(); empty = true;}
	public int Len() {return hash.Count();}
	public Keyval Get_at(int i) {return (Keyval)hash.Get_at(i);}
	public byte[] Get_val_or_self(byte[] k) {	// NOTE: return self; note that MW defaults "." and "," to self, even though MessagesLa.php only specifies ","; i.e.: always return something for "."; DATE:2014-05-13
		Keyval kv = (Keyval)hash.Get_by(k);
		return kv == null ? k : (byte[])kv.Val();
	}
	public Xol_transform_mgr Set(byte[] k, byte[] v) {
		trie_k_to_v.Add(k, v);
		trie_v_to_k.Add(v, k);
		Keyval kv = Keyval_.new_(String_.new_u8(k), v);
		hash.Del(k);
		hash.Add(k, kv);
		empty = false;
		return this;
	}
	public byte[] Replace(Bry_bfr tmp_bfr, byte[] src, boolean k_to_v) {
		if (empty || src == null) return src;
		int src_len = src.length; if (src_len == 0) return src;
		Btrie_fast_mgr trie = k_to_v ? trie_k_to_v : trie_v_to_k;
		return trie.Replace(tmp_bfr, src, 0, src_len);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_set))			Set(m.ReadBry("k"), m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_clear))			Clear();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Invk_set = "set", Invk_clear = "clear";
}
