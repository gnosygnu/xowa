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
package gplx.core.gfobjs; import gplx.*; import gplx.core.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.langs.jsons.*;
public class Gfobj_rdr__json_tst {
	private final    Gfobj_wtr__json_fxt fxt = new Gfobj_wtr__json_fxt();
	@Test 	public void Type() {
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "{ 'k1':true"
		, ", 'k2':123"
		, ", 'k3':9876543210"
		, ", 'k4':1.23"
		, ", 'k5':null"
		, ", 'k6':'abc'"
		, "}"
		)
		, fxt.Make__nde
		( fxt.Make__fld_bool	("k1", true)
		, fxt.Make__fld_int		("k2", 123)
		, fxt.Make__fld_long	("k3", 9876543210L)
		, fxt.Make__fld_double	("k4", 1.23)
		, fxt.Make__fld_str		("k5", null)
		, fxt.Make__fld_str		("k6", "abc")
		));
	}
	@Test   public void Nested() {
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "{ 'a1':'1a'"
		, ", 'a2':"
		, "  { 'b1':'1b'"
		, "  , 'b2':"
		, "    { 'c1':'1c'"
		, "    }"
		, "  }"
		, ", 'a3':[1, 2, 3]"
		, "}"
		)
		, fxt.Make__nde
		( fxt.Make__fld_str		("a1", "1a")
		, fxt.Make__fld_nde		("a2"
		,	fxt.Make__fld_str("b1", "1b")
		,	fxt.Make__fld_nde("b2"
		,		fxt.Make__fld_str("c1", "1c"))
		)
		, fxt.Make__fld_ary		("a3", 1, 2, 3)
		));
	}
	@Test   public void Array() {
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "["
		, "  [1, 2, 3]"
		, ", ['a', 'b', 'c']"
		, ", [true, false]"
		, ", [9876543210, 9876543211, 9876543212]"
		//, ", [1.23, 1.24, 1.25]"
		, ", [{'a':1}, {'b':2}, {'c':3}]"
		, "]"
		)
		, fxt.Make__ary
		(	fxt.Make__ary		(1, 2, 3)
		,	fxt.Make__ary		("a", "b", "c")
		,	fxt.Make__ary		(true, false)
		,	fxt.Make__ary		(9876543210L, 9876543211L, 9876543212L)
		// ,	fxt.Make__ary		(1.23, 1.24, 1.25)
		,	fxt.Make__ary		
		(		fxt.Make__nde(fxt.Make__fld_int("a", 1))
		,		fxt.Make__nde(fxt.Make__fld_int("b", 2))
		,		fxt.Make__nde(fxt.Make__fld_int("c", 3))
		)
		));
	}
}
