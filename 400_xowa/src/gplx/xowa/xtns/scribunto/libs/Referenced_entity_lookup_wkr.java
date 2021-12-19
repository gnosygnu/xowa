/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.scribunto.libs;
import gplx.types.errs.Err;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.Xoa_url;
import gplx.xowa.xtns.wbases.Wdata_doc;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_grp;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_type_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_base;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_entity;
import gplx.xowa.xtns.wbases.stores.Wbase_doc_mgr;
// REF: https://github.com/wmde/WikibaseDataModelServices/blob/master/src/Lookup/EntityRetrievingClosestReferencedEntityIdLookup.php
class Referenced_entity_lookup_wkr {		
	private final Wbase_doc_mgr entity_mgr;
	private final int maxDepth;
	private final int maxEntityVisits;
	private final Xoa_url url;
	private final byte[] fromId;
	private final int propertyId;
	private final Ordered_hash toIds;
	private final Ordered_hash alreadyVisited = Ordered_hash_.New_bry();
	private final List_adp tmp_snak_list = List_adp_.New();
	public Referenced_entity_lookup_wkr(int maxDepth, int maxEntityVisits, Wbase_doc_mgr entity_mgr, Xoa_url url, byte[] fromId, int propertyId, Ordered_hash toIds) {
		this.maxDepth = maxDepth;
		this.maxEntityVisits = maxEntityVisits;
		this.entity_mgr = entity_mgr;
		this.url = url;
		this.fromId = fromId;
		this.propertyId = propertyId;
		this.toIds = toIds;
	}
	public byte[] Get_referenced_entity() {
		if (toIds == null)
			return null;

		int steps = this.maxDepth + 1;// Add one as checking $fromId already is a step
		Ordered_hash toVisit = Ordered_hash_.New_bry();
		toVisit.AddAsKeyAndVal(fromId);

		while (steps-- > 0) {
			// $this->entityPrefetcher->prefetch( $toVisit );
			Ordered_hash toVisitNext = Ordered_hash_.New_bry();
			int toVisitLen = toVisit.Len();
			for (int i = 0; i < toVisitLen; i++) {
				byte[] curId = (byte[])toVisit.GetAt(i);
				byte[] result = processEntityById(alreadyVisited, curId, fromId, propertyId, toIds, toVisitNext);
				if (result != null)
					return result;
			}
			// Remove already visited entities
			toVisit = merge(toVisitNext, alreadyVisited);
			if (toVisit.Len() == 0)
				return null;
		}
		// Exhausted the max. depth without finding anything.
		throw newErr(true);
	}
	private Err newErr(boolean isMaxDepthOrMaxEntities) {
		return ErrUtl.NewArgs("max exceeded", "type", isMaxDepthOrMaxEntities ? "depth" : "entities", "url", url.To_bry(true, false), "fromId", fromId, "propertyId", propertyId, "toIds", toString(toIds));
	}
	private static String toString(Ordered_hash hash) {
		BryWtr bfr = BryWtr.New();
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			bfr.AddStrU8(ObjectUtl.ToStrOrNullMark(hash.GetAt(i))).AddBytePipe();
		}
		return bfr.ToStrAndClear();
	}
	private byte[] processEntityById(Ordered_hash alreadyVisited, byte[] id, byte[] fromId, int propertyId, Ordered_hash toIds, Ordered_hash toVisit) {
		Wdata_doc entity = getEntity(alreadyVisited, id, fromId, propertyId, toIds);
		if (entity == null)
			return null;
		Wbase_claim_base[] mainSnaks = getMainSnaks(entity, propertyId);
		for (Wbase_claim_base mainSnak : mainSnaks) {
			byte[] result = processSnak(mainSnak, toVisit, toIds);
			if (result != null)
				return result;
		}
		return null;
	}
	private Wdata_doc getEntity(Ordered_hash alreadyVisited, byte[] id, byte[] fromId, int propertyId, Ordered_hash toIds) {
		if (alreadyVisited.Has(id)) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "Entity " + StringUtl.NewU8(id) + " already visited");
			return null;
		}
		alreadyVisited.AddAsKeyAndVal(id);
		if (alreadyVisited.Len() > maxEntityVisits)
			throw newErr(false);
		return entity_mgr.Get_by_loose_id_or_null(id);
	}
	private Wbase_claim_base[] getMainSnaks(Wdata_doc entity, int propertyId) {
		Wbase_claim_grp claims = entity.Get_claim_grp_or_null(propertyId);
		if (claims == null) return Wbase_claim_base.Ary_empty;
		return claims.Get_best(tmp_snak_list);
	}
	private byte[] processSnak(Wbase_claim_base snak, Ordered_hash toVisit, Ordered_hash toIds) {
		if (snak.Val_tid() != Wbase_claim_type_.Tid__entity)
			return null;
		Wbase_claim_entity snakEntity = (Wbase_claim_entity)snak;
		byte[] entityId = snakEntity.Page_ttl_db();
		if (toIds.Has(entityId))
			return entityId;
		toVisit.AddAsKeyAndVal(entityId);
		return null;
	}

	private Ordered_hash merge(Ordered_hash toVisitNext, Ordered_hash alreadyVisited) {
		int len = alreadyVisited.Len();
		for (int i = 0; i < len; i++) {
			byte[] bry = (byte[])alreadyVisited.GetAt(i);
			if (toVisitNext.Has(bry))
				toVisitNext.Del(bry);
		}
		return toVisitNext;
	}
}
