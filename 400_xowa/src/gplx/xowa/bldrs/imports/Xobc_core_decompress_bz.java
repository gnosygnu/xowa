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
import gplx.ios.*; import gplx.threads.*; import gplx.xowa.bldrs.*; 
public class Xobc_core_decompress_bz extends Xob_itm_basic_base implements Xob_cmd {
	public Xobc_core_decompress_bz(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki);}		
	public String Cmd_key() {return KEY;} public static final String KEY = "core.decompress_bz2";
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run() {
		if (Io_mgr._.ExistsFil(trg)) return; // file already exists; don't decompress again
		usr_dlg.Note_many(GRP_KEY, "bgn", "decompressing ~{0}", src.Raw(), trg.Raw());
		Decompress(bldr.App(), src.Raw(), trg);
	}
	public void Cmd_end() {}
	public void Cmd_print() {}
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
	public static boolean Decompress(Xoa_app app, String src_fil, Io_url trg_fil) {
		Io_mgr._.CreateDirIfAbsent(trg_fil.OwnerDir());	// 7zip will fail if dir does not exist
		ProcessAdp decompress = app.Fsys_mgr().App_mgr().App_decompress_bz2();
		decompress.Prog_dlg_(app.Usr_dlg()).Run_mode_(ProcessAdp.Run_mode_async);
		decompress.Run(src_fil, trg_fil, trg_fil.OwnerDir().Xto_api());
		while (decompress.Exit_code() == ProcessAdp.Exit_init) {
			String size = gplx.ios.Io_size_.Xto_str(Io_mgr._.QueryFil(trg_fil).Size());
			app.Gui_wtr().Prog_many(GRP_KEY, "decompress", "decompressing: ~{0}", size);
			ThreadAdp_.Sleep(1000);
		}
		return true;
	}
}
