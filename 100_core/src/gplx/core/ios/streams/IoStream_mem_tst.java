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
import org.junit.*; //using System.IO; /*Stream*/
public class IoStream_mem_tst {
	@Test  public void Write() { // confirm that changes written to Stream acquired via .AdpObj are written to IoStream_mem.Buffer
		IoStream_mem stream = IoStream_mem.wtr_data_(Io_url_.Empty, 0);
		byte[] data = Bry_.New_by_ints(1);
		stream.Write(data, 0, Array_.Len(data));

		Tfds.Eq(1L		, stream.Len());
		Tfds.Eq((byte)1	, stream.Buffer()[0]);
		stream.Rls();
	}
}
