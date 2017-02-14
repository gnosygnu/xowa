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
package gplx.gfui.kits.swts; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
import gplx.*;
import gplx.core.threads.Thread_adp_;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import gplx.gfui.controls.gxws.GxwCbkHost;
import gplx.gfui.controls.gxws.GxwCore_base;
import gplx.gfui.controls.gxws.Gxw_grp;
import gplx.gfui.draws.*;
import gplx.gfui.kits.core.GfuiInvkCmd;
import gplx.gfui.kits.core.Swt_kit;

public class Swt_grp implements Gxw_grp, Swt_control, FocusListener, Gfo_evt_mgr_owner {
//	private GfuiInvkCmd cmd_sync;
	private Composite composite;
	public Swt_grp(Swt_kit kit, Swt_control owner, Keyval_hash ctorArgs) {
		this.kit = kit;
		composite = new Composite(owner.Under_composite(), SWT.NONE);
		core = new Swt_core__basic(composite);
		composite.addKeyListener(new Swt_lnr_key(this));
		composite.addMouseListener(new Swt_lnr_mouse(this));
	}
	public Swt_kit Kit() {return kit;} private Swt_kit kit;
	@Override public Control Under_control() {return composite;}
	@Override public Composite Under_composite() {return composite;}
	@Override public Control Under_menu_control() {return composite;}
	public Gfo_evt_mgr Evt_mgr() {return ev_mgr;} private Gfo_evt_mgr ev_mgr;
	public void Evt_mgr_(Gfo_evt_mgr v) {ev_mgr = v;}
	@Override public GxwCore_base Core() {return core;} GxwCore_base core;
	@Override public GxwCbkHost Host() {return host;} @Override public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host;
	@Override public String TextVal() {return "not implemented";}
	@Override public void TextVal_set(String v) {}
	@Override public void EnableDoubleBuffering() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return Gfo_invk_.Rv_unhandled;
	}
	@Override public void focusGained(FocusEvent arg0) {}
	@Override public void focusLost(FocusEvent arg0) {}
}
