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
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
public class GxwComboBox_lang extends JComboBox implements GxwComboBox, GxwElem, ActionListener {
	public void DataSource_set(Object... ary) {
		for (Object o : ary)
			this.insertItemAt(o, this.getItemCount());
		/*
			Get current value
			Object obj = cb.getSelectedItem(); 
			
			Set a new valu
			cb.setSelectedItem("item2");
			obj = cb.getSelectedItem();  
 		 */
	}
	public Object SelectedItm() {return this.getEditor().getItem();} public void SelectedItm_set(Object v) {
		this.getEditor().setItem(v);
	}
	void ctor_() {
		ctrlMgr = GxwCore_lang.new_(this);
		this.enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK);
		GxwBoxListener lnr = new GxwBoxListener(this);
//		this.setFocusTraversalKeysEnabled(false);
//		this.addKeyListener(this);
		this.addComponentListener(lnr);
		this.addFocusListener(lnr);
		this.setEditable(true);
//		this.addActionListener(this);
		this.setFocusable(true);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		Component jc = this.getEditor().getEditorComponent();	// WORKAROUND:SWING:JComboBox does not reroute events from editor; will not handle canceling key
		jc.addKeyListener(new ComboBox_keylistener(this));
	}
	public GxwCore_base Core() {return ctrlMgr;} GxwCore_base ctrlMgr;
	public GxwCbkHost Host() {return host;} public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host = GxwCbkHost_.Null;
	// need to validate, else dropDownArrow will not get redrawn in correct pos
	@Override public void setBounds(Rectangle r) {super.setBounds(r); this.validate();}
	@Override public void setSize(int w, int h) {super.setSize(w, h); this.validate();}
	@Override public void setSize(Dimension d) {super.setSize(d); this.validate();}
	public String TextVal() {return Object_.XtoStr_OrEmpty(this.SelectedItm());} public void TextVal_set(String v) {this.SelectedItm_set(v);}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return this;
	}
	public void SendKeyDown(IptKey key)								{}
	public void SendMouseMove(int x, int y)							{}
	public void SendMouseDown(IptMouseBtn btn)						{}
	public void SendMouseWheel(IptMouseWheel direction)				{}
//	@Override public void keyPressed(KeyEvent arg0) {this.processKeyEvent(arg0);}
//	@Override public void keyReleased(KeyEvent arg0) {this.processKeyEvent(arg0);}
//	@Override public void keyTyped(KeyEvent arg0) {this.processKeyEvent(arg0);}
	@Override public void processKeyEvent(KeyEvent e) 					{
		if (GxwCbkHost_.ExecKeyEvent(host, e)) 
			super.processKeyEvent(e);
		}
//	@Override public void actionPerformed(ActionEvent e) {
//	}
	@Override public void processMouseEvent(MouseEvent e) 				{if (GxwCbkHost_.ExecMouseEvent(host, e))	super.processMouseEvent(e);}
	@Override public void processMouseWheelEvent(MouseWheelEvent e) 	{if (GxwCbkHost_.ExecMouseWheel(host, e))	super.processMouseWheelEvent(e);}
	@Override public void processMouseMotionEvent(MouseEvent e)			{if (host.MouseMoveCbk(IptEvtDataMouse.new_(IptMouseBtn_.None, IptMouseWheel_.None, e.getX(), e.getY()))) super.processMouseMotionEvent(e);}
	@Override public void paint(Graphics g) {
		if (host.PaintCbk(PaintArgs.new_(GfxAdpBase.new_((Graphics2D)g), RectAdp_.Zero)))	// ClipRect not used by any clients; implement when necessary	
			super.paint(g);	// Reevaluate if necessary: super.paint might need to (a) always happen and (b) go before PaintCbk (had issues with drawing text on images) 
	}
	public void EnableDoubleBuffering() {	// eliminates flickering during OnPaint
	}
	public void CreateControlIfNeeded() {
	}
	public static GxwComboBox_lang new_() {
		GxwComboBox_lang rv = new GxwComboBox_lang();
		rv.ctor_();
		return rv;
	}	GxwComboBox_lang() {}
}
class ComboBox_keylistener implements KeyListener {
	public ComboBox_keylistener(GxwComboBox_lang host) {this.host = host;} GxwComboBox_lang host;
	@Override public void keyPressed(KeyEvent arg0) {
//		if (arg0.getKeyChar() == 'f')
//			System.out.println('h');
		host.processKeyEvent(arg0);
		}
	@Override public void keyReleased(KeyEvent arg0) {
		//host.processKeyEvent(arg0);
		}
	@Override public void keyTyped(KeyEvent arg0) {
		//host.processKeyEvent(arg0);
		}	
}

