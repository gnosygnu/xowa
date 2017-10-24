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
package gplx.xowa.addons.wikis.searchs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
public class Srch_link_row {
	public Srch_link_row(int word_id, int page_id, int link_score) {
		this.Word_id = word_id;
		this.Page_id = page_id;
		this.Link_score = link_score;
	}
	public final    int Word_id;
	public final    int Page_id;
	public final    int Link_score;
	public int Trg_db_id;

	public int Db_row_size() {return Db_row_size_fixed;}
	private static final int Db_row_size_fixed = (3 * 4);	// 5 ints
}
