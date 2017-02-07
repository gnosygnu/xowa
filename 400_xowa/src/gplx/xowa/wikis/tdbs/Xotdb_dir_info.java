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
