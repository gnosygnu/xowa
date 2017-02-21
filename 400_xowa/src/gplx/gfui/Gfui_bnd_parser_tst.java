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
package gplx.gfui; import gplx.*;
import org.junit.*;
public class Gfui_bnd_parser_tst {
	@Before public void init() {fxt.Clear();} private Gfui_bnd_parser_fxt fxt = new Gfui_bnd_parser_fxt();
	@Test  public void Norm_one() {
		fxt.Test__to_norm("mod.c"							, "Ctrl");
		fxt.Test__to_norm("key.ctrl"						, "Ctrl");
		fxt.Test__to_norm("key.a"							, "A");
		fxt.Test__to_norm("key.left"						, "Left");
	}
	@Test  public void Norm_add() {
		fxt.Test__to_norm("mod.c+key.a"						, "Ctrl + A");
		fxt.Test__to_norm("mod.ca+key.a"					, "Ctrl + Alt + A");
		fxt.Test__to_norm("mod.cas+key.a"					, "Ctrl + Alt + Shift + A");
	}
	@Test  public void Norm_chord() {
		fxt.Test__to_norm("key.a,key.b"						, "A, B");
	}
	@Test  public void Norm_add_and_chord() {
		fxt.Test__to_norm("mod.c+key.a,mod.a+key.b"			, "Ctrl + A, Alt + B");
	}
	@Test  public void Gfui_add() {
		fxt.Test__to_gfui("Ctrl + A"						, "mod.c+key.a");
		fxt.Test__to_gfui("Ctrl + Shift + A"				, "mod.cs+key.a");
		fxt.Test__to_gfui("Ctrl + Alt + Shift + A"			, "mod.cas+key.a");
	}
	@Test  public void Keypad_enter() {
		fxt.Test__to_norm("key.numpad_enter"				, "Numpad Enter");
		fxt.Test__to_norm("mod.c+key.numpad_enter"			, "Ctrl + Numpad Enter");
	}
	@Test  public void None() {
		fxt.Test__to_gfui("None"							, "key.none");
		fxt.Test__to_norm("key.none"						, "None");
	}
}
class Gfui_bnd_parser_fxt {
	private Gfui_bnd_parser parser;
	public void Clear() {
		parser = Gfui_bnd_parser.new_en_();
	}
	public void Test__to_norm(String key, String expd) {
		Tfds.Eq(expd, parser.Xto_norm(key));
	}
	public void Test__to_gfui(String key, String expd) {
		Tfds.Eq(expd, parser.Xto_gfui(key));
	}
}
