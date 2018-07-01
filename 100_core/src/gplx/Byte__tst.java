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
package gplx;
import org.junit.*;
public class Byte__tst {
	@Test  public void int_() {
		tst_int_(   0,    0);
		tst_int_( 127,  127);
		tst_int_( 128,  128);	// NOTE: JAVA defines byte as -128 -> 127
		tst_int_( 255,  255);
		tst_int_( 256,    0);	// NOTE: 256 will cast to 1; (byte)256 works same in both JAVA/.NET
	}	void tst_int_(int v, int expd) {Tfds.Eq((byte)expd, Byte_.By_int(v));}	// WORKAROUND/JAVA: expd is of type int b/c java promotes numbers to ints
	@Test  public void To_int() {
		tst_XtoInt(   0,    0);
		tst_XtoInt( 127,  127);
		tst_XtoInt( 128,  128);
		tst_XtoInt( 255,  255);
		tst_XtoInt( 256,    0);
	}	void tst_XtoInt(int v, int expd) {Tfds.Eq(expd, Byte_.To_int((byte)v));}	// WORKAROUND/JAVA: v is of type int b/c java promotes numbers to ints
}
