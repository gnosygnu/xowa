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
package gplx.xowa.addons.apps.searchs.searchers.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.searchs.*; import gplx.xowa.addons.apps.searchs.searchers.*;
public class Srch_crt_tkn {
	public Srch_crt_tkn(byte tid, byte[] val) {this.Tid = tid; this.Val = val;}
	public final    byte	Tid;
	public final    byte[]	Val;
	public static final byte 
	  Tid__escape		=  0
	, Tid__space		=  1
	, Tid__quote		=  2
	, Tid__not			=  3
	, Tid__and			=  4
	, Tid__or			=  5
	, Tid__paren_bgn	=  6
	, Tid__paren_end	=  7
	, Tid__word			=  8
	, Tid__word_w_quote	=  9
	, Tid__eos			= 10
	, Tid__null			= 11
	;
	public static final    Srch_crt_tkn[] Ary_empty = new Srch_crt_tkn[0];
}
