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
public class Xoav_wiki_mgr implements Xoa_wiki_mgr {
	private final    Xoav_app app; private final    Ordered_hash hash = Ordered_hash_.New_bry();		
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
	public Xow_wiki		Make(byte[] domain_bry, Io_url wiki_root_dir) {return new Xowv_wiki(app, domain_bry, wiki_root_dir);}
	public Xow_wiki		Import_by_url(Io_url url) {return Xoa_wiki_mgr_.Import_by_url(app, this, url);}
	public void			Load_by_user_data() {
		Xoud_site_row[] ary = app.User().User_db_mgr().Site_mgr().Get_all();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xoud_site_row itm = ary[i];
			Xow_wiki wiki = Make(Bry_.new_u8(itm.Domain()), Io_url_.new_dir_(itm.Path()));
			this.Add(wiki);
		}
	}
}
