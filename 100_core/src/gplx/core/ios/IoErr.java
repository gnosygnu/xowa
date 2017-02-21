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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public class IoErr {
	public static String Namespace				= "gplx.core.ios.";
	public static String FileIsReadOnly_key		= Namespace + "FileIsReadOnlyError";
	public static String FileNotFound_key		= Namespace + "FileNotFoundError";
	public static Err FileIsReadOnly(Io_url url) {
		return Err_.new_(FileIsReadOnly_key, "file is read-only", "url", url.Xto_api()).Trace_ignore_add_1_();
	}
	public static Err FileNotFound(String op, Io_url url) {
		// file is missing -- op='copy' file='C:\a.txt' copyFile_target='D:\a.txt' 
		return Err_.new_(FileNotFound_key, "file not found", "op", op, "file", url.Xto_api()).Trace_ignore_add_1_();
	}
}