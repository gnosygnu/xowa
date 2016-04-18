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
package gplx.xowa.addons.wikis.randoms.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.randoms.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
public class Rndm_bldr_cmd extends Xob_cmd__base {
	private int rndm_uid = 0; private String rndm_where_sql = "AND ~{page_namespace} = 0 AND ~{page_is_redirect} = 0"; private int rndm_interval = 1000;
	public Rndm_bldr_cmd(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		// wiki.Special_mgr().Page_random().Mgr().Rebuild(rndm_uid, rndm_where_sql, rndm_interval);
            Tfds.Write(rndm_uid, rndm_where_sql, rndm_interval);
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_rndm_uid_))			rndm_uid = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_rndm_where_sql_))	rndm_where_sql = m.ReadStr("v");
		else if	(ctx.Match(k, Invk_rndm_interval_))		rndm_interval = m.ReadInt("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_rndm_uid_ = "rndm_uid_", Invk_rndm_where_sql_ = "rndm_where_sql_", Invk_rndm_interval_ = "rndm_interval_";

	public static final String BLDR_CMD_KEY = "wiki.random";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Rndm_bldr_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Rndm_bldr_cmd(bldr, wiki);}
}
