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
public class RlsAble_ {
	public static RlsAble as_(Object obj) {return obj instanceof RlsAble ? (RlsAble)obj : null;}
	public static RlsAble cast_(Object obj) {try {return (RlsAble)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, RlsAble.class, obj);}}
	public static final RlsAble Null = new RlsAble__noop();
}
class RlsAble__noop implements RlsAble {
	public void Rls() {}
}
