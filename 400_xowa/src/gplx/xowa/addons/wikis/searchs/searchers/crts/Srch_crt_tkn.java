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
package gplx.xowa.addons.wikis.searchs.searchers.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
public class Srch_crt_tkn {
	public Srch_crt_tkn(byte tid, byte[] val) {this.Tid = tid; this.Val = val;}
	public final    byte	Tid;
	public final    byte[]	Val;
	public static final byte 
	  Tid__escape		=  0
	, Tid__space		=  1
	, Tid__quote		=  2
	, Tid__not			=  3
	, Tid__and			=  4
	, Tid__or			=  5
	, Tid__paren_bgn	=  6
	, Tid__paren_end	=  7
	, Tid__word			=  8
	, Tid__word_w_quote	=  9
	, Tid__eos			= 10
	, Tid__null			= 11
	;
	public static final    Srch_crt_tkn[] Ary_empty = new Srch_crt_tkn[0];
}
