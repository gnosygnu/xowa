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
package gplx.xowa.xtns.scores; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Score_xnde_tst {
	@Test  public void Version() {
		Tfds.Eq_bry(Bry_.new_a7("2.16.2"), Score_xnde.Get_lilypond_version("GNU LilyPond 2.16.2\nline1\nline2"));
		Tfds.Eq_bry(Bry_.new_a7("2.16.2"), Score_xnde.Get_lilypond_version("GNU LilyPond 2.16.2\r\nline1\r\nline2"));
	}
}
