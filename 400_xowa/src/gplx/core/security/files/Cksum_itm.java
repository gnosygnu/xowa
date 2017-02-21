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
package gplx.core.security.files; import gplx.*; import gplx.core.*; import gplx.core.security.*;
public class Cksum_itm implements gplx.core.brys.Bry_bfr_able {
	public Cksum_itm(byte[] hash, Io_url file_url, long file_size) {
		this.Hash = hash; this.File_url = file_url; this.File_size = file_size;
	}
	public final    byte[] Hash;
	public final    Io_url File_url;
	public final    long File_size;
	public void To_bfr(Bry_bfr bfr) {
		bfr.Add(Hash).Add_byte_pipe();
		bfr.Add_str_u8(File_url.Raw()).Add_byte_pipe();
		bfr.Add_long_variable(File_size);
	}
}
