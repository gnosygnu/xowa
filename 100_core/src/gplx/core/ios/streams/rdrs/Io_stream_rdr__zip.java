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
package gplx.core.ios.streams.rdrs; import gplx.*; import gplx.core.*; import gplx.core.ios.*; import gplx.core.ios.streams.*;
public class Io_stream_rdr__zip extends Io_stream_rdr__base {
	@Override public byte Tid() {return Io_stream_tid_.Tid__gzip;}
		public Object Under() {return zip_stream;} private java.util.zip.ZipInputStream zip_stream;
	public void Src_bfr_(Bry_bfr v) {this.src_bfr = v;} Bry_bfr src_bfr;
	public void Open_mem(byte[] v) {
		this.zip_stream = (java.util.zip.ZipInputStream)Wrap_stream(new java.io.ByteArrayInputStream(v));
	}
	public Io_stream_rdr Open() {
		try {this.zip_stream = (java.util.zip.ZipInputStream)Wrap_stream(new java.io.FileInputStream(url.Xto_api()));}
		catch (Exception e) {throw Err_.new_exc(e, "io", "open failed", "url", url.Xto_api());}
		return this;
	}
	public int Read(byte[] bry, int bgn, int len) {
		try {
			while (true){ 
				int read = zip_stream.read(bry, bgn, len);
				if (read == Io_stream_rdr_.Read_done) {
					if (zip_stream.getNextEntry() == null)
						return Io_stream_rdr_.Read_done;
				}
				else
					return read;
			}
		}
		catch (Exception e) {throw Err_.new_exc(e, "io", "read failed", "bgn", bgn, "len", len);}
	}
	public long Skip(long len) {		
		try {return zip_stream.skip(len);}
		catch (Exception e) {throw Err_.new_exc(e, "io", "skip failed", "len", len);}
	}
	public void Rls() {
		try {zip_stream.close();}
		catch (Exception e) {throw Err_.new_exc(e, "io", "close failed", "url", url.Xto_api());}
	}
	@Override public java.io.InputStream Wrap_stream(java.io.InputStream input_stream) {return new java.util.zip.ZipInputStream(input_stream);}
	}
