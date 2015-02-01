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
package gplx.xowa.xtns.wdatas.pfuncs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.core.primitives.*;
public class Wdata_pf_property_data {
	public byte[] Of() {return of;} private byte[] of;
	public byte[] Q() {return q;} private byte[] q;
	public byte[] Id() {return id;} private byte[] id;
	public int Id_int() {return id_int;} private int id_int;
	public void Init_by_parse(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Wdata_pf_property pfunc) {
		byte[] id = pfunc.Eval_argx(ctx, src, caller, self);
		Init_by_parse(ctx, src, caller, self, pfunc, id);
	}
	public void Init_by_parse(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Wdata_pf_property pfunc, byte[] id) {
		this.id = id;
		id_int = Wdata_pf_property.Parse_pid(ctx.App().Utl_num_parser(), id);
		if (id_int == Wdata_wiki_mgr.Pid_null) {}	// named; TODO: get pid from pid_regy
		int args_len = self.Args_len();
		Bry_bfr tmp_bfr = ctx.Wiki().Utl_bry_bfr_mkr().Get_b512();
		for (int i = 0; i < args_len; i++) {
			Arg_nde_tkn nde = self.Args_get_by_idx(i);
			Arg_itm_tkn nde_key = nde.Key_tkn();
			int nde_key_bgn = nde_key.Src_bgn(), nde_key_end = nde_key.Src_end();
			if (nde_key_bgn == nde_key_end && nde_key_bgn == -1) continue;	// null arg; ignore, else will throw warning below; EX: {{#property:p1|}}; DATE:2013-11-15
			Object o = Atr_keys.Get_by_mid(src, nde_key_bgn, nde_key_end);
			if (o == null) {
				ctx.App().Usr_dlg().Warn_many("", "", "unknown key for property: ~{0} ~{1}", String_.new_utf8_(ctx.Cur_page().Ttl().Full_txt()), String_.new_utf8_(src, self.Src_bgn(), self.Src_end())); 
				continue;
			}
			nde.Val_tkn().Tmpl_evaluate(ctx, src, self, tmp_bfr);
			byte[] val = tmp_bfr.Xto_bry_and_clear();
			byte key_tid = ((Byte_obj_val)o).Val(); 
			switch (key_tid) {
				case Atr_of_id: of = val; break;
				case Atr_q_id:  q = val; break;
				default: throw Err_.unhandled(key_tid);
			}
		}
		tmp_bfr.Mkr_rls();	
	}
	static final byte Atr_of_id = 1, Atr_q_id = 2;
	private static final byte[] Atr_of_bry = Bry_.new_ascii_("of"), Atr_q_bry = Bry_.new_ascii_("q");
	private static final Hash_adp_bry Atr_keys = Hash_adp_bry.ci_ascii_().Add_bry_byte(Atr_of_bry, Atr_of_id).Add_bry_byte(Atr_q_bry, Atr_q_id);
} 
