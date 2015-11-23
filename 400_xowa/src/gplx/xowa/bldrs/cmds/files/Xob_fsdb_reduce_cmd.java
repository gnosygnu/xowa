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
package gplx.xowa.bldrs.cmds.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.core.envs.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.fsdb.meta.*;
import gplx.xowa.bldrs.wkrs.*;
public class Xob_fsdb_reduce_cmd extends Xob_itm_basic_base implements Xob_cmd {
	public Xob_fsdb_reduce_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return Xob_cmd_keys.Key_file_fsdb_reduce;}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run() {Exec_main();}
	public void Cmd_end() {}
	public void Cmd_term() {}
	private void Exec_main() {
		/*
		Open_bin_dir
		Iterate_files
			Convert_bin
			Mark_meta_record				
		*/
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return this;
	}
}
class Fsdb_reduce_wkr {
	private final Process_adp convert_process;
	private final Io_url src_url, trg_url;
	private final int dpi, quality;
	public Fsdb_reduce_wkr(Process_adp convert_process, Io_url tmp_dir, int dpi, int quality) {
		this.convert_process = convert_process;
		this.src_url = tmp_dir.GenSubFil("fsdb_reduce.src.bin");
		this.trg_url = tmp_dir.GenSubFil("fsdb_reduce.trg.bin");
		this.dpi = dpi;
		this.quality = quality;
	}
	public byte[] Reduce(byte[] orig_bry) {
		Io_mgr.Instance.SaveFilBry(src_url, orig_bry);
		convert_process.Run(src_url, trg_url, dpi, quality); // -strip -quality 50% -density 72 -resample 72
		if (!convert_process.Exit_code_pass()) {
			// throw err with convert_process.Rslt_out();
		}
		byte[] rv = Io_mgr.Instance.LoadFilBry(trg_url);
		// fail if 0; fail if greater than;
		// warn if not between 50% - 70% of size
		return rv;
	}
}
