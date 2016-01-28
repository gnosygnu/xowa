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
package gplx.xowa.bldrs.cmds.randoms; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
public class Xob_rnd_cmd implements Xob_cmd {
	// private final Xob_bldr bldr; private final Xowe_wiki wiki;
	public Xob_rnd_cmd(Xob_bldr bldr, Xowe_wiki wiki) {}//this.bldr = bldr; this.wiki = wiki;}
	public String Cmd_key()		{return Xob_cmd_keys.Key_util_random;}
	public void Cmd_run() {
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return this;}

	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_term() {}
}
