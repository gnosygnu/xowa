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
package gplx.gfui; import gplx.*;
import org.junit.*;
public class Gfui_bnd_parser_tst {
	@Before public void init() {fxt.Clear();} private Gfui_bnd_parser_fxt fxt = new Gfui_bnd_parser_fxt();
	@Test  public void Norm_one() {
		fxt.Test_x_to_norm("mod.c"							, "Ctrl");
		fxt.Test_x_to_norm("key.ctrl"						, "Ctrl");
		fxt.Test_x_to_norm("key.a"							, "A");
		fxt.Test_x_to_norm("key.left"						, "Left");
	}
	@Test  public void Norm_add() {
		fxt.Test_x_to_norm("mod.c+key.a"					, "Ctrl + A");
		fxt.Test_x_to_norm("mod.ca+key.a"					, "Ctrl + Alt + A");
		fxt.Test_x_to_norm("mod.cas+key.a"					, "Ctrl + Alt + Shift + A");
	}
	@Test  public void Norm_chord() {
		fxt.Test_x_to_norm("key.a,key.b"					, "A, B");
	}
	@Test  public void Norm_add_and_chord() {
		fxt.Test_x_to_norm("mod.c+key.a,mod.a+key.b"		, "Ctrl + A, Alt + B");
	}
	@Test  public void Gfui_add() {
		fxt.Test_x_to_gfui("Ctrl + A"						, "mod.c+key.a");
		fxt.Test_x_to_gfui("Ctrl + Shift + A"				, "mod.cs+key.a");
		fxt.Test_x_to_gfui("Ctrl + Alt + Shift + A"			, "mod.cas+key.a");
	}
}
class Gfui_bnd_parser_fxt {
	private Gfui_bnd_parser parser;
	public void Clear() {
		parser = Gfui_bnd_parser.new_en_();
	}
	public void Test_x_to_norm(String key, String expd) {
		Tfds.Eq(expd, parser.Xto_norm(key));
	}
	public void Test_x_to_gfui(String key, String expd) {
		Tfds.Eq(expd, parser.Xto_gfui(key));
	}
}
