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
package gplx.gfui.controls.standards; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.draws.*;
import gplx.gfui.kits.core.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*;
public class GfuiTextBox extends GfuiElemBase {
	public static final    String SelectionStartChanged_evt = "SelectionStartChanged";

	public boolean Border_on() {return textBox.Border_on();} public void Border_on_(boolean v) {BorderOn_set(v);}
	public ColorAdp Border_color() {return textBox.Border_color();} public void Border_color_(ColorAdp v) {textBox.Border_color_(v);}
	public int SelBgn() {return textBox.SelBgn();} public void SelBgn_set(int v) {textBox.SelBgn_set(v); Gfo_evt_mgr_.Pub(this, SelectionStartChanged_evt);}
	public int SelLen() {return textBox.SelLen();} public void SelLen_set(int v) {textBox.SelLen_set(v);}
	public String SelText() {
		return String_.MidByLen(this.TextMgr().Val(), this.SelBgn(), this.SelLen());
	}
	public void Focus_select_all() {
		this.Focus();
		this.SelBgn_set(0);
		int len = String_.Len(this.Text());
		this.SelLen_set(len);
	}
	public boolean OverrideTabKey() {return textBox.OverrideTabKey();} public void OverrideTabKey_(boolean val) {textBox.OverrideTabKey_(val);}
	public void Margins_set(int left, int top, int right, int bot) {textBox.Margins_set(left, top, right, bot);}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_Margins_)) {
			int u = m.ReadInt("u");
			int l = m.ReadInt("l");
			int d = m.ReadInt("d");
			int r = m.ReadInt("r");
			if (ctx.Deny()) return this;
			Margins_set(l, u, r, d);
			return this;
		}
		else	return super.Invk (ctx, ikey, k, m);
	}	static final String Invk_Margins_ = "Margins_";
	void BorderOn_set(boolean val) {
		textBox.Border_on_(val);
		if (val == false)
			this.Height_(13); // WORKAROUND (WinForms): if border is off, height automatically becomes 13 and immutable for singleLine fields; affects statusBox in opal.imgs which will show with small gap over bottom of screen
	}
	@gplx.Internal @Override protected void Click_key_set_(String v) {}// TextBox's shouldn't have clickKeys; among other things, .Text is used to set ClickKey, which for textBox may be very large

	@gplx.Internal protected void SetTextBox(GxwTextFld textBox) {this.textBox = textBox;}
	public void CreateControlIfNeeded() {textBox.CreateControlIfNeeded();}
	@Override public GxwElem UnderElem_make(Keyval_hash ctorArgs) {return GxwElemFactory_.Instance.text_fld_();}
	@Override public void ctor_GfuiBox_base(Keyval_hash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		textBox = (GxwTextFld)this.UnderElem();
	}	GxwTextFld textBox;
	@Override public void ctor_kit_GfuiElemBase(Gfui_kit kit, String key, GxwElem underElem, Keyval_hash ctorArgs) {
		super.ctor_kit_GfuiElemBase(kit, key, underElem, ctorArgs);
		textBox = (GxwTextFld)underElem;
	}
	public static final String CFG_border_on_ = "border_on_";
}
