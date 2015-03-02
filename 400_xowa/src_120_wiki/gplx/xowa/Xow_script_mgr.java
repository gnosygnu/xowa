/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa; import gplx.*;
import gplx.xowa.wikis.*;
public class Xow_script_mgr implements GfoInvkAble {
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_set))		Set(m.ReadBry("key"), m.ReadBry("wiki_type"), m.ReadBry("script"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_set = "set";
	public void Exec(Xowe_wiki wiki) {
		int len = hash.Count();
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_k004();
		for (int i = 0; i < len; i++) {
			Xow_script_itm itm = (Xow_script_itm)hash.FetchAt(i);
			int wiki_tid = wiki.Domain_tid();
			if (Int_.In(wiki_tid, itm.Wiki_tids()))	// wiki_tid matches itm
				itm.Fmtr().Bld_bfr_many(bfr, wiki.Domain_bry(), Xow_domain_.Tid__get_bry(wiki_tid), wiki.Lang().Key_bry());
		}
		String gfs_script = String_.Replace(bfr.Xto_str_and_clear(), Op_sys.Wnt.Nl_str(), Op_sys.Lnx.Nl_str());
		wiki.Appe().Gfs_mgr().Run_str(gfs_script);
		bfr.Mkr_rls();
	}
	public void Set(byte[] key, byte[] wiki_types_raw, byte[] script) {
		byte[][] wiki_tid_names = Bry_.Split(wiki_types_raw, Byte_ascii.Tilde);
		int len = wiki_tid_names.length;
		int[] wiki_tids = new int[len];
		for (int i = 0; i < len; i++)
			wiki_tids[i] = Xow_domain_.Tid__get_int(wiki_tid_names[i]);

		Xow_script_itm itm = new Xow_script_itm(key, wiki_tids, script);
		hash.AddReplace(itm.Key(), itm);
	}
	public OrderedHash hash = OrderedHash_.new_bry_();
}
class Xow_script_itm {
	public Xow_script_itm(byte[] key, int[] wiki_tids, byte[] script) {
		this.key = key; this.wiki_tids = wiki_tids; this.fmtr = Bry_fmtr.new_bry_(script, "wiki_key", "wiki_type_name", "wiki_lang");
	}
	public byte[] Key() {return key;} private byte[] key;
	public int[] Wiki_tids() {return wiki_tids;} private int[] wiki_tids;
	public Bry_fmtr Fmtr() {return fmtr;} Bry_fmtr fmtr;
}
