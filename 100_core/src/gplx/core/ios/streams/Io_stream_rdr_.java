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
import gplx.core.ios.streams.rdrs.*;
public class Io_stream_rdr_ {
	public static final int Read_done = -1, Read_done_compare = 1;
	public static final    Io_stream_rdr Noop = new Io_stream_rdr__noop();
	public static Io_stream_rdr		New__raw(Io_url url)			{return new Io_stream_rdr__raw().Url_(url);}
	public static Io_stream_rdr		New__raw(java.io.InputStream strm)	{return new Io_stream_rdr__raw().Under_(strm);}	
	private static Io_stream_rdr	New__zip(Io_url url)			{return new Io_stream_rdr__zip().Url_(url);}
	private static Io_stream_rdr	New__gzip(Io_url url)			{return new Io_stream_rdr__gzip().Url_(url);}
	public static Io_stream_rdr		New__bzip2(Io_url url)			{return new Io_stream_rdr__bzip2().Url_(url);}
	public static Io_stream_rdr	New__mem(String v) {return New__mem(Bry_.new_u8(v));}
	public static Io_stream_rdr New__mem(byte[] v) {
		Io_stream_rdr rv = new Io_stream_rdr__adp(New__mem_as_stream(v));
		rv.Len_(v.length);
		return rv;
	}	
	public static java.io.InputStream New__mem_as_stream(byte[] v) {	
		return new java.io.ByteArrayInputStream(v);						
	}
	public static Io_stream_rdr New_by_url(Io_url url) {
		String ext = url.Ext();
		if		(String_.Eq(ext, Io_stream_tid_.Ext__zip)) 		return Io_stream_rdr_.New__zip(url);
		else if	(String_.Eq(ext, Io_stream_tid_.Ext__gz)) 		return Io_stream_rdr_.New__gzip(url);
		else if	(String_.Eq(ext, Io_stream_tid_.Ext__bz2)) 		return Io_stream_rdr_.New__bzip2(url);
		else if	(String_.Eq(ext, Io_stream_tid_.Ext__xz)) 		return new Io_stream_rdr__xz().Url_(url);
		else 													return Io_stream_rdr_.New__raw(url);
	}
	public static Io_stream_rdr New_by_tid(byte tid) {
		switch (tid) {
			case Io_stream_tid_.Tid__raw:						return new Io_stream_rdr__raw();
			case Io_stream_tid_.Tid__zip: 						return new Io_stream_rdr__zip();
			case Io_stream_tid_.Tid__gzip: 						return new Io_stream_rdr__gzip();
			case Io_stream_tid_.Tid__bzip2: 					return new Io_stream_rdr__bzip2();
			case Io_stream_tid_.Tid__xz: 						return new Io_stream_rdr__xz();
			default:											throw Err_.new_unhandled_default(tid);
		}
	}
	public static String Load_all_as_str(Io_stream_rdr rdr) {return String_.new_u8(Load_all_as_bry(Bry_bfr_.New(), rdr));}
	public static byte[] Load_all_as_bry(Bry_bfr rv, Io_stream_rdr rdr) {
		Load_all_to_bfr(rv, rdr);
		return rv.To_bry_and_clear();
	}
	public static void Load_all_to_bfr(Bry_bfr rv, Io_stream_rdr rdr) {
		try {
			byte[] bry = new byte[4096];
			while (true) {
				int read = rdr.Read(bry, 0, 4096);
				if (read < gplx.core.ios.streams.Io_stream_rdr_.Read_done_compare) break; 
				rv.Add_mid(bry, 0, read);
			}
		} finally {rdr.Rls();}
	}
	public static int Read_by_parts(java.io.InputStream stream, int part_len, byte[] bry, int bgn, int len) {
		/*
		NOTE: BZip2CompressorInputStream will fail if large len is used
		Instead, make smaller requests and fill bry
		*/
		try {
			int rv = 0;
			int end = bgn + len;
			int cur = bgn;
			while (true) {
				int bry_len = part_len;		// read in increments of part_len
				if (cur + bry_len > end)	// if cur + 8 kb > bry_len, trim to end; EX: 9 kb bry passed; 1st pass is 8kb, 2nd pass should be 1kb, not 8 kb;   
					bry_len = end - cur;
				if (cur == end) break;		// no more bytes needed; break; EX: 8 kb bry passed; 1st pass is 8kb; 2nd pass is 0 and cur == end
				int read = stream.read(bry, cur, bry_len);		
				if (read == gplx.core.ios.streams.Io_stream_rdr_.Read_done) // read done; end
					break;
				rv += read;
				cur += read;
			}
			return rv;
		}
		catch (Exception exc) {
			throw Err_.new_exc(exc, "io", "read failed", "bgn", bgn, "len", len);
		}
	}
	public static boolean Close(java.io.InputStream stream) {	
		try {
			if (stream != null) stream.close();	
			return true;
		} catch (Exception e) {Err_.Noop(e); return false;}
	}
}
