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
package gplx.xowa.addons.searchs.searchers.crts.visitors; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.searchs.*; import gplx.xowa.addons.searchs.searchers.*; import gplx.xowa.addons.searchs.searchers.crts.*;
public class Srch_crt_visitor__words implements Srch_crt_visitor {
	private final    List_adp words_list = List_adp_.new_();
	public byte Words_tid() {return words_tid;} private byte words_tid;
	public Srch_crt_itm Words_nth() {return words_nth;} private Srch_crt_itm words_nth;
	public Srch_crt_itm[] Words_ary() {return (Srch_crt_itm[])words_list.To_ary_and_clear(Srch_crt_itm.class);}
	public void Gather(Srch_crt_itm root) {
		words_list.Clear();
		words_tid = Srch_crt_mgr.Tid__ands;
		words_nth = null;
		Visit(root);
		if (words_list.Count() == 1)
			words_tid = Srch_crt_mgr.Tid__one;
	}
	public void Visit(Srch_crt_itm itm) {
		int itm_tid = itm.Tid;
		switch (itm_tid) {
			case Srch_crt_itm.Tid__and:
			case Srch_crt_itm.Tid__or:
				if (itm_tid == Srch_crt_itm.Tid__or)
					words_tid = Srch_crt_mgr.Tid__mixed;
				Srch_crt_itm[] subs = itm.Subs;
				int subs_len = subs.length;
				for (int i = 0; i < subs_len; ++i)
					Visit(subs[i]);
				break;
			case Srch_crt_itm.Tid__word:
			case Srch_crt_itm.Tid__word_quote:
			case Srch_crt_itm.Tid__not:
				if (	itm_tid == Srch_crt_itm.Tid__not
					&&	itm.Subs.length == 1) {
					Srch_crt_itm lone = itm.Subs[0];
					if (	lone.Tid == Srch_crt_itm.Tid__word
						||	lone.Tid == Srch_crt_itm.Tid__word_quote)
						words_nth = lone;
				}
				else
					words_nth = itm;
				words_list.Add(itm);
				break;
			case Srch_crt_itm.Tid__invalid:		break;			// should not happen
			default:								throw Err_.new_unhandled_default(itm.Tid);
		}
	}
}
