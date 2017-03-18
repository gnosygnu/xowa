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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
public class XophpUtility {
	public static boolean empty(byte[] v)   {return v == null || v.length == 0;}
	public static boolean empty(boolean v)     {return v == false;}
	public static boolean empty(int v)      {return v == 0;}
	public static boolean isset(byte[] v)   {return v != null;}
	public static boolean isset(int v)      {return v != NULL_INT;}
	public static boolean isset(double v)   {return v != NULL_DOUBLE;}
	public static boolean istrue(int v)     {return v != NULL_INT;}
	public static boolean isnumeric(byte[] src) {
		if (src == null) return false;
		int len = src.length;
		for (int i = 0; i < len; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					break;
				default:
					return false;
			}
		}
		return true;
	}

	public static boolean is_null(int v) {return v == NULL_INT;}
	public static final int NULL_INT = Int_.Max_value;
	public static final double NULL_DOUBLE = Double_.MinValue;
	public static final    byte[] NULL_BRY = null;
}
