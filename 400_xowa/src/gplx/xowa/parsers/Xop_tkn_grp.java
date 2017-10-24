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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
public interface Xop_tkn_grp {
	int Subs_len();
	Xop_tkn_itm Subs_get(int i);
	void Subs_add(Xop_tkn_itm sub);
	void Subs_add_grp(Xop_tkn_itm sub, Xop_tkn_grp old_grp, int old_sub_idx);
	void Subs_del_after(int pos_bgn);
	void Subs_clear();
	void Subs_move(Xop_tkn_itm tkn);
	int Subs_src_bgn(int sub_idx);
	int Subs_src_end(int sub_idx);
	void Subs_src_pos_(int sub_idx, int bgn, int end);
	Xop_tkn_itm Immutable_clone(Xop_ctx ctx, Xop_tkn_itm tkn, int sub_idx);
}
class Xop_tkn_grp_ {
	public static final Xop_tkn_grp Null = null;
}
