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
package gplx.xowa.specials.search.parsers_old; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.search.*;
class Xow_search_tkn {
	private final int val_bgn, val_end;
	private final byte[] val_bry;
	Xow_search_tkn(byte tid, int val_bgn, int val_end, byte[] val_bry) {this.tid = tid; this.val_bgn = val_bgn; this.val_end = val_end; this.val_bry = val_bry;}
	public byte			Tid()				{return tid;} private final byte tid;	
	public byte[]		Val(byte[] src)		{return val_bry == null ? Bry_.Mid(src, val_bgn, val_end) : val_bry;}
	public static Xow_search_tkn new_pos(byte tid, int val_bgn, int val_end)	{return new Xow_search_tkn(tid, val_bgn, val_end, null);}
	public static Xow_search_tkn new_bry(byte tid, byte[] val_bry)				{return new Xow_search_tkn(tid, -1, -1, val_bry);}
	public static final byte 
	  Tid_root = 1
	, Tid_word = 2
	, Tid_word_quoted = 3
	, Tid_space = 4
	, Tid_quote = 5
	, Tid_not = 6
	, Tid_paren_bgn = 7
	, Tid_paren_end = 8
	, Tid_or = 9
	, Tid_and = 10
	, Tid_eos = 11
	;
}
