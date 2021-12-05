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
package gplx.xowa.xtns.wbases.claims.itms;

import gplx.Bry_;
import gplx.objects.strings.AsciiByte;
import gplx.Err_;
import gplx.Int_;
import gplx.String_;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_visitor;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_entity_type_;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_type_;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_value_type_;

public class Wbase_claim_entity extends Wbase_claim_base {
	public Wbase_claim_entity(int pid, byte snak_tid, byte entityType, byte[] numericIdBry) {
		this(pid, snak_tid, entityType, numericIdBry, null);
	}
	public Wbase_claim_entity(int pid, byte snak_tid, byte entityType, byte[] numericIdBry, byte[] id) {
		super(pid, snak_tid);
		this.entityType = entityType;
		this.numericIdBry = numericIdBry;
		// NOTE: form and sense claims do not have `numeric-id`; DATE:2020-07-27
		if (numericIdBry != null)
			this.numericId = Bry_.To_int(numericIdBry);
		// NOTE: item, property, lexeme do not have an id (Make_claims calls don't pass them)
		this.id = id == null ? ToId(entityType, numericIdBry) : id;
	}
	@Override public byte   Val_tid()           {return Wbase_claim_type_.Tid__entity;}
	public byte[]           Id()                {return id;} private final byte[] id;       // EX: Q123
	public int              Entity_id()         {return numericId;} private int numericId;  // EX: 123
	public byte[]           Entity_id_bry()     {return numericIdBry;} private final byte[] numericIdBry;
	public byte             Entity_tid()        {return entityType;} private final byte entityType;
	public boolean          Entity_tid_is_qid() {return entityType == Wbase_claim_entity_type_.Tid__item;}
	public String           Entity_tid_str()    {return Wbase_claim_entity_type_.Reg.Get_str_or_fail(entityType);}
	public byte[]           Entity_tid_bry()    {return Wbase_claim_entity_type_.Reg.Get_bry_or_fail(entityType);}
	public byte[]           Page_ttl_db()       {return To_xid__db(entityType, numericIdBry);}
	public byte[]           Page_ttl_gui()      {return Bry_.Add(ToTtlPrefix(entityType), numericIdBry);}
	@Override public void Welcome(Wbase_claim_visitor visitor) {visitor.Visit_entity(this);}
	@Override public String toString() {// TEST:
		return String_.Concat_with_str("|", Wbase_claim_value_type_.Reg.Get_str_or_fail(this.Snak_tid()), Wbase_claim_type_.Reg.Get_str_or_fail(this.Val_tid()), this.Entity_tid_str(), Int_.To_str(numericId), String_.new_u8(id));
	}

	public static byte[] To_xid__db(byte tid, byte[] bry) {return Bry_.Add(ToTtlPrefix(tid), bry);}	// EX: 'item,2' -> Q2; 'property,2' -> Property:P2
	private static byte[] ToTtlPrefix(byte entityType) {
		switch (entityType) {
			case Wbase_claim_entity_type_.Tid__item:
				return TTL_PREFIX_QID;
			case Wbase_claim_entity_type_.Tid__property:
				return TTL_PREFIX_PID;
			case Wbase_claim_entity_type_.Tid__lexeme:
				return TTL_PREFIX_LID;
			default:
				throw Err_.new_unhandled_default(entityType);
		}
	}
	private static byte[] ToId(byte entityType, byte[] numericId) {
		switch (entityType) {
			case Wbase_claim_entity_type_.Tid__item:
				return Bry_.Add(AsciiByte.Ltr_Q, numericId);
			case Wbase_claim_entity_type_.Tid__property:
				return Bry_.Add(AsciiByte.Ltr_P, numericId);
			case Wbase_claim_entity_type_.Tid__lexeme:
				return Bry_.Add(AsciiByte.Ltr_L, numericId);
			case Wbase_claim_entity_type_.Tid__form:
			case Wbase_claim_entity_type_.Tid__sense:
			default:
				throw Err_.new_unhandled_default(entityType);
		}
	}
	private static final byte[]
	  TTL_PREFIX_QID      = Bry_.new_a7("Q") // NOTE: use uppercase Q for writing html; DATE:2015-06-12
	, TTL_PREFIX_PID      = Bry_.new_a7("Property:P")
	, TTL_PREFIX_LID      = Bry_.new_a7("Lexeme:L")
	// TOMBSTONE: TTL_PREFIX_QID_OLD  = Bry_.new_a7("q") // NOTE: for historical reasons this is standardized as lowercase q not Q; DATE:2015-06-12
	;
}
