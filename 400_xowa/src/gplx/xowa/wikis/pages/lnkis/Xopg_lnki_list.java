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
package gplx.xowa.wikis.pages.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.parsers.lnkis.*;
public class Xopg_lnki_list {
	private final    List_adp list = List_adp_.New();
	private int lnki_idx;
	public Xopg_lnki_list() {
		this.Clear();
	}
	public boolean			Disabled() {return disabled;} private boolean disabled; public Xopg_lnki_list Disabled_(boolean v) {this.disabled = v; return this;} 
	public int				Len() {return list.Len();}
	public Xopg_lnki_itm	Get_at(int i) {return (Xopg_lnki_itm)list.Get_at(i);}
	public void Add_direct(Xopg_lnki_itm lnki) {list.Add(lnki);}	// add lnki directly to list without changing html_uid; needed for hdumps which call "Fill_page" to transfer from Xoh_page to Xoae_page
	public void	Add(Xopg_lnki_itm lnki) {
		if (disabled) return;
		Xoa_ttl ttl = lnki.Ttl(); if (ttl == null) return;		// ttl is null for invalid links
		Xow_ns ns = ttl.Ns();
		lnki.Html_uid_(++lnki_idx);								// NOTE: set html_id in order html to print out "id='xowa_lnki_1'; want to print out id for consistency's sake, even if these links won't be check for redlinks; DATE:2015-05-07
		if (	ns.Id_is_file_or_media()						// ignore files which will usually not be in local wiki (most are in commons), and whose html is built up separately
			||	(ns.Id_is_ctg() && !ttl.ForceLiteralLink())		// ignore ctgs which have their own html builder, unless it is literal; EX: [[:Category:A]]; DATE:2014-02-24
			||	ns.Id_is_special()								// ignore special, especially Search; EX: Special:Search/Earth
			||	ttl.Anch_bgn() == Anch_bgn_anchor_only			// anchor only link; EX: [[#anchor]]
			||	ttl.Wik_itm() != null							// xwiki lnki; EX: simplewiki links in homewiki; [[simplewiki:Earth]]
			)
			return;				
		list.Add(lnki);
	}
	public void	Clear() {
		lnki_idx = gplx.xowa.htmls.core.wkrs.lnkis.htmls.Xoh_lnki_wtr.Lnki_id_min;	// NOTE: must start at 0, so that ++lnki_idx is > 0; html_wtr checks for > 0; DATE:2014-10-09; OLD_COMMENT: NOTE: should be 0, but for historical reasons, 1st lnki starts at 2; EX: id='xowa_lnki_2'
		list.Clear();
	}

	public static final String Lnki_id_prefix = "xolnki_";
	private static final int Anch_bgn_anchor_only = 1;	// signifies lnki which is only anchor; EX: [[#anchor]]
}
