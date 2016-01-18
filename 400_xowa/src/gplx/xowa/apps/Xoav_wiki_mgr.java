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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
import gplx.xowa.*; import gplx.xowa.langs.cases.*; import gplx.xowa.users.data.*;
import gplx.xowa.wikis.*;
public class Xoav_wiki_mgr implements Xoa_wiki_mgr, GfoInvkAble {
	private final Xoav_app app; private final Ordered_hash hash = Ordered_hash_.New_bry();		
	public Xoav_wiki_mgr(Xoav_app app, Xol_case_mgr case_mgr) {this.app = app;}
	public int			Count()								{return hash.Count();}
	public boolean			Has(byte[] key)						{return hash.Has(key);}
	public Xow_wiki		Get_at(int idx)						{return (Xow_wiki)hash.Get_at(idx);}
	public Xow_wiki		Get_by_or_null(byte[] key)			{return (Xow_wiki)hash.Get_by(key);}
	public Xow_wiki		Get_by_or_make_init_y(byte[] key) {
		Xow_wiki rv = this.Get_by_or_null(key);
		// if (rv == null) rv = New_wiki(key);	// TODO: must init wiki, but need wiki_url; DATE:2015-05-23
		return rv;
	}
	public Xow_wiki		Get_by_or_make_init_n(byte[] key) {return Get_by_or_make_init_y(key);}
	public void			Add(Xow_wiki wiki) {hash.Add_if_dupe_use_nth(wiki.Domain_bry(), wiki);}
	public Xow_wiki		Load_by_fil(Io_url fil) {
		Io_url wiki_dir = fil.OwnerDir();
		Xow_wiki rv = Load(Gen_domain_str(fil.NameOnly()), wiki_dir);
		app.User().User_db_mgr().Site_mgr().Import(rv.Domain_str(), rv.Domain_str(), wiki_dir.Raw(), "");
		return rv;
	}
	public void			Load_by_user_data() {
		Xoud_site_row[] ary = app.User().User_db_mgr().Site_mgr().Get_all();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xoud_site_row itm = ary[i];
			Load(itm.Domain(), Io_url_.new_dir_(itm.Path()));
		}
	}
	private Xow_wiki Load(String domain_str, Io_url wiki_dir) {
		byte[] domain_bry = Bry_.new_u8(domain_str);
		Xow_wiki rv = new Xowv_wiki(app, domain_bry, wiki_dir);
		hash.Add_if_dupe_use_nth(domain_bry, rv);
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_import_by_fil))		return Load_by_fil(Io_url_.new_fil_(m.ReadStr("v")));
		else	return GfoInvkAble_.Rv_unhandled;
	}
	public static final String Invk_import_by_fil = "import_by_fil";
	private static String Gen_domain_str(String file_name) {
		int dash_pos = String_.FindFwd(file_name, "-");
		if (dash_pos != Bry_find_.Not_found) file_name = String_.Mid(file_name, 0, dash_pos);
		return file_name;
	}
}
