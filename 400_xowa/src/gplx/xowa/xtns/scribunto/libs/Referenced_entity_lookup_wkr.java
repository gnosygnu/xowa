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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*; import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.stores.*;
// REF: https://github.com/wmde/WikibaseDataModelServices/blob/master/src/Lookup/EntityRetrievingClosestReferencedEntityIdLookup.php
class Referenced_entity_lookup_wkr {		
	private final    Wbase_doc_mgr entity_mgr;
	private final    int maxDepth;
	private final    int maxEntityVisits;
	private final    Xoa_url url;
	private final    byte[] fromId;
	private final    int propertyId;
	private final    Ordered_hash toIds;
	private final    Ordered_hash alreadyVisited = Ordered_hash_.New_bry();
	private final    List_adp tmp_snak_list = List_adp_.New();
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
		toVisit.Add_as_key_and_val(fromId);

		while (steps-- > 0) {
			// $this->entityPrefetcher->prefetch( $toVisit );
			Ordered_hash toVisitNext = Ordered_hash_.New_bry();
			int toVisitLen = toVisit.Len();
			for (int i = 0; i < toVisitLen; i++) {
				byte[] curId = (byte[])toVisit.Get_at(i);
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
		return Err_.new_wo_type("max exceeded", "type", isMaxDepthOrMaxEntities ? "depth" : "entities", "url", url.To_bry(true, false), "fromId", fromId, "propertyId", propertyId, "toIds", toString(toIds));
	}
	private static String toString(Ordered_hash hash) {
		Bry_bfr bfr = Bry_bfr_.New();
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			bfr.Add_str_u8(Object_.Xto_str_strict_or_null_mark(hash.Get_at(i))).Add_byte_pipe();
		}
		return bfr.To_str_and_clear();
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
			Gfo_usr_dlg_.Instance.Warn_many("", "", "Entity " + String_.new_u8(id) + " already visited");
			return null;
		}
		alreadyVisited.Add_as_key_and_val(id);
		if (alreadyVisited.Len() > maxEntityVisits)
			throw newErr(false);
		return entity_mgr.Get_by_loose_id_or_null(id);
	}
	private Wbase_claim_base[] getMainSnaks(Wdata_doc entity, int propertyId) {
		Wbase_claim_grp claims = entity.Claim_list_get(propertyId);
		return claims.Get_best(tmp_snak_list);
	}
	private byte[] processSnak(Wbase_claim_base snak, Ordered_hash toVisit, Ordered_hash toIds) {
		if (snak.Val_tid() != Wbase_claim_type_.Tid__entity)
			return null;
		Wbase_claim_entity snakEntity = (Wbase_claim_entity)snak;
		byte[] entityId = snakEntity.Page_ttl_db();
		if (toIds.Has(entityId))
			return entityId;
		toVisit.Add_as_key_and_val(entityId);
		return null;
	}

	private Ordered_hash merge(Ordered_hash toVisitNext, Ordered_hash alreadyVisited) {
		int len = alreadyVisited.Len();
		for (int i = 0; i < len; i++) {
			byte[] bry = (byte[])alreadyVisited.Get_at(i);
			if (toVisitNext.Has(bry))
				toVisitNext.Del(bry);
		}
		return toVisitNext;
	}
}
