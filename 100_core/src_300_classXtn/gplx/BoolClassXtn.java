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
package gplx;
public class BoolClassXtn extends ClassXtn_base implements ClassXtn {
	public static final String Key_const = "bo" + "ol";
	public String Key() {return Key_const;}
	@Override public Class<?> UnderClass()					{return boolean.class;}
	public Object DefaultValue()								{return false;}
	public boolean Eq(Object lhs, Object rhs) {try {return Bool_.cast_(lhs) == Bool_.cast_(rhs);} catch (Exception e) {Exc_.Noop(e); return false;}}
	@Override public Object ParseOrNull(String raw)				{
		if	(	String_.Eq(raw, "true")
			||	String_.Eq(raw, "True")	// needed for Store_Wtr() {boolVal.toString();}
			||	String_.Eq(raw, "1")		// needed for db; gplx field for boolean is int; need simple way to convert from dbInt to langBool
			)		
			return true;
		else if 
			(	String_.Eq(raw, "false")
			||	String_.Eq(raw, "False")
			||	String_.Eq(raw, "0")
			) 
			return false;
		throw Exc_.new_parse_type(boolean.class, raw);
	}
	@Override public Object XtoDb(Object obj)					{return obj;}
	public static final BoolClassXtn _ =  new BoolClassXtn(); BoolClassXtn() {} // added to ClassXtnPool by default
}