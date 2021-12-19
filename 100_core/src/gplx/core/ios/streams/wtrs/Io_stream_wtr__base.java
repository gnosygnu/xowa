/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios.streams.wtrs;
import gplx.core.ios.streams.*;
import gplx.frameworks.objects.Virtual;
import gplx.libs.files.Io_mgr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.libs.files.Io_url;
import gplx.types.errs.ErrUtl;
public abstract class Io_stream_wtr__base implements Io_stream_wtr {
	public abstract byte Tid();
	public Io_url Url() {return url;} private Io_url url;
	public Io_stream_wtr Url_(Io_url v) {url = v; return this;} 
	public void Trg_bfr_(BryWtr v) {this.trg_bfr = v;} private BryWtr trg_bfr;
	public byte[] To_ary_and_clear() {return trg_bfr.ToBryAndClear();}
	
		private java.io.OutputStream zip_stream;
	private java.io.ByteArrayOutputStream mem_stream;
	@Virtual public Io_stream_wtr Open() {
		java.io.OutputStream bry_stream = null;
		if (trg_bfr == null) {
			if (!Io_mgr.Instance.ExistsFil(url)) Io_mgr.Instance.SaveFilStr(url, "");
			try {bry_stream = new java.io.FileOutputStream(url.Raw());}
			catch (Exception e) {throw ErrUtl.NewArgs(e, "open failed", "url", url.Raw());}
		}
		else {
			mem_stream = new java.io.ByteArrayOutputStream();
			bry_stream = mem_stream;
		}
		zip_stream = Wrap_stream(bry_stream);
		return this;
	}
	public void Write(byte[] bry, int bgn, int len) {
		try {zip_stream.write(bry, bgn, len);}
		catch (Exception e) {ErrUtl.NewArgs(e, "write failed", "bgn", bgn, "len", len);}
	}
	public void Flush() {
		if (trg_bfr != null) {
			try {zip_stream.close();} catch (Exception e) {throw ErrUtl.NewArgs(e, "flush failed");}    // must close zip_stream to flush all bytes
			trg_bfr.Add(mem_stream.toByteArray());
		}
	}
	public void Rls() {
		try {
			if (zip_stream != null) zip_stream.close();
			if (mem_stream != null) mem_stream.close();
		}
		catch (Exception e) {throw ErrUtl.NewArgs(e, "close failed", "url", url.Raw());}
	}
	@Virtual protected java.io.OutputStream Wrap_stream(java.io.OutputStream stream) {throw ErrUtl.NewUnimplemented();}
	}
