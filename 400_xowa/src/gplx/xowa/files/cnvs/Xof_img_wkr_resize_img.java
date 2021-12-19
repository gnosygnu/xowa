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
package gplx.xowa.files.cnvs;
import gplx.libs.files.Io_url;
import gplx.types.basics.wrappers.StringRef;
public interface Xof_img_wkr_resize_img {
	boolean Resize_exec(Io_url src, Io_url trg, int trg_w, int trg_h, int ext_id, StringRef rslt_val);
}
