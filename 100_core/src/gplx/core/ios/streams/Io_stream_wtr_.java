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
package gplx.core.ios.streams; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
import gplx.core.ios.streams.wtrs.*;
public class Io_stream_wtr_ {
	public static Io_stream_wtr		New__raw(Io_url url)		{return new Io_stream_wtr__raw().Url_(url);}
	private static Io_stream_wtr	New__zip(Io_url url)		{return new Io_stream_wtr__zip().Url_(url);}
	private static Io_stream_wtr	New__gzip(Io_url url)		{return new Io_stream_wtr__gzip().Url_(url);}
	private static Io_stream_wtr	New__bzip2(Io_url url)		{return new Io_stream_wtr__bzip2().Url_(url);}
	public static Io_stream_wtr		New_by_url(Io_url url) {
		String ext = url.Ext();
		if		(String_.Eq(ext, Io_stream_tid_.Ext__zip)) 	return Io_stream_wtr_.New__zip(url);
		else if	(String_.Eq(ext, Io_stream_tid_.Ext__gz)) 	return Io_stream_wtr_.New__gzip(url);
		else if	(String_.Eq(ext, Io_stream_tid_.Ext__bz2)) 	return Io_stream_wtr_.New__bzip2(url);
		else if	(String_.Eq(ext, Io_stream_tid_.Ext__xz)) 	return new Io_stream_wtr__xz().Url_(url);
		else 												return Io_stream_wtr_.New__raw(url);
	}
	public static Io_stream_wtr New_by_tid(byte v) {
		switch (v) {
			case Io_stream_tid_.Tid__raw:					return new Io_stream_wtr__raw();
			case Io_stream_tid_.Tid__zip:					return new Io_stream_wtr__zip();
			case Io_stream_tid_.Tid__gzip:					return new Io_stream_wtr__gzip();
			case Io_stream_tid_.Tid__bzip2:					return new Io_stream_wtr__bzip2();
			case Io_stream_tid_.Tid__xz:					return new Io_stream_wtr__xz();
			default:										throw Err_.new_unhandled(v);
		}
	}
	public static Io_stream_wtr New_by_mem(Bry_bfr bfr, byte tid) {
		Io_stream_wtr wtr = New_by_tid(tid).Url_(Io_url_.Empty);
		wtr.Trg_bfr_(bfr);
		return wtr;
	}
	public static void Save_rdr(Io_url url, Io_stream_rdr rdr, Io_download_fmt download_progress) {
		byte[] bry = new byte[4096];
		Io_stream_wtr wtr = New_by_url(url);
		try {
			wtr.Open();
			if (download_progress != Io_download_fmt.Null)
				download_progress.Bgn(rdr.Len());
			while (true) {
				int read = rdr.Read(bry, 0, 4096);
				if (read < gplx.core.ios.streams.Io_stream_rdr_.Read_done_compare) break; 
				if (download_progress != Io_download_fmt.Null)
					download_progress.Prog(read);
				wtr.Write(bry, 0, read);
			}
			wtr.Flush();
			if (download_progress != Io_download_fmt.Null)
				download_progress.Term();
		}
		finally {wtr.Rls(); rdr.Rls();}
	}
}
