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
import gplx.core.ios.streams.*;
public class Io_stream_zip_mgr {
	private Io_stream_wtr wtr__gzip, wtr__zip, wtr__bzip2, wtr__xz;
	public byte[] Zip(byte type, byte[] val) {
		if (type == Io_stream_tid_.Tid__raw) return val;
		Io_stream_wtr wtr = Wtr(type);
		wtr.Write(val, 0, val.length);
		wtr.Flush();
		return wtr.To_ary_and_clear();
	}
	public byte[] Unzip(byte type, byte[] val) {
		if (type == Io_stream_tid_.Tid__raw) return val;
		Io_stream_rdr rdr = Rdr(type);
		rdr.Open_mem(val);
		return Io_stream_rdr_.Load_all_as_bry(Bry_bfr_.New(), rdr);
	}
	private Io_stream_wtr Wtr(byte type) {
		Bry_bfr bfr = Bry_bfr_.New();
		switch (type) {
			case Io_stream_tid_.Tid__gzip:		if (wtr__gzip  == null) wtr__gzip  = Io_stream_wtr_.New_by_mem(bfr, Io_stream_tid_.Tid__gzip);  return wtr__gzip.Open();
			case Io_stream_tid_.Tid__zip:		if (wtr__zip   == null) wtr__zip   = Io_stream_wtr_.New_by_mem(bfr, Io_stream_tid_.Tid__zip);   return wtr__zip.Open(); 
			case Io_stream_tid_.Tid__bzip2:		if (wtr__bzip2 == null) wtr__bzip2 = Io_stream_wtr_.New_by_mem(bfr, Io_stream_tid_.Tid__bzip2); return wtr__bzip2.Open();
			case Io_stream_tid_.Tid__xz:		if (wtr__xz    == null)	wtr__xz    = Io_stream_wtr_.New_by_mem(bfr, Io_stream_tid_.Tid__xz);    return wtr__xz.Open();
			case Io_stream_tid_.Tid__raw:
			default:							throw Err_.new_unhandled(type);
		}
	}
	private Io_stream_rdr Rdr(byte type) {	// TS.MEM: DATE:2016-07-12
		switch (type) {
			case Io_stream_tid_.Tid__gzip:		return Io_stream_rdr_.New_by_tid(Io_stream_tid_.Tid__gzip);
			case Io_stream_tid_.Tid__zip:		return Io_stream_rdr_.New_by_tid(Io_stream_tid_.Tid__zip);
			case Io_stream_tid_.Tid__bzip2:		return Io_stream_rdr_.New_by_tid(Io_stream_tid_.Tid__bzip2);
			case Io_stream_tid_.Tid__xz:		return Io_stream_rdr_.New_by_tid(Io_stream_tid_.Tid__xz);
			case Io_stream_tid_.Tid__raw:
			default:							throw Err_.new_unhandled(type);
		}
	}
}
