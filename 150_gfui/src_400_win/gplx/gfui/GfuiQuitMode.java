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
public class GfuiQuitMode {
	public int Val() {return val;} int val;
	GfuiQuitMode(int val) {this.val = val;}
	public static final GfuiQuitMode ExitApp		= new GfuiQuitMode(1);
	public static final GfuiQuitMode Destroy		= new GfuiQuitMode(2);
	public static final GfuiQuitMode Suspend		= new GfuiQuitMode(3);
	public static final String
		    Destroy_cmd			= "destroy"
		,	Suspend_cmd			= "suspend"
		,	SuspendApp_cmd		= "suspendApp"	// TODO: merge with suspend; needs Msg Addressing (*.suspend vs app.suspend)
		;
	public static void Exec(GfoInvkAble invk, GfuiQuitMode stopMode) {
		int val = stopMode.Val();
		if		(val == GfuiQuitMode.Destroy.Val())		GfoInvkAble_.InvkCmd(invk, GfuiQuitMode.Destroy_cmd);
		else if	(val == GfuiQuitMode.Suspend.Val())		GfoInvkAble_.InvkCmd(invk, GfuiQuitMode.Suspend_cmd);
		else if	(val == GfuiQuitMode.ExitApp.Val())		GfuiEnv_.Exit();
	}
}
