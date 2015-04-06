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
package gplx.xowa2.apps; import gplx.*; import gplx.xowa2.*;
import gplx.xowa.langs.cases.*; import gplx.xowa.users.data.*;
import gplx.xowa2.wikis.*;
public class Xoav_wiki_mgr implements GfoInvkAble {
	private final Xoav_app app; private final OrderedHash hash = OrderedHash_.new_bry_();		
	public Xoav_wiki_mgr(Xoav_app app, Xol_case_mgr case_mgr) {this.app = app;}
	public Xowv_wiki Get_by_domain(byte[] domain) {return (Xowv_wiki)hash.Fetch(domain);}
	public Xowv_wiki Import_by_fil(Io_url fil) {
		Io_url wiki_dir = fil.OwnerDir();
		Xowv_wiki rv = Load(Gen_domain_str(fil.NameOnly()), wiki_dir);
		app.User_data_mgr().Site_mgr().Import(rv.Domain_str(), rv.Domain_str(), wiki_dir.Raw(), "");
		return rv;
	}
	public void Load_by_user_data() {
		Xoud_site_row[] ary = app.User_data_mgr().Site_mgr().Get_all();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xoud_site_row itm = ary[i];
			Load(itm.Domain(), Io_url_.new_dir_(itm.Path()));
		}
	}
	public Xowv_wiki Load_by_fil(Io_url fil)		{
		return Load(Gen_domain_str(fil.NameOnly()), fil.OwnerDir());
	}
	public void Load_by_dir(Io_url wiki_root_dir)	{
		Io_url[] wiki_dirs = Io_mgr._.QueryDir_args(wiki_root_dir).DirOnly_().ExecAsUrlAry();
		for (Io_url wiki_dir : wiki_dirs) {
			String wiki_dir_url = wiki_dir.Raw();
			if (String_.HasAtBgn(wiki_dir_url, "#")) continue;
			Load(wiki_dir.NameOnly(), wiki_dir);
		}
	}
	private Xowv_wiki Load(String domain_str, Io_url wiki_dir) {
		byte[] domain_bry = Bry_.new_utf8_(domain_str);
		Xowv_wiki wiki = new Xowv_wiki(app, domain_bry, wiki_dir);
		hash.AddReplace(domain_bry, wiki);
		return wiki;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_import_by_fil))		return Import_by_fil(Io_url_.new_fil_(m.ReadStr("v")));
		else	return GfoInvkAble_.Rv_unhandled;
	}
	public static final String Invk_import_by_fil = "import_by_fil";
	private static String Gen_domain_str(String file_name) {
		int dash_pos = String_.FindFwd(file_name, "-");
		if (dash_pos != Bry_finder.Not_found) file_name = String_.Mid(file_name, 0, dash_pos);
		return file_name;
	}
}
