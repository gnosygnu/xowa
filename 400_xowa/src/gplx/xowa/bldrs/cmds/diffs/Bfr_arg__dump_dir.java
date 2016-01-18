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
package gplx.xowa.bldrs.cmds.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.core.brys.*; import gplx.xowa.wikis.*;
class Bfr_arg__dump_dir implements Bfr_arg {	// .dump_dir = "C:\xowa\wiki\en.wikipedia.org"
	private final Xow_wiki wiki;
	public Bfr_arg__dump_dir(Xow_wiki wiki) {this.wiki = wiki;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		bfr.Add(wiki.Fsys_mgr().Root_dir().RawBry());
	}
}
class Bfr_arg__dump_core implements Bfr_arg {// .dump_core = "en.wikipedia.org-core.xowa"
	private final Xow_wiki wiki;
	public Bfr_arg__dump_core(Xow_wiki wiki) {this.wiki = wiki;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		bfr.Add(wiki.Data__core_mgr().Db__core().Url().RawBry());
	}
}
class Bfr_arg__dump_domain implements Bfr_arg {// .dump_domain = en.wikipedia.org
	private final Xow_wiki wiki;
	public Bfr_arg__dump_domain(Xow_wiki wiki) {this.wiki = wiki;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		bfr.Add(wiki.Domain_bry());
	}
}
