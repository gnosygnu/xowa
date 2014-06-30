/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx;
public class GfoMsgUtl {
	public static int SetInt(GfsCtx ctx, GfoMsg m, int cur)						{return ctx.Deny() ? cur : m.ReadIntOr("v", cur);}
	public static boolean SetBool(GfsCtx ctx, GfoMsg m, boolean cur)					{return ctx.Deny() ? cur : m.ReadBoolOr("v", cur);}
	public static String SetStr(GfsCtx ctx, GfoMsg m, String cur)				{return ctx.Deny() ? cur : m.ReadStrOr("v", cur);}
	public static Io_url SetIoUrl(GfsCtx ctx, GfoMsg m, Io_url cur)				{return ctx.Deny() ? cur : m.ReadIoUrlOr("v", cur);}
	public static DecimalAdp SetDecimal(GfsCtx ctx, GfoMsg m, DecimalAdp cur)	{return ctx.Deny() ? cur : m.ReadDecimalOr("v", cur);}
}
