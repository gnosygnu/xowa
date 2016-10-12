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
package gplx.core.ios.streams.wtrs; import gplx.*; import gplx.core.*; import gplx.core.ios.*; import gplx.core.ios.streams.*;
public class Io_stream_wtr__zip extends Io_stream_wtr__base {
	@Override public byte Tid() {return Io_stream_tid_.Tid__zip;}
		private java.util.zip.ZipOutputStream zip_stream;	
	public Io_url Url() {return url;} public Io_stream_wtr Url_(Io_url v) {url = v; trg_bfr = null; return this;} private Io_url url = Io_url_.Empty;
	public void Trg_bfr_(Bry_bfr v) {trg_bfr = v;} private Bry_bfr trg_bfr; private java.io.ByteArrayOutputStream mem_stream;
	// rely on zip_stream to close bry_stream 
	@Override public Io_stream_wtr Open() {
		java.io.OutputStream bry_stream;
		if (trg_bfr == null) {
			if (!Io_mgr.Instance.ExistsFil(url)) Io_mgr.Instance.SaveFilStr(url, "");	// create file if it doesn't exist
			try {bry_stream = new java.io.FileOutputStream(url.Xto_api());}
			catch (Exception e) {throw Err_.new_exc(e, "io", "open failed", "url", url.Raw());}
		}
		else {
			mem_stream = new java.io.ByteArrayOutputStream();
			bry_stream = mem_stream;
		}
		zip_stream = new java.util.zip.ZipOutputStream(bry_stream);
		java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("file");
		try {zip_stream.putNextEntry(entry);}
		catch (Exception e) {throw Err_.new_exc(e, "io", "open failed", "url", url.Raw());}
		return this;
	}
	public void Write(byte[] bry, int bgn, int len) {
		try {zip_stream.write(bry, bgn, len);}
		catch (Exception e) {throw Err_.new_exc(e, "io", "write failed", "url", url.Raw(), "bgn", bgn, "len", len);}
	}
	public void Flush() {// fixed as of DATE:2014-04-15
		try {
			zip_stream.closeEntry();
			zip_stream.close();
			if (trg_bfr != null)
				trg_bfr.Add(mem_stream.toByteArray());
			zip_stream.flush();
		}
		catch (Exception e) {throw Err_.new_exc(e, "io", "flush failed", "url", url.Raw());}
	}
	public void Rls() {
		try {
			if (zip_stream != null) zip_stream.close();
			if (mem_stream != null) mem_stream.close();
		}
		catch (Exception e) {throw Err_.new_exc(e, "io", "close failed", "url", url.Raw());}
	}
	public byte[] To_ary_and_clear() {
		byte[] rv = trg_bfr.To_bry_and_clear();
		this.Rls();
		return rv;
	}
	}
