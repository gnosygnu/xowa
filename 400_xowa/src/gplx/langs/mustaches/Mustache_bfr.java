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
package gplx.langs.mustaches; import gplx.*; import gplx.langs.*;
public class Mustache_bfr {
	private final    Bry_bfr bfr;
	public Mustache_bfr(Bry_bfr bfr) {this.bfr = bfr;}
	public Bry_bfr Bfr() {return bfr;}
	public Mustache_bfr Escape_(boolean v) {escape = v; return this;} private boolean escape;
	public void Add_int			(int v)		{bfr.Add_int_variable(v);}
	public void Add_long		(long v)	{bfr.Add_long_variable(v);}
	public void Add_double		(double v)	{bfr.Add_double(v);}
	public void Add_str_u8		(String v)	{bfr.Add_str_u8(v);}
	public void Add_str_u8_safe	(String v)	{if (v != null) bfr.Add_str_u8(v);}
	public void Add_mid			(byte[] src, int bgn, int end) {bfr.Add_mid(src, bgn, end);}
	public void Add_bry			(byte[] v) {
		if (v == null) return;	// allow items to have null props
		if (escape)
			gplx.langs.htmls.Gfh_utl.Escape_html_to_bfr(bfr, v, 0, v.length, Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y);
		else
			bfr.Add(v);
	}
	public byte[] To_bry_and_clear() {return bfr.To_bry_and_clear();}
	public String To_str_and_clear() {return bfr.To_str_and_clear();}
	public static Mustache_bfr New()				{return new Mustache_bfr(Bry_bfr_.New());}
	public static Mustache_bfr New_bfr(Bry_bfr v)	{return new Mustache_bfr(v);}
}
