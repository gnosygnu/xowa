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
package gplx.xowa.addons.bldrs.centrals.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import gplx.core.progs.*; import gplx.core.security.*;
public class Xobc_cmd__verify_fil extends Xobc_cmd__base {
	private final    Io_url src_url; private final    byte[] expd_hash;
	public Xobc_cmd__verify_fil(Xobc_task_mgr task_mgr, int task_id, int step_id, int cmd_id, Io_url src_url, String expd_hash_str, long prog_data_end) {super(task_mgr, task_id, step_id, cmd_id);
		this.src_url = src_url;
		this.expd_hash = Bry_.new_a7(expd_hash_str);
		this.Prog_data_end_(prog_data_end);
	}
	@Override public String Cmd_type() {return CMD_TYPE;} public static final    String CMD_TYPE = "xowa.core.hash_fil";
	@Override public String Cmd_name() {return "verify";}
	@Override public String Cmd_fallback() {return Xobc_cmd__download.CMD_TYPE;}

	@Override protected void Cmd_exec_hook(Xobc_cmd_ctx ctx) {
		Hash_algo algo = Hash_algo_.New__md5();
		gplx.core.ios.streams.IoStream stream = Io_mgr.Instance.OpenStreamRead(src_url);
		byte[] actl_hash = Bry_.Empty;
		try {actl_hash = algo.Hash_stream_as_bry(this, stream);}
		finally {stream.Rls();}
		if (this.Prog_status() != Gfo_prog_ui_.Status__suspended && !Bry_.Eq(expd_hash, actl_hash))
			this.Cmd_exec_err_(Xobc_cmd__verify_fil.Err_make(actl_hash, expd_hash));
	}

	public static String Err_make(byte[] actl, byte[] expd) {return String_.Format("bad hash: bad={0} good={1}", String_.new_u8(actl), String_.new_u8(expd));}
}
