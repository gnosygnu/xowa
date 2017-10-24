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
package gplx.gfui.controls; import gplx.*; import gplx.gfui.*;
import gplx.gfui.draws.*; import gplx.gfui.gfxs.*;
import gplx.core.strings.*;
public class GfuiBorderMgr {
	public PenAdp All() {return all;}		public GfuiBorderMgr All_(PenAdp v) {SyncPens(true); all = v; return this;} PenAdp all;
	public PenAdp Left() {return left;}		public GfuiBorderMgr Left_(PenAdp v) {SyncPens(false); left = v; return this;} PenAdp left;
	public PenAdp Right() {return right;}	public GfuiBorderMgr Right_(PenAdp v) {SyncPens(false); right = v; return this;} PenAdp right;
	public PenAdp Top() {return top;}		public GfuiBorderMgr Top_(PenAdp v) {SyncPens(false); top = v; return this;} PenAdp top;
	public PenAdp Bot() {return bot;}		public GfuiBorderMgr Bot_(PenAdp v) {SyncPens(false); bot = v; return this;} PenAdp bot;
	public void Bounds_sync(RectAdp v) {bounds = v;} RectAdp bounds = RectAdp_.Zero;
	public void DrawData(GfxAdp gfx) {
		if (all != null)
			gfx.DrawRect(all, bounds);
		else {
			if (left != null) gfx.DrawLine(left, bounds.CornerBL(), bounds.CornerTL());
			if (right != null) gfx.DrawLine(right, bounds.CornerTR(), bounds.CornerBR());
			if (top != null) gfx.DrawLine(top, bounds.CornerTL(), bounds.CornerTR());
			if (bot != null) gfx.DrawLine(bot, bounds.CornerBR(), bounds.CornerBL());
		}
	}
	public GfuiBorderMgr None_() {Edge_set(GfuiBorderEdge.All, null); return this;}
	public void Edge_setObj(Object o) {
		if (o == null)
			this.None_();
		else {
			Object[] ary = (Object[])o;
			this.Edge_set(GfuiBorderEdge.All, PenAdp_.new_((ColorAdp)ary[1], Float_.cast(ary[0])));
		}
	}
	public void Edge_set(GfuiBorderEdge edge, PenAdp pen) {
		int val = edge.Val();
		if		(val == GfuiBorderEdge.All.Val())		{all = pen; return;}
		else if (val == GfuiBorderEdge.Left.Val())		{left = pen; return;}
		else if (val == GfuiBorderEdge.Right.Val())		{right = pen; return;}
		else if (val == GfuiBorderEdge.Top.Val())		{top = pen; return;}
		else if (val == GfuiBorderEdge.Bot.Val())		{bot = pen; return;}
		else throw Err_.new_unhandled(edge);
	}
	void SyncPens(boolean isAll) {
		if (isAll) {
			left = null; right = null; top = null; bot = null;
		}
		else {
			if (all != null) {
				left = all.Clone(); right = all.Clone(); top = all.Clone(); bot = all.Clone();
			}
			all = null;
		}
	}
	public String To_str() {return String_bldr_.new_().Add_kv_obj("all", all).Add_kv_obj("left", left).Add_kv_obj("right", right).Add_kv_obj("top", top).Add_kv_obj("bot", bot).To_str();}
	@Override public String toString() {return To_str();}
	public static GfuiBorderMgr new_() {return new GfuiBorderMgr();} GfuiBorderMgr() {}
}
