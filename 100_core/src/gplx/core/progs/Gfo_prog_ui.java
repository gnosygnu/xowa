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
package gplx.core.progs; import gplx.*; import gplx.core.*;
public interface Gfo_prog_ui extends Cancelable {
	byte			Prog_status();
	void			Prog_status_(byte v);
	long			Prog_data_cur();
	long			Prog_data_end();
	boolean			Prog_notify_and_chk_if_suspended(long cur, long max);
	void			Prog_notify_by_msg(String msg);
}
