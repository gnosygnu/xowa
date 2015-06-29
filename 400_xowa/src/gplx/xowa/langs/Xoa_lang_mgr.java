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
package gplx.xowa.langs; import gplx.*; import gplx.xowa.*;
import gplx.intl.*; 
import gplx.xowa.apps.fsys.*; import gplx.xowa.bldrs.langs.*; import gplx.xowa.langs.vnts.*;
public class Xoa_lang_mgr implements GfoInvkAble {
	private final Xoa_fsys_mgr fsys_mgr; private final Gfo_msg_log msg_log; private final Xol_lang lang_en; 
	private final Ordered_hash hash = Ordered_hash_.new_bry_(); private final Hash_adp_bry fallback_regy = Hash_adp_bry.cs_();
	public Xoa_lang_mgr(Xoae_app app) {
		this.fsys_mgr = app.Fsys_mgr(); this.msg_log = app.Msg_log();
		mw_converter = new Xobc_utl_make_lang(this, fsys_mgr, msg_log);
		lang_en = Lang_en_make(this); this.Add(lang_en);
	}
	public void			Clear() {hash.Clear();}
	public int			Len() {return hash.Count();}
	public void			Add(Xol_lang itm) {hash.Add(itm.Key_bry(), itm);}
	public Xol_lang		Get_at(int i) {return (Xol_lang)hash.Get_at(i);}
	public Xol_lang		Get_by_key(byte[] key) {return (Xol_lang)hash.Get_by(key);}
	public Xol_lang		Get_by_key_or_load(byte[] key) {return Get_by_key_or_new(key).Init_by_load_assert();}
	public Xol_lang		Get_by_key_or_new(byte[] key) {
		Xol_lang rv = Get_by_key(key);
		if (rv == null) {
			rv = new Xol_lang(this, key);
			this.Add(rv);
		}
		return rv;
	}
	public Hash_adp_bry Fallback_regy() {return fallback_regy;}
	public Xol_lang		Lang_en() {return lang_en;}
	public Xobc_utl_make_lang Mw_converter() {return mw_converter;} private Xobc_utl_make_lang mw_converter;
	public Cfg_nde_root Groups() {return groups;} Cfg_nde_root groups = new Cfg_nde_root().Root_(new Xoac_lang_grp(Bry_.Empty), Xoac_lang_grp.Make_grp, Bry_.Ary_empty);
	public byte[]		Default_lang() {return default_lang;} private byte[] default_lang = Xol_lang_.Key_en;
	private void Load_lang(byte[] bry) {this.Get_by_key_or_new(bry).Init_by_load();}
	public void Local_set_bulk(byte[] src) {	// NOTE: setting local lang names/grps on app level; may need to move to user level or wiki level (for groups) later
		int len = src.length;
		int pos = 0, fld_bgn = 0, fld_idx = 0;
		byte[] code = Bry_.Empty, name = Bry_.Empty;
		Xol_csv_parser csv_parser = Xol_csv_parser._;
		while (true) {
			boolean last = pos == len;
			byte b = last ? Byte_ascii.Nl : src[pos];
			switch (b) {
				case Byte_ascii.Pipe:
					switch (fld_idx) {
						case 0:		code = csv_parser.Load(src, fld_bgn, pos); break;
						case 1:		name = csv_parser.Load(src, fld_bgn, pos); break;
						default:	throw Err_.unhandled(fld_idx);
					}
					fld_bgn = pos + 1;
					++fld_idx;
					break;
				case Byte_ascii.Nl:
					byte[] grp = csv_parser.Load(src, fld_bgn, pos);
					Xol_lang_itm itm = Xol_lang_itm_.Get_by_key(code);
					itm.Localized_name_(name, grp);
					fld_bgn = pos + 1;
					fld_idx = 0;
					break;
			}
			if (last) break;
			++pos;
		}
	}
	public void Bld_xowa() {
		Xol_mw_lang_parser lang_parser = new Xol_mw_lang_parser(msg_log);
		lang_parser.Bld_all(this, fsys_mgr);
	}
	public Ordered_hash Xto_hash(byte[] raw) {
		byte[][] keys = Bry_.Split(raw, Byte_ascii.Tilde);
		int len = keys.length;
		Ordered_hash langs = Ordered_hash_.new_();
		Cfg_nde_root lang_root = groups;
		for (int i = 0; i < len; i++) {
			byte[] key = keys[i];
			Cfg_nde_obj lang_grp = lang_root.Grps_get(key);
			if (lang_grp == null) {
				Xol_lang_itm itm = Xol_lang_itm_.Get_by_key(key);
				if (itm == null) throw Err_mgr._.fmt_(GRP_KEY, "invalid_lang", "unknown lang group or key: ~{0}", String_.new_u8(key));
				langs.Add(key, Xoac_lang_grp.Regy_get_or_new(key));
			}
			else
				Cfg_nde_obj_.Fill_recurse(langs, lang_grp);
		}
		return langs;
	}	private static final String GRP_KEY = "xowa.langs";
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))				return Get_by_key_or_new(m.ReadBry("key"));
		else if	(ctx.Match(k, Invk_groups))				return groups;
		else if (ctx.Match(k, Invk_local_set_bulk))		Local_set_bulk(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_load_lang))			Load_lang(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_mediawiki_converter))return mw_converter;
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_get = "get", Invk_local_set_bulk = "local_set_bulk", Invk_load_lang = "load_lang", Invk_groups = "groups", Invk_mediawiki_converter = "mediawiki_converter";
	public static final byte[] Fallback_false = Bry_.new_a7("false");
	public static Xol_lang Lang_en_make(Xoa_lang_mgr lang_mgr) {
		Xol_lang rv = new Xol_lang(lang_mgr, Xol_lang_.Key_en);
		Xol_lang_.Lang_init(rv);
		rv.Evt_lang_changed();
		return rv;
	}
}
