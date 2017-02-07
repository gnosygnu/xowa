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
public class Rls_able_ {
	public static Rls_able as_(Object obj) {return obj instanceof Rls_able ? (Rls_able)obj : null;}
	public static Rls_able cast(Object obj) {try {return (Rls_able)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, Rls_able.class, obj);}}
	public static final Rls_able Null = new Rls_able__noop();
}
class Rls_able__noop implements Rls_able {
	public void Rls() {}
}
