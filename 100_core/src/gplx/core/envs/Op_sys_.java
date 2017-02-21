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
package gplx.core.envs; import gplx.*; import gplx.core.*;
public class Op_sys_ {
	public static boolean Wnt_invalid_char(byte b) {
		switch (b) {
			case Byte_ascii.Slash:
			case Byte_ascii.Backslash:
			case Byte_ascii.Lt:
			case Byte_ascii.Gt:
			case Byte_ascii.Colon:
			case Byte_ascii.Pipe:
			case Byte_ascii.Question:
			case Byte_ascii.Star:
			case Byte_ascii.Quote:		return true;
			default:					return false;
		}
	}
}
