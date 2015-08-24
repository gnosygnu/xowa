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
public class GfuiBorderEdge {
	public int Val() {return val;} int val;
	public boolean Has(GfuiBorderEdge comp) {return Enm_.Has_int(val, comp.val);}
	public GfuiBorderEdge Add(GfuiBorderEdge comp) {
		return new GfuiBorderEdge(comp.val + val);
	}
	@gplx.Internal protected GfuiBorderEdge(int v) {this.val = v;}
	public static final GfuiBorderEdge Left		= new GfuiBorderEdge(1);
	public static final GfuiBorderEdge Right		= new GfuiBorderEdge(2);
	public static final GfuiBorderEdge Top		= new GfuiBorderEdge(4);
	public static final GfuiBorderEdge Bot		= new GfuiBorderEdge(8);
	public static final GfuiBorderEdge All		= new GfuiBorderEdge(15);
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
	public static GfuiBorderEdge parse_(String raw) {
		if		(String_.Eq(raw, Left_raw))		return GfuiBorderEdge.Left;
		else if (String_.Eq(raw, Right_raw))	return GfuiBorderEdge.Right;
		else if (String_.Eq(raw, Top_raw))		return GfuiBorderEdge.Top;
		else if (String_.Eq(raw, Bot_raw))		return GfuiBorderEdge.Bot;
		else if (String_.Eq(raw, All_raw))		return GfuiBorderEdge.All;
		else throw Err_.new_unhandled(raw);
	}
	public static final String 
		  All_raw	= "all"
		, Top_raw	= "top"
		, Left_raw	= "left"
		, Right_raw = "right"
		, Bot_raw	= "bottom"
		;
}
