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
package gplx.xowa.addons.bldrs.mass_parses.inits; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
public class Xomp_init_cmd extends Xob_cmd__base {
	private final    Xomp_init_mgr mgr;
	public Xomp_init_cmd(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);
		mgr = new Xomp_init_mgr(wiki);
	}
	@Override public void Cmd_run() {
		wiki.Init_assert();
		mgr.Exec();
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__cfg))		return this;
		else	return super.Invk(ctx, ikey, k, m);
	}	private static final String Invk__cfg = "cfg";
	
	@Override public String Cmd_key() {return BLDR_CMD_KEY;}  private static final String BLDR_CMD_KEY = "wiki.mass_parse.init";
	public static final    Xob_cmd Prototype = new Xomp_init_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xomp_init_cmd(bldr, wiki);}
}
