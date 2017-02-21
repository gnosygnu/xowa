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
package gplx.gfui; import gplx.*;
import gplx.core.bits.*;
public class GfuiBorderEdge {
	public int Val() {return val;} int val;
	public boolean Has(GfuiBorderEdge comp) {return Bitmask_.Has_int(val, comp.val);}
	public GfuiBorderEdge Add(GfuiBorderEdge comp) {
		return new GfuiBorderEdge(comp.val + val);
	}
	@gplx.Internal protected GfuiBorderEdge(int v) {this.val = v;}
	public static final    GfuiBorderEdge Left		= new GfuiBorderEdge(1);
	public static final    GfuiBorderEdge Right		= new GfuiBorderEdge(2);
	public static final    GfuiBorderEdge Top		= new GfuiBorderEdge(4);
	public static final    GfuiBorderEdge Bot		= new GfuiBorderEdge(8);
	public static final    GfuiBorderEdge All		= new GfuiBorderEdge(15);
}
class GfuiBorderEdge_ {
	public static String To_str(GfuiBorderEdge edge) {
		int val = edge.Val();
		if		(val == GfuiBorderEdge.Left.Val())		return Left_raw;
		else if (val == GfuiBorderEdge.Right.Val())		return Right_raw;
		else if (val == GfuiBorderEdge.Top.Val())		return Top_raw;
		else if (val == GfuiBorderEdge.Bot.Val())		return Bot_raw;
		else if (val == GfuiBorderEdge.All.Val())		return All_raw;
		else throw Err_.new_unhandled(edge);
	}
	public static GfuiBorderEdge parse(String raw) {
		if		(String_.Eq(raw, Left_raw))		return GfuiBorderEdge.Left;
		else if (String_.Eq(raw, Right_raw))	return GfuiBorderEdge.Right;
		else if (String_.Eq(raw, Top_raw))		return GfuiBorderEdge.Top;
		else if (String_.Eq(raw, Bot_raw))		return GfuiBorderEdge.Bot;
		else if (String_.Eq(raw, All_raw))		return GfuiBorderEdge.All;
		else throw Err_.new_unhandled(raw);
	}
	public static final    String 
		  All_raw	= "all"
		, Top_raw	= "top"
		, Left_raw	= "left"
		, Right_raw = "right"
		, Bot_raw	= "bottom"
		;
}
