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
public class IptKey implements IptArg {
	@gplx.Internal protected IptKey(int val, String key) {this.val = val; this.key = key;}
	public String Key()					{return key;} private final String key;
	public int Val()					{return val;} private final int val;
	public boolean Eq(IptArg comp)			{return String_.Eq(key, comp.Key());}
	public String XtoUiStr()			{return IptKeyStrMgr._.To_str(this);}
	public IptKey Add(IptKey comp)		{return IptKey_.add_(this, comp);}
	public boolean Mod_shift()				{return Enm_.Has_int(val, IptKey_.Shift.Val());}
	public boolean Mod_ctrl()				{return Enm_.Has_int(val, IptKey_.Ctrl.Val());}
	public boolean Mod_alt()				{return Enm_.Has_int(val, IptKey_.Alt.Val());}
}
