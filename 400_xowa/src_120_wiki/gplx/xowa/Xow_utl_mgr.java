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
package gplx.xowa; import gplx.*;
import gplx.xowa.parsers.apos.*; import gplx.xowa.parsers.amps.*; import gplx.xowa.parsers.lnkes.*; import gplx.xowa.parsers.paras.*;
public class Xow_utl_mgr {
	public Xow_utl_mgr(Xow_wiki wiki) {this.wiki = wiki;} private Xow_wiki wiki;
	public Xop_parser Anchor_encode_parser() {
		if (anchor_encode_parser == null) {
			anchor_encode_parser = new Xop_parser(wiki, wiki.Parser().Tmpl_lxr_mgr(), Anchor_encode_lxr_mgr);
			anchor_encode_parser.Init_by_wiki(wiki);
			anchor_encode_parser.Init_by_lang(wiki.Lang());
		}
		return anchor_encode_parser;
	}	private Xop_parser anchor_encode_parser;
	private static final Xop_lxr_mgr Anchor_encode_lxr_mgr
		= new Xop_lxr_mgr(new Xop_lxr[] 
		{ Xop_pipe_lxr._, new Xop_eq_lxr(false), Xop_space_lxr._, Xop_tab_lxr._, Xop_nl_lxr._
		, Xop_curly_bgn_lxr._, Xop_curly_end_lxr._
		, Xop_amp_lxr._, Xop_colon_lxr._
		, Xop_apos_lxr._
		, Xop_lnki_lxr_bgn._, Xop_lnki_lxr_end._
		, Xop_lnke_lxr._, Xop_lnke_end_lxr._
		, Xop_xnde_lxr._
		});
}
