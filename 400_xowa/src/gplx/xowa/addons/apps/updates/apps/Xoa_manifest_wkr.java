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
package gplx.xowa.addons.apps.updates.apps;
import gplx.libs.files.Io_mgr;
import gplx.types.custom.brys.BryFind;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.StringUtl;
class Xoa_manifest_wkr {
	private final Xoa_manifest_view view;
	private final Xoa_manifest_list list = new Xoa_manifest_list();
	private Io_url manifest_url;
	private String run_xowa_cmd;
	public Xoa_manifest_wkr(Xoa_manifest_view view) {
		this.view = view;
	}
	public void Init(Io_url manifest_url) {
		// load manifest
		this.manifest_url = manifest_url;
		view.Append("loading manifest from: " + manifest_url.Raw());
		byte[] src = Io_mgr.Instance.LoadFilBry(manifest_url);

		// parse manifest
		int nl_pos = BryFind.FindFwd(src, AsciiByte.Nl);
		if (nl_pos == BryFind.NotFound) throw ErrUtl.NewArgs("could not find nl in manifest", "manifest_url", manifest_url.Raw());
		this.run_xowa_cmd = StringUtl.NewU8(src, 0, nl_pos);
		list.Load(src, nl_pos + 1, src.length);
		
		this.Wait();
	}
	private void Wait() {
		int tries = 0;
		while (tries++ < 100) {
			gplx.core.threads.Thread_adp_.Sleep(1000);
			if (Copy_files())
				break;
			else {
				String topmost = "#error";
				if (list.Len() > 0) {
					Xoa_manifest_item item = (Xoa_manifest_item)list.Get_at(0);
					topmost = item.Src().Raw();
				}
				view.Append("waiting for XOWA to release file: " + topmost);
			}
		}
		this.On_exit();
	}
	public void On_exit() {
		Io_mgr.Instance.DeleteFil(manifest_url);
		view.Mark_done(run_xowa_cmd);
	}
	private boolean Copy_files() {
		// loop list and copy items
		int len = list.Len();
		int idx = 0;
		for (idx = 0; idx < len; idx++) {
			Xoa_manifest_item item = (Xoa_manifest_item)list.Get_at(idx);
			try {
				Io_mgr.Instance.DeleteFil_args(item.Trg()).MissingFails_off().Exec();
				Io_mgr.Instance.CopyFil(item.Src(), item.Trg(), true);
				view.Append(StringUtl.Format("copied file: src={0} trg={1}", item.Src().Raw(), item.Trg().Raw()));
			}
			catch (Exception exc) {
				view.Append(StringUtl.Format("failed to copy file: src={0} trg={1} err={2}", item.Src().Raw(), item.Trg().Raw(), ErrUtl.Message(exc)));
				return false;
			}
		}

		// remove completed items
		for (int i = 0; i < idx; i++) {
			list.Del_at(0);
		}
		return true;
	}
}
