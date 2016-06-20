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
public class Gfobj_wtr__json_tst {
	private final    Gfobj_wtr__json_fxt fxt = new Gfobj_wtr__json_fxt();
	@Test 	public void Flds() {
		fxt.Test__write
		( fxt.Make__nde
		(   fxt.Make__fld_str	("k1", "v1")
		,   fxt.Make__fld_long	("k2", 2)
		,   fxt.Make__fld_int	("k3", 3)
		)
		, "{ 'k1':'v1'"
		, ", 'k2':2"
		, ", 'k3':3"
		, "}"
		, ""
		);
	}
	@Test 	public void Sub_ndes() {
		fxt.Test__write
		( fxt.Make__nde
		(   fxt.Make__fld_str	("k1", "v1")
		,   fxt.Make__fld_nde	("k2"
		,     fxt.Make__fld_str	("k2a", "v2a")
		,     fxt.Make__fld_int	("k2b", 2)
		)
		,   fxt.Make__fld_int	("k3", 3)
		)
		, "{ 'k1':'v1'"
		, ", 'k2':"
		, "  { 'k2a':'v2a'"
		, "  , 'k2b':2"
		, "  }"
		, ", 'k3':3"
		, "}"
		, ""
		);
	}
	@Test 	public void Ary_str() {
		fxt.Test__write
		( fxt.Make__nde
		(   fxt.Make__fld_str		("k1", "v1")
		,   fxt.Make__fld_ary		("k2", "a1", "a2", "a3")
		,   fxt.Make__fld_int		("k3", 3)
		)
		, "{ 'k1':'v1'"
		, ", 'k2':"
		, "  [ 'a1'"
		, "  , 'a2'"
		, "  , 'a3'"
		, "  ]"
		, ", 'k3':3"
		, "}"
		, ""
		);
	}
	@Test 	public void Ary_int() {
		fxt.Test__write
		( fxt.Make__nde
		(   fxt.Make__fld_str		("k1", "v1")
		,   fxt.Make__fld_ary		("k2", 1, 2, 3)
		,   fxt.Make__fld_int		("k3", 3)
		)
		, "{ 'k1':'v1'"
		, ", 'k2':"
		, "  [ 1"
		, "  , 2"
		, "  , 3"
		, "  ]"
		, ", 'k3':3"
		, "}"
		, ""
		);
	}
	@Test 	public void Ary_nde() {
		fxt.Test__write
		( fxt.Make__nde
		(   fxt.Make__fld_str		("k1", "v1")
		,   fxt.Make__fld_ary		("k2"
		,     fxt.Make__nde			(fxt.Make__fld_str("k21", "v21"))
		,     fxt.Make__nde			(fxt.Make__fld_str("k22", "v22"))
		)
		,   fxt.Make__fld_int		("k3", 3)
		)
		, "{ 'k1':'v1'"
		, ", 'k2':"
		, "  ["
		, "    { 'k21':'v21'"
		, "    }"
		, "  ,"
		, "    { 'k22':'v22'"
		, "    }"
		, "  ]"
		, ", 'k3':3"
		, "}"
		, ""
		);
	}
	@Test 	public void Ary_ary() {
		fxt.Test__write
		( fxt.Make__nde
		(   fxt.Make__fld_str		("k1", "v1")
		,   fxt.Make__fld_ary		("k2"
		,     fxt.Make__ary			(1, 2, 3)
		,     fxt.Make__ary			(4, 5, 6)
		)
		,   fxt.Make__fld_int		("k3", 3)
		)
		, "{ 'k1':'v1'"
		, ", 'k2':"
		, "  ["
		, "    [ 1"
		, "    , 2"
		, "    , 3"
		, "    ]"
		, "  ,"
		, "    [ 4"
		, "    , 5"
		, "    , 6"
		, "    ]"
		, "  ]"
		, ", 'k3':3"
		, "}"
		, ""
		);
	}
	@Test 	public void Root_ary() {
		fxt.Test__write
		( fxt.Make__ary(1, 2, 3)
		, "[ 1"
		, ", 2"
		, ", 3"
		, "]"
		, ""
		);
	}
}
