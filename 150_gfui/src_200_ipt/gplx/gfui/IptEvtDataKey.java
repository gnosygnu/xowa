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
public class IptEvtDataKey {
	public IptKey Key() {return key;} IptKey key;
	public boolean Handled() {return handled;} public void Handled_set(boolean v) {handled = v;} private boolean handled;

	public static IptEvtDataKey as_(Object obj) {return obj instanceof IptEvtDataKey ? (IptEvtDataKey)obj : null;}
	public static IptEvtDataKey cast(Object obj) {try {return (IptEvtDataKey)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, IptEvtDataKey.class, obj);}}
	@gplx.Internal protected static final IptEvtDataKey Null = new_(IptKey_.None);
	@gplx.Internal protected static IptEvtDataKey test_(IptKey keyArg) {return new_(keyArg);}
	@gplx.Internal protected static IptEvtDataKey int_(int val) {
		IptKey keyArg = IptKey_.api_(val);
		return new_(keyArg);
	}
	@gplx.Internal protected static IptEvtDataKey new_(IptKey key) {
		IptEvtDataKey rv = new IptEvtDataKey();
		rv.key = key;
		return rv;
	}
}
