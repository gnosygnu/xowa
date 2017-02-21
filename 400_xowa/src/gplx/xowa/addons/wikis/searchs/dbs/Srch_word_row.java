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
public class Srch_word_row {
	public Srch_word_row(int id, byte[] text, int link_count, int link_count_score, int link_score_min, int link_score_max) {
		this.Id = id; this.Text = text;
		this.Link_count = link_count; this.Link_count_score = link_count_score;
		this.Link_score_min = link_score_min; this.Link_score_max = link_score_max;
	}
	public final    int Id;
	public final    byte[] Text;
	public final    int Link_count;
	public final    int Link_count_score;
	public final    int Link_score_min;
	public final    int Link_score_max;

	public int Db_row_size() {return Db_row_size_fixed + Text.length;}
	private static final int Db_row_size_fixed = (5 * 4);	// 5 ints

        public static final    Srch_word_row Empty = new Srch_word_row(-1, Bry_.Empty, 0, 0, 0, 0);
}
