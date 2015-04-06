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
import gplx.xowa.bldrs.*; import gplx.xowa.tdbs.*;
public class Xob_deploy_copy_cmd extends Xob_itm_basic_base implements Xob_cmd, GfoInvkAble {
	public Xob_deploy_copy_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return Xob_cmd_keys.Key_deploy_copy;}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_run() {
		Io_url src_root_dir = wiki.Fsys_mgr().Root_dir();
		Xotdb_fsys_mgr url_mgr = wiki.Tdb_fsys_mgr();
		Copy_dir_root(src_root_dir, Xotdb_dir_info_.Name_cfg);
		Copy_dir_ns(url_mgr.Ns_dir(), Xotdb_dir_info_.Name_page);
		Copy_dir_ns(url_mgr.Ns_dir(), Xotdb_dir_info_.Name_title);
		Copy_dir_root(src_root_dir, Xotdb_dir_info_.Name_site, Xotdb_dir_info_.Name_category);
		Copy_dir_root(src_root_dir, Xotdb_dir_info_.Name_site, Xotdb_dir_info_.Name_id);
		Copy_dir_root(src_root_dir, Xotdb_dir_info_.Name_site, Xotdb_dir_info_.Name_search_ttl);
	}
	public void Cmd_term() {}
	private void Copy_dir_ns(Io_url root, String name) {
		Io_url[] ns_dirs = Io_mgr._.QueryDir_args(root).DirOnly_().ExecAsUrlAry();
		for (int i = 0; i < ns_dirs.length; i++) {
			Io_url ns_dir = ns_dirs[i];
			Io_url src_sub_dir = ns_dir.GenSubDir(name);
			String dir_name = name;
			if (zip) {
				Io_url src_zip_dir = ns_dir.GenSubDir(name + Xob_deploy_zip_cmd.Dir_zip_suffix);
				if (Io_mgr._.ExistsDir(src_zip_dir)) {
					src_sub_dir = src_zip_dir;
					dir_name = name + Xob_deploy_zip_cmd.Dir_zip_suffix;
				}
			}
			Copy_dir(src_sub_dir, trg_root_dir.GenSubDir_nest(Xotdb_dir_info_.Name_ns, ns_dir.NameOnly(), dir_name));
		}
	}
	private void Copy_dir_root(Io_url src_root_dir, String... sub_dirs) {
		Io_url src = src_root_dir.GenSubDir_nest(sub_dirs);
		Io_url trg = trg_root_dir.GenSubDir_nest(sub_dirs);
		Copy_dir(src, trg);
	}
	private void Copy_dir(Io_url src, Io_url trg) {
		bldr.Usr_dlg().Prog_many(GRP_KEY, "copy", "copying to ~{1}", src.Xto_api(), trg.Xto_api());
		Io_mgr._.CopyDirDeep(src, trg);
	}
	boolean zip; Io_url trg_root_dir;
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_trg_dir_))	{trg_root_dir = m.ReadIoUrl("val");}
		else if	(ctx.Match(k, Invk_zip_))		{zip = m.ReadBoolOrFalse("val");}
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}	private static final String Invk_trg_dir_ = "trg_dir_", Invk_zip_ = "zip_";
	static final String GRP_KEY = "xowa.bldr.copy";
}
