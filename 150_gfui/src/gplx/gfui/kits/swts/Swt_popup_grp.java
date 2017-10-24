/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.gfui.kits.swts; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
import gplx.*;
import gplx.gfui.controls.elems.GfuiElem;
import gplx.gfui.controls.windows.GfuiWin;
import gplx.gfui.imgs.ImageAdp;
import gplx.gfui.imgs.ImageAdp_;
import gplx.gfui.kits.core.Gfui_mnu_grp;
import gplx.gfui.kits.core.Gfui_mnu_itm;
import gplx.gfui.kits.core.Gfui_mnu_itm_;
import gplx.gfui.kits.core.Swt_kit;

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
	@Override public Gfui_mnu_itm Itms_add_btn_cmd(String txt, ImageAdp img, Gfo_invk invk, String cmd) {
		Swt_popup_itm itm = new Swt_popup_itm(menu);
		itm.Text_(txt);
		if (img != null) itm.Img_(img);
		itm.Invk_set_cmd(invk, cmd);
		return itm;
	}
	@Override public Gfui_mnu_itm Itms_add_btn_msg(String txt, ImageAdp img, Gfo_invk invk, Gfo_invk_root_wkr root_wkr, GfoMsg invk_msg) {
		Swt_popup_itm itm = new Swt_popup_itm(menu);
		itm.Text_(txt);
		if (img != null) itm.Img_(img);
		itm.Invk_set_msg(root_wkr, invk, invk_msg);
		return itm;
	}
	@Override public Gfui_mnu_itm Itms_add_chk_msg(String txt, ImageAdp img, Gfo_invk invk, Gfo_invk_root_wkr root_wkr, GfoMsg msg_n, GfoMsg msg_y) {
		Swt_popup_itm itm = new Swt_popup_itm(menu, SWT.CHECK);
		itm.Text_(txt);
		if (img != null) itm.Img_(img);
		itm.Invk_set_chk(root_wkr, invk, msg_n, msg_y);
		return itm;
	}
	@Override public Gfui_mnu_itm Itms_add_rdo_msg(String txt, ImageAdp img, Gfo_invk invk, Gfo_invk_root_wkr root_wkr, GfoMsg msg) {
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
	public void Invk_set_cmd(Gfo_invk invk, String cmd) {
		itm.addListener(SWT.Selection, new Swt_lnr__menu_btn_cmd(invk, cmd));
	}
	public void Invk_set_msg(Gfo_invk_root_wkr root_wkr, Gfo_invk invk, GfoMsg msg) {
		itm.addListener(SWT.Selection, new Swt_lnr__menu_btn_msg(root_wkr, invk, msg));
	}
	public void Invk_set_chk(Gfo_invk_root_wkr root_wkr, Gfo_invk invk, GfoMsg msg_n, GfoMsg msg_y) {
		itm.addListener(SWT.Selection, new Swt_lnr__menu_chk_msg(this, root_wkr, invk, msg_n, msg_y));
	}
}
class Swt_lnr__menu_btn_cmd implements Listener {
	public Swt_lnr__menu_btn_cmd(Gfo_invk invk, String cmd) {this.invk = invk; this.cmd = cmd;} Gfo_invk invk; String cmd;
	public void handleEvent(Event ev) {
		try {Gfo_invk_.Invk_by_key(invk, cmd);}
		catch (Exception e) {Swt_kit.Instance.Ask_ok("", "", "error while invoking command: cmd=~{0} err=~{1}", cmd, Err_.Message_gplx_full(e));}
	}	
}
class Swt_lnr__menu_btn_msg implements Listener {
	private Gfo_invk_root_wkr root_wkr; private Gfo_invk invk; private GfoMsg msg;
	public Swt_lnr__menu_btn_msg(Gfo_invk_root_wkr root_wkr, Gfo_invk invk, GfoMsg msg) {this.root_wkr = root_wkr; this.invk = invk; this.msg = msg;}
	public void handleEvent(Event ev) {
		try {
			msg.Args_reset();
			root_wkr.Run_str_for(invk, msg);
		}
		catch (Exception e) {Swt_kit.Instance.Ask_ok("", "", "error while invoking command: cmd=~{0} err=~{1}", msg.Key(), Err_.Message_gplx_full(e));}
	}	
}
class Swt_lnr__menu_chk_msg implements Listener {
	private Swt_popup_itm mnu_itm; private Gfo_invk_root_wkr root_wkr; private Gfo_invk invk; private GfoMsg msg_n, msg_y;
	public Swt_lnr__menu_chk_msg(Swt_popup_itm mnu_itm, Gfo_invk_root_wkr root_wkr, Gfo_invk invk, GfoMsg msg_n, GfoMsg msg_y) {
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
		catch (Exception e) {Swt_kit.Instance.Ask_ok("", "", "error while invoking command: cmd=~{0} err=~{1}", msg.Key(), Err_.Message_gplx_full(e));}
	}	
}
