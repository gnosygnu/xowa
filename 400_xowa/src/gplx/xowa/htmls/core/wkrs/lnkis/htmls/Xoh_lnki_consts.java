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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls;
import gplx.types.basics.utls.BryUtl;
public class Xoh_lnki_consts {
	public static final byte
	  Tid_a_cls_none = 0	, Tid_a_cls_image = 1
	, Tid_a_rel_none = 0	, Tid_a_rel_nofollow = 1
	;
	private static final byte[] 
	  Bry_anchor_class_image	= BryUtl.NewA7(" class=\"image\"")
	, Bry_anchor_rel_nofollow	= BryUtl.NewA7(" rel=\"nofollow\"")
	;
	public static byte[] A_cls_to_bry(byte tid) {return tid == Tid_a_cls_none ? BryUtl.Empty : Bry_anchor_class_image;}
	public static byte[] A_rel_to_bry(byte tid) {return tid == Tid_a_rel_none ? BryUtl.Empty : Bry_anchor_rel_nofollow;}
}
