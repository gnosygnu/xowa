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
package gplx.xowa.xtns.new_window_links; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.langs.kwds.*; import gplx.xowa.xtns.pfuncs.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;	
import gplx.core.net.*;
public class New_window_link_func extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_new_window_link;}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public Pf_func New(int id, byte[] name) {return new New_window_link_func().Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] title = Eval_argx(ctx, src, caller, self);
		int self_args_len = self.Args_len();
		byte[] caption = Pf_func_.Eval_arg_or(ctx, src, caller, self, self_args_len, 0, null);
		Gfo_url url = url_parser.Parse(title);
		if (url.Protocol_tid() == Gfo_protocol_itm.Tid_unknown) {
			bfr.Add_str_a7("[[");
			bfr.Add(title);
			if (caption != null) {
				bfr.Add_byte_pipe();
				bfr.Add(caption);
			}
//				bfr.Add_str_a7("|target=_blank");
			bfr.Add_str_a7("]]");
		}
		else {
			if (caption == null)
				bfr.Add(title);
			else {
				bfr.Add_str_a7("[");
				bfr.Add(title);
				bfr.Add_byte_space();
				bfr.Add(caption);
				bfr.Add_str_a7("]");
			}
		}
	}
	private static final Gfo_url_parser url_parser = new Gfo_url_parser();
}
