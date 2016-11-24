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
package gplx.gfml; import gplx.*;
import org.junit.*;
public class z422_types_apply_ndes_multi_tst {
	@Before public void setup() {
		fx.Regy().Add
			(	fx.typ_().Key_("pos").Subs_
			(		GfmlFld_mok.new_().ini_atr_("x")
			,		GfmlFld_mok.new_().ini_atr_("y")
			).XtoGfmlType());
		fx.Regy().Add
			(	fx.typ_().Key_("size").Subs_
			(		GfmlFld_mok.new_().ini_atr_("w")
			,		GfmlFld_mok.new_().ini_atr_("h")
			).XtoGfmlType());
		fx.Regy().Add
			(	fx.typ_().Key_("rect").Subs_
			(		GfmlFld_mok.new_().ini_ndk_("pos", "pos")
			,		GfmlFld_mok.new_().ini_ndk_("size", "size")
			).XtoGfmlType());
	}	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
	@Test  public void Unnamed() {
		fx.tst_Resolve
		(	fx.nde_().Hnd_("rect").Subs_
			(	fx.nde_().Key_(GfmlTkn_.NullRaw).Atrs_("1")
			,	fx.nde_().Key_(GfmlTkn_.NullRaw).Atrs_("3")
			)
		,	fx.nde_().Typ_("rect").Subs_
			(	fx.nde_().Typ_("pos").Atrk_("x", "1")
			,	fx.nde_().Typ_("size").Atrk_("w", "3")
			)
		);
	}
	@Test  public void Partial() {
		fx.tst_Resolve
		(	fx.nde_().Hnd_("rect").Subs_
			(	fx.nde_().Key_("pos").Atrs_("1")
			,	fx.nde_().Key_(GfmlTkn_.NullRaw).Atrs_("3")
			)
		,	fx.nde_().Typ_("rect").Subs_
			(	fx.nde_().Typ_("pos").Atrk_("x", "1")
			,	fx.nde_().Typ_("size").Atrk_("w", "3")
			)
		);
	}
	@Test  public void OutOfOrder() {
		fx.tst_Resolve
		(	fx.nde_().Hnd_("rect").Subs_
			(	fx.nde_().Key_("size").Atrs_("3")
			,	fx.nde_().Key_(GfmlTkn_.NullRaw).Atrs_("1")
			)
		,	fx.nde_().Typ_("rect").Subs_
			(	fx.nde_().Typ_("size").Atrk_("w", "3")
			,	fx.nde_().Typ_("pos").Atrk_("x", "1")
			)
		);
	}
}
