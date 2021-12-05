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
package gplx.xowa.parsers.tmpls;
import gplx.objects.strings.AsciiByte;
public class Xop_tkn_ {
	public static final byte[] 
		Lnki_bgn = new byte[] {AsciiByte.BrackBgn, AsciiByte.BrackBgn}
	,	Lnki_end = new byte[] {AsciiByte.BrackEnd, AsciiByte.BrackEnd}
	,	Anchor = new byte[] {AsciiByte.Hash}
	;
	public static final int Lnki_bgn_len = 2, Lnki_end_len = 2;
	public static final byte Anchor_byte = AsciiByte.Hash;
}
