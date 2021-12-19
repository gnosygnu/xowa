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
package gplx.xowa.langs.lnki_trails;
import gplx.types.basics.strings.unicodes.Utf8Utl;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.KeyVal;
import gplx.types.basics.utls.StringUtl;
public class Xol_lnki_trail_mgr implements Gfo_invk {
	public void Clear() {trie.Clear();}
	public int Count() {return trie.Count();}
	public Btrie_slim_mgr Trie() {return trie;} private final Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	public void Add(byte[] v) {trie.AddObj(v, v);}
	public void Del(byte[] v) {trie.Del(v);}
	private void Add(String... ary) {
		for (String itm_str : ary) {
			byte[] itm = BryUtl.NewU8(itm_str);
			trie.AddObj(itm, itm);
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_add_range)) 	Add_range(m);
		else if	(ctx.Match(k, Invk_add_many)) 	Add_many(m);
		else if	(ctx.Match(k, Invk_add_bulk)) 	Add_bulk(m);
		else if	(ctx.Match(k, Invk_clear)) 		Clear();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_add_many = "add_many", Invk_add_range = "add_range", Invk_add_bulk = "add_bulk", Invk_clear = "clear";
	private void Add_bulk(GfoMsg m) {byte[] src = m.ReadBry("bulk"); Add_bulk(src);}
	public void Add_bulk(byte[] src) {
		int pos = 0, src_len = src.length;
		while (true) {
			byte[] itm = Utf8Utl.GetCharAtPosAsBry(src, pos);
			Add(itm);
			pos += itm.length;
			if (pos >= src_len) break;
		}
	}
	private void Add_many(GfoMsg m) {
		int len = m.Args_count();
		for (int i = 0; i < len; i++) {
			KeyVal kv = m.Args_getAt(i);
			Add(kv.ValToStrOrEmpty());
		}
	}
	private void Add_range(GfoMsg m) {
		byte bgn = Add_rng_extract(m, "bgn");
		byte end = Add_rng_extract(m, "end");
		for (byte i = bgn; i <= end; i++)
			Add(new byte[] {i});
	}
	public void Add_range(byte bgn, byte end) {
		for (byte i = bgn; i <= end; i++)
			Add(new byte[] {i});
	}
	byte Add_rng_extract(GfoMsg m, String key) {
		byte[] bry = m.ReadBry(key);
		if (bry.length != 1) throw ErrUtl.NewArgs("argument must be ascii character", "key", key, "bry", StringUtl.NewU8(bry));
		return bry[0];
	}
}
