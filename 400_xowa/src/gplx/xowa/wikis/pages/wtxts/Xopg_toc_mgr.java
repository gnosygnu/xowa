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
package gplx.xowa.wikis.pages.wtxts; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.hdrs.*;
public class Xopg_toc_mgr {
	private Xop_hdr_tkn[] hdrs_ary = Xop_hdr_tkn.Ary_empty; private int hdrs_max, hdrs_len;
	public boolean Enabled() {
		return	!flag__notoc			// __NOTOC__ not set
			&&	hdrs_len != 0			// never show TOC if 0 headers, even when __FORCETOC__
			&&	(	hdrs_len > Hdrs_min // show TOC automatically if 4 or more headers
				||	flag__toc			// or when __TOC__ specified (EX: 2 headers)
				||	flag__forcetoc		// or when __FORCETOC__ to (a) show TOC when < 4 headers or (b) let TOC show at default position; __TOC__ would force TOC to show at __TOC__; __FORCETOC__ can be placed at bottom of page
				)
			;
	}
	public boolean Flag__toc()			{return flag__toc;}
	public void Flag__toc_y_()		{flag__toc = true;}			private boolean flag__toc;			// __TOC__
	public void Flag__forcetoc_y_()	{flag__forcetoc = true;}	private boolean flag__forcetoc;	// __FORCETOC__
	public void Flag__notoc_y_()	{flag__notoc = true;}		private boolean flag__notoc;		// __NOTOC__

	public int			Len()			{return hdrs_len;}
	public Xop_hdr_tkn	Get_at(int i)	{return hdrs_ary[i];}
	public void Add(Xop_hdr_tkn hdr) {
		// add tkn
		if (hdrs_len == 0) hdr.First_in_doc_y_();	// if 1st hdr, mark it; easier for toc-insertion logic later

		// add to list; logic for bounds checking
		int new_len = hdrs_len + 1;
		if (new_len > hdrs_max) {
			hdrs_max = (new_len * 2) + 1;
			hdrs_ary = (Xop_hdr_tkn[])Array_.Resize(hdrs_ary, hdrs_max);
		}
		hdrs_ary[hdrs_len] = hdr;
		hdrs_len = new_len;
	}
	public void Clear() {
		flag__toc = flag__forcetoc = flag__notoc = false;
		hdrs_len = 0; hdrs_max = 0;
		hdrs_ary = Xop_hdr_tkn.Ary_empty;
	}
	public static final int Hdrs_min = 3;
}
