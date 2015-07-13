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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pft_func_time_int_tst {
	@Before	public void init()							{fxt.Reset(); Tfds.Now_set(DateAdp_.new_(2012, 1, 2, 3, 4, 5, 6));} private Xop_fxt fxt = new Xop_fxt();
	@Test   public void Time_before_date__dmy()			{fxt.Test_parse_tmpl_str("{{#time:Y-m-d H:i|01:02 3.4.2005}}"				, "2005-04-03 01:02");}	// PAGE:sk.w:Dr._House; DATE:2014-09-23
	@Test   public void Time_before_date__mdy()			{fxt.Test_parse_tmpl_str("{{#time:Y-m-d H:i|01:02 3.14.2005}}"				, "<strong class=\"error\">Invalid month: 14</strong>");}	// mdy is invalid; DATE:2014-09-23
}
