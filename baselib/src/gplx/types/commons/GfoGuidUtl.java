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
public class GfoGuidUtl {
	public static final String Cls_ref_name = "Guid";
	public static final GfoGuid Empty = Parse("00000000-0000-0000-0000-000000000000");
	public static String NewAsstr() {return New().ToStr();}
	public static GfoGuid New() {return new GfoGuid(java.util.UUID.randomUUID());}
	public static GfoGuid Parse(String s) {return new GfoGuid(java.util.UUID.fromString(s));}
}
