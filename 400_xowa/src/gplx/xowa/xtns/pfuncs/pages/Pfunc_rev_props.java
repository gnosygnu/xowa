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
package gplx.xowa.xtns.pfuncs.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.pages.*;
public class Pfunc_rev_props extends Pf_func_base {
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {
		byte[] argx = Eval_argx(ctx, src, caller, self);
		Xopg_revision_data rev_data = ctx.Cur_page().Revision_data();
		switch (id) {
			case Xol_kwd_grp_.Id_page_id:
			case Xol_kwd_grp_.Id_rev_id:	bfr.Add_int_variable(ctx.Cur_page().Revision_data().Id()); break;	// NOTE: making rev_id and page_id interchangeable; XOWA does not store rev_id
			case Xol_kwd_grp_.Id_rev_user:	bfr.Add(rev_data.User()); break;
			case Xol_kwd_grp_.Id_rev_pagesize:
				if (argx.length > 0) {
					Xoa_ttl argx_ttl = Xoa_ttl.parse_(ctx.Wiki(), argx);
					if (argx_ttl == null) {	// invalid ttl; EX: {{PAGESIZE:{{{bad}}}}}
						bfr.Add_byte(Byte_ascii.Num_0);
						return;
					}
					Xoae_page argx_page = ctx.Wiki().Data_mgr().Get_page(argx_ttl, false);
					if (!argx_page.Missing()) {
						bfr.Add_int_variable(argx_page.Data_raw().length);
						return;
					}
				}
				bfr.Add_byte(Byte_ascii.Num_0);
				break;
			case Xol_kwd_grp_.Id_rev_protectionlevel: bfr.Add(rev_data.Protection_level()); break;
			default: throw Err_.unhandled(id);
		}
	}
	public Pfunc_rev_props(int id) {this.id = id;}
	@Override public int Id() {return id;} private int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_rev_props(id).Name_(name);}
	public static final Pfunc_rev_props _ = new Pfunc_rev_props(-1);
}
