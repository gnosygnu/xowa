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
