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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.btries.*; import gplx.core.bits.*;
import gplx.xowa.parsers.vnts.*;
public class Xol_vnt_regy {
	private final    Hash_adp_bry hash = Hash_adp_bry.ci_a7(); private int hash_len;
	private final    List_adp list = List_adp_.New();
	public Btrie_slim_mgr	Trie()				{return trie;} private final    Btrie_slim_mgr trie = Btrie_slim_mgr.ci_a7();
	public int				Len()				{return hash.Count();}
	public boolean				Has(byte[] k)		{return hash.Has(k);}
	public Xol_vnt_itm		Get_at(int i)		{return (Xol_vnt_itm)list.Get_at(i);}
	public Xol_vnt_itm		Get_by(byte[] k)	{return (Xol_vnt_itm)hash.Get_by(k);}
	public Xol_vnt_itm		Get_by(byte[] s, int b, int e) {return (Xol_vnt_itm)hash.Get_by_mid(s, b, e);}
	public void				Clear()				{hash.Clear(); list.Clear(); trie.Clear(); hash_len = 0;}
	public Xol_vnt_itm Add(byte[] key, byte[] name) {
		int mask = gplx.core.brys.Bit_.Get_flag(hash_len);
		Xol_vnt_itm itm = new Xol_vnt_itm(hash_len, key, name, mask);
		hash.Add(key, itm);
		list.Add(itm);
		trie.Add_obj(key, itm);
		hash_len = hash.Count();
		return itm;
	}
	public int Mask__calc(byte[]... ary) {
		int rv = 0;
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			byte[] key = ary[i];
			Xol_vnt_itm itm = (Xol_vnt_itm)hash.Get_by(key); if (itm == null) continue;	// handle bad vnt from user input; EX: -{zh;bad|text}-
			int itm_mask = itm.Mask__vnt();
			rv = rv == 0 ? itm_mask : Bitmask_.Flip_int(true, rv, itm_mask);
		}
		return rv;
	}
	public boolean Mask__match_any(int lhs, int rhs) {			// EX: match "zh-cn|zh-hans|zh-hant" against "zh|zh-hans|zh-hant"			
		for (int i = 0; i < hash_len; ++i) {
			int mask = gplx.core.brys.Bit_.Get_flag(i);		// 1,2,4,8
			if (Bitmask_.Has_int(lhs, mask)) {				// lhs has mask; EX: for lhs=6, mask=1 -> 'n'; mask=2 -> 'y'
				if (Bitmask_.Has_int(rhs, mask))			// if rhs does not have mask, return false;
					return true;
			}
		}
		return false;	// should only occur when len = 0;
	}
}
