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
package gplx.xowa.apps.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*; import gplx.core.tests.*;
public class Xoa_url_encoder__tst {
	private final    Xoa_url_encoder__fxt fxt = new Xoa_url_encoder__fxt();
	@Test  public void Syms__diff() 	{fxt.Test__encode(" &'=+", "_%26%27%3D%2B");}
	@Test  public void Syms__same() 	{fxt.Test__encode("!\"#$%()*,-./:;<>?@[\\]^_`{|}~", "!\"#$%()*,-./:;<>?@[\\]^_`{|}~");}
	@Test  public void Mixed() 			{fxt.Test__encode("a &'=+b", "a_%26%27%3D%2Bb");}	// PURPOSE: make sure dirty flag is set
}
class Xoa_url_encoder__fxt {
	private final    Xoa_url_encoder encoder = new Xoa_url_encoder();
	public void Test__encode(String raw, String expd) {
		Gftest.Eq__bry(Bry_.new_u8(expd), encoder.Encode(Bry_.new_u8(raw)));
	}
}
