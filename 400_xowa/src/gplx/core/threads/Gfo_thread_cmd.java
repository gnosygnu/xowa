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
package gplx.core.threads; import gplx.*; import gplx.core.*;
public interface Gfo_thread_cmd extends Gfo_invk {
	void Cmd_ctor();
	String Async_key();
	int Async_sleep_interval();
	boolean Async_prog_enabled(); 
	void Async_prog_run(int async_sleep_sum);
	byte Async_init();
	boolean Async_term();
	void Async_run();
	boolean Async_running();
	Gfo_thread_cmd Async_next_cmd(); void Async_next_cmd_(Gfo_thread_cmd next);
}
