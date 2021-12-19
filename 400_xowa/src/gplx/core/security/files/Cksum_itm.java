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
package gplx.core.security.files;
import gplx.types.custom.brys.wtrs.BryBfrAble;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.libs.files.Io_url;
public class Cksum_itm implements BryBfrAble {
	public Cksum_itm(byte[] hash, Io_url file_url, long file_size) {
		this.Hash = hash; this.File_url = file_url; this.File_size = file_size;
	}
	public final byte[] Hash;
	public final Io_url File_url;
	public final long File_size;
	public void AddToBfr(BryWtr bfr) {
		bfr.Add(Hash).AddBytePipe();
		bfr.AddStrU8(File_url.Raw()).AddBytePipe();
		bfr.AddLongVariable(File_size);
	}
}
