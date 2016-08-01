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
import gplx.xowa.xtns.wbases.imports.*;
public class Xob_init_tdb extends Xob_init_base {
	public Xob_init_tdb(Xob_bldr bldr, Xowe_wiki wiki) {this.Ctor(bldr, wiki);}
	@Override public String Cmd_key() {return Xob_cmd_keys.Key_tdb_text_init;}
	@Override public void Cmd_ini_wdata(Xob_bldr bldr, Xowe_wiki wiki) {
		bldr.Cmd_mgr().Add_cmd(wiki, Xob_cmd_keys.Key_tdb_text_wdata_qid);
		bldr.Cmd_mgr().Add_cmd(wiki, Xob_cmd_keys.Key_tdb_text_wdata_pid);
	}
	@Override public void Cmd_run_end(Xowe_wiki wiki) {}
}
