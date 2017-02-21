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
package gplx.xowa.wikis.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.brys.fmtrs.*; import gplx.core.envs.*;
import gplx.xowa.wikis.domains.*;
public class Xow_script_mgr implements Gfo_invk {
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_set))		Set(m.ReadBry("key"), m.ReadBry("wiki_type"), m.ReadBry("script"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_set = "set";
	public void Exec(Xowe_wiki wiki) {
		int len = hash.Count();
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_k004();
		for (int i = 0; i < len; i++) {
			Xow_script_itm itm = (Xow_script_itm)hash.Get_at(i);
			int wiki_tid = wiki.Domain_tid();
			if (Int_.In(wiki_tid, itm.Wiki_tids()))	// wiki_tid matches itm
				itm.Fmtr().Bld_bfr_many(bfr, wiki.Domain_bry(), Xow_domain_tid_.Get_type_as_bry(wiki_tid), wiki.Lang().Key_bry());
		}
		String gfs_script = String_.Replace(bfr.To_str_and_clear(), Op_sys.Wnt.Nl_str(), Op_sys.Lnx.Nl_str());
		wiki.Appe().Gfs_mgr().Run_str(gfs_script);
		bfr.Mkr_rls();
	}
	public void Set(byte[] key, byte[] wiki_types_raw, byte[] script) {
		byte[][] wiki_tid_names = Bry_split_.Split(wiki_types_raw, Byte_ascii.Tilde);
		int len = wiki_tid_names.length;
		int[] wiki_tids = new int[len];
		for (int i = 0; i < len; i++)
			wiki_tids[i] = Xow_domain_tid_.Get_type_as_tid(wiki_tid_names[i]);

		Xow_script_itm itm = new Xow_script_itm(key, wiki_tids, script);
		hash.Add_if_dupe_use_nth(itm.Key(), itm);
	}
	public Ordered_hash hash = Ordered_hash_.New_bry();
}
class Xow_script_itm {
	public Xow_script_itm(byte[] key, int[] wiki_tids, byte[] script) {
		this.key = key; this.wiki_tids = wiki_tids; this.fmtr = Bry_fmtr.new_bry_(script, "wiki_key", "wiki_type_name", "wiki_lang");
	}
	public byte[] Key() {return key;} private byte[] key;
	public int[] Wiki_tids() {return wiki_tids;} private int[] wiki_tids;
	public Bry_fmtr Fmtr() {return fmtr;} Bry_fmtr fmtr;
}
