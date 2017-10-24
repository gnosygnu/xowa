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
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import gplx.gfui.ipts.*; import gplx.gfui.gfxs.*; import gplx.gfui.controls.windows.*;
public class GxwCheckListBox_lang extends JScrollPane implements GxwCheckListBox, GxwElem {
	Vector<CheckListItem> internalItems;
	GxwListBox_lang listBox;
	public GxwCheckListBox_lang() {
		initComponents();
	}
	private void initComponents() {
		listBox = GxwListBox_lang.new_();
		this.setViewportView(listBox);

		internalItems = new Vector<CheckListItem>();
		this.listBox.setListData(internalItems);

		CheckListCellRenderer renderer = new CheckListCellRenderer();
		listBox.setCellRenderer(renderer);
		listBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		CheckListListener lst = new CheckListListener(this); listBox.addMouseListener(lst); listBox.addKeyListener(lst);
		ctrlMgr = new GxwCore_host(GxwCore_lang.new_(this), listBox.ctrlMgr);
		
//		ctrlMgr = GxwCore_lang.new_(this);
		this.enableEvents(AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK);
		GxwBoxListener_checkListBox lnr = new GxwBoxListener_checkListBox(this, this);
//		this.setFocusTraversalKeysEnabled(false);
		this.addComponentListener(lnr);
		this.addFocusListener(lnr);
	}
	public GxwCbkHost Host() {return host;}
	public void Host_set(GxwCbkHost host) {
		this.host = host;
		listBox.Host_set(host);
	} 	private GxwCbkHost host = GxwCbkHost_.Null;
//	@Override public boolean requestFocusInWindow() {
//		return listBox.requestFocusInWindow();
////		return super.requestFocusInWindow();
//	}
	public List_adp Items_getAll() {return Items_get(Mode_All);}
	public int Items_count() {return internalItems.size();}
	public boolean Items_getCheckedAt(int i) {return internalItems.get(i).selected;}
	public void Items_setCheckedAt(int i, boolean v) {internalItems.get(i).selected = v;}
	public List_adp Items_getChecked() {return Items_get(Mode_Selected);}
	static final int Mode_All = 1, Mode_Selected = 2; 
	List_adp Items_get(int mode) {
		List_adp list = List_adp_.New();
		for (CheckListItem data: internalItems) {
			boolean add = (mode == Mode_All) || (mode == Mode_Selected) && data.Selected();
			if (add)
				list.Add(data.Data());
		}
		return list;
	}
	public void Items_add(Object data, boolean selected) {		
		internalItems.add(new CheckListItem(data, selected));
		this.listBox.setListData(internalItems);
		this.listBox.updateUI();
	}
	public void Items_setAll(boolean v) {
		for (CheckListItem data: internalItems) {
			data.Selected_set(v);
		}		
		this.listBox.updateUI();
	}
	public void Items_reverse() {
		for (CheckListItem data: internalItems) {
			data.Selected_set(!data.Selected());
		}		
		this.listBox.updateUI();
	}
	public void Items_clear() {
		internalItems.clear();
		this.listBox.updateUI();
	}
	@Override public void setBounds(Rectangle r) {super.setBounds(r); this.validate();}
	@Override public void setSize(int w, int h) {super.setSize(w, h); this.validate();}
	@Override public void setSize(Dimension d) {super.setSize(d); this.validate();}

	public GxwCore_base Core() {return ctrlMgr;} GxwCore_base ctrlMgr;
	public String TextVal() {return "GxwCheckListBox_lang";} public void TextVal_set(String v) {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return this;
	}
	public void SendKeyDown(IptKey key)								{}
	public void SendMouseMove(int x, int y)							{}
	public void SendMouseDown(IptMouseBtn btn)						{}
	public void SendMouseWheel(IptMouseWheel direction)				{}
	@Override public void processKeyEvent(KeyEvent e) 					{
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
	public void EnableDoubleBuffering() {	// eliminates flickering during OnPaint
	}
	public void CreateControlIfNeeded() {
	}
}
class GxwBoxListener_checkListBox extends GxwBoxListener {
	@Override public void focusGained(FocusEvent e) 			{
		host.Host().FocusGotCbk();
		clb.listBox.requestFocusInWindow();
		if (clb.listBox.getSelectedIndex() < 0)
			clb.listBox.setSelectedIndex(0);
	}
	@Override public void focusLost(FocusEvent e) 				{host.Host().FocusLostCbk();}
	public GxwBoxListener_checkListBox(GxwElem host, GxwCheckListBox_lang clb) {super(host); this.clb = clb;}
	GxwCheckListBox_lang clb;
}
class CheckListListener implements MouseListener, KeyListener {	
	GxwCheckListBox_lang m_parent; JList m_list;	
	public CheckListListener(GxwCheckListBox_lang parent) {
		m_parent = parent;
		m_list = parent.listBox;
	}
	public void mouseClicked(MouseEvent e) {
		if (e.getX() < 20) doCheck();
	}
	public void mousePressed(MouseEvent e) {}	
	public void mouseReleased(MouseEvent e) {}	
	public void mouseEntered(MouseEvent e) {}	
	public void mouseExited(MouseEvent e) {}	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == ' ') doCheck();
	}	
	public void keyTyped(KeyEvent e) {
	}	
	public void keyReleased(KeyEvent e) {
	}	
	protected void doCheck() {
		int index = m_list.getSelectedIndex();
		if (index < 0) return;
		CheckListItem data = (CheckListItem)m_list.getModel().getElementAt(index);
		data.Selected_toggle();
		m_list.repaint();
	}
}
class CheckListCellRenderer extends JCheckBox implements ListCellRenderer{
	public CheckListCellRenderer() {
		setOpaque(true);
		setBorder(GxwBorderFactory.Empty);
	}	
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {		
		if (value != null) setText(value.toString());		
		setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
		setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
		
		CheckListItem data = (CheckListItem)value;
		setSelected(data.Selected());
		
		setFont(list.getFont());
		setBorder((cellHasFocus) ? UIManager.getBorder("List.focusCellHighlightBorder") : GxwBorderFactory.Empty);		
		return this;
	}
}
class CheckListItem {
    public CheckListItem(Object data, Boolean selected) {this.data = data; this.selected = selected;}
    public Object Data() {return data;} Object data;       
    public boolean Selected() {return selected;} public void Selected_set(boolean selected) {this.selected = selected;}	protected boolean selected;
    public void Selected_toggle() {selected = !selected;}
    public String toString() {return Object_.Xto_str_strict_or_null_mark(data);}
}
