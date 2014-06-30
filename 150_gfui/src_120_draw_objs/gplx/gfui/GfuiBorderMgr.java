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
			this.Edge_set(GfuiBorderEdge.All, PenAdp_.new_((ColorAdp)ary[1], Float_.cast_(ary[0])));
		}
	}
	public void Edge_set(GfuiBorderEdge edge, PenAdp pen) {
		int val = edge.Val();
		if		(val == GfuiBorderEdge.All.Val())		{all = pen; return;}
		else if (val == GfuiBorderEdge.Left.Val())		{left = pen; return;}
		else if (val == GfuiBorderEdge.Right.Val())		{right = pen; return;}
		else if (val == GfuiBorderEdge.Top.Val())		{top = pen; return;}
		else if (val == GfuiBorderEdge.Bot.Val())		{bot = pen; return;}
		else throw Err_.unhandled(edge);
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
	public String XtoStr() {return String_bldr_.new_().Add_kv_obj("all", all).Add_kv_obj("left", left).Add_kv_obj("right", right).Add_kv_obj("top", top).Add_kv_obj("bot", bot).XtoStr();}
	@Override public String toString() {return XtoStr();}
	public static GfuiBorderMgr new_() {return new GfuiBorderMgr();} GfuiBorderMgr() {}
}
