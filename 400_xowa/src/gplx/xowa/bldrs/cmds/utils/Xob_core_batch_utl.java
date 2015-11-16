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
package gplx.xowa.bldrs.cmds.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.bldrs.wms.dumps.*;
public class Xob_core_batch_utl implements GfoInvkAble {
	private final Xob_bldr bldr;
	private final Bry_fmtr fmtr = Bry_fmtr.keys_("bz2_fil", "wiki_key");
	public Xob_core_batch_utl(Xob_bldr bldr, byte[] raw) {this.bldr = bldr; fmtr.Fmt_(raw);}
	private void Run() {
		Io_url[] bz2_fils = Io_mgr.Instance.QueryDir_fils(bldr.App().Fsys_mgr().Wiki_dir().GenSubDir_nest(Dir_dump, "todo"));
		Bry_bfr bfr = Bry_bfr.reset_(Io_mgr.Len_kb);
		int bz2_fils_len = bz2_fils.length;
		for (int i = 0; i < bz2_fils_len; i++) {
			Io_url bz2_fil_url = bz2_fils[i];
			Xowm_dump_file dump_file = Xowm_dump_file_.parse(Bry_.new_u8(bz2_fil_url.NameOnly()));
			String domain_str = dump_file.Domain_itm().Domain_str();
			fmtr.Bld_bfr_many(bfr, bz2_fil_url.Raw(), domain_str);
			bldr.Usr_dlg().Note_many("", "", "starting script for ~{0}", domain_str);
			bldr.App().Gfs_mgr().Run_str(bfr.To_str_and_clear());
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner))			return bldr.Cmd_mgr();
		else if	(ctx.Match(k, Invk_run))			Run();
		return this;
	}	private static final String Invk_owner = "owner", Invk_run = "run";
	public static String Dir_dump = "#dump";
}
