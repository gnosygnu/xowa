/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.commons;
import gplx.types.basics.strings.bfrs.GfoStringBldr;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.basics.utls.ObjectUtl;
public class KeyValUtl {
	public static final KeyVal[] AryEmpty = new KeyVal[0];
	public static KeyVal[] Ary(KeyVal... ary) {return ary;}
	public static String AryToStr(KeyVal... ary) {
		GfoStringBldr sb = new GfoStringBldr();
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			KeyVal itm = ary[i];
			if (itm == null) {
				sb.Add("<<NULL>>");
				continue;
			}
			sb.Add(itm.KeyToStr()).Add("=");
			Object itm_val = itm.Val();
			if (ClassUtl.EqByObj(KeyVal[].class, itm_val))
				sb.Add(AryToStr((KeyVal[])itm_val));
			else
				sb.Add(ObjectUtl.ToStrOrNullMark(itm_val));
			sb.AddCharNl();
		}
		return sb.ToStr();
	}
}
