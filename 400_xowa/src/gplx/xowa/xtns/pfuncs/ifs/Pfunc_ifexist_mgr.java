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
package gplx.xowa.xtns.pfuncs.ifs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.wmfs.apis.*; import gplx.xowa.wikis.data.tbls.*;
public class Pfunc_ifexist_mgr {
	private Xowd_page_itm db_page = Xowd_page_itm.new_tmp();
	private Hash_adp regy = Hash_adp_bry.cs();
	public void Clear() {regy.Clear();}
	public boolean Exists(Xowe_wiki wiki, byte[] raw_bry) {
		if (Bry_.Len_eq_0(raw_bry)) return false;	// return early; NOTE: {{autolink}} can pass in "" (see test)
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, raw_bry); if (ttl == null) return false;
		byte[] ttl_bry = ttl.Page_db();	// NOTE: must use Page_db; EX: {{#ifexist:File:Peter & Paul fortress in SPB 03.jpg|y|n}}
		Object exists_obj = regy.Get_by(ttl_bry);
		if (exists_obj != null) return ((Pfunc_ifexist_itm)exists_obj).Exists();
		Pfunc_ifexist_itm exists_itm = new Pfunc_ifexist_itm(ttl_bry);
		regy.Add(ttl_bry, exists_itm);
		db_page.Clear();
		Xow_ns ttl_ns = ttl.Ns();
		boolean rv = false;
		switch (ttl_ns.Id()) {
			case Xow_ns_.Id_special:	rv = true; break; // NOTE: some pages call for [[Special]]; always return true for now; DATE:2014-07-17
			case Xow_ns_.Id_media:		rv = Find_ttl_for_media_ns(exists_itm, wiki, ttl_ns, ttl_bry); break;
			default:					rv = Find_ttl_in_db(exists_itm, wiki, ttl_ns, ttl_bry); break;
		}
		exists_itm.Exists_(rv);
		return rv;
	}
	private boolean Find_ttl_in_db(Pfunc_ifexist_itm itm, Xowe_wiki wiki, Xow_ns ns, byte[] ttl_bry) {
		boolean rv = wiki.Db_mgr().Load_mgr().Load_by_ttl(db_page, ns, ttl_bry);
		if (	!rv
			&&	wiki.Lang().Vnt_mgr().Enabled()) {
			Xowd_page_itm page = wiki.Lang().Vnt_mgr().Convert_ttl(wiki, ns, ttl_bry);
			if (page != Xowd_page_itm.Null)
				rv = page.Exists();
		}
		itm.Exists_(rv);
		return rv;
	}
	private boolean Find_ttl_for_media_ns(Pfunc_ifexist_itm itm, Xowe_wiki wiki, Xow_ns ns, byte[] ttl_bry) {
		Xow_ns file_ns = wiki.Ns_mgr().Ns_file();
		boolean rv = Find_ttl_in_db(itm, wiki, file_ns, ttl_bry); if (rv) return true;		// rarely true, but check local wiki's [[File:]] table anyway
		Xowe_wiki commons_wiki = wiki.Appe().Wiki_mgr().Wiki_commons();
		boolean env_is_testing = Env_.Mode_testing();
		if (	commons_wiki != null														// null check
			&&	(	commons_wiki.Init_assert().Db_mgr().Tid() == gplx.xowa.dbs.Xodb_mgr_sql.Tid_sql	// make sure tid=sql; tid=txt automatically created for online images; DATE:2014-09-21
				||	env_is_testing
				)
			) {
			file_ns = commons_wiki.Ns_mgr().Ns_file();
			return Find_ttl_in_db(itm, commons_wiki, file_ns, ttl_bry);						// accurate test using page table in commons wiki (provided commons is up to date)
		}
		else {
			if (!env_is_testing)
				wiki.File_mgr().Init_file_mgr_by_load(wiki);								// NOTE: must init Fsdb_mgr (else conn == null), and with bin_wkrs (else no images will ever load); DATE:2014-09-21
			return wiki.File_mgr().Exists(ttl_bry);											// less-accurate test using either (1) orig_wiki table in local wiki (v2) or (2) meta_db_mgr (v1)
		}
	}
 	}
class Pfunc_ifexist_itm {
	public Pfunc_ifexist_itm(byte[] ttl) {this.ttl = ttl;}
	public byte[] Ttl() {return ttl;} private byte[] ttl;
	public boolean Exists() {return exists;} public void Exists_(boolean v) {exists = v;} private boolean exists;
}