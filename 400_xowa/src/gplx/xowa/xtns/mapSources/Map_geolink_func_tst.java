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
package gplx.xowa.xtns.mapSources; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Map_geolink_func_tst {		
	@Before public void init()				{fxt.Reset();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Example() {
		fxt.Test_parse_tmpl_str_test
		( "{{#geoLink: $1 $2 $3 $4 $5 $6|lat=10.5|long=20.5|plusLat=N|plusLong=E|minusLat=S|minusLong=W|precision=decimal places}}", "{{test}}"
		, "10.5_N 20.5_E 10° 30' 0&quot; N 20° 30' 0&quot; E 10.5 20.5")
		;
	}
	@Test  public void Error__invalid_dlm() {	// PURPOSE: handle invalid dlm; EX:20°13′17'; PAGE:pl.v:Rezerwat_przyrody_Ciosny DATE:2014-08-14
		Xop_fxt.Init_msg(fxt.Wiki(), "mapsources-math-incorrect-input", "incorrect input");
		fxt.Test_parse_tmpl_str_test
		( "{{#geoLink: $1 $2 $3 $4 $5 $6|lat=51°31′37″|long=20°13′17'}}", "{{test}}"
		, "51.5269_N 0_E 51° 31' 37&quot; N incorrect input 51.5269 0")
		;
	}
}
