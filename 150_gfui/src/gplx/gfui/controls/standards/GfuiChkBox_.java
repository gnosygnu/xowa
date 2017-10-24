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
import gplx.gfui.gfxs.*; import gplx.gfui.controls.elems.*;
public class GfuiChkBox_ {
	public static GfuiChkBox as_(Object obj) {return obj instanceof GfuiChkBox ? (GfuiChkBox)obj : null;}
	public static GfuiChkBox cast(Object obj) {try {return (GfuiChkBox)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, GfuiChkBox.class, obj);}}
	@gplx.Internal protected static GfuiChkBox new_() {
		GfuiChkBox rv = new GfuiChkBox();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_true_());
		return rv;
	}

	public static GfuiChkBox msg_(String key, GfuiElem owner, String text, GfoMsg msg) {
		GfuiChkBox rv = new_(); rv.Owner_(owner, key).Text_any_(text);
		rv.Click_msg_(msg);
		return rv;
	}
	public static GfuiChkBox invk_(String key, GfuiElem owner, String text, Gfo_invk invk, String m) {
		GfuiChkBox rv = new_(); rv.Owner_(owner, key).Text_any_(text);
		rv.Click_invk(Gfo_invk_cmd.New_by_key(invk, m));
		return rv;
	}
	public static GfuiChkBox noop_(String key, GfuiElem owner, String text) {
		GfuiChkBox rv = new_(); rv.Owner_(owner, key); rv.Text_any_(text);
		return rv;
	}
	public static RectAdp CboxRect(GfuiChkBox box) {
		SizeAdp size = SizeAdp_.new_(12, 12);
		PointAdp pos = GfuiAlign_.CalcInsideOf(box.AlignH(), box.AlignV(), size, box.Size(), box.ChkAlignCustom());
		return RectAdp_.vector_(pos.Op_add(0, 0), size);
	}
	public static void DrawCheckBox(GfuiChkBox box, GfxAdp gfx) {
		RectAdp cboxRect = CboxRect(box);
		box.TextMgr().AlignH_(box.AlignH()).AlignV_(box.AlignV());
		RectAdpF textRect = box.TextMgr().TextRect_calc(gfx);

		int w = cboxRect.Width() + 4 + (int)textRect.Width();
		int h = (int)textRect.Height();	// assume textRect.Height is >= cboxRect.Height
		SizeAdp size = SizeAdp_.new_(w, h);
		PointAdp pos = GfuiAlign_.CalcInsideOf(box.AlignH(), box.AlignV(), size, box.Size(), PointAdp_.Zero);
		cboxRect = RectAdp_.vector_(pos.Op_add(0, 1), cboxRect.Size());		
		textRect = RectAdpF.new_(pos.X() + cboxRect.Width() + 4, textRect.Y(), textRect.Width(), textRect.Height());
		box.TextMgr().TextRect_set(textRect);
		box.TextMgr().DrawData(gfx);

		gfx.DrawRect(box.ChkPen(), cboxRect.X(), cboxRect.Y(), cboxRect.Width(), cboxRect.Height());
		if (box.Val()) {
			gfx.DrawLine(box.ChkPen(), cboxRect.CornerTL().Op_add(3), cboxRect.CornerBR().Op_add(-3));
			gfx.DrawLine(box.ChkPen(), cboxRect.CornerBL().Op_add(3, -3), cboxRect.CornerTR().Op_add(-3, 3));
		}
		if (box.Focus_has()) {
			// -1 so border does not start right on top of text; RoundUp to give a little more space to edges
			// +2 so that focusRect does not overlap mnemonic (in C#)
			box.FocusBorder().Bounds_sync(RectAdp_.new_((int)textRect.X() - 1, (int)cboxRect.Y(), Float_.RoundUp(textRect.Width()), Float_.RoundUp(cboxRect.Height())));	
			box.FocusBorder().DrawData(gfx);
		}
	}
}
