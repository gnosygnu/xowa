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
package gplx.gfui; import gplx.*;
public class IptEvtDataKeyHeld {
	public char KeyChar() {return c;} char c;
	public boolean Handled() {return handled;} public void Handled_set(boolean v) {handled = v;} private boolean handled;

	public static IptEvtDataKeyHeld as_(Object obj) {return obj instanceof IptEvtDataKeyHeld ? (IptEvtDataKeyHeld)obj : null;}
	public static IptEvtDataKeyHeld cast_(Object obj) {try {return (IptEvtDataKeyHeld)obj;} catch(Exception exc) {throw Err_.type_mismatch_exc_(exc, IptEvtDataKeyHeld.class, obj);}}
	@gplx.Internal protected static final IptEvtDataKeyHeld Null = char_((char)0);
	@gplx.Internal protected static IptEvtDataKeyHeld char_(char c) {
		IptEvtDataKeyHeld rv = new IptEvtDataKeyHeld();
		rv.c = c;
		return rv;
	}
}
