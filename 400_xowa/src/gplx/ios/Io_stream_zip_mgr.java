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
package gplx.ios; import gplx.*;
public class Io_stream_zip_mgr {
	private Bry_bfr bfr = Bry_bfr.reset_(256);
	private Io_stream_wtr wtr_gzip, wtr_zip, wtr_bzip2;
	private Io_stream_rdr rdr_gzip, rdr_zip, rdr_bzip2;
	public byte[] Zip(byte type, byte[] val) {
		if (type == Io_stream_.Tid_raw) return val;
		Io_stream_wtr wtr = Wtr(type);
		wtr.Write(val, 0, val.length);
		wtr.Flush();
		return wtr.To_ary_and_clear();
	}
	public byte[] Unzip(byte type, byte[] val) {
		if (type == Io_stream_.Tid_raw) return val;
		Io_stream_rdr rdr = Rdr(type);
		rdr.Open_mem(val);
		return Io_stream_rdr_.Load_all_as_bry(bfr, rdr);
	}
	private Io_stream_wtr Wtr(byte type) {
		switch (type) {
			case Io_stream_.Tid_gzip	: if (wtr_gzip	== null) wtr_gzip	= Io_stream_wtr_.new_by_mem(bfr, Io_stream_.Tid_gzip)	; return wtr_gzip.Open();
			case Io_stream_.Tid_zip		: if (wtr_zip	== null) wtr_zip	= Io_stream_wtr_.new_by_mem(bfr, Io_stream_.Tid_zip)	; return wtr_zip.Open(); 
			case Io_stream_.Tid_bzip2	: if (wtr_bzip2 == null) wtr_bzip2	= Io_stream_wtr_.new_by_mem(bfr, Io_stream_.Tid_bzip2)	; return wtr_bzip2.Open();
			case Io_stream_.Tid_raw	:
			default						: throw Exc_.new_unhandled(type);
		}
	}
	private Io_stream_rdr Rdr(byte type) {
		switch (type) {
			case Io_stream_.Tid_gzip	: if (rdr_gzip	== null) rdr_gzip	= Io_stream_rdr_.new_by_tid_(Io_stream_.Tid_gzip)	; return rdr_gzip;
			case Io_stream_.Tid_zip		: if (rdr_zip	== null) rdr_zip	= Io_stream_rdr_.new_by_tid_(Io_stream_.Tid_zip)	; return rdr_zip; 
			case Io_stream_.Tid_bzip2	: if (rdr_bzip2 == null) rdr_bzip2	= Io_stream_rdr_.new_by_tid_(Io_stream_.Tid_bzip2)	; return rdr_bzip2;
			case Io_stream_.Tid_raw	:
			default						: throw Exc_.new_unhandled(type);
		}
	}
}
