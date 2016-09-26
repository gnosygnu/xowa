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
package gplx.xowa.addons.bldrs.updates.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.updates.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;	
public class Xodel_small_cmd extends Xob_cmd__base {
	public Xodel_small_cmd(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	private final    int[] ext_max_ary = Xobldr__fsdb_db__delete_small_files_.New_ext_max_ary();
	@Override public void Cmd_run() {
		wiki.Init_assert();
		new Xodel_small_mgr().Exec(wiki, ext_max_ary);
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return this;}

	public static final String BLDR_CMD_KEY = "file.deletion_db.small_files";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;}
	public static final    Xob_cmd Prototype = new Xodel_small_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xodel_small_cmd(bldr, wiki);}
}
