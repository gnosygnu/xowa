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
package gplx.objects.primitives;
import gplx.objects.ObjectUtl;
import gplx.objects.errs.ErrUtl;
public class ByteUtl {
	public static final String ClsValName = "byte";
	public static final Class<?> ClsRefType = Byte.class;
	public static byte Cast(Object o) {
		try {
			return (Byte)o;
		} 
		catch (Exception e) {
			throw ErrUtl.NewFmt(e, "failed to cast to byte; obj={0}", ObjectUtl.ToStr(o));
		}
	}
}
