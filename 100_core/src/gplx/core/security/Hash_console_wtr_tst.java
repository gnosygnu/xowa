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
package gplx.core.security; import gplx.*; import gplx.core.*;
import org.junit.*; import gplx.core.consoles.*; import gplx.core.ios.streams.*; /*IoStream*/
public class Hash_console_wtr_tst {
	@Before public void setup() {
		Hash_algo__tth_192 algo = new Hash_algo__tth_192();
		algo.BlockSize_set(10);
		calc = algo;
	}
	@Test  public void Basic() {
		tst_Status(10, stringAry_(" - hash: 100%"));
		tst_Status(11, stringAry_(" - hash: 66%"));
		tst_Status(30, stringAry_(" - hash: 40%", " - hash: 60%", " - hash: 100%"));
	}
	void tst_Status(int count, String[] expdWritten) {
		Console_adp__mem dialog = Console_adp_.Dev();
		String data = String_.Repeat("A", count);
		IoStream stream = IoStream_.mem_txt_(Io_url_.Empty, data);
		calc.Hash_stream_as_str(dialog, stream);
		String[] actlWritten = dialog.Written().To_str_ary();
		Tfds.Eq_ary(actlWritten, expdWritten);
	}
	String[] stringAry_(String... ary) {return ary;}
	Hash_algo calc;
}
