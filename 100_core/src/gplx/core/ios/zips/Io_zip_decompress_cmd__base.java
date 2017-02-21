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
package gplx.core.ios.zips; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
import gplx.core.progs.*;
public abstract class Io_zip_decompress_cmd__base implements Io_zip_decompress_cmd {
	private Io_url checkpoint_url;
	private String resume_name;
	private long resume_file, resume_item;
	private long checkpoint_interval = 32 * Io_mgr.Len_mb, checkpoint_nxt = 0;
	public String Fail_msg() {return fail_msg;} private String fail_msg;
	public abstract Io_zip_decompress_cmd Make_new();
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public long Prog_data_cur() {return resume_file;}
	public byte Exec(gplx.core.progs.Gfo_prog_ui prog_ui, Io_url src_fil, Io_url trg_dir, List_adp trg_fils) {
		this.Checkpoint__load_by_src_fil(src_fil);
		this.checkpoint_nxt = resume_file + checkpoint_interval;
		this.fail_msg = null;

		byte status = this.Exec_hook(prog_ui, src_fil, trg_dir, trg_fils, resume_name, resume_file, resume_item);
		switch (status) {
			case Gfo_prog_ui_.Status__done:
			case Gfo_prog_ui_.Status__fail: 
				this.Exec_cleanup();
				break;
			case Gfo_prog_ui_.Status__suspended:
				break;
		}
		return status;
	}
	protected abstract byte Exec_hook(gplx.core.progs.Gfo_prog_ui prog_ui, Io_url src_fil, Io_url trg_dir, List_adp trg_fils, String resume_name, long resume_file, long resume_item);
	public void Exec_cleanup() {
		if (checkpoint_url != null) Io_mgr.Instance.DeleteFil(checkpoint_url);
	}
	public long Checkpoint__load_by_src_fil(Io_url src_fil) {
		this.checkpoint_url = src_fil.GenNewExt(".checkpoint");
		this.Checkpoint__load();
		return resume_file;
	}
	private void Checkpoint__load() {
		this.resume_name = null; this.resume_file = resume_item = 0;
		byte[] data = Io_mgr.Instance.LoadFilBryOrNull(checkpoint_url); if (data == null) return;
		byte[][] lines = Bry_split_.Split_lines(data); if (lines.length != 3) return;
		this.resume_name = String_.new_u8(lines[0]);
		this.resume_file = Long_.parse_or(String_.new_a7(lines[1]), -1); if (resume_file == -1) return;
		this.resume_item = Long_.parse_or(String_.new_a7(lines[2]), -1);
	}
	public boolean Checkpoint__save(String resume_name, long resume_file, long resume_item) {
		if (resume_file < checkpoint_nxt) return false;
		bfr.Add_str_u8(resume_name).Add_byte_nl();
		bfr.Add_long_variable(resume_file).Add_byte_nl();
		bfr.Add_long_variable(resume_item);
		Io_mgr.Instance.SaveFilBry(checkpoint_url, bfr.To_bry_and_clear());
		this.resume_name = resume_name;
		this.resume_file = resume_file;
		this.resume_item = resume_item;
		checkpoint_nxt += checkpoint_interval;
		return true;
	}
}
