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
package gplx.core.logs; import gplx.*; import gplx.core.*;
public class Gfo_log_itm {
	public Gfo_log_itm(byte type, long time, long elapsed, String msg, Object[] args) {
		this.Type = type;
		this.Time = time;
		this.Elapsed = elapsed;
		this.Msg = msg;
		this.Args = args;
	}
	public final    byte Type;
	public final    long Time;
	public final    long Elapsed;
	public final    String Msg;
	public final    Object[] Args;

	public static final byte Type__fail = 0, Type__warn = 1, Type__note = 2, Type__info = 3, Type__prog = 4;
}
