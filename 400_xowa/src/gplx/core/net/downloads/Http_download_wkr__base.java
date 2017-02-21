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
package gplx.core.net.downloads; import gplx.*; import gplx.core.*; import gplx.core.net.*;
import gplx.core.progs.*;
public abstract class Http_download_wkr__base implements Http_download_wkr {
	private long expd_size;
	private Io_url tmp_url, checkpoint_url;
	private long downloaded;
	private long checkpoint_interval = 1024 * 1024, checkpoint_nxt = 0;
	protected Io_url Checkpoint_url() {return checkpoint_url;}
	protected Io_url Trg_url() {return trg_url;} private Io_url trg_url;
	public Io_url Tmp_url() {return tmp_url;}
	public String Fail_msg() {return fail_msg;} private String fail_msg;
	public abstract Http_download_wkr Make_new();
	public byte Exec(gplx.core.progs.Gfo_prog_ui prog_ui, String src_str, Io_url trg_url, long expd_size_val) {
		this.trg_url = trg_url;
		this.downloaded = this.Checkpoint__load_by_trg_fil(trg_url);
		this.checkpoint_nxt = downloaded + checkpoint_interval;
		this.expd_size = expd_size_val;
		this.fail_msg = null;

		byte status = Gfo_prog_ui_.Status__fail;
		try {status = this.Exec_hook(prog_ui, src_str, tmp_url, downloaded);}
		catch (Exception e) {
			status = Gfo_prog_ui_.Status__fail;
			fail_msg = Err_.Message_lang(e);
		}
		switch (status) {
			case Gfo_prog_ui_.Status__done: {
				if (expd_size_val != -1) {
					long actl_size = Io_mgr.Instance.QueryFil(tmp_url).Size();
					if (expd_size != actl_size) {
						this.fail_msg = String_.Format("bad size: bad={0} good={1}", actl_size, expd_size);
						return Gfo_prog_ui_.Status__fail;
					}
				}
				Io_mgr.Instance.MoveFil_args(tmp_url, trg_url, true).Exec();
				this.Exec_cleanup();
				break;
			}
			case Gfo_prog_ui_.Status__suspended:
			case Gfo_prog_ui_.Status__fail: {
				break;
			}
		}
		return status;
	}
	protected abstract byte Exec_hook(gplx.core.progs.Gfo_prog_ui prog_ui, String src_str, Io_url trg_url, long downloaded);
	public void Exec_cleanup() {
		if (tmp_url != null) Io_mgr.Instance.DeleteFil(tmp_url);
		if (checkpoint_url != null) Io_mgr.Instance.DeleteFil(checkpoint_url);
	}
	public long Checkpoint__load_by_trg_fil(Io_url trg_url) {
		this.tmp_url = trg_url.GenNewExt(".tmp");
		this.checkpoint_url = trg_url.GenNewExt(".checkpoint");
		return this.Checkpoint__load();
	}
	private long Checkpoint__load() {
		byte[] data = Io_mgr.Instance.LoadFilBryOrNull(checkpoint_url);
		return data == null ? 0 : Long_.parse_or(String_.new_a7(data), 0);
	}
	public void Checkpoint__save(long new_val) {
		if (new_val < checkpoint_nxt) return;
		Io_mgr.Instance.SaveFilStr(checkpoint_url, Long_.To_str(new_val));
		downloaded = new_val;
		checkpoint_nxt += checkpoint_interval;
	}
}
