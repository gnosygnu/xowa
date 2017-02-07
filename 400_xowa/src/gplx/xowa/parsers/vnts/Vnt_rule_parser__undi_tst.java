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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Vnt_rule_parser__undi_tst {
	private final Vnt_rule_parser_fxt fxt = new Vnt_rule_parser_fxt();
	@Test  public void One()					{fxt.Test_parse("k1=>x1:v1;"				, "x1:k1=v1");}
	@Test  public void Many()					{fxt.Test_parse("k1=>x1:v1;k2=>x2:v2;"		, "x1:k1=v1", "x2:k2=v2");}
}
