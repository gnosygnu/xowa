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
package gplx.xowa.specials.search.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.search.*;
class Srch_crt_tkn {
	public final byte	tid;
	public final byte[]	val;
	public Srch_crt_tkn(byte tid, byte[] val) {this.tid = tid; this.val = val;}

	public static final byte 
	  Tid_root			=  1
	, Tid_word			=  2
	, Tid_word_quoted	=  3
	, Tid_space			=  4
	, Tid_quote			=  5
	, Tid_not			=  6
	, Tid_paren_bgn		=  7
	, Tid_paren_end		=  8
	, Tid_or			=  9
	, Tid_and			= 10
	, Tid_eos			= 11
	;
}
