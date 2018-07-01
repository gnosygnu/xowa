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
package gplx.gfml; import gplx.*;
import org.junit.*;
public class z601_edit_atr_tst {
	GfmlUpdateFx fx = GfmlUpdateFx.new_();
	@Test  public void Basic() {
		fx	.Raw_("a=1;").Update_(fx.atr_().NdeIdxs_(0).Atr_("a", "2"))
			.tst_("a=2;");
	}
	@Test  public void WhiteSpaceComment() {
		fx	.Raw_("a = /*comment*/1;").Update_(fx.atr_().NdeIdxs_(0).Atr_("a", "2"))
			.tst_("a = /*comment*/2;");
	}
	@Test  public void Quoted() {
		fx	.Raw_("a='1';").Update_(fx.atr_().NdeIdxs_(0).Atr_("a", "2"))
			.tst_("a='2';");
	}
	@Test  public void EmbeddedQuote() {
		fx	.Raw_("a=1;").Update_(fx.atr_().NdeIdxs_(0).Atr_("a", "1'2"))
			.tst_("a='1''2';");
	}
	@Test  public void ReuseQuote() {
		fx	.Raw_("a=|'1'|;").Update_(fx.atr_().NdeIdxs_(0).Atr_("a", "2"))
			.tst_("a=|'2'|;");
	}
	@Test  public void ReuseQuoteWithEmbeddedQuote() {
		fx	.Raw_("a=|'1'2'|;").Update_(fx.atr_().NdeIdxs_(0).Atr_("a", "2'3"))
			.tst_("a=|'2'3'|;");
	}
//		@Test  public void AtrIdx() {
//			fx	.Raw_("1;").Update_(fx.atr_().NdeIdxs_(0).AtrIdx_(0, "2"))
//				.tst_("2;");
//		}
//		@Test  public void AtrIdx_parens() {
//			fx	.Raw_("A_('1');").Update_(fx.atr_().NdeIdxs_(0).AtrIdx_(0, "2"))
//				.tst_("A_('2');");
//		}
	@Test  public void AddNew() {
		fx	.Raw_("a:;").Update_(fx.atr_().NdeIdxs_(0).Atr_("b", "1"))
			.tst_("a:b='1';");
	}
	@Test  public void AddExisting() {
		fx	.Raw_("a='1';").Update_(fx.atr_().NdeIdxs_(0).Atr_("b", "2"))
			.tst_("a='1' b='2';");
	}
	@Test  public void Add_many() {
		fx	.Raw_("a='1';").Update_(fx.atr_().NdeIdxs_(0).Atr_("b", "2")).Update_(fx.atr_().NdeIdxs_(0).Atr_("c", "3"))
			.tst_("a='1' b='2' c='3';");
	}
	@Test  public void AddNode() {
		fx	.Raw_("a(){}").Update_(fx.nde_().NdeIdxs_(0).Nest_("b"))
			.tst_("a(){b(){}}");
	}
	@Test  public void AddNodeMany() {
		fx	.Raw_("a(){}").Update_(fx.nde_().NdeIdxs_(0).Nest_("b")).Update_(fx.nde_().NdeIdxs_(0).Nest_("c"))
			.tst_("a(){b(){}c(){}}");
	}
	@Test  public void EditNode() {
		fx	.Raw_("a(){b(){}}").Update_(fx.nde_().NdeIdxs_(0).Nest_("b"))
			.tst_("a(){b(){}}");
	}
}
interface GfmlUpdateCmd {
	void Exec(GfmlDoc gdoc);
}
class GfmlUpdateCmd_atr implements GfmlUpdateCmd {
	public GfmlUpdateCmd_atr NdeIdxs_(int... v) {ndeIdxs = v; return this;} int[] ndeIdxs;
	public GfmlUpdateCmd_atr Atr_(String key, String val) {atrKey = key; atrVal = val; return this;}	String atrKey, atrVal;
//		public GfmlUpdateCmd_atr AtrIdx_(int idx, String val) {atrIdx = idx; atrVal = val; return this;} int atrIdx = -1;
	public void Exec(GfmlDoc gdoc) {
		GfmlNde nde = GetNde(ndeIdxs, gdoc.RootNde());
		nde.UpdateAtr(atrKey, atrVal);
//			GfmlAtr atr = atrIdx != -1 ? GfmlAtr.as_(nde.SubKeys().Get_at(atrIdx)) : GfmlAtr.as_(nde.SubKeys().Get_by(atrKey));
//			atr.UpdateAtr(atrKey, atrVal);
		atrKey = ""; atrVal = "";//atrIdx = -1; 			
	}
	public static GfmlNde GetNde(int[] ndeIdxs, GfmlNde owner) {
		GfmlNde nde = owner;
		for (int i = 0; i < ndeIdxs.length; i++) {
			int ndeIdx = ndeIdxs[i];
			nde = (GfmlNde)owner.SubHnds().Get_at(ndeIdx);
		}
		return nde;
	}
        public static GfmlUpdateCmd_atr new_() {return new GfmlUpdateCmd_atr();} GfmlUpdateCmd_atr() {}
}
class GfmlUpdateCmd_nde implements GfmlUpdateCmd {
	public GfmlUpdateCmd_nde NdeIdxs_(int... v) {ndeIdxs = v; return this;} int[] ndeIdxs;
	public GfmlUpdateCmd_nde Nest_(String hnd) {ndeHnd = hnd; return this;}	String ndeHnd;
	public void Exec(GfmlDoc gdoc) {
		GfmlNde nde = GfmlUpdateCmd_atr.GetNde(ndeIdxs, gdoc.RootNde());
		nde.UpdateNde(ndeHnd);
		ndeHnd = "";
	}
        public static GfmlUpdateCmd_nde new_() {return new GfmlUpdateCmd_nde();} GfmlUpdateCmd_nde() {}
}
class GfmlUpdateFx {
	public GfmlUpdateCmd_atr atr_() {return GfmlUpdateCmd_atr.new_();}
	public GfmlUpdateCmd_nde nde_() {return GfmlUpdateCmd_nde.new_();}
	public String Raw() {return raw;} public GfmlUpdateFx Raw_(String v) {raw = v; return this;} private String raw;
	public GfmlUpdateFx Update_(GfmlUpdateCmd cmd) {cmds.Add(cmd); return this;} List_adp cmds = List_adp_.New();
	public GfmlUpdateFx tst_(String expd) {
		GfmlDoc actlDoc = GfmlDataNde.new_any_eol_(raw).Doc();
		for (int i = 0; i < cmds.Count(); i++) {
			GfmlUpdateCmd cmd = (GfmlUpdateCmd)cmds.Get_at(i);
			cmd.Exec(actlDoc);
		}
		String actl = actlDoc.RootNde().To_str();
		Tfds.Eq(expd, actl);
		cmds.Clear();
		return this;
	}
        public static GfmlUpdateFx new_() {return new GfmlUpdateFx();} GfmlUpdateFx() {}
}
