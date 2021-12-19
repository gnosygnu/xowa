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
import gplx.types.errs.ErrUtl;
public class Io_stream_wtr__bzip2 extends Io_stream_wtr__base {
	@Override public byte Tid() {return Io_stream_tid_.Tid__bzip2;}
		@Override public java.io.OutputStream Wrap_stream(java.io.OutputStream stream) {
		try {return new org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream(stream);}
		catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to open bzip2 stream");}
	}
	}
