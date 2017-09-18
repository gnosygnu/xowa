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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import org.junit.*;
public class Xof_file_wkr__tst {		
	private final    Xof_file_wkr___fxt fxt = new Xof_file_wkr___fxt();
	@Test 	public void Ttl_standardize() {
		fxt.Test__ttl_standardize("Abc.png"		, "Abc.png");		// basic
		fxt.Test__ttl_standardize("A b.png"		, "A_b.png");		// spaces -> unders
		fxt.Test__ttl_standardize("A b c.png"	, "A_b_c.png");		// spaces -> unders; multiple
		fxt.Test__ttl_standardize("abc.png"		, "Abc.png");		// ucase 1st
	}
}
class Xof_file_wkr___fxt {
	public void Test__ttl_standardize(String src_str, String expd) {
		Tfds.Eq_bry(Bry_.new_u8(expd), Xof_file_wkr_.Ttl_standardize(Bry_.new_u8(src_str)));
	}
}
