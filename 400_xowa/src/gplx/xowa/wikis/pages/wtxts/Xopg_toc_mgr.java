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
	public boolean Flag__toc()                {return flag__toc;}
	public void Flag__toc_(boolean v)         {flag__toc = v;}          private boolean flag__toc;		// __TOC__
	public void Flag__forcetoc_(boolean v)    {flag__forcetoc = v;}     private boolean flag__forcetoc;	// __FORCETOC__
	public void Flag__notoc_(boolean v)       {flag__notoc = v;}        private boolean flag__notoc;		// __NOTOC__

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
