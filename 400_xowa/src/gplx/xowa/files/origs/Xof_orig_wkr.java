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
import gplx.xowa.files.fsdb.*;
public interface Xof_orig_wkr {
	byte			Tid();
	Xof_orig_itm	Find_as_itm(byte[] ttl, int list_idx, int list_len);
	void			Find_by_list(Ordered_hash rv, List_adp itms);
	boolean			Add_orig(byte repo, byte[] page, int ext_id, int w, int h, byte[] redirect);
	void			Db_txn_save();
	void			Db_rls();
}
