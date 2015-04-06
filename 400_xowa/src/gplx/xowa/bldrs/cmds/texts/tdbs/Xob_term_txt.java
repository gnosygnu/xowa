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
package gplx.xowa.bldrs.cmds.texts.tdbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.xowa.bldrs.cmds.texts.*;
public class Xob_term_txt extends Xob_term_base {
	public Xob_term_txt(Xob_bldr bldr, Xowe_wiki wiki) {this.Ctor(bldr, wiki); this.wiki = wiki;} private Xowe_wiki wiki;
	@Override public String Cmd_key() {return Xob_cmd_keys.Key_tdb_core_term;}
	@Override public void Cmd_end_hook() {
		Io_mgr._.SaveFilBry(wiki.Tdb_fsys_mgr().Cfg_wiki_core_fil(), wiki.Cfg_wiki_core().Build_gfs());
	}
}
