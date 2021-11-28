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
package gplx.core.security.algos.gplx_crypto; import gplx.*; import gplx.core.*; import gplx.core.security.*; import gplx.core.security.algos.*;
import org.junit.*; import gplx.core.consoles.*; import gplx.core.ios.streams.*; /*IoStream*/
public class Hash_console_wtr_tst {
	private final    Hash_console_wtr_fxt fxt = new Hash_console_wtr_fxt();
	@Test public void Basic() {
		fxt.Test__Status(10, " - hash: 100%");
		fxt.Test__Status(11, " - hash: 66%");
		fxt.Test__Status(30, " - hash: 40%", " - hash: 60%", " - hash: 100%");
	}
}
class Hash_console_wtr_fxt {
	private Hash_algo__tth_192 algo;
	public Hash_console_wtr_fxt() {
		this.algo = new Hash_algo__tth_192();
		algo.BlockSize_set(10);
	}
	public void Test__Status(int count, String... expd) {
		// init
		Console_adp__mem console = Console_adp_.Dev();

		// exec
		String data = String_.Repeat("A", count);
		IoStream stream = IoStream_.mem_txt_(Io_url_.Empty, data);
		algo.Calc_hash_w_prog_as_str(stream, console);

		// test
		String[] actl = console.Written().To_str_ary();
		Tfds.Eq_ary(actl, expd);
	}
}
