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
public class Pfunc_ifexist_mgr {
	private Xodb_page db_page = Xodb_page.tmp_();
	private HashAdp regy = HashAdp_.new_bry_();
	public void Clear() {regy.Clear();}
	public boolean Exists(Xow_wiki wiki, byte[] raw_bry) {
		if (Bry_.Len_eq_0(raw_bry)) return false;	// return early; NOTE: {{autolink}} can pass in "" (see test)
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, raw_bry); if (ttl == null) return false;
		byte[] ttl_bry = ttl.Page_db();	// NOTE: must use Page_db; EX: {{#ifexist:File:Peter & Paul fortress in SPB 03.jpg|y|n}}
		Object exists_obj = regy.Fetch(ttl_bry);
		if (exists_obj != null) return ((Pfunc_ifexist_itm)exists_obj).Exists();
		Pfunc_ifexist_itm exists_itm = new Pfunc_ifexist_itm(ttl_bry);
		regy.Add(ttl_bry, exists_itm);
		db_page.Clear();
		Xow_ns ttl_ns = ttl.Ns();
		switch (ttl_ns.Id()) {
			case Xow_ns_.Id_special:
				wiki.App().Usr_dlg().Warn_many("", "", "ifexist.special ns page; page=~{0} ifexist=~{1}", wiki.Ctx().Cur_page().Url().X_to_full_str_safe(), String_.new_utf8_(raw_bry));
				exists_itm.Exists_(true);
				return true;
			case Xow_ns_.Id_media:		return Find_ttl_for_media_ns(exists_itm, wiki, ttl_ns, ttl_bry);
			default:					return Find_ttl_in_db(exists_itm, wiki, ttl_ns, ttl_bry);
		}
	}
	private boolean Find_ttl_in_db(Pfunc_ifexist_itm itm, Xow_wiki wiki, Xow_ns ns, byte[] ttl_bry) {
		boolean rv = wiki.Db_mgr().Load_mgr().Load_by_ttl(db_page, ns, ttl_bry);
		if (	!rv
			&&	wiki.Lang().Vnt_mgr().Enabled()) {
			Xodb_page page = wiki.Lang().Vnt_mgr().Convert_ttl(wiki, ns, ttl_bry);
			if (page != Xodb_page.Null)
				rv = page.Exists();
		}
		itm.Exists_(rv);
		return rv;
	}
	private boolean Find_ttl_for_media_ns(Pfunc_ifexist_itm itm, Xow_wiki wiki, Xow_ns ns, byte[] ttl_bry) {
		Xow_ns file_ns = wiki.Ns_mgr().Ns_file();
		boolean rv = Find_ttl_in_db(itm, wiki, file_ns, ttl_bry); if (rv) return true;		// rarely true, but check first anyway
		Xow_wiki commons_wiki = wiki.App().Wiki_mgr().Wiki_commons();
		if (commons_wiki == null)
			return wiki.File_mgr().Exists(ttl_bry);										// user may not have commons_wiki; try to check files
		else {
			file_ns = commons_wiki.Ns_mgr().Ns_file();
			return Find_ttl_in_db(itm, commons_wiki, file_ns, ttl_bry);					// accurate test of whether or not Media file exists
		}
	}
 	}
class Pfunc_ifexist_itm {
	public Pfunc_ifexist_itm(byte[] ttl) {this.ttl = ttl;}
	public byte[] Ttl() {return ttl;} private byte[] ttl;
	public boolean Exists() {return exists;} public void Exists_(boolean v) {exists = v;} private boolean exists;
}