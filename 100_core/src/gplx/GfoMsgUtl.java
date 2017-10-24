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
package gplx;
public class GfoMsgUtl {
	public static int SetInt(GfsCtx ctx, GfoMsg m, int cur)						{return ctx.Deny() ? cur : m.ReadIntOr("v", cur);}
	public static boolean SetBool(GfsCtx ctx, GfoMsg m, boolean cur)					{return ctx.Deny() ? cur : m.ReadBoolOr("v", cur);}
	public static String SetStr(GfsCtx ctx, GfoMsg m, String cur)				{return ctx.Deny() ? cur : m.ReadStrOr("v", cur);}
	public static Io_url SetIoUrl(GfsCtx ctx, GfoMsg m, Io_url cur)				{return ctx.Deny() ? cur : m.ReadIoUrlOr("v", cur);}
	public static Decimal_adp SetDecimal(GfsCtx ctx, GfoMsg m, Decimal_adp cur)	{return ctx.Deny() ? cur : m.ReadDecimalOr("v", cur);}
}
