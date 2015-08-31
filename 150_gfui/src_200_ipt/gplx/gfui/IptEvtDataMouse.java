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
public class IptEvtDataMouse {
	public IptMouseBtn		Button() {return button;} IptMouseBtn button;
	public IptMouseWheel	Wheel() {return wheel;} IptMouseWheel wheel;
	public PointAdp			Pos() {return location;} PointAdp location;

	public static IptEvtDataMouse as_(Object obj) {return obj instanceof IptEvtDataMouse ? (IptEvtDataMouse)obj : null;}
	public static IptEvtDataMouse cast(Object obj) {try {return (IptEvtDataMouse)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, IptEvtDataMouse.class, obj);}}
	@gplx.Internal protected static final IptEvtDataMouse Null = IptEvtDataMouse.new_(IptMouseBtn_.None, IptMouseWheel_.None, 0, 0);
	public static IptEvtDataMouse new_(IptMouseBtn button, IptMouseWheel wheel, int x, int y) {
		IptEvtDataMouse rv = new IptEvtDataMouse();
		rv.button = button;
		rv.wheel = wheel;
		rv.location = PointAdp_.new_(x, y);
		return rv;
	}
}
