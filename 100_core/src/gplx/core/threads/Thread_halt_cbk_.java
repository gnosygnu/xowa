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
package gplx.core.threads;
import gplx.*; import gplx.core.*;
public class Thread_halt_cbk_ {
	public static final Thread_halt_cbk Noop = new Thread_halt_cbk_noop();
}
class Thread_halt_cbk_noop implements Thread_halt_cbk {
	public void Thread__on_halt(boolean interrupted) {}
}
