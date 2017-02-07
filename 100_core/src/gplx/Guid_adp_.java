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
public class Guid_adp_ {
	public static final String Cls_ref_name = "Guid";
	public static final    Guid_adp Empty = Parse("00000000-0000-0000-0000-000000000000");
	public static String New_str() {return New().To_str();}
	public static Guid_adp New() {return new Guid_adp(java.util.UUID.randomUUID());}
	public static Guid_adp Parse(String s) {return new Guid_adp(java.util.UUID.fromString(s));}
}