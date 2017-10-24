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
package gplx.xowa.wikis.tdbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.ios.*;
public class Xotdb_dir_info {
	public Xotdb_dir_info(boolean ns_root, byte id, String name) {this.ns_root = ns_root; this.id = id; this.name = name;}
	public byte Id() {return id;} private byte id;
	public String Name() {return name;} private String name;
	public boolean Ns_root() {return ns_root;} private boolean ns_root;
	public String Ext() {return ext_str;} private String ext_str = Wtr_xdat_str;
	public byte[] Ext_bry() {return ext_bry;} private byte[] ext_bry = Wtr_xdat_bry;
	public byte Ext_tid() {return ext_tid;}
	public Xotdb_dir_info Ext_tid_(byte v) {
		ext_tid = v; 
		ext_bry = Wtr_ext(v);
		ext_str = String_.new_a7(ext_bry);
		return this;
	}	byte ext_tid = gplx.core.ios.streams.Io_stream_tid_.Tid__raw;
	public static final    String Wtr_xdat_str = ".xdat", Wtr_zip_str = ".zip", Wtr_gz_str = ".gz", Wtr_bz2_str = ".bz2";
	public static final    byte[] Wtr_xdat_bry = Bry_.new_a7(Wtr_xdat_str), Wtr_zip_bry = Bry_.new_a7(Wtr_zip_str), Wtr_gz_bry = Bry_.new_a7(Wtr_gz_str), Wtr_bz2_bry = Bry_.new_a7(Wtr_bz2_str);
	public static String Wtr_dir(byte v) {
		switch (v) {
			case gplx.core.ios.streams.Io_stream_tid_.Tid__raw	: return "";
			case gplx.core.ios.streams.Io_stream_tid_.Tid__zip	: return "_zip";
			case gplx.core.ios.streams.Io_stream_tid_.Tid__gzip	: return "_gz";
			case gplx.core.ios.streams.Io_stream_tid_.Tid__bzip2	: return "_bz2";
			default								: throw Err_.new_unhandled(v);
		}
	}
	public static byte[] Wtr_ext(byte v) {
		switch (v) {
			case gplx.core.ios.streams.Io_stream_tid_.Tid__raw	: return Wtr_xdat_bry;
			case gplx.core.ios.streams.Io_stream_tid_.Tid__zip	: return Wtr_zip_bry;
			case gplx.core.ios.streams.Io_stream_tid_.Tid__gzip	: return Wtr_gz_bry;
			case gplx.core.ios.streams.Io_stream_tid_.Tid__bzip2	: return Wtr_bz2_bry;
			default								: throw Err_.new_unhandled(v);
		}
	}
}
