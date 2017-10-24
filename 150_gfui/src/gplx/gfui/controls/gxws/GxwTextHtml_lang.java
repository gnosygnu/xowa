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
package gplx.gfui.controls.gxws; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.core.strings.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.StringWriter;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.ParagraphView;
import javax.swing.text.PlainDocument;
import javax.swing.text.Segment;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLEditorKit.HTMLFactory;
import javax.swing.text.html.InlineView;
import javax.swing.text.html.StyleSheet;
import gplx.gfui.draws.*;
import gplx.gfui.ipts.*; import gplx.gfui.gfxs.*;
public class GxwTextHtml_lang extends JScrollPane implements GxwTextHtml {
	@Override public GxwCore_base Core() {return core;} GxwCore_host core;
	public GxwCbkHost Host() {return host;} public void Host_set(GxwCbkHost host) {this.host = host; editor.Host_set(host);} GxwCbkHost host;
	@Override public int SelBgn() {return editor.SelBgn();} @Override public void SelBgn_set(int v) {editor.SelBgn_set(v);}
	@Override public int SelLen() {return editor.SelLen();} @Override public void SelLen_set(int v) {editor.SelLen_set(v);}
	@Override public String TextVal() {return editor.TextVal();} @Override public void TextVal_set(String v) {editor.TextVal_set(v);}
	@Override public boolean Border_on() {return false;} //boolean borderOn = true;
	@Override public void Border_on_(boolean v) {
//		borderOn = v;
//		Border border = v ? BorderFactory.createLineBorder(Color.BLACK) : null;
//		this.setBorder(border);			
	}
	public ColorAdp Border_color() {return border_color;} public void Border_color_(ColorAdp v) {border_color = v;} private ColorAdp border_color;
	@Override public boolean OverrideTabKey() {return false;}
	@Override public void OverrideTabKey_(boolean v) {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return null;}
	public void Html_enabled(boolean v) {
//		String newContentType = v ? "text/html" : "text/plain";
//		if (!String_.Eq(editor.getContentType(), newContentType)) {
//			editor = new GxwTextHtml_editor().ctor();
//			core.Inner_set(editor.core);
//		    this.setViewportView(editor);
//		}
		editor.Html_enabled(v);
	}
	public GxwTextHtml_editor Editor() {return editor;} GxwTextHtml_editor editor;
	public void ScrollTillCaretIsVisible() {throw Err_.new_unimplemented();}
	public GxwTextHtml_lang ctor() {
		editor = new GxwTextHtml_editor().ctor();
		core = new GxwCore_host(GxwCore_lang.new_(this), editor.core);
	    this.setViewportView(editor);
//		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
//		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setBorder(null);
		return this;
	}
	public Keyval[] Html_sel_atrs() {return editor.Html_sel_atrs();}
	public String Html_doc_html() {return editor.Html_doc_html();}
	public void Html_css_set(String s) {editor.Html_css_set(s);}
	@Override public void Margins_set(int left, int top, int right, int bot) {}
	@Override public void EnableDoubleBuffering() {}
	@Override public void CreateControlIfNeeded() {}
	@Override public int LinesPerScreen() {return 0;}
	@Override public int LinesTotal() {return 0;}
	@Override public int ScreenCount() {return 0;}
	@Override public int CharIndexOf(int lineIndex) {return 0;}
	@Override public int CharIndexOfFirst() {return 0;}
	@Override public int LineIndexOfFirst() {return 0;}
	@Override public int LineIndexOf(int charIndex) {return 0;}
	@Override public PointAdp PosOf(int charIndex) {return null;}
	@Override public void ScrollLineUp() {}
	@Override public void ScrollLineDown() {}
	@Override public void ScrollScreenUp() {}
	@Override public void ScrollScreenDown() {}
	@Override public void SelectionStart_toFirstChar() {}
	@Override public void ScrollTillSelectionStartIsFirstLine() {}
}

class GxwTextHtml_editor extends JEditorPane implements GxwTextHtml {
	public GxwTextHtml_editor ctor() {
		styledKit = new StyledEditorKit();
		htmlKit = new HTMLEditorKit();
		this.setEditorKit(htmlKit);
//		this.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE); this.setFont(new Font("Courier New", 6, Font.PLAIN)); // force jeditorpane to take font 
		core = GxwCore_lang.new_(this);
		this.setCaret(new javax.swing.text.DefaultCaret() {public void setSelectionVisible(boolean vis) {super.setSelectionVisible(true);}});// else highlighted selection will not be visible when text box loses focus 		
		return this;
	}
//	public HTMLEditorKit HtmlKit() {return htmlKit;} HTMLEditorKit htmlKit;
	@Override public GxwCore_base Core() {return core;} GxwCore_base core;
	public GxwCbkHost Host() {return host;} public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host;
	@Override public int SelBgn() {return this.getSelectionStart();} @Override public void SelBgn_set(int v) {this.setSelectionStart(v); this.setCaretPosition(v);}
	@Override public int SelLen() {return this.getSelectionEnd() - this.getSelectionStart();} @Override public void SelLen_set(int v) {this.setSelectionEnd(this.getSelectionStart() + v);}
	@Override public String TextVal() {return this.getText();}
	@Override public void TextVal_set(String v) {this.setText(v);}
	@Override public boolean Border_on() {return borderOn;} boolean borderOn = true;
	@Override public void Border_on_(boolean v) {
		borderOn = v;
		Border border = v ? BorderFactory.createLineBorder(Color.BLACK) : null;
		this.setBorder(border);		
	}
	public ColorAdp Border_color() {return border_color;} public void Border_color_(ColorAdp v) {border_color = v;} private ColorAdp border_color;
	@Override public boolean OverrideTabKey() {return false;}
	@Override public void OverrideTabKey_(boolean v) {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return null;}
	StyledEditorKit styledKit; HTMLEditorKit htmlKit;
	
	public void Html_enabled(boolean v) {
//		String contentType = v ? "text/html" : "text/rtf";
//		this.setEditorKit(v ? new StyledEditorKit() : new DefaultEditorKit());
		this.setEditorKit(v ? htmlKit : styledKit);
	}
	public void ScrollTillCaretIsVisible() {throw Err_.new_unimplemented();}
	public void Html_css_set(String s) {
		StyleSheet styleSheet = htmlKit.getStyleSheet();
		styleSheet.addRule(s);
	}
	public String Html_print() {
		String_bldr sb = String_bldr_.new_();
		sb.Add("selBgn=").Add(Int_.To_str(Html_sel_bgn())).Add_char_crlf();
		sb.Add("selEnd=").Add(Int_.To_str(Html_sel_end())).Add_char_crlf();
		sb.Add("selTxt=").Add(Html_sel_text()).Add_char_crlf();
		Keyval[] atrs = Html_sel_atrs();
		for (int i = 0; i < atrs.length; i++) {
			Keyval atr = atrs[i];
			sb.Add(atr.Key() + "=").Add(atr.Val_to_str_or_null()).Add_char_crlf();
		}
		return sb.To_str();
	}
	Element Html_sel_elm() {
		HTMLDocument doc = (HTMLDocument)this.getDocument();
		int pos = this.getCaretPosition();
		return doc.getCharacterElement(pos);		
	}
	public int Html_sel_bgn() {return Html_sel_elm().getStartOffset();}
	public int Html_sel_end() {return Html_sel_elm().getEndOffset();}
//	public String Html_doc_html() {
//		HTMLEditorKit kit = (HTMLEditorKit)this.getEditorKit();
//		HTMLDocument doc = (HTMLDocument)this.getDocument();
//		StringWriter sw = new StringWriter();		
//		try {kit.write(sw, doc, 0, doc.getLength());}
//		catch (Exception exc) {throw Err_.err_(exc, "Html_doc_html");}
//		return sw.toString();
//	}
	public String Html_doc_html() {
		Document doc = this.getDocument();
		try {return this.getDocument().getText(0, doc.getLength());}
		catch (Exception e) {throw Err_.new_exc(e, "ui", "Html_doc_html");}
	}
	public String Html_sel_text() {
		Element elm = Html_sel_elm();
		int sel_bgn = elm.getStartOffset();
		int sel_end = elm.getEndOffset();
		try {return this.getDocument().getText(sel_bgn, sel_end - sel_bgn);}
		catch (Exception e) {throw Err_.new_exc(e, "ui", "Html_sel_text");}
	}
	static void Html_sel_atrs(AttributeSet atrs, List_adp list, String ownerKey, String dlm) {
		if (atrs == null) return;
		Enumeration<?> keys = atrs.getAttributeNames();
		while (true) {
			if (!keys.hasMoreElements()) break;				
			Object atr_key = keys.nextElement(); if (atr_key == null) break;
			Object atr_val = atrs.getAttribute(atr_key);
			String atr_key_str = atr_key.toString();
			String itm_key = ownerKey == null ? atr_key_str : ownerKey + dlm + atr_key_str;
			if (atr_val instanceof javax.swing.text.AttributeSet)
				Html_sel_atrs((AttributeSet)atr_val, list, itm_key, dlm);
			else
				list.Add(Keyval_.new_(itm_key, atr_val));
		}
	}
	public Keyval[] Html_sel_atrs() {
		if (String_.Eq(this.getContentType(), "text/plain")) return Keyval_.Ary_empty;
		Element elm = Html_sel_elm(); if (elm == null) return Keyval_.Ary_empty;
		List_adp sel_atrs_list = List_adp_.New();
		Html_sel_atrs(elm.getAttributes(), sel_atrs_list, null, ".");
		return (Keyval[])sel_atrs_list.To_ary(Keyval.class);
	}

	@Override public void processKeyEvent(KeyEvent e) 					{
//			if (overrideTabKey && e.getKeyCode() == KeyEvent.VK_TAB) {
//				super.processKeyEvent(e); return;}
		if (GxwCbkHost_.ExecKeyEvent(host, e)) 
			super.processKeyEvent(e);
	}
	@Override public void processMouseEvent(MouseEvent e) 				{if (GxwCbkHost_.ExecMouseEvent(host, e))	super.processMouseEvent(e);}
	@Override public void processMouseWheelEvent(MouseWheelEvent e) 	{if (GxwCbkHost_.ExecMouseWheel(host, e))	super.processMouseWheelEvent(e);}
	@Override public void processMouseMotionEvent(MouseEvent e)			{if (host.MouseMoveCbk(IptEvtDataMouse.new_(IptMouseBtn_.None, IptMouseWheel_.None, e.getX(), e.getY()))) super.processMouseMotionEvent(e);}
	@Override public void paint(Graphics g) {
		if (host.PaintCbk(PaintArgs.new_(GfxAdpBase.new_((Graphics2D)g), RectAdp_.Zero)))	// ClipRect not used by any clients; implement when necessary
			super.paint(g);	// Reevaluate if necessary: super.paint might need to (a) always happen and (b) go before PaintCbk (had issues with drawing text on images) 
	}

	@Override public void Margins_set(int left, int top, int right, int bot) {}
	@Override public void EnableDoubleBuffering() {}
	@Override public void CreateControlIfNeeded() {}
	@Override public int LinesPerScreen() {return 0;}
	@Override public int LinesTotal() {return 0;}
	@Override public int ScreenCount() {return 0;}
	@Override public int CharIndexOf(int lineIndex) {return 0;}
	@Override public int CharIndexOfFirst() {return 0;}
	@Override public int LineIndexOfFirst() {return 0;}
	@Override public int LineIndexOf(int charIndex) {return 0;}
	@Override public PointAdp PosOf(int charIndex) {return null;}
	@Override public void ScrollLineUp() {}
	@Override public void ScrollLineDown() {}
	@Override public void ScrollScreenUp() {}
	@Override public void ScrollScreenDown() {}
	@Override public void SelectionStart_toFirstChar() {}
	@Override public void ScrollTillSelectionStartIsFirstLine() {}
}
