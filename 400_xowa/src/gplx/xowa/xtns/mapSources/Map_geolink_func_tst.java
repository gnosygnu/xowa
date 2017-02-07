/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
