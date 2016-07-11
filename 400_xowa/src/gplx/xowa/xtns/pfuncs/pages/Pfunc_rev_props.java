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
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.dbs.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_rev_props extends Pf_func_base {
	public Pfunc_rev_props(int id) {this.id = id;}
	@Override public int Id() {return id;} private final    int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_rev_props(id).Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] argx = Eval_argx(ctx, src, caller, self);
		Xopg_db_protection rev_data = ctx.Page().Db().Protection();
		switch (id) {
			case Xol_kwd_grp_.Id_page_id:
			case Xol_kwd_grp_.Id_rev_id:				bfr.Add_int_variable(ctx.Page().Db().Page().Id()); break;	// NOTE: making rev_id and page_id interchangeable; XOWA does not store rev_id
			case Xol_kwd_grp_.Id_rev_user:				bfr.Add(rev_data.User()); break;
			case Xol_kwd_grp_.Id_rev_protectionlevel:	bfr.Add(rev_data.Protection_level()); break;
			case Xol_kwd_grp_.Id_rev_protectionexpiry:	bfr.Add(rev_data.Protection_expiry()); break;
			case Xol_kwd_grp_.Id_rev_revisionsize:	// default revsize to pagesize; MW has additional logic for subst which should not apply to XO; https://gerrit.wikimedia.org/r/#/c/82650/
				bfr.Add_int_variable(ctx.Page().Db().Text().Text_bry().length);
				break;
			case Xol_kwd_grp_.Id_rev_pagesize:
				if (argx.length > 0) {
					Xoa_ttl argx_ttl = Xoa_ttl.Parse(ctx.Wiki(), argx);
					if (argx_ttl == null) {	// invalid ttl; EX: {{PAGESIZE:{{{bad}}}}}
						bfr.Add_byte(Byte_ascii.Num_0);
						return;
					}
					Xoae_page argx_page = ctx.Wiki().Data_mgr().Load_page_by_ttl(argx_ttl);
					if (argx_page.Db().Page().Exists()) {
						bfr.Add_int_variable(argx_page.Db().Text().Text_bry().length);
						return;
					}
				}
				bfr.Add_byte(Byte_ascii.Num_0);
				break;
			default: throw Err_.new_unhandled(id);
		}
	}
	public static final    Pfunc_rev_props Instance = new Pfunc_rev_props(-1);
}
