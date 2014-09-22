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
package gplx.xowa.hdumps; import gplx.*; import gplx.xowa.*;
import gplx.xowa.langs.cases.*;
public class Xoav_wiki_mgr {
	private final Xoav_app app; private final OrderedHash hash = OrderedHash_.new_bry_();
	private Io_url wiki_root_dir;
	public Xoav_wiki_mgr(Xoav_app app, Io_url wiki_root_dir, Xol_case_mgr case_mgr) {this.app = app; this.wiki_root_dir = wiki_root_dir;}
	public Xowv_wiki Get_by_domain(byte[] domain) {return (Xowv_wiki)hash.Fetch(domain);}
	public void Load_default() {
		Io_url[] wiki_dirs = Io_mgr._.QueryDir_args(wiki_root_dir).DirOnly_().ExecAsUrlAry();
		for (Io_url wiki_dir : wiki_dirs) {
			String wiki_dir_url = wiki_dir.Raw();
			if (String_.HasAtBgn(wiki_dir_url, "#")) continue;
			Xowv_wiki wiki = new Xowv_wiki(app, wiki_dir.NameOnly(), wiki_dir);
			hash.Add(wiki.Domain_bry(), wiki);
		}
	}
}
