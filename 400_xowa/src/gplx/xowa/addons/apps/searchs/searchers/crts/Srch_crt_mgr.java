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
public class Srch_crt_mgr {
	public Srch_crt_mgr(byte[] key, Srch_crt_tkn[] tkns, Srch_crt_itm root, byte words_tid, Srch_crt_itm[] words_ary, Srch_crt_itm words_nth) {
		this.Key = key;
		this.Tkns = tkns;
		this.Root = root;
		this.Words_tid = words_tid;
		this.Words_ary = words_ary;
		this.Words_nth = words_nth;
	}
	public final    byte[]				Key;
	public final    Srch_crt_tkn[]		Tkns;
	public final    Srch_crt_itm		Root;
	public final    byte				Words_tid;
	public final    Srch_crt_itm[]		Words_ary;
	public final    Srch_crt_itm		Words_nth;
	public int Words_nth__len() {
		return Words_nth == null ? 0 : Words_nth.Raw.length;
	}

	public static final byte
	  Tid__one			= 0
	, Tid__ands			= 1
	, Tid__mixed		= 2
	;
	public static Srch_crt_mgr Invalid = new Srch_crt_mgr(Bry_.Empty, Srch_crt_tkn.Ary_empty, Srch_crt_itm.Invalid, Tid__one, Srch_crt_itm.Ary_empty, Srch_crt_itm.Invalid);
}
