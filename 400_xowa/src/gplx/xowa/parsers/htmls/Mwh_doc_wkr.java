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
package gplx.xowa.parsers.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public interface Mwh_doc_wkr extends Mwh_atr_wkr {
	Hash_adp_bry Nde_regy();
	void On_txt_end		(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end);
	void On_nde_head_bgn(Mwh_doc_parser mgr, byte[] src, int nde_tid, int key_bgn, int key_end);
	void On_nde_head_end(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end, boolean inline);
	void On_nde_tail_end(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end);
	void On_comment_end (Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end);
	void On_entity_end	(Mwh_doc_parser mgr, byte[] src, int nde_tid, int itm_bgn, int itm_end);
}
