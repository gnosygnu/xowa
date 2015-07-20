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
package gplx.gfui; import java.security.acl.Owner;

import gplx.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
public class Swt_popup_grp implements Gfui_mnu_grp {
	private Decorations owner_win; private Control owner_box; private boolean menu_is_bar = false;
	Swt_popup_grp(String root_key){this.root_key = root_key;}
	@Override public int Tid() {return Gfui_mnu_itm_.Tid_grp;}
	@Override public String Uid() {return uid;} private String uid = Gfui_mnu_itm_.Gen_uid(); 
	@Override public boolean Enabled() {return menu.getEnabled();} @Override public void Enabled_(boolean v) {menu.setEnabled(v);}
	@Override public boolean Disposed() {return menu.isDisposed();}
	@Override public String Text() {return menu_item.getText();} @Override public void Text_(String v) {menu_item.setText(v);}
	@Override public ImageAdp Img() {return img;} @Override public void Img_(ImageAdp v) {
		img = v; 
		if (v == ImageAdp_.Null)
			menu_item.setImage(null);
		else
			menu_item.setImage((Image)v.Under());
	}	private ImageAdp img;
	@Override public boolean Selected() {return menu_item.getSelection();} @Override public void Selected_(boolean v) {menu_item.setSelection(v);}
	public String Root_key() {return root_key;} private String root_key;
	public Menu Under_menu() {return menu;} private Menu menu;
	public MenuItem Under_menu_item() {return menu_item;} private MenuItem menu_item;
	public Object Under() {return menu;}
	@Override public void Itms_clear() {
		menu.dispose();
		if (menu_is_bar) {
			menu = new Menu(owner_win, SWT.BAR);
			owner_win.setMenuBar(menu);
		}
		else {
			menu = new Menu(owner_box);			
			owner_box.setMenu(menu);
		}
	}
	@Override public Gfui_mnu_itm Itms_add_btn_cmd(String txt, ImageAdp img, GfoInvkAble invk, String cmd) {
		Swt_popup_itm itm = new Swt_popup_itm(menu);
		itm.Text_(txt);
		if (img != null) itm.Img_(img);
		itm.Invk_set_cmd(invk, cmd);
		return itm;
	}
	@Override public Gfui_mnu_itm Itms_add_btn_msg(String txt, ImageAdp img, GfoInvkAble invk, GfoInvkRootWkr root_wkr, GfoMsg invk_msg) {
		Swt_popup_itm itm = new Swt_popup_itm(menu);
		itm.Text_(txt);
		if (img != null) itm.Img_(img);
		itm.Invk_set_msg(root_wkr, invk, invk_msg);
		return itm;
	}
	@Override public Gfui_mnu_itm Itms_add_chk_msg(String txt, ImageAdp img, GfoInvkAble invk, GfoInvkRootWkr root_wkr, GfoMsg msg_n, GfoMsg msg_y) {
		Swt_popup_itm itm = new Swt_popup_itm(menu, SWT.CHECK);
		itm.Text_(txt);
		if (img != null) itm.Img_(img);
		itm.Invk_set_chk(root_wkr, invk, msg_n, msg_y);
		return itm;
	}
	@Override public Gfui_mnu_itm Itms_add_rdo_msg(String txt, ImageAdp img, GfoInvkAble invk, GfoInvkRootWkr root_wkr, GfoMsg msg) {
		Swt_popup_itm itm = new Swt_popup_itm(menu, SWT.RADIO);
		itm.Text_(txt);
		if (img != null) itm.Img_(img);
		itm.Invk_set_msg(root_wkr, invk, msg);
		return itm;
	}
	@Override public Gfui_mnu_itm Itms_add_separator() {
		new MenuItem(menu, SWT.SEPARATOR);
		return null;
	}
	@Override public Gfui_mnu_grp Itms_add_grp(String txt, ImageAdp img) {
		Swt_popup_itm itm = new Swt_popup_itm(menu, SWT.CASCADE);
		if (img != null) itm.Img_(img);
		itm.Text_(txt);
		
		Swt_popup_grp grp = new_grp(root_key, owner_win);
		grp.menu_item = itm.Under_menu_item();
		itm.Text_(txt);
		
		itm.Under_menu_item().setMenu(grp.Under_menu());
		return grp;
	}
	public static Swt_popup_grp new_popup(String key, GfuiElem owner_elem) 	{
		Swt_popup_grp rv = new Swt_popup_grp(key);
		Shell owner_win = cast_to_shell(owner_elem.OwnerWin());
		Control owner_box = ((Swt_control)owner_elem.UnderElem()).Under_control();
		rv.owner_win = owner_win; rv.owner_box = owner_box;
		rv.menu = new Menu(owner_box);
		owner_box.setMenu(rv.menu);
		return rv;
	} 
	
	private static Swt_popup_grp new_grp(String key, Decorations owner_win) {
		Swt_popup_grp rv = new Swt_popup_grp(key);
		rv.owner_win = owner_win;
		rv.menu = new Menu(owner_win, SWT.DROP_DOWN);
		return rv;
	}
	public static Swt_popup_grp new_bar(String key, GfuiWin win) {
		Swt_popup_grp rv = new Swt_popup_grp(key);
		Shell owner_win = cast_to_shell(win);
		rv.owner_win = owner_win;
		rv.menu_is_bar = true;
		rv.menu = new Menu(owner_win, SWT.BAR);
		owner_win.setMenuBar(rv.menu);
		return rv;
	}
	private static Shell cast_to_shell(GfuiWin win) {
		return ((Swt_win)win.UnderElem()).UnderShell();
	}
}
class Swt_lnr__menu_detect implements MenuDetectListener {
	private final Swt_kit kit; private final GfuiElem elem;
	public Swt_lnr__menu_detect(Swt_kit kit, GfuiElem elem) {this.kit = kit; this.elem = elem;}
	@Override public void menuDetected(MenuDetectEvent arg0) {
		try {GfoEvMgr_.Pub(elem, GfuiElemKeys.Evt_menu_detected);}
		catch (Exception e) {
			kit.Ask_ok("", "", "error during right-click; err=~{0}", Err_.Message_gplx_full(e));
		}
	}	
}
class Swt_popup_itm implements Gfui_mnu_itm {
	private Menu menu;
	public Swt_popup_itm(Menu menu) {
		this.menu = menu; itm = new MenuItem(menu, SWT.NONE);
		this.tid = Gfui_mnu_itm_.Tid_btn; 
	}
	public Swt_popup_itm(Menu menu, int swt_type) {
		this.menu = menu; itm = new MenuItem(menu, swt_type);
		switch (swt_type) {
			case SWT.CASCADE: 	this.tid = Gfui_mnu_itm_.Tid_grp; break;
			case SWT.CHECK:		this.tid = Gfui_mnu_itm_.Tid_chk; break;
			case SWT.RADIO: 	this.tid = Gfui_mnu_itm_.Tid_rdo; break;
			default:			throw Err_.new_unhandled(swt_type);
		}
	}
	@Override public int Tid() {return tid;} private int tid;
	@Override public String Uid() {return uid;} private String uid = Gfui_mnu_itm_.Gen_uid(); 
	@Override public boolean Enabled() {return itm.getEnabled();} @Override public void Enabled_(boolean v) {itm.setEnabled(v);}
	@Override public String Text() {return itm.getText();} @Override public void Text_(String v) {itm.setText(v);}
	@Override public ImageAdp Img() {return img;} @Override public void Img_(ImageAdp v) {
		img = v; 
		if (v != ImageAdp_.Null)
			itm.setImage((Image)v.Under());
	}	private ImageAdp img;
	@Override public boolean Selected() {return itm.getSelection();}
	@Override public void Selected_(boolean v) {
		selected_changing = true;
		itm.setSelection(v);
		selected_changing = false;
	}
	public boolean Selected_changing() {return selected_changing;} private boolean selected_changing;
	@Override public Object Under() {return menu;}
	public MenuItem Under_menu_item() {return itm;} private MenuItem itm; 	
	public void Invk_set_cmd(GfoInvkAble invk, String cmd) {
		itm.addListener(SWT.Selection, new Swt_lnr__menu_btn_cmd(invk, cmd));
	}
	public void Invk_set_msg(GfoInvkRootWkr root_wkr, GfoInvkAble invk, GfoMsg msg) {
		itm.addListener(SWT.Selection, new Swt_lnr__menu_btn_msg(root_wkr, invk, msg));
	}
	public void Invk_set_chk(GfoInvkRootWkr root_wkr, GfoInvkAble invk, GfoMsg msg_n, GfoMsg msg_y) {
		itm.addListener(SWT.Selection, new Swt_lnr__menu_chk_msg(this, root_wkr, invk, msg_n, msg_y));
	}
}
class Swt_lnr__menu_btn_cmd implements Listener {
	public Swt_lnr__menu_btn_cmd(GfoInvkAble invk, String cmd) {this.invk = invk; this.cmd = cmd;} GfoInvkAble invk; String cmd;
	public void handleEvent(Event ev) {
		try {GfoInvkAble_.InvkCmd(invk, cmd);}
		catch (Exception e) {Swt_kit._.Ask_ok("", "", "error while invoking command: cmd=~{0} err=~{1}", cmd, Err_.Message_gplx_full(e));}
	}	
}
class Swt_lnr__menu_btn_msg implements Listener {
	private GfoInvkRootWkr root_wkr; private GfoInvkAble invk; private GfoMsg msg;
	public Swt_lnr__menu_btn_msg(GfoInvkRootWkr root_wkr, GfoInvkAble invk, GfoMsg msg) {this.root_wkr = root_wkr; this.invk = invk; this.msg = msg;}
	public void handleEvent(Event ev) {
		try {
			msg.Args_reset();
			root_wkr.Run_str_for(invk, msg);
		}
		catch (Exception e) {Swt_kit._.Ask_ok("", "", "error while invoking command: cmd=~{0} err=~{1}", msg.Key(), Err_.Message_gplx_full(e));}
	}	
}
class Swt_lnr__menu_chk_msg implements Listener {
	private Swt_popup_itm mnu_itm; private GfoInvkRootWkr root_wkr; private GfoInvkAble invk; private GfoMsg msg_n, msg_y;
	public Swt_lnr__menu_chk_msg(Swt_popup_itm mnu_itm, GfoInvkRootWkr root_wkr, GfoInvkAble invk, GfoMsg msg_n, GfoMsg msg_y) {
		this.mnu_itm = mnu_itm;
		this.root_wkr = root_wkr; this.invk = invk; this.msg_n = msg_n; this.msg_y = msg_y;
	}
	public void handleEvent(Event ev) {
		if (mnu_itm.Selected_changing()) return;
		GfoMsg msg = mnu_itm.Under_menu_item().getSelection() ? msg_y : msg_n;
		try {
			msg.Args_reset();
			root_wkr.Run_str_for(invk, msg);
		}
		catch (Exception e) {Swt_kit._.Ask_ok("", "", "error while invoking command: cmd=~{0} err=~{1}", msg.Key(), Err_.Message_gplx_full(e));}
	}	
}
