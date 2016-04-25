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
package gplx.xowa.addons.updates.downloads.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.updates.*; import gplx.xowa.addons.updates.downloads.*;
import gplx.langs.jsons.*; import gplx.langs.mustaches.*;
public class Xodl_itm_regy implements Mustache_doc_itm {
	public Xodl_itm_pack[] Packs = Xodl_itm_pack.Ary_empty;
	public Xodl_itm_regy() {}
	public Xodl_itm_regy(Xodl_itm_pack[] pack_ary) {this.Packs = pack_ary;}
	public Xodl_itm_pack[] Packs__select(int[] ids) {
		Hash_adp packs_hash = Hash_adp_.new_();
		int packs_len = Packs.length;
		for (int i = 0; i < packs_len; ++i) {
			Xodl_itm_pack pack = Packs[i];
			packs_hash.Add(pack.Id, pack);
		}
		
		List_adp rv = List_adp_.new_();
		int ids_len = ids.length;
		for (int i = 0; i < ids_len; ++i) {
			int id = ids[i];
			Xodl_itm_pack pack = (Xodl_itm_pack)packs_hash.Get_by(id);
			if (pack != null)
				rv.Add(pack);
		}
		return (Xodl_itm_pack[])rv.To_ary_and_clear(Xodl_itm_pack.class);
	}
	public void To_json(Json_wtr wtr) {
		wtr.Doc_ary_bgn();
		int wikis_len = Packs.length;
		for (int i = 0; i < wikis_len; ++i) {
			wtr.Nde_bgn_ary();
			Xodl_itm_pack wiki = Packs[i];
			wiki.To_json(wtr);
			wtr.Nde_end();
		}
		wtr.Doc_ary_end();
	}
	public void By_json(Json_grp ary) {
		int len = ary.Len();
		Packs = new Xodl_itm_pack[len];
		for (int i = 0; i < len; ++i) {
			Json_nde sub_nde = ary.Get_as_nde(i);
			Xodl_itm_pack sub_itm = new Xodl_itm_pack();
			Packs[i] = sub_itm;
			sub_itm.By_json(sub_nde);
		}
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {return false;}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "packs"))			return Packs;
		return Mustache_doc_itm_.Ary__empty;
	}
	public static Xodl_itm_regy Load_by_json(Io_url url) {return Load_by_json(Io_mgr.Instance.LoadFilBry(url));}
	public static Xodl_itm_regy Load_by_json(byte[] bry) {
		Xodl_itm_regy rv = new Xodl_itm_regy();
		Json_parser parser = new Json_parser();
		Json_doc jdoc = parser.Parse(bry);
		rv.By_json(jdoc.Root_grp());
		return rv;
	}
}
