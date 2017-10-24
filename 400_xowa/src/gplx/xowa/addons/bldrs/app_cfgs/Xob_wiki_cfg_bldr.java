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
package gplx.xowa.addons.bldrs.app_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.core.strings.*;
import gplx.xowa.bldrs.*;
public class Xob_wiki_cfg_bldr implements Gfo_invk {
	public Xob_wiki_cfg_bldr(Xob_bldr bldr) {this.app = bldr.App();} private Xoae_app app;
	public void Exec() {
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			Xoac_wiki_cfg_bldr_fil fil = (Xoac_wiki_cfg_bldr_fil)hash.Get_at(i);
			Exec_fil(fil);
		}
	}
	private void Exec_fil(Xoac_wiki_cfg_bldr_fil fil) {
		String wiki_key = fil.Wiki();
		Io_url cfg_file = app.Fsys_mgr().Cfg_wiki_core_dir().GenSubFil(wiki_key + ".gfs");
		String cfg_text = Io_mgr.Instance.LoadFilStr_args(cfg_file).MissingIgnored_().Exec();
		int len = fil.Itms_count();
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < len; i++) {
			Xoac_wiki_cfg_bldr_cmd cmd = fil.Itms_get_at(i);
			cfg_text = cmd.Exec(sb, wiki_key, cfg_text);
		}
		Io_mgr.Instance.SaveFilStr(cfg_file, cfg_text);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))		 		return Itms_get_or_new(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_run))		 		Exec();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_get = "get", Invk_run = "run";
	public void Clear() {hash.Clear();}
	public Xoac_wiki_cfg_bldr_fil Itms_get_or_new(String wiki) {
		Xoac_wiki_cfg_bldr_fil rv = (Xoac_wiki_cfg_bldr_fil)hash.Get_by(wiki);
		if (rv == null) {
			rv = new Xoac_wiki_cfg_bldr_fil(wiki);
			hash.Add(wiki, rv);
		}
		return rv;
	}	private Ordered_hash hash = Ordered_hash_.New();
}
