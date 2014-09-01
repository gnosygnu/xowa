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
package gplx.xowa.xtns.wdatas; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.json.*;
public class Wdata_doc_parser {
	public Wdata_doc_parser(Gfo_usr_dlg usr_dlg) {this.usr_dlg = usr_dlg;} private Gfo_usr_dlg usr_dlg;
	public OrderedHash Bld_hash(Json_doc doc, byte[] key) {
		Json_itm_nde nde = Json_itm_nde.cast_(doc.Get_grp(key)); if (nde == null) return Empty_ordered_hash_bry;
		OrderedHash rv = OrderedHash_.new_bry_();
		int len = nde.Subs_len();
		for (int i = 0; i < len; i++) {
			Json_itm_kv kv = Json_itm_kv.cast_(nde.Subs_get_at(i));
			byte[] kv_key = kv.Key().Data_bry();
			rv.Add(kv_key, kv);
		}
		return rv;
	}
	public OrderedHash Bld_props(Json_doc doc) {
		Json_itm_ary claims_nde = Json_itm_ary.cast_(doc.Get_grp(Wdata_doc_consts.Key_atr_claims_bry)); if (claims_nde == null) return Empty_ordered_hash_generic;
		return Bld_props(doc.Src(), claims_nde);
	}
	public OrderedHash Bld_props(byte[] src, Json_itm_ary claims_nde) {
		OrderedHash hash = OrderedHash_.new_();
		int len = claims_nde.Subs_len();
		for (int i = 0; i < len; i++) {
			Json_itm_nde prop_nde = Json_itm_nde.cast_(claims_nde.Subs_get_at(i));
			Wdata_prop_itm_core prop_itm = New_prop(src, prop_nde);
			Int_obj_ref prop_itm_id = Int_obj_ref.new_(prop_itm.Pid());
			Wdata_prop_grp prop_grp = (Wdata_prop_grp)hash.Fetch(prop_itm_id);
			if (prop_grp == null) {
				prop_grp = new Wdata_prop_grp(prop_itm_id);
				hash.Add(prop_itm_id, prop_grp);
			}
			prop_grp.Itms_add(prop_itm);
		}
		len = hash.Count();
		OrderedHash rv = OrderedHash_.new_();
		for (int i = 0; i < len; i++) {
			Wdata_prop_grp grp = (Wdata_prop_grp)hash.FetchAt(i);
			grp.Itms_make();
			rv.Add(grp.Id_ref(), grp);
		}
		return rv;
	}
	private Wdata_prop_itm_core New_prop(byte[] src, Json_itm_nde prop_nde) {
		int len = prop_nde.Subs_len();	// should have 5 (m, q, g, rank, refs), but don't enforce (can rely on keys)
		Wdata_prop_itm_core rv = null;
		for (int i = 0; i < len; i++) {
			Json_itm_kv kv = Json_itm_kv.cast_(prop_nde.Subs_get_at(i));
			Json_itm kv_key = kv.Key();
			Byte_obj_val bv = (Byte_obj_val)Prop_key_hash.Get_by_mid(src, kv_key.Src_bgn(), kv_key.Src_end());
			if (bv == null) {Warn("invalid prop node: ~{0}", String_.new_utf8_mid_safe_(src, kv_key.Src_bgn(), kv_key.Src_end())); return null;}
			switch (bv.Val()) {
				case Prop_tid_m:
					rv = New_prop_by_m(src, Json_itm_ary.cast_(kv.Val()));
					if (rv == null) return null;
					break;
				case Prop_tid_g:
					rv.Wguid_(kv.Data_bry());
					break;
				case Prop_tid_rank:
					rv.Rank_tid_((byte)Int_.cast_(((Json_itm_int)kv.Val()).Data_as_int()));
					break;
				case Prop_tid_q:
					break;
				case Prop_tid_refs:
					break;
				default:				throw Err_.unhandled(bv.Val());
			}
		}
		return rv;
	}
	private Wdata_prop_itm_core New_prop_by_m(byte[] src, Json_itm_ary ary) {
		byte snak_tid = Wdata_prop_itm_base_.Snak_tid_parse(ary.Subs_get_at(0).Data_bry());
		int pid = Json_itm_int.cast_(ary.Subs_get_at(1)).Data_as_int();
		switch (snak_tid) {
			case Wdata_prop_itm_base_.Snak_tid_novalue		: return Wdata_prop_itm_core.new_novalue_(pid);
			case Wdata_prop_itm_base_.Snak_tid_somevalue	: return Wdata_prop_itm_core.new_somevalue_(pid);
		}
		Json_itm val_tid_itm = ary.Subs_get_at(2);
		byte val_tid = Wdata_prop_itm_base_.Val_tid_parse(src, val_tid_itm.Src_bgn(), val_tid_itm.Src_end());
		byte[] val_bry = Parse_val(ary, val_tid);
		return new Wdata_prop_itm_core(snak_tid, pid, val_tid, val_bry);
	}
	private byte[] Parse_val(Json_itm_ary ary, byte val_tid) {
		switch (val_tid) {
			case Wdata_prop_itm_base_.Val_tid_string:
				return ary.Subs_get_at(3).Data_bry();
			case Wdata_prop_itm_base_.Val_tid_entity: {
				Json_itm_nde sub_nde = Json_itm_nde.cast_(ary.Subs_get_at(3));
				Json_itm_kv entity_kv = Json_itm_kv.cast_(sub_nde.Subs_get_at(1));
				return entity_kv.Val().Data_bry();
			}
			case Wdata_prop_itm_base_.Val_tid_time: {
				Json_itm_nde sub_nde = Json_itm_nde.cast_(ary.Subs_get_at(3));
				Json_itm_kv entity_kv = Json_itm_kv.cast_(sub_nde.Subs_get_at(0));
				return entity_kv.Val().Data_bry();
			}
			case Wdata_prop_itm_base_.Val_tid_globecoordinate: case Wdata_prop_itm_base_.Val_tid_bad: {
				Json_itm_nde sub_nde = Json_itm_nde.cast_(ary.Subs_get_at(3));
				return Add_kvs(tmp_parse_bfr, sub_nde, 0, 2);
			}
			case Wdata_prop_itm_base_.Val_tid_quantity: {
				Json_itm_nde sub_nde = Json_itm_nde.cast_(ary.Subs_get_at(3));
				return Add_kvs(tmp_parse_bfr, sub_nde, 0, 4);
			}
			case Wdata_prop_itm_base_.Val_tid_monolingualtext: {
				Json_itm_nde sub_nde = Json_itm_nde.cast_(ary.Subs_get_at(3));
				return Add_kvs(tmp_parse_bfr, sub_nde, 0, 2);
			}
			default: {throw Err_.unhandled(val_tid);}
		}		
	}
	private Bry_bfr tmp_parse_bfr = Bry_bfr.reset_(32);
	private static byte[] Add_kvs(Bry_bfr bfr, Json_itm_nde sub_nde, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Json_itm_kv kv = Json_itm_kv.cast_(sub_nde.Subs_get_at(i));
			if (i != 0) bfr.Add_byte(Wdata_prop_itm_core.Prop_dlm);
			bfr.Add(kv.Val().Data_bry());
		}
		return bfr.XtoAryAndClear();
	}
	private void Warn(String fmt, Object... args) {usr_dlg.Warn_many("", "", fmt, args);}
	public static final OrderedHash Empty_ordered_hash_bry = OrderedHash_.new_bry_(), Empty_ordered_hash_generic = OrderedHash_.new_();
	private static final byte Prop_tid_m = 0, Prop_tid_q = 1, Prop_tid_g = 2, Prop_tid_rank = 3, Prop_tid_refs = 4;
	private static final Hash_adp_bry Prop_key_hash = Hash_adp_bry.ci_ascii_()
	.Add_bry_byte(Wdata_doc_consts.Key_claims_m_bry		, Prop_tid_m)
	.Add_bry_byte(Wdata_doc_consts.Key_claims_q_bry		, Prop_tid_q)
	.Add_bry_byte(Wdata_doc_consts.Key_claims_g_bry		, Prop_tid_g)
	.Add_bry_byte(Wdata_doc_consts.Key_claims_rank_bry	, Prop_tid_rank)
	.Add_bry_byte(Wdata_doc_consts.Key_claims_refs_bry	, Prop_tid_refs);
}
