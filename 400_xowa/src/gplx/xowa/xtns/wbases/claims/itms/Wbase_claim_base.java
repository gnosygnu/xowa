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
package gplx.xowa.xtns.wbases.claims.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*;
import gplx.xowa.xtns.wbases.claims.enums.*;
public abstract class Wbase_claim_base implements CompareAble {
	public Wbase_claim_base(int pid, byte snak_tid) {
		this.pid = pid;
		this.snak_tid = snak_tid;
	}
	public int						Pid()				{return pid;} private final    int pid;
	public byte						Snak_tid()			{return snak_tid;} private final    byte snak_tid;
	public byte						Rank_tid()			{return rank_tid;} private byte rank_tid = Wbase_claim_rank_.Tid__normal;	// TEST: default to normal for tests
	public String					Prop_type()			{return Prop_type_statement;} private static final String Prop_type_statement = "statement";
	public byte[]					Wguid()				{return wguid;} private byte[] wguid;
	public Wbase_claim_grp_list		Qualifiers()		{return qualifiers;} private Wbase_claim_grp_list qualifiers;
	public int[]					Qualifiers_order()	{return qualifiers_order;} private int[] qualifiers_order;
	public Wbase_references_grp[]	References()		{return references;} private Wbase_references_grp[] references;
	public abstract byte			Val_tid();
	public abstract void			Welcome(Wbase_claim_visitor visitor);

	public void						Rank_tid_(byte v) {this.rank_tid = v;} 
	public void						Wguid_(byte[] v) {this.wguid = v;} 
	public Wbase_claim_base			Qualifiers_(Wbase_claim_grp_list v) {qualifiers = v; return this;} 
	public void						Qualifiers_order_(int[] v) {qualifiers_order = v;} 
	public void						References_(Wbase_references_grp[] v) {references = v;} 

	public int compareTo(Object obj) {
		Wbase_claim_base comp = (Wbase_claim_base)obj;
		return Int_.Compare(pid, comp.pid);
	}
}
