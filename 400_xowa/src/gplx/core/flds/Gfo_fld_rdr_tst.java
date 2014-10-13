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
package gplx.core.flds; import gplx.*; import gplx.core.*;
import org.junit.*;
import gplx.ios.*;
public class Gfo_fld_rdr_tst {
	Gfo_fld_rdr_fxt fxt = new Gfo_fld_rdr_fxt();
	@Test  public void Read_int() 							{fxt.ini_xdat().Raw_("123|")			.tst_Read_int(123);}
	@Test  public void Read_double() 						{fxt.ini_xdat().Raw_("1.23|")			.tst_Read_double(1.23);}
	@Test  public void Read_str_simple() 					{fxt.ini_xdat().Raw_("ab|")				.tst_Read_str_simple("ab");}
	@Test  public void Read_str_escape_pipe() 				{fxt.ini_xdat().Raw_("a~pb|")			.tst_Read_str_escape("a|b");}
	@Test  public void Read_str_escape_tilde() 				{fxt.ini_xdat().Raw_("a~~b|")			.tst_Read_str_escape("a~b");}
	@Test  public void Read_str_escape_nl() 				{fxt.ini_xdat().Raw_("a~nb|")			.tst_Read_str_escape("a\nb");}
	@Test  public void Read_str_escape_tab() 				{fxt.ini_xdat().Raw_("a~tb|")			.tst_Read_str_escape("a\tb");}
	@Test  public void Write_str_escape_pipe() 				{fxt.ini_xdat().tst_Write_str_escape("a|b", "a~pb|");}
	@Test  public void Read_str_quoted_comma() 				{fxt.ini_sql ().Raw_("'a,b',")			.tst_Read_str_escape("a,b");}
	@Test  public void Read_str_quoted_apos() 				{fxt.ini_sql ().Raw_("'a\\'b',")		.tst_Read_str_escape("a'b");}
	@Test  public void Read_multiple() {
		fxt.ini_xdat().Raw_("ab|1|.9|\n")
		.tst_Read_str_escape("ab").tst_Read_int(1).tst_Read_double(.9)
		;
	}
	@Test  public void Read_dlm_nl()							{fxt.ini_xdat().Raw_("123\n")			.tst_Read_int(123);}
}
class Gfo_fld_rdr_fxt {
	Gfo_fld_rdr rdr = new Gfo_fld_rdr(); Gfo_fld_wtr wtr = Gfo_fld_wtr.xowa_();
	public Gfo_fld_rdr_fxt Raw_(String v) {rdr.Data_(Bry_.new_utf8_(v)); return this;}
	public Gfo_fld_rdr_fxt ini_xdat() 	{rdr.Ctor_xdat(); return this;}
	public Gfo_fld_rdr_fxt ini_sql() 	{rdr.Ctor_sql(); return this;}
	public Gfo_fld_rdr_fxt tst_Read_int(int expd) 					{Tfds.Eq(expd, rdr.Read_int()); return this;}
	public Gfo_fld_rdr_fxt tst_Read_double(double expd) 			{Tfds.Eq(expd, rdr.Read_double()); return this;}
	public Gfo_fld_rdr_fxt tst_Read_str_simple(String expd) 		{Tfds.Eq(expd, rdr.Read_str_simple()); return this;}
	public Gfo_fld_rdr_fxt tst_Read_str_escape(String expd) 		{Tfds.Eq(expd, rdr.Read_str_escape()); return this;}
	public Gfo_fld_rdr_fxt tst_Write_str_escape(String val, String expd) {
		byte[] bry = Bry_.new_utf8_(val);
		wtr.Bfr_(bfr);
		wtr.Write_bry_escape_fld(bry);
		Tfds.Eq(expd, bfr.Xto_str());
		return this;
	}	private Bry_bfr bfr = Bry_bfr.new_();
}
