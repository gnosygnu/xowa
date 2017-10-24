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
package gplx.core.ios.streams; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
public class Io_stream_tid_ {
	public static final byte Tid__null = 0, Tid__raw = 1, Tid__zip = 2, Tid__gzip = 3, Tid__bzip2 = 4, Tid__xz = 5;	// SERIALIZED:xo.text_db;xo.html_db
	public static final String Ext__zip = ".zip", Ext__gz = ".gz", Ext__bz2 = ".bz2", Ext__xz = ".xz";
	private static final String Key__raw = "raw", Key__zip = "zip", Key__gzip = "gzip", Key__bzip2 = "bzip2", Key__xz = "xz";

	public static String To_key(byte v) {
		switch (v) {
			case Io_stream_tid_.Tid__raw			: return Key__raw;
			case Io_stream_tid_.Tid__zip			: return Key__zip;
			case Io_stream_tid_.Tid__gzip			: return Key__gzip;
			case Io_stream_tid_.Tid__bzip2			: return Key__bzip2;
			case Io_stream_tid_.Tid__xz				: return Key__xz;
			default									: throw Err_.new_unhandled_default(v);
		}
	}
	public static byte To_tid(String v) {
		if		(String_.Eq(v, Key__raw))			return Io_stream_tid_.Tid__raw;
		else if	(String_.Eq(v, Key__zip))			return Io_stream_tid_.Tid__zip;
		else if	(String_.Eq(v, Key__gzip))			return Io_stream_tid_.Tid__gzip;
		else if	(String_.Eq(v, Key__bzip2))			return Io_stream_tid_.Tid__bzip2;
		else if	(String_.Eq(v, Key__xz))			return Io_stream_tid_.Tid__xz;
		else										throw Err_.new_unhandled_default(v);
	}
	public static String Obsolete_to_str(byte v) {
		switch (v) {
			case Io_stream_tid_.Tid__raw			: return ".xdat";
			case Io_stream_tid_.Tid__zip			: return ".zip";
			case Io_stream_tid_.Tid__gzip			: return ".gz";
			case Io_stream_tid_.Tid__bzip2			: return ".bz2";
			default									: throw Err_.new_unhandled_default(v);
		}
	}
	public static byte Obsolete_to_tid(String v) {
		if		(String_.Eq(v, ".xdat"))			return Io_stream_tid_.Tid__raw;
		else if	(String_.Eq(v, ".zip"))				return Io_stream_tid_.Tid__zip;
		else if	(String_.Eq(v, ".gz"))				return Io_stream_tid_.Tid__gzip;
		else if	(String_.Eq(v, ".bz2"))				return Io_stream_tid_.Tid__bzip2;
		else										throw Err_.new_unhandled_default(v);
	}
}
