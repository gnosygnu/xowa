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
package gplx.xowa.wmfs.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
import gplx.dbs.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.wikis.xwikis.*;
public class Site_core_db {
	private Db_conn conn;
	private final Site_core_tbl tbl__core;
	private final Site_kv_tbl tbl__general;
	private final Site_namespace_tbl tbl__namespace;
	private final Site_statistic_tbl tbl__statistic;
	private final Site_interwikimap_tbl tbl__interwikimap;
	private final Site_namespacealias_tbl tbl__namespacealias;
	private final Site_specialpagealias_tbl tbl__specialpagealias;
	private final Site_library_tbl tbl__library;
	private final Site_extension_tbl tbl__extension;
	private final Site_skin_tbl tbl__skin;
	private final Site_magicword_tbl tbl__magicword;
	private final Site_val_tbl tbl__functionhook;
	private final Site_showhook_tbl tbl__showhook;
	private final Site_val_tbl tbl__extensiontag;
	private final Site_val_tbl tbl__protocol;
	private final Site_kv_tbl tbl__defaultoption;
	private final Site_language_tbl tbl__language;
	private final Db_tbl[] tbl_ary;
	public Site_core_db(Io_url db_url) {
		Db_conn_bldr_data conn_data = Db_conn_bldr.I.Get_or_new(db_url);
		this.conn = conn_data.Conn(); boolean created = conn_data.Created();
		this.tbl__core = new Site_core_tbl(conn);
		this.tbl__general = new Site_kv_tbl(conn, "site_general");
		this.tbl__namespace = new Site_namespace_tbl(conn);
		this.tbl__statistic = new Site_statistic_tbl(conn);
		this.tbl__namespacealias = new Site_namespacealias_tbl(conn);
		this.tbl__interwikimap = new Site_interwikimap_tbl(conn);
		this.tbl__specialpagealias = new Site_specialpagealias_tbl(conn);
		this.tbl__library = new Site_library_tbl(conn);
		this.tbl__extension = new Site_extension_tbl(conn);
		this.tbl__skin = new Site_skin_tbl(conn);
		this.tbl__magicword = new Site_magicword_tbl(conn);
		this.tbl__functionhook = new Site_val_tbl(conn, "site_functionhook");
		this.tbl__showhook = new Site_showhook_tbl(conn);
		this.tbl__extensiontag = new Site_val_tbl(conn, "site_extensiontag");
		this.tbl__protocol = new Site_val_tbl(conn, "site_protocol");
		this.tbl__defaultoption = new Site_kv_tbl(conn, "site_defaultoption");
		this.tbl__language = new Site_language_tbl(conn);
		this.tbl_ary = new Db_tbl[]
		{ tbl__core, tbl__general, tbl__namespace, tbl__statistic, tbl__interwikimap, tbl__namespacealias, tbl__specialpagealias, tbl__library
		, tbl__extension, tbl__skin, tbl__magicword, tbl__functionhook, tbl__showhook, tbl__extensiontag, tbl__protocol, tbl__defaultoption, tbl__language
		};
		if (created) Db_tbl_.Create_tbl(tbl_ary);	// NOTE: each table also creates index; will be "slower", but logic is simpler for updates
	}
	public Site_core_tbl		Tbl__core() {return tbl__core;}
	public Site_namespace_tbl	Tbl__namespace() {return tbl__namespace;}
	public void Rls() {Db_tbl_.Rls(tbl_ary);}
	public void Save(Site_meta_itm site_meta, byte[] site_abrv) {
		conn.Txn_bgn("site_meta");
		tbl__general.Insert(site_abrv, site_meta.General_list());
		tbl__statistic.Insert(site_abrv, site_meta.Statistic_itm());
		tbl__namespace.Insert(site_abrv, site_meta.Namespace_list());
		tbl__interwikimap.Insert(site_abrv, site_meta.Interwikimap_list());
		tbl__namespacealias.Insert(site_abrv, site_meta.Namespacealias_list());
		tbl__specialpagealias.Insert(site_abrv, site_meta.Specialpagealias_list());
		tbl__library.Insert(site_abrv, site_meta.Library_list());
		tbl__extension.Insert(site_abrv, site_meta.Extension_list());
		tbl__skin.Insert(site_abrv, site_meta.Skin_list());
		tbl__magicword.Insert(site_abrv, site_meta.Magicword_list());
		tbl__showhook.Insert(site_abrv, site_meta.Showhook_list());
		tbl__functionhook.Insert(site_abrv, site_meta.Functionhook_list());
		tbl__extensiontag.Insert(site_abrv, site_meta.Extensiontag_list());
		tbl__protocol.Insert(site_abrv, site_meta.Protocol_list());
		tbl__defaultoption.Insert(site_abrv, site_meta.Defaultoption_list());
		tbl__language.Insert(site_abrv, site_meta.Language_list());
		tbl__core.Update(site_abrv, Bool_.Y);
		conn.Txn_end();
	}
	public void Load(Site_meta_itm site_meta, byte[] site_abrv) {
		tbl__general.Select(site_abrv, site_meta.General_list());
		tbl__statistic.Select(site_abrv, site_meta.Statistic_itm());
		tbl__namespace.Select(site_abrv, site_meta.Namespace_list());
		tbl__interwikimap.Select(site_abrv, site_meta.Interwikimap_list());
		tbl__namespacealias.Select(site_abrv, site_meta.Namespacealias_list());
		tbl__specialpagealias.Select(site_abrv, site_meta.Specialpagealias_list());
		tbl__library.Select(site_abrv, site_meta.Library_list());
		tbl__extension.Select(site_abrv, site_meta.Extension_list());
		tbl__skin.Select(site_abrv, site_meta.Skin_list());
		tbl__magicword.Insert(site_abrv, site_meta.Magicword_list());
		tbl__showhook.Select(site_abrv, site_meta.Showhook_list());
		tbl__functionhook.Select(site_abrv, site_meta.Functionhook_list());
		tbl__extensiontag.Select(site_abrv, site_meta.Extensiontag_list());
		tbl__protocol.Select(site_abrv, site_meta.Protocol_list());
		tbl__defaultoption.Select(site_abrv, site_meta.Defaultoption_list());
		tbl__language.Select(site_abrv, site_meta.Language_list());
	}
	public Xow_ns_mgr Load_namespace(byte[] domain_bry) {
		Xow_ns_mgr rv = new Xow_ns_mgr(gplx.xowa.langs.cases.Xol_case_mgr_.U8());
		Ordered_hash hash = Ordered_hash_.new_();
		tbl__namespace.Select(Xow_abrv_xo_.To_bry(domain_bry), hash);
		Ns_mgr__load(rv, hash);
		return rv;
	}
	public void Load_interwikimap(Xow_domain_itm domain_itm, gplx.xowa.wikis.xwikis.Xow_xwiki_mgr xwiki_mgr) {
		Ordered_hash hash = Ordered_hash_.new_();
		tbl__interwikimap.Select(domain_itm.Abrv_xo(), hash);
		int len = hash.Count();
		for (int i = 0; i < len; ++i)  {
			Site_interwikimap_itm itm = (Site_interwikimap_itm)hash.Get_at(i);
			Xow_xwiki_itm xwiki_itm = Xow_xwiki_itm_bldr.I.Bld(domain_itm, itm.Prefix(), itm.Url(), null);
			xwiki_mgr.Add_itm(xwiki_itm);
		}
	}
	public void Load_extensiontag(Xow_domain_itm domain_itm, gplx.xowa.parsers.xndes.Xop_xnde_tag_regy xnde_tag_regy) {
		try {
//				Ordered_hash tag_hash = Ordered_hash_.new_();
//				tbl__extensiontag.Select(domain_itm.Abrv_xo(), tag_hash);
//				Hash_adp_bry key_hash = To_key_hash(tag_hash);
//				xnde_tag_regy.Init_by_meta(key_hash);
			xnde_tag_regy.Init_by_meta(null);
		}
		catch (Exception e) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "failed to load extensiontag; wiki=~{0} err=~{1}", domain_itm.Domain_str(), Err_.Message_gplx_full(e));
		}
	}
//		private static Hash_adp_bry To_key_hash(Ordered_hash tag_hash) {
//			Hash_adp_bry rv = Hash_adp_bry.ci_a7();	// ASCII: assume all xtn tags do not have non-ASCII chars
//			int len = tag_hash.Count(); if (len == 0) return null;
//			for (int i = 0; i < len; ++i) {
//				byte[] tag = (byte[])tag_hash.Get_at(i);
//				int idx_last = tag.length - 1;
//				if (	tag.length < 3
//					||	tag[0]			!= Byte_ascii.Angle_bgn
//					||	tag[idx_last]	!= Byte_ascii.Angle_end
//					)
//					throw Err_.new_("site_meta", "invalid extensiontag", "tag", tag);
//				byte[] key = Bry_.Mid(tag, 1, idx_last);
//				rv.Add(key, key);
//			}
//			return rv;
//		}
	private static void Ns_mgr__load(Xow_ns_mgr rv, Ordered_hash hash) {
		rv.Clear();
		int len = hash.Count();
		for (int i = 0; i < len; ++i) {
			Site_namespace_itm itm = (Site_namespace_itm)hash.Get_at(i);
			byte case_match = Xow_ns_case_.parse(String_.new_u8(itm.Case_tid()));
			rv.Add_new(itm.Id(), itm.Localized(), case_match, Bool_.N);
		}
	}
}
