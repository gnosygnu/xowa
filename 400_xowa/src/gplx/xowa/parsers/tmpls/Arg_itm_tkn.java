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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public interface Arg_itm_tkn extends Xop_tkn_itm {
	int Dat_bgn();
	int Dat_end();
	Arg_itm_tkn Dat_end_(int v);
	Arg_itm_tkn Dat_rng_(int bgn, int end);
	Arg_itm_tkn Dat_rng_ary_(byte[] src, int bgn, int end);
	byte[] Dat_ary();
	Arg_itm_tkn Dat_ary_(byte[] dat_ary);
	byte[] Dat_to_bry(byte[] src);
	boolean Dat_ary_had_subst(); void Dat_ary_had_subst_y_();
	byte Itm_static(); Arg_itm_tkn Itm_static_(boolean v);
	Arg_itm_tkn Subs_add_ary(Xop_tkn_itm... ary);
}
