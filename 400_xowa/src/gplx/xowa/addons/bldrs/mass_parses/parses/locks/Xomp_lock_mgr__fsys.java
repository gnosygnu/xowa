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
package gplx.xowa.addons.bldrs.mass_parses.parses.locks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*;
public class Xomp_lock_mgr__fsys implements Xomp_lock_mgr {
	private final    Io_url root_dir, uid_fil, active_fil;
	private final    int wait_time;
	private Io_url stop_fil;
	public Xomp_lock_mgr__fsys(int wait_time, Io_url root_dir) {
		this.wait_time = wait_time;
		this.root_dir = root_dir;
		this.uid_fil = root_dir.GenSubFil("xomp.semaphore.uid.txt");
		this.active_fil = root_dir.GenSubFil("xomp.semaphore.active.txt");
	}
	public void Remake() {
		Io_url[] fils = Io_mgr.Instance.QueryDir_fils(root_dir);
		for (Io_url fil : fils) {
			if (String_.Has_at_end(fil.NameAndExt(), ".sempahore.txt"))
				Io_mgr.Instance.DeleteFil(fil);
		}
		Io_mgr.Instance.SaveFilStr(uid_fil, Int_.To_str(Uid__bos));
	}
	public int Uid_prv__get(String machine_name) {
		// return -1 if stop file exists; note that -1 will stop machine
		if (stop_fil == null) this.stop_fil = root_dir.GenSubFil("xomp.semaphore.stop." + machine_name + ".txt");
		if (Io_mgr.Instance.ExistsFil(stop_fil)) return Uid__eos;

		// loop until permit is acquired
		int tries = 0;
		while (true) {
			++tries;
			if (tries > 10) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to acquire permit");
				return Uid__eos;	// too many tries; just exit now
			}

			// if active_file exists, then assume another machine is reading;
			if (Io_mgr.Instance.ExistsFil(active_fil)) {
				Sleep(machine_name, "active file exists");
				continue;
			}

			// write to the active_file
			Io_mgr.Instance.SaveFilStr(active_fil, machine_name);

			// now read it to make sure it's the same
			String cur_active = String_.new_u8(Io_mgr.Instance.LoadFilBryOr(active_fil, Bry_.Empty));
			if (!String_.Eq(cur_active, machine_name)) {
				Sleep(machine_name, "active file differs: " + cur_active);
				continue;
			}
			break;
		}

		// get next uid and fill pages
		byte[] cur_uid_bry = Io_mgr.Instance.LoadFilBryOr(uid_fil, null);
		if (cur_uid_bry == null) return 0;	// file is empty; should only occur on 1st run; return 0, which will start from beginning;

		int cur_uid = Int_.Min_value;
		if (cur_uid_bry != null)
			cur_uid = Bry_.To_int_or(cur_uid_bry, Int_.Min_value);
		if (cur_uid == Int_.Min_value) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "uid fil has bad data: data:~{0}", cur_uid_bry);
			return Uid__eos;
		}
		return cur_uid;
	} 
	private void Sleep(String machine_name, String reason) {
		Gfo_usr_dlg_.Instance.Note_many("", "", "waiting for permit: machine:~{0} reason:~{1}", machine_name, reason);
		gplx.core.threads.Thread_adp_.Sleep(wait_time);
	}
	public void Uid_prv__rls(String machine_name, int uid_prv) {
		Io_mgr.Instance.SaveFilStr(uid_fil, Int_.To_str(uid_prv));
		Io_mgr.Instance.DeleteFil(active_fil);
	}
	public static final int Uid__bos = 0, Uid__eos = -1;
}
