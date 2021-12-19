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
import gplx.libs.files.Io_mgr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.libs.files.Io_url;
import gplx.types.errs.ErrUtl;
public class Io_stream_wtr__raw implements Io_stream_wtr {
	public byte Tid() {return Io_stream_tid_.Tid__raw;}
	public Io_url Url() {return url;} public Io_stream_wtr Url_(Io_url v) {url = v; return this;} private Io_url url;
	public void Trg_bfr_(BryWtr v) {trg_bfr = v;} private BryWtr trg_bfr;

		private IoStream bry_stream;
	@Override public Io_stream_wtr Open() {
		try {
			if (trg_bfr == null)
				bry_stream = Io_mgr.Instance.OpenStreamWrite(url);
		}
		catch (Exception e) {throw ErrUtl.NewArgs(e, "open failed", "url", url.Raw());}
		return this;
	}
	public void Write(byte[] bry, int bgn, int len) {
		if (trg_bfr == null) {
			try {bry_stream.Write(bry, bgn, len);}
			catch (Exception e) {throw ErrUtl.NewArgs(e, "write failed", "url", url.Raw(), "bgn", bgn, "len", len);}
		}
		else
			trg_bfr.AddMid(bry, bgn, bgn + len);
	}
	public byte[] To_ary_and_clear() {         
		return trg_bfr == null ? Io_mgr.Instance.LoadFilBry(url) : trg_bfr.ToBryAndClear();
	}
	public void Flush() {
		if (trg_bfr == null)
			bry_stream.Flush();
	}
	public void Rls() {
		try {
			if (trg_bfr == null)
				bry_stream.Rls();
		}
		catch (Exception e) {throw ErrUtl.NewArgs(e, "close failed", "url", url.Raw());}
	}
	}
