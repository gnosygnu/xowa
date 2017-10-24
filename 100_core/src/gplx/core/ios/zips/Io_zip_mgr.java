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
package gplx.core.ios.zips; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
public interface Io_zip_mgr {
	void Zip_fil(Io_url src_fil, Io_url trg_fil);
	byte[] Zip_bry(byte[] src, int bgn, int len);
	byte[] Unzip_bry(byte[] src, int bgn, int len);
	void Unzip_to_dir(Io_url src_fil, Io_url trg_dir);
	void Zip_dir(Io_url src_dir, Io_url trg_fil);
}
