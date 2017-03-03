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
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import gplx.gfui.GfuiAlign;
import gplx.gfui.GfuiAlign_;
import gplx.gfui.PointAdp;
import gplx.gfui.PointAdp_;
import gplx.gfui.RectAdp;
import gplx.gfui.SizeAdp;
import gplx.gfui.controls.elems.GfuiElem;
import gplx.gfui.draws.*;
import gplx.gfui.ipts.*; import gplx.gfui.controls.windows.*;
import gplx.gfui.layouts.swts.*;
public class GxwTextMemo_lang extends JScrollPane implements GxwTextMemo {
	public JTextArea Inner() {return txt_box;} GxwTextBox_lang txt_box;
	public GxwCore_base Core() {return core;} GxwCore_base core; 
	public GxwCbkHost Host() {return host;} public void Host_set(GxwCbkHost host) {this.host = host; txt_box.Host_set(host);} GxwCbkHost host;
	@Override public void setBackground(Color c) {
		if (txt_box == null) return; // WORKAROUND.OSX: OSX LookAndFeel calls setBackground during ctor of Mem_html; DATE:2015-05-11
		if 		(c.getRGB() == Color.BLACK.getRGB())  	txt_box.setCaretColor(Color.WHITE);			
		else if (c.getRGB() == Color.WHITE.getRGB()) 	txt_box.setCaretColor(Color.BLACK);		
		super.setBackground(c);
	}
	public void ScrollTillCaretIsVisible() {throw Err_.new_unimplemented();}
	public void Margins_set(int left, int top, int right, int bot) {
		if (left == 0 && right == 0) {
			txt_box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			txt_box.setMargin(new Insets(0, 0, 0,0));			
		}
		else {			
//			txt_box.setBorder(BasicBorders.getTextFieldBorder());
//			txt_box.setMargin(new Insets(0, l, 0, r));						
			txt_box.setFont(new Font("Courier New", FontStyleAdp_.Plain.Val(), 12));
			txt_box.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(top, left, bot, right)));
		}
	}
	public void ctor_MsTextBoxMultiline_() {
		txt_box = new GxwTextBox_lang();
		txt_box.ctor_MsTextBox_();
		core = new GxwCore_host(GxwCore_lang.new_(this), txt_box.ctrlMgr);
	    this.setViewportView(txt_box);
	    txt_box.setLineWrap(true);
	    txt_box.setWrapStyleWord(true);	// else text will wrap in middle of words
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		this.setBorder(null);
		txt_box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		txt_box.setCaretColor(Color.BLACK);
		txt_box.getCaret().setBlinkRate(0);
		txt_box.setMargin(new Insets(0, 200, 200,0));
		OverrideKeyBindings();
		InitUndoMgr();
		txt_box.setCaret(new javax.swing.text.DefaultCaret() {public void setSelectionVisible(boolean vis) {super.setSelectionVisible(true);}});// else highlighted selection will not be visible when text box loses focus 		
		//		this.setLayout(null);
		
//		Object fontDefinition = new UIDefaults.ProxyLazyValue("javax.swing.plaf.FontUIResource", null, new Object[] { "dialog", new Integer(Font.PLAIN), new Integer(12) });
//		java.util.Enumeration keys = UIManager.getDefaults().keys();
//		while (keys.hasMoreElements()) {
//		    Object key = keys.nextElement();
//		    Object value = UIManager.get(key);
//		    if (value instanceof javax.swing.plaf.FontUIResource) {
//		        UIManager.put(key, fontDefinition);
//		    }
//		}
	}	@gplx.Internal protected GxwTextMemo_lang() {}
	void InitUndoMgr() {
		final UndoManager undo = new UndoManager();
		Document doc = txt_box.getDocument();

		// Listen for undo and redo events
		doc.addUndoableEditListener(new UndoableEditListener() {
		    public void undoableEditHappened(UndoableEditEvent evt) {
		        undo.addEdit(evt.getEdit());
		    }
		});

		// Create an undo action and add it to the text component
		txt_box.getActionMap().put("Undo",
		    new AbstractAction("Undo") {
		        public void actionPerformed(ActionEvent evt) {
		            try {
		                if (undo.canUndo()) {
		                    undo.undo();
		                }
		            } catch (CannotUndoException e) {
		            }
		        }
		   });

		// Bind the undo action to ctl-Z
		txt_box.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");

		// Create a redo action and add it to the text component
		txt_box.getActionMap().put("Redo",
		    new AbstractAction("Redo") {
		        public void actionPerformed(ActionEvent evt) {
		            try {
		                if (undo.canRedo()) {
		                    undo.redo();
		                }
		            } catch (CannotRedoException e) {
		            }
		        }
		    });

		// Bind the redo action to ctl-Y
		txt_box.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");		
	}
	void OverrideKeyBindings() {
		Set<AWTKeyStroke> forTraSet = new HashSet<AWTKeyStroke> ();
		txt_box.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forTraSet);
		txt_box.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, forTraSet);

		GxwTextBox_overrideKeyCmd.noop_((GfuiElem)host, txt_box, "control H"); // else ctrl+h deletes current char		
//		GxwTextBox_overrideKeyCmd.new_(txt_box, "ENTER", Env_.NewLine); // else enter will always use \n on window; jtextBox allows separation of \r from \n
	}
	public void AlignH_(GfuiAlign val) {
		// can't work with jtextArea
//		if (val.Val() == GfuiAlign_.Mid.Val())
//			txt_box.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
	}
	public int LinesPerScreen() {return LinesPerScreen(this);}
	public int LinesTotal() {
		return 40;
//		return this.getStyledDocument(). .getLineCount();
	}
	public int ScreenCount() {return Int_.DivAndRoundUp(this.LinesTotal(), this.LinesPerScreen());}
	public int LineLength(int lineIndex) {return LineLength(this, lineIndex);}
	public int CharIndexOfFirst() {
		int firstLineIndex = LineIndexOfFirst();
		return CharIndexOf(firstLineIndex);
	}
	public int CharIndexOf(int lineIndex) {
		return lineIndex;
//		try 	{return this.getLineStartOffset(lineIndex);}
//		catch 	(BadLocationException e) {return -1;}
	}
	public int LineIndexOfFirst() {
//		this.getl
		
		return 0;	//todo
//		return TextBoxNpi.LineIndexFirst(ControlNpi.Hwnd(this));
}
	public int LineIndexOf(int charIndex) {
		return charIndex;
//		try		{return this.getLineOfOffset(charIndex);}
//		catch	(BadLocationException e) {return -1;}
	}
	public PointAdp PosOf(int charIndex) {
//		Document d = this.getDocument();
//		Element e = d.getDefaultRootElement();
//		e.
		return PointAdp_.Zero; //todo
//		PointAdp point = TextBoxNpi.PosOf(ControlNpi.Hwnd(this), charIndex);
//		return (point.Eq(TextBoxNpi.InvalidPoint))
//			? TextBoxNpi.InvalidPoint
//			: PointAdp_.XtoPointAdp(this.PointToScreen(point.XtoPoint()));
	}
	public void SelectionStart_toFirstChar() {
//		scrollPane.s
//		JScrollPane scrollingResult = new JScrollPane(this);
//		scrollingResult.
		//todo
//		this.SelectionStart = this.CharIndexOfFirst();
	}
	public void ScrollLineUp() {
//		ScrollWindow(TextBoxScrollTypes.LineUp);
	}
	public void ScrollLineDown() {
//		ScrollWindow(TextBoxScrollTypes.LineDown);
	}
	//		public void ExtendLineUp() {int l = this.SelectionStart; ScrollWindow(TextBoxScrollTypes.LineUp); int m = this.SelectionStart; this.SelectionStart = l; this.SelectionLength = m - l;}
	//		public void ExtendLineDown() {int l = this.SelectionStart; ScrollWindow(TextBoxScrollTypes.LineDown); int m = this.SelectionStart; this.SelectionStart = l; this.SelectionLength = m - l;}
	public void ScrollScreenUp() {
//		ScrollWindow(TextBoxScrollTypes.PageUp);
	}
	public void ScrollScreenDown() {
//		ScrollWindow(TextBoxScrollTypes.PageDown);
	}
	public void ScrollTillSelectionStartIsFirstLine() {
//		this.Visible = false;
//		this.ScrollToCaret();
//		int currentLine = TextBoxNpi.LineIndex(ControlNpi.Hwnd(this), this.SelectionStart);
//		int lineCount = this.LinesPerScreen();
//		int previousFirstLine = -1;
//		do {
//			int firstLine = TextBoxNpi.LineIndexFirst(ControlNpi.Hwnd(this));
//			if (firstLine == previousFirstLine) break;	// NOTE: avoid infinite loop
//	
//			if (firstLine - currentLine > lineCount)
//				this.ScrollScreenUp();
//			else if (currentLine - firstLine > lineCount)
//				this.ScrollScreenDown();
//			else if (currentLine < firstLine)
//				this.ScrollLineUp();
//			else if (currentLine > firstLine)
//				this.ScrollLineDown();
//			else
//				break;
//			previousFirstLine = firstLine;
//		}	while (true);
//		this.Visible = true;
//		GfuiEnv_.DoEvents(); // WORKAROUND (WCE): needed to repaint screen
//		this.Parent.Focus();
	}
//	void ScrollWindow(TextBoxScrollTypes scrollType) {TextBoxNpi.ScrollWindow(ControlNpi.Hwnd(this), scrollType);}
	public static int LinesPerScreen(GxwTextMemo_lang textBox) {
		return 50;
//		int lineIndexLine0 = textBox.LineIndexOfFirst();
//		int charIndexLine0 = textBox.CharIndexOf(lineIndexLine0);
//		PointAdp posLine0 = textBox.PosOf(charIndexLine0);
//		int charIndexLine1 = textBox.CharIndexOf(lineIndexLine0 + 1);
//		PointAdp posLine1 = textBox.PosOf(charIndexLine1);
//	
//		int availHeight = textBox.Height - (2 * textBox.BorderWidth());
//		int lineHeight = (posLine1.Eq(TextBoxNpi.InvalidPoint))	// TextBox is sized for one line, or textBox.Text = ""
//			? availHeight
//			: posLine1.Y() - posLine0.Y();
//		int rv = availHeight / lineHeight;
//		return rv == 0 ? 1 : rv;									// always return at least 1 line
	}
	public static int LineLength(GxwTextMemo_lang textBox, int lineIndex) {
		return -1;
//		int lineLength = TextBoxNpi.LineLength(ControlNpi.Hwnd(textBox), lineIndex);
//	
//		// WORKAROUND (TextBox): TextBoxNpi.LineLength ignores String_.NewLine; manually check for NewLine
//		int newLineLength = String_.NewLine.length;
//		String text = textBox.Text;
//		int charIndexFirst = textBox.CharIndexOf(lineIndex);
//		int charIndexLastAccordingToApi = charIndexFirst + lineLength;
//		if (charIndexLastAccordingToApi + newLineLength > text.length) return lineLength;			// last line of text; no ignored line possible
//		String charactersPastEndOfLine = text.Substring(charIndexLastAccordingToApi, newLineLength);
//		return charactersPastEndOfLine == String_.NewLine ? lineLength + newLineLength : lineLength;
	}
	public boolean Border_on() {return txt_box.Border_on();} public void Border_on_(boolean v) {txt_box.Border_on_(v);}
	public ColorAdp Border_color() {return border_color;} public void Border_color_(ColorAdp v) {border_color = v;} private ColorAdp border_color;	
	public void CreateControlIfNeeded() {txt_box.CreateControlIfNeeded();}
	public boolean OverrideTabKey() {return txt_box.OverrideTabKey();}
	public void OverrideTabKey_(boolean v) {
		txt_box.OverrideTabKey_(v);
		if (v) {
			Set<AWTKeyStroke> forTraSet = new HashSet<AWTKeyStroke> ();
			txt_box.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forTraSet);
			txt_box.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, forTraSet);
		}
		else {
			Set<AWTKeyStroke> forTraSet = new HashSet<AWTKeyStroke> ();
			txt_box.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forTraSet);
			txt_box.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, forTraSet);
			GxwTextBox_overrideKeyCmd.focus_((GfuiElem)host, txt_box, "TAB"); // else ctrl+h deletes current char		
			GxwTextBox_overrideKeyCmd.focusPrv_((GfuiElem)host, txt_box, "shift TAB"); // else ctrl+h deletes current char		
//			Set<AWTKeyStroke> forTraSet = new HashSet<AWTKeyStroke> ();
//			forTraSet.add(AWTKeyStroke.getAWTKeyStroke("TAB"));
//			Set<AWTKeyStroke> bwdTraSet = new HashSet<AWTKeyStroke> ();
//			bwdTraSet.add(AWTKeyStroke.getAWTKeyStroke("control TAB"));
//			txt_box.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forTraSet);
//			txt_box.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, bwdTraSet);
//			txt_box.OverrideTabKey_(false);
		}
	}	
	public int SelBgn() {return txt_box.SelBgn();} public void SelBgn_set(int v) {txt_box.SelBgn_set(v);}	
	public int SelLen() {return txt_box.SelLen();} public void SelLen_set(int v) {txt_box.SelLen_set(v);}	
	public void EnableDoubleBuffering() {txt_box.EnableDoubleBuffering();}
	
	public void SendKeyDown(IptKey key) {txt_box.SendKeyDown(key);}	
	public String TextVal() {return txt_box.TextVal();}
	public void TextVal_set(String v) {
		txt_box.TextVal_set(v);
		txt_box.setSelectionStart(0); txt_box.setSelectionEnd(0); // else selects whole text and scrolls to end of selection
	}	
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if (ctx.Match(k, GxwElem_lang.AlignH_cmd))  AlignH_(GfuiAlign_.cast(m.CastObj("v")));
		return txt_box.Invk(ctx, ikey, k, m);
	}
}
class GxwCore_host extends GxwCore_base {
	@Override public void Controls_add(GxwElem sub){outer.Controls_add(sub);}
	@Override public void Controls_del(GxwElem sub){outer.Controls_del(sub);}
	@Override public int Width(){return outer.Width();} @Override public void Width_set(int v){outer.Width_set(v); inner.Width_set(v);}
	@Override public int Height(){return outer.Height();} @Override public void Height_set(int v){outer.Height_set(v); inner.Height_set(v);}
	@Override public int X(){return outer.X();} @Override public void X_set(int v){outer.X_set(v);}	// NOTE: noop inner.x
	@Override public int Y(){return outer.Y();} @Override public void Y_set(int v){outer.Y_set(v);} // NOTE: noop inner.y
	@Override public SizeAdp Size(){return outer.Size();} @Override public void Size_set(SizeAdp v){outer.Size_set(v); inner.Size_set(v);}
	@Override public PointAdp Pos(){return outer.Pos();} @Override public void Pos_set(PointAdp v){outer.Pos_set(v);} // NOTE: noop inner.pos
	@Override public RectAdp Rect(){return outer.Rect();} @Override public void Rect_set(RectAdp v){outer.Rect_set(v); inner.Size_set(v.Size());} // NOTE: noop inner.pos
	@Override public boolean Visible(){return outer.Visible();} @Override public void Visible_set(boolean v){outer.Visible_set(v); inner.Visible_set(v);}
	@Override public ColorAdp BackColor(){return outer.BackColor();} @Override public void BackColor_set(ColorAdp v){outer.BackColor_set(v); inner.BackColor_set(v);}
	@Override public ColorAdp ForeColor(){return outer.ForeColor();} @Override public void ForeColor_set(ColorAdp v){outer.ForeColor_set(v); inner.ForeColor_set(v);}
	@Override public FontAdp TextFont(){return outer.TextFont();} @Override public void TextFont_set(FontAdp v){outer.TextFont_set(v); inner.TextFont_set(v);}
	@Override public String TipText() {return tipText;} @Override public void TipText_set(String v) {tipText = v;} String tipText;	
	@Override public Swt_layout_mgr Layout_mgr() {return null;}
	@Override public void Layout_mgr_(Swt_layout_mgr v) {}
	@Override public Swt_layout_data Layout_data() {return null;}
	@Override public void Layout_data_(Swt_layout_data v) {}

	public Object Reapply() {
		TextFont_set(outer.TextFont()); return this;} // HACK:

	@Override public int Focus_index(){return inner.Focus_index();} @Override public void Focus_index_set(int v){outer.Focus_index_set(v); inner.Focus_index_set(v);}
	@Override public boolean Focus_able(){return inner.Focus_able();} @Override public void Focus_able_(boolean v){outer.Focus_able_(v); inner.Focus_able_(v);}
//	@Override public void Focus_able_force_(boolean v){outer.Focus_able_force_(v); inner.Focus_able_force_(v);}
	@Override public boolean Focus_has(){return inner.Focus_has();}
	@Override public void Focus(){
		inner.Focus();
		}
	@Override public void Select_exec(){
		inner.Select_exec();
		}
	@Override public void Zorder_front(){outer.Zorder_front();} @Override public void Zorder_back(){outer.Zorder_back();}
	@Override public void Invalidate(){outer.Invalidate(); 
	inner.Invalidate();}
	@Override public void Dispose(){outer.Dispose();}
	GxwCore_base outer; GxwCore_base inner;
	public void Inner_set(GxwCore_base v) {inner = v;}
	public GxwCore_host(GxwCore_base outer, GxwCore_base inner) {this.outer = outer; this.inner = inner;}
}
