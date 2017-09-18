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
