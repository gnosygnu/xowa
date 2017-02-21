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
package gplx.xowa.files.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*;
public class Xof_orig_wkr__orig_db implements Xof_orig_wkr {
	private final    boolean addable;
	public Xof_orig_wkr__orig_db(Xof_orig_tbl tbl, boolean addable) {this.tbl = tbl; this.addable = addable;}
	public byte				Tid() {return Xof_orig_wkr_.Tid_xowa_db;}
	public Xof_orig_tbl		Tbl() {return tbl;} private final    Xof_orig_tbl tbl;
	public void				Find_by_list(Ordered_hash rv, List_adp itms) {tbl.Select_by_list(rv, itms);}
	public Xof_orig_itm		Find_as_itm(byte[] ttl, int list_idx, int list_len) {return tbl.Select_itm(ttl);}
	public boolean				Add_orig(byte repo, byte[] page, int ext_id, int w, int h, byte[] redirect) {
		if (!addable) return false;
		if (tbl.Exists__repo_ttl(repo, page))
			tbl.Update(repo, page, ext_id, w, h, redirect);
		else
			tbl.Insert(repo, page, ext_id, w, h, redirect);
		return true;
	}
	public void				Db_txn_save() {tbl.Conn().Txn_end();}
	public void				Db_rls() {tbl.Conn().Rls_conn();}
}
