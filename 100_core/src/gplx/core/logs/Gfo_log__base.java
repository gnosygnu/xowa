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
public abstract class Gfo_log__base implements Gfo_log {
	private long time_prv = 0; 
	public void Warn(String msg, Object... args) {
		long time = gplx.core.envs.System_.Ticks();
		long elapsed = time_prv == 0 ? 0 : time - time_prv;
		Exec(Gfo_log_itm.Type__warn, time, elapsed, msg, args);
	}
	public void Note(String msg, Object... args) {
		long time = gplx.core.envs.System_.Ticks();
		long elapsed = time_prv == 0 ? 0 : time - time_prv;
		Exec(Gfo_log_itm.Type__note, time, elapsed, msg, args);
	}
	public void Info(String msg, Object... args) {
		long time = gplx.core.envs.System_.Ticks();
		long elapsed = time_prv == 0 ? 0 : time - time_prv;
		Exec(Gfo_log_itm.Type__info, time, elapsed, msg, args);
	}
	public void Prog(String msg, Object... args) {
		long time = gplx.core.envs.System_.Ticks();
		long elapsed = time_prv == 0 ? 0 : time - time_prv;
		Exec(Gfo_log_itm.Type__prog, time, elapsed, msg, args);
	}
	public abstract List_adp Itms(); public abstract Gfo_log Itms_(List_adp v);
	public abstract void Exec(byte type, long time, long elapsed, String msg, Object[] args);
	public abstract void Flush();
}
