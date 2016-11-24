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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
import gplx.gfui.controls.elems.*;
public class IptEventData {
	public GfuiElem		Sender() {return sender;} GfuiElem sender;
	public IptArg		EventArg() {return eventArg;} IptArg eventArg;
	public IptEventType EventType() {return eventType;} IptEventType eventType;

	public IptKey Key() {return keyData.Key();} IptEvtDataKey keyData; IptEvtDataKeyHeld keyPressData;
	public IptMouseBtn MouseBtn() {return mouseData.Button();} IptEvtDataMouse mouseData;
	public IptMouseWheel MouseWheel() {return mouseData.Wheel();}
	public PointAdp MousePos() {return mouseData.Pos();}
	public boolean Handled() {return handled;} private boolean handled;
	public void Handled_on() {Handled_set(true);}
	public void Handled_off() {Handled_set(false);}
	public boolean CancelIteration;
	void Handled_set(boolean val) {
		keyData.Handled_set(val);
		keyPressData.Handled_set(val);
		handled = val;
	}

	public static IptEventData as_(Object obj) {return obj instanceof IptEventData ? (IptEventData)obj : null;}
	public static IptEventData cast(Object obj) {try {return (IptEventData)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, IptEventData.class, obj);}}
	@gplx.Internal protected static IptEventData new_(GfuiElem sender, IptEventType eventType, IptArg eventArg, IptEvtDataKey keyData, IptEvtDataMouse mouseData) {return new_(sender, eventType, eventArg, keyData, IptEvtDataKeyHeld.Null, mouseData);}
	@gplx.Internal protected static IptEventData new_(GfuiElem sender, IptEventType eventType, IptArg eventArg, IptEvtDataKey keyData, IptEvtDataKeyHeld keyPressData, IptEvtDataMouse mouseData) {
		IptEventData rv = new IptEventData();
		rv.sender = sender;
		rv.eventType = eventType; rv.eventArg = eventArg;
		rv.keyData = keyData; rv.keyPressData = keyPressData; rv.mouseData = mouseData;
		return rv;
	}	IptEventData() {}
	public static IptEventData ctx_(GfsCtx ctx, GfoMsg m) {return IptEventData.cast(m.CastObj("iptData"));}
}
