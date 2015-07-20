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
public class IptMouseBtn_ {
	public static final int 
	  Tid_none		= 0x00000000
	, Tid_left		= 0x00100000
	, Tid_right		= 0x00200000
	, Tid_middle	= 0x00400000
	, Tid_x1		= 0x00400000
	, Tid_x2		= 0x01000000
	;
	public static final IptMouseBtn	// REF: System.Windows.Forms.MouseButtons
	  None		= new IptMouseBtn(Tid_none		, "mouse.none")
	, Left		= new IptMouseBtn(Tid_left		, "mouse.left")
	, Right		= new IptMouseBtn(Tid_right		, "mouse.right")
	, Middle	= new IptMouseBtn(Tid_middle	, "mouse.middle")
	, X1		= new IptMouseBtn(Tid_x1		, "mouse.x1")
	, X2		= new IptMouseBtn(Tid_x2		, "mouse.x2")
	;
	public static IptMouseBtn parse_(String raw) {
		if		(String_.Eq(raw, None.Key())) return None;
		else if	(String_.Eq(raw, Left.Key())) return Left;
		else if	(String_.Eq(raw, Right.Key())) return Right;
		else if	(String_.Eq(raw, Middle.Key())) return Middle;
		else if	(String_.Eq(raw, X1.Key())) return X1;
		else if	(String_.Eq(raw, X2.Key())) return X2;
		else throw Err_.new_parse_type(IptMouseBtn.class, raw);
	}
	@gplx.Internal protected static IptMouseBtn api_(int val) {
		if		(val == None.Val()) return None;
		else if	(val == Left.Val()) return Left;
		else if	(val == Right.Val()) return Right;
		else if	(val == Middle.Val()) return Middle;
		else if	(val == X1.Val()) return X1;
		else if	(val == X2.Val()) return X2;
		else throw Err_.new_unhandled(val);
	}
}
