/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.gfui.kits.swts;

import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.evts.Gfo_evt_itm;
import gplx.frameworks.evts.Gfo_evt_mgr;
import gplx.frameworks.evts.Gfo_evt_mgr_;
import gplx.frameworks.evts.Gfo_evt_mgr_owner;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.types.basics.utls.IntUtl;
import gplx.libs.files.Io_mgr;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.types.commons.KeyValHash;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.libs.dlgs.UsrDlg_;
import gplx.core.envs.SystemUtl;
import gplx.gfui.controls.elems.GfuiElem;
import gplx.gfui.controls.gxws.GxwCbkHost;
import gplx.gfui.controls.gxws.GxwCore_base;
import gplx.gfui.controls.gxws.GxwElem;
import gplx.gfui.controls.gxws.Gxw_html;
import gplx.gfui.controls.gxws.Gxw_html_load_tid_;
import gplx.gfui.controls.standards.Gfui_html;
import gplx.gfui.ipts.IptEvtDataMouse;
import gplx.gfui.ipts.IptMouseBtn;
import gplx.gfui.ipts.IptMouseBtn_;
import gplx.gfui.ipts.IptMouseWheel_;
import gplx.gfui.kits.core.Swt_kit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class Swt_html implements Gxw_html, Swt_control, FocusListener, Gfo_evt_mgr_owner {
	private Swt_html_lnr_location lnr_location; private Swt_html_lnr_status lnr_status;
	public Swt_html(Swt_kit kit, Swt_control owner_control, KeyValHash ctorArgs) {
		this.kit = kit;
		lnr_location = new Swt_html_lnr_location(this);
		lnr_status = new Swt_html_lnr_status(this);
		Object browser_tid_obj = ctorArgs.GetByValOr(Swt_kit.Cfg_Html_BrowserType, null);
		this.browser_tid = browser_tid_obj == null ? Browser_tid_none : IntUtl.Cast(browser_tid_obj);
		browser = new Browser(owner_control.Under_composite(), browser_tid);
		core = new Swt_core_cmds_html(this, browser);
		browser.addKeyListener(new Swt_lnr_key(this));
		browser.addMouseListener(new Swt_html_lnr_mouse(this, browser, kit));
		browser.addLocationListener(lnr_location);
		browser.addProgressListener(new Swt_html_lnr_progress(this));
		browser.addStatusTextListener(lnr_status);
		browser.addFocusListener(this);
		browser.addTitleListener(new Swt_html_lnr_title(this));
		browser.addMouseWheelListener(new Swt_html_lnr_wheel(this));
		
		// browser.addOpenWindowListener(new Swt_open_window_listener(this));	// handle target='blank'
		// browser.addTraverseListener(new Swt_html_lnr_Traverse(this));
	}
	public Swt_kit Kit() {return kit;} private Swt_kit kit;
	public Gfo_evt_mgr Evt_mgr() {return ev_mgr;} private Gfo_evt_mgr ev_mgr; 	public void Evt_mgr_(Gfo_evt_mgr v) {ev_mgr = v;}
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
	public void 		Html_js_cbks_add(String func_name, Gfo_invk invk) 				{new Swt_html_func(browser, func_name, invk);}
	public String 		Html_js_eval_script(String script) 								{return Eval_script_as_str(script);}
	public Object		Html_js_eval_script_as_obj(String script) 						{return Eval_script(script);}
	public boolean 		Html_js_eval_proc_as_bool(String proc, Object... args) 			{return BoolUtl.Cast(Html_js_eval_proc_as_obj(proc, args));}
	public String	Html_js_eval_proc_as_str(String proc, Object... args) {return ObjectUtl.ToStrOrNull(Html_js_eval_proc_as_obj(proc, args));}
	public String Html_js_send_json(String name, String data) {
		String script = StringUtl.Format("return {0}('{1}');", name, StringUtl.Replace(data, "\n", "") );
		return (String)Eval_script(script);
	}
	private Object Html_js_eval_proc_as_obj(String proc, Object... args) {
		BryWtr bfr = BryWtr.New();
		bfr.AddStrA7("return ").AddStrU8(proc).AddByte(AsciiByte.ParenBgn);
		int args_len = args.length;
		for (int i = 0; i < args_len; ++i) {
			Object arg = args[i];
			if (i != 0) bfr.AddByte(AsciiByte.Comma);
			boolean quote_val = true;
			if 		(	ClassUtl.EqByObj(BoolUtl.ClsRefType, arg)
					||	ClassUtl.EqByObj(IntUtl.ClsRefType, arg)
					||	ClassUtl.EqByObj(LongUtl.ClsRefType, arg)
				) {
				quote_val = false;
			}
			if (quote_val) bfr.AddByte(AsciiByte.Apos);
			if (quote_val) 
				bfr.AddStrU8(Escape_quote(ObjectUtl.ToStrOrNullMark(arg)));
			else
				bfr.AddObjStrict(arg);
			if (quote_val) bfr.AddByte(AsciiByte.Apos);
		}
		bfr.AddByte(AsciiByte.ParenEnd).AddByte(AsciiByte.Semic);
		return Eval_script(bfr.ToStrAndClear());
	}
	public static String Escape_quote(String v) {
		String rv = v;
		rv = StringUtl.Replace(rv, "'", "\\'");
		rv = StringUtl.Replace(rv, "\"", "\\\"");
		rv = StringUtl.Replace(rv, "\n", "\\n");
		return rv;
	}
	public void Html_invk_src_(Gfo_evt_itm invk) {lnr_location.Host_set(invk); lnr_status.Host_set(invk);}
	public void Html_dispose() {
		browser.dispose();
		delete_owner.SubElems().DelOrFail(delete_cur);	// NOTE: must delete cur from owner, else new tab will fail after closing one; DATE:2014-07-09
		SystemUtl.Garbage_collect();
	}
	private GfuiElem delete_owner, delete_cur;
	public void Delete_elems_(GfuiElem delete_owner, GfuiElem delete_cur) {this.delete_owner = delete_owner; this.delete_cur = delete_cur;}	// HACK: set owner / cur so delete can work;
	@Override public GxwCore_base Core() {return core;} private GxwCore_base core;
	@Override public GxwCbkHost Host() {return host;} @Override public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host;
	@Override public String TextVal() {return browser.getText();}
	@Override public void TextVal_set(String v) {browser.setText(v);}
	@Override public void EnableDoubleBuffering() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return Gfo_invk_.Rv_unhandled;}
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
class Swt_core_cmds_html extends Swt_core__basic {
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
		catch (Exception e) {html_box.Kit().Ask_ok("xowa.swt.html_box", "title.fail", ErrUtl.ToStrFull(e));}	// NOTE: must catch error or will cause app to lock; currently called inside displaySync
	}
}
class Swt_html_func extends BrowserFunction {    
	private final Gfo_invk invk;
	private final Browser browser;
    public Swt_html_func(Browser browser, String name, Gfo_invk invk) {
        super (browser, name);
        this.browser = browser;
        this.invk = invk;
    }
    public Object function (Object[] args) {
    	try {
    		return gplx.gfui.controls.standards.Gfui_html.Js_args_exec(invk, args);
    	}
    	catch (Exception e) {
    		String rv = ErrUtl.ToStrFull(e);
    		browser.execute("alert('" + Swt_html.Escape_quote(rv) + "')");
    		return rv;
    	}
    }
}
class Swt_html_lnr_status implements StatusTextListener {
	public Swt_html_lnr_status(Swt_html html_box) {this.html_box = html_box;} private Swt_html html_box;
	public void Host_set(Gfo_evt_itm host) {this.host = host;} Gfo_evt_itm host;
	@Override public void changed(StatusTextEvent ev) {
		if (html_box.Kit().Kit_mode__term()) return;	// shutting down raises status changed events; ignore, else SWT exception thrown; DATE:2014-05-29 

		// 2020-09-22|ISSUE#:799|normalize URL due to SWT 4.16
		String ev_text = Swt_html_utl.NormalizeSwtUrl(ev.text);

		String load_by_url_path = html_box.Load_by_url_path();
		if (load_by_url_path != null) ev_text = StringUtl.Replace(ev_text, load_by_url_path, "");	// remove "C:/xowa/tab_1.html"
//		if (String_.Has(ev_text, "Loading [MathJax]")) return;	// suppress MathJax messages; // NOTE: disabled for 2.1 (which no longer outputs messages to status); DATE:2013-05-03
		try {if (host != null) Gfo_evt_mgr_.Pub_obj(host, Gfui_html.Evt_link_hover, "v", ev_text);}
		catch (Exception e) {html_box.Kit().Ask_ok("xowa.gui.html_box", "status.fail", ErrUtl.ToStrFull(e));}	// NOTE: must catch error or will cause app to lock; currently called inside displaySync
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
	public void Host_set(Gfo_evt_itm host) {this.host = host;} private Gfo_evt_itm host;
	@Override public void changed(LocationEvent arg) 	{Pub_evt(arg, Gfui_html.Evt_location_changed);}
	@Override public void changing(LocationEvent arg) 	{Pub_evt(arg, Gfui_html.Evt_location_changing);}
	private void Pub_evt(LocationEvent arg, String evt) {
		// 2020-09-22|ISSUE#:799|normalize URL due to SWT 4.16
		String location = Swt_html_utl.NormalizeSwtUrl(arg.location);

		// location_changing fires once when page is loaded -> ignore
		if (StringUtl.Eq(location, StringUtl.Empty)) {
			return;
		}

		// navigating to file://page.html will fire location event; ignore if url mode (loading pages from file)
		if (html_box.Html_doc_html_load_tid() == Gxw_html_load_tid_.Tid_url
			&& 	StringUtl.HasAtBgn(location, "file:")
			&& 	StringUtl.HasAtEnd(location, ".html")
			) {
			return;
		}

		if (StringUtl.HasAtBgn(location, "javascript:")) {
			html_box.Html_js_eval_script(location);
			arg.doit = false;
			return;
		}

		try {
			Gfo_evt_mgr_.Pub_obj(host, evt, "v", location);
			arg.doit = false; // cancel navigation event, else there will be an error when trying to go to invalid location
		}
		catch (Exception e) {html_box.Kit().Ask_ok("xowa.gui.html_box", evt, ErrUtl.ToStrFull(e));}	// NOTE: must catch error or will cause app to lock; currently called inside displaySync
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
class Swt_html_lnr_wheel implements MouseWheelListener {
	private final Swt_html html_box;
	public Swt_html_lnr_wheel(Swt_html html_box) {
		this.html_box = html_box;
	}
	@Override public void mouseScrolled(MouseEvent evt) {
		if (evt.stateMask == SWT.CTRL) {	// ctrl held;
			Gfo_evt_mgr_.Pub_obj(html_box, Gfui_html.Evt_zoom_changed, "clicks_is_positive", evt.count > 0);
		}
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
