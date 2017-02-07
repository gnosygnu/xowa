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
package gplx.xowa.bldrs.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.ios.*;
import gplx.xowa.apps.*;
public abstract class Xob_itm_dump_base extends Xob_itm_basic_base {
	protected int sort_mem_len = Int_.Neg1, dump_fil_len = Int_.Neg1, make_fil_len = Int_.Neg1;
	public Io_url Temp_dir() {return temp_dir;}
	public boolean Delete_temp() {return delete_temp;} protected boolean delete_temp = true;
	protected Io_url temp_dir, make_dir;
	protected Bry_bfr dump_bfr;
	public int Make_fil_len() {return make_fil_len;} public void Make_fil_len_(int v) {make_fil_len = v;}
	public Io_url_gen Dump_url_gen() {return dump_url_gen;} protected Io_url_gen dump_url_gen;
	public void Init_dump(String tmp_dir_key) {Init_dump(tmp_dir_key, null);}
	public void Init_dump(String tmp_dir_key, Io_url make_dir_val) {
		if (sort_mem_len == Int_.Neg1) sort_mem_len = bldr.Sort_mem_len();
		if (dump_fil_len == Int_.Neg1) dump_fil_len = bldr.Dump_fil_len();
		if (make_fil_len == Int_.Neg1) make_fil_len = bldr.Make_fil_len();
		dump_bfr = Bry_bfr_.New_w_size(dump_fil_len);
		temp_dir = wiki.Fsys_mgr().Tmp_dir().GenSubDir(tmp_dir_key);
		if (make_dir_val == null)	make_dir = temp_dir.GenSubDir("make");
		else						make_dir = make_dir_val;
		Io_mgr.Instance.DeleteDirDeep_ary(temp_dir, make_dir);
		dump_url_gen = Io_url_gen_.dir_(temp_dir.GenSubDir("dump"));
	}
	@gplx.Virtual public void Term_dump(Io_sort_cmd make_cmd) {
		Io_mgr.Instance.AppendFilBfr(dump_url_gen.Nxt_url(), dump_bfr); dump_bfr.Rls();
		Xobdc_merger.Basic(bldr.Usr_dlg(), dump_url_gen, temp_dir.GenSubDir("sort"), sort_mem_len, Io_line_rdr_key_gen_.first_pipe, make_cmd);
	}
	protected void Flush_dump() {Io_mgr.Instance.AppendFilBfr(dump_url_gen.Nxt_url(), dump_bfr);}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_sort_mem_len_))		sort_mem_len = gplx.core.ios.Io_size_.Load_int_(m);
		else if	(ctx.Match(k, Invk_dump_fil_len_)) 		dump_fil_len = gplx.core.ios.Io_size_.Load_int_(m);
		else if	(ctx.Match(k, Invk_make_fil_len_)) 		make_fil_len = gplx.core.ios.Io_size_.Load_int_(m);
		else if (ctx.Match(k, Invk_delete_temp_))		delete_temp = m.ReadBool("v");
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}
	public static final String Invk_sort_mem_len_ = "sort_mem_len_", Invk_dump_fil_len_ = "dump_fil_len_", Invk_make_fil_len_ = "make_fil_len_", Invk_delete_temp_ = "delete_temp_";
}
