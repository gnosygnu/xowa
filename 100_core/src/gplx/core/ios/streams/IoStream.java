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
public interface IoStream extends Rls_able {
	Object UnderRdr();
	Io_url Url();
	long Pos();
	long Len();

	int ReadAry(byte[] array);
	int Read(byte[] array, int offset, int count);
	long Seek(long pos);
	void WriteAry(byte[] ary);
	void Write(byte[] array, int offset, int count);
	void Transfer(IoStream trg, int bufferLength);
	void Flush();
	void Write_and_flush(byte[] bry, int bgn, int end);
}