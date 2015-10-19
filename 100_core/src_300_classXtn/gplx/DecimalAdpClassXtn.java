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
public class DecimalAdpClassXtn extends ClassXtn_base implements ClassXtn {
	public String Key() {return Key_const;}						public static final String Key_const = "decimal";	// current dsv files reference "decimal"
	@Override public Class<?> UnderClass()					{return Decimal_adp.class;}
	public Object DefaultValue()								{return 0;}
	public boolean Eq(Object lhs, Object rhs) {try {return Decimal_adp_.cast(lhs).Eq(Decimal_adp_.cast(rhs));} catch (Exception e) {Err_.Noop(e); return false;}}
	@Override public Object ParseOrNull(String raw)				{return Decimal_adp_.parse(raw);}
	@Override public String XtoUi(Object obj, String fmt)		{return Decimal_adp_.cast(obj).To_str();}
	public static final DecimalAdpClassXtn Instance =  new DecimalAdpClassXtn(); DecimalAdpClassXtn() {} // added to ClassXtnPool by default
}
