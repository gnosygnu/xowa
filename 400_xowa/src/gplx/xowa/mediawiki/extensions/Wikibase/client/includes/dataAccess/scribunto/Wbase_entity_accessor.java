/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.mediawiki.extensions.Wikibase.client.includes.dataAccess.scribunto;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.xtns.wbases.Wdata_doc;
import gplx.xowa.xtns.wbases.Wdata_prop_val_visitor_;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_grp;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_rank_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_base;
import gplx.xowa.xtns.wbases.stores.Wbase_doc_mgr;

public class Wbase_entity_accessor {
	private final Wbase_doc_mgr entity_mgr;
	public Wbase_entity_accessor(Wbase_doc_mgr entity_mgr) {
		this.entity_mgr = entity_mgr;
	}
	public Wbase_claim_base[] getEntityStatements(byte[] prefixedEntityId, byte[] propertyIdSerialization, byte[] rank) {
		prefixedEntityId = BryUtl.Trim(prefixedEntityId);
		// entityId = $this->entityIdParser->parse( $prefixedEntityId );
		int propertyId = Wdata_prop_val_visitor_.To_pid_int(propertyIdSerialization);
		// $this->usageAccumulator->addStatementUsage( $entityId, $propertyId );
		// $this->usageAccumulator->addOtherUsage( $entityId );

		// for some reason, prefixedEntityId can be ""; PAGE:en.w:Nature_and_Art DATE:2017-11-28
		if (BryUtl.IsNullOrEmpty(prefixedEntityId))
			return null;

		Wdata_doc entity = null;
		try {
			entity = entity_mgr.Get_by_xid_or_null(prefixedEntityId);
		} catch (Exception ex) { // RevisionedUnresolvedRedirectException ex
			// We probably hit a double redirect
			Gfo_usr_dlg_.Instance.Log_many("", "", "Encountered a UnresolvedRedirectException when trying to load {0}; exc={1}", prefixedEntityId, ErrUtl.Message(ex));
			return null;
		}
		if (entity == null) return null; // must check for null; PAGE:fr.w:Wikipédia:Ateliers_Bases/Recherche ISSUE#:773; DATE:2020-08-04

		int selected_rank = ID_NULL;
		if		(BryLni.Eq(rank, RANK_BEST)) {
			selected_rank = ID_BEST;
		}
		else if (BryLni.Eq(rank, RANK_ALL)) {
			selected_rank = ID_ALL;
		}
		else {
			throw ErrUtl.NewArgs("rank must be 'best' or 'all', " + StringUtl.NewU8(rank) + " given");
		}

		List_adp rv = List_adp_.New();
		Wbase_claim_grp statements = entity.Get_claim_grp_or_null(propertyId);
		if (statements == null)
			return null;

		int statements_len = statements.Len();
		for (int i = 0; i < statements_len; i++) {
			Wbase_claim_base statement = statements.Get_at(i);
			if (	selected_rank == ID_ALL 
				||	(selected_rank == ID_BEST && statement.Rank_tid() == Wbase_claim_rank_.Tid__preferred)
				) {
				rv.Add(statement);
			}
		}

		// no preferred exists; add normal
		if (rv.Len() == 0 && selected_rank == ID_BEST) {
			for (int i = 0; i < statements_len; i++) {
				Wbase_claim_base statement = statements.Get_at(i);
				if (statement.Rank_tid() == Wbase_claim_rank_.Tid__normal) {
					rv.Add(statement);
				}
			}
		}

//			$serialization = $this->newClientStatementListSerializer()->serialize( $statements );
//			$this->renumber( $serialization );
//			return $serialization;
		return rv.Len() == 0 ? null : (Wbase_claim_base[])rv.ToAry(Wbase_claim_base.class);
	}

	private static final int ID_NULL = 0, ID_BEST = 1, ID_ALL = 2;
	private static final byte[]
	  RANK_BEST = BryUtl.NewA7("best")
	, RANK_ALL  = BryUtl.NewA7("all");
}
