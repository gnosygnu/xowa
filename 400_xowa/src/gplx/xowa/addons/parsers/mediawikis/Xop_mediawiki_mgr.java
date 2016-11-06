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
package gplx.xowa.addons.parsers.mediawikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.parsers.*;
public class Xop_mediawiki_mgr {
	private final    Xoae_app app;
	public Xop_mediawiki_mgr(Io_url root_dir) {
		Gfo_usr_dlg usr_dlg = Xoa_app_.New__usr_dlg__console();
		Gfo_usr_dlg_.Instance = usr_dlg;

		this.app = new Xoae_app(usr_dlg, gplx.xowa.apps.Xoa_app_mode.Itm_cmd
		, root_dir
		, root_dir.GenSubDir("wiki")
		, root_dir.GenSubDir("file")
		, root_dir.GenSubDir("user")
		, root_dir.GenSubDir_nest("user", "anonymous", "wiki")
		, gplx.xowa.apps.boots.Xoa_cmd_arg_mgr.Bin_dir_name()
		);
	}
	public Xop_mediawiki_wkr Make(String domain_str) {
		Xowe_wiki wiki = (Xowe_wiki)app.Wiki_mgr().Make(Bry_.new_u8(domain_str), app.Fsys_mgr().Wiki_dir());
		return new Xop_mediawiki_wkr(wiki);
	}
}
