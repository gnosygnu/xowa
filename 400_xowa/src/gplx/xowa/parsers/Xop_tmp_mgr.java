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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*;
import gplx.xowa.files.*;
import gplx.xowa.xtns.pfuncs.exprs.*; import gplx.xowa.xtns.math.*;
public class Xop_tmp_mgr {
	public Xof_math_itm			Math_itm()				{return math_itm;}		private final    Xof_math_itm math_itm = new Xof_math_itm();
	public Xof_xfer_itm			Xfer_itm()				{return xfer_itm;}		private final    Xof_xfer_itm xfer_itm = new Xof_xfer_itm();
	public Number_parser		Pfunc_num_parser_0()	{return num_parser_0;}	private final    Number_parser num_parser_0 = new Number_parser().Hex_enabled_(true);
	public Number_parser		Pfunc_num_parser_1()	{return num_parser_1;}	private final    Number_parser num_parser_1 = new Number_parser().Hex_enabled_(true);
	public Pfunc_expr_shunter	Expr_shunter()			{return expr_shunter;}	private final    Pfunc_expr_shunter expr_shunter = new Pfunc_expr_shunter();
}
