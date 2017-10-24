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
package gplx.xowa.addons.apps.updates.js; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.xowa.guis.cbks.*;
public class Xojs_wkr__replace extends Xojs_wkr__base {
	private final    Io_url src_dir, trg_dir;
	private final    Io_url[] src_fils;
	public Xojs_wkr__replace(Xog_cbk_mgr cbk_mgr, Xog_cbk_trg cbk_trg, String js_cbk, Gfo_invk_cmd done_cbk, Io_url src_dir, Io_url trg_dir) {super(cbk_mgr, cbk_trg, js_cbk, done_cbk, null, "replacing");
		this.src_dir = src_dir; 
		this.trg_dir = trg_dir;
		this.src_fils = Io_mgr.Instance.QueryDir_args(src_dir).Recur_().ExecAsUrlAry();
		this.Prog_data_end_(src_fils.length);
	}
	public Keyval[] Failed() {return failed;} private Keyval[] failed = Keyval_.Ary_empty;
	@Override protected void Exec_run() {
		List_adp failed_list = List_adp_.New();

		int len = src_fils.length;
		for (int i = 0; i < len; i++) {
			if (this.Prog_notify_and_chk_if_suspended(i, len)) return;	// stop if canceled

			Io_url src_fil = src_fils[i];
			Io_url trg_fil = trg_dir.GenSubFil(src_fil.GenRelUrl_orEmpty(src_dir));
			try {
				Io_mgr.Instance.DeleteFil(trg_fil);	// delete first; will fail if file is in use
				Io_mgr.Instance.MoveFil_args(src_fil, trg_fil, true).Exec(); // replace with src file
			} catch (Exception exc) {
				Gfo_usr_dlg_.Instance.Log_many("failed to delete and move file; file=~{0} msg=~{1}", trg_fil.Raw(), Err_.Message_gplx_log(exc));
				failed_list.Add(Keyval_.new_(src_fil.Raw(), trg_fil.Raw()));
				
				// try {Io_mgr.Instance.CopyFil(src_fil, trg_fil, true);}	// try to copy file anyway
				// catch (Exception exc2) {Gfo_usr_dlg_.Instance.Log_many("failed to fopy file; file=~{0} msg=~{1}", trg_fil.Raw(), Err_.Message_gplx_log(exc2));}
			}
		}

		this.failed = (Keyval[])failed_list.To_ary_and_clear(Keyval.class);
	}
}
