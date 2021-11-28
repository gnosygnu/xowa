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
package gplx.xowa.parsers.uniqs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.langs.htmls.entitys.*;
// EX: "\u007fUNIQ-item-1--QINU\u007f"
public class Xop_uniq_tkn extends Xop_tkn_itm_base {
	public Xop_uniq_tkn(int bgn, int end, byte[] key) {
		this.Tkn_ini_pos(false, bgn, end);
		this.key = key;
	}
	@Override public byte Tkn_tid()			{return Xop_tkn_itm_.Tid_uniq;}
	public byte[] Key()                     {return key;} private final byte[] key;
}
