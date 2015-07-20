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
public class GfuiWin_ {
	public static final String 
		  InitKey_winType			= "winType"
		, InitKey_winType_toaster	= "toaster"
		, InitKey_winType_app		= "app"
		, InitKey_winType_tool		= "tool"
		;
	public static GfuiWin as_(Object obj) {return obj instanceof GfuiWin ? (GfuiWin)obj : null;}
	public static GfuiWin cast_(Object obj) {try {return (GfuiWin)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, GfuiWin.class, obj);}}
	public static GfuiWin app_(String key)							{return bld_(key, InitKey_winType_app, KeyValHash.new_());}
	public static GfuiWin tool_(String key)							{return bld_(key, InitKey_winType_tool, KeyValHash.new_()).TaskbarVisible_(false);}
	public static GfuiWin sub_(String key, GfuiWin ownerWin)		{
		KeyValHash ctorArgs = KeyValHash.new_();
		if (ownerWin != null) ctorArgs.Add(GfuiElem_.InitKey_ownerWin, ownerWin);	// WORKAROUND/JAVA: null ownerWin will fail when adding to hash
		return bld_(key, InitKey_winType_tool, ctorArgs);
	}
	public static GfuiWin toaster_(String key, GfuiWin ownerWin)	{return bld_(key, InitKey_winType_toaster, KeyValHash.new_().Add(GfuiElem_.InitKey_ownerWin, ownerWin));}
	static GfuiWin bld_(String key, String winType, KeyValHash ctorArgs) {
		GfuiWin rv = new GfuiWin();
		rv.Key_of_GfuiElem_(key);
		ctorArgs.Add(InitKey_winType, winType)
			.Add(GfuiElem_.InitKey_focusAble, false);
		rv.ctor_GfuiBox_base(ctorArgs);
		return rv;
	}
	public static GfuiWin kit_(Gfui_kit kit, String key, GxwWin under, KeyValHash ctorArgs) {
		GfuiWin rv = new GfuiWin();
		rv.ctor_kit_GfuiElemBase(kit, key, under, ctorArgs);
		return rv;
	}
}
class GfuiWinUtl {
	@gplx.Internal protected static void Open_exec(GfuiWin win, List_adp loadList, GfuiElemBase owner) {
		for (int i = 0; i < owner.SubElems().Count(); i++) {
			GfuiElemBase sub = (GfuiElemBase)owner.SubElems().Get_at(i);
			sub.OwnerWin_(win);
			for (Object itmObj : loadList) {
				GfuiWinOpenAble itm = (GfuiWinOpenAble)itmObj;
				itm.Open_exec(win, owner, sub);
			}
			Open_exec(win, loadList, sub);
		}
	}
	@gplx.Internal protected static void SubElems_dispose(GfuiElem owner) {
		for (int i = 0; i < owner.SubElems().Count(); i++) {
			GfuiElem sub = (GfuiElem)owner.SubElems().Get_at(i);
			sub.Dispose();
			SubElems_dispose(sub);
		}
	}
}
interface GfuiWinOpenAble {
	void Open_exec(GfuiWin win, GfuiElemBase owner, GfuiElemBase sub);
}
