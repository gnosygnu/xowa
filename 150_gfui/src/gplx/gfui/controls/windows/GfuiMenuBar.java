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
package gplx.gfui.controls.windows; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import gplx.gfui.draws.*; import gplx.gfui.ipts.*; import gplx.gfui.layouts.*; import gplx.gfui.kits.core.*; import gplx.gfui.controls.gxws.*;
import gplx.langs.gfs.*;
public class GfuiMenuBar implements Gfo_invk {
	public Object Under() {return winMenu;}
	public boolean Visible() {return visible;} private boolean visible;
	public void Visible_set(boolean v) {
		this.visible = v;
		MenuBar_visible_set(v);
		boolean menuBandExists = win.Lyt().Bands_has(menuBand.Key());
		if		( visible && !menuBandExists)
			win.Lyt().Bands_add(menuBand);
		else if (!visible &&  menuBandExists)
			win.Lyt().Bands_del(menuBand.Key());
		GftGrid.LytExecRecur(win);
	}	GftBand menuBand = GftBand.fillWidth_().Key_("GfuiMenuBar");
	public void Load_cfg(String cfgKey) {
		try {
			root.ForeColor_(ColorAdp_.Black).BackColor_(ColorAdp_.White);
			root.ExecProps();
			curOwnerItm = root;
			GfsCore.Instance.AddObj(this, "GfuiMenuBar_");
			GfsCore.Instance.ExecRegy("gplx.gfui.GfuiMenuBar.ini");
		}
		catch (Exception e) {GfuiEnv_.ShowMsg(Err_.Message_gplx_full(e));}
	}
	String separatorText, mnemonicPrefix; int separatorIdx = 0;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_visible_toggle))		this.Visible_set(!visible);
		else if (ctx.Match(k, Invk_SeparatorText_))		separatorText = GfoMsgUtl.SetStr(ctx, m, separatorText);
		else if (ctx.Match(k, Invk_MnemonicPrefix_))	mnemonicPrefix = GfoMsgUtl.SetStr(ctx, m, mnemonicPrefix);
		else if	(ctx.Match(k, Invk_Visible_))	{
			boolean v = m.ReadBoolOr("v", true);
			if (ctx.Deny()) return this;
			Visible_set(v);
		}
		else if (ctx.Match(k, Invk_RegTop)) {
			String s = m.ReadStrOr("v", null);
			if (ctx.Deny()) return this;
			GfuiMenuBarItm itm = GfuiMenuBarItm.sub_(root);
			itm.Type_(GfuiMenuBarItmType.Top);
			itm.Text_(s);
			itm.Ipt_(Read_prop_ipt(IptKey_.None, itm));
			itms.Add(s, itm);
			itm.ExecProps();
			curOwnerItm = itm;
		}
		else if (ctx.Match(k, Invk_RegSpr)) {
			String text = m.ReadStr("text");
			if (ctx.Deny()) return this;
			GfuiMenuBarItm itm = GfuiMenuBarItm.sub_(curOwnerItm);
			itm.Type_(GfuiMenuBarItmType.Spr);
			itm.Text_(text);
			itm.Key_(curOwnerItm.Key() + "." + text + Int_.To_str(separatorIdx++));
			itm.ExecProps();
		}
		else if (ctx.Match(k, Invk_RegCmd)) {
			String text = m.ReadStr("text");
			String cmd = m.ReadStrOr("cmd", null);
			String tipText = m.ReadStrOr("tipText", null);
			if (ctx.Deny()) return this;
			GfuiMenuBarItm itm = GfuiMenuBarItm.sub_(curOwnerItm);
			itm.Type_(GfuiMenuBarItmType.Cmd);
			itm.Text_(text);
			itm.Cmd_(cmd);
			itm.TipText_(tipText);
			itm.Ipt_(Read_prop_ipt(IptKey_.None, itm));
			itm.Key_(curOwnerItm.Key() + "." + text);
			itm.ExecProps();
		}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	public static final    String Invk_visible_toggle = "MenuBar_toggle"
			, Invk_Visible_ = "Visible_", Invk_SeparatorText_ = "SeparatorText_", Invk_MnemonicPrefix_ = "MnemonicPrefix_"
			, Invk_RegTop = "RegTop", Invk_RegCmd = "RegCmd", Invk_RegSpr = "RegSpr"
			;
	GfuiMenuBarItm curOwnerItm = null;
	IptKey Read_prop_ipt(IptKey ipt, GfuiMenuBarItm itm) {
		if (mnemonicPrefix == null) return ipt;
		String text = itm.Text();
		int pos = String_.FindFwd(text, mnemonicPrefix);
		if (pos == String_.Find_none || pos == String_.Len(text) - 1) return ipt;	// mnemonic not found
		String keyChar = String_.MidByLen(text, pos + 1, 1);
		if (!Char_.IsLetterEnglish(String_.CharAt(keyChar, 0))) return ipt;	// keyChar is not a character; EX: 'A & B' (keyChar = space)
		String keyCharRaw = "key." + String_.Lower(keyChar);
		ipt = IptKey_.parse(keyCharRaw);
		text = String_.MidByLen(text, 0, pos) + String_.Mid(text, pos + 1);	// remove mnemPrefix; ex: &File -> File && key.f
		itm.Text_(text);
		return ipt;
	}
		void MenuBar_visible_set(boolean v) {winMenu.setVisible(v);}
	void InitMenuBar(GxwWin win) {
		ownerForm = (JFrame)win;
		ownerForm.setJMenuBar(winMenu);
		winMenu.setBorder(GxwBorderFactory.Empty);
	}
	JMenuBar winMenu = new JMenuBar();
	JFrame ownerForm;
		GfuiMenuBarItm root;
	void Init(GfuiWin win) {
		InitMenuBar((GxwWin)win.UnderElem());
		root = GfuiMenuBarItm.root_(winMenu);
		itms.Add(root.Key(), root);
		this.win = win;
		IptBnd_.cmd_to_(GfuiEnv_.IptBndMgr_win, win, this, Invk_visible_toggle, IptKey_.add_(IptKey_.Ctrl, IptKey_.Shift, IptKey_.F12));
		win.SubItms_add(SubItms_key, this);
	}
	Hash_adp itms = Hash_adp_.New(); GfuiWin win;
	public static final    String SubItms_key = "menuBar";
	public static GfuiMenuBar new_(GfuiWin win) {
		GfuiMenuBar rv = new GfuiMenuBar();
		rv.Init(win);
		return rv;
	}	GfuiMenuBar() {}
}
class GfuiMenuBarItm {
	public String Key() {return key;} public GfuiMenuBarItm Key_(String val) {key = val; return this;} private String key = "";
	public String Text() {return text;} public GfuiMenuBarItm Text_(String val) {text = val; return this;} private String text = "";
	public String Cmd() {return cmd;} public GfuiMenuBarItm Cmd_(String val) {cmd = val; return this;} private String cmd = "";
	public GfuiMenuBarItmType Type() {return type;} public GfuiMenuBarItm Type_(GfuiMenuBarItmType val) {type = val; return this;} GfuiMenuBarItmType type;
	public IptKey Ipt() {return ipt;} public GfuiMenuBarItm Ipt_(IptKey val) {ipt = val; return this;} IptKey ipt;
	public String TipText() {return tipText;} public GfuiMenuBarItm TipText_(String val) {tipText = val; return this;} private String tipText = "";
	public ColorAdp ForeColor() {return foreColor;} public GfuiMenuBarItm ForeColor_(ColorAdp val) {foreColor = val; return this;} ColorAdp foreColor;
	public ColorAdp BackColor() {return backColor;} public GfuiMenuBarItm BackColor_(ColorAdp val) {backColor = val; return this;} ColorAdp backColor;
	public String FontFamily() {return fontFamily;} public GfuiMenuBarItm FontFamily_(String val) {fontFamily = val; return this;} private String fontFamily = "";
	public float FontSize() {return fontSize;} public GfuiMenuBarItm FontSize_(float val) {fontSize = val; return this;} float fontSize = -1;
	public FontStyleAdp FontStyle() {return fontStyle;} public GfuiMenuBarItm FontStyle_(FontStyleAdp val) {fontStyle = val; return this;} FontStyleAdp fontStyle;
	public GfuiMenuBarItm OwnerItm() {return ownerItm;} public GfuiMenuBarItm OwnerItm_(GfuiMenuBarItm val) {ownerItm = val; return this;} GfuiMenuBarItm ownerItm;
	public Object Under() {return under;}
		void ExecProps() {
		if 		(type == GfuiMenuBarItmType.Root) 	Exec_root();
		else if (type == GfuiMenuBarItmType.Cmd)	Exec_cmd();
		else if (type == GfuiMenuBarItmType.Spr) 	Exec_spr();
		else										Exec_mnu();				
	}
	void Exec_root() {
		SetProps((JMenuBar)under);
	}
	void Exec_mnu() {
		JMenu itm = new JMenu(text);
		if 	(ownerItm.type == GfuiMenuBarItmType.Root)
			((JMenuBar)ownerItm.Under()).add(itm);
		else {
			((JMenu)ownerItm.Under()).add(itm);
		}
		SetMnemonic(itm);
		SetProps(itm);
		under = itm;		
	}	
	void Exec_cmd() {
		JMenuItem itm = new JMenuItem(text);
		JMenu ownerMenu = ((JMenu)ownerItm.Under());
		ownerMenu.add(itm);
		itmCmd = GfuiMenuBarItmCmd.new_(this);			
		itm.addActionListener(itmCmd);
		SetMnemonic(itm);
		SetProps(itm);
		under = itm;		
	}
	void Exec_spr() {
		JMenu ownerMenu = ((JMenu)ownerItm.Under());
		JSeparator itm = new JSeparator();
		ownerMenu.add(itm);
		SetProps(itm);
		under = itm;		
	}
	void SetMnemonic(AbstractButton itm) {
		char mnem = GetMnemonic(text, ipt);
		if (mnem != '\0') itm.setMnemonic(mnem); 		
	}
	void SetProps(JComponent itm) {
		if (backColor != null) itm.setBackground(ColorAdpCache.Instance.GetNativeColor(backColor));
		if (foreColor != null) itm.setForeground(ColorAdpCache.Instance.GetNativeColor(foreColor));
		itm.setFont(MakeFont(itm.getFont()));		
		if (String_.Len(tipText) > 0) itm.setToolTipText(tipText);		
	}
	Font MakeFont(Font cur) {
		if (fontFamily == null && fontStyle == null && fontSize == -1) return cur;
		String family = fontFamily == null ? cur.getFamily() : fontFamily;
		int style = fontStyle == null ? cur.getStyle() : fontStyle.Val();
		int size = fontSize == -1 ? cur.getSize() : (int)fontSize;
		return new Font(family, style, (int)size);
	}
	GfuiMenuBarItm Under_(Object o) {under = o; return this;}
	char GetMnemonic(String text, IptKey ipt) {
		if (ipt.Val() == IptKey_.None.Val()) return '\0';
		String iptStr = ipt.XtoUiStr(); if (String_.Len(iptStr) > 1) return '\0';
		return String_.CharAt(iptStr, 0);
	}
	GfuiMenuBarItmCmd itmCmd;
	Object under;
		public static GfoMsg CmdMsg(GfuiMenuBarItm itm) {
		if (itm.cmd == null) throw Err_.new_null().Args_add("key", itm.key, "text", itm.text);
		return gplx.gfml.GfmlDataNde.XtoMsgNoRoot(itm.cmd);
	}
	public static GfuiMenuBarItm new_() {return new GfuiMenuBarItm();}
	public static GfuiMenuBarItm sub_(GfuiMenuBarItm owner) {
		GfuiMenuBarItm rv = new_();
		rv.ownerItm = owner;
		rv.foreColor = owner.foreColor;
		rv.backColor = owner.backColor;
		rv.fontFamily = owner.fontFamily;
		rv.fontSize = owner.fontSize;
		rv.fontStyle = owner.fontStyle;
		return rv;
	}
	public static GfuiMenuBarItm root_(Object underMenuBar) {
		GfuiMenuBarItm rv = new GfuiMenuBarItm().Type_(GfuiMenuBarItmType.Root).Key_("root").Under_(underMenuBar);
		return rv;
	}	GfuiMenuBarItm() {}
}
class GfuiMenuBarItmType {
	public int Val() {return val;} int val;
	public String Name() {return name;} private String name;
	GfuiMenuBarItmType(int v, String n) {val = v; name = n; regy.Add(n, this);}
	public static GfuiMenuBarItmType parse(String raw) {
		try {return (GfuiMenuBarItmType)regy.Get_by(raw);}
		catch (Exception e) {Err_.Noop(e); throw Err_.new_parse("GfuiMenuBarItmType", raw);}
	}
	static Hash_adp regy = Hash_adp_.New();
	public static final    GfuiMenuBarItmType Root = new GfuiMenuBarItmType(1, "root");
	public static final    GfuiMenuBarItmType Top = new GfuiMenuBarItmType(2, "top");
	public static final    GfuiMenuBarItmType Mnu = new GfuiMenuBarItmType(3, "mnu");
	public static final    GfuiMenuBarItmType Cmd = new GfuiMenuBarItmType(4, "cmd");
	public static final    GfuiMenuBarItmType Spr = new GfuiMenuBarItmType(5, "spr");
}
