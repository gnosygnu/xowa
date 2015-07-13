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
import gplx.ios.*; import gplx.core.threads.*; import gplx.xowa.bldrs.*; 
public class Xob_decompress_bz2_cmd extends Xob_itm_basic_base implements Xob_cmd {
	public Xob_decompress_bz2_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}		
	public String Cmd_key() {return Xob_cmd_keys.Key_decompress_bz2;}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run() {
		if (Io_mgr.I.ExistsFil(trg)) return; // file already exists; don't decompress again
		usr_dlg.Note_many(GRP_KEY, "bgn", "decompressing ~{0}", src.Raw(), trg.Raw());
		Decompress(bldr.App(), src.Raw(), trg);
	}
	public void Cmd_end() {}
	public void Cmd_term() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_src_))				this.Src_(m.ReadIoUrl("v"));
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}	private static final String Invk_src_ = "src_";
	private void Src_(Io_url v) {
		src = v;
		trg = bldr.App().Fsys_mgr().Wiki_dir().GenSubFil_nest(wiki.Domain_str(), v.NameOnly());	// NOTE: NameOnly() will take "enwiki.xml.bz2" and make it "enwiki.xml"
	}	Io_url src, trg;
	static final String GRP_KEY = "xowa.bldr.cmd.decompress_bz2";
	public static boolean Decompress(Xoae_app app, String src_fil, Io_url trg_fil) {
		Io_mgr.I.CreateDirIfAbsent(trg_fil.OwnerDir());	// 7zip will fail if dir does not exist
		ProcessAdp decompress = app.Prog_mgr().App_decompress_bz2();
		decompress.Prog_dlg_(app.Usr_dlg()).Run_mode_(ProcessAdp.Run_mode_async);
		decompress.Run(src_fil, trg_fil, trg_fil.OwnerDir().Xto_api());
		while (decompress.Exit_code() == ProcessAdp.Exit_init) {
			String size = gplx.ios.Io_size_.To_str(Io_mgr.I.QueryFil(trg_fil).Size());
			app.Usr_dlg().Prog_many(GRP_KEY, "decompress", "decompressing: ~{0}", size);
			Thread_adp_.Sleep(1000);
		}
		return true;
	}
}
