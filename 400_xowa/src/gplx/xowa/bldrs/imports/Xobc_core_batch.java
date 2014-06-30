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
package gplx.xowa.bldrs.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public class Xobc_core_batch implements GfoInvkAble {
	public Xobc_core_batch(Xob_bldr bldr, byte[] raw) {this.bldr = bldr; fmtr.Fmt_(raw);} private Xob_bldr bldr;
	Bry_fmtr fmtr = Bry_fmtr.keys_("bz2_fil", "wiki_key");
	private void Run() {
		Io_url[] bz2_fils = Io_mgr._.QueryDir_fils(bldr.App().Fsys_mgr().Wiki_dir().GenSubDir_nest(Dir_dump, "todo"));
		Bry_bfr bfr = Bry_bfr.reset_(Io_mgr.Len_kb);
		int bz2_fils_len = bz2_fils.length;
		Xob_bz2_file bz2_fil = new Xob_bz2_file();
		for (int i = 0; i < bz2_fils_len; i++) {
			Io_url bz2_fil_url = bz2_fils[i];
			bz2_fil.Fil_(bz2_fil_url).Parse(bz2_fil_url.NameOnly());
			fmtr.Bld_bfr_many(bfr, bz2_fil_url.Raw(), bz2_fil.Domain());
			bldr.Usr_dlg().Note_many(GRP_KEY, "bgn", "starting script for ~{0}", String_.new_utf8_(bz2_fil.Domain()));
			bldr.App().Gfs_mgr().Run_str(bfr.XtoStrAndClear());
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner))			return bldr.Cmd_mgr();
		else if	(ctx.Match(k, Invk_run))			Run();
		return this;
	}	private static final String Invk_owner = "owner", Invk_run = "run";
	public static String Dir_dump = "#dump";
	static final String GRP_KEY = "xowa.bldr.cmd.batch";
}
