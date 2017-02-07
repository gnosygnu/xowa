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
public class z803_useCase_KbdKeyboard_tst {
	String raw; GfmlDoc gdoc;
	@Test  public void Quote() {		// smokeTest; make sure DefaultLxr supports both quoting mechanisms
		fx.tst_Parse(String_.Concat
			(	"gfui-keyboard-ui:{"
			,	"	keyQuote {"
			,	"		\"'\"	'key.quote';"
			,	"		'\"'	'key.quote+key.shift' shift;"
			,	"	}"
			,	"}"
			)
			,	fx.nde_().Hnd_("gfui-keyboard-ui").Subs_
			(		fx.nde_().Atru_("keyQuote").Subs_
			(			fx.nde_().Atrs_("'",	"key.quote")
			,			fx.nde_().Atrs_("\"",	"key.quote+key.shift", "shift")
			)
			)
			);
	}
	@Test  public void Key_LtrA() {
		fx.tst_Parse(String_.Concat
			(	TypeHeader
			,	"keys:{"
			,	"	keyA {"
			,	"		a 'key.a';"
			,	"		A 'key.a+key.shift' shift;"
			,	"	}"
			,	"}"
			)
			,	fx.nde_().Hnd_("keys").Subs_
			(		fx.nde_().Hnd_("key").Typ_("keys/key").Atrk_("size", "48,45").Atrk_("relAnchor", "{previous},rightOf").Atrk_("id", "keyA").Subs_
			(			fx.nde_().Hnd_("sendKeyCode").Typ_("sendKeyCode").Atrk_("modifier", "normal").Atrk_("displayText", "a").Atrk_("keyCode", "key.a")
			,			fx.nde_().Hnd_("sendKeyCode").Typ_("sendKeyCode").Atrk_("displayText", "A").Atrk_("keyCode", "key.a+key.shift").Atrk_("modifier", "shift")
			)
			)
			);
	}
	@Test  public void Load_Smoke() {
		Io_url url = Tfds.RscDir.GenSubFil_nest("110_gfml", "cfgs_archive", "gfui-keyboard-ui.cfg.gfml");
		raw = Io_mgr.Instance.LoadFilStr(url);
		gdoc = GfmlDoc_.parse_any_eol_(raw);
//			Tfds.Write(gdoc.RootNde().To_str());
	}
	String TypeHeader = String_.Concat
		(	"_type:{"
		,	"	keys {"
		,	"		key {"
		,	"			id;"
		,	"			size 		default='48,45';"
		,	"			relAnchor	default='{previous},rightOf';"
		,	"			sendKeyCode	type=sendKeyCode;"
		,	"		}"
		,	"	}"
		,	"	sendKeyCode {"
		,	"		displayText;"
		,	"		keyCode;"
		,	"		modifier		default=normal;"
		,	"	}"
		,	"	changeModifier {"
		,	"		displayText;"
		,	"		modifier		default=normal;"
		,	"		newModifierId;"
		,	"		isSticky;"
		,	"	}"
		,	"}"
		);
	GfmlTypeCompiler_fxt fx = GfmlTypeCompiler_fxt.new_();
}
