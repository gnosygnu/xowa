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
package gplx.gfui;
import gplx.core.envs.Env_;
import gplx.core.primitives.*;
import gplx.core.threads.Thread_adp_;

import java.security.acl.Owner;
import gplx.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import java.security.acl.Owner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
class Swt_html implements Gxw_html, Swt_control, FocusListener {
	private Swt_html_lnr_location lnr_location; private Swt_html_lnr_status lnr_status;
	public Swt_html(Swt_kit kit, Swt_control owner_control, Keyval_hash ctorArgs) {
		this.kit = kit;
		lnr_location = new Swt_html_lnr_location(this);
		lnr_status = new Swt_html_lnr_status(this);
		Object browser_tid_obj = ctorArgs.Get_val_or(Swt_kit.Cfg_Html_BrowserType, null);
		this.browser_tid = browser_tid_obj == null ? Browser_tid_none : Int_.cast(browser_tid_obj);
		browser = new Browser(owner_control.Under_composite(), browser_tid);
		core = new Swt_core_cmds_html(this, browser);
		browser.addKeyListener(new Swt_lnr_key(this));
		browser.addMouseListener(new Swt_html_lnr_mouse(this, browser, kit));
		browser.addLocationListener(lnr_location);
		browser.addProgressListener(new Swt_html_lnr_progress(this));
		browser.addStatusTextListener(lnr_status);
		browser.addFocusListener(this);
		browser.addTitleListener(new Swt_html_lnr_title(this));
		// browser.addOpenWindowListener(new Swt_open_window_listener(this));	// handle target='blank'
		// browser.addTraverseListener(new Swt_html_lnr_Traverse(this));
	}
	public Swt_kit Kit() {return kit;} private Swt_kit kit;
	@Override public Control Under_control() {return browser;} private Browser browser;
	@Override public Composite Under_composite() {return null;}
	@Override public Control Under_menu_control() {return browser;}
	public int Browser_tid() {return browser_tid;} private final int browser_tid;
	public String Load_by_url_path() {return load_by_url_path;} private String load_by_url_path;
	public void 		Html_doc_html_load_by_mem(String html) {
		this.html_doc_html_load_tid = Gxw_html_load_tid_.Tid_mem;
		this.load_by_url_path = null; 
		browser.setText(html);	// DBG: Io_mgr.I.SaveFilStr(Io_url_.new_fil_("C:\\temp.txt"), s)
	}
	public void Html_doc_html_load_by_url(Io_url path, String html) {
		this.html_doc_html_load_tid = Gxw_html_load_tid_.Tid_url;
		this.load_by_url_path = path.To_http_file_str(); 
		Io_mgr.Instance.SaveFilStr(path, html);
		browser.setUrl(path.Xto_api());
	}
	public byte 		Html_doc_html_load_tid() {return html_doc_html_load_tid;} private byte html_doc_html_load_tid;
	public void 		Html_doc_html_load_tid_(byte v) {html_doc_html_load_tid = v;}
	public void 		Html_js_enabled_(boolean v) 									{browser.setJavascriptEnabled(v);}
	public void 		Html_js_cbks_add(String func_name, GfoInvkAble invk) 			{new Swt_html_func(browser, func_name, invk);}
	public String 		Html_js_eval_script(String script) 								{return Eval_script_as_str(script);}
	public boolean 	Html_js_eval_proc_as_bool(String proc, Object... args) {return Bool_.cast(Html_js_eval_proc_as_obj(proc, args));}
	public String	Html_js_eval_proc_as_str(String proc, Object... args) {return Object_.Xto_str_strict_or_null(Html_js_eval_proc_as_obj(proc, args));}
	public String Html_js_send_json(String name, String data) {
		String script = String_.Format("return {0}('{1}');", name, String_.Replace(data, "\n", "") );
		return (String)Eval_script(script);
	}
	private Object Html_js_eval_proc_as_obj(String proc, Object... args) {
		Bry_bfr bfr = Bry_bfr.new_();
		bfr.Add_str_a7("return ").Add_str_u8(proc).Add_byte(Byte_ascii.Paren_bgn);
		int args_len = args.length;
		for (int i = 0; i < args_len; ++i) {
			Object arg = args[i];
			if (i != 0) bfr.Add_byte(Byte_ascii.Comma);
			boolean quote_val = true;
			if 		(	Type_adp_.Eq_typeSafe(arg, Bool_.Cls_ref_type)
					||	Type_adp_.Eq_typeSafe(arg, Int_.Cls_ref_type)
					||	Type_adp_.Eq_typeSafe(arg, Long_.Cls_ref_type)
				) {
				quote_val = false;
			}
			if (quote_val) bfr.Add_byte(Byte_ascii.Apos);
			if (quote_val) 
				bfr.Add_str_u8(Escape_quote(Object_.Xto_str_strict_or_null_mark(arg)));
			else
				bfr.Add_obj_strict(arg);
			if (quote_val) bfr.Add_byte(Byte_ascii.Apos);
		}
		bfr.Add_byte(Byte_ascii.Paren_end).Add_byte(Byte_ascii.Semic);
		return Eval_script(bfr.To_str_and_clear());
	}
	private static String Escape_quote(String v) {
		String rv = v;
		rv = String_.Replace(rv, "'", "\\'");
		rv = String_.Replace(rv, "\"", "\\\"");
		rv = String_.Replace(rv, "\n", "\\n");
		return rv;
	}
	public void Html_invk_src_(GfoEvObj invk) {lnr_location.Host_set(invk); lnr_status.Host_set(invk);}
	public void Html_dispose() {
		browser.dispose();
		delete_owner.SubElems().DelOrFail(delete_cur);	// NOTE: must delete cur from owner, else new tab will fail after closing one; DATE:2014-07-09
		Env_.GarbageCollect();
	}
	private GfuiElem delete_owner, delete_cur;
	public void Delete_elems_(GfuiElem delete_owner, GfuiElem delete_cur) {this.delete_owner = delete_owner; this.delete_cur = delete_cur;}	// HACK: set owner / cur so delete can work;
	@Override public GxwCore_base Core() {return core;} private GxwCore_base core;
	@Override public GxwCbkHost Host() {return host;} @Override public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host;
	@Override public String TextVal() {return browser.getText();}
	@Override public void TextVal_set(String v) {browser.setText(v);}
	@Override public void EnableDoubleBuffering() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return GfoInvkAble_.Rv_unhandled;}
	private String Eval_script_as_str(String script) 	{return (String)Eval_script(script);}
	public Object Eval_script(String script) {
		eval_rslt.Clear();
		try {
			eval_rslt.Result_set(browser.evaluate(script));
			return eval_rslt.Result();
		}
		catch (Exception e) {eval_rslt.Error_set(e.getMessage()); 				return eval_rslt.Error();}
	}	private Swt_html_eval_rslt eval_rslt = new Swt_html_eval_rslt();
	@Override public void focusGained(FocusEvent arg0) {}
	@Override public void focusLost(FocusEvent arg0) {}
	public static final int
	  Browser_tid_none 		= SWT.NONE
	, Browser_tid_mozilla 	= SWT.MOZILLA
	, Browser_tid_webkit	= SWT.WEBKIT
	;	
}
class Swt_core_cmds_html extends Swt_core_cmds {
	public Swt_core_cmds_html(Swt_html html_box, Control control) {super(control);}
	@Override public void Focus() {
		if (Focus_able())
			control.forceFocus();
	}
	@Override public void Select_exec() {
		this.Focus();
	}
}
class Swt_html_lnr_traverse implements TraverseListener {
	public Swt_html_lnr_traverse(Swt_html html_box) {}
	@Override public void keyTraversed(TraverseEvent arg0) {}
}
class Swt_html_lnr_title implements TitleListener {
	private Swt_html html_box;
	public Swt_html_lnr_title(Swt_html html_box) {this.html_box = html_box;}
	@Override public void changed(TitleEvent ev) {
		try {UsrDlg_.Instance.Note(ev.title);}		
		catch (Exception e) {html_box.Kit().Ask_ok("xowa.swt.html_box", "title.fail", Err_.Message_gplx_full(e));}	// NOTE: must catch error or will cause app to lock; currently called inside displaySync 
	}
}
class Swt_html_func extends BrowserFunction {    
	private GfoInvkAble invk;
    public Swt_html_func(Browser browser, String name, GfoInvkAble invk) {
        super (browser, name);
        this.invk = invk;
    }
    public Object function (Object[] args) {
    	try {
    		return gplx.gfui.Gfui_html.Js_args_exec(invk, args);
    	}
    	catch (Exception e) {
    		return Err_.Message_gplx_full(e);
    	}
    }
}
class Swt_html_lnr_status implements StatusTextListener {
	public Swt_html_lnr_status(Swt_html html_box) {this.html_box = html_box;} private Swt_html html_box;
	public void Host_set(GfoEvObj host) {this.host = host;} GfoEvObj host;
	@Override public void changed(StatusTextEvent ev) {
		if (html_box.Kit().Kit_mode__term()) return;	// shutting down raises status changed events; ignore, else SWT exception thrown; DATE:2014-05-29 
		String ev_text = ev.text;
		String load_by_url_path = html_box.Load_by_url_path();
		if (load_by_url_path != null) ev_text = String_.Replace(ev_text, load_by_url_path, "");	// remove "C:/xowa/tab_1.html"
//		if (String_.Has(ev_text, "Loading [MathJax]")) return;	// suppress MathJax messages; // NOTE: disabled for 2.1 (which no longer outputs messages to status); DATE:2013-05-03
		try {if (host != null) GfoEvMgr_.PubObj(host, Gfui_html.Evt_link_hover, "v", ev_text);}
		catch (Exception e) {html_box.Kit().Ask_ok("xowa.gui.html_box", "status.fail", Err_.Message_gplx_full(e));}	// NOTE: must catch error or will cause app to lock; currently called inside displaySync 
	}
}
class Swt_html_lnr_progress implements ProgressListener {
	public Swt_html_lnr_progress(Swt_html html_box) {}
	@Override public void changed(ProgressEvent arg0) {}
	@Override public void completed(ProgressEvent arg0) {
//		UsrDlg_._.Note("done");
	}
}
class Swt_html_lnr_location implements LocationListener {
	public Swt_html_lnr_location(Swt_html html_box) {this.html_box = html_box;} private Swt_html html_box;
	public void Host_set(GfoEvObj host) {this.host = host;} private GfoEvObj host;
	@Override public void changed(LocationEvent arg) 	{Pub_evt(arg, Gfui_html.Evt_location_changed);}
	@Override public void changing(LocationEvent arg) 	{Pub_evt(arg, Gfui_html.Evt_location_changing);}
	private void Pub_evt(LocationEvent arg, String evt) {		
		String location = arg.location;
		if (String_.Eq(location, "about:blank")) return;	// location changing event fires once when page is loaded; ignore
		if (	html_box.Browser_tid() == Swt_html.Browser_tid_webkit	// webkit prefixes "about:blank" to anchors; causes TOC to fail when clicking on links; EX:about:blank#TOC1; DATE:2015-06-09
			&&	String_.Has_at_bgn(location, "about:blank")) {
			location = String_.Mid(location, 11);	// 11 = "about:blank".length 
		}
		if (	html_box.Html_doc_html_load_tid() == Gxw_html_load_tid_.Tid_url	// navigating to file://page.html will fire location event; ignore if url mode
			&& 	String_.Has_at_bgn(location, "file:")
			&& 	String_.Has_at_end(location, ".html")
			)
			return;
		try {
			GfoEvMgr_.PubObj(host, evt, "v", location);
			arg.doit = false; // cancel navigation event, else there will be an error when trying to go to invalid location
		}
		catch (Exception e) {html_box.Kit().Ask_ok("xowa.gui.html_box", evt, Err_.Message_gplx_full(e));}	// NOTE: must catch error or will cause app to lock; currently called inside displaySync 		
	}
}
class Swt_html_lnr_mouse implements MouseListener {
	private GxwElem elem; private Browser browser; private Swt_kit kit;
	public Swt_html_lnr_mouse(GxwElem elem, Browser browser, Swt_kit kit) {this.elem = elem; this.browser = browser; this.kit = kit;}
	@Override public void mouseDown(MouseEvent ev) {
		if (Is_at_scrollbar_area()) return;
		elem.Host().MouseDownCbk(XtoMouseData(ev));
	}
	@Override public void mouseUp(MouseEvent ev) {
		if (Is_at_scrollbar_area()) return;
		elem.Host().MouseUpCbk(XtoMouseData(ev));
	}
	private boolean Is_at_scrollbar_area() {
		// WORKAROUND.SWT: SEE:NOTE_1:browser scrollbar and click  
		Point browser_size = browser.getSize();
		Point click_pos = kit.Swt_display().getCursorLocation();
		return click_pos.x >= browser_size.x - 12;
	}
	@Override public void mouseDoubleClick(MouseEvent ev) {}
	IptEvtDataMouse XtoMouseData(MouseEvent ev) {
		IptMouseBtn btn = null;
		switch (ev.button) {
			case 1: btn = IptMouseBtn_.Left; break;
			case 2: btn = IptMouseBtn_.Middle; break;
			case 3: btn = IptMouseBtn_.Right; break;
			case 4: btn = IptMouseBtn_.X1; break;
			case 5: btn = IptMouseBtn_.X2; break;
		}
		return IptEvtDataMouse.new_(btn, IptMouseWheel_.None, ev.x, ev.y);		
	}
}
//class Swt_open_window_listener implements OpenWindowListener {
//	private final Swt_html html_box;
//	public Swt_open_window_listener(Swt_html html_box) {this.html_box = html_box;}
//	@Override public void open(WindowEvent arg0) {
//		Tfds.Write();
//	}	
//}
/*
NOTE_1:browser scrollbar and click
a click in the scrollbar area will raise a mouse-down/mouse-up event in content-editable mode
. a click should be consumed by the scrollbar and not have any effect elsewhere on the window
. instead, a click event is raised, and counted twice
  1) for the scroll bar this will scroll the area.
  2) for the window. if keyboard-focus is set on a link, then it will activate the link.

swt does not expose any scrollbar information (visible, width), b/c the scrollbar is controlled by the underlying browser
so, assume:
. scrollbar is always present
. scrollbar has arbitrary width (currently 12)
. and discard if click is in this scrollbar area

two issues still occur with the workaround
1) even if the scrollbar is not present, any click on the right-hand edge of the screen will be ignored
2) click -> hold -> move mouse over to left -> release; the mouse up should be absorbed, but it is not due to position of release   
*/
