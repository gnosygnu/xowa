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
package gplx.xowa.files.fsdb.fs_roots; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.dbs.*;
import gplx.xowa.files.origs.*;
class Xof_orig_wkr__fs_root implements Xof_orig_wkr {
	private final    Fs_root_wkr wkr;
	public Xof_orig_wkr__fs_root(Fs_root_wkr wkr) {this.wkr = wkr;}
	public byte				Tid() {return Xof_orig_wkr_.Tid_fs_root;}
	public void				Find_by_list(Ordered_hash rv, List_adp itms) {Xof_orig_wkr_.Find_by_list(this, rv, itms);}
	public Xof_orig_itm		Find_as_itm(byte[] ttl, int list_idx, int list_len) {
		Orig_fil_row orig_row = wkr.Get_by_ttl(ttl);
		if (orig_row == Orig_fil_row.Null) return Xof_orig_itm.Null;

		Xof_orig_itm rv = new Xof_orig_itm
		( gplx.xowa.files.repos.Xof_repo_tid_.Tid__local
		, ttl
		, Xof_ext_.new_by_ttl_(ttl).Id()
		, orig_row.W()
		, orig_row.H()
		, null
		);
		return rv;
	}
	public boolean				Add_orig(byte repo, byte[] page, int ext_id, int w, int h, byte[] redirect) {return false;}
	public void				Db_txn_save() {}
	public void				Db_rls() {}
}
