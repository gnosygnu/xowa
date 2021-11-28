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
package gplx.core.security.algos; import gplx.*; import gplx.core.*; import gplx.core.security.*;
public class Hash_algo__fxt {
	private final Hash_algo algo;
	public Hash_algo__fxt(Hash_algo algo) {this.algo = algo;}
	public void Test__hash(String expd, String raw) {
		Tfds.Eq(expd, Hash_algo_utl.Calc_hash_as_str(algo, Bry_.new_u8(raw)));
		Tfds.Eq(expd, Hash_algo_utl.Calc_hash_w_prog_as_str(algo, gplx.core.ios.streams.IoStream_.mem_txt_(Io_url_.Empty, raw), gplx.core.consoles.Console_adp_.Noop));
	}
}
