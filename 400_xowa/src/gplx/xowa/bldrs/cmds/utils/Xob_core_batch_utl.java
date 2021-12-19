/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.bldrs.cmds.utils;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.GfsCtx;
import gplx.libs.files.Io_mgr;
import gplx.libs.ios.IoConsts;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.libs.files.Io_url;
import gplx.xowa.bldrs.*;
import gplx.types.custom.brys.fmts.fmtrs.*;
import gplx.xowa.bldrs.wms.dumps.*;
public class Xob_core_batch_utl implements Gfo_invk {
	private final Xob_bldr bldr;
	private final BryFmtr fmtr = BryFmtr.NewKeys("bz2_fil", "wiki_key");
	public Xob_core_batch_utl(Xob_bldr bldr, byte[] raw) {this.bldr = bldr; fmtr.FmtSet(raw);}
	private void Run() {
		Io_url[] bz2_fils = Io_mgr.Instance.QueryDir_fils(bldr.App().Fsys_mgr().Wiki_dir().GenSubDir_nest(Dir_dump, "todo"));
		BryWtr bfr = BryWtr.NewAndReset(IoConsts.LenKB);
		int bz2_fils_len = bz2_fils.length;
		for (int i = 0; i < bz2_fils_len; i++) {
			Io_url bz2_fil_url = bz2_fils[i];
			Xowm_dump_file dump_file = Xowm_dump_file_.parse(BryUtl.NewU8(bz2_fil_url.NameOnly()));
			String domain_str = dump_file.Domain_itm().Domain_str();
			fmtr.BldToBfrMany(bfr, bz2_fil_url.Raw(), domain_str);
			bldr.Usr_dlg().Note_many("", "", "starting script for ~{0}", domain_str);
			bldr.App().Gfs_mgr().Run_str(bfr.ToStrAndClear());
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner))			return bldr.Cmd_mgr();
		else if	(ctx.Match(k, Invk_run))			Run();
		return this;
	}	private static final String Invk_owner = "owner", Invk_run = "run";
	public static String Dir_dump = "#dump";
}
