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
public class Err_arg extends Err {
	public String ArgName() {return argName;} private String argName;
	public Object ArgValue() {return argValue;} Object argValue;
	public static Err_arg null_(String argName) {
		Err_arg rv = new Err_arg();
		rv.Key_("gplx.arg").Hdr_("@" + rv.argName + " cannot be null");
		rv.argName = argName;
		return rv;
	}
	public static Err_arg cannotBe_(String msg, String argName, Object argValue) {
		Err_arg rv = new Err_arg();
		rv.Key_("gplx.arg");
		rv.Hdr_("val cannot be " + msg);
		rv.Add("key", argName);
		rv.Add("val", argValue);
		return rv;
	}
	public static Err_arg notFound_key_(String argName, Object argValue) {
		Err_arg rv = new Err_arg();
		rv.Key_("gplx.arg").Hdr_("arg not found").Add(argName, argValue);
		rv.argName = argName;
		rv.argValue = argValue;
		return rv;
	}
	public static Err_arg outOfBounds_(String argName, int i, int count) {
		Err_arg rv = new Err_arg();
		rv.Key_("gplx.arg").Hdr_("arg out of bounds").Add("argName", argName).Add("argVal", i).Add("count", count);
		rv.argName = argName;
		rv.argValue = i;
		return rv;
	}
	public static boolean ClassCheck(Exception e) {
		return ClassAdp_.Eq_typeSafe(e, Err_arg.class);
	}
}
