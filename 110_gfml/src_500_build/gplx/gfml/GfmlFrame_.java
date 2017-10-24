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
package gplx.gfml; import gplx.*;
class GfmlFrame_ {
	public static GfmlFrame comment_() {return new GfmlFrame_comment();}
	public static GfmlFrame quote_() {return new GfmlFrame_quote();}
	public static GfmlFrame eval_() {return new GfmlFrame_eval();}
	public static final int
		  Type_nde		= 1
		, Type_data		= 2
		, Type_comment	= 3
		;
}
abstract class GfmlFrame_base implements GfmlFrame {
	public GfmlLxr Lxr() {return lxr;} GfmlLxr lxr;
	public abstract int FrameType();
	public int BgnPos() {return bgnPos;} public void BgnPos_set(int v) {bgnPos = v;} int bgnPos;
	public GfmlObjList WaitingTkns() {return waitingTkns;} @gplx.Internal protected final GfmlObjList waitingTkns = GfmlObjList.new_();
	public GfmlFrame MakeNew(GfmlLxr newLxr) {GfmlFrame_base rv = MakeNew_hook(); rv.ctor_(newLxr); return rv;}
	public abstract void Build_end(GfmlBldr bldr, GfmlFrame ownerFrame);
	protected abstract GfmlFrame_base MakeNew_hook();
	protected void ctor_(GfmlLxr lxr) {this.lxr = lxr;}
}
class GfmlFrame_comment extends GfmlFrame_base {
	@Override public int FrameType() {return GfmlFrame_.Type_comment;}
	@Override public void Build_end(GfmlBldr bldr, GfmlFrame ownerFrame) {
		GfmlTkn commentTkn = GfmlTkn_.composite_list_("tkn:comment", waitingTkns);
		ownerFrame.WaitingTkns().Add(commentTkn);						// always add commentTkn to WaitingTkns
	}
	@Override protected GfmlFrame_base MakeNew_hook() {return new GfmlFrame_comment();}
}	
class GfmlFrame_quote extends GfmlFrame_base {
	@Override public int FrameType() {return GfmlFrame_.Type_data;}
	@Override public void Build_end(GfmlBldr bldr, GfmlFrame ownerFrame) {
		GfmlTkn quoteTkn = GfmlTkn_.composite_list_("tkn:quote", waitingTkns);
		GfmlFrameUtl.AddFrameTkn(ownerFrame, quoteTkn);
	}
	@Override protected GfmlFrame_base MakeNew_hook() {return new GfmlFrame_quote();}
}
class GfmlFrame_eval extends GfmlFrame_base {
	@Override public int FrameType() {return GfmlFrame_.Type_data;}
	@Override public void Build_end(GfmlBldr bldr, GfmlFrame ownerFrame) {			
		GfmlTkn evalTkn = MakeEvalTkn(bldr, waitingTkns);
		GfmlFrameUtl.AddFrameTkn(ownerFrame, evalTkn);
	}
	@Override protected GfmlFrame_base MakeNew_hook() {return new GfmlFrame_eval();}
	static GfmlTkn MakeEvalTkn(GfmlBldr bldr, GfmlObjList waitingTkns) {
		GfmlTkn composite = GfmlTkn_.composite_list_("tkn:eval_temp", waitingTkns);
		String[] ary = ExtractContextKey(composite.Val());
		String varContext = ary[0], varKey = ary[1];
		GfmlVarCtx evalContext = GfmlVarCtx_.FetchFromCacheOrNew(bldr.Vars(), varContext);
		return new GfmlVarTkn("tkn:eval", composite.SubTkns(), evalContext, varKey);
	}
	static String[] ExtractContextKey(String raw) {
		String[] ary = String_.Split(raw, ".");
		if (ary.length == 2) return ary; // NOOP: elems already assigned; context = ary[0]; key = ary[1];
		if (ary.length > 2) throw Err_.new_wo_type("invalid context key for eval frame; should have 0 or 1 dlms", "key", raw);
		String[] rv = new String[2];
		rv[0] = GfmlVarCtx_.DefaultKey;
		rv[1] = ary[0];
		return rv;
	}
}	
class GfmlFrameUtl {
	@gplx.Internal protected static void AddFrameTkn(GfmlFrame ownerFrame, GfmlTkn frameTkn) {
		GfmlFrame_nde nodeFrame = GfmlFrame_nde_.as_(ownerFrame);
		if (nodeFrame != null)						// ownerFrame is node: set frameTkn as dataTkn
			nodeFrame.DatTkn_set(frameTkn);
		else										// ownerFrame is likely quoteFrame; add to waitingTkns (allows multiple nestings)
			ownerFrame.WaitingTkns().Add(frameTkn);
	}
}
