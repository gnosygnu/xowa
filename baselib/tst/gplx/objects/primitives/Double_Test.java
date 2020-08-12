/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.objects.primitives;

import gplx.tests.Gftest_fxt;
import org.junit.Test;

public class Double_Test {
    private final Double_Tstr tstr = new Double_Tstr();

    @Test
    public void TrimZeroes() {
        tstr.Test_TrimZeroes("12.100"       , "12.1");
        tstr.Test_TrimZeroes("12.000"       , "12");
        tstr.Test_TrimZeroes("12.001"       , "12.001");
        tstr.Test_TrimZeroes("1020.00"      , "1020");
        tstr.Test_TrimZeroes("1020.00"      , "1020");
        tstr.Test_TrimZeroes("1.200e5"      , "1.2E5");
        tstr.Test_TrimZeroes("1.200e-05"    , "1.2E-5");
    }

    @Test
    public void ToStrByPrintF() {
        tstr.Test_ToStrByPrintF(1d / 2d                 , "0.5");                 // fails with 0.50000000000000
        tstr.Test_ToStrByPrintF(5d / 100000000000000000d, "5E-17");               // fails with 5.0000000000000e-17
        tstr.Test_ToStrByPrintF(7538000d / 7773352000d  , "0.00096972322879499"); // fails with 0; ISSUE#:697; DATE:2020-08-11
        tstr.Test_ToStrByPrintF(56225d   / 7776747000d  , "7.2298867379895E-06"); // fails with 0; ISSUE#:697; DATE:2020-08-11
        tstr.Test_ToStrByPrintF(35746d   / 7805411000d  , "4.5796435319037E-06"); // fails with 0; ISSUE#:697; DATE:2020-08-11
    }
}
class Double_Tstr {
    public void Test_ToStrByPrintF(double v, String expd) {
        Gftest_fxt.Eq__str(expd, Double_.ToStrByPrintF(v));
    }
    public void Test_TrimZeroes(String val, String expd) {
        Gftest_fxt.Eq__str(expd, Double_.TrimZeroes(val));
    }
}
