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
package gplx.dbs.diffs.builds; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
public interface Gfdb_diff_wkr {
	void Init_rdrs(Gdif_bldr_ctx ctx, Gfdb_diff_tbl tbl, Db_rdr old_rdr, Db_rdr new_rdr);
	void Term_tbls();
	void Handle_same();
	void Handle_old_missing();
	void Handle_new_missing();
}
